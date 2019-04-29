package co.chatbot.data.database.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "message")
public class Message {

    @Property(nameInDb = "chatbotID")
    String chatBotID;
    @Property(nameInDb = "senderID")
    String senderID;
    @Property(nameInDb = "externalID")
    String externalID;
    @Property(nameInDb = "messageText")
    String messageText;
    @Property(nameInDb = "createdAt")
    long createdAt;
    @Property(nameInDb = "status")
    String status;
    @Generated(hash = 145624102)
    public Message(String chatBotID, String senderID, String externalID,
            String messageText, long createdAt, String status) {
        this.chatBotID = chatBotID;
        this.senderID = senderID;
        this.externalID = externalID;
        this.messageText = messageText;
        this.createdAt = createdAt;
        this.status = status;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public String getChatBotID() {
        return this.chatBotID;
    }
    public void setChatBotID(String chatBotID) {
        this.chatBotID = chatBotID;
    }
    public String getSenderID() {
        return this.senderID;
    }
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
    public String getExternalID() {
        return this.externalID;
    }
    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }
    public String getMessageText() {
        return this.messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    public long getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }


}
