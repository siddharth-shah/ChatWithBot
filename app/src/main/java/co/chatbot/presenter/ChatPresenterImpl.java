package co.chatbot.presenter;

import java.util.Date;
import java.util.HashMap;

import co.chatbot.AppConstants;
import co.chatbot.data.database.MessageProvider;
import co.chatbot.data.database.models.Message;
import co.chatbot.data.models.BotResponse;
import co.chatbot.data.models.ChatItem;
import co.chatbot.data.network.ApiClient;
import co.chatbot.data.network.ChatApi;
import co.chatbot.view.ChatView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ChatPresenterImpl implements ChatPresenter {
    private MessageProvider messageProvider;
    ChatView chatView;
    ChatApi chatApi;

    public ChatPresenterImpl(ChatView chatView) {
        this.chatView = chatView;
        this.chatApi = ApiClient.getClient().create(ChatApi.class);
    }

    public ChatPresenterImpl(ChatView chatView, MessageProvider messageProvider) {
        this.chatView = chatView;
        this.chatApi = ApiClient.getClient().create(ChatApi.class);
        this.messageProvider = messageProvider;
    }

    @Override
    public void sendMessage(final String messageText, final String userId, final String botId) {
        Message dbMessage = new Message();
        dbMessage.setChatBotID(botId);
        dbMessage.setExternalID(userId);
        dbMessage.setMessageText(messageText);
        dbMessage.setCreatedAt(new Date().getTime());
        dbMessage.setSenderID(userId);
        messageProvider.addMessage(dbMessage);
        // add current messageText in the view
        chatView.addMessage(new ChatItem(messageText, userId));
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(AppConstants.QUERY_PARAM_MESSAGE, messageText);
        queryMap.put(AppConstants.QUERY_PARAM_EXTERNAL_ID, userId);
        queryMap.put(AppConstants.QUERY_PARAM_CHAT_BOT_ID, botId);
        chatApi.sendMessage(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BotResponse, ChatItem>() {
                    @Override
                    public ChatItem apply(BotResponse botResponse) throws Exception {
                        return new ChatItem(botResponse.getMessage().getMessage(),
                                String.valueOf(botResponse.getMessage().getChatBotID()));
                    }
                }).subscribe(new Observer<ChatItem>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ChatItem chatItem) {
                Message dbMessage = new Message();

                dbMessage.setChatBotID(botId);
                dbMessage.setExternalID(userId);
                dbMessage.setMessageText(chatItem.getMessage());
                dbMessage.setCreatedAt(new Date().getTime());
                dbMessage.setSenderID(botId);
                messageProvider.addMessage(dbMessage);
                chatView.addMessage(chatItem);
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
