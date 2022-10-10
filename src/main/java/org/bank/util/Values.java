package org.bank.util;

public class Values {
    public static final double CARD_TO_CARD = 600;
    public static final double INVENTORY = 120;
    public static final int EXPIRATION_CARD_TIME = 60; // month of validation card
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d#@!_.]{8,}$";
    public static final String PHONE_NUMBER_REGEX = "^([\\+]*[\\d]{10,14})$";
    public static final String NATIONAL_CODE_REGEX = "^([\\d]{10})$";
    public static final String CARD_NUMBER_REGEX = "^([\\d]{16})$";
    public static final String CVV2_REGEX = "^([\\d]{3,4})$";
    public static final String ACCOUNT_NUMBER_REGEX = "^([\\d]{13})$";
    public static final int NUMBER_OF_INCORRECT_PASSWORD = 3;
    //password must be at least one uppercase, one lowercase and one number.
}
