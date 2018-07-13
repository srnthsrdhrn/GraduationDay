package io.iqube.kctgrad.ConnectionService;

import com.google.gson.JsonObject;

import java.util.List;

import io.iqube.kctgrad.model.Agenda;
import io.iqube.kctgrad.model.Guest;
import io.iqube.kctgrad.model.NotificationModel;
import io.iqube.kctgrad.model.Question;
import io.iqube.kctgrad.model.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public class ServiceGenerator {

//    public static final String API_BASE_URL = "https://gday.iqube.io/api/";
    public static final String API_BASE_URL = "http://10.1.75.96:8000/api/";
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

        @POST("feedbackresponse/")
        Call<JsonObject> finishFeedBack(@Body JsonObject answer);

        @GET
        Call<List<NotificationModel>> getNotification(@Url String url);

        @GET("guest/")
        Call<List<Guest>> getGuests();

        @GET
        Call<List<User>> getUser(@Url String url);

        @GET
        Call<List<Agenda>> getAgenda(@Url String url);

    }
}