package com.dude.funky.charitylookup.View;

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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class Main_main extends AppCompatActivity {

    @BindView(R.id.progress_bar1)
    ProgressBar progressBar;

    @BindView(R.id.friend_list)
    RecyclerView friendList;

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_main);
        ButterKnife.bind(this);
        init();
        getFriendList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //respond to menu item selection

        switch (item.getItemId()) {

            case R.id.logout:

                startActivity(new Intent(this, LoginActivity.class));

                return true;

            case R.id.profile:

                startActivity(new Intent(this, CharityLogin.class));

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }


    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        friendList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }

    private void getFriendList(){
        Query query = db.collection("Charities");

        FirestoreRecyclerOptions<FriendsResponse> response = new FirestoreRecyclerOptions.Builder<FriendsResponse>()
                .setQuery(query, FriendsResponse.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<FriendsResponse, FriendsHolder>(response) {
            @Override
            public void onBindViewHolder(FriendsHolder holder, int position, FriendsResponse model) {
                //CollectionReference citiesRef = db.collection("Test");
                //Query query1 = citiesRef.whereEqualTo("Name", "Akshat");




                progressBar.setVisibility(View.GONE);
                holder.textName.setText(model.getName());

                holder.textTitle.setText(model.getUID());
                holder.textCompany.setText(model.getEmail());
                //Glide.with(getApplicationContext())
                //.load(model.getImage())
                //.into(holder.imageView);

                holder.itemView.setOnClickListener(v -> {
                    Snackbar.make(friendList, model.getName()+", "+model.getUID()+" at "+model.getEmail(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                    launchMakeBooking(model.getName());

                });
            }

            @Override
            public FriendsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_item, group, false);

                return new FriendsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        friendList.setAdapter(adapter);


    }

    void launchMakeBooking(String tp){
        Intent intent = new Intent(Main_main.this, MakeBooking.class);
        intent.putExtra("charity_name", tp );
        startActivity(intent);
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView textName;
        //@BindView(R.id.image)
        //CircleImageView imageView;
        @BindView(R.id.title)
        TextView textTitle;
        @BindView(R.id.company)
        TextView textCompany;

        public FriendsHolder(View itemView) {
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