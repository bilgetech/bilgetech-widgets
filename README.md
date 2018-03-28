# bilgetech-widgets


How to get a project into your build:


1. Add the JitPack repository to your build file

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
  compile 'com.github.bilgetech:bilgetech-widgets:1.0.2'
}
```

3. Application.java
```
    @Override
    public void onCreate() {
        super.onCreate();
        BTInitializer.init(this);
    }
```

Simple usages after initialize : https://github.com/bilgetech/bilgetech-widgets/blob/master/app/src/main/java/com/bilgetech/libraries/MainActivity.java

# Custom Widgets

```
MainToolbarLayout
SimpleToolbarLayout
RecyclerViewLayout
ProgressButton


Custom Image Views
CircularImageView
ProfileImageView
SimpleImageView
SquareImageView

CustomAlertDialogBuilder

LinkDispatcher

And a few helper class
```
