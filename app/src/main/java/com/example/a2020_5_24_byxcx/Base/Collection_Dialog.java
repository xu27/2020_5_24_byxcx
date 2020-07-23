package com.example.a2020_5_24_byxcx.Base;

import android.content.Context;
import android.widget.Button;

import com.example.a2020_5_24_byxcx.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class Collection_Dialog extends BottomSheetDialog {

    public Collection_Dialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.collection_dialog);
        this.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
    }

    public Collection_Dialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected Collection_Dialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
