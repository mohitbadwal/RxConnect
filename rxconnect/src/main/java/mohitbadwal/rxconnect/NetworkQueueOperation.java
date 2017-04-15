package mohitbadwal.rxconnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by JohnConnor on 15-Apr-17.
 */

public class NetworkQueueOperation {
    private static RequestQueue<RxConnect> rxConnectRequestQueue;
    private static Thread thread;
    private static int i=0;
    private static Context context;
    static {
        rxConnectRequestQueue=new RequestQueue<>();
        i=0;
        redoRequest();
    }

    public NetworkQueueOperation(Context context)
    {
       this.context=context;
    }
    public void addRequest(RxConnect rxConnect)
    {
        rxConnectRequestQueue.offer(rxConnect);
    }
    private static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    private static void performQueueOperation()
    {   int e=rxConnectRequestQueue.size();
        for(int j=0;j<e;j++)
        {
            RxConnect r=(RxConnect)rxConnectRequestQueue.poll();
            if(r.getParamSize())
            r.execute(r.getUrl(),r.getMethod(),r.getRxResultHelper());
            else
            r.executeNoParam(r.getUrl(),r.getRxResultHelper());
            rxConnectRequestQueue.remove();
        }
    }
    private static void redoRequest()
    {
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    Log.d("qwerty","running" + rxConnectRequestQueue.size());
                       if(haveNetworkConnection())
                       {
                           if(i==0)
                           {
                               performQueueOperation();
                               i=1;
                           }
                       }
                    else i=0;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
