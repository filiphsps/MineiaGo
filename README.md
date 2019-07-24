# MineaGo
[![Build Status](https://travis-ci.org/filfat/MineiaGo.svg?branch=master)](https://travis-ci.org/filfat/MineiaGo) [![codecov](https://codecov.io/gh/filfat/MineiaGo/branch/master/graph/badge.svg)](https://codecov.io/gh/filfat/MineiaGo) [![Discord](https://img.shields.io/static/v1.svg?label=&message=discord&color=purple&logo=discord)](https://discord.gg/u7Xma4N)

As it currently stands Minecraft happens to be split into two communities.
One side is playing on the older version "Minecraft Java Edition" limited to desktop platforms while the other one is playing on the much more portable platform "Minecraft Bedrock Edition".

Minecraft Java is developed in Java while Minecraft Bedrock is developed in C++
and while the games are extremely similar their underlying engine and therefore netcode is not. For example Minecraft Java uses TCP while Minecraft Bedrock uses Raknet through UDP.

MineaGo is a BungeeCord plugin that aims to bride that gap by allowing Bedrock clients to connect to your server network. It is currently in extremely early development and should therefore not be used in production, especially not in any large scale form.

This the current author's ([@filfat](https://github.com/filfat)) first project in Java so don't be surprised if the code feels C++ or JavaScript-like.

[![Analytics](https://bstats.org/signatures/bungeecord/MineiaGo.svg)](https://bstats.org/plugin/bungeecord/MineiaGo)

## Requirements
* openjdk11
* maven

## Contributing
Any help is appreciated, check in with us on our Discord if you don't know where to get started. You can also remind ([@filfat](https://github.com/filfat)) to write a proper contribution, cause he's probably "forgotten".

### Building
```$ mvn eclipse:eclipse install```

### Running tests
```$ todo```

### Useful resources
* [NukkitX/Protocol](https://github.com/NukkitX/Protocol)
* [MiNET](https://github.com/NiclasOlofsson/MiNET/blob/master/src/MiNET/MiNET/Net/MCPE%20Protocol%20Documentation.md)
* [pmmp](https://github.com/pmmp/PocketMine-MP/blob/master/src/pocketmine/network/mcpe/protocol/ProtocolInfo.php)
* [minecraft-bedrock-documentation](https://github.com/MisteFr/minecraft-bedrock-documentation)
* [DragonProxy](https://github.com/DragonetMC/DragonProxy)

## Thanks to
* [KernelFreeze](https://github.com/KernelFreeze) while none of his original code is still in use this project wouldn't exist without him.
* The [NukkitX team](https://github.com/NukkitX/) for their Protocol library and all their help over on discord. 

See ```AUTHORS.md``` for more info.

## License
Licensed under the "MIT" license. See ```LICENSE.md``` for more info.

If you're a medium to large-scale server community/provider contact me for details regarding specialized support.
