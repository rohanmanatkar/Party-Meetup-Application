package com.example.puneet.jashn;

import android.util.Log;

import java.util.Date;

/**
 * Class responsible to hold the name and the message to the user
 * to send to firebase
 */
public class ChatData {

    private String userName;
    private String eventName;
    private String mMessage;
    private String timeNow;
    private String chatId;

    public ChatData(String eventName, String userName, String mMessage, String time) {
        //this.chatId = chatID;
        this.eventName = eventName;
        this.userName = userName;
        this.mMessage = mMessage;
        this.timeNow = time;
    }

    public ChatData() {
        // empty constructor
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String uname) {
        userName = uname;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String ename) {
        eventName = ename;
    }

    public String getMessage() { return mMessage; }

    public void setMessage(String message) { mMessage = message;  }

    public String getTime() { return String.valueOf(new Date().getTime());  }

    public void setTime(String timenow) { timeNow = timenow;  }

}
