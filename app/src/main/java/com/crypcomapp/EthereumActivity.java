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

class Ethereum {
    private String name;
    private double[] prices;
    // 0. Binance   1. Coinbase      2. Bitfinex      3. FTX


    public Ethereum() {
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
public class EthereumActivity extends AppCompatActivity {

    private TextView binanceprice;
    private TextView coinbaseprice;
    private TextView bitfinexprice;
    private TextView ftxprice;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethereum);

        // Creamos un listview que va a contener los resultados de las consulta a Google Places
        binanceprice = (TextView) findViewById(R.id.binanceprice);
        coinbaseprice = (TextView) findViewById(R.id.coinbaseprice);
        bitfinexprice = (TextView) findViewById(R.id.bitfinexprice);
        ftxprice = (TextView) findViewById(R.id.ftxprice);

        // Obtener referencia al TextView que visualizara el saludo
        new Ethereums().execute();
    }
    private class Ethereums extends AsyncTask<View, Void, Ethereum> {

        @Override
        protected Ethereum doInBackground(View... urls) {
            Ethereum temp;
            //print the call in the console
            System.out.println("https://api.coingecko.com/api/v3/coins/ethereum/tickers");

            // make Call to the url

            temp = makeCall("https://api.coingecko.com/api/v3/coins/ethereum/tickers");


            return temp;
        }
        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(Ethereum ethereum) {


            // set the results to the list
            // and show them in the xml
            binanceprice.setText(Double.toString(ethereum.getPrices(0)));

            coinbaseprice.setText(Double.toString(ethereum.getPrices(1)));

            bitfinexprice.setText(Double.toString(ethereum.getPrices(2)));

            ftxprice.setText(Double.toString(ethereum.getPrices(3)));
        }
    }

    public static Ethereum makeCall(String stringURL)  {
        System.out.println("MAKECALL METHOD");
        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        Ethereum ethereum = new Ethereum();  //EL OBJETO BITCOIN SE CREA AQUI O MAS ABAJO???

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
                    if(reader.nextString().equals("ETH")){
                        System.out.println("base es ETH");
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
                            if(exchange.equals("Binance")) {
                                System.out.println("market es binance");
                                //reader.skipValue();
                                reader.nextName();
                                reader.skipValue();
                                reader.nextName();
                                reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                ethereum.setPrices(reader.nextDouble(), 0);
                                System.out.println("El precio es" + ethereum.getPrices(0));
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
                                ethereum.setPrices(reader.nextDouble(), 1);
                                System.out.println("El precio es" + ethereum.getPrices(1));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array

                            }else if(exchange.equals("Bitfinex")){
                                System.out.println("market es Bitfinex");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                ethereum.setPrices(reader.nextDouble(), 2);
                                System.out.println("El precio es" + ethereum.getPrices(2));
                                while(reader.hasNext()){    //vamos saltandonos el resto de tokens
                                    reader.nextName();
                                    reader.skipValue();
                                }//salimos al while de leer objetos de array


                            }else if(exchange.equals("FTX")){
                                System.out.println("market es FTX");
                                //reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.nextName();reader.skipValue();
                                reader.endObject();     //salimos de market

                                reader.nextName();      //cogemos Last
                                ethereum.setPrices(reader.nextDouble(), 3);
                                System.out.println("El precio es" + ethereum.getPrices(3));
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
                        System.out.println("base no es ETH");
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

                return new Ethereum();
            }

        }

        return ethereum;}




    public void ethereum_to_home(View view){
        ImageButton HomeButtonEthereum = (ImageButton) findViewById(R.id.HomeButtonEthereum);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ethereum_to_favourites(View view){
        ImageButton FavouriteButtonEthereum = (ImageButton) findViewById(R.id.FavouritesButtonEthereum);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void ethereum_to_profile(View view){
        ImageButton ProfileButtonEthereum = (ImageButton) findViewById(R.id.ProfileButtonEthereum);

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
