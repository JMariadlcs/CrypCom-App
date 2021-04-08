package com.crypcomapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

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
    public double[] getPrices() { return prices; }

}

public class BitcoinActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);

        // Obtener referencia al TextView que visualizara el saludo
        new Bitcoins().execute();
    }

    private class Bitcoins extends AsyncTask<View, Void, ArrayList<Bitcoin>> {

        @Override
        protected ArrayList<Bitcoin> doInBackground(View... urls) {
            ArrayList<Bitcoin> temp;
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
        protected void onPostExecute(ArrayList<Bitcoin> result) {
            // Aquí se actualiza el interfaz de usuario
            List<String> listTitle = new ArrayList<String>();

            for (int i = 0; i < result.size(); i++) {
                // make a list of the venus that are loaded in the list.
                // show the name, the category and the city
                listTitle.add(i, "Place name: " +result.get(i).getName() + "\nLatitude: " + result.get(i).getLatitude() + "\nLongitude:" + result.get(i).getLongitude());
            }

            // set the results to the list
            // and show them in the xml
            ArrayAdapter<String> myAdapter;
            myAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.row_layout, R.id.listText, listTitle);
            m_listview.setAdapter(myAdapter);
        }
    }

    public static ArrayList<Bitcoin> makeCall(String stringURL) {

        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        ArrayList<Bitcoin> temp = new ArrayList<Bitcoin>();

        try {
            url = new URL(stringURL);
        } catch (Exception ex) {
            System.out.println("Malformed URL");
        }

        try {
            if (url != null) {
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                is = new BufferedInputStream(urlConnection.getInputStream());
            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        }

        if (is != null) {
            try {
                reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                reader.beginObject();
                Bitcoin bitcoin = new Bitcoin();  //EL OBJETO BITCOIN SE CREA AQUI O MAS ABAJO???
                while (reader.hasNext()){         //COMPROBAR SI EL WHILE LO HACEMOS BIEN O HAY QUE HACER MAS
                    String nameToRead = reader.nextName();

                    //comprobamos base
                    if(nameToRead.equals("base")){
                        String base = reader.nextString();
                        if(base.equals("BTC")){

                            nameToRead = reader.nextName();
                            //comprobamos target
                            if(nameToRead.equals("target")){
                                String target= reader.nextString();
                                if(target.equals("USD")){

                                    //comprobamos el exchange Binance
                                    nameToRead=reader.nextName();
                                    String name= reader.nextString();
                                    if(name.equals("Binance")){

                                        //Cogemos el precio
                                        nameToRead=reader.nextName();
                                        bitcoin.setPrices(reader.nextDouble(),0);
                                    }

                                    //comprobamos el exchange Coinbase Pro
                                    nameToRead=reader.nextName();
                                    name= reader.nextString();
                                    if(name.equals("Coinbase Pro")){

                                        //Cogemos el precio
                                        nameToRead=reader.nextName();
                                        bitcoin.setPrices(reader.nextDouble(),1);
                                    }

                                    //comprobamos el exchange Crypto.com
                                    nameToRead=reader.nextName();
                                    name= reader.nextString();
                                    if(name.equals("Crypto.com")){

                                        //Cogemos el precio
                                        nameToRead=reader.nextName();
                                        bitcoin.setPrices(reader.nextDouble(),2);
                                    }

                                    //comprobamos el exchange Bitfinex
                                    nameToRead=reader.nextName();
                                    name= reader.nextString();
                                    if(name.equals("Bitfinex")){

                                        //Cogemos el precio
                                        nameToRead=reader.nextName();
                                        bitcoin.setPrices(reader.nextDouble(),2);
                                    }


                                }

                            }
                        }
                    }
                }
                reader.endObject();
            } catch (Exception e) {
                System.out.println("Exception");
                return new ArrayList<Bitcoin>();
            }
        }

        return temp;}

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
