package net.kiriti.peelabus;


public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://admin.peelabus.com/webservices/parentlogin.asmx/ParentLogin";

    public static final String CHANGE_PASSWORD_URL = "http://admin.peelabus.com/Webservices/ChangePassword.asmx/changepassword";
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String USER = "txtUser";
    public static final String CURRENT = "txtCurrPass";
    public static final String NEW_PASSWORD = "txtNewPass";
    public static final String CONFIRM_NEW = "txtConfirmNewPass";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "";

    public static final String RESPONSE = "";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String child1name = "name";
    public static final String child1class = "class";
    public static final String child1section = "0th";
    public static final String child1bus = "134";
    public static final String child1pic = "UploadedFiles/jamil1.jpg";


    public static final String child2name = "name";
    public static final String child2class = "class";
    public static final String child2section = "0th";
    public static final String child2bus = "123";
    public static final String child2pic = "UploadedFiles/jamil1.jpg";
}
