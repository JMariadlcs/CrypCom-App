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


                String name;
                reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                reader.beginObject();


                name = reader.nextName();reader.skipValue();    // leemos name: bitcoin
                name = reader.nextName();                       //leemos tickers: [...



                /*          DENTRO DE ARRAY         */

                reader.beginArray();
                System.out.println("Abrimos array");
                while(reader.hasNext()){     //vamos leyendo objetos del array

                    reader.beginObject();   //abrimos objeto ticker
                    name = reader.nextName();      //leemos base
                    System.out.println("leemos base");
                    if(reader.nextString().equals("BTC")){
                        System.out.println("base es BTC");
                        //reader.skipValue();
                        name = reader.nextName();      //leemos target
                        System.out.println("leemos target");
                        if(reader.nextString().equals("USD")){
                            System.out.println("target es USD");
                            //reader.skipValue();
                            name = reader.nextName();   //leemos market
                            reader.beginObject();       //abrimos objeto market

                            /*          DENTRO DE MARKET         */
                            name = reader.nextName();   //leemos name:
                            System.out.println("leemos name de market");
                            if(reader.nextString().equals("FTX.US")){
                                System.out.println("market es binance");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                bitcoin.setPrices(reader.nextDouble(), 0);
                                System.out.println("El precio es" + bitcoin.getPrices(0));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array

                                //aqui añadimos el resto con ELSE IF

                            }else{      //si no nos interesa el market
                                System.out.println("no nos interesa el market");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array
                            }


                        }else{  //si no es USD
                            System.out.println("target no es USD");
                            //reader.skipValue();     //obviamos valor de target
                            while(reader.hasNext()){    //vamos saltandonos el resto de tokens

                                reader.nextName();
                                reader.skipValue();
                            }
                        }

                    }else{      //si no es BTC
                        System.out.println("base no es BTC");
                        //reader.skipValue();     //obviamos valor de base
                        while(reader.hasNext()){    //vamos saltandonos el resto de tokens

                            reader.nextName();
                            reader.skipValue();
                        }
                    }
                }
                System.out.println("cerramos array");
                reader.endArray();      //  Cerramos array
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
