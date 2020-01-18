package com.prototipados.expressfood;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity implements ComunicaMenu {

    Fragment[] misFrag;
    boolean atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        misFrag = new Fragment[3];
        misFrag[0] = new ListaFragment();
        misFrag[1] = new ActualFragment();
        misFrag[2] = new PerfilFragment();

        menu(0);
    }

    @Override
    public void menu(int b) {

        if (b == 0) {
            atras = true;
        } else {
            atras = false;
        }

        FragmentManager manejador = getFragmentManager();

        FragmentTransaction trasac = manejador.beginTransaction();

        Fragment menu_iluminado = new MenuFragment();

        Bundle datos = new Bundle();

        datos.putInt("botonPul", b);

        menu_iluminado.setArguments(datos);

        trasac.replace(R.id.Fmenu, menu_iluminado);

        trasac.replace(R.id.vistaEstado, new EstadoFragment());

        trasac.replace(R.id.vistasRe, misFrag[b]);

        trasac.addToBackStack(null);

        trasac.commit();
    }

    @Override public void onBackPressed() {

    }

}
