package com.example.diamondrun;

import static com.example.diamondrun.PlayerName.KEY_CREDENTIALS;
import static com.example.diamondrun.PlayerName.PREF_LOGIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiplayerGames extends AppCompatActivity {

    DatabaseReference databaseReference;
    static ListView listView2;
    SharedPreferences preferences;
    String myName;
    static ArrayList<String> arrayList2 = new ArrayList<>();
    static ListViewAdapter2 listViewAdapter2;
    static String uniqueSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer_games);
        preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference("DiamondRun");
        myName = preferences.getString(KEY_CREDENTIALS, "");
        listView2 = (ListView) findViewById(R.id.listviewsessions);
        listViewAdapter2 = new ListViewAdapter2(MultiplayerGames.this, arrayList2, databaseReference, myName);
        listView2.setAdapter(listViewAdapter2);
        int r = getResources().getIdentifier("multiplayerlistbackground", "drawable", "com.example.diamondrun");
        listView2.setBackgroundResource(r);




        databaseReference.child("Sessions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {

                    //if I am one of the users in an active session
                    if (data.child("user1").getValue().toString().equals(myName) || data.child("user2").getValue().toString().equals(myName)) {
                        uniqueSessionId = data.getKey(); //getting unique key that this location is stored at
                        String user1 = data.child("user1").getValue().toString();
                        String user2 = data.child("user2").getValue().toString();
                        //checking to make sure the players dont have an active session already
                        if(!arrayList2.contains(user1.toString() + " vs " + user2.toString()) && (!arrayList2.contains(user2.toString() + " vs " + user1.toString()))) {
                            arrayList2.add(user1 + " vs " + user2);
                            listViewAdapter2.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        findViewById(R.id.sendInvitesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MultiplayerGames.this, SendInvite.class));
            }
        });

        findViewById(R.id.myInvitesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MultiplayerGames.this, MyInvites.class));
            }
        });


    }

    public static void startActivity(){ //removing item from array adapter

    }

}