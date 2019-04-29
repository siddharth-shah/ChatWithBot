package co.chatbot.view;

import co.chatbot.data.models.ChatItem;

public interface ChatView {

    void addMessage(ChatItem chatItem);
    void showError(String errorMessage);

}
