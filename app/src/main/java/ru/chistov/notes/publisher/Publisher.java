package ru.chistov.notes.publisher;

import java.util.ArrayList;
import java.util.List;

import ru.chistov.notes.repository.NoteData;

public class Publisher {
    public Publisher(){
        observers = new ArrayList<>();
    }
    private List<Observer> observers;

    public void subscribe(Observer observer) {
        observers.add(observer);
    }
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void sendMessage(NoteData noteData){
        for (Observer observer: observers) {
            observer.receiveMessage(noteData);
        }
    }
}
