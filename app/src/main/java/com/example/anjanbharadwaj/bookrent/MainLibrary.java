package com.example.anjanbharadwaj.bookrent;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

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

public class MainLibrary extends AppCompatActivity {
    WebView w;
    String s = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_library);
        w = (WebView)findViewById(R.id.web);
        /*
        DatabaseReference r = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Library");


        r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //s = (String) (dataSnapshot.child("Library").getValue());
               // ArrayList<String> set = new ArrayList<String>();
                System.out.println((String) (dataSnapshot.getValue()));
                s = (String) (dataSnapshot.getValue());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        r.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println((String) (dataSnapshot.getValue()));
                s = (String) (dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */
        s = "http://libsearch.santaclaraca.gov/iii/encore/?lang=eng";
        System.out.println(s);
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new MyJavaScriptInterface(this), "HTMLOUT");
        w.loadUrl(s);

    }
    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }
        @JavascriptInterface
        public void showHTML(String html) {
            //html is your code


            /*
            for(int i = 16026 ;i<17000; i++){
                if(html.charAt(i)=='.'){
                    break;
                }
                sentence += html.charAt(i);
            }
            */
            //System.out.println(html);



        }

    }
}


