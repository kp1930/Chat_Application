package com.example.chatapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.chatapplication.Activities.MessageActivity;
import com.example.chatapplication.Models.Chat;
import com.example.chatapplication.Models.User;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<Chat> chats;
    private String imageurl;
    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats, String imageurl) {
        this.chats = chats;
        this.context = context;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, final int i) {
        Chat chat= chats.get(i);
        viewHolder.show_message.setText(chat.getMessage());

        if (imageurl.equals("default")) viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        else Glide.with(context).load(imageurl).into(viewHolder.profile_image);

        if (i == chats.size()-1) {
            if (chat.isIsseen()) viewHolder.txt_seen.setText("Seen");
            else viewHolder.txt_seen.setText("Delivered");
        } else viewHolder.txt_seen.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() { return chats.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView show_message, txt_seen;
        ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            profile_image = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equals(firebaseUser.getUid())) return MSG_TYPE_RIGHT;
        else return MSG_TYPE_LEFT;
    }
}
