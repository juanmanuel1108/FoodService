package com.prototipados.expressfood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.ArrayList;

public class AdapterProd extends RecyclerView.Adapter<AdapterProd.ViewHolder> {

    ArrayList<Producto> lista;

    public AdapterProd (ArrayList<Producto> param){
        lista = param;
    }

    @NonNull
    @Override
    public AdapterProd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prod, null, false);

        return new AdapterProd.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProd.ViewHolder holder, int position) {
        holder.cantidad.setText(String.valueOf(lista.get(position).getCantidad()));
        holder.producto.setText(lista.get(position).getNombre());
        holder.subtotal.setText(String.valueOf(lista.get(position).getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView producto, cantidad, subtotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            producto = itemView.findViewById(R.id.lblNomProd);
            cantidad = itemView.findViewById(R.id.lblCantProd);
            subtotal = itemView.findViewById(R.id.lblSubTot);

        }
    }
}
