package co.chatbot.data.network;

import java.util.HashMap;

import co.chatbot.data.models.BotResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ChatApi {

    @GET("/api/chat")
    Observable<BotResponse> sendMessage(@QueryMap HashMap<String, String> params);
}
