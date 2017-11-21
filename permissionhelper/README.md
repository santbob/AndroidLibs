# PermissionHelper [![](https://jitpack.io/v/santbob/AndroidLibs.svg)](https://jitpack.io/#santbob/AndroidLibs)

Android 6.0 introduced [Dangerous Permissions](https://developer.android.com/guide/topics/permissions/requesting.html#normal-dangerous) and you have to check for these dangerous permissions before using them in your app as opposed to before where Permissions are given at the Install time.
Dangerous and Normal permissions should still be declared in the `AndroidManifest.xml` but Dangerous permission are not given by default unlike `Normal Permissions`.

This Helper module is something you can use to if a permission has been granted to your app in the runtime.


## Download

via Maven:
```xml
<dependency>
  <groupId>com.github.santbob.AndroidLibs</groupId>
  <artifactId>locationhelper</artifactId>
  <version>0.1.1</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.santbob.AndroidLibs:locationhelper:0.1.1'
```

*if you already dont have jitpack in the your gradle, please add the following*

via Maven - Add the JitPack repository to your build file 
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
or Gradle - Add it in your root build.gradle at the end of repositories
```groovy
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add it in your root build.gradle at the end of repositories:
