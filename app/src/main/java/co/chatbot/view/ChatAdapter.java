package co.chatbot.view;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import co.chatbot.AppConstants;
import co.chatbot.R;
import co.chatbot.data.models.ChatItem;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int MY_MESSAGE = 1;
    private static final int OTHERS_MESSAGE = 2;
    private Context context;
    List<ChatItem> chatItems;

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
        final ChatItem chatItem = chatItems.get(i);
        if (viewHolder instanceof MyMessageViewHolder) {
            ((MyMessageViewHolder) viewHolder).myMessage.setText(chatItem.getMessage());
        } else if (viewHolder instanceof OtherMessageViewHolder) {
            ((OtherMessageViewHolder) viewHolder).otherMessage.setText(chatItem.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (chatItems == null)
            return 0;
        return chatItems.size();
    }


    public void addMessage(ChatItem chatItem) {
        if (chatItems == null)
            chatItems = new ArrayList<>();
        chatItems.add(chatItem);
        notifyItemInserted(chatItems.size() - 1);
    }

    public void updateMessage(ChatItem chatItem) {
        if (chatItems == null)
            return;
        int count = -1;
        final Iterator<ChatItem> iterator = chatItems.iterator();
        while (iterator.hasNext()) {
            count++;
            final ChatItem item = iterator.next();
            if (item.getTimestamp() == chatItem.getTimestamp()) {
                chatItems.set(count, chatItem);
                break;
            }
        }
        if (count > -1 && count < chatItems.size()) {
            notifyItemChanged(count);
        }

    }

    @Override
    public int getItemViewType(int position) {
        String currentUser = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(AppConstants.QUERY_PARAM_EXTERNAL_ID, "");
        if (chatItems.get(position).getSenderId().equalsIgnoreCase(currentUser)) {
            return MY_MESSAGE;
        } else {
            return OTHERS_MESSAGE;
        }
    }

    public void setMessages(List<ChatItem> messages) {
        if (this.chatItems == null)
            chatItems = new ArrayList<>();
        chatItems.clear();
        chatItems.addAll(messages);
        notifyDataSetChanged();
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
