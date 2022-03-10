package ru.chistov.notes.repository;

import java.util.List;

public interface NoteSource {
    int size();
    NoteData getCardData(int position);
    List<NoteData> getAllCardData();
}
