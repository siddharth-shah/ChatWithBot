package co.chatbot.data.database;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import co.chatbot.data.database.models.Message;
import co.chatbot.data.database.models.MessageDao;

public class MessageProviderImpl implements MessageProvider {

    MessageDao messageDao;

    public MessageProviderImpl(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Override
    public List<Message> getAllMessagesWithBot(String chatbotID, String externalID) {

        WhereCondition c1 = MessageDao.Properties.ChatBotID.eq(chatbotID);
        WhereCondition c2 = MessageDao.Properties.ExternalID.eq(externalID);

        QueryBuilder<Message> qb = messageDao.queryBuilder();
        qb.where(c1, c2);

        return qb.orderDesc(MessageDao.Properties.CreatedAt).list();
    }

    @Override
    public boolean addMessage(Message message) {
        return messageDao.insert(message) != 0;
    }
}
