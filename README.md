# RxJSONPost
An Android Library to POST JSON using Rxjava.

Add this line to your <b>dependencies</b> in your apps <b>build.gradle</b>

<code>compile 'mohitbadwal.rxjsonpost:rxjsonpost:1.0'</code>

# Usage
Make new <b>JSONPost</b> object

```java  
JSONPost jsonPost=new JSONPost();
```

  Set Parameter of JSON

  ```java
jsonPost.setParam("phone","9999999999");
// set json key and value (necessary)
jsonPost.setParam("password","enteredpassword");
  ```
  Use <b>execute</b> method to perform operation
```java
      //all override methods run on ui thread
        jsonPost.execute(yoururl,context, new JSONPost.JSONResultHelper() {
            @Override
            public void onResult(String result) {
              //do something on result
            }

            @Override
            public void onNoResult() {
                //do something
            }

            @Override
            public void onError(Throwable throwable) {
               //do somenthing on error
            }

        });
```

Add Permission to your AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
