package com.example.pruebacavi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.GoalRow;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
    // WEAR
    //GoogleApiClient googleApiClient = null; // Para conectividad con wear
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";
    public static final String TAG = "MyDataMAP.......";
    // TCP y UDP
    public String b, resp = "";
    public String wearmessage;
    public int a;
    public Button btAC, btTv, btLcuarto, btLcocina, btLsala;
    public static String CLASS_TAG = MainActivity.class.getSimpleName();
    public int Identificador = 0;
    public static ImageView aa, ab, ac, ad, ae, ba, bb, bc, bd, be;
    private ProgressBar barraProgreso;
    private Switch cambioTCP;
    private TextView tvMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMensaje = (TextView) findViewById(R.id.txMensaje);
        String nombreUsuario = getIntent().getStringExtra("usuario");
        tvMensaje.setText("Bienvenido(a) " + nombreUsuario);

        aa = (ImageView) findViewById(R.id.aa); //inicadores de botones
        ab = (ImageView) findViewById(R.id.ab); //a es sinonimo de azul
        ac = (ImageView) findViewById(R.id.ac); //b es sinónimo de blanco
        ad = (ImageView) findViewById(R.id.ad);
        ae = (ImageView) findViewById(R.id.ae);
        ba = (ImageView) findViewById(R.id.ba);
        bb = (ImageView) findViewById(R.id.bb);
        bc = (ImageView) findViewById(R.id.bc);
        bd = (ImageView) findViewById(R.id.bd);
        be = (ImageView) findViewById(R.id.be);

        cambioTCP = (Switch) findViewById(R.id.cambioTCP); //cambio de switch
        barraProgreso = (ProgressBar) findViewById(R.id.barraProgreso);
        Button btAC = (Button) findViewById(R.id.btAC); //se asocia el id con el boton
        btAC.setOnClickListener(this); //permite escuchar, es decir que puedas presionar
        Button btTv = (Button) findViewById(R.id.btTv);
        btTv.setOnClickListener(this);
        Button btLcuarto = (Button) findViewById(R.id.btLcuarto);
        btLcuarto.setOnClickListener(this);
        Button btLcocina = (Button) findViewById(R.id.btLcocina);
        btLcocina.setOnClickListener(this);
        Button btLsala = (Button) findViewById(R.id.btLsala);
        btLsala.setOnClickListener(this);

        //WEAR
       /* GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(Wearable.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        googleApiClient = builder.build();*/
    }


    //WEAR

    /*
        @Override
        protected void onStart() {
            super.onStart();
            googleApiClient.connect();
        }

        @Override
        protected void onStop() {

            if (googleApiClient != null && googleApiClient.isConnected()) {
                googleApiClient.disconnect();
            }

            super.onStop();
        }

    */
    //Respuestas según Servidor, control de luces
    private void indicador(String r) {

        switch (r) {

            case "0": //ac prendido
                aa.setVisibility(View.VISIBLE);
                ba.setVisibility(View.INVISIBLE);
                Log.d(CLASS_TAG, "ac prendido");
                break;

            case "1": //ac apagado
                aa.setVisibility(View.INVISIBLE);
                ba.setVisibility(View.VISIBLE);
                Log.d(CLASS_TAG, "ac apagado");
                break;

            case "2": //Tv prendido
                Log.d(CLASS_TAG, "Tv prendido");
                ab.setVisibility(View.VISIBLE);
                bb.setVisibility(View.INVISIBLE);
                break;

            case "3": //Tv apagado
                Log.d(CLASS_TAG, "Tv apagado");
                ab.setVisibility(View.INVISIBLE);
                bb.setVisibility(View.VISIBLE);
                break;

            case "4": //lcu prendido
                Log.d(CLASS_TAG, "Luz del cuarto prendido");
                ac.setVisibility(View.VISIBLE);
                bc.setVisibility(View.INVISIBLE);
                break;

            case "5": //Lcu apagado
                Log.d(CLASS_TAG, "Luz del cuarto apagado");
                ac.setVisibility(View.INVISIBLE);
                bc.setVisibility(View.VISIBLE);
                break;

            case "6": //lco prendido
                Log.d(CLASS_TAG, "Luz de la cocina prendido");
                ad.setVisibility(View.VISIBLE);
                bd.setVisibility(View.INVISIBLE);
                break;

            case "7": //Lco apagado
                Log.d(CLASS_TAG, "Luz de la cocina apagado");
                ad.setVisibility(View.INVISIBLE);
                bd.setVisibility(View.VISIBLE);
                break;

            case "8": //lco prendido
                Log.d(CLASS_TAG, "Luz de la sala prendido");
                ae.setVisibility(View.VISIBLE);
                be.setVisibility(View.INVISIBLE);
                break;

            case "9": //Lco apagado
                Log.d(CLASS_TAG, "Luz de la sala apagado");
                ae.setVisibility(View.INVISIBLE);
                be.setVisibility(View.VISIBLE);
                break;

            default:
                Log.d(CLASS_TAG, "No reconoce el codigo");
                break;
        }

    }

    @Override // qué sucede cuando presionas un boton, y se envía valor a tareas asíncronas
    public void onClick(View view) {

        if (cambioTCP.isChecked()) {
            Identificador = 1;
        } else {
            Identificador = 0;
        }

        a = view.getId();
        switch (a) {
            case R.id.btAC:
                b = "1";
                Log.d(CLASS_TAG, "Se logro 1");

                //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                new SendTask(Identificador).execute(b);
                break;

            case R.id.btTv:
                b = "2";
                Log.d(CLASS_TAG, "Se logro 2");

                //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                new SendTask(Identificador).execute(b);
                break;

            case R.id.btLcuarto:
                b = "3";
                Log.d(CLASS_TAG, "Se logro 3");

                //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                new SendTask(Identificador).execute(b);
                break;

            case R.id.btLcocina:
                b = "4";
                Log.d(CLASS_TAG, "Se logro 4");

                //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                new SendTask(Identificador).execute(b);
                break;

            case R.id.btLsala:
                b = "5";
                Log.d(CLASS_TAG, "Se logro 5");

                //Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                new SendTask(Identificador).execute(b);
                break;
        }

    }


    //MÉTODOS WEAR
/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sendMessage();
    }

    public void sendMessage() {

        new SendMessageToDataLayer(WEARABLE_DATA_PATH, wearmessage).start();
    }

    @Override
    public void onConnectionSuspended(int i) {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/

    private class SendTask extends AsyncTask<String, Void, String> {


        String result;
        public int iden;

        public SendTask(int identi) {
            this.iden = identi;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override  //tareas asíncronas
        protected void onPreExecute() {
            super.onPreExecute();
            String m = "Triste en Venezuela";
            // Seguridad sec = new Seguridad();
            //sec.addKey("pepe");
            //m = sec.encriptar(m);
            // Log.d(CLASS_TAG, "Encriptado "+m);
            //m = sec.desencriptar(m);
            //Log.d(CLASS_TAG, "Desencriptado "+m);
            barraProgreso.setVisibility(View.VISIBLE); //barra de progreso
            Log.d(CLASS_TAG, "Identificador " + iden);
            //inicializo unprogressbar
        }

        @RequiresApi(api = Build.VERSION_CODES.O) //proceso de comunicación
        @Override
        protected String doInBackground(String... string) {

            //Seguridad sec = new Seguridad();
            String message = string[0];
            //String resp = string[0];
            Socket s = null;
            DatagramSocket p = null;
            DatagramPacket f;

            //BufferedReader br;
            //DataOutputStream out = null;
            //DataInputStream in = null;
            //InputStreamReader in;
            //PrintWriter pw = null, pw2 = null;
            //DataInputStream dis;*/
/*
            ///Todo el proceso de com

            if (this.iden == 0) {

                //  todo el proceso TCP
                String mensaje;
                try {

                    s = new Socket("192.168.92.43", 5000);

                    DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
                    BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));

                    //sec.addKey("pepe");
                    //outToServer.writeBytes(sec.encriptar(message)  + '\n');

                    message = Base64.getEncoder().encodeToString(message.getBytes());
                    outToServer.writeBytes(message);

                    Log.d(CLASS_TAG, "Despues de mandar");
                    resp = inFromServer.readLine();
                    byte[] respb = Base64.getDecoder().decode(resp);
                    Log.d(CLASS_TAG, "Encriptado "+resp);
                    //resp = sec.desencriptar(resp);
                    Log.d(CLASS_TAG, "Desencriptado "+resp);
                    resp = new String(respb);
                    s.close();

                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }

                    /*pw = new PrintWriter(s.getOutputStream());

             //       pw.write(message);
            //        pw.flush();
             //       pw.close();


                   /* in = new DataInputStream(s.getInputStream());
                    String st = in.readLine();

                    /*br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    resp = br.readLine();
                    in.close();

                    in = new InputStreamReader(s.getInputStream());
                    br = new BufferedReader(in);
                    resp = br.readLine();
                    in.close();*/

            Log.d(CLASS_TAG, "respuesta del servidor es:" + resp);

                   /* br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    resp = br.readLine();

                   /*try
                    {
                        s.close();
                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }

                }
                catch(IOException i)
                {
                System.out.println(i);
                 }


                Log.d(CLASS_TAG, "Proceso completado ");



             }else{

                    //todo el proceso Udp

                    try {
                        p = new DatagramSocket();
                        InetAddress severAddr = InetAddress.getByName("192.168.92.43");
                        Log.d(CLASS_TAG, "UDP 1");
                        message = Base64.getEncoder().encodeToString(message.getBytes());
                        f = new DatagramPacket(message.getBytes(), message.length(), severAddr, 5001);
                        Log.d(CLASS_TAG, "UDP 2");
                        p.send(f);
                        Log.d(CLASS_TAG, "Se envió");
                        p.receive(f);
                        Log.d(CLASS_TAG, "largo " + f.getLength());
                        resp = new String(f.getData(), 0, f.getLength());
                        byte[] respb = Base64.getDecoder().decode(resp);
                        resp = new String(respb);
                        Log.d(CLASS_TAG, "Se recibió: "  + resp);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(CLASS_TAG, "UDP Ready");
                    p.close();
                }

         */
            resp = "1";
            wearmessage = resp;
            Log.d(CLASS_TAG, "Por enviar");
            //sendMessage();
            Log.d(CLASS_TAG, "Enviado");
            result = "";

            return result;
        }


        @Override
        protected void onPostExecute (String s){
            super.onPostExecute(s);
            indicador(resp);
            barraProgreso.setVisibility(View.INVISIBLE);
        }
    }
/*

    public class SendMessageToDataLayer extends Thread {
        String path;
        String message;

        public SendMessageToDataLayer(String path, String message) {
            this.path = path;
            this.message = message;

        }

        @Override
        public void run() {
            NodeApi.GetConnectedNodesResult nodesList = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
            for(Node node : nodesList.getNodes() ){
                MessageApi.SendMessageResult messageResult = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(),path,message.getBytes()).await();

                if(messageResult.getStatus().isSuccess()){

                    Log.v(TAG,"Message: successfully sent to"+ node.getDisplayName());

                }else{
                    Log.v(TAG,"Message: Error while sending Message");
                }
            }

        }    */
}














