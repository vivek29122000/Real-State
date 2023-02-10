package com.example.realestatear.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.realestatear.R;
import com.example.realestatear.interfaces.CustomInterface;

public class DeleteDialog extends Dialog {
    Context c;
    CardView yes_btn, no_btn;
    CustomInterface customInterface;

    public DeleteDialog(@NonNull Context context, CustomInterface customInterface) {
        super(context);
        this.c = context;
        this.customInterface = customInterface;
    }

    public DeleteDialog(@NonNull Context context, Context c) {
        super(context);
        this.c = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dialog_delete_layout);
        super.onCreate(savedInstanceState);

        yes_btn = findViewById(R.id.yes_btn);
        no_btn = findViewById(R.id.no_btn);

        yes_btn.setOnClickListener(v -> {
            customInterface.methon1();
        });

    }
}
