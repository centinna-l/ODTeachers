package com.example.jerryjoy.od_teachers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HandlerOdForm.MyCallback {
    private Toolbar hToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference mDataBase;
    private FirebaseUser user;
    String email;
    private RecyclerView rview;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager manager;
    private ArrayList<String> keys=new ArrayList<>();
    private ArrayList<String> names=new ArrayList<>();
    private ArrayList<String> regno=new ArrayList<>();
    private ArrayList<String> sec=new ArrayList<>();
    private ArrayList<String> reasons=new ArrayList<>();
    private ArrayList<String> dates=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        hToolbar = findViewById(R.id.home_toolbar);
        rview = findViewById(R.id.rview);
        setSupportActionBar(hToolbar);
        getSupportActionBar().setTitle("OD Teachers");
        email = user.getEmail();
        email = email.substring(0, email.indexOf('@'));
        mDataBase = FirebaseDatabase.getInstance().getReference().child(email);
        mDataBase.keepSynced(true);
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
            mDataBase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        ApplicationHandler applicationHandler = dataSnapshot.getValue(ApplicationHandler.class);
                        names.add(applicationHandler.getName());
                        regno.add(applicationHandler.getRegNo());
                        sec.add(applicationHandler.getSec());
                        reasons.add(applicationHandler.getReason());
                        dates.add(applicationHandler.getTo());
                        keys.add(dataSnapshot.getKey());
                    adapter = new HandlerOdForm(HomeActivity.this, names, regno, sec, reasons, dates, HomeActivity.this);
                    manager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
                    rview.setLayoutManager(manager);
                    rview.setAdapter(adapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            updateUI();
        }
    }

    public void updateUI() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout) {
            FirebaseAuth.getInstance().signOut();
            updateUI();
        }
        return true;
    }

    @Override
    public void handleAccept(int position) {


            String name = names.get(position);
            String reg = regno.get(position);
            String reason = reasons.get(position);
            String section = sec.get(position);
            String date = dates.get(position);
            Boolean status=true;
            String classAdvisor=user.getEmail();
            String from="";
            String year="";
            String dept="";
            ApplicationHandler applicationHandler = new ApplicationHandler(name,reg, dept,section,year,reason,from, date,classAdvisor,status);
        if (email.equals("hodcsevdp")){
            mDataBase=FirebaseDatabase.getInstance().getReference(names.get(position)).child("HOD");
            mDataBase.push().setValue("Approve");

        }
        else {
            mDataBase = FirebaseDatabase.getInstance().getReference().child("hodcsevdp");
            mDataBase.push().setValue(applicationHandler);
        }

        //Toast.makeText(this, "Key: "+keys.get(position), Toast.LENGTH_SHORT).show();
        //adapter.notifyItemRemoved(position);
    }

    @Override
    public void handleReject(int position) {
        mDataBase=FirebaseDatabase.getInstance().getReference(email).child(keys.get(position));
        mDataBase.removeValue();
        mDataBase=FirebaseDatabase.getInstance().getReference(names.get(position)).child("Hod");
        mDataBase.push().setValue("Reject");
        adapter.notifyItemRemoved(position);
        rview.setAdapter(adapter);

    }

}
