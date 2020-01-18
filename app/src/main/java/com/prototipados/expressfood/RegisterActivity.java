package com.prototipados.expressfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {



    public  final static String direccionWeb ="http://xamarinejemplos.onlinewebshop.net/RegistrarRepar.php";

    private EditText nombre,cedula,email,contrasena,telefono,usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        nombre = findViewById(R.id.txtNombre);
        cedula = findViewById(R.id.txtCedula);
        email = findViewById(R.id.txtEmail);
        contrasena = findViewById(R.id.txtContrasenaReg);
        telefono = findViewById(R.id.txtTelefono);
        usuario = findViewById(R.id.txtUsuarioReg);



    }

    public void Registrarse (View v){

        String Nombre = nombre.getText().toString();
        String Cedula = cedula.getText().toString();
        String Constrasena = contrasena.getText().toString();
        String Telefono =telefono.getText().toString();
        String Usuario = usuario.getText().toString();
        String Email = email.getText().toString();

        if(!Nombre.isEmpty() && !Cedula.isEmpty() && !Constrasena.isEmpty() && !Telefono.isEmpty() && !Usuario.isEmpty() && !Email.isEmpty())
        {
            AsyncHttpClient cliente = new AsyncHttpClient();
            RequestParams parametro = new RequestParams();


            ArrayList<HashMap<String, String>> registro = new ArrayList<>();
            HashMap<String, String> mapa =  new HashMap<>();
            mapa.put("nombre", Nombre);
            mapa.put("cedula", Cedula);
            mapa.put("contrasena", Constrasena);
            mapa.put("telefono", Telefono);
            mapa.put("usuario", Usuario);
            mapa.put("email", Email);

            mapa.put("estado","0");

            Gson gson = new GsonBuilder().create();
            registro.add(mapa);
            String json = gson.toJson(registro);

            parametro.put("RegistrarRepar",json);


            cliente.post(direccionWeb, parametro, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    nombre.setText("");
                    cedula.setText("");
                    telefono.setText("");
                    usuario.setText("");
                    email.setText("");
                    contrasena.setText("");
                    nombre.requestFocus();

                    Toast.makeText(RegisterActivity.this, "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();

                    volver();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(RegisterActivity.this,"error",Toast.LENGTH_LONG).show();
                }
            });

        }else{
            Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void volver(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }





}
