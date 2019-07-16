# MineaGo [![Build Status](https://travis-ci.org/filfat/MineiaGo.svg?branch=master)](https://travis-ci.org/filfat/MineiaGo) [![CircleCI](https://circleci.com/gh/filfat/MineiaGo/tree/master.svg?style=svg)](https://circleci.com/gh/filfat/MineiaGo/tree/master)

MineaGo is a BungeeCord plugin that allows bedrock clients to connect to your server network. It is currently in extremely early development and should therefore not be used in production, especially not in any large scale form.

This the current author's ([@filfat](https://github.com/filfat)) first project in Java so don't be surprised if the code feels C++ or JavaScript-like.

## Requirements
* openjdk11
* maven

## Contributing
Any help is appreciated, but especially with nbt & chunks.

### Building
```$ mvn eclipse:eclipse install```

### Running tests
```$ todo```

## How it's intended to work
![Diagram](https://imgur.com/0NwkI8H.png)

## Useful resources
* [NukkitX/Protocol](https://github.com/NukkitX/Protocol)
* [MiNET](https://github.com/NiclasOlofsson/MiNET/blob/master/src/MiNET/MiNET/Net/MCPE%20Protocol%20Documentation.md)
* [pmmp](https://github.com/pmmp/PocketMine-MP/blob/master/src/pocketmine/network/mcpe/protocol/ProtocolInfo.php)
* [minecraft-bedrock-documentation](https://github.com/MisteFr/minecraft-bedrock-documentation)
* [DragonProxy](https://github.com/DragonetMC/DragonProxy)
