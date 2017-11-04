package com.lost.chamadagps.chamadagps.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.lost.chamadagps.chamadagps.model.Sala;
import com.lost.chamadagps.chamadagps.util.Util;

import java.util.List;

/**
 * Created by gustavo on 24/10/2017.
 */

public class SalaAdapter extends RecyclerView.Adapter<SalaAdapter.MyViewHolder> {

    private List<Sala> lista;
    private View view;
    private DatabaseReference databaseReference;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome,colegio;
        public ImageButton btdelete;
        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            colegio = (TextView) view.findViewById(R.id.colegio);
            btdelete = (ImageButton) view.findViewById(R.id.btdelete);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

        }
    }

    public SalaAdapter(List<Sala> lista,Context mContext) {
        this.lista = lista;
        this.mContext = mContext;
    }



    @Override
    public SalaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sala, parent, false);
        return new SalaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Sala s = lista.get(position);
        final String key= s.getKey();

        holder.nome.setText(s.getColegio());
        holder.colegio.setText(s.getNome());

        holder.btdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference
                        .child("USUARIOS")
                        .child(Util.retornaNick(mContext))
                        .child("SALAS")
                        .child(key).removeValue();

                Toast.makeText(mContext, "Excluído!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista(){
        lista.clear();
    }


}