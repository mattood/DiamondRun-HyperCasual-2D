package com.example.diamondrun;

import static com.example.diamondrun.PlayerName.KEY_CREDENTIALS;
import static com.example.diamondrun.PlayerName.PREF_LOGIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SendInvite extends AppCompatActivity {

    String name;
    private Button sendInviteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invite);

        //retrieving the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("DiamondRun");

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        sendInviteBtn = (Button) findViewById(R.id.sendInviteBtn);

        //storing the saved users name into name
        name = preferences.getString(KEY_CREDENTIALS, "");

        final EditText playerNameInviteEt = findViewById(R.id.playerNameInviteEt);


        //storing saved player into source hashmap
        HashMap<String, Object> hashMap = new HashMap();



        sendInviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //storing typed player into dest hashmap
                final String getPlayerName = playerNameInviteEt.getText().toString();
                hashMap.put("source", name);
                hashMap.put("dest", getPlayerName);
                hashMap.put("status", "null");
                //new database child being added with source and dest players
                databaseReference.child("Invites").push().setValue(hashMap);
                Intent intent = new Intent(SendInvite.this, MultiplayerGames.class);
                startActivity(intent);
            }
        });
    }



}