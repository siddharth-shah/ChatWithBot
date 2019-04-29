package co.chatbot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.chatbot.R;
import co.chatbot.data.models.Message;
import co.chatbot.presenter.ChatPresenterImpl;

public class MainActivity extends AppCompatActivity implements ChatView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChatPresenterImpl presenter =
                new ChatPresenterImpl(this);
        presenter.sendMessage("Hey! What's up?");
    }

    @Override
    public void addMessage(Message message) {

    }
}
