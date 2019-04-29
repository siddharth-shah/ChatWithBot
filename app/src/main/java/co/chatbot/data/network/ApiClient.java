package co.chatbot.data.network;

import java.io.IOException;

import co.chatbot.AppConstants;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://www.personalityforge.com/api/";


    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHTTPClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHTTPClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        final HttpUrl url = request.url();
                        final HttpUrl newUrl = url.newBuilder()
                                .addQueryParameter(AppConstants.QUERY_PARAM_API_KEY, AppConstants.API_KEY)
                                .addQueryParameter(AppConstants.QUERY_PARAM_CHAT_BOT_ID,
                                        AppConstants.CHAT_BOT_ID).build();

                        Request newRequest = request.newBuilder()
                                .url(newUrl).build();
                        return chain.proceed(newRequest);
                    }
                }).build();


    }
}
