package co.chatbot.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.chatbot.R;
import co.chatbot.data.models.Message;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int MY_MESSAGE = 1;
    private static final int OTHERS_MESSAGE = 2;
    private Context context;
    List<Message> messages;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (getItemViewType(i)) {
            case MY_MESSAGE:
                return new MyMessageViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.chat_item_right_side, viewGroup, false));
            case OTHERS_MESSAGE:
                return new MyMessageViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.chat_item_left_side, viewGroup, false));
            default:
                throw new IllegalArgumentException("No such view type exists");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if (messages == null)
            return 0;
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId().equalsIgnoreCase("ajsnsjssjf")) {
            return MY_MESSAGE;
        } else {
            return OTHERS_MESSAGE;
        }
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder {

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    class OtherMessageViewHolder extends RecyclerView.ViewHolder {

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
