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
  <version>0.1.2</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.santbob.AndroidLibs:permissionhelper:0.1.2'
```

## How to Use

```java
PermissionHelper permissionHelper = new PermissionHelper(this, new PermissionHelper.PermissionHelperListener() {
    @Override
    public void OnPermissionGranted(String permission, int requestCode) {
        //yippe, your app has been granted the permission, go ahead and do something useful with it.
    }

    @Override
    public void OnPermissionDenied(String permission, int requestCode) {
      //Argh! Permission denied, decide what to do next, may be give feature which doesnt require this permission.
    }

    @Override
    public void OnShowRationale(String permission, int requestCode) {
      //Show a message, why you are asking for this permission and then upon the user accepting the message request permission again this time without the rationale check.
    }

    @Override
    public void OnNeverAskAgain(String permission, int requestCode) {
      //Argh! user has denied permission and has selected never show again! 
    }
});

//Requesting permission with Rationale Check
permissionHelper.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);

//requesting permission without Rationale Check
permissionHelper.requestPermissionWithoutRationalCheck(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_REQUEST_CODE);
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
