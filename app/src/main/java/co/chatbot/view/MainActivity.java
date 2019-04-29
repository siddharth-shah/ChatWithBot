package co.chatbot.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.chatbot.R;

public class MainActivity extends AppCompatActivity implements ChatView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
