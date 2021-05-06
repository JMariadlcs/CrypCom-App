package com.crypcomapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Chain;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class FavouriteActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Obtener referencia al TextView que visualizara el saludo
        new Prices().execute(); //hacemos execute


    }



    private class Prices extends AsyncTask<View, Void, double[]> {

        @Override
        protected double[] doInBackground(View... urls) {
            double[] prices = new double[4];
            //prices: 0. Bitcoin    1. Ethereum     2. Chainlink    3. Cardano

            //print the call in the console
            System.out.println("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=USD");

            // make Call to the url

            prices[0] = makeCall("https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=USD");
            prices[1] = makeCall("https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=USD");
            prices[2] = makeCall("https://api.coingecko.com/api/v3/simple/price?ids=chainlink&vs_currencies=USD");
            prices[3] = makeCall("https://api.coingecko.com/api/v3/simple/price?ids=cardano&vs_currencies=USD");


            return prices;
        }

        @Override
        protected void onPreExecute() {
            /*
            String sharedPrefFile = "com.crypcomappcalculator";
            SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

            // Obtenemos un editor de preferencias
            SharedPreferences.Editor editor = mPreferences.edit();
            // we can start a progress bar here

            editor.putString("Bitcoin", "0");*/
        }

        @Override
        protected void onPostExecute(double[] prices) {

            // Obtener referencia al TextView que visualizara el saludo

            TextView bitcoincalculated = (TextView)findViewById(R.id.bitcoincalculated);
            TextView ethereumcalculated = (TextView)findViewById(R.id.ethereumcalculated);
            TextView chainlinkcalculated = (TextView)findViewById(R.id.chainlinkcalculated);
            TextView cardanocalculated = (TextView)findViewById(R.id.cardanocalculated);

            // Recuperamos la informacion salvada en la preferencia
            String sharedPrefFile = "com.crypcomappcalculator";
            SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


            String Bitcoin = mPreferences.getString("Bitcoin", "0");
            String Ethereum = mPreferences.getString("Ethereum", "0");
            String Chainlink = mPreferences.getString("Chainlink", "0");
            String Cardano = mPreferences.getString("Cardano", "0");

            // Construimos el saludo a mostrar
            try{
                if(bitcoincalculated!=null || !bitcoincalculated.equals("")) {
                    String b = Bitcoin + " BTC = " +  (double)Math.round(prices[0]*Double.parseDouble(Bitcoin) * 1000d) / 1000d + "$";

                    bitcoincalculated.setText(b);
                }
            }catch(NumberFormatException e){
                bitcoincalculated.setText("Error");
            }
            try {
                if (ethereumcalculated != null || !ethereumcalculated.equals("")) {
                    String e = Ethereum + " ETH = " + (double) Math.round(prices[1] * Double.parseDouble(Ethereum) * 1000d) / 1000d + "$";
                    ethereumcalculated.setText(e);
                }
            }catch(NumberFormatException e){
                ethereumcalculated.setText("Error");
            }
            try{
                if(chainlinkcalculated!=null || !chainlinkcalculated.equals("")) {
                    String c = Chainlink + " LINK = " + (double)Math.round(prices[2]*Double.parseDouble(Chainlink) * 1000d) / 1000d + "$";
                    chainlinkcalculated.setText(c);
                }
            }catch(NumberFormatException e){
                chainlinkcalculated.setText("Error");
            }
            try{
                if(cardanocalculated!=null || !cardanocalculated.equals("")) {
                    String car = Cardano + " ADA = " + (double)Math.round(prices[3]*Double.parseDouble(Cardano) * 1000d) / 1000d + "$";
                    cardanocalculated.setText(car);
                }
            }catch(NumberFormatException e){
                cardanocalculated.setText("Error");
            }


        }
    }

    public static double makeCall(String stringURL)  {
        System.out.println("MAKECALL METHOD");
        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        double price = 0;

        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            System.out.println("Malformed URL");
        }

        try {
            if (url != null) {
                System.out.println("url not NULL");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                is = new BufferedInputStream(urlConnection.getInputStream());

            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        }

        if (is != null) {
            try { //apartir de aqui cogemos de simulador java


                String name;
                reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                reader.beginObject();


                name = reader.nextName();                       //leemos "bitcoin"
                reader.beginObject();                           //abrimos objeto bitcoin
                name = reader.nextName();                       //leemos "usd"
                price = (double)Math.round(reader.nextDouble() * 1000d) / 1000d;    //Cogemos precio                   //cogemos precio
                System.out.println("El precio es " + price);
                reader.endObject();         //cerramos objeto "bitcoin"
                reader.endObject();         //cerramos objeto principal

            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e);

                return 0;
            }

        }

        return price;
    }

    public void favourites_to_home(View view){   // button for goint from favourites to home
        ImageButton HomeButtonF = (ImageButton) findViewById(R.id.HomeButtonF);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void favourites_to_profile(View view){   // button for goint from favourites to home
        ImageButton ProfileButtonF = (ImageButton) findViewById(R.id.ProfileButtonF);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void favourites_to_favouritesEdit(View view){
        Button EditButton = (Button) findViewById(R.id.EditButton);

        Intent intent = new Intent(this, FavouriteEditActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
