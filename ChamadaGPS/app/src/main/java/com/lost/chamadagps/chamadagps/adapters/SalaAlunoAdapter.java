package com.lost.chamadagps.chamadagps.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lost.chamadagps.chamadagps.R;
import com.lost.chamadagps.chamadagps.main.TelaProfessor;
import com.lost.chamadagps.chamadagps.model.Aluno;
import com.lost.chamadagps.chamadagps.model.Sala;
import com.lost.chamadagps.chamadagps.util.Util;

import java.util.List;

/**
 * Created by gustavo on 02/11/2017.
 */

public class SalaAlunoAdapter extends RecyclerView.Adapter<SalaAlunoAdapter.MyViewHolder> {

    private List<Sala> lista;
    private View view;
    private DatabaseReference databaseReference;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView nome, colegio;
        public ImageButton btadd;

        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            colegio = (TextView) view.findViewById(R.id.colegio);
            btadd = (ImageButton) view.findViewById(R.id.btadd);


        }

    }

    public SalaAlunoAdapter(List<Sala> lista, Context mContext) {
        this.lista = lista;
        this.mContext = mContext;
    }


    @Override
    public SalaAlunoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sala_aluno, parent, false);
        return new SalaAlunoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Sala s = lista.get(position);
        final String key = s.getKey();

        holder.nome.setText(s.getColegio());
        holder.colegio.setText(s.getNome());

        holder.btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();

                if (!databaseReference
                        .child("USUARIOS")
                        .child(s.getProf())
                        .child("SALAS")
                        .child(s.getKey())
                        .child("ALUNOS")
                        .child(key)
                        .setValue(new Aluno(Util.retornaNick(mContext),0,0))
                        .isSuccessful()) {

                    Toast.makeText(mContext, "Cadastrado ^^", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(mContext, "Erro :/", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista() {
        lista.clear();
    }

}