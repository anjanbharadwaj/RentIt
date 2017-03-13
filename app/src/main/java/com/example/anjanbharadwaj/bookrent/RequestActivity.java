package com.example.anjanbharadwaj.bookrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RequestActivity extends AppCompatActivity {
DatabaseReference r = FirebaseDatabase.getInstance().getReference();
    EditText name;
    Button find;
    ArrayList<String> addresses = new ArrayList<>();
    ArrayList<String> users;
    int j;
    String name1;
    String remember = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        find = (Button)findViewById(R.id.find);
        name = (EditText)findViewById(R.id.editText6);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String book = name.getText().toString();
                r.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        users = new ArrayList<String>();
                        Iterator i = dataSnapshot.getChildren().iterator();

                        while(i.hasNext()){
                            String p = ((DataSnapshot)i.next()).getKey();
                            users.add(p);
                            System.out.println(users.size());
                            System.out.println("User" + p);

                        }

                        for(j = 1; j<users.size(); j++){
                           DatabaseReference rootu = r.child(users.get(j-1)).child("Books");
                            rootu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    System.out.println("came here");
                                    //ArrayList<String> books = new ArrayList<String>();
                                    Iterator i = dataSnapshot.getChildren().iterator();

                                    while(i.hasNext()){
                                        String l = ((DataSnapshot)i.next()).getKey();
                                        System.out.println(l);
                                        //System.out.println(name + ",,,,");
                                        //books.add(((DataSnapshot)i.next()).getKey());
                                        System.out.println(book + "book");
                                        System.out.println(l);
                                        if(l.equals(book)){
                                            System.out.println("match");
                                            DatabaseReference rb = r.child(users.get(j-1)).child("Address");
                                            rb.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    ArrayList<String> st = new ArrayList<String>();
                                                    Iterator i = dataSnapshot.getChildren().iterator();

                                                    while(i.hasNext()){
                                                        st.add(((DataSnapshot)i.next()).getKey());
                                                    }
                                                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                                    //System.out.println("Hi" + addresses.get(0));
                                                    //intent.putStringArrayListExtra("ArrayList",addresses);
                                                    DatabaseReference root1 = FirebaseDatabase.getInstance().getReference().child(users.get(j-1)).child("Name");
                                                    root1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            Iterator i = dataSnapshot.getChildren().iterator();

                                                            while(i.hasNext()){
                                                                String k = ((DataSnapshot)i.next()).getKey();
                                                                System.out.println(k + " ::::::::::sdafiusdjuvh");
                                                                name1 = k;

                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                    intent.putExtra("String", st.get(0) + " Santa Clara");
                                                    intent.putExtra("User", name1);
                                                    System.out.println("boooooo " + name1);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            /*
                                            DatabaseReference rd = r.child(users.get(j)).child("City");
                                            rd.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    ArrayList<String> st = new ArrayList<String>();
                                                    Iterator i = dataSnapshot.getChildren().iterator();

                                                    while(i.hasNext()){
                                                        st.add(((DataSnapshot)i.next()).getKey());
                                                    }
                                                    /*
                                                    remember += (" " + st.get(0));
                                                    System.out.println(st.get(0));
                                                    addresses.add(remember);
                                                    System.out.println(remember + ".." + addresses.size());

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            */

                                            //addresses.add(r.child(users.get(j)).child("Address"))
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(addresses.size()==0){
                    Toast.makeText(RequestActivity.this, "Attempting Request. If you aren't redirected within 5 seconds, the book couldn't be found.",
                            Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            /*
            for (int i = 0; i < addresses.size(); i++) {
                intent.putExtra(String.valueOf(i), addresses.get(i));
            }
            */
                    System.out.println("Hi" + addresses.get(0));
                    //intent.putStringArrayListExtra("ArrayList",addresses);
                    intent.putExtra("String", addresses.get(0));
                    startActivity(intent);
                }


            }
        });


    }
}
