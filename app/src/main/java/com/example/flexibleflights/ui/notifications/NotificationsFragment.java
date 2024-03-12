package com.example.flexibleflights.ui.notifications;

import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flexibleflights.Item;
import com.example.flexibleflights.MyAdapter;
import com.example.flexibleflights.R;
import com.example.flexibleflights.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    List<Item> items = new ArrayList<Item>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //////
        //Buttons
        /////
        Button date = root.findViewById(R.id.buttonDate2);
        Button date2 = root.findViewById(R.id.buttonDate3);
        Button passengers = root.findViewById(R.id.buttonNumPassengers2);

        /////
        //RecyclerView
        /////
        items = makeDummyList();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MyAdapter(items));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatePickerFragment newFragment = new DatePickerFragment();
                //newFragment.show(getParentFragmentManager(), "datePicker");
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        view.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int day) {
                                // on below line we are setting date to our text view.
                                month++; //Month returns month-1 for some reason, this fixes it
                                date.setText(year + "-" + (month) + "-" + day);

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();

            }
        });

        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder d = new AlertDialog.Builder(root.getContext());

                View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
                d.setTitle("Title");
                d.setMessage("Message");
                d.setView(dialogView);
                final NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.dialog_number_picker);
                numberPicker.setMaxValue(10);
                numberPicker.setMinValue(1);
                numberPicker.setWrapSelectorWheel(false);
                d.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        passengers.setText(numberPicker.getValue() +"");
                    }
                });
                d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = d.create();
                alertDialog.show();
            }
        });


        return root;
    }

    public List<Item> makeDummyList(){
        List<Item> items = new ArrayList<Item>();

        for(int i = 0; i < 20; i++){
            items.add(new Item(("Test" + valueOf(i))));
        }

        return items;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}