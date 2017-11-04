package com.lost.chamadagps.chamadagps.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lost.chamadagps.chamadagps.GPSTracker;
import com.lost.chamadagps.chamadagps.R;
import com.lost.chamadagps.chamadagps.model.User;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView latlong;
    private EditText nick;
    private GPSTracker gpsTracker;
    private Button bt;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private boolean isProfessor = true;
    private List<String> pessoas = new ArrayList<String>();
    private RadioGroup rp;
    private RadioButton prof, aluno;
    private boolean jaTemNick = false;
    private boolean ehProfessor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        initCollapsingToolbar();

        rp = (RadioGroup) findViewById(R.id.radio);
        prof = (RadioButton) findViewById(R.id.prof);
        aluno = (RadioButton) findViewById(R.id.aluno);
        nick = (EditText) findViewById(R.id.nick);

        SharedPreferences sharedPreferences = getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        jaTemNick = sharedPreferences.getBoolean("NICK_JA_SELECIONADO", false);
        ehProfessor = sharedPreferences.getBoolean("IS_PROFESSOR", false);

        if (jaTemNick && ehProfessor) {
            Intent i = new Intent(MainActivity.this, TelaProfessor.class);
            startActivity(i);
            finish();
        } else if (jaTemNick && !ehProfessor) {
            Intent intent = new Intent(MainActivity.this, TelaAluno.class);
            startActivity(intent);
            finish();
        } else {

        }

        verificaNick();

        rp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.prof) {
                    isProfessor = true;
                    Log.i("LOG","professor true");
                } else {
                    isProfessor = false;
                    Log.i("LOG","professor false");
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (isProfessor) {

                    Log.i("LOG","professor true 2");
                    if (!TextUtils.isEmpty(nick.getText())) {

                        if (!pessoas.contains(nick.getText().toString())) {

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference
                                    .child("USUARIOS")
                                    .child(nick.getText().toString().replaceAll(" ", ""))
                                    .setValue(new User(nick.getText().toString().replaceAll(" ", "")));

                            SharedPreferences sharedPreferences = getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("NICK", nick.getText().toString().replaceAll(" ", ""));
                            editor.putBoolean("IS_PROFESSOR", true);
                            editor.putBoolean("NICK_JA_SELECIONADO", true);
                            editor.commit();

                            Intent intent = new Intent(MainActivity.this, TelaProfessor.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(getApplicationContext(), "Nick já existente", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Campos em branco", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Log.i("LOG","professor false 2");

                    if (!pessoas.contains(nick.getText().toString())) {


                        SharedPreferences sharedPreferences = getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("NICK", nick.getText().toString().replaceAll(" ", ""));
                        editor.putBoolean("IS_PROFESSOR", false);
                        editor.putBoolean("NICK_JA_SELECIONADO", true);
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, TelaAluno.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Nick já existente", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

//        bt = (Button)findViewById(R.id.bt);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });

//        gpsTracker = new GPSTracker(MainActivity.this);
//        Log.i("LOG GPSTRACKER - ", "" + gpsTracker.getLocation().getLatitude() + "," + gpsTracker.getLocation().getLongitude());
//
//        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(MainActivity.this);
//        locationProvider.getLastKnownLocation()
//                .subscribe(new Action1<Location>() {
//                    @Override
//                    public void call(Location location) {
//                        latlong.setText(String.valueOf(location.getLatitude() + " " + location.getLongitude()));
//                        Log.i("LOG api - ", "" + location.getLatitude() + "," + location.getLongitude());
//
//                        Location loc1 = new Location("");
//                        loc1.setLatitude(-5.056789);
//                        loc1.setLongitude(-42.790906);
//                        Log.i("LOG api - ", "Minha distancia ate p1 é " +location.distanceTo(loc1) + " metros");
//
//
//                        Location loc2 = new Location("");
//                        loc2.setLatitude(-5.056333);
//                        loc2.setLongitude(-42.790692);
//
//                        Log.i("LOG api - ", "Minha distancia ate p1 é " +location.distanceTo(loc2) + " metros");
//
//                        Location loc3 = new Location("");
//                        loc3.setLatitude(-5.056164);
//                        loc3.setLongitude(-42.790491);
//
//                        Log.i("LOG api - ", "Minha distancia ate p1 é " +location.distanceTo(loc3) + " metros");
//
//                        Location loc4 = new Location("");
//                        loc4.setLatitude(-5.056736);
//                        loc4.setLongitude(-42.790239);
//
//                        Log.i("LOG api - ", "Minha distancia ate p1 é " +location.distanceTo(loc4) + " metros");
//
//                        Location loc5 = new Location("");
//                        loc5.setLatitude(-5.056477);
//                        loc5.setLongitude(-42.790706);
//
//
//                        Log.i("LOG api - ", "Minha distancia ate p1 é " +location.distanceTo(loc5) + " metros");
//                    }
//                });

        try {
            Glide.with(this).load(R.drawable.fundo).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void verificaNick() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("USUARIOS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.i("LOG", "AQUIiiiiiiiiiii " + dataSnapshot);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.i("LOG", postSnapshot.getKey());
                    pessoas.add(postSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOG", "Failed to read value.", error.toException());
            }
        });

    }

    public void signOut() {
        auth.signOut();
        finish();
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
