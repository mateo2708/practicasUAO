package com.proyecto.mtg.practicas_uao;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Usuario> listaUsuarios = new LinkedList<>();
    private EditText txt_email, txt_passwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                5);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                5);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("users");

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_passwd = (EditText) findViewById(R.id.txt_passwd);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    Usuario u = single.getValue(Usuario.class);
                    listaUsuarios.add(u);
                    Log.d("lectura", "onDataChange: " + u);
                }
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                // ...
            }
        };
        myRef.addValueEventListener(postListener);
    }

    public void onClickIniciar(View view) {
        String email = txt_email.getText().toString();
        String passwd = txt_passwd.getText().toString();
        Log.d("lectura", "onClickIniciar: " + email + "----" + passwd);

        for (Usuario user : listaUsuarios) {
            if (user.getCorreo().equals(email)&& user.getContraseña().equals(passwd)) {
                Intent i = new Intent(MainActivity.this, PracticasActivity.class);
                startActivity(i);
                Log.d("lectura", "onClickIniciar: Correccctooo");
            }
        }
    }


}
