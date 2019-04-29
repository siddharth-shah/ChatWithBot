package co.chatbot.view;

import co.chatbot.data.models.Message;

public interface ChatView {

    void addMessage(Message message);
    void showError(String errorMessage);

}
