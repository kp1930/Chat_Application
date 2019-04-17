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
import com.example.chatapplication.Models.User;
import com.example.chatapplication.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<User> users;
    private boolean isChat;

    public UserAdapter(Context context, List<User> users, boolean isChat) {
        this.users = users;
        this.context = context;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, final int i) {

        final User user = users.get(i);
        viewHolder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")) viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        else Glide.with(context).load(user.getImageURL()).into(viewHolder.profile_image);

        if (isChat) {
            if (user.getStatus().equals("online")) {
                viewHolder.image_on.setVisibility(View.VISIBLE);
                viewHolder.image_off.setVisibility(View.GONE);
            } else {
                viewHolder.image_on.setVisibility(View.GONE);
                viewHolder.image_off.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.image_on.setVisibility(View.GONE);
            viewHolder.image_off.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return users.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        ImageView profile_image, image_on, image_off;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            image_on = itemView.findViewById(R.id.image_on);
            image_off = itemView.findViewById(R.id.image_off);
        }
    }
}
