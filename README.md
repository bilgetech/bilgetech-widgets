# bilgetech-widgets


How to get a project into your build:


1.   Add the JitPack repository to your build file

Root Gradle: 

```	
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

2. Add the dependency

App Gradle:

```
dependencies {
  compile 'com.github.bilgetech:bilgetech-widgets:1.0'
}
```
