package com.crypcomapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

class Cardano {
    private String name;
    private double[] prices;
    // 0. Binance   1. Coinbase      2. Crypto.com      3. FTX.US


    public Cardano() {
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

public class CardanoActivity extends AppCompatActivity {

    private TextView binanceprice;
    private TextView coinbaseprice;
    private TextView cryptocomprice;
    private TextView blockchainprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardano);

        // Creamos un listview que va a contener los resultados de las consulta a Google Places
        binanceprice = (TextView) findViewById(R.id.cardanobinanceprice);
        coinbaseprice = (TextView) findViewById(R.id.cardanocoinbaseprice);
        cryptocomprice = (TextView) findViewById(R.id.cardanocryptocomprice);
        blockchainprice = (TextView) findViewById(R.id.cardanoblockchainprice);

        // Obtener referencia al TextView que visualizara el saludo
        new Cardanos().execute(); //hacemos execute

    }

    private class Cardanos extends AsyncTask<View, Void, Cardano> {

        @Override
        protected Cardano doInBackground(View... urls) {
            Cardano temp;
            //print the call in the console
            System.out.println("https://api.coingecko.com/api/v3/coins/cardano/tickers");

            // make Call to the url

            temp = makeCall("https://api.coingecko.com/api/v3/coins/cardano/tickers");


            return temp;
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(Cardano cardano) {


            // set the results to the list
            // and show them in the xml
            binanceprice.setText(Double.toString(cardano.getPrices(0)));

            coinbaseprice.setText(Double.toString(cardano.getPrices(1)));

            cryptocomprice.setText(Double.toString(cardano.getPrices(2)));

            blockchainprice.setText(Double.toString(cardano.getPrices(3)));
        }
    }

    public static Cardano makeCall(String stringURL)  {
        System.out.println("MAKECALL METHOD");
        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        Cardano cardano = new Cardano();  //EL OBJETO BITCOIN SE CREA AQUI O MAS ABAJO???

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
                    if(reader.nextString().equals("ADA")){
                        System.out.println("base es ADA");
                        //reader.skipValue();
                        name = reader.nextName();      //leemos target
                        String target = reader.nextString();
                        System.out.println("leemos target");
                        if(target.equals("USD") || target.equals("USDT")){
                            System.out.println("target es USD");
                            //reader.skipValue();
                            name = reader.nextName();   //leemos market
                            reader.beginObject();       //abrimos objeto market

                            /*          DENTRO DE MARKET         */

                            name = reader.nextName();   //leemos name:
                            String exchange = reader.nextString();

                            System.out.println("leemos name de market: " + exchange);
                            if(exchange.equals("Binance US")) {
                                System.out.println("market es binance");
                                //reader.skipValue();
                                reader.nextName();
                                reader.skipValue();
                                reader.nextName();
                                reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                cardano.setPrices(reader.nextDouble(), 0);
                                System.out.println("El precio es" + cardano.getPrices(0));
                                while (reader.hasNext()) {    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array

                            }else if(exchange.equals("Coinbase Pro")){
                                System.out.println("market es Coinbase");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                cardano.setPrices(reader.nextDouble(), 1);
                                System.out.println("El precio es" + cardano.getPrices(1));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array

                            }else if(exchange.equals("Crypto.com")){
                                System.out.println("market es Crypto.com");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                cardano.setPrices(reader.nextDouble(), 2);
                                System.out.println("El precio es" + cardano.getPrices(2));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array


                            }else if(exchange.equals("FTX.US")){
                                System.out.println("market es FTX.US");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                cardano.setPrices(reader.nextDouble(), 3);
                                System.out.println("El precio es" + cardano.getPrices(3));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array

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

                                //reader.nextName();
                                reader.skipValue();
                            }
                        }

                    }else{      //si no es BTC
                        System.out.println("base no es BTC");
                        //reader.skipValue();     //obviamos valor de base
                        while(reader.hasNext()){    //vamos saltandonos el resto de tokens

                            //reader.nextName();
                            reader.skipValue();
                        }
                    }
                    reader.endObject();     //cerramos objeto tickers
                }
                System.out.println("cerramos array");
                reader.endArray();      //  Cerramos array
                reader.endObject();


            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e);

                return new Cardano();
            }

        }

        return cardano;}

    public void cardano_to_home(View view){
        ImageButton HomeButtonCardano = (ImageButton) findViewById(R.id.HomeButtonCardano);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void cardano_to_favourites(View view){
        ImageButton FavouriteButtonCardano = (ImageButton) findViewById(R.id.FavouritesButtonCardano);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void cardano_to_profile(View view){
        ImageButton ProfileButtonCardano = (ImageButton) findViewById(R.id.ProfileButtonCardano);

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
