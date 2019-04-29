package co.chatbot.data.database;

import java.util.List;

import co.chatbot.data.database.models.Message;

public interface MessageProvider {

    List<Message> getAllMessagesWithBot(String chatbotID);

    void addMessage(Message message);

}
