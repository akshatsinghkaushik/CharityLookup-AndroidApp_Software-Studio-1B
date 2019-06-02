package com.dude.funky.charitylookup.Account;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dude.funky.charitylookup.R;
import com.dude.funky.charitylookup.View.Main_main;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditBooking extends AppCompatActivity {

    private TextView charityName;
    private TextView selectDateTv;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private boolean dateSelected = false;
    private TextView selectTimeTv;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private boolean timeSelected = false;
    private TextView selectItemTv;
    private boolean itemSelected = false;
    private Button confirmBookingBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_booking);

        charityName = findViewById(R.id.make_booking_charity_name);
        selectDateTv = findViewById(R.id.select_date_tv);
        selectTimeTv = findViewById(R.id.select_time_tv);
        selectItemTv = findViewById(R.id.select_item_tv);
        confirmBookingBtn = findViewById(R.id.confirm_booking_button);
        progressBar = findViewById(R.id.make_booking_progressbar);


        Intent intent = getIntent();
        String message = intent.getStringExtra("charity_name");
        charityName.setText(message);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // Allow user to make date selection
        selectDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditBooking.this,
                        android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                        dateSetListener,
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Set TextView to show selected date
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ++month;
                Log.d("TAG", "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                dateSelected = true;
                selectDateTv.setText(date);
            }
        };

        // Allow user to make time selection
        selectTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog = new TimePickerDialog(
                        EditBooking.this,
                        android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                        timeSetListener, 12, 00, false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Set TextView to show selected date
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (minute < 10 && hourOfDay == 0) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + 12 + " : 0" + minute + "am");
                    String time = 12 + " : 0" + minute + "am";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute >= 10 && hourOfDay == 0) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + 12 + " : " + minute + "am");
                    String time = 12 + " : " + minute + "am";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute < 10 && hourOfDay < 12) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + hourOfDay + " : 0" + minute + "am");
                    String time = hourOfDay + " : 0" + minute + "am";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute >= 10 && hourOfDay < 12) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + hourOfDay + " : " + minute + "am");
                    String time = hourOfDay + " : " + minute + "am";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute < 10 && hourOfDay == 12) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + hourOfDay + " : 0" + minute + "pm");
                    String time = hourOfDay + " : 0" + minute + "pm";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute > 10 && hourOfDay == 12) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + hourOfDay + " : " + minute + "pm");
                    String time = hourOfDay + " : " + minute + "pm";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else if (minute < 10 && hourOfDay > 12) {
                    Log.d("TAG", "onTimeSet: hh : mm: " + (hourOfDay - 12) + " : 0" + minute + "pm");
                    String time = (hourOfDay - 12) + " : 0" + minute + "pm";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
                else {
                    Log.d("TAG", "onTimeSet: hh : mm: " + (hourOfDay - 12) + " : " + minute + "pm");
                    String time = (hourOfDay - 12) + " : " + minute + "pm";
                    timeSelected = true;
                    selectTimeTv.setText(time);
                }
            }
        };

        selectItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditBooking.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                builder.setTitle("Name or description of item");

                // Set up AlertDialog input textbox
                final EditText input = new EditText(EditBooking.this);
                input.setPadding(20, 20, 20, 20);
                input.setHeight(300);
                input.setHint("Click here to type");
                input.setGravity(Gravity.CENTER);
                input.setBackgroundResource(R.drawable.make_booking_edittext_border);
                builder.setView(input);

                // Set up AlertDialog buttons
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectItemTv.setText(input.getText().toString());
                        itemSelected = true;
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String charName = charityName.getText().toString();
                String date = DateConversionMethods.encodeDate(selectDateTv.getText().toString());
                String time = selectTimeTv.getText().toString();
                String item = selectItemTv.getText().toString();

                if (dateSelected && timeSelected && itemSelected) {
                    confirmBookingBtn.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    Map<String, Object> booking = new HashMap<>();
                    booking.put("time", time);
                    booking.put("date",date);
                    booking.put("item", item);

                    // Add booking to database
                    db.collection("Bookings").document(user.getEmail())
                            .collection(charName).document()
                            .update(booking)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    confirmBookingBtn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    Log.d("TAG", "DocumentSnapshot successfully written");
                                    Toast.makeText(EditBooking.this, "Booking Successful", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error writing document", e);
                                    Toast.makeText(EditBooking.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                    startActivity(new Intent(EditBooking.this, Main_main.class));
                    finish();
                }
            }
        });
    }
}