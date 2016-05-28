# RxConnect
An Android Library to POST JSON and normal GET/POST using Rxjava.

Add this line to your <b>dependencies</b> in your apps <b>build.gradle</b>

<code>compile 'mohitbadwal.rxconnect:rxconnect:1.0.33'</code>

# Usage
Make new <b>RxConnect</b> object

```java  
RxConnect rxConnect=new RxConnect(context);
```
  Set Caching
```java
//by default caching is enabled for better performance
rxConnect.setCachingEnabled(false);
//call this method before setting parameters if you don't want caching
```
  Set Parameter to send

```java
rxConnect.setParam("phone","9999999999");
// setParam(keys,values)
rxConnect.setParam("password","enteredpassword");
```
  Use <b>execute</b> method to perform operation
```java
      //all override methods run on ui thread
      //use RxConnect.GET for GET
      //use RxConnect.POST for POST
      //use RxConnect.JSON_POST for POST JSON data
        rxConnect.execute(yoururl,RxConnect.GET, new RxConnect.RxResultHelper() {
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
