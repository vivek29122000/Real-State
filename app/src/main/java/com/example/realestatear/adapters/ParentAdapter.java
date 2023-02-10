package com.example.realestatear.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatear.Constants;
import com.example.realestatear.R;
import com.example.realestatear.models.ParentRvModel;

import java.io.File;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.Holder> {

    List<ParentRvModel> list;
    Context c;
    View rename_view;
    View delete_view;
    AlertDialog rename_dialog;
    AlertDialog delete_dialog;

    public ParentAdapter(Context c, List<ParentRvModel> list) {
        this.c = c;
        this.list = list;
        rename_view = LayoutInflater.from(c).inflate(R.layout.dialog_rename_layout, null);
        delete_view = LayoutInflater.from(c).inflate(R.layout.dialog_delete_layout, null);
        rename_dialog = new AlertDialog.Builder(c).setView(rename_view).create();
        delete_dialog = new AlertDialog.Builder(c).setView(delete_view).create();
    }

    @NonNull
    @Override
    public ParentAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_rv_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.Holder holder, int position) {

        ChildAdapter adapter = new ChildAdapter(list.get(position).list, c);
        LinearLayoutManager manager = new LinearLayoutManager(c, LinearLayoutManager.HORIZONTAL, false);
        holder.photos_rv.setAdapter(adapter);

        holder.space_name_tv.setText(list.get(position).name.getName());
        holder.delete_btn.setOnClickListener(view -> {
            delete_dialog.show();

            delete_view.findViewById(R.id.yes_btn).setOnClickListener(v -> {
                File file = list.get(holder.getAdapterPosition()).name;
                for(File tmp: file.listFiles()) {
                    for(File tmp2: tmp.listFiles()) {
                        tmp2.delete();
                    }
                    tmp.delete();
                }
                if(!file.delete()) {
                    Toast.makeText(c, "some error occured", Toast.LENGTH_SHORT).show();
                }
                else {
                    list.remove(holder.getAdapterPosition());
                    notifyItemRemoved(position);
                }
                delete_dialog.dismiss();
            });
            delete_view.findViewById(R.id.no_btn).setOnClickListener(v -> {
                delete_dialog.dismiss();
            });

        });

        holder.space_name_tv.setOnClickListener(view -> {
            rename_dialog.show();
            rename_view.findViewById(R.id.save_btn).setOnClickListener(v -> {
                EditText et = rename_view.findViewById(R.id.name_et);
                String txt = et.getText().toString();
                if(txt.trim().length() <= 1) et.setError("enter valid name");
                else {
                    rename_dialog.dismiss();
                    rename_dialog.cancel();
                    File file = new File(Constants.cur_project_dir, "/indoor/"+holder.space_name_tv.getText().toString());
                    if(file.renameTo(new File(Constants.cur_project_dir, "/indoor/"+txt))) {
                        holder.space_name_tv.setText(txt);
//                        ParentRvModel model = new ParentRvModel(list.get(position).list, file);
//                        list.remove(holder.getAdapterPosition());
////                        notifyItemRemoved(position);
//                        list.add(model);
//                        notifyDataSetChanged();
////                        notifyItemChanged(position);
////
                    }
                    else {
                        Toast.makeText(c, "some error occured", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        TextView space_name_tv;
        ImageView delete_btn;
        RecyclerView photos_rv;
//            LinearLayout image_holder;

        public Holder(@NonNull View itemView) {
            super(itemView);
            space_name_tv = itemView.findViewById(R.id.space_name_tv);
            delete_btn = itemView.findViewById(R.id.delete_btn);
//                image_holder = itemView.findViewById(R.id.image_holder);
            photos_rv = itemView.findViewById(R.id.photos_rv);
        }

        public void rename(TextView v) {
            EditText et = rename_view.findViewById(R.id.name_et);
            String txt = et.getText().toString();
            if(txt.trim().length() <= 1) et.setError("enter valid name");
            else v.setText(txt);
        }
    }

}