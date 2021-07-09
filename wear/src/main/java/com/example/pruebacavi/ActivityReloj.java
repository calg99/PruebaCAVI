package com.example.pruebacavi;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityReloj extends WearableActivity {

    private TextView mTextView;
    public static ImageView acoff,acon,tvoff,tvon,lcoff,lcon,lcooff,lcoon,lsoff,lson;
    public static String CLASS_TAG = ActivityReloj.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reloj);

        mTextView = (TextView) findViewById(R.id.text);

        acoff = (ImageView) findViewById(R.id.acoff); //inicadores de botones
        acon = (ImageView) findViewById(R.id.acon); //a es sinonimo de azul
        tvoff = (ImageView) findViewById(R.id.tvoff); //b es sinónimo de blanco
        tvon = (ImageView) findViewById(R.id.tvon);
        lcoff = (ImageView) findViewById(R.id.lcoff);
        lcon = (ImageView) findViewById(R.id.lcon);
        lcooff = (ImageView) findViewById(R.id.lcooff);
        lcoon = (ImageView) findViewById(R.id.lcoon);
        lsoff = (ImageView) findViewById(R.id.lsoff);
        lson = (ImageView) findViewById(R.id.lson);

        // Enables Always-on
        // setAmbientEnabled();

        String wearmessage = getIntent().getStringExtra("message");
        Log.d(CLASS_TAG, "mensaje recibido: " +  wearmessage);

        indicador(wearmessage);
        //aquí va cambio de luces

    }

    //Respuestas según Servidor, control de luces
    private void indicador(String r) {

        switch (r) {

            case "0": //ac prendido
                acon.setVisibility(View.VISIBLE);
                acoff.setVisibility(View.INVISIBLE);
                Log.d(CLASS_TAG, "ac prendido");
                break;

            case "1": //ac apagado
                acon.setVisibility(View.INVISIBLE);
                acoff.setVisibility(View.VISIBLE);
                Log.d(CLASS_TAG, "ac apagado");
                break;

            case "2": //Tv prendido
                Log.d(CLASS_TAG, "Tv prendido");
                tvon.setVisibility(View.VISIBLE);
                tvoff.setVisibility(View.INVISIBLE);
                break;

            case "3": //Tv apagado
                Log.d(CLASS_TAG, "Tv apagado");
                tvon.setVisibility(View.INVISIBLE);
                tvoff.setVisibility(View.VISIBLE);
                break;

            case "4": //lcu prendido
                Log.d(CLASS_TAG, "Luz del cuarto prendido");
                lcon.setVisibility(View.VISIBLE);
                lcoff.setVisibility(View.INVISIBLE);
                break;

            case "5": //Lcu apagado
                Log.d(CLASS_TAG, "Luz del cuarto apagado");
                lcon.setVisibility(View.INVISIBLE);
                lcoff.setVisibility(View.VISIBLE);
                break;

            case "6": //lco prendido
                Log.d(CLASS_TAG, "Luz de la cocina prendido");
                lcoon.setVisibility(View.VISIBLE);
                lcooff.setVisibility(View.INVISIBLE);
                break;

            case "7": //Lco apagado
                Log.d(CLASS_TAG, "Luz de la cocina apagado");
                lcoon.setVisibility(View.INVISIBLE);
                lcoff.setVisibility(View.VISIBLE);
                break;

            case "8": //lco prendido
                Log.d(CLASS_TAG, "Luz de la sala prendido");
                lson.setVisibility(View.VISIBLE);
                lsoff.setVisibility(View.INVISIBLE);
                break;

            case "9": //Lco apagado
                Log.d(CLASS_TAG, "Luz de la sala apagado");
                lson.setVisibility(View.INVISIBLE);
                lsoff.setVisibility(View.VISIBLE);
                break;

            default:
                Log.d(CLASS_TAG, "No reconoce el codigo");
                break;
        }

    }

}

