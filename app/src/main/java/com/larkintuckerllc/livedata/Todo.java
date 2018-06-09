package com.larkintuckerllc.livedata;

public class Todo {

    private long mId;
    private String mName;
    private String mcode;

    public Todo(long id, String name, String code) {
        mId = id;
        mName = name;
        mcode = code;
    }

    public Todo(Todo todo) {
        mId = todo.getId();
        mName = todo.getName();
        mcode = todo.getcode();
    }

    public long getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getcode() {
        return mcode;
    }

    public boolean equals(Todo todo) {
        return todo.getId() == mId &&
            todo.getName() == mName &&
            todo.getcode() == mcode;
    }

}