package com.elevenfifty.www.elevennote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.elevenfifty.www.elevennote.R.id.dateView;


public class NoteDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int index;
    private EditText noteTitle;
    private EditText noteText;
    private TextView dueDate;
    private TextView dueTime;
    private TextView noteCat;
    private TextView noteCom;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Spinner spinner;
    Spinner spinnerCom;
    public TextView catpicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        spinner=(Spinner) findViewById(R.id.catDrop);
       ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.category_arrays,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinnerCom=(Spinner) findViewById(R.id.completeSpinner);
        ArrayAdapter adapter1=ArrayAdapter.createFromResource(this,R.array.complete_arrays,android.R.layout.simple_spinner_item);
        spinnerCom.setAdapter(adapter1);
        spinnerCom.setOnItemSelectedListener(this);


        noteTitle = (EditText) findViewById(R.id.note_title);
        noteText = (EditText) findViewById(R.id.note_text);
        dueDate = (TextView) findViewById(R.id.dateView);
        dueTime = (TextView) findViewById(R.id.timeView);
        noteCat = (TextView) findViewById(R.id.catpic);
        noteCom = (TextView) findViewById(R.id.status);


        Intent intent = getIntent();
        index = intent.getIntExtra(NotesActivity.NOTE_INDEX, -1);

        noteTitle.setText(intent.getStringExtra(NotesActivity.NOTE_TITLE));
        noteText.setText(intent.getStringExtra(NotesActivity.NOTE_TEXT));
        dueDate.setText(intent.getStringExtra(NotesActivity.NOTE_DUEDATE));
        dueTime.setText(intent.getStringExtra(NotesActivity.NOTE_DUETIME));
        noteCat.setText(intent.getStringExtra(NotesActivity.NOTE_CATEGORY));
        noteCom.setText(intent.getStringExtra(NotesActivity.NOTE_COMPLETE));

        Button saveButton = (Button) findViewById(R.id.save_button);
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
                intent.putExtra(NotesActivity.NOTE_COMPLETE, noteCom.getText().toString());
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

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        // When an Image is picked
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       TextView myText= (TextView) view;
     catpicked =(TextView)findViewById(R.id.catpic);
        catpicked.setText("Category: "+myText.getText());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}



