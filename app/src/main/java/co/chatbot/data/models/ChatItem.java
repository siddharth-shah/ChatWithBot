package co.chatbot.data.models;

public class ChatItem {
    String message;
    String senderId;
    long timestamp;
    String status;

    public ChatItem() {
        super();
    }

    public ChatItem(String message, String senderId, long timestamp, String status) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }
}
