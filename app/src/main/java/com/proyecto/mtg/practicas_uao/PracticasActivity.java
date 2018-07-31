package com.proyecto.mtg.practicas_uao;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class PracticasActivity extends AppCompatActivity {

    ArrayList<Practica> listaPracticas = new ArrayList<>();
    private ListView lv;
    private double longitud, latitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practicas);

        longitud = -71.52264241491616;
        latitud = 4.3541464594093333;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d("lectura", "onCreate: JEPEESEEEEEEEEEEEEE");






        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener myLocationListener = new MyLocationListener();

        myLocationListener.setPracticasActivity(this);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) myLocationListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Practicas");

        lv = (ListView) findViewById(R.id.ListView);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                for (DataSnapshot single : dataSnapshot.getChildren()) {
                    Practica p = single.getValue(Practica.class);
                    listaPracticas.add(p);
                    //insertarPractica(p);
                    Log.d("lectura", "onDataChange: " + p);
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

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    createList(listaPracticas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }

    public void showDialog(Practica p) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(p.toString());
        builder1.setTitle(p.getnom_empres());
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void insertarPractica(Practica practica) {
        AdminSQLLiteOpenHelper admin = new AdminSQLLiteOpenHelper(this, "DB", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        ContentValues registro = new ContentValues();

        registro.put("codigo", practica.getcodigo());
        registro.put("nom_empres", practica.getnom_empres());
        registro.put("fecha_inicio", practica.getfecha_fin());
        registro.put("fecha_fin", practica.getfecha_fin());
        registro.put("programa", practica.getProgramas());
        registro.put("requisitos", practica.getRequisitos());
        registro.put("fotografia", practica.getFotografia());
        registro.put("sede", practica.getSede());

        bd.insert("PRACTICAS", null, registro);
        bd.close();

    }

    public void createList(ArrayList listaPracticas) {
        final ArrayList<Practica> lista = listaPracticas;

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    //Your code goes here
                    final PracticasAdapter adapter = new PracticasAdapter(PracticasActivity.this, lista, latitud, longitud);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Practica x = adapter.getIt(position);
                            showDialog(x);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            StringTokenizer t = new StringTokenizer(scanContent, "*");
            final String codigo = t.nextToken();
            Practica practicaEscaneada = new Practica();
            for (Practica practica : listaPracticas) {
                if (practica.getcodigo().equals(codigo)) {
                    practicaEscaneada = practica;
                }
            }
            showDialog(practicaEscaneada);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void setLocation(Location loc) {
        longitud = loc.getLongitude();
        latitud = loc.getLatitude();
        Log.d("lectura", "setLocation: " + longitud +"-----" + latitud);
    }


}


