# MineaGo
[![Build Status](https://travis-ci.org/filfat/MineiaGo.svg?branch=master)](https://travis-ci.org/filfat/MineiaGo) [![codecov](https://codecov.io/gh/filfat/MineiaGo/branch/master/graph/badge.svg)](https://codecov.io/gh/filfat/MineiaGo) [![Discord](https://img.shields.io/static/v1.svg?label=&message=discord&color=purple&logo=discord)](https://discord.gg/u7Xma4N)

MineaGo is a BungeeCord plugin that allows bedrock clients to connect to your server network. It is currently in extremely early development and should therefore not be used in production, especially not in any large scale form.

This the current author's ([@filfat](https://github.com/filfat)) first project in Java so don't be surprised if the code feels C++ or JavaScript-like.

[![Analytics](https://bstats.org/signatures/bungeecord/MineiaGo.svg)](https://bstats.org/plugin/bungeecord/MineiaGo)

## Requirements
* openjdk11
* maven

## Contributing
Any help is appreciated, but especially with nbt & chunks.

### Building
```$ mvn eclipse:eclipse install```

### Running tests
```$ todo```

### How it's intended to work
![Diagram](https://imgur.com/0NwkI8H.png)

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
