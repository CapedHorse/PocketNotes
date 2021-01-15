package com.capedhorse.pocketnotes.towhatlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.capedhorse.pocketnotes.R;
import com.capedhorse.pocketnotes.RealmHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListNameAdapter extends RecyclerView.Adapter<ListNameAdapter.MyViewHolder> {
    private List<ListModel> listModels;
    Context context;
    RealmHelper helper;

    public ListNameAdapter(Context context, List<ListModel> listModels) {
        this.context = context;
        this.listModels = listModels;

    }

    @NonNull
    @Override
    public ListNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListNameAdapter.MyViewHolder holder, int position) {
        final ListModel model = listModels.get(position);
        holder.name.setText(model.getName());
        holder.inputName.setText(model.getName());
        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.name.setVisibility(View.GONE);
                holder.inputName.setVisibility(View.VISIBLE);
                return false;
            }
        });
        holder.inputName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    holder.inputName.setVisibility(View.GONE);
                    holder.name.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ListItemActivity.class);
                i.putExtra("category", model.getName().toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnDelete;
        EditText inputName;
        TextView name;
        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvListName);
            inputName = v.findViewById(R.id.inputListName);
            btnDelete = v.findViewById(R.id.btnDeleteList);
        }
    }
}
