package com.example.anjanbharadwaj.bookrent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    String li = "http://libsearch.santaclaraca.gov/iii/encore/?lang=eng";
    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth.AuthStateListener mAuthListener;
    Button signup;
    EditText email;
    EditText password;
    EditText name;
    EditText address;
    EditText city;
    //EditText library;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        li = "http://libsearch.santaclaraca.gov/iii/encore/?lang=eng";
        System.out.println(li);
        signup = (Button)findViewById(R.id.button2);
        email = (EditText)findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        address = (EditText)findViewById(R.id.editText3);
        city = (EditText)findViewById(R.id.editText4);
        name = (EditText)findViewById(R.id.editText5);
        //library = (EditText)findViewById(R.id.editText6);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                String emai = email.getText().toString();
                String passwor = password.getText().toString();
                mAuth.createUserWithEmailAndPassword(emai, passwor)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Registration Failed",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    /*
                                    if(library.getText().toString() != ""){
                                        li = library.getText().toString();
                                    }else{
                                        li = "http://libsearch.santaclaraca.gov/iii/encore/?lang=eng";
                                    }
                                    */
                                    Map<String, Object> map1 = new HashMap<String, Object>();
                                    Map<String, Object> map2 = new HashMap<String, Object>();
                                    Map<String, Object> map3 = new HashMap<String, Object>();
                                    Map<String, Object> map4 = new HashMap<String, Object>();
                                    Map<String, Object> map5 = new HashMap<String, Object>();
                                    Map<String, Object> map6 = new HashMap<String, Object>();

                                    map1.put("Address", "");
                                    map2.put("City", "");
                                    map3.put("Name", "");
                                    map4.put(address.getText().toString(), "");
                                    map5.put(city.getText().toString(), "");
                                    map6.put(name.getText().toString(), "");
                                    DatabaseReference r2 = root.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                    r2.updateChildren(map1);
                                    //r2.updateChildren(map2);
                                    r2.child("Name").updateChildren(map6);
                                    //r2.updateChildren(map4);
                                    System.out.println("Yo");
                                    r2.child("Address").updateChildren(map4);
                                    r2.child("City").updateChildren(map5);

                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }

                                // ...
                            }
                        });
            }
        });

    }
}
