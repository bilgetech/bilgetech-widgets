package com.bilgetech.widgets.ui.alertdialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgetech.widgets.R;

public abstract class CustomAlertDialogView extends LinearLayout {

    View rootView;
    DialogListener dialogListener;

    TextView tvDialogMessage;
    Button btnDone;

    abstract String getDialogMessage();

    public interface DialogListener {
        void performDoneClick();
    }

    public CustomAlertDialogView(Context context) {
        super(context);
        init(context);
    }

    public CustomAlertDialogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomAlertDialogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public DialogListener getDialogListener() {
        if (dialogListener == null) {
            dialogListener = new DialogListener() {
                @Override
                public void performDoneClick() {
                    Toast.makeText(getContext(), "You must implement DialogListener interface!", Toast.LENGTH_SHORT).show();
                }
            };
            throw new IllegalStateException("You must implement DialogListener interface!");
        }


        return dialogListener;
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.custom_dialog_content, this);
        tvDialogMessage = rootView.findViewById(R.id.tvDialogMessage);
        btnDone = rootView.findViewById(R.id.btnDone);
        tvDialogMessage.setText(getDialogMessage());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialogListener().performDoneClick();
            }
        });
    }
}