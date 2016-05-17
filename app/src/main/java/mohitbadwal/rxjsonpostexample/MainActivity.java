package mohitbadwal.rxjsonpostexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import mohitbadwal.rxconnect.RxConnect;

public class MainActivity extends AppCompatActivity {
    RxConnect rxConnect;
    String url=""; //replace with your url
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rxConnect=new RxConnect(MainActivity.this);

        rxConnect.setParam("phone","9999999999");
        // set json key and value (necessary)
        rxConnect.setParam("password","enteredpassword");

       //all override methods run on ui thread
        rxConnect.execute(url,RxConnect.GET, new RxConnect.RxResultHelper() {
            @Override
            public void onResult(String result) {
                Log.d("result here",result);
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNoResult() {
                Toast.makeText(getApplicationContext(),"No Result",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }

        });
    }
}
