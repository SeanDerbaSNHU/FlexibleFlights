package com.example.flexibleflights;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.flexibleflights.databinding.FragmentDashboardBinding;

import java.util.Calendar;



public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private FragmentDashboardBinding binding;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date the user picks


        Button button = view.findViewById(R.id.buttonDate);
        button.setText((year + "-" + month + "-" + day));
    }
}
