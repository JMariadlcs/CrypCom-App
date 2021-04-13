package com.crypcomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

class Bitcoin {
    private String name;
    private double[] prices;


    public Bitcoin() {
        this.name = "";
        this.prices = new double[4];
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrices(double prices,int index) { this.prices[index] = prices; }
    public String getName() {
        return name;
    }
    public double getPrices(int i) { return prices[i]; }

}

public class BitcoinActivity extends AppCompatActivity {

    private TextView binanceprice;
    private TextView coinbaseprice;
    private TextView cryptocomprice;
    private TextView blockchainprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);

        // Creamos un listview que va a contener los resultados de las consulta a Google Places
        binanceprice = (TextView) findViewById(R.id.binanceprice);
        coinbaseprice = (TextView) findViewById(R.id.coinbaseprice);
        cryptocomprice = (TextView) findViewById(R.id.cryptocomprice);
        blockchainprice = (TextView) findViewById(R.id.blockchainprice);

        // Obtener referencia al TextView que visualizara el saludo
        new Bitcoins().execute();
    }

    private class Bitcoins extends AsyncTask<View, Void, Bitcoin> {

        @Override
        protected Bitcoin doInBackground(View... urls) {
            Bitcoin temp;
            //print the call in the console
            System.out.println("https://api.coingecko.com/api/v3/coins/bitcoin/tickers");

            // make Call to the url

                temp = makeCall("https://api.coingecko.com/api/v3/coins/bitcoin/tickers");


            return temp;
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(Bitcoin bitcoin) {


            // set the results to the list
            // and show them in the xml
            binanceprice.setText(Double.toString(bitcoin.getPrices(0)));

            coinbaseprice.setText(Double.toString(bitcoin.getPrices(1)));

            cryptocomprice.setText(Double.toString(bitcoin.getPrices(2)));

            blockchainprice.setText(Double.toString(bitcoin.getPrices(3)));
        }
    }

    public static Bitcoin makeCall(String stringURL)  {
        System.out.println("MAKECALL METHOD");
        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        Bitcoin bitcoin = new Bitcoin();  //EL OBJETO BITCOIN SE CREA AQUI O MAS ABAJO???

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

                reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                reader.beginObject();

                while(reader.hasNext()){

                    String name = reader.nextName();
                    if(name.equals("tickers")){
                        System.out.println("Data1");
                        reader.beginArray();

                        while (reader.hasNext()) {

                            reader.beginObject();
                            // comienza un objeto

                            while (reader.hasNext()) {

                                name = reader.nextName();
                                Log.i("debugjson",name);
                                if(name.equals("base")){

                                    System.out.println("Data2");
                                    if(reader.nextString().equals("BTC")){
                                        System.out.println("Data3");
                                        name = reader.nextName();
                                        Log.i("debugjson",name);
                                        if(name.equals("target")){
                                            System.out.println("Data4");
                                            System.out.println(reader.nextString());
                                            if(reader.nextString().equals("USD")){

                                                System.out.println("Data5");
                                                name = reader.nextName();
                                                Log.i("debugjson",name);
                                                if(name.equals("market")){
                                                    System.out.println("Data6");
                                                    reader.beginObject(); //object abierto para market
                                                    while(reader.hasNext()){
                                                        name = reader.nextName();
                                                        Log.i("debugjson",name);
                                                        if(reader.nextString().equals("Binance")){
                                                            System.out.println("Data7");
                                                            reader.endObject(); //cerramos object de market si es caso Binance
                                                            name = reader.nextName();

                                                            if(reader.nextString().equals("last")){ //cogemos el precio
                                                                System.out.println("Data8");
                                                                bitcoin.setPrices( reader.nextDouble(),0);
                                                            }


                                                        }else if(reader.nextString().equals("Coinbase Pro")){
                                                            System.out.println("Data9");
                                                            reader.endObject(); //cerramos object de market si es caso Binance
                                                            name = reader.nextName();

                                                            if(name.equals("last")){ //cogemos el precio
                                                                System.out.println("Data10");
                                                                bitcoin.setPrices( reader.nextDouble(),1);
                                                            }

                                                        }else if(reader.nextString().equals("Crypto.com")){
                                                            System.out.println("Data11");
                                                            reader.endObject(); //cerramos object de market si es caso Binance
                                                            name = reader.nextName();

                                                            if(name.equals("last")){ //cogemos el precio
                                                                System.out.println("Data12");
                                                                bitcoin.setPrices( reader.nextDouble(),2); //next double??
                                                            }


                                                        }else if(reader.nextString().equals("Bitfinex")){
                                                            System.out.println("Data13");
                                                            reader.endObject(); //cerramos object de market si es caso Binance
                                                            name = reader.nextName();

                                                            if(name.equals("last")){ //cogemos el precio
                                                                System.out.println("Data14");
                                                                bitcoin.setPrices( reader.nextDouble(),3);
                                                            }


                                                        }else{
                                                            System.out.println("Data15");
                                                            reader.endObject(); //cerramos object de market si no es ninguno de los 4
                                                        }

                                                    }

                                                }else{
                                                    System.out.println("Data16");
                                                    reader.skipValue();
                                                }

                                            }else{
                                                reader.endObject();
                                            }


                                        }else{
                                            System.out.println("Data17");
                                            reader.skipValue();
                                        }
                                    }else{
                                        reader.endObject();
                                    }


                                }else{
                                    System.out.println("Data18");
                                    reader.skipValue();
                                }

                                System.out.println("Data19");

                                reader.endObject();
                            }

                            System.out.println("Data20");


                        }

                        reader.endArray();
                        System.out.println("Data21");
                        } else {

                        System.out.println("Data22");
                            reader.skipValue();
                        }


                    }

                   reader.endObject();
            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e);

                return new Bitcoin();
            }

            }

            return bitcoin;}

//nuestro

    public void bitcoin_to_home(View view){
        ImageButton HomeButtonBitcoin = (ImageButton) findViewById(R.id.HomeButtonBitcoin);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void bitcoin_to_favourites(View view){
        ImageButton FavouriteButtonBitcoin = (ImageButton) findViewById(R.id.FavouritesButtonBitcoin);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void bitcoin_to_profile(View view){
        ImageButton ProfileButtonBitcoin = (ImageButton) findViewById(R.id.ProfileButtonBitcoin);

        Intent intent = new Intent(this, ProfileActivity.class);
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
