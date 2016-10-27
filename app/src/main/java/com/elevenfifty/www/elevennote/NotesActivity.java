package com.elevenfifty.www.elevennote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class NotesActivity extends AppCompatActivity {
    public static final String NOTE_INDEX = "com.elevenfifty.www.elevennote.NOTE_INDEX";
    public static final String NOTE_TITLE = "com.elevenfifty.www.elevennote.NOTE_TITLE";
    public static final String NOTE_TEXT = "com.elevenfifty.www.elevennote.NOTE_TEXT";
    public static final String NOTE_CATEGORY = "com.elevenfifty.www.elevennote.NOTE_CATEGORY";
    public static final String NOTE_DUEDATE = "com.elevenfifty.www.elevennote.NOTE_DUEDATE";
    public static final String NOTE_DUETIME = "com.elevenfifty.www.elevennote.NOTE_DUETIME";


    private String filename="todo";

    private ListView notesList;
//    private final String[] notes = new String[] {"note 1", "note 2", "note 3"};
    private ArrayList<Note> notesArray;
    private NotesArrayAdapter notesArrayAdapter;

    private SharedPreferences notesPrefs;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        notesList = (ListView)findViewById(R.id.listView);
//        notesList.setAdapter(new ArrayAdapter<>(this, R.layout.notes_textview_list_item, notes));

        notesPrefs = getPreferences(Context.MODE_PRIVATE);
        gson = new Gson();

        setupNotes();

        Collections.sort(notesArray);
        notesArrayAdapter = new NotesArrayAdapter(this, R.layout.notes_list_item, notesArray);
        notesList.setAdapter(notesArrayAdapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = notesArray.get(position);
                Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);

                intent.putExtra(NOTE_INDEX, position);
                intent.putExtra(NOTE_TITLE, note.getTitle());
                intent.putExtra(NOTE_TEXT, note.getText());
                intent.putExtra(NOTE_CATEGORY, note.getCategory());
                intent.putExtra(NOTE_DUEDATE, note.getDueDate());
                intent.putExtra(NOTE_DUETIME, note.getDueTime());

//                startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NotesActivity.this);
                alertBuilder.setTitle(getString(R.string.delete));
                alertBuilder.setMessage(getString(R.string.delete_confirm_text));
                alertBuilder.setNegativeButton(getString(R.string.cancel), null);
                alertBuilder.setPositiveButton(getString(R.string.delete),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note = notesArray.get(position);
                        deleteFile(note.getKey());
                        notesArray.remove(position);
                        notesArrayAdapter.updateAdapter(notesArray);
                    }
                });
                alertBuilder.create().show();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            int index = data.getIntExtra(NOTE_INDEX, -1);
            Note note = new Note(data.getStringExtra(NOTE_TITLE),
                                data.getStringExtra(NOTE_TEXT),
                                new Date(),
                                data.getStringExtra(NOTE_CATEGORY),
                    data.getStringExtra(NOTE_DUEDATE),
            data.getStringExtra(NOTE_DUETIME));
            if (index < 0 || index > notesArray.size() - 1) {
                notesArray.add(note);
            } else {
                Note oldNote = notesArray.get(index);
                note.setKey(oldNote.getKey());
                notesArray.set(index, note);
            }
            writeFile(note);
            Collections.sort(notesArray);
            notesArrayAdapter.updateAdapter(notesArray);
        }
    }

    private void setupNotes() {
        notesArray = new ArrayList<>();
        if (notesPrefs.getBoolean("firstRun", true)) {
            SharedPreferences.Editor editor = notesPrefs.edit();
            editor.putBoolean("firstRun", false);
            editor.apply();

            Note note1 = new Note("Task 1", "This is a task", new Date(), "Shopping", "Due 9/12/17","5:30am");
            notesArray.add(note1);
            for (Note note : notesArray) {
                writeFile(note);
            }
        } else {
            File[] filesDir = this.getFilesDir().listFiles();
            for (File file : filesDir) {
                if (file.getName().equals(filename)) {
                    FileInputStream inputStream = null;
                    String title = file.getName();
                    Date date = new Date(file.lastModified());
                    String text = "";
                    String dueDate = "";
                    String category = "";
                    try {
                        inputStream = openFileInput(title);
                        byte[] input = new byte[inputStream.available()];
                        while (inputStream.read(input) != -1) {
                        }
                        text += new String(input);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Note note = gson.fromJson(text, Note.class);
                        note.setDate(date);
                        notesArray.add(note);
                        try {
                            inputStream.close();
                        } catch (Exception ignored) {

                        }
                    }
                }
            }
        }
    }

    private void writeFile(Note note) {
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(gson.toJson(note).getBytes());
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (Exception ignored) {}
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);

            intent.putExtra(NOTE_TITLE, "");
            intent.putExtra(NOTE_TEXT, "");
            intent.putExtra(NOTE_CATEGORY, "");
            intent.putExtra(NOTE_DUEDATE, "");

            startActivityForResult(intent, 1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
