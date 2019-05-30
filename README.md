# DjiDronePilot

DjiDronePilot is an application that utilizes the [DAILIB](https://github.com/hatati/DAILIB) library in order to add Custom AI functionality to DJI drones. This app has been tested on a DJI Mavic Air type drone. 

### Initial setup
DAILIB,  the DJI SDK and tensorflow-lite are included as dependencies in the **app** `build.gradle` file:

```
dependencies {
  ...
  api('com.dji:dji-sdk:4.8')
  compileOnly('com.dji:dji-sdk-provided:4.8')
  implementation 'org.tensorflow:tensorflow-lite:1.13.1'
  implementation 'com.nermi.dailib:dailib:1.0.6'
}
```

The following is added when working with the DJI SDK and tensorflow lite:

```
android {
  ...
  packagingOptions {
          doNotStrip "*/*/libdjivideo.so"
          doNotStrip "*/*/libSDKRelativeJNI.so"
          doNotStrip "*/*/libFlyForbid.so"
          doNotStrip "*/*/libduml_vision_bokeh.so"
          doNotStrip "*/*/libyuv2.so"
          doNotStrip "*/*/libGroudStation.so"
          doNotStrip "*/*/libFRCorkscrew.so"
          doNotStrip "*/*/libUpgradeVerify.so"
          doNotStrip "*/*/libFR.so"
          exclude 'META-INF/rxjava.properties'
      }

      aaptOptions {
          noCompress "tflite"
      }
      compileOptions {
          targetCompatibility 1.8
          sourceCompatibility 1.8
      }
}
```

Before running the application remember to add your DJI API key in the `AndroidManifest.xml` for your DJI app that you created in the [DJI Developer](https://developer.dji.com/) section:
```
<meta-data
            android:name="com.dji.sdk.API_KEY"
            android:value="YOUR API KEY HERE" />
```


### License
The library is released under the GNU General Public License `GPL-3.0`.
