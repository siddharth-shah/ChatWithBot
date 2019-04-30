package co.chatbot.presenter;

public interface ChatPresenter {
    void onSendButtonClicked(String message, String senderId, String botId);
    void getAllMessages(String userId, String botId);

}
