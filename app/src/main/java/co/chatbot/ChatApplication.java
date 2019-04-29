package co.chatbot;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import co.chatbot.data.database.DatabaseOpenHelper;
import co.chatbot.data.database.models.DaoMaster;
import co.chatbot.data.database.models.DaoSession;

public class ChatApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.OpenHelper openHelper
                = new DatabaseOpenHelper(this, "chatbot-db");
        Database database = openHelper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
