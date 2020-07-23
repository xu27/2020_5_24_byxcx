package com.example.a2020_5_24_byxcx.Base;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a2020_5_24_byxcx.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class Collection_Dialog extends BottomSheetDialog implements View.OnClickListener {
    private static final String TAG = "Collection_Dialog";

    private DialogOnClickListener listener;
    private TextView dismiss, delete;

    public Collection_Dialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.collection_dialog);
        this.getWindow().findViewById(R.id.design_bottom_sheet)
                .setBackgroundResource(android.R.color.transparent);
        dismiss = (TextView) findViewById(R.id.collection_dialog_dismiss);
        delete = (TextView) findViewById(R.id.collection_dialog_delete);
        delete.setOnClickListener(this);
        dismiss.setOnClickListener(this);
    }

    public Collection_Dialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected Collection_Dialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        if (listener != null) {
            if (view.getId() == R.id.collection_dialog_dismiss) {
                Log.d(TAG, "onClick: D");
                listener.dialog_dismiss();
            } else {
                Log.d(TAG, "onClick: O");
                listener.onclick(view);
            }
        }
    }

    public void setListener(DialogOnClickListener listener) {
        this.listener = listener;
    }
}
