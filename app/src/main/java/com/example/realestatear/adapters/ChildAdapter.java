package com.example.realestatear.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realestatear.R;
import com.example.realestatear.models.ChildRvModel;

import java.io.File;
import java.util.LinkedList;

class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.Holder> {

    LinkedList<ChildRvModel> list;
    Context c;

    public ChildAdapter(LinkedList<ChildRvModel> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @NonNull
    @Override
    public ChildAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rv_layout, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.Holder holder, int position) {

        File f = list.get(position).parent;
        getImage(f, holder.image, holder.text);

    }

    public void getImage(File f, ImageView im, TextView tv) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file;

                File ar[] = f.listFiles();

                if(ar.length <= 1) file = ar[0];
                else {
                    if(ar[0].getName().equals("add.png")) file = ar[1];
                    else file = ar[0];
                }

                ((Activity)c).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        im.setImageURI(Uri.fromFile(file));
                        tv.setText(f.getName());
                    }
                });
            }
        }).start();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView text;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            text = itemView.findViewById(R.id.name_view);
        }
    }
}