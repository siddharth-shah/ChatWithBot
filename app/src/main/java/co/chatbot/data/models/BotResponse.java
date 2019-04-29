package co.chatbot.data.models;

public class BotResponse {

    int success;
    String errorMessage;
    BotMessage message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BotMessage getMessage() {
        return message;
    }

    public void setMessage(BotMessage message) {
        this.message = message;
    }
}

