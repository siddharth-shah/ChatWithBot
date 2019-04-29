package co.chatbot.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.chatbot.R;
import co.chatbot.data.models.Message;

public class MainActivity extends AppCompatActivity implements ChatView, View.OnClickListener {

    RecyclerView chatList;
    ChatAdapter chatAdapter;
    EditText messageEdit;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        messageEdit = findViewById(R.id.message_edit);
        sendButton = findViewById(R.id.send_message_button);
        sendButton.setOnClickListener(this);
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
    public void addMessage(Message message) {
        if (chatAdapter != null) {
            chatAdapter.addMessage(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_message_button:
                break;
        }
    }
}
