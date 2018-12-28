package com.example.puneet.jashn;

import android.widget.CheckBox;

public class TodoData {

    private String todoId;
    private String tUserName;
    private String tEventName;
    private String tTodoItem;
    private boolean isChecked;

    public TodoData(String eventName, String userName, String todoItem, boolean isCheck) {
        this.tEventName = eventName;
        this.tUserName = userName;
        this.tTodoItem = todoItem;
        this.isChecked = isCheck;
    }

    public TodoData() {
        // empty constructor
    }

    public String getUserName() { return tUserName; }

    public void setUserName(String username) { tUserName = username; }

    public void setEventName(String eventName) { tEventName = eventName; }

    public String getEventName() { return tEventName; }

    public void setIsChecked(boolean isCheck) { isChecked = isCheck; }

    public boolean getIsChecked() { return isChecked; }


    public String getTodoItem() { return tTodoItem; }

    public void setTodoItem(String topic) {
        tTodoItem = topic;
    }

}
