package com.app.demoopencartapp.di.modules;




import com.app.demoopencartapp.data.network.ApiHelper;
import com.app.demoopencartapp.di.WithOutAuth;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;


/**
 * Created by svk on 5/6/17.
 */
@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@WithOutAuth OkHttpClient httpClient){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }



  /*  @Singleton
    @Provides
    @WithAuth
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logging, BasicAuthInterceptor interceptor){

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(interceptor);
        httpClient.connectTimeout(200, TimeUnit.SECONDS);
        httpClient.readTimeout(200, TimeUnit.SECONDS);
        return httpClient.build();

    }*/

    @Singleton
    @Provides
    @WithOutAuth
    OkHttpClient provideHttpClientWithoutAuth(HttpLoggingInterceptor logging){
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(200, TimeUnit.SECONDS);
        httpClient.readTimeout(200, TimeUnit.SECONDS);
        return httpClient.build();

    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideLogInterceptor(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Timber.tag("OkHttp").d(message);
            }
        });
        return logging;
    }

 /*   @Singleton
    @Provides
    BasicAuthInterceptor provideAuthInterceptor(){
        return new BasicAuthInterceptor(Constants.USERNAME, Constants.APP_SECRET);
    }*/

    @Singleton
    @Provides
    ApiHelper provideApiHelper(Retrofit retrofit){
        return retrofit.create(ApiHelper.class);
    }
}
