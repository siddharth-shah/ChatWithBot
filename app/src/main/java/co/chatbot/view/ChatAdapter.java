package co.chatbot.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case MY_MESSAGE:
                return new MyMessageViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.chat_item_right_side, viewGroup, false));
            case OTHERS_MESSAGE:
                return new OtherMessageViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.chat_item_left_side, viewGroup, false));
            default:
                throw new IllegalArgumentException("No such view type exists");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Message message = messages.get(i);
        if (viewHolder instanceof MyMessageViewHolder) {
            ((MyMessageViewHolder) viewHolder).myMessage.setText(message.getMessage());
        } else if (viewHolder instanceof OtherMessageViewHolder) {
            ((OtherMessageViewHolder) viewHolder).otherMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (messages == null)
            return 0;
        return messages.size();
    }


    public void addMessage(Message message) {
        if (messages == null)
            messages = new ArrayList<>();
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
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
        TextView myMessage;

        public MyMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            myMessage = itemView.findViewById(R.id.my_message);
        }
    }


    class OtherMessageViewHolder extends RecyclerView.ViewHolder {

        TextView otherMessage;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            otherMessage = itemView.findViewById(R.id.bot_message);
        }


    }
}