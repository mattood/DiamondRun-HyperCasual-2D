package com.example.diamondrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter2 extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;
    ImageView decline;
    ImageView playBtn;
    ImageView waitingForTurn;

    DatabaseReference databaseReference;
    String myName;
    SharedPreferences preferences;

    public ListViewAdapter2(Context context, ArrayList<String>items, DatabaseReference databaseReference, String myName){
        super(context, R.layout.list_row2, items);
        this.context = context;
        list = items;
        this.databaseReference = databaseReference;
        this.myName = myName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row2, null);

            TextView number = convertView.findViewById(R.id.number2);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.name2);
            name.setText(list.get(position));
            name.setTypeface(Typeface.DEFAULT_BOLD);
            name.setTextColor(Color.WHITE);
            name.setTextSize(30);
            playBtn = convertView.findViewById(R.id.play);
            waitingForTurn = convertView.findViewById(R.id.waitingforturn);

                databaseReference.child("Sessions").child(MultiplayerGames.uniqueSessionId).child("turn").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String currentPlayerTurn = snapshot.getValue().toString();
                        if (currentPlayerTurn.equals(myName)) {
                            //if its my turn then play button is visible
                            playBtn.setVisibility(View.VISIBLE);
                            //if its not my turn then play button is invisible and show waiting for turn
                            waitingForTurn.setVisibility(View.INVISIBLE);
                        } else {
                            //not my turn
                            //if its not my turn then play button is visible
                            playBtn.setVisibility(View.INVISIBLE);
                            //if its not my turn then play button is invisible and show waiting for turn
                            waitingForTurn.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            //on accept we change database status to accepted
            playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //on click of accept invite
                    context.startActivity(new Intent(context, GameActivity.class));

                }
            });

        }
        return convertView;
    }


}
