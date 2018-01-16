package com.bilgetech.widgets.helper.validator;

import android.app.Activity;
import android.widget.EditText;

import com.bilgetech.widgets.multilanguageview.MultiLanguageHelper;
import com.bilgetech.widgets.ui.alertdialog.CustomAlertDialogBuilder;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.List;

public class Validator {
    private ValidatorConfig config;
    private Activity _activity;

    public Validator(Activity activity) {
        this.config = new ValidatorConfig();
        this._activity = activity;
    }

    public Boolean validate(List<ValidationBundle> bundles) {
        boolean valid = true;
        for (ValidationBundle bundle : bundles) {
            EditText editText = bundle.getEditText();
            InputType inputType = bundle.getInputType();
            switch (inputType) {

                case PASSWORD:
                    if (bundle.getEditTextSecondary() == null) {
                        if (bundle.isEditTextEmpty()) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Enter your password"));
                            valid = false;
                        } else if (!(editText.getText().length() >= config.getMinPasswordLength())) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Password must be at least 8 characters"));
                            valid = false;
                        }
                        break;
                    } else {
                        if (bundle.isEditTextEmpty()) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Enter your password"));
                            valid = false;
                        } else if (!(editText.getText().length() >= config.getMinPasswordLength())) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Password must be at least 8 characters"));
                            valid = false;
                        } else if (!editText.getText().toString().matches(bundle.getEditTextSecondary().getText().toString())) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Passwords do not match"));
                            valid = false;
                        }
                        break;
                    }

                case PHONE:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Please enter a phone number"));
                        valid = false;
                    } else {

                        boolean b = false;
                        try {
                            String countryCode = PhoneNumberUtil.getInstance().getRegionCodeForCountryCode(90);
                            Phonenumber.PhoneNumber phonenumber = PhoneNumberUtil.getInstance().parse(bundle.getEditText().getText().toString()
                                    .replaceAll("[^0-9]", ""), countryCode.toUpperCase());
                            b = PhoneNumberUtil.getInstance().isValidNumber(phonenumber);
                        } catch (NumberParseException e) {
                            e.printStackTrace();
                        }

                        if (!b) {
                            showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Please enter a valid phone number"));
                            valid = false;
                        }
                    }
                    break;

                case NAME:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Enter name"));
                        valid = false;
                    } else if (!(editText.getText().length() >= config.getMinNameSurnameLength())) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Name must be at least 2 characters"));
                        valid = false;
                    }
                    break;

                case SURNAME:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Enter surname"));
                        valid = false;
                    } else if (!(editText.getText().length() >= config.getMinNameSurnameLength())) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Surname must be at least 2 characters"));
                        valid = false;
                    }
                    break;

                case MAIL:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Enter your e-mail address"));
                        valid = false;
                    } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText()).matches()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "The e-mail address you entered is invalid. Please enter a valid email address"));
                        valid = false;
                    }
                    break;
                case NAME_SURNAME:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Global", "Enter your name and surname"));
                        valid = false;
                    }
                    break;

                case MESSAGE:
                    if (bundle.isEditTextEmpty()) {
                        showAlertDialog(MultiLanguageHelper.getInstance().translate("Contact Form", "The message field is required."));
                        valid = false;
                    }

                default:
                    break;

            }
            if (!valid) break;
        }
        return valid;
    }

    private void showAlertDialog(String message) {
        new CustomAlertDialogBuilder(_activity, message);
    }
}