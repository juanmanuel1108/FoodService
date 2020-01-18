package com.prototipados.expressfood;


import android.app.Activity;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private final int[] botonesM =  {R.id.btnListado,R.id.btnActual,R.id.btnPerfil};
    private final int[] iluminado = {R.drawable.ic_action_pendientesselec,R.drawable.ic_action_actualselec,R.drawable.ic_action_perfilselec};

    private int boton;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mm = inflater.inflate(R.layout.fragment_menu, container, false);

        boton = -1;

        if(getArguments() != null) {
            boton = getArguments().getInt("botonPul");
        }

        Button botonmenu;

        for(int i = 0; i < botonesM.length; i++)
        {
            botonmenu = (Button) mm.findViewById(botonesM[i]);

            if(boton == i){

                botonmenu.setTextColor(getResources().getColor(R.color.colorPrimary));
                botonmenu.setCompoundDrawablesWithIntrinsicBounds(iluminado[i], 0, 0, 0);

            }

            final int quebot = i;

            botonmenu.setOnClickListener( new View.OnClickListener(){

                public void onClick(View v){

                    Activity estaAc = getActivity();

                    ((ComunicaMenu)estaAc).menu(quebot);

                }

            });
        }

        return mm;
    }

}
