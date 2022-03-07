package ru.chistov.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private String[] data;

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
    this.onItemClickListener=onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        layoutInflater.inflate(R.layout.fragment_name_notes_recycler_item,parent,false);
        return new MyViewHolder(layoutInflater.inflate(R.layout.fragment_name_notes_recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindContentWithLayout(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(getLayoutPosition());
                    }
                }
            });
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemLongClick(getLayoutPosition(),view);
                    }
                    return false;
                }
            });
        }
        public void bindContentWithLayout(String content){
            textView.setText(content);
        }
    }
}
