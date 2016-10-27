package com.elevenfifty.www.elevennote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * Created by bkeck on 3/9/15.
 *
 */
public class NotesArrayAdapter extends ArrayAdapter<Note> {
    private int resource;
    private ArrayList<Note> notes;
    private LayoutInflater inflater;
    private SimpleDateFormat formatter;

    public NotesArrayAdapter(Context context, int resource, ArrayList<Note> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.notes = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View notesRow = inflater.inflate(resource, parent, false);

        TextView noteTitle = (TextView)notesRow.findViewById(R.id.note_title);
        TextView noteText = (TextView)notesRow.findViewById(R.id.note_text);
        TextView noteDate = (TextView)notesRow.findViewById(R.id.note_date);
        TextView noteCategory = (TextView)notesRow.findViewById(R.id.catView);
        TextView dueDate = (TextView)notesRow.findViewById(R.id.dueView);
        TextView dueTime = (TextView)notesRow.findViewById(R.id.timeDue);


        Note note = notes.get(position);
        noteDate.setText(note.getDueDate());
        noteCategory.setText(note.getCategory());
        noteTitle.setText(note.getTitle());
        noteText.setText(note.getText());
        dueDate.setText(note.getDueDate());
        dueTime.setText(note.getDueTime());
        noteDate.setText(formatter.format(note.getDate()));

        return notesRow;
    }

    public void updateAdapter(ArrayList<Note> notes) {
        this.notes = notes;
        super.notifyDataSetChanged();
    }
}
