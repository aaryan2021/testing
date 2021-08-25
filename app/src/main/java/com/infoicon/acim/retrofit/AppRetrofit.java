package com.infoicon.acim.retrofit;






import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


public class AppRetrofit {
//    private static Context mContext;
//    private static boolean mStatus;
    private ApiService apiServices;
    private static AppRetrofit appRetrofit;

    private AppRetrofit() {
        //code for retrofit 2.0

        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                      // .addHeader(ServiceConstants.APP_VERSION,"")
                       //.addHeader("Content-Type", "application/json;charset=utf-8")
                        //.addHeader(ServiceConstants.APP_AUTHENTICATION_KEY, "hgfggff")
                        .build();
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.networkInterceptors().add(HEADER_INTERCEPTOR);
        client.interceptors().add(interceptor);
        //URL.setURLStreamHandlerFactory(new OkUrlFactory(client));
        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceUrls.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();


        apiServices = retrofit.create(ApiService.class);


    }

    public AppRetrofit(String baseUrl) {

        //code for retrofit 2.0
        Interceptor HEADER_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        // .addHeader(ServiceConstants.APP_VERSION,"")
                        //.addHeader("Content-Type", "application/json;charset=utf-8")
                        //.addHeader(ServiceConstants.APP_AUTHENTICATION_KEY, "hgfggff")
                        .build();
                return chain.proceed(request);
            }
        };

        //for logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.networkInterceptors().add(HEADER_INTERCEPTOR);
        client.interceptors().add(interceptor);
        //rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)

                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        /*.serializeNulls()*/
                        .create()))
                .build();
        apiServices = retrofit.create(ApiService.class);
    }

    public ApiService getApiServices() {
        return apiServices;
    }

    // static method to get singleton object of AppRetrofit
    public static synchronized AppRetrofit getAppRetrofitInstance() {
        if (appRetrofit == null) {
            appRetrofit = new AppRetrofit();
            return appRetrofit;
        } else {
            return appRetrofit;
        }
    }

    // static method to get singleton object of AppRetrofit
    public static synchronized AppRetrofit getAppRetrofitInstance(String baseUrl) {
        if (appRetrofit == null) {
            appRetrofit = new AppRetrofit(baseUrl);
            return appRetrofit;
        } else {
            return appRetrofit;
        }
    }


}

