package ru.chistov.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.chistov.notes.R;
import ru.chistov.notes.repository.NoteData;
import ru.chistov.notes.repository.NoteSource;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    Fragment fragment;
    private NoteSource noteSource;
    private int menuPosition;

    public int getMenuPosition() {
        return menuPosition;
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    this.onItemClickListener=onItemClickListener;
    }
    public NotesAdapter(NoteSource noteSource){
        this.noteSource = noteSource;
    }
    public NotesAdapter(){
    }
    public NotesAdapter(Fragment fragment){
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder(layoutInflater.inflate(R.layout.fragment_name_notes_cardview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(noteSource.getCardData(position));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewCreationDate;
        private ImageView imageView;
        private CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            textViewCreationDate = (TextView) itemView.findViewById(R.id.creationDate);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            checkBox = (CheckBox) itemView.findViewById(R.id.completed);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();
                    return false;
                }
            });
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getLayoutPosition();

                    return false;
                }
            });
            fragment.registerForContextMenu(itemView);
            /*textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
            textViewTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemLongClick(getLayoutPosition(),view);
                    }
                    return false;
                }
            });*/
        }
        public void bindContentWithLayout(NoteData content){
            textViewTitle.setText(content.getTitle());
            textViewDescription.setText(content.getDescription());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = content.getDate();
            String dateString = sdf.format(date);
            textViewCreationDate.setText(dateString);
            imageView.setImageResource(content.getPicture());
            checkBox.setChecked(content.isCompleted());
        }
    }
}
