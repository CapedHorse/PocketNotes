package com.capedhorse.pocketnotes.towhatlist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capedhorse.pocketnotes.ListItemActivity;
import com.capedhorse.pocketnotes.R;
import com.capedhorse.pocketnotes.RealmConfig;
import com.capedhorse.pocketnotes.RealmHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListNameAdapter extends RecyclerView.Adapter<ListNameAdapter.MyViewHolder> {
    private List<ListModel> listModels;

    Context context;

    RealmHelper realmHelper = new RealmHelper(RealmConfig.newRealmInstance());

    public ListNameAdapter(Context context, List<ListModel> listModels) {
        this.context = context;
        this.listModels = listModels;

    }

    @NonNull
    @Override
    public ListNameAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ListNameAdapter.MyViewHolder holder, final int position) {
        final ListModel model = listModels.get(position);

        holder.name.setText(model.getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ListItemActivity.class);
                i.putExtra("id", String.valueOf(model.getId()));
                i.putExtra("category", model.getName());
                v.getContext().startActivity(i);
            }
        });

        holder.btnEditList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnEditList.setVisibility(View.GONE);
                holder.name.setVisibility(View.GONE);

                holder.btnConfirmEdit.setVisibility(View.VISIBLE);
                holder.inputName.setVisibility(View.VISIBLE);

                holder.inputName.setText(holder.name.getText());
            }
        });

        holder.btnConfirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListModel listModel = new ListModel();

                listModel.setId(model.getId());
                listModel.setName(holder.inputName.getText().toString());

                realmHelper.updateData(listModel);

                holder.inputName.setVisibility(View.GONE);
                holder.btnConfirmEdit.setVisibility(View.GONE);

                holder.btnEditList.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);

                notifyDataSetChanged();
                Toast.makeText(context, "Updated the list name", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder warning = new AlertDialog.Builder(context)
                        .setTitle("Are you sure wanna delete this list?")
                        .setMessage("Everything inside the list will be deleted")
                        .setPositiveButton("Yea", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String listName = model.getName();
                                realmHelper.deleteItemOnList(model.getId());
                                realmHelper.deleteList(model.getId());

                                notifyDataSetChanged();

                                dialog.cancel();

                                Toast.makeText(context, "Deleted " + listName + " :(", Toast.LENGTH_SHORT).show();
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
        return listModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btnDelete, btnEditList, btnConfirmEdit;
        EditText inputName;
        TextView name;

        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvListName);
            inputName = v.findViewById(R.id.inputListName);
            btnEditList = v.findViewById(R.id.btnEditList);
            btnDelete = v.findViewById(R.id.btnDeleteList);
            btnConfirmEdit = v.findViewById(R.id.btnConfirmEdit);

        }
    }
}
