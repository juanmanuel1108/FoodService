package com.prototipados.expressfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    public final static String DIR_LECTURA = "http://xamarinejemplos.onlinewebshop.net/Repartidores.php";

    private EditText txtUs, txtContra;
    private RequestQueue requestQueue;
    private JsonRequest jsonRequest;
    private Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUs = findViewById(R.id.txtUsuarioReg);
        txtContra = findViewById(R.id.txtContra);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegisterM);
    }

    public void vistaResgistro (View v)
    {
        register.setEnabled(false);
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    public void vistaListado (View v)
    {
        login.setEnabled(false);

        String us = txtUs.getText().toString();
        String c = txtContra.getText().toString();

        if(!us.isEmpty() && !c.isEmpty())
        {
            requestQueue = Volley.newRequestQueue(this);
            jsonRequest = new JsonObjectRequest(Request.Method.GET,DIR_LECTURA + "?usuario=" + us + "&contrasena=" + c,null,this,this);
            requestQueue.add(jsonRequest);
        }
        else
        {
            Toast.makeText(this, "Debe llenar todos los capos", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("error de response");
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String men = response.getJSONArray("mensaje").get(0).toString();

            JSONArray jsonArray = response.getJSONArray("mensaje");
            Repartidor a = null;

            if(!men.equals("noPaso")) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String cedula = jsonObject.getString("cedula");
                    String telefono = jsonObject.getString("telefono");
                    int estado = jsonObject.getInt("estado");
                    String email = jsonObject.getString("email");
                    String nombre = jsonObject.getString("nombre");
                    a = new Repartidor(id, cedula, telefono, estado, email, nombre);
                }

                SharedPreferences preferences = getSharedPreferences("datosR", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.clear().apply();

                editor.putInt("id",a.getId());
                editor.putString("cedula", a.getDocumento());
                editor.putString("telefono", a.getTelefono());
                editor.putInt("estado", a.getEstado());
                editor.putString("email", a.getEmail());
                editor.putString("nombre", a.getNombre());

                editor.commit();

            }

            if(a != null)
            {
                txtContra.setText("");
                txtUs.setText("");

                Toast.makeText(this, "Inicio sesión correctamente", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, ListActivity.class);
                startActivity(i);
            }
            else if(a == null)
            {
                Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
            }else
            {
                Toast.makeText(this, "Error al iniciar sesión ", Toast.LENGTH_SHORT).show();
                login.setEnabled(true);
            }

            login.setEnabled(true);


        } catch (JSONException e)
        {
            System.out.println(e.getMessage());
            login.setEnabled(true);
        }
    }
}
