package com.example.realestatear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    MaterialCardView projectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "activity", Toast.LENGTH_SHORT).show();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setViews();
        setClickListener();

    }

    private void setViews() {
        projectBtn = findViewById(R.id.create_project_btn);
    }

    public void setClickListener() {
        projectBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.create_project_btn:

                if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 106);
                    break;
                }

                File f = new File(getDataDir(), "/projects");
                if(!f.exists()) {
                    f.mkdir();
                    Toast.makeText(this, "file created", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(this, "file failed", Toast.LENGTH_SHORT).show();
                View view = getLayoutInflater().inflate(R.layout.dialog_name_project, null);
                view.findViewById(R.id.save_btn).setOnClickListener(vw -> {
                    EditText et = view.findViewById(R.id.name_et);
                    String name = et.getText().toString();
                    if(name.trim().length() <= 1) et.setError("enter valid project name");
                    else {
                        File file = new File(f, "/"+name);
                        if(file.exists()) et.setError("project already exists");
                        else {
                            file.mkdirs();
                            new File(file, "/indoor").mkdirs();
                            new File(file, "/outdoor").mkdirs();
                            new File(file, "/common").mkdirs();
                            Constants.cur_project_dir = file;
                            try {
                                createIndoorSpaceFiles();
                                startActivity(new Intent(MainActivity.this, ProgressActivity.class));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            startActivity(new Intent(MainActivity.this, ProgressActivity.class));
                        }
                    }
                });
                AlertDialog dialog =  dialog = new AlertDialog.Builder(this)
                        .setView(view).create();
                dialog.show();
                break;
            default:
                break;
        }
    }

    private void createIndoorSpaceFiles() throws IOException {

        LinkedList<File> file_list = new LinkedList<>();

        File f = Constants.getIndoorDir(this);

        File rooma = new File(f, "/RoomCategory A");
        File roomb = new File(f, "/RoomCategory B");
        File roomc = new File(f, "/RoomCategory C");

        if(!rooma.exists()) rooma.mkdirs();
        if(!roomb.exists()) roomb.mkdirs();
        if(!roomc.exists()) roomc.mkdirs();

        File room = new File(rooma, "/Rooom");
        File washroom = new File(rooma, "/WashRoom");
        File other = new File(rooma, "/Other");

        if(!room.exists()) room.mkdirs();
        if(!washroom.exists()) washroom.mkdirs();
        if(!other.exists()) other.mkdirs();
        file_list.add(room);
        file_list.add(washroom);
        file_list.add(other);

        room = new File(roomb, "/Rooom");
        washroom = new File(roomb, "/WashRoom");
        other = new File(roomb, "/Other");

        if(!room.exists()) room.mkdirs();
        if(!washroom.exists()) washroom.mkdirs();
        if(!other.exists()) other.mkdirs();
        file_list.add(room);
        file_list.add(washroom);
        file_list.add(other);

        room = new File(roomc, "/Rooom");
        washroom = new File(roomc, "/WashRoom");
        other = new File(roomc, "/Other");

        if(!room.exists()) room.mkdirs();
        if(!washroom.exists()) washroom.mkdirs();
        if(!other.exists()) other.mkdirs();
        file_list.add(room);
        file_list.add(washroom);
        file_list.add(other);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add_image_icon);
        File add = null;
        FileOutputStream out;

        for(File file: file_list) {
            add = new File(file, "/add.png");
            add.createNewFile();
            out = new FileOutputStream(add);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        }

    }

}