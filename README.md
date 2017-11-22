# AndroidLibs [![](https://jitpack.io/v/santbob/AndroidLibs.svg)](https://jitpack.io/#santbob/AndroidLibs)
A collection of simple android libraries

1. [PermissionHelper](permissionhelper/README.md) 
2. [LocationHelper](locationhelper/README.md)

## Download
You can download the individual modules (libary) by following the corresponding README linked above or you can include the whole library by including the settings below.

via Maven:
```xml
<dependency>
  <groupId>com.github.santbob</groupId>
  <artifactId>AndroidLibs</artifactId>
  <version>0.1.3</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.santbob.AndroidLibs:0.1.3'
```

## Example

Example app is at the root of this repo, please refer [MainActivity.java](app/src/main/java/com/santbob/androidlibs_sampleapp/MainActivity.java) or clone/download this repo to see it in action.

## JitPack

The libarry is distrubuted via jitpack, if you dont have jitpack in the your project settings already, please add the following

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

## Contribution
Please feel free to send a Pull Request, if there are bugs that you find or want to add features. Also log issues if you see bugs, will try my best to fix ASAP.

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
