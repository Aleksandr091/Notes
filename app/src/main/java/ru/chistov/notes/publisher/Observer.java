package ru.chistov.notes.publisher;

import ru.chistov.notes.repository.NoteData;

public interface Observer {
    void receiveMessage(NoteData noteData);
}
