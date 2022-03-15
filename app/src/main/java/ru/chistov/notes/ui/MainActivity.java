package ru.chistov.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ru.chistov.notes.R;
import ru.chistov.notes.publisher.Publisher;
import ru.chistov.notes.repository.NoteData;

public class MainActivity extends AppCompatActivity  {

    private Publisher publisher;
    Fragment backStackFragment;
    NoteData noteData;
    Navigation navigation;

    public Navigation getNavigation() {
        return navigation;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());
        if(savedInstanceState==null){
            navigation.replaceFragment(NameNoteFragment.newInstance(),false);

        }
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            setSupportActionBar(findViewById(R.id.toolbar_land));
        }else {
            setSupportActionBar(findViewById(R.id.toolbar));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_about):{
                navigation.replaceFragment((new AboutFragment()),true);

                return true;
            }
            case (R.id.action_exit):{
                new AlertDialog.Builder(this)
                        .setTitle("Закрыть приложение")
                        .setPositiveButton("да", (dialogInterface, i) -> finish())
                        .setNegativeButton("нет",(dialogInterface, i) -> Toast.makeText(this, "приложение работает", Toast.LENGTH_SHORT).show())
                        .show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backStackFragment = (Fragment)getSupportFragmentManager().findFragmentById(R.id.name_notes);
        if(backStackFragment!=null&&backStackFragment instanceof DescriptionFragment){
            getSupportFragmentManager().popBackStack();
        }
    }


}