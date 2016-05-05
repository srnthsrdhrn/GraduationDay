package io.iqube.kctgrad.ConnectionService;

import com.google.gson.JsonObject;

import java.util.List;

import io.iqube.kctgrad.model.Degree;
import io.iqube.kctgrad.model.Question;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by raja sudhan on 2/2/2016.
 */public class ServiceGenerator {

//    public static final String API_BASE_URL = "http://10.1.75.35:8000/polls/api/";
        public static final String API_BASE_URL = "http://kctgrad-brafius.rhcloud.com/polls/api/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public interface KCTClient{
        @GET("questions/")
        Call<List<Question>> getQuestions();

        @GET("degrees/")
        Call<List<Degree>> getDegrees();

        @POST("answers/")
        Call<JsonObject> finishFeedBack(@Body JsonObject answer);



        @POST("feedback/")
        Call<JsonObject>postFeedback(@Body JsonObject feedBack);
    }
}