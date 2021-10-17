![Banner](https://media.discordapp.net/attachments/751216523330322564/899141043960815647/unknown.png)

## **💻 Development Purposes**
if you want to use this API in your mod, you need to implement it to your `build.gradle`.

### **Curse Maven Plugin**
To get the library, you'll need to add the Curse Maven repository in your `build.gradle`.

paste the following block after the `repositories` section in your `build.gradle`:

    maven {
          url = "https://www.cursemaven.com"
    }
### **Adding the Dependency**
You'll next need to implement the API as a dependency on your project and tell Forge to deobfuscate it. In the `dependencies` block paste in the following:

    implementation fg.deobf("curse.maven:cavebiomeapi-projectID:fileID")
*The fileId can be found on the end of the link of the file on CurseForge, and the projectId can be found in the project sidebar. For example, the projectId of `441706` along with the fileId of `3489595` would add CaveBiomeAPI 1.16.5-1.4.0 as a dependency.*

### **Refresh Gradle**
Once you've done the steps above, you now need to reload the Gradle project. This can differ from IDE to IDE, so we can't give explicit details on how. On most IDEs, just follow the steps you would when setting up your mod environment.

### **Adding the API as a Forge Dependency**
You'll need to tell Forge that the API is a hard-dependency for your mod, this means that the API will be **required** to run your mod. At the bottom of your `mods.toml` file paste the following:
```
[[dependencies.<modid>]]
    modId="cavebiomeapi"
    mandatory=true
    versionRange="[x.x.x,)"
    ordering="AFTER"
    side="BOTH"
```
Replace `<modid>` with your mod's id of course. Set the `versionRange` to the version you chose on CurseForge.

That's it! You should now have CaveBiomeAPI in your workspace and as a required dependency. Any issues you find should be reported to the [Issue Tracker](https://github.com/ItsBlackGear/CaveBiomeAPI/issues).

## **💻 Using the API**
We tried to make this API as simple as possible, so simple that you'll have your own cave biome in no time

### **Creating a Cave Biome**
Creating a cave biome is not really complicated, you follow the same steps that you do to create a default biome,
you can use both the `Deferred Registries` or the `Vanilla Registry` methods, any of them will work with this API so you don't have to worry about them 

We don't recommend using features that are used in the surface while creating a biome like using `DefaultBiomeFeatures.withDisks` because these features will generate in the surface in biomes where they don't belong, such as swamps.

### **Injecting a Cave Biome**
Injecting the cave biome is the easiest and most complicated part of this API, all you have to do is initialize it by using the following method:

    CaveBiomeAPI.addCaveBiome(biome, new Biome.Attributes(temperature, humidity, altitude, weirdness, offset));
*It won't be always `Biome.Attributes`, if you're using the official mappings they will be replaced by `Biome.ClimateParameters` or at least that's how i've seen it working in 1.17*

If you are familiar with `MultiNoise` you'll catch this up pretty quickly, if not then don't worry, you'll learn how to use it in no time. 
The `biome` parameter will call for the cave biome that you want to inject.

MultiNoise works with 4 different noise parameters which modify the biome distribution, i could explain it myself but one of the Devs of Minecraft actually made a video explaining how MultiNoise works and with graphic examples right [here](https://www.youtube.com/watch?v=VYZl2MUat-M).

As an extra feature, a cool person called Misode made a cool tool which will help you to preview biome generation and it does support MultiNoise you can find the tool [here](https://misode.github.io/dimension/) and i've made an [example json](https://github.com/ItsBlackGear/CaveBiomeAPI/blob/main/EXAMPLE.json) file that you can use to preview the cave biome generation using the 1.17 cave biomes, just paste it in the json holder of the website and mess with the values until you like it.

Once you're happy with the results just add them into the method and remember to initialize it, here's an example using the vanilla caves:

    CaveBiomeAPI.addCaveBiome(Biomes.DRIPSTONE_CAVES, new Biome.Attributes(0.0F, 0.0F, 0.8F, 0.0F, 0.0F));
    CaveBiomeAPI.addCaveBiome(Biomes.LUSH_CAVES, new Biome.Attributes(0.0F, 0.7F, 0.0F, 0.0F, 0.0F));
After injecting the cave biomes you can proceed to run the game and you'll find your cave biomes generating in the underground and ready to go.
If by any chance you have any more further questions or you're having some difficulties while following these steps feel free to contact me in my [discord server](https://discord.gg/fTzK9SP), i'm very active and always glad to help
