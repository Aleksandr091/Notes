package ru.chistov.notes.ui;

import android.view.View;

public interface OnItemClickListener {
    void OnItemClick(int position);
    void OnItemLongClick(int position, View view);
}
