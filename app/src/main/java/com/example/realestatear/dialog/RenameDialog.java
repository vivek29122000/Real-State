package com.example.realestatear.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.realestatear.R;
import com.example.realestatear.interfaces.CustomInterface;

public class RenameDialog extends Dialog {

    Context c;
    EditText name_et;
    CardView save_btn;
    CustomInterface customInterface;

    public RenameDialog(@NonNull Context context, CustomInterface customInterface) {
        super(context);
        this.c = context;
        this.customInterface = customInterface;
    }

    public RenameDialog(@NonNull Context context) {
        super(context);
        this.c = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_rename_layout);

        View view = getLayoutInflater().inflate(R.layout.dialog_rename_layout, null);
        setContentView(view);

        name_et = findViewById(R.id.name_et);
        save_btn = findViewById(R.id.save_btn);

        save_btn.setOnClickListener(v -> {
            customInterface.methon2(name_et.getText().toString());
        });
    }
}
