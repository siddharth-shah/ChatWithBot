package co.chatbot.presenter;

public interface ChatPresenter {
    void onSendButtonClicked(String message, String senderId, String botId, boolean isNetworkAvailable);
    void getAllMessages(String userId, String botId);
    void retrySendingFailedMessages(String chatbotID,String senderId);

}
