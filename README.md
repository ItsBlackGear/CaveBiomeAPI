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
