package co.chatbot.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ChatAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
