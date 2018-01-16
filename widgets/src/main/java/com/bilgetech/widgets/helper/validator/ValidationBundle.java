package com.bilgetech.widgets.helper.validator;

import android.widget.EditText;

public class ValidationBundle {
    private EditText editText;
    private EditText editText_secondary;
    private InputType inputType;

    public ValidationBundle() {
    }

    public ValidationBundle(EditText editText, InputType inputType) {
        this.editText = editText;
        this.inputType = inputType;
    }

    public ValidationBundle(EditText password1, EditText password2, InputType inputType) {
        this.editText = password1;
        this.editText_secondary = password2;
        this.inputType = inputType;
    }

    public EditText getEditText() {
        return editText;
    }

    public EditText getEditTextSecondary() {
        return editText_secondary;
    }

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public boolean isEditTextEmpty() {
        return editText.getText() == null || editText.getText().length() == 0;
    }

    public boolean isEditTextEmptyForNumbers() {
        return editText.getText() == null || editText.getText().toString().replaceAll("[^0-9]", "").length() == 0;
    }

    public boolean isMonthValid() {
        String text = editText.getText().toString();
        text = text.replaceAll("[^0-9]", "");
        if (text.length() > 0 && text.length() < 3) {
            int number = Integer.parseInt(text);
            return number > 0 && number < 13;
        } else {
            return false;
        }
    }

    public boolean isYearValid() {
        String text = editText.getText().toString();
        text = text.replaceAll("[^0-9]", "");
        if (text.length() == 4) {
            int number = Integer.parseInt(text);
            return number >= 2015 && number < 2100;
        } else if (text.length() == 2) {
            int number = Integer.parseInt(text);
            return number >= 15 && number < 50;
        } else {
            return false;
        }
    }
}