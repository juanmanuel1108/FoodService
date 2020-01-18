package com.prototipados.expressfood;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstadoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    public final static String DIR_ESTADO = "http://xamarinejemplos.onlinewebshop.net/estadoRepartidor.php?id=";

    TextView estadoAc;
    FloatingActionButton estado1,estado2;

    private RequestQueue requestQueue;
    private JsonRequest jsonRequest;

    public EstadoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_estado, container, false);

        estadoAc = v.findViewById(R.id.lblEstadoActual);
        estado1 = v.findViewById(R.id.flbEstadoAc);
        estado2 = v.findViewById(R.id.flbEstadoAc2);

        SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);
        int id = preferences.getInt("id",-1);

        requestQueue = Volley.newRequestQueue(getActivity());
        jsonRequest = new JsonObjectRequest(Request.Method.GET,DIR_ESTADO + id,null,this,this);
        requestQueue.add(jsonRequest);

        return v;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("error de response");
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("datos");

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int es = jsonObject.getInt("estado");

            if(es == 0)
            {
                estadoAc.setText("");
                estadoAc.setText("Disponible");
                estado1.hide();
                estado2.show();

                SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);
                Editor editor = preferences.edit();

                editor.putInt("estado",es);

                editor.commit();

                int idRepartidor = preferences.getInt("id",-1);


            }
            else if(es == 1)
            {
                estadoAc.setText("");
                estadoAc.setText("Ocupado");
                estado1.show();
                estado2.hide();

                SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);
                Editor editor = preferences.edit();

                editor.putInt("estado",es);

                editor.commit();

            }


        } catch (JSONException e)
        {
            System.out.println("error del try");
        }
    }
}
