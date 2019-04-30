package co.chatbot.data.database;

import java.util.List;

import co.chatbot.data.database.models.Message;
import io.reactivex.Observable;

public interface MessageProvider {

    Observable<List<Message>> getAllMessagesWithBot(String chatbotID, String externalID);

    boolean addMessage(Message message);

    void updateMessage(Message message);

    Observable<List<Message>> getUndeliveredMessage(String chatbotID, String externalID);

}
