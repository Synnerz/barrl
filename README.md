# barrl
Barrl is a fabric rendering library targeted towards minecraft 1.21.5 - 1.21.10

# API
How to use it as an api for your own mod.<br>
First we'll need to add the JitPack repository so we can actually pull from
### `build.gradle`
```gradle
repositories {
    mavenCentral()
	maven "https://jitpack.io"
}
```
Now we head over to the [JitPack Builds](https://jitpack.io/#Synnerz/barrl) where you can go into `Branches` and get the one you need, once you have located the correct one for you, you need to click on the `Get It` button.

### What now?
Below you can scroll in the page, it'll show you how to "import" it, basically we need to edit the `build.gradle` file once more

```gradle
dependencies {
	// To change the versions see the gradle.properties file
	minecraft "com.mojang:minecraft:${project.minecraft_version}"
	mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2"
	modImplementation "net.fabricmc:fabric-loader:${project.loader_version}"

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation "net.fabricmc.fabric-api:fabric-api:${project.fabric_version}"
	modImplementation "net.fabricmc:fabric-language-kotlin:${project.fabric_kotlin_version}"

	// Adding Barrl library
	modImplementation "com.github.Synnerz:barrl:1.21.5-SNAPSHOT"
}
```

You need to add it here, you get the link (`"com.github.Synnerz:barrl:1.21.5-SNAPSHOT"`) in the same page that was linked above.

# How To Use
Barrl implements its own `Events` and `Context`, this `Context` class is the most important one since everything is basically in there.

## `Context`
This class holds reference to a few things that are required for rendering, as well as providing some helpful helper methods for you to render in the world.

### `Context.Immediate`
`Immediate` is a `static` field that will basically act as the "global" `Context` instance in order for you to be able to use it inside other contexts (even if they don't come from `Barrl`)<br>
NOTE: This mode requires you to use it at the _almost_ end of a rendering, for example:
```kt
WorldRenderEvents.LAST.register {
    Context.Immediate?.renderWaypoint(-5.0, -61.0, -3.0, phase = true, color = Color.CYAN)
}
```
If you use `START` instead it will throw error/not work

## `Context` dot
These are the helper methods that are added inside of `Context`
* `renderFilledShape`
* `renderFilledBox`
* `renderBoxShape`
* `renderBox`
* `renderString`
* `renderBeam`
* `renderWaypoint`

## `WorldRenderEvent`
We also add our own events since it's easier to handle a library like this that way<br>
These events are used as regular `Fabric` events are used.

### Types
* `START`
* `LAST`

# License
Barrl is licensed under the [AGPL 3.0 License](https://github.com/Synnerz/barrl/blob/main/LICENSE)
