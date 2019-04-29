package co.chatbot.view;

import java.util.List;

import co.chatbot.data.database.models.Message;
import co.chatbot.data.models.ChatItem;

public interface ChatView {

    void addMessage(ChatItem chatItem);
    void showError(String errorMessage);

    void onInitialMessagesLoaded(List<ChatItem> messages);
}
