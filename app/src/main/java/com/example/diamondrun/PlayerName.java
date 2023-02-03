package com.example.diamondrun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlayerName extends AppCompatActivity {




    public static final String PREF_LOGIN = "LOGIN_PREF";
    public static final String KEY_CREDENTIALS = "LOGIN_CREDENTIALS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        final EditText playerNameEt = findViewById(R.id.playerNameEt);
         AppCompatButton startGameBtn = findViewById(R.id.startGameBtn);

        //child name and value
        //databaseReference.child("UserName2").setValue("Chris");

        //child name and value
        //databaseReference.child("PlayerScore").setValue("10");

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String getPlayerName = playerNameEt.getText().toString();

                if(getPlayerName.isEmpty()){
                    Toast.makeText(PlayerName.this, "Please Enter a Name", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(PlayerName.this, MainActivity.class);
                    //intent.putExtra("username", getPlayerName);
                    SharedPreferences.Editor editor = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE).edit();
                    //inserting typed et into editor
                    editor.putString(KEY_CREDENTIALS, getPlayerName);
                    editor.commit();

                    //child name and value. Saving player name to database username child
                    //databaseReference.child("Username").setValue(getPlayerName);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }


}
