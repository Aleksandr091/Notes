package ru.chistov.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;


public class DescriptionChildFragment extends Fragment {

    public static final String ARG_NOTE = "Note";
    private Note note;

    public static DescriptionChildFragment newInstance(Note note) {
        DescriptionChildFragment fragment = new DescriptionChildFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ARG_NOTE,note);
        fragment.setArguments(bundle);
        return fragment;
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_notes_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        note = getArguments().getParcelable(ARG_NOTE);
        TextView ed = view.findViewById(R.id.ed_txt_dn);
        String[] description = getResources().getStringArray(R.array.description);
        ed.setText(description[note.getIndex()]);

        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });


    }
}