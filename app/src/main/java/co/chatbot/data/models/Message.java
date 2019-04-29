package co.chatbot.data.models;

public class Message {
    String message;
    String senderId;

    public Message() {
        super();
    }

    public Message(String message, String senderId) {
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
