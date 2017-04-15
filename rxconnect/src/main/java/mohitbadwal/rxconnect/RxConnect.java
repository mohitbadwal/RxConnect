package mohitbadwal.rxconnect;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
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
import java.util.PriorityQueue;
import java.util.Queue;

import rx.Observable;
import rx.Subscriber;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

/**
 * Created by MohitBadwal on 16-May-16.
 */
public class RxConnect {
    private Context context;
    List<String> subject;List<String> answer;
    public static final String JSON_POST="1";
    public static final String POST="2";
    public static final String GET="3";
    RxConnect rxConnect;
    public static final String PATCH="4";
    private boolean CACHING_ENABLED=true;
    private boolean addToQueue=true;
    private String helper="";
    List<String> title,message;
    public void setParam(String subject,String answer)
    {
        this.subject.add(subject);
       this.answer.add(answer);
        if (isCachingEnabled())
        helper=helper+subject+answer;
    }
    public void setHeader(String title,String message)
    {
        this.title.add(title);
        this.message.add(message);
    }

    public boolean isAddToQueue() {
        return addToQueue;
    }

    public void setAddToQueue(boolean addToQueue) {
        this.addToQueue = addToQueue;
    }

    public boolean isCachingEnabled()
    {
        return CACHING_ENABLED;
    }
    public void setCachingEnabled(Boolean cachingEnabled)
    {
        CACHING_ENABLED=cachingEnabled;
    }
    private void tempExecutor(final String newHelper1, final String url, final int method)
    {
        Log.d("newhelper1",newHelper1);
        Log.d("url temp", url);
        Log.d("method",""+method);
        final Observable<String> observable=AsyncSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s = null;
                    try {
                        if(method==1)
                        {
                            s=POST(url);
                        }
                        else if (method==2)
                        {
                            s=normalPOST(url);
                        }
                        else if (method==3)
                        {
                            s=normalGET(url);
                        }
                        else if (method==4)
                        {
                            s=PATCH(url);
                        }
                        else
                        {
                            s=getLinkContent(url);
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                subscriber.onNext(s);
                subscriber.onCompleted();


            }
        });
        final Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(final Throwable e) {
                if(isAddToQueue())
                networkQueueOperation.addRequest(rxConnect);
                e.printStackTrace();
            }

            @Override
            public void onNext(final String s) {
                resultCache.removeCachePut(newHelper1,s);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribe(subscriber);

    }
    private String url,method;
    private RxResultHelper rxResultHelper;

    protected String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    protected String getMethod() {
        return method;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    protected RxResultHelper getRxResultHelper() {
        return rxResultHelper;
    }

    private void setRxResultHelper(RxResultHelper rxResultHelper) {
        this.rxResultHelper = rxResultHelper;
    }

    public void execute(final String url, final String method, final RxResultHelper RxResultHelper)
    {

        Observable<String> observable= AsyncSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= null;
                String newHelper="";
                String cachedResult="";
                try {
                    if(isCachingEnabled()) {

                        if (method.contentEquals("1")) {
                            newHelper=url+helper+"jsonpost";
                            cachedResult=resultCache.getResultCache(newHelper);
                          if(cachedResult==null) {
                              s = POST(url);
                              resultCache.putResultToCache(newHelper,s);
                          }
                            else {
                              s = cachedResult;
                              tempExecutor(newHelper,url,1);
                          }
                        }
                        else if (method.contentEquals("2")) {
                            newHelper=url+helper+"post";
                            cachedResult=resultCache.getResultCache(newHelper);
                            if(cachedResult==null) {
                                s = normalPOST(url);
                                resultCache.putResultToCache(newHelper ,s);
                            }
                            else {
                                s = cachedResult;
                                tempExecutor(newHelper,url,2);
                            }
                        }
                        else if (method.contentEquals("3")) {
                            newHelper=url+helper+"get";
                            cachedResult=resultCache.getResultCache(newHelper);
                            if(cachedResult==null) {
                                s = normalGET(url);
                                resultCache.putResultToCache(newHelper,s);
                            }
                            else  {
                                s = cachedResult;
                                tempExecutor(newHelper,url,3);
                            }
                        }
                        else if(method.contentEquals("4")) {
                            newHelper=url+helper+"patch";
                            cachedResult=resultCache.getResultCache(newHelper);
                            if(cachedResult==null) {
                                s = PATCH(url);
                                resultCache.putResultToCache(newHelper,s);
                            }
                            else  {
                                s = cachedResult;
                                tempExecutor(newHelper,url,4);
                            }
                        }
                        else {
                            throw new ParamsException();
                        }
                    }
                    else
                    {
                        if(method.contentEquals("1"))
                            s = POST(url);
                        else if (method.contentEquals("2"))
                            s=normalPOST(url);
                        else if (method.contentEquals("3"))
                            s=normalGET(url);
                        else if (method.contentEquals("4"))
                            s=PATCH(url);
                        else {
                            throw new ParamsException();
                        }

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
                if(isAddToQueue())
                    networkQueueOperation.addRequest(rxConnect);
                RxResultHelper.onError(e);



            }

            @Override
            public void onNext(final String s) {
                        if(!s.contentEquals("Empty Stream"))
                            RxResultHelper.onResult(s);
                        else
                            RxResultHelper.onNoResult();

            }
        };
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        setUrl(url);
        setMethod(method);
        setRxResultHelper(RxResultHelper);
    }

    public void executeNoParam(final String url,final RxResultHelper RxResultHelper)
    {
        Observable<String> observable= AsyncSubject.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= null;
                try {   if (isCachingEnabled()) {
                    if (resultCache.getResultCache(url) == null) {
                        s = getLinkContent(url);
                        resultCache.putResultToCache(url, s);
                    } else {
                        s = resultCache.getResultCache(url);
                        tempExecutor(url,url,0);
                    }
                }
                    else {
                    s = getLinkContent(url);
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
                if(isAddToQueue())
                    networkQueueOperation.addRequest(rxConnect);
                RxResultHelper.onError(e);


            }

            @Override
            public void onNext(final String s) {

                        if(!s.contentEquals("Empty Stream"))
                            RxResultHelper.onResult(s);
                        else
                            RxResultHelper.onNoResult();

            }
        };
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        setUrl(url);
        setMethod(RxConnect.GET);
        setRxResultHelper(RxResultHelper);
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
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Content-type", "application/json");
        if(title.size()!=0)
        {
            for (int i=0;i<title.size();i++)
            {
                httpGet.setHeader(title.get(i),message.get(i));
            }
        }
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
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if(title.size()!=0)
        {
            for (int i=0;i<title.size();i++)
            {
                httpPost.setHeader(title.get(i),message.get(i));
            }
        }
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
            if(title.size()!=0)
            {
                for (int i=0;i<title.size();i++)
                {
                    httpPost.setHeader(title.get(i),message.get(i));
                }
            }
            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null)
                result = streamConverter(inputStream);
            else
                result = "Empty Stream";



        return result;
    }
    private String PATCH(String url) throws Exception {
        InputStream inputStream = null;
        String result = "";


        HttpClient httpclient = new DefaultHttpClient();

        HttpPatch httpPost = new HttpPatch(url);
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
        if(title.size()!=0)
        {
            for (int i=0;i<title.size();i++)
            {
                httpPost.setHeader(title.get(i),message.get(i));
            }
        }
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
    ResultCache resultCache;
    NetworkQueueOperation networkQueueOperation;
    public RxConnect(Activity context)
    {   this.context=context;
        subject=new ArrayList<>();
        Queue<RxConnect> queue=new PriorityQueue<>();
        answer=new ArrayList<>();
        title=new ArrayList<>();
        message=new ArrayList<>();
        resultCache=new ResultCache();
        networkQueueOperation=new NetworkQueueOperation(context);
        rxConnect=this;
        if(rxConnect==null)
        {
            Log.d("qwerty","null rxconnect");
        }
    }
    public RxConnect(Context context)
    {   this.context=context;
        subject=new ArrayList<>();
        answer=new ArrayList<>();
        title=new ArrayList<>();
        message=new ArrayList<>();
        resultCache=new ResultCache();
        networkQueueOperation=new NetworkQueueOperation(context);
        rxConnect=this;
        if(rxConnect==null)
        {
            Log.d("qwerty","null rxconnect");
        }
    }
public interface RxResultHelper
{
    void onResult(String result);
    void onNoResult();
    void onError(Throwable throwable);

}
}
