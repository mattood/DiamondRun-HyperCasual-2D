package com.example.diamondrun;

import static com.example.diamondrun.PlayerName.KEY_CREDENTIALS;
import static com.example.diamondrun.PlayerName.PREF_LOGIN;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MyInvites extends AppCompatActivity {

    DatabaseReference databaseReference;
    static ListView listView;
    SharedPreferences preferences;
    String myName;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ListViewAdapter listViewAdapter;
    String test;
    static String inviteSourceName;
    static String uSessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invites);

        preferences = getSharedPreferences(PREF_LOGIN, MODE_PRIVATE);
        databaseReference = FirebaseDatabase.getInstance().getReference("DiamondRun");

        myName = preferences.getString(KEY_CREDENTIALS, "");
        listView= (ListView) findViewById(R.id.listviewtxt);
        listViewAdapter = new ListViewAdapter(MyInvites.this, arrayList, databaseReference, myName);
        listView.setAdapter(listViewAdapter);
        int r = getResources().getIdentifier("background", "drawable", "com.example.diamondrun");
        listView.setBackgroundResource(r);

        //we are checking if there is an invite with the current player listed as the destination
        databaseReference.child("Invites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if(data.child("dest").getValue().toString().equals(myName) ) {
                        Toast.makeText(MyInvites.this, "found your name in database", Toast.LENGTH_SHORT).show();

                        //getting the user who invited
                        inviteSourceName = data.child("source").getValue().toString();

                            //adding invite source if its not the current player
                            if (!inviteSourceName.equals(myName)) {
                                //if the invite is not already there from that invite source player
                                if(!arrayList.contains(inviteSourceName.toString())) {
                                    arrayList.add(inviteSourceName);
                                    listViewAdapter.notifyDataSetChanged();
                                }
                            }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public static void removeItem(int remove){ //removing item from array adapter
        arrayList.remove(remove);
        listView.setAdapter(listViewAdapter);
    }

    public static void makeNewSession(DatabaseReference databaseReference, String myName, String uSessionId){
        HashMap hashMap = new HashMap();
        hashMap.put("user1", myName); //person who acepted invite
        hashMap.put("user2", inviteSourceName); //person who send the invite
        hashMap.put("user1score", 0);
        hashMap.put("user2score", 0);
        hashMap.put("round", 0);
        hashMap.put("turn", myName); //user who accepted the invite has first turn
        //uSessionId = databaseReference.push().getKey(); //creating the key here so we can retrieve it later
        databaseReference.child("Sessions").child(uSessionId).setValue(hashMap); //instead of push we set uSessionId as child(same thing)



    }

}
