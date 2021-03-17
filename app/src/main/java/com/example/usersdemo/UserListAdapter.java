package com.example.usersdemo;

import android.app.LauncherActivity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static android.content.ContentValues.TAG;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public ImageView avatar;

        @Override
        public void onClick(View v) {
            Log.d("ULA", "onClick " + getAdapterPosition());
            mOnClickListener.onListItemClicK(getAdapterPosition());
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            avatar = itemView.findViewById(R.id.avatar);
            nameTextView = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    interface ListItemClickListener {
        void onListItemClicK(int position);
    }

    private List<User> mUsers;

    public UserListAdapter(List<User> users, ListItemClickListener mOnClickListener) {
        mUsers = users;
        this.mOnClickListener = mOnClickListener;
    }

    private ListItemClickListener mOnClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.user_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUsers.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(user.getName());
        Picasso.get().load(user.getAvatar()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
