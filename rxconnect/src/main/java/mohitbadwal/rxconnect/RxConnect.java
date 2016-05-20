package mohitbadwal.rxconnect;

import android.app.Activity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

/**
 * Created by MohitBadwal on 16-May-16.
 */
public class RxConnect {
    private Activity context;
    List<String> subject;List<String> answer;
    public static final String JSON_POST="1";
    public static final String POST="2";
    public static final String GET="3";
    public void setParam(String subject,String answer)
    {
        this.subject.add(subject);
       this.answer.add(answer);
    }
    public void execute(final String url, final String method, final RxResultHelper RxResultHelper)
    {
        Observable<String> observable= AsyncSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= null;
                try {
                    if(method.contentEquals("1"))
                    s = POST(url);
                    else if (method.contentEquals("2"))
                        s=normalPOST(url);
                    else if (method.contentEquals("3"))
                        s=normalGET(url);
                    else {
                        throw new ParamsException();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RxResultHelper.onError(e);
                    }
                });

            }

            @Override
            public void onNext(final String s) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!s.contentEquals("Empty Stream"))
                            RxResultHelper.onResult(s);
                        else
                            RxResultHelper.onNoResult();
                    }
                });
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribe(subscriber);
    }
    public void executeNoParam(final String url,final RxResultHelper RxResultHelper)
    {
        Observable<String> observable= AsyncSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= null;
                try {
                    s=getLinkContent(url);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RxResultHelper.onError(e);
                    }
                });

            }

            @Override
            public void onNext(final String s) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!s.contentEquals("Empty Stream"))
                            RxResultHelper.onResult(s);
                        else
                            RxResultHelper.onNoResult();
                    }
                });
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribe(subscriber);
    }
    private String getLinkContent(String url) throws Exception {
        InputStream inputStream = null;
        String result = "";

        DefaultHttpClient httpClient = new DefaultHttpClient();
        url += "?";
        HttpGet httpGet = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();
        if(inputStream != null)
            result = streamConverter(inputStream);
        else
            result = "Empty Stream";



        return result;




    }
    private String normalGET(String url) throws Exception {
        InputStream inputStream = null;
        String result = "";
        if(!(subject.size()>0))
        {
            throw new ParamsException();
        }
        DefaultHttpClient httpClient = new DefaultHttpClient();
      
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (int i=0;i<subject.size();i++) {

            params.add(new BasicNameValuePair(subject.get(i),answer.get(i)));


        }
        String paramString = URLEncodedUtils.format(params, "utf-8");
        url += "?" + paramString;
        HttpGet httpGet = new HttpGet(url);

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();
        if(inputStream != null)
            result = streamConverter(inputStream);
        else
            result = "Empty Stream";



        return result;
       
       
       
    
    }
    private String normalPOST(String url) throws Exception {
        InputStream inputStream = null;
        String result = "";
        if(!(subject.size()>0))
        {
            throw new ParamsException();
        }

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        for (int i=0;i<subject.size();i++) {

            params.add(new BasicNameValuePair(subject.get(i),answer.get(i)));


        }
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        inputStream = httpEntity.getContent();
        

        if(inputStream != null)
            result = streamConverter(inputStream);
        else
            result = "Empty Stream";



        return result;
    }
    private String POST(String url) throws Exception {
        InputStream inputStream = null;
        String result = "";


            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            String json = "";
            JSONObject jsonObject = new JSONObject();
            if(!(subject.size()>0))
            {
                throw new ParamsException();
            }

            for (int i=0;i<subject.size();i++) {


                jsonObject.accumulate(subject.get(i), answer.get(i));

            }

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = streamConverter(inputStream);
            else
                result = "Empty Stream";



        return result;
    }
    private  String streamConverter(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String mine = "";
        String result = "";
        while((mine = bufferedReader.readLine()) != null)
            result += mine;

        inputStream.close();
        return result;

    }

    public RxConnect(Activity context)
    {   this.context=context;
        subject=new ArrayList<>();
        answer=new ArrayList<>();
    }
public interface RxResultHelper
{
    void onResult(String result);
    void onNoResult();
    void onError(Throwable throwable);

}
}
