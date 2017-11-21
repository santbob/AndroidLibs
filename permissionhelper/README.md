# PermissionHelper [![](https://jitpack.io/v/santbob/AndroidLibs.svg)](https://jitpack.io/#santbob/AndroidLibs)

Android 6.0 introduced [Dangerous Permissions](https://developer.android.com/guide/topics/permissions/requesting.html#normal-dangerous) and you have to check for these dangerous permissions before using them in your app as opposed to before where Permissions are given at the Install time.
Dangerous and Normal permissions should still be declared in the `AndroidManifest.xml` but Dangerous permission are not given by default unlike `Normal Permissions`.

This Helper module is something you can use to if a permission has been granted to your app in the runtime.


## Download

via Maven:
```xml
<dependency>
  <groupId>com.github.santbob.AndroidLibs</groupId>
  <artifactId>permissionhelper</artifactId>
  <version>0.1.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.santbob.AndroidLibs:permissionhelper:0.1.1'
```

*if you dont have jitpack in the your project settings already, please add the following*

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

## License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
