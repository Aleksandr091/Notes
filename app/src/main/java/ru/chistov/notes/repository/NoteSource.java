package ru.chistov.notes.repository;

import java.util.List;

public interface NoteSource {
    int size();
    NoteData getCardData(int position);
    List<NoteData> getAllCardData();

    void clearNoteData();
    void addNoteData(NoteData noteData);
    void deleteNoteData(int position);
    void updateNoteData(int position,NoteData newNoteData);
}
