package com.prototipados.expressfood;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActualFragment extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener {


    public final static String DIR_LECTURA = "http://xamarinejemplos.onlinewebshop.net/pedidoActual.php?id=";
    public final static String DIR_CAMBIO = "http://xamarinejemplos.onlinewebshop.net/CambiarEstadoPe.php?estado=2&id=";

    TextView estado, nombre, direccion, costo, envio, total;
    Button entregar;
    RecyclerView list;
    int idPe;

    AdapterProd adapter;

    private RequestQueue requestQueue;
    private JsonRequest jsonRequest;

    public ActualFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_actual, container, false);

        estado = v.findViewById(R.id.Estado);
        nombre = v.findViewById(R.id.Nombre);
        direccion = v.findViewById(R.id.Direccion);
        costo = v.findViewById(R.id.Costo);
        envio = v.findViewById(R.id.Envio);
        total = v.findViewById(R.id.Total);
        entregar = v.findViewById(R.id.btnCambioPedido);
        list = v.findViewById(R.id.rcListaProd);

        ArrayList<Producto> lista = new ArrayList<>();
        adapter = new AdapterProd(lista);

        list.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(llm);

        SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);

        int id = preferences.getInt("id",-1);


        requestQueue = Volley.newRequestQueue(getActivity());
        jsonRequest = new JsonObjectRequest(Request.Method.GET,DIR_LECTURA + id,null,this,this);
        requestQueue.add(jsonRequest);

        entregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Producto> p = new ArrayList<>();
                adapter = new AdapterProd(p);
                list.setAdapter(adapter);

                requestQueue = Volley.newRequestQueue(getActivity());
                jsonRequest = new JsonObjectRequest(Request.Method.GET,DIR_CAMBIO + idPe,null,ActualFragment.this,ActualFragment.this);
                requestQueue.add(jsonRequest);

                estado.setText("");
                nombre.setText("");
                direccion.setText("");
                costo.setText("");
                envio.setText("");
                total.setText("");

                Toast.makeText(getActivity(), "Pedido Entregado", Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putInt("estado",0);

                editor.commit();

                int idRepartidor = preferences.getInt("id",-1);

                jsonRequest = new JsonObjectRequest(Request.Method.GET, "http://xamarinejemplos.onlinewebshop.net/CambiarEstadoRepar.php?estado=0&id=" + idRepartidor, null, ActualFragment.this, ActualFragment.this);
                requestQueue.add(jsonRequest);

                FragmentManager manejador = getFragmentManager();

                FragmentTransaction trasac = manejador.beginTransaction();

                Fragment menu_iluminado = new MenuFragment();

                Bundle datos = new Bundle();

                datos.putInt("botonPul", 0);

                menu_iluminado.setArguments(datos);

                trasac.replace(R.id.Fmenu, menu_iluminado);

                trasac.replace(R.id.vistasRe, new ListaFragment());

                trasac.replace(R.id.vistaEstado, new EstadoFragment());

                trasac.addToBackStack(null);

                trasac.commit();

            }
        });

        return v;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("error de response : " + error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            ArrayList<Producto> lista = new ArrayList<>();

            JSONArray jsonArray = response.getJSONArray("datos");
            System.out.println(jsonArray);
            double CostoEnvio = 0;
            double costoPlatos = 0;

            for (int i = 0; i < jsonArray.length(); i++)
            {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                idPe = jsonObject.getInt("idPe");

                nombre.setText(jsonObject.getString("cliente"));
                direccion.setText(jsonObject.getString("direccion"));

                int ocupado = jsonObject.getInt("estado");


                    if(ocupado == 0)
                    {
                         estado.setText("Pendiente");
                    }
                    if(ocupado == 1)
                    {
                        estado.setText("En camino");
                        estado.setTextColor(Color.GREEN);
                    }
                    if(ocupado == 2)
                    {
                        estado.setText("Entregado");
                    }



                CostoEnvio = jsonObject.getDouble("envio");

                double costo = jsonObject.getDouble("precio");

                int cantidad = jsonObject.getInt("cantidad");

                costoPlatos += costo * cantidad;

                String plato = jsonObject.getString("plato");


                Producto p = new Producto(plato,cantidad,costo, costo * cantidad);
                lista.add(p);
            }

            envio.setText(CostoEnvio + "");
            costo.setText(costoPlatos + "");
            double totals = costoPlatos + CostoEnvio;
            total.setText(totals + "");
            adapter = new AdapterProd(lista);

            list.setAdapter(adapter);



        } catch (JSONException e)
        {
            System.out.println("error del try : " + e.getMessage());
        }

    }
}
