package ru.chistov.notes.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.chistov.notes.R;

public class Navigation {

    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager){
        this.fragmentManager = fragmentManager;
    }

    public void replaceFragment(Fragment fragment,boolean useBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.name_notes, fragment);
        if(useBackStack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment,boolean useBackStack){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.name_notes, fragment);
        if(useBackStack){
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction.commit();
    }

}
