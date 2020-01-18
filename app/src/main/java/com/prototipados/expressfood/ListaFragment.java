package com.prototipados.expressfood;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
public class ListaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    public final static String DIR_LECTURA = "http://xamarinejemplos.onlinewebshop.net/Pedido.php";

    RecyclerView recy;
    AdapterPedidos adapter;

    private RequestQueue requestQueue;
    private JsonRequest jsonRequest;
    private JsonRequest jsonRequest1;


    ArrayList<Pedido> listado = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lista, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        jsonRequest = new JsonObjectRequest(Request.Method.GET,DIR_LECTURA,null,this,this);
        requestQueue.add(jsonRequest);

        if(v != null){
            recy = v.findViewById(R.id.recyPedidos);

            adapter = new AdapterPedidos(listado);

            adapter.darListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Selecciono un pedido", Toast.LENGTH_SHORT).show();
                }
            });

            recy.setAdapter(adapter);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recy.setLayoutManager(llm);
        }

        return v;

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("error de response: " + error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            listado = new ArrayList<>();

            JSONArray jsonArray = response.getJSONArray("datos");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String direccion = jsonObject.getString("direccion");
                String cliente = jsonObject.getString("nombre");
                double valor = jsonObject.getDouble("valor");

                Pedido a = new Pedido(id, cliente, direccion, valor);

                listado.add(a);
            }

            adapter = new AdapterPedidos(listado);

            adapter.darListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("datosR", Context.MODE_PRIVATE);

                    int idRepartidor = preferences.getInt("id",-1);
                    int idPedido = listado.get(recy.getChildAdapterPosition(view)).getId();
                    int estado = preferences.getInt("estado", -1);

                    if(estado == 0) {

                        Toast.makeText(getActivity(), "Selecciono el pedido: " + listado.get(recy.getChildAdapterPosition(view)).getId(), Toast.LENGTH_SHORT).show();

                        jsonRequest1 = new JsonObjectRequest(Request.Method.GET, "http://xamarinejemplos.onlinewebshop.net/asignarPedido.php?idP=" + idPedido + "&idR=" + idRepartidor, null, ListaFragment.this, ListaFragment.this);
                        requestQueue.add(jsonRequest1);

                        jsonRequest1 = new JsonObjectRequest(Request.Method.GET, "http://xamarinejemplos.onlinewebshop.net/CambiarEstadoRepar.php?estado=1&id=" + idRepartidor, null, ListaFragment.this, ListaFragment.this);
                        requestQueue.add(jsonRequest1);

                        jsonRequest1 = new JsonObjectRequest(Request.Method.GET, "http://xamarinejemplos.onlinewebshop.net/CambiarEstadoPe.php?estado=1&id=" + idPedido, null, ListaFragment.this, ListaFragment.this);
                        requestQueue.add(jsonRequest1);

                        requestQueue = Volley.newRequestQueue(getActivity());
                        jsonRequest = new JsonObjectRequest(Request.Method.GET, DIR_LECTURA, null, ListaFragment.this, ListaFragment.this);
                        requestQueue.add(jsonRequest);

                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putInt("estado", 1);

                        editor.commit();

                        FragmentManager manejador = getFragmentManager();

                        FragmentTransaction trasac = manejador.beginTransaction();

                        Fragment menu_iluminado = new MenuFragment();

                        Bundle datos = new Bundle();

                        datos.putInt("botonPul", 1);

                        menu_iluminado.setArguments(datos);

                        trasac.replace(R.id.Fmenu, menu_iluminado);

                        trasac.replace(R.id.vistasRe, new ActualFragment());

                        trasac.replace(R.id.vistaEstado, new EstadoFragment());

                        trasac.addToBackStack(null);

                        trasac.commit();
                    }
                    else if(estado == 1)
                    {
                        Toast.makeText(getActivity(), "Ya tiene un pedido en curso", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Error al seleccionar pedido", Toast.LENGTH_SHORT).show();
                    }

                }


            });

            recy.setAdapter(adapter);

        } catch (JSONException e)
        {
            System.out.println("error del try");
        }
    }

}
