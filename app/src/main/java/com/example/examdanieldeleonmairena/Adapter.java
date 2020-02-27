package com.example.examdanieldeleonmairena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PersonasViewHolder> {

    ArrayList<TodoListt> listaContactos;

    public Adapter(ArrayList<TodoListt> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @Override
    public PersonasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null,false);
        return new PersonasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonasViewHolder holder, int position) {
        holder.texto.setText(listaContactos.get(position).getTexto());
        holder.fecha.setText(listaContactos.get(position).getFecha_ini());
        if(listaContactos.get(position).getCheckbox()==0){
            holder.chek.setText("No Complete");
        }else{
            holder.chek.setText("Complete");
        }
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class PersonasViewHolder extends RecyclerView.ViewHolder {

        TextView texto,fecha,chek;

        public PersonasViewHolder(View itemView) {
            super(itemView);
            texto = (TextView) itemView.findViewById(R.id.text);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            chek = (TextView) itemView.findViewById(R.id.checkBox);
        }
    }

}
