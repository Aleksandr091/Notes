package ru.chistov.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            NameNoteFragment nameNotesFragment = NameNoteFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.name_notes,nameNotesFragment).commit();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment backStackFragment = (Fragment)getSupportFragmentManager().findFragmentById(R.id.name_notes);
        if(backStackFragment!=null&&backStackFragment instanceof DescriptionFragment){
            onBackPressed();
        }
    }
}