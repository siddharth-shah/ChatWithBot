package co.chatbot.presenter;

import java.util.HashMap;

import co.chatbot.AppConstants;
import co.chatbot.data.models.BotResponse;
import co.chatbot.data.models.Message;
import co.chatbot.data.network.ApiClient;
import co.chatbot.data.network.ChatApi;
import co.chatbot.view.ChatView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ChatPresenterImpl implements ChatPresenter {
    ChatView chatView;
    ChatApi chatApi;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        this.chatApi = ApiClient.getClient().create(ChatApi.class);
    }

    @Override
    public void sendMessage(String message, String senderId, String botId) {
        // add current message in the view
        chatView.addMessage(new Message(message, senderId));
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(AppConstants.QUERY_PARAM_MESSAGE, message);
        queryMap.put(AppConstants.QUERY_PARAM_EXTERNAL_ID, senderId);
        queryMap.put(AppConstants.QUERY_PARAM_CHAT_BOT_ID, botId);
        chatApi.sendMessage(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BotResponse, Message>() {
                    @Override
                    public Message apply(BotResponse botResponse) throws Exception {

                        return new Message(botResponse.getMessage().getMessage(),
                                String.valueOf(botResponse.getMessage().getChatBotID()));
                    }
                }).subscribe(new Observer<Message>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                chatView.addMessage(message);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
