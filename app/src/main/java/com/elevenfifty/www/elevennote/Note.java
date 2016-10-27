package com.elevenfifty.www.elevennote;

import android.widget.Spinner;

import java.util.Date;
import java.util.UUID;

/**
 *
 * Created by bkeck on 3/9/15.
 *
 */
public class Note implements Comparable<Note> {
    private String title;
    private String text;
    private Date date;
    private String key;
    private String category;
    private String dueDate;
    private String dueTime;


    public Note(String title, String text, Date date, String category, String dueDate,String dueTime) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.key = UUID.randomUUID().toString();
        this.category = category;
        this.dueDate=dueDate;
        this.dueTime=dueTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int compareTo(Note another) {
//        return getDate().compareTo(another.getDate());
        return another.getDate().compareTo(getDate());
    }
}