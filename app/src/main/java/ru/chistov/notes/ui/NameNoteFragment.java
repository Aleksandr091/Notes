package ru.chistov.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;

import ru.chistov.notes.R;
import ru.chistov.notes.repository.LocalRepositoryImpl;


public class NameNoteFragment extends Fragment implements OnItemClickListener {

    public static final String CURRENT_NOTE = "CURRENT_NOTE";

    private Note currentNote;
    NotesAdapter notesAdapter;

    public static NameNoteFragment newInstance() {
        NameNoteFragment fragment = new NameNoteFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_notes, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_NOTE,currentNote);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecycler(view);
        if(savedInstanceState!=null){
            currentNote=savedInstanceState.getParcelable(CURRENT_NOTE);
        }else {
            currentNote = new Note(0);
        }
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            showLand();
        }
        initView(view);


    }
    public void initView(View view){
        view.findViewById(R.id.addNote).setOnClickListener(view1 -> Snackbar.make(view,"Добавлена заметка",Snackbar.LENGTH_LONG).show());

        /*for (int i=0;i<notes.length;i++){
            String nameNotes = notes[i];
            TextView tv = new TextView(getContext());
            tv.setTextSize(30f);
            tv.setText(nameNotes);
            ((LinearLayout)view).addView(tv);



            TextView tvd = new TextView(getContext());
            tvd.setTextSize(20f);
            setData(tvd); // дата меняетя при каждой отрисовки фрагмента,позже попробую сохранение в sharedPreferences
            ((LinearLayout)view).addView(tvd);

            final int finalI = i;

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentNote = new Note(finalI);
                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                        showLand();
                    }else {
                        showPort();
                    }
                }
            });


            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(requireContext(),view);
                    requireActivity().getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case (R.id.action_popup_open):{
                                    currentNote = new Note(finalI);
                                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                                        showLand();
                                    }else {
                                        showPort();
                                    }
                                    return true;
                                }

                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });

        }*/
    }
    public void initRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setHasFixedSize(true);
    }
    public void initAdapter(){
        notesAdapter = new NotesAdapter();
        LocalRepositoryImpl localRepository =new LocalRepositoryImpl(requireContext().getResources());

        notesAdapter.setData(localRepository.init());
        notesAdapter.setOnItemClickListener(this);
    }
    public String[] getData(){
        String[] data = getResources().getStringArray(R.array.notes);
        return data;
    }
    public void showLand(){
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.description_notes,descriptionFragment).commit();
    }
    public void showPort(){
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.name_notes,descriptionFragment).addToBackStack("").commit();
    }
    public void setData (TextView view){
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateString = sdf.format(date);
        view.setText(dateString);
    }

    @Override
    public void OnItemClick(int position) {
        currentNote = new Note(position);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showLand();
        } else {
            showPort();
        }
    }
    @Override
    public void OnItemLongClick(int position,View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(),view);
        requireActivity().getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case (R.id.action_popup_open):{
                        currentNote = new Note(position);
                        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
                            showLand();
                        }else {
                            showPort();
                        }
                        return true;
                    }

                }
                return false;
            }
        });
        popupMenu.show();

    }


}