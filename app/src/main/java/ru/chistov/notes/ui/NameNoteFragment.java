package ru.chistov.notes.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RadioButton;

import java.util.Calendar;

import ru.chistov.notes.R;
import ru.chistov.notes.publisher.Observer;
import ru.chistov.notes.repository.LocalRepositoryImpl;
import ru.chistov.notes.repository.LocalSharedPreferencesRepositoryImpl;
import ru.chistov.notes.repository.NoteData;
import ru.chistov.notes.repository.NoteSource;
import ru.chistov.notes.repository.PictureIndexConverter;
import ru.chistov.notes.repository.RemoteFireStoreRepositoryImpl;
import ru.chistov.notes.repository.RemoteFireStoreResponse;


public class NameNoteFragment extends Fragment implements OnItemClickListener {

    public static final String CURRENT_NOTE = "CURRENT_NOTE";

    private Note currentNote;
    NotesAdapter notesAdapter;
    NoteSource data;
    RecyclerView recyclerView;
    Navigation navigation;

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
        outState.putParcelable(CURRENT_NOTE, currentNote);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSource();
        initRecycler(view);
        setHasOptionsMenu(true);
        initRadioGroup(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.addNoteData(noteData);
                        notesAdapter.notifyItemInserted(data.size() - 1);
                        recyclerView.smoothScrollToPosition(data.size() - 1);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(EditorCardFragment.newInstance(new NoteData("Заголовок", "Описание" ,PictureIndexConverter.getIndexByPicture(PictureIndexConverter.randomPictureIndex()) , false, Calendar.getInstance().getTime())), true);

                return true;
            case R.id.action_clear:
                data.clearNoteData();
                notesAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int menuPosition = notesAdapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                Observer observer = new Observer() {
                    @Override
                    public void receiveMessage(NoteData noteData) {
                        ((MainActivity) requireActivity()).getPublisher().unsubscribe(this);
                        data.updateNoteData(menuPosition, noteData);
                        notesAdapter.notifyItemChanged(menuPosition);
                    }
                };
                ((MainActivity) requireActivity()).getPublisher().subscribe(observer);
                ((MainActivity) requireActivity()).getNavigation().addFragment(EditorCardFragment.newInstance(data.getCardData(menuPosition)), true);
                return true;
            case R.id.action_delete:
                data.deleteNoteData(menuPosition);
                notesAdapter.notifyItemRemoved(menuPosition);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    public void initRecycler(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void initAdapter() {
        if(notesAdapter == null)
        notesAdapter = new NotesAdapter(this);
        notesAdapter.setData(data);
        notesAdapter.setOnItemClickListener(this);
    }
    void setupSource(){
        switch (getCurrentSource()){
            case SOURCE_ARRAY:
                data = new LocalRepositoryImpl(requireContext().getResources()).init();
                initAdapter();
                break;
            case SOURCE_SP:
                data = new LocalSharedPreferencesRepositoryImpl(requireContext().getSharedPreferences(LocalSharedPreferencesRepositoryImpl.KEY_SP_2,Context.MODE_PRIVATE)).init();
                initAdapter();
                break;
            case SOURCE_GF:
                data = new RemoteFireStoreRepositoryImpl().init(new RemoteFireStoreResponse() {
                    @Override
                    public void initialized(NoteSource noteSource) {
                        initAdapter();
                    }
                });
                initAdapter();
                break;
        }
    }

    private void initRadioGroup(View view) {
        view.findViewById(R.id.sourceArrays).setOnClickListener(listener);
        view.findViewById(R.id.sourceSP).setOnClickListener(listener);
        view.findViewById(R.id.sourceGF).setOnClickListener(listener);
        switch (getCurrentSource()){
            case SOURCE_ARRAY:
                ((RadioButton) view.findViewById(R.id.sourceArrays)).setChecked(true);
                break;
            case SOURCE_SP:
                ((RadioButton) view.findViewById(R.id.sourceSP)).setChecked(true);
                break;
            case SOURCE_GF:
                ((RadioButton) view.findViewById(R.id.sourceGF)).setChecked(true);
                break;
        }
    }

    static final int SOURCE_ARRAY = 1;
    static final int SOURCE_SP = 2;
    static final int SOURCE_GF = 3;

    static String KEY_SP_S1 = "KEY1";
    static String KEY_SP_S1_CELL1 = "CELL1";

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sourceArrays:
                    setCurrentSource(SOURCE_ARRAY);
                    break;
                case R.id.sourceSP:
                    setCurrentSource(SOURCE_SP);
                    break;
                case R.id.sourceGF:
                    setCurrentSource(SOURCE_GF);
                    break;
            }
            setupSource();
        }
    };
    void setCurrentSource(int currentSource ){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SP_S1_CELL1,currentSource);
        editor.apply();
    }
    int getCurrentSource(){
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(KEY_SP_S1, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SP_S1_CELL1,SOURCE_ARRAY);
    }


    public String[] getData() {
        String[] data = getResources().getStringArray(R.array.notes);
        return data;
    }

    public void showLand() {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.description_notes, descriptionFragment).commit();
    }

    public void showPort() {
        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance(currentNote);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.name_notes, descriptionFragment).addToBackStack("").commit();
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
    public void OnItemLongClick(int position, View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        requireActivity().getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.action_popup_open): {
                        navigation = new Navigation(requireActivity().getSupportFragmentManager());
                        navigation.replaceFragment(EditorCardFragment.newInstance(data.getCardData(position)), true);
                        return true;
                    }
                }
                return false;
            }
        });
        popupMenu.show();

    }


}