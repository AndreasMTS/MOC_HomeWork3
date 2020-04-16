package com.example.moc_homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    final private ListItemClickListener listClickListener;
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameView;
        TextView numberView;

        MyViewHolder(@NonNull View v) {
            super(v);

            nameView   = (TextView) v.findViewById(R.id.tv_name);
            numberView = (TextView) v.findViewById(R.id.tv_telephone_number);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listClickListener.onListItemClick(clickedPosition);
        }
    }

    private List<Contact> contacts;
    RvAdapter(List<Contact> contacts, ListItemClickListener listener) {
        this.contacts = contacts;
        listClickListener = listener;
    }

    @Override
    public RvAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.number_list_item, parent, false);

        return new MyViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(contacts.get(position).name);
        holder.numberView.setText(contacts.get(position).number);
    }

    @Override
    public int getItemCount() {
        if(contacts != null)
            return contacts.size();
        return 0;
    }

}
