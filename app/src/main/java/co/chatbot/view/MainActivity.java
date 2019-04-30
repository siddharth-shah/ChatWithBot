package co.chatbot.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import co.chatbot.AppConstants;
import co.chatbot.ChatApplication;
import co.chatbot.R;
import co.chatbot.data.database.MessageProviderImpl;
import co.chatbot.data.models.ChatItem;
import co.chatbot.presenter.ChatPresenter;
import co.chatbot.presenter.ChatPresenterImpl;
import co.chatbot.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements ChatView, View.OnClickListener {

    RecyclerView chatList;
    ChatAdapter chatAdapter;
    EditText messageEdit;
    Button sendButton;
    ChatPresenter presenter;
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectivityReceiver = new ConnectivityReceiver();
        presenter = new ChatPresenterImpl(this,
                new MessageProviderImpl(((ChatApplication) getApplication())
                        .getDaoSession().getMessageDao()));
        setupRecyclerView();
        messageEdit = findViewById(R.id.message_edit);
        sendButton = findViewById(R.id.send_message_button);
        sendButton.setOnClickListener(this);
        setUserInPreferences("siddharthshah");
        presenter.getAllMessages("siddharthshah", AppConstants.CHAT_BOT_ID);
        presenter.retrySendingFailedMessages(AppConstants.CHAT_BOT_ID, "siddharthshah");
    }

    private void setUserInPreferences(String userId) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString(AppConstants.QUERY_PARAM_EXTERNAL_ID, userId);
        editor.apply();
    }

    private void setupRecyclerView() {
        //stack like list for chatItems
        chatList = findViewById(R.id.chat_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatList.setLayoutManager(layoutManager);
        chatList.setHasFixedSize(true);
        chatAdapter = new ChatAdapter(this);
        chatList.setAdapter(chatAdapter);


    }

    @Override
    public void addMessage(ChatItem chatItem) {
        if (chatAdapter != null) {
            chatAdapter.addMessage(chatItem);
            chatList.scrollToPosition(chatAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void updateMessage(ChatItem chatItem) {
        chatAdapter.updateMessage(chatItem);

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitialMessagesLoaded(List<ChatItem> messages) {
        chatAdapter.setMessages(messages);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_button:
                final boolean isConnected = NetworkUtils.getInstance(this).isConnected();
                if (!isConnected) {
                    showError("Please check your internet");
                }
                String message = messageEdit.getEditableText().toString();
                final String currentUser = PreferenceManager.
                        getDefaultSharedPreferences(this).getString(AppConstants
                        .QUERY_PARAM_EXTERNAL_ID, "");
                if (presenter != null) {
                    presenter.onSendButtonClicked(message, currentUser, AppConstants.CHAT_BOT_ID, isConnected);
                    messageEdit.getText().clear();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(connectivityReceiver, new IntentFilter(CONNECTIVITY_ACTION));
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectivityReceiver);
    }

    class ConnectivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CONNECTIVITY_ACTION.equalsIgnoreCase(action)) {
                if (NetworkUtils.getInstance(MainActivity.this).isConnected()) {
                    Log.d("Network check", "connected");
                    presenter.retrySendingFailedMessages(AppConstants.CHAT_BOT_ID,
                            PreferenceManager.getDefaultSharedPreferences(MainActivity.this)
                                    .getString(AppConstants.QUERY_PARAM_EXTERNAL_ID, ""));
                }
            }
        }
    }


}
