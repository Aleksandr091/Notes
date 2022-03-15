package ru.chistov.notes.repository;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData implements Parcelable {
    private String title;
    private String description;
    private  int picture;
    private boolean completed;
    private Date date;


    public NoteData(String title, String description, int picture, boolean completed, Date date) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.completed = completed;
        this.date = date;
    }

    protected NoteData(Parcel in) {
        title = in.readString();
        description = in.readString();
        picture = in.readInt();
        completed = in.readByte() != 0;
        date = new Date(in.readLong());
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(picture);
        dest.writeByte((byte) (completed ? 1 : 0));
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }


    public boolean isCompleted() {
        return completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
