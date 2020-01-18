package com.prototipados.expressfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<Pedido> listado;
    View.OnClickListener listener;

    public AdapterPedidos(ArrayList<Pedido> lista){ listado = lista; }

    @Override
    public void onClick(View view) {

        if(listener != null)
        {
            listener.onClick(view);
        }

    }

    public void darListener(View.OnClickListener lis){ listener = lis; }

    @NonNull
    @Override
    public AdapterPedidos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = null;

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedidos, null, false);

            v.setOnClickListener(this);

        return new ViewHolderDatos(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPedidos.ViewHolderDatos holder, int position) {
        holder.direccion.setText(listado.get(position).getDireccion());
        holder.cliente.setText(listado.get(position).getNombreC());
        holder.id.setText(String.valueOf(listado.get(position).getId()));
        holder.valor.setText(String.valueOf(listado.get(position).getValor()));
    }

    @Override
    public int getItemCount() {
        return listado.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView direccion, cliente, valor, id;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.lblId);

            id.setVisibility(View.INVISIBLE);

            direccion = itemView.findViewById(R.id.lblDireccion);
            cliente = itemView.findViewById(R.id.lblCliente);
            valor = itemView.findViewById(R.id.lblValor);


        }
    }
}
