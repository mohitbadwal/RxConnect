package mohitbadwal.rxjsonpostexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import mohitbadwal.rxconnect.RxConnect;

public class MainActivity extends AppCompatActivity {

    String url=""; //replace with your url
    Button button;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        editText=(EditText)findViewById(R.id.edittext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             operation();
            }
        });

    }
    void operation()
    {
        RxConnect rxConnect;
        rxConnect=new RxConnect(MainActivity.this);
        //By default caching is enabled
        //By default Requests are added to queue
        //rxConnect.setAddToQueue(false);
        //rxConnect.setCachingEnabled(false);
        rxConnect.setParam("user","username");
        // set  key and value (necessary)
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
                throwable.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }

        });
    }
}
