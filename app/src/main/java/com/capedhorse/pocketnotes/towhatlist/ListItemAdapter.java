package com.capedhorse.pocketnotes.towhatlist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.capedhorse.pocketnotes.R;
import com.capedhorse.pocketnotes.RealmConfig;
import com.capedhorse.pocketnotes.RealmHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.MyViewHolder> {

    private List<ListItemModel> itemModels;

    Context context;

    RealmHelper realmHelper = new RealmHelper(RealmConfig.newRealmInstance());

    public ListItemAdapter(Context context, List<ListItemModel> itemModels) {
        this.context = context;
        this.itemModels = itemModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ListItemModel model = itemModels.get(position);

        holder.name.setText(model.getName());

        holder.doneCheck.setChecked(model.getDone());

        holder.doneCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListItemModel itemModel = new ListItemModel();

                itemModel.setId(model.getId());
                itemModel.setName(model.getName());
                itemModel.setCategoryId(model.getCategoryId());
                itemModel.setDone(isChecked);

                realmHelper.updateData(itemModel);

                notifyDataSetChanged();

                Toast.makeText(context, model.getName() + " is done :))", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder warning = new AlertDialog.Builder(context)
                        .setTitle("Are you sure wanna delete this item?")
                        .setMessage("You can still add them again tho.")
                        .setPositiveButton("Yea", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String itemName = model.getName();
                                realmHelper.deleteItem(model.getId());

                                notifyDataSetChanged();

                                dialog.cancel();

                                Toast.makeText(context, itemName + " is deleted.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Nah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                warning.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox doneCheck;
        TextView name;
        Button btnDelete;
        public MyViewHolder(View v) {
            super(v);
            doneCheck = v.findViewById(R.id.doneCheck);
            name = v.findViewById(R.id.tvItemName);
            btnDelete = v.findViewById(R.id.btnDeleteItem);

        }
    }
}
