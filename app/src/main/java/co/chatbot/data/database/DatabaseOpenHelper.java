package co.chatbot.data.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import co.chatbot.data.database.models.DaoMaster;

public class DatabaseOpenHelper extends DaoMaster.OpenHelper {

    public DatabaseOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
