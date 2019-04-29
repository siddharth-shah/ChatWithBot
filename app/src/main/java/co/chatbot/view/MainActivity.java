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
        setupRecyclerView();
        addMessage(new Message("Hey What's up", "ajsnsjssjf"));
        addMessage(new Message("I am cool. What's up with you?", "63906"));
        addMessage(new Message("Cool cool", "ajsnsjssjf"));
        addMessage(new Message("Did you watch Endgame", "63906"));
        addMessage(new Message("Yup", "ajsnsjssjf"));
        addMessage(new Message("What about you", "ajsnsjssjf"));
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
}
