package ru.chistov.notes;

import android.view.View;

public interface OnItemClickListener {
    void OnItemClick(int position);
    void OnItemLongClick(int position, View view);
}
