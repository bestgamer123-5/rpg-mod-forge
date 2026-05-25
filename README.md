# RPG Minecraft Mod (Forge)

A Minecraft Forge mod that adds an **RPG Sword** and launcher weapons.

## Features
- Adds a craftable RPG Sword.
- Adds RPG, Nuke, and Hunter launchers.

## Build
```bash
./gradlew build
```

### Java requirement
- Forge `1.20.1` requires Java 17 bytecode for compilation/runtime.
- If your machine uses a newer JDK (for example Java 21), this project now compiles with `--release 17` so a dedicated Gradle toolchain install is not required.

## Run (client dev environment)
```bash
./gradlew runClient
```
