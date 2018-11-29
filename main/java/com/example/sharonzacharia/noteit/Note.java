package com.example.sharonzacharia.noteit;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    int noteid;
    String title;
    String body;
    String TimeStamp;
    String Author;
    String no_of_chars;
    String Img_url;
    String Img_count;


    public Note(int noteid, String note_name, String note_content, String timeStamp, String author) {
        this.noteid = noteid;
        this.title = note_name;
        this.body = note_content;
        TimeStamp = timeStamp;
        Author = author;
    }

    public Note() {
    }


    protected Note(Parcel in) {
        noteid = in.readInt();
        title = in.readString();
        body = in.readString();
        TimeStamp = in.readString();
        Author = in.readString();
        no_of_chars = in.readString();
        Img_url = in.readString();
        Img_count = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getNote_name() {
        return title;
    }

    public void setNote_name(String note_name) {
        this.title = note_name;
    }

    public String getNote_content() {
        return body;
    }

    public void setNote_content(String note_content) {
        this.body = note_content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(noteid);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(TimeStamp);
        parcel.writeString(Author);
        parcel.writeString(no_of_chars);
        parcel.writeString(Img_url);
        parcel.writeString(Img_count);
    }
}
