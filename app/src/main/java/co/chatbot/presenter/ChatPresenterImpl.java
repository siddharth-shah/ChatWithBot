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
    public void onSendButtonClicked(final String messageText, final String userId, final String botId) {
        String status = "pending";
        Message dbMessage =
                getMessageForDB(messageText, botId, userId,
                        userId, new Date().getTime(), status);
        messageProvider.addMessage(dbMessage);
        addMessageToChat(dbMessage);
        sendMessageToServer(dbMessage);

    }

    private void sendMessageToServer(Message dbMessage) {
        // add current messageText in the view
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put(AppConstants.QUERY_PARAM_MESSAGE, dbMessage.getMessageText());
        queryMap.put(AppConstants.QUERY_PARAM_EXTERNAL_ID, dbMessage.getExternalID());
        queryMap.put(AppConstants.QUERY_PARAM_CHAT_BOT_ID, dbMessage.getChatBotID());
        chatApi.sendMessage(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<BotResponse, Message>() {
                    @Override
                    public Message apply(BotResponse botResponse) throws Exception {
                        final String chatBotID = String.
                                valueOf(botResponse.getMessage().getChatBotID());
                        Message message =
                                getMessageForDB(botResponse.getMessage().getMessage(), chatBotID, dbMessage.getExternalID(), chatBotID, new Date().getTime(), "success");
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

    private Message getMessageForDB(String messageText, String chatbotID, String externalID
            , String senderID, long timestamp, String status) {
        Message dbMessage = new Message();
        dbMessage.setLocalId(timestamp);
        dbMessage.setChatBotID(chatbotID);
        dbMessage.setExternalID(externalID);
        dbMessage.setMessageText(messageText);
        dbMessage.setCreatedAt(timestamp);
        dbMessage.setSenderID(senderID);
        dbMessage.setStatus(status);

        return dbMessage;

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
