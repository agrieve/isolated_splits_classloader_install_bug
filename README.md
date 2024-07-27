# README

Demo app showing Android's isolated split classloader bug.

## Repro Steps

```sh
./gradlew bundle
bundletool build-apks --bundle ./app/build/outputs/bundle/debug/app-debug.aab --output foo.apks --overwrite
unzip foo.apks splits
adb uninstall com.example.myapplication
adb install -t splits/base-master.apk
adb push splits/dynamicfeature-master.apk splits/dynamicfeature2-master.apk splits/base-fr.apk /data/local/tmp
adb shell pm install -t -p com.example.myapplication --dont-kill /data/local/tmp/dynamicfeature-master.apk
adb shell am start -n com.example.myapplication/com.example.dynamicfeature.MainActivity
# Click button to show the ClassLoader does not change when you re-launch the activity.
adb shell pm install -t -p com.example.myapplication --dont-kill /data/local/tmp/base-fr.apk
# Click button to show the ClassLoader changes when you re-launch the activity.
adb shell pm install -t -p com.example.myapplication --dont-kill /data/local/tmp/dynamicfeature2-master.apk
# Click button to show the ClassLoader changes when you re-launch the activity.
```
