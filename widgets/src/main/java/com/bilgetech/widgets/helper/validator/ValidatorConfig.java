package com.bilgetech.widgets.helper.validator;

class ValidatorConfig {
    private int minPasswordLength;
    private int minNameSurnameLength;

    public ValidatorConfig() {
        minPasswordLength = 8;
        minNameSurnameLength = 2;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public int getMinNameSurnameLength() {
        return minNameSurnameLength;
    }
}
