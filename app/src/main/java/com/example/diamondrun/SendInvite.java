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
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SendInvite extends AppCompatActivity {

    String name;
    String uSessionId;
    private ImageView sendInviteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invite);

        //retrieving the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("DiamondRun");

        SharedPreferences preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        sendInviteBtn = (ImageView) findViewById(R.id.sendInviteBtn);

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
                uSessionId = databaseReference.push().getKey(); //creating the key here so we can retrieve it later
                hashMap.put("uSessionID", uSessionId);
                //new database child being added with source and dest players
                databaseReference.child("Invites").child(uSessionId).setValue(hashMap);
                Intent intent = new Intent(SendInvite.this, MultiplayerGames.class);
                startActivity(intent);
            }
        });
    }


    //if a session has me as user2 then it means theres an accepted game that i sent the invite

}