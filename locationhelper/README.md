# LocationHelper [![](https://jitpack.io/v/santbob/AndroidLibs.svg)](https://jitpack.io/#santbob/AndroidLibs)

If you want to the listen to location updates in Android, this module will help you do that easily.

via Maven:
```xml
<dependency>
  <groupId>com.github.santbob.AndroidLibs</groupId>
  <artifactId>locationhelper</artifactId>
  <version>0.1.2</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.github.santbob.AndroidLibs:locationhelper:0.1.2'
```
## How to Use

```java

// Sets the desired interval for active location updates. This interval is inexact. 
// You may not receive updates at all if no location sources are available, or you may receive them slower than requested. 
// You may also receive updates faster than requested if other applications are requesting location at a faster interval.
private static final int UPDATE_INTERVAL_IN_MILLISECONDS = 1000; //1sec

// Sets the fastest rate for active location updates. 
// This interval is exact, and your application will never receive updates faster than this value.
private static final int FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 500; // half a sec

// pass `false` for the last param, if you need just one update (just to get current location), in that case dont need to call stopLocationUpdates.
// experiement with different values for the accuracy param.
LocationHelper locationHelper = new LocationHelper(this, new LocationConfig(
                UPDATE_INTERVAL_IN_MILLISECONDS, 
                FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS, 
                LocationConfig.Priority.PRIORITY_HIGH_ACCURACY, 
                true));
                
// To start requesting location updates.
locationHelper.requestLocationUpdate(new LocationHelper.LocationHelperListener() {
    @Override
    public void onLocationIdentified(Location location) {
        String builder = "Latitude : " + location.getLatitude() +
                "\nLongitude : " + location.getLongitude();
        locationInfoText.setText(builder);
    }
});

// To Stop requesting location updates
locationHelper.stopLocationUpdates();
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
