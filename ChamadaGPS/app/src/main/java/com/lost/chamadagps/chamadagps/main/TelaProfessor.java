package com.lost.chamadagps.chamadagps.main;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lost.chamadagps.chamadagps.GPSTracker;
import com.lost.chamadagps.chamadagps.R;
import com.lost.chamadagps.chamadagps.adapters.SalaAdapter;
import com.lost.chamadagps.chamadagps.model.Sala;
import com.lost.chamadagps.chamadagps.util.RecyclerItemClickListener;
import com.lost.chamadagps.chamadagps.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TelaProfessor extends AppCompatActivity {

    private TextView textSemturmas;
    private ImageView imageSemturmas;
    private ProgressBar progressBar;
    private List<Sala> listS = new ArrayList<>();
    private SalaAdapter mAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private EditText edColegio;
    private EditText edNome;
    private EditText edRaio;
    private GPSTracker gpsTracker;
    private double lat = 0, longi = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_professor);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SalaAdapter(listS, TelaProfessor.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TelaProfessor.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(TelaProfessor.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );

        textSemturmas = (TextView) findViewById(R.id.textSemturmas);
        imageSemturmas = (ImageView) findViewById(R.id.imageSemturmas);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);
        imageSemturmas.setVisibility(View.INVISIBLE);
        textSemturmas.setVisibility(View.INVISIBLE);


        completaListSalas();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                gpsTracker = new GPSTracker(TelaProfessor.this);
                Log.i("LOG", "GPSTRACKER - " + gpsTracker.getLocation().getLatitude() + "," + gpsTracker.getLocation().getLongitude());
                lat = gpsTracker.getLocation().getLatitude();
                longi = gpsTracker.getLocation().getLongitude();

                final Dialog dialog = new Dialog(TelaProfessor.this, R.style.Theme_Dialog);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(true);

                edColegio = (EditText) dialog.findViewById(R.id.editText);
                edNome = (EditText) dialog.findViewById(R.id.editText2);
                edRaio = (EditText) dialog.findViewById(R.id.editText3);

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  dialog.dismiss();

                        if (TextUtils.isEmpty(edColegio.getText()) || TextUtils.isEmpty(edNome.getText()) || TextUtils.isEmpty(edRaio.getText())) {
                            Toast.makeText(TelaProfessor.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                        } else {

                            String key = databaseReference.push().getKey();
                            if (!databaseReference
                                    .child("USUARIOS")
                                    .child(Util.retornaNick(TelaProfessor.this))
                                    .child("SALAS")
                                    .child(key)
                                    .setValue(new Sala(edNome.getText().toString(), edColegio.getText().toString(), lat, longi, Integer.parseInt(edRaio.getText().toString())))
                                    .isSuccessful()) {

                                Toast.makeText(TelaProfessor.this, "Cadastrado ^^", Toast.LENGTH_SHORT).show();

                                dialog.dismiss();

                            } else {
                                Toast.makeText(TelaProfessor.this, "Erro :/", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void completaListSalas() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("USUARIOS").child(Util.retornaNick(TelaProfessor.this)).child("SALAS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mAdapter.limpalista();

                Log.i("LOG", dataSnapshot.toString());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Sala sala = postSnapshot.getValue(Sala.class);
                    Sala sala2 = new Sala(sala.getColegio(),sala.getNome(),sala.getLatitude(),sala.getLongitude(),sala.getRaio(),postSnapshot.getKey());
                    Log.e("LOG", sala.getNome());
                    listS.add(sala2);
                    textSemturmas.setVisibility(View.INVISIBLE);
                    imageSemturmas.setVisibility(View.INVISIBLE);


                }
                if (listS.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    textSemturmas.setVisibility(View.VISIBLE);
                    imageSemturmas.setVisibility(View.VISIBLE);

                }
                progressBar.setVisibility(View.INVISIBLE);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LOG", "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            SharedPreferences settings = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
            settings.edit().clear().commit();

            signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        auth.signOut();
        finish();
    }

}
