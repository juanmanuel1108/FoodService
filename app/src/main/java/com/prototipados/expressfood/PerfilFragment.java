package com.prototipados.expressfood;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    TextView nombre,telefono,documeto,estado;
    Button cerrar;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        if(v != null)
        {
            nombre = v.findViewById(R.id.lblNombreRepar);
            telefono = v.findViewById(R.id.lblTelefonoRepar);
            documeto = v.findViewById(R.id.lblDocumentoRepar);
            estado = v.findViewById(R.id.lblEstadoRepartidor);

            cargarpreferencias();

            cerrar = v.findViewById(R.id.btnCerrarS);
            cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Alerta");

                    dialog.setMessage("¿Desea cerrar sesión?");

                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferences.edit();

                            editor.clear().apply();

                            editor.commit();

                            getActivity().finish();
                        }
                    });

                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    dialog.show();
                }
            });
        }

        return v;
    }

    private void cargarpreferencias() {

        SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);

        nombre.setText( preferences.getString("nombre","no existe sesión"));
        telefono.setText( preferences.getString("telefono","no existe sesión"));
        documeto.setText( preferences.getString("cedula","no existe sesión"));
        int es = preferences.getInt("estado", -1);

        if(es == 0)
        {
            estado.setText("Disponible");
            estado.setTextColor(Color.GREEN);
        }
        else if(es == 1)
        {
           estado.setText("Ocupado");
           estado.setTextColor(Color.RED);
        }

    }

}
