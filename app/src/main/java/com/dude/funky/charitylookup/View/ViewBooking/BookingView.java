package com.dude.funky.charitylookup.View.ViewBooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dude.funky.charitylookup.Account.CharityLogin;
import com.dude.funky.charitylookup.Account.LoginActivity;
import com.dude.funky.charitylookup.Account.MakeBooking;
import com.dude.funky.charitylookup.R;
import com.dude.funky.charitylookup.View.ViewBooking.BookingResponse;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookingView extends AppCompatActivity {

    @BindView(R.id.progress_bar1)
    ProgressBar progressBar;

    @BindView(R.id.booking_list)
    RecyclerView bookingList;

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    String message;
    String message1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        Intent intent1 = getIntent();
        message = intent1.getStringExtra("donor_email");
        message1 = intent1.getStringExtra("charity_name");

        ButterKnife.bind(this);
        init();
        getBookingList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_booking, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection

        switch (item.getItemId()) {

            case R.id.logout:

                startActivity(new Intent(this, LoginActivity.class));

                return true;

            case R.id.make:

                Intent intent1 = new Intent(this, MakeBooking.class);
                intent1.putExtra("charity_name", message1 );
                startActivity(intent1);
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }


    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        bookingList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }

    private void getBookingList(){
        Query query = db.collection("Bookings").document(message).collection(message1);

        FirestoreRecyclerOptions<BookingResponse> response = new FirestoreRecyclerOptions.Builder<BookingResponse>()
                .setQuery(query, BookingResponse.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<BookingResponse, BookingHolder>(response) {
            @Override
            public void onBindViewHolder(BookingHolder holder, int position, BookingResponse model) {
                //CollectionReference citiesRef = db.collection("Test");
                //Query query1 = citiesRef.whereEqualTo("Name", "Akshat");


                holder.textDate.setText(model.getDate());

                progressBar.setVisibility(View.GONE);
                holder.textItem.setText(model.getItem());

                //holder.textTitle.setText(model.getUID());
                holder.textTime.setText(model.getTime());


                holder.itemView.setOnClickListener(v -> {
                    //Snackbar.make(bookingList, model.getName()+", "+model.getUID()+" at "+model.getEmail(), Snackbar.LENGTH_LONG)
                            //.setAction("Action", null).show();


                    //launchMakeBooking(model.getName());

                });
            }

            @Override
            public BookingHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_booking, group, false);

                return new BookingHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        bookingList.setAdapter(adapter);


    }

    void launchMakeBooking(String tp){
        Intent intent = new Intent(this, MakeBooking.class);
        intent.putExtra("charity_name", tp );
        startActivity(intent);
    }

    public class BookingHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item)
        TextView textItem;
        //@BindView(R.id.image)
        //CircleImageView imageView;
        @BindView(R.id.date)
        TextView textDate;
        @BindView(R.id.time)
        TextView textTime;

        public BookingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}