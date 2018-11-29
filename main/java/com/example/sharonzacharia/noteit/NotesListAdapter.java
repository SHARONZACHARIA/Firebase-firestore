package com.example.sharonzacharia.noteit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    public  static int NOTES_COUNT = 0;
   private ArrayList<Note> notes;
    private Context context;

    NotesListAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView notesheading;
        TextView notes_cont,timeStamp;
        LinearLayout notes_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notesheading = itemView.findViewById(R.id.textheadding);
            notes_cont = itemView.findViewById(R.id.textcontext);
            timeStamp = itemView.findViewById(R.id.date);
            notes_card = itemView.findViewById(R.id.mainview);


        }
    }

    @NonNull
    @Override
    public NotesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.noteslist, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.ViewHolder  viewHolder, final int i) {

         Note n = notes.get(i);
        viewHolder.notes_cont.setText(n.getNote_content());
        viewHolder.notesheading.setText(n.getNote_name());
        viewHolder.timeStamp.setText(n.TimeStamp);
       viewHolder.notes_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note n = notes.get(i);
                Intent i = new Intent(context,ReadActivity.class);
                i.putExtra("object",n);
                context.startActivity(i);
            }
        });




    }

    @Override
    public int getItemCount() {
        NOTES_COUNT = notes.size();
        return notes.size();

    }
}
