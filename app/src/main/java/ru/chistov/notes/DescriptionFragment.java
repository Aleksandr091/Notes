package ru.chistov.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;


public class DescriptionFragment extends Fragment {

    public static final String ARG_NOTE = "Note";
    private Note note;

    public static DescriptionFragment newInstance(Note note) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(ARG_NOTE,note);
        fragment.setArguments(bundle);
        return fragment;
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        note = getArguments().getParcelable(ARG_NOTE);

        getChildFragmentManager().beginTransaction().replace(R.id.container_child,DescriptionChildFragment.newInstance(note)).addToBackStack("").commit();

        view.findViewById(R.id.btn_rem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Fragment> list= requireActivity().getSupportFragmentManager().getFragments();
                for (Fragment fr:list) {
                    if(fr instanceof DescriptionFragment){
                        requireActivity().getSupportFragmentManager().beginTransaction().remove(fr).commit();
                    }
                }
            }
        });


    }
}