package ru.chistov.notes.repository;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.chistov.notes.R;

public class LocalSharedPreferencesRepositoryImpl implements NoteSource {

    private List<NoteData> dataSource;
    SharedPreferences sharedPreferences;
    static final String KEY_CELL_1 = "cell_1";
    public static final String KEY_SP_2 = "key_SP_2";

    public LocalSharedPreferencesRepositoryImpl(SharedPreferences sharedPreferences){
        dataSource = new ArrayList<NoteData>();
        this.sharedPreferences = sharedPreferences;

    }
    public LocalSharedPreferencesRepositoryImpl init(){
        String savedNote = sharedPreferences.getString(KEY_CELL_1,null);
        if(savedNote != null) {
            Type type = new TypeToken<ArrayList<NoteData>>(){}.getType();
            dataSource = (new GsonBuilder().create().fromJson(savedNote, type));
        }
        return this;
    }

    @Override
    public void addNoteData(NoteData noteData) {
        dataSource.add(noteData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1,new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }

    @Override
    public void deleteNoteData(int position) {
        dataSource.remove(position);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1,new GsonBuilder().create().toJson(dataSource));
        editor.apply();

    }

    @Override
    public void updateNoteData(int position, NoteData newNoteData) {
        dataSource.set(position,newNoteData);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1,new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public NoteData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public List<NoteData> getAllCardData() {
        return dataSource;
    }

    @Override
    public void clearNoteData() {
        dataSource.clear();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CELL_1,new GsonBuilder().create().toJson(dataSource));
        editor.apply();
    }
}
