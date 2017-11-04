package com.lost.chamadagps.chamadagps.main;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lost.chamadagps.chamadagps.R;
import com.lost.chamadagps.chamadagps.adapters.SalaAdapter;
import com.lost.chamadagps.chamadagps.adapters.SalaAlunoAdapter;
import com.lost.chamadagps.chamadagps.model.Sala;
import com.lost.chamadagps.chamadagps.util.RecyclerItemClickListener;
import com.lost.chamadagps.chamadagps.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TelaAluno extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    private List<Sala> listS = new ArrayList<>();
    private SalaAlunoAdapter mAdapter;
    private RecyclerView recyclerView;
    private EditText nomeProf;
    private ImageButton buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_aluno);

        nomeProf = (EditText) findViewById(R.id.etNomeProf);
        buscar = (ImageButton)findViewById(R.id.btPesqSala);

        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SalaAlunoAdapter(listS, TelaAluno.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TelaAluno.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(TelaAluno.this, new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//
//
//                        Sala s = listS.get(position);
//
//                        Toast.makeText(TelaAluno.this,"Sala"  + s.getNome(),Toast.LENGTH_LONG).show();
//                    }
//                })
//        );

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = nomeProf.getText().toString();

                if(TextUtils.isEmpty(user)){
                    Toast.makeText(TelaAluno.this,"Insira um nick válido",Toast.LENGTH_LONG).show();
                    return;
                }

                completaListSalas(user);
            }
        });
    }



    private void completaListSalas(final String user) {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("USUARIOS").child(user).child("SALAS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mAdapter.limpalista();

                Log.i("LOG", dataSnapshot.toString());

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Sala sala = postSnapshot.getValue(Sala.class);
                    Sala sala2 = new Sala(sala.getColegio(),sala.getNome(),sala.getLatitude(),sala.getLongitude(),sala.getRaio(),postSnapshot.getKey(),user);
                    Log.e("LOG", sala.getNome());
                    listS.add(sala2);
//                    textSemturmas.setVisibility(View.INVISIBLE);
//                    imageSemturmas.setVisibility(View.INVISIBLE);


                }
                if (listS.isEmpty()) {
//                    progressBar.setVisibility(View.INVISIBLE);
//                    textSemturmas.setVisibility(View.VISIBLE);
//                    imageSemturmas.setVisibility(View.VISIBLE);

                    Toast.makeText(TelaAluno.this,"Professor nao existe",Toast.LENGTH_LONG).show();

                }
           //     progressBar.setVisibility(View.INVISIBLE);
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
