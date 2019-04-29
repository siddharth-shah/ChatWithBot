package co.chatbot.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import co.chatbot.R;
import co.chatbot.data.models.Message;

public class MainActivity extends AppCompatActivity implements ChatView {

    RecyclerView chatList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatAdapter = new ChatAdapter(this);
        setupRecyclerView();
        chatList.setAdapter(chatAdapter);
    }

    private void setupRecyclerView() {
        chatList = findViewById(R.id.chat_list);
        //stack like list for chatItems
        chatList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
    }

    @Override
    public void addMessage(Message message) {

    }
}
