package com.example.realestatear.fragment;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.realestatear.Constants;
import com.example.realestatear.R;
import com.example.realestatear.models.ChildRvModel;
import com.example.realestatear.models.ParentRvModel;
import com.example.realestatear.adapters.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IndoorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndoorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView parent_rv, child_rv;
    TextView add_btn;
    LinkedList<ParentRvModel> parent_list;
    ParentAdapter adapter;
    AlertDialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IndoorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndoorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IndoorFragment newInstance(String param1, String param2) {
        IndoorFragment fragment = new IndoorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_indoor, container, false);

        loadFiles();

        add_btn = v.findViewById(R.id.add_btn);
        parent_rv = v.findViewById(R.id.parent_rv);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        adapter = new ParentAdapter(getContext(), parent_list);
        parent_rv.setAdapter(adapter);

        add_btn.setOnClickListener(view -> {
            createSpaceFile();
        });

        return v;
    }

    public void loadFiles() {
        File file = Constants.getIndoorDir(getContext());
        File arr[] = file.listFiles();
        parent_list = new LinkedList<>();

        for(File f: arr) {
            LinkedList<ChildRvModel> lst = new LinkedList<>();
            for(File f1: f.listFiles()) {
                ChildRvModel mod = new ChildRvModel(f1.listFiles(), f1);
                lst.add(mod);
            }
            parent_list.add(new ParentRvModel(lst, f));
        }

    }

    public void createSpaceFile() {
        View view = getLayoutInflater().inflate(R.layout.dialog_name_project, null);
        view.findViewById(R.id.save_btn).setOnClickListener(vw -> {
            EditText et = view.findViewById(R.id.name_et);
            String name = et.getText().toString();
            if(name.trim().length() <= 1) et.setError("enter valid space name");
            else {
                File file = new File(Constants.cur_project_dir, "/indoor/"+name.trim());
                if(file.exists()) et.setError("space already exists");
                else {
                    file.mkdirs();

                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add_image_icon);
                    File add_folder = new File(file, "/add");
                    add_folder.mkdirs();
                    File add = null;
                    FileOutputStream out;
                    add = new File(add_folder, "/add.png");
                    try {
                        add.createNewFile();
                        out = new FileOutputStream(add);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                        LinkedList<ChildRvModel> tmp_model = new LinkedList<>();
                        tmp_model.add(new ChildRvModel(add_folder.listFiles(), add_folder));
                        parent_list.add(new ParentRvModel(tmp_model, file));

                        Collections.sort(parent_list, new Comparator<ParentRvModel>() {
                            @Override
                            public int compare(ParentRvModel o1, ParentRvModel o2) {
                                return o1.name.getName().compareTo(o2.name.getName());
                            }
                        });

                        adapter.notifyItemInserted(parent_list.size()-1);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Log.i("size -------------", parent_list.size()+"");
                }
            }
            if(dialog.isShowing()) {
                dialog.dismiss();
            }
        });
        dialog =  dialog = new AlertDialog.Builder(getContext())
                .setView(view).create();
        dialog.show();
    }

}