package com.elevenfifty.www.elevennote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static com.elevenfifty.www.elevennote.R.id.dateView;


public class NoteDetailActivity extends AppCompatActivity {
    private int index;
    private EditText noteTitle;
    private EditText noteText;
    private TextView dueDate;
    private TextView dueTime;
    private TextView noteCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        noteTitle = (EditText)findViewById(R.id.note_title);
        noteText = (EditText)findViewById(R.id.note_text);
        dueDate= (TextView)findViewById(R.id.dateView);
        dueTime = (TextView) findViewById(R.id.timeView);
        noteCat= (TextView) findViewById(R.id.catView);

        Intent intent = getIntent();
        index = intent.getIntExtra(NotesActivity.NOTE_INDEX, -1);

        noteTitle.setText(intent.getStringExtra(NotesActivity.NOTE_TITLE));
        noteText.setText(intent.getStringExtra(NotesActivity.NOTE_TEXT));
        dueDate.setText(intent.getStringExtra(NotesActivity.NOTE_DUEDATE));
        dueTime.setText(intent.getStringExtra(NotesActivity.NOTE_DUETIME));
        //noteCat.setText(intent.getStringExtra(NotesActivity.NOTE_CATEGORY));

        Button saveButton = (Button)findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra(NotesActivity.NOTE_INDEX, index);
                intent.putExtra(NotesActivity.NOTE_TITLE, noteTitle.getText().toString());
                intent.putExtra(NotesActivity.NOTE_TEXT, noteText.getText().toString());
                intent.putExtra(NotesActivity.NOTE_DUEDATE, dueDate.getText().toString());
                intent.putExtra(NotesActivity.NOTE_DUETIME, dueTime.getText().toString());
                intent.putExtra(NotesActivity.NOTE_CATEGORY, noteCat.getText().toString());
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_detail, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");

    }


}
