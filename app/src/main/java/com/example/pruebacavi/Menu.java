package com.example.pruebacavi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    public static String CLASS_TAG = MainActivity.class.getSimpleName();
    public Button btValidar;
    public EditText etUsuario, etClave;
    public ProgressBar progressBar2;
    private TextView txError;
    public String NombreUsuario = "Profesor", Password = "AM";
    public int a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btValidar = (Button) findViewById(R.id.btValidar);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        txError = (TextView) findViewById(R.id.txError);


        btValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task1().execute(etUsuario.getText().toString());
            }
        });
    }
    /*private void quienes(String usuario, String clave){

        usuario = etUsuario.getText().toString();
        clave = etUsuario.getText().toString();
        switch(usuario){

            case "Vivi":
                if(clave.equals("1")) {
                    a = 1;
                    Log.d(CLASS_TAG, usuario);
                    Log.d(CLASS_TAG, "clave correcta");

                }else{
                    a = 0;
                    Log.d(CLASS_TAG, "Clave invalida");
                    txError = (TextView)findViewById(R.id.txError);
                    txError.setText("Clave invalida");
                    txError.setVisibility(View.VISIBLE);

                }

            case "Carlitos":

                if(clave.equals("2")){
                    a = 1;

                }else{
                    a = 0;
                    Log.d(CLASS_TAG, "Clave invalida");
                    txError = (TextView)findViewById(R.id.txError);
                    txError.setText("Clave invalida");
                    txError.setVisibility(View.VISIBLE);

                }

            //case "Profesor Barrios":

              /*  if(clave.equals("AM");{
                    a = 1;

                }else{
                    a = 0;
                    Log.d(CLASS_TAG, "Clave invalida");
                    txError = (TextView)findViewById(R.id.txError);
                    txError.setText("Clave invalida");
                    txError.setVisibility(View.VISIBLE);

                }

            default:
                Log.d(CLASS_TAG, "Usuario invalido");
                txError = (TextView)findViewById(R.id.txError);
                txError.setText("Usuario invalido");
                txError.setVisibility(View.VISIBLE);

        }
    } */

    class Task1 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar2.setVisibility(View.VISIBLE);
            txError.setVisibility(View.INVISIBLE);
            btValidar.setEnabled(false);
          /*  String usuario = etUsuario.getText().toString();
            String clave = etClave.getText().toString();

            switch(usuario){

                case "Vivi":
                    if(clave == "asintas") {
                    Intent intent = new Intent(Menu.this, MainActivity.class);
                    intent.putExtra("usuario", etUsuario.getText().toString());
                    startActivity(intent);
                }else{
                        Log.d(CLASS_TAG, "Clave invalida");
                    }

                case "Carlitos":

                    if(clave == "notengocasa"){
                        Intent intent = new Intent(Menu.this, MainActivity.class);
                        intent.putExtra("usuario", etUsuario.getText().toString());
                        startActivity(intent);
                    }else{
                        Log.d(CLASS_TAG, "Clave invalida");
                    }

                case "Profesor Barrios":

                    if(clave == "AM"){
                        Intent intent = new Intent(Menu.this, MainActivity.class);
                        intent.putExtra("usuario", etUsuario.getText().toString());
                        startActivity(intent);
                    }else{
                        Log.d(CLASS_TAG, "Clave invalida");
                    }

                default:
                    Log.d(CLASS_TAG, "Usuario invalido");

            }*/
        }

        @Override
        protected String doInBackground(String... strings) {
            String nombre = etUsuario.getText().toString();
            String contra = etClave.getText().toString();
            a = 0;
            if (nombre.equals(NombreUsuario) && contra.equals(Password)) {
                a = 1;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {

            progressBar2.setVisibility(View.INVISIBLE);
            btValidar.setEnabled(true);
            if (a == 1) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("usuario", etUsuario.getText().toString());
                startActivity(intent);
            } else {
                Log.d(CLASS_TAG, etUsuario.getText().toString() + "\n" + etClave.getText().toString());

                txError.setText("Clave o  Usuario Inválido");
                txError.setVisibility(View.VISIBLE);
            }
            /*switch(usuario){

            case "Vivi":
            if(clave == "asintas") {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("usuario", etUsuario.getText().toString());
                startActivity(intent);
            }else{
                Log.d(CLASS_TAG, "Clave invalida");
                txError = (TextView)findViewById(R.id.txError);
                txError.setText("Clave invalida");
                txError.setVisibility(View.VISIBLE);
            }

            case "Carlitos":

            if(clave == "notengocasa"){
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("usuario", etUsuario.getText().toString());
                startActivity(intent);
            }else{
                Log.d(CLASS_TAG, "Clave invalida");
                txError = (TextView)findViewById(R.id.txError);
                txError.setText("Clave invalida");
                txError.setVisibility(View.VISIBLE);
            }

            case "Profesor Barrios":

            if(clave == "AM"){
                Intent intent = new Intent(Menu.this, MainActivity.class);
                intent.putExtra("usuario", etUsuario.getText().toString());
                startActivity(intent);
            }else{
                Log.d(CLASS_TAG, "Clave invalida");
                txError = (TextView)findViewById(R.id.txError);
                txError.setText("Clave invalida");
                txError.setVisibility(View.VISIBLE);
            }

            default:
            Log.d(CLASS_TAG, "Usuario invalido");
                txError = (TextView)findViewById(R.id.txError);
                txError.setText("Usuario invalido");
                txError.setVisibility(View.VISIBLE);

        }*/
        }


    }
}