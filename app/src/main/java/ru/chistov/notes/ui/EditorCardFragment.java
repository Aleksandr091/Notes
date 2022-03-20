package ru.chistov.notes.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;

import ru.chistov.notes.R;
import ru.chistov.notes.repository.NoteData;

public class EditorCardFragment extends Fragment {

    NoteData noteData;
    DatePicker datePicker;
    Calendar calendar;
    EditText edTitle;
    EditText edDescription;

    public static EditorCardFragment newInstance(NoteData noteData) {
        EditorCardFragment fragment = new EditorCardFragment();
        Bundle args = new Bundle();
        args.putParcelable("noteData", noteData);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor_cardview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable("noteData");
            initView(view);
            setContent();
            initDatePicker(noteData.getDate(), view);
            setOnDateChanged();
            save(view);
        }
    }

    public void initView(View view) {
        edTitle = view.findViewById(R.id.edTitle);
        edDescription = view.findViewById(R.id.edDescription);
        datePicker = (DatePicker) view.findViewById(R.id.date_picker);
    }

    public void setContent() {
        edTitle.setText(noteData.getTitle());
        edDescription.setText(noteData.getDescription());
    }

    public void setOnDateChanged() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(Calendar.YEAR, i);
                    calendar.set(Calendar.MARCH, i1);
                    calendar.set(Calendar.DAY_OF_MONTH, i2);
                }
            });
        } else {
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MARCH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        }
    }

    public void setUpdate(View view) {
        noteData.setTitle(((EditText) view.findViewById(R.id.edTitle)).getText().toString());
        noteData.setDescription(((EditText) view.findViewById(R.id.edDescription)).getText().toString());
        noteData.setDate(calendar.getTime());
    }

    public void save(View view) {
        view.findViewById(R.id.btnSave).setOnClickListener(view1 -> {
            setUpdate(view);
            ((MainActivity) requireActivity()).getPublisher().sendMessage(noteData);
            ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();
        });
    }

    private void initDatePicker(Date date, View view) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        ((DatePicker) view.findViewById(R.id.date_picker)).init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);

    }


}
