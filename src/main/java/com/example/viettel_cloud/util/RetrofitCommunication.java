package com.example.viettel_cloud.util;

import com.example.viettel_cloud.configuration.APIConnectionConfig;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RetrofitCommunication {

    public static <T> T buildSetting(Class<T> classType, String apiUrl, APIConnectionConfig.Connection connect) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(buildCommunication(connect))
                .build();

        return retrofit.create(classType);
    }

    private static OkHttpClient buildCommunication(APIConnectionConfig.Connection connect) {
        final int maxIdle = 256;
        final long keepAliveDuration = 60;

        Dispatcher dispatcher = new Dispatcher(Executors.newFixedThreadPool(connect.getMaxRequest()));
        dispatcher.setMaxRequests(connect.getMaxRequest());
        dispatcher.setMaxRequestsPerHost(connect.getMaxRequestPerHost());
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .addInterceptor(logger)
                .connectTimeout(connect.getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(connect.getConnectTimeout(), TimeUnit.SECONDS)
                .writeTimeout(connect.getConnectTimeout(), TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(maxIdle, keepAliveDuration, TimeUnit.SECONDS))
                .build();
    }
}
