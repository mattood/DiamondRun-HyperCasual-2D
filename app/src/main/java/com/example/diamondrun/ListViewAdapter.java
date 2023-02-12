package com.example.diamondrun;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diamondrun.PlayerName.PREF_LOGIN;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> list;
    Context context;
    ImageView decline;
    ImageView accept;
    DatabaseReference databaseReference;
    String myName;
    SharedPreferences preferences;

    public ListViewAdapter(Context context, ArrayList<String>items, DatabaseReference databaseReference, String myName){
        super(context, R.layout.list_row, items);
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
            convertView = layoutInflater.inflate(R.layout.list_row, null);

            TextView number = convertView.findViewById(R.id.number);
            number.setText(position + 1 + ".");

            TextView name = convertView.findViewById(R.id.name);
            name.setText(list.get(position));
            name.setTypeface(Typeface.DEFAULT_BOLD);
            name.setTextColor(Color.WHITE);
            name.setTextSize(30);
            accept = convertView.findViewById(R.id.accept);
            decline = convertView.findViewById(R.id.decline);

            preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
            SharedPreferences.Editor editor = preferences.edit();

            //on accept we change database status to accepted
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //on click of accept invite
                    MyInvites.removeItem(position);

                    databaseReference.child("Invites").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("status", "accepted");
                                //if my name is listed as the invite destination
                                if (data.child("dest").getValue().toString().equals(myName)) {
                                    String uInviteSessionId = data.child("uSessionID").getValue().toString(); //getting unique id that this location is stored at
                                    databaseReference.child("Invites").child(uInviteSessionId).updateChildren(hashMap); //updating status to exact person
                                    MyInvites.makeNewSession(databaseReference, myName, uInviteSessionId);//creating a new session upon accept of invite
                                    //storing unique session id created upon accept into sharedpreferences

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });

            decline.setOnClickListener(new View.OnClickListener() { //on decline we remove invite from database and adapter
                @Override
                public void onClick(View v) {

                }
            });
        }
        return convertView;
    }


}
