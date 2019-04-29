package co.chatbot.presenter;

public interface ChatPresenter {
    void sendMessage(String message, String senderId, String botId);
    void getAllMessages(String userId, String botId);

}
