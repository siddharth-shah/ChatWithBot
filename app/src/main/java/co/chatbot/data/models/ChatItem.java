package co.chatbot.data.models;

public class ChatItem {
    String message;
    String senderId;

    public ChatItem() {
        super();
    }

    public ChatItem(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }
}
