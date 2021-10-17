
![Banner](https://media.discordapp.net/attachments/751216523330322564/899141043960815647/unknown.png)

## **💻 For Developers**
In order to use this API in your mod, you need to add it to your `build.gradle`.

### **Curse Maven Plugin**
In order to get the library from Curse, you'll need to add the Curse Maven plugin in your `build.gradle`.

paste the following block after the `repositories` section in your `build.gradle`:

    repositories {
          maven { 
                url = "https://www.cursemaven.com" 
          }
    }
### **Adding the Dependency**
You'll next need to add the API as a dependency on your project and tell Forge to deobfuscate it. In the `dependencies` block paste in the following:

    implementation fg.deobf("curse.maven:cavebiomeapi-projectID:fileID")
*The fileId can be found on the end of the link of the file on CurseForge, and the projectId can be found in the project sidebar. For example, the projectId of `441706` along with the fileId of `3489595` would add CaveBiomeAPI 1.16.5-1.4.0 as a dependency.*
