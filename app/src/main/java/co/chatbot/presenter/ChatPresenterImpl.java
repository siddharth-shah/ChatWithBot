package co.chatbot.presenter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import co.chatbot.AppConstants;
import co.chatbot.data.database.MessageProvider;
import co.chatbot.data.database.models.Message;
import co.chatbot.data.models.BotResponse;
import co.chatbot.data.models.ChatItem;
import co.chatbot.data.network.ApiClient;
import co.chatbot.data.network.ChatApi;
import co.chatbot.view.ChatView;
import io.reactivex.Observable;
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
        addMessageToChat(dbMessage);

        // add current messageText in the view
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(AppConstants.QUERY_PARAM_MESSAGE, messageText);
        queryMap.put(AppConstants.QUERY_PARAM_EXTERNAL_ID, userId);
        queryMap.put(AppConstants.QUERY_PARAM_CHAT_BOT_ID, botId);
        chatApi.sendMessage(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BotResponse, Message>() {
                    @Override
                    public Message apply(BotResponse botResponse) throws Exception {
                        Message message = new Message();
                        final String chatbotId = String.valueOf(botResponse.getMessage().getChatBotID());
                        message.setSenderID(chatbotId);
                        message.setMessageText(botResponse.getMessage().getMessage());
                        message.setExternalID(userId);
                        message.setChatBotID(chatbotId);
                        message.setCreatedAt(new Date().getTime());
                        return message;
                    }
                }).subscribe(new Observer<Message>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Message message) {
                messageProvider.addMessage(message);
                addMessageToChat(message);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void addMessageToChat(Message dbMessage) {
        ChatItem chatItem = getChatItemFromDbMessage(dbMessage);
        chatView.addMessage(chatItem);
    }

    private ChatItem getChatItemFromDbMessage(Message dbMessage) {
        return new ChatItem(dbMessage.getMessageText(), dbMessage.getSenderID());
    }

    @Override
    public void getAllMessages(String userId, String botId) {
        messageProvider.getAllMessagesWithBot(botId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(list -> Observable.fromIterable(list))
                .map(message -> getChatItemFromDbMessage(message))
                .toList()
                .toObservable()
                .subscribe(new Observer<List<ChatItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<ChatItem> chatItems) {
                        chatView.onInitialMessagesLoaded(chatItems);
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
