package com.crypcomapp;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;





import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;



import javax.net.ssl.HttpsURLConnection;



public class CardanoChartActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new CardanoCharts().execute(); //hacemos execute
    }




    private class CardanoCharts extends AsyncTask<View, Void, ArrayList<DataEntry>> {

        @Override
        protected ArrayList<DataEntry> doInBackground(View... urls) {
            ArrayList<DataEntry> temp;
            //print the call in the console
            System.out.println("https://api.coingecko.com/api/v3/coins/cardano/market_chart?vs_currency=USD&days=60&interval=daily");

            // make Call to the url

            temp = makeCall("https://api.coingecko.com/api/v3/coins/cardano/market_chart?vs_currency=USD&days=60&interval=daily");


            return temp;
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
            //anyChartView.setProgressBar(findViewById(R.id.ProgressBar));

        }

        @Override
        protected void onPostExecute(ArrayList<DataEntry> prices) {
            AnyChartView anyChartView = findViewById(R.id.any_chart_view);
            Cartesian cartesian = AnyChart.line();

            cartesian.animation(true);

            cartesian.padding(10d, 20d, 5d, 20d);

            cartesian.crosshair().enabled(true);
            cartesian.crosshair()
                    .yLabel(true)
                    // TODO ystroke
                    .yStroke((Stroke) null, null, null, (String) null, (String) null);

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

            cartesian.title("Value for Cardano");

            cartesian.yAxis(0).title("Cardano in USD");
            cartesian.xAxis(0).labels().padding(10d, 10d, 10d, 5d);

            List<DataEntry> seriesData = new ArrayList<>();


            for (int i = 0; i <prices.size(); i++){
                seriesData.add(prices.get(i));
            }

            Set set = Set.instantiate();
            set.data(seriesData);
            Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");


            Line series1 = cartesian.line(series1Mapping);
            series1.name("Cardano");
            series1.hovered().markers().enabled(true);
            series1.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4d);
            series1.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5d)
                    .offsetY(5d);



            cartesian.legend().enabled(true);
            cartesian.legend().fontSize(13d);
            cartesian.legend().padding(0d, 0d, 10d, 0d);
            anyChartView.setChart(cartesian);



        }
        private class CustomDataEntry extends ValueDataEntry {

            CustomDataEntry(String x, Number value, Number value2, Number value3) {
                super(x, value);
                setValue("value2", value2);
                setValue("value3", value3);
            }

        }

    }

    public static ArrayList<DataEntry> makeCall(String stringURL) {
        System.out.println("MAKECALL METHOD");
        URL url = null;
        BufferedInputStream is = null;
        JsonReader reader;
        ArrayList <DataEntry> prices = new ArrayList();
        int contador=1;
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
                System.out.println("new BufferInputStream");
            }
        } catch (IOException ioe) {
            System.out.println("IOException");
        }

        if (is != null) {
            try { //apartir de aqui cogemos de simulador java

                System.out.println("Iniciando JSon");
                reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
                reader.beginObject();

                reader.nextName();       //leemos prices
                System.out.println("Leemos prices");
                reader.beginArray();            //abrimos array general
                System.out.println("abrimos array general");

                while(contador<=61){

                    reader.beginArray();            //abrimos array de datos
                    System.out.println("abrimos array de datos");
                    reader.nextDouble();            //leemos dato que no queremos
                    System.out.println("leemos dato que no queremos");




                    prices.add(new ValueDataEntry((61-contador)+"d",
                            (double)Math.round(reader.nextDouble() * 1000d) / 1000d));
                    System.out.println("añadimos");


                    System.out.println(contador);
                    contador++;

                    reader.endArray();      //cerramos array de dato
                    System.out.println("cerramos array de dato");


                }
                System.out.println("Salimos del while");
                //reader.beginArray();      //cerramos array prices

                reader.endArray();
                System.out.println("cerramos array prices");
                while(reader.hasNext()){
                    System.out.println("saltamos");
                    reader.skipValue();
                }


                reader.endObject();
                System.out.println("cerramos final");

            } catch (Exception e) {
                System.out.println("Exception");
                System.out.println(e);

                return null;
            }

        }

        return prices;
    }
}
