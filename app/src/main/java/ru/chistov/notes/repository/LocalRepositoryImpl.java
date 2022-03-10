package ru.chistov.notes.repository;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import ru.chistov.notes.R;

public class LocalRepositoryImpl implements NoteSource {

    private List<NoteData> dataSource;
    Resources resources;

    public LocalRepositoryImpl(Resources resources){
        dataSource = new ArrayList<NoteData>();
        this.resources = resources;

    }
    public LocalRepositoryImpl init(){
        String[]titles = resources.getStringArray(R.array.notes);
        String[]description = resources.getStringArray(R.array.description);
        String[]creationDate = resources.getStringArray(R.array.Data);
        TypedArray pictures = resources.obtainTypedArray(R.array.pictures);

        for (int i=0;i<titles.length;i++){
            dataSource.add(new NoteData(titles[i],description[i],pictures.getResourceId(i,0),false,creationDate[i]));
        }
        return this;
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
}
