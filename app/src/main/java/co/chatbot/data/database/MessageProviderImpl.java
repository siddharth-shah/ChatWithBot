package co.chatbot.data.database;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import co.chatbot.data.database.models.Message;
import co.chatbot.data.database.models.MessageDao;
import io.reactivex.Observable;

public class MessageProviderImpl implements MessageProvider {

    MessageDao messageDao;

    public MessageProviderImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public Observable<List<Message>> getAllMessagesWithBot(String chatbotID, String externalID) {

        WhereCondition c1 = MessageDao.Properties.ChatBotID.eq(chatbotID);
        WhereCondition c2 = MessageDao.Properties.ExternalID.eq(externalID);

        QueryBuilder<Message> qb = messageDao.queryBuilder();
        qb.where(c1, c2);

        return Observable.just(qb.orderAsc(MessageDao.Properties.CreatedAt).list());
    }

    @Override
    public boolean addMessage(Message message) {
        return messageDao.insert(message) != 0;
    }

    @Override
    public void updateMessage(Message message) {
        messageDao.update(message);
    }

    @Override
    public Observable<List<Message>> getUndeliveredMessage(String chatbotID, String externalID) {
        WhereCondition c1 = MessageDao.Properties.ChatBotID.eq(chatbotID);
        WhereCondition c2 = MessageDao.Properties.ExternalID.eq(externalID);
        WhereCondition c3 = MessageDao.Properties.SenderID.eq(externalID);
        WhereCondition c4 = MessageDao.Properties.Status.eq("failed");

        QueryBuilder<Message> qb = messageDao.queryBuilder();
        qb.where(c1, c2,c3,c4);

        return Observable.just(qb.orderAsc(MessageDao.Properties.CreatedAt).list());
    }
}
