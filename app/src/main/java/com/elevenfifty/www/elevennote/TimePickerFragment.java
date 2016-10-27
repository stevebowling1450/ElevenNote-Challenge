package com.elevenfifty.www.elevennote;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by stevebowling on 10/24/16.
 */


    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
    int hour;
    int minute;
    String am_pm;
    String col=":";



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);



        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }

    public void onTimeSet(TimePicker view, int hour, int minute) {

        if (hour == 0) {
            hour += 12;
            am_pm = "AM";
        }
        else if (hour == 12) {
            am_pm = "PM";
        } else if (hour > 12) {
            hour -= 12;
            am_pm = "PM";
        } else {
            am_pm = "AM";
        }
        if (minute <10){
            col=":0";
        }

        // Do something with the time chosen by the user

        TextView displayTime= (TextView) getActivity().findViewById(R.id.timeView);
        displayTime.clearFocus();
        displayTime.setText("Time Due: "+hour + col + minute + " " + am_pm);
        }
    }



