package ru.chistov.notes.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int index;
    private String DescriptionNotes;
    private String nameNotes;
    private String creationDate;

    protected Note(Parcel in) {
        index = in.readInt();
        DescriptionNotes = in.readString();
        nameNotes = in.readString();
        creationDate = in.readString();
    }

    public Note(int i) {
        index = i;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(DescriptionNotes);
        dest.writeString(nameNotes);
        dest.writeString(creationDate);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDescriptionNotes() {
        return DescriptionNotes;
    }

    public void setDescriptionNotes(String descriptionNotes) {
        DescriptionNotes = descriptionNotes;
    }

    public String getNameNotes() {
        return nameNotes;
    }

    public void setNameNotes(String nameNotes) {
        this.nameNotes = nameNotes;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}




