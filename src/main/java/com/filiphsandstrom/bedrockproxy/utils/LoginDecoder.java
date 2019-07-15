package com.filiphsandstrom.bedrockproxy.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import com.google.gson.*;
import com.google.common.io.*;
import com.google.common.reflect.*;

public class LoginDecoder {

    private static final Gson gson = new Gson();

    private static final String ENCODED_ROOT_CA_KEY;
    private static final KeyFactory EC_KEY_FACTORY;

    // login data
    private final byte[] chainJWT;
    public String username;
    public UUID clientUniqueId;

    // player data
    private final byte[] clientDataJWT;
    public long clientId;
    public JsonObject clientData;
    public byte[] skin;
    public String skinGeometryName;
    public byte[] skinGeometry;

    private boolean loginVerified = false;

    private JsonObject extraData = null;

    public LoginDecoder(byte[] chainJWT, byte[] clientDataJWT) {
        this.chainJWT = chainJWT;
        this.clientDataJWT = clientDataJWT;
    }

    public void decode() {
        Map<String, List<String>> map = gson.fromJson(new String(this.chainJWT, StandardCharsets.UTF_8), new TypeToken<Map<String, List<String>>>() {}.getType());
        
        if (map.isEmpty() || !map.containsKey("chain") || map.get("chain").isEmpty())
            return;

        List<DecodedJWT> chainJWTs = new ArrayList<>();

        // Add the JWT tokens to a chain
        for (String token : map.get("chain"))
            chainJWTs.add(JWT.decode(token));

        DecodedJWT clientJWT = null;
        if (this.clientDataJWT != null) {
            clientJWT = JWT.decode(new String(this.clientDataJWT, StandardCharsets.UTF_8));
            chainJWTs.add(clientJWT);
        }

        // first step, check if the public provided key can decode the received chain
        try {
            ECPublicKey prevPublicKey = null;

            for (DecodedJWT jwt : chainJWTs) {
                JsonObject payload = gson.fromJson(new String(Base64.getDecoder().decode(jwt.getPayload())),
                        JsonObject.class);
                String encodedPublicKey = null;
                ECPublicKey publicKey = null;

                if (payload.has("identityPublicKey")) {
                    encodedPublicKey = payload.get("identityPublicKey").getAsString();
                    publicKey = (ECPublicKey) EC_KEY_FACTORY
                            .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(encodedPublicKey)));
                }

                // Trust the root ca public key and use it to verify the chain
                if (ENCODED_ROOT_CA_KEY.equals(encodedPublicKey) && payload.has("certificateAuthority")
                        && payload.get("certificateAuthority").getAsBoolean()) {
                    prevPublicKey = publicKey;
                    continue;
                }

                // This will happen if the root ca key we have does not match the one presented
                // by the client chain
                if (prevPublicKey == null)
                    throw new NullPointerException(
                            "No trusted public key found in chain, is the client logged in or cracked");

                // Throws a SignatureVerificationException if the verification failed
                Algorithm.ECDSA384(prevPublicKey, null).verify(jwt);

                // Verification was successful since no exception was thrown
                // Set the previous public key to this one so that it can be used
                // to verify the next JWT token in the chain
                prevPublicKey = publicKey;
            }

            // The for loop successfully verified all JWT tokens with no exceptions thrown
            this.loginVerified = true;

            Logger.getLogger(this.getClass().getSimpleName())
                    .info("The LoginPacket has been successfully verified for integrity");

        } catch (Exception e) {
            this.loginVerified = false;
            Logger.getLogger(this.getClass().getSimpleName()).info("Failed to verify the integrity of the LoginPacket");
            e.printStackTrace();
        }

        // step 2, extract player displayName and identity
        // This is in its own for loop due to the possibility that the chain
        // verification failed
        for (DecodedJWT jwt : chainJWTs) {
            JsonObject payload = gson.fromJson(new String(Base64.getDecoder().decode(jwt.getPayload())),
                    JsonObject.class);
            // Get the information we care about - The UUID and display name
            if (payload.has("extraData") && !payload.has("certificateAuthority")) {
                extraData = payload.get("extraData").getAsJsonObject();
                if (extraData.has("displayName"))
                    this.username = extraData.get("displayName").getAsString();
                if (extraData.has("identity"))
                    this.clientUniqueId = UUID.fromString(extraData.get("identity").getAsString());
                break;
            }
        }

        // step 3, extract LanguageCode and Skin
        // client data & skin
        if (clientJWT != null) {
            this.clientData = gson.fromJson(
                    new String(Base64.getDecoder().decode(clientJWT.getPayload()), StandardCharsets.UTF_8),
                    JsonObject.class);

            if (this.clientData.has("ClientRandomId"))
                this.clientId = this.clientData.get("ClientRandomId").getAsLong();

            if (this.clientData.has("SkinGeometryName"))
                this.skinGeometryName = this.clientData.get("SkinGeometryName").getAsString();
            if (this.clientData.has("SkinGeometry"))
                this.skinGeometry = Base64.getDecoder().decode(this.clientData.get("SkinGeometry").getAsString());
        }
    }

    public boolean isLoginVerified() {
        return this.loginVerified;
    }

    static {
        try  {
            ENCODED_ROOT_CA_KEY = "MHYwEAYHKoZIzj0CAQYFK4EEACIDYgAE8ELkixyLcwlZryUQcu1TvPOmI2B7vX83ndnWRUaXm74wFfa5f/lwQNTfrLVHa2PmenpGI6JhIMUJaWZrjmMj90NoKNFSNBuKdm8rYiXsfaz3K36x/1U26HpG0ZxK/V1V";

            // Java 7 fully implements elliptic curve crypto
            EC_KEY_FACTORY = KeyFactory.getInstance("EC");

        } catch (Exception e) {
            e.printStackTrace();
            // Throw a runtime exception to indicate that this exception should never happen
            // Possible causes include the key file missing and EC not supported
            // Though EC is supported in Java 7, and the key file is embedded into the jar
            throw new RuntimeException(e);
        }

    }

    public String getChainJWT() {
        return new String(this.chainJWT, StandardCharsets.UTF_8);
    }

    public String getClientDataJWT() {
        if (this.clientDataJWT != null)
            return new String(this.clientDataJWT, StandardCharsets.UTF_8);
        return null;
    }

}