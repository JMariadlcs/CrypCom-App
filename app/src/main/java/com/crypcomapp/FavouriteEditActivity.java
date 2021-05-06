package com.crypcomapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FavouriteEditActivity extends AppCompatActivity {

    public void savePref(View view) {
        // Creamos colecciÃ³n de preferencias
        String sharedPrefFile = "com.crypcomappcalculator";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Obtenemos un editor de preferencias
        SharedPreferences.Editor editor = mPreferences.edit();

        // Obtenemos referencias a los elementos del interfaz grafico

        EditText bitcoinCalculator = (EditText) findViewById(R.id.bitcoinCalculator);
        EditText ethereumCalculator = (EditText) findViewById(R.id.ethereumCalculator);
        EditText chainlinkCalculator = (EditText) findViewById(R.id.chainlinkCalculator);
        EditText cardanoCalculator = (EditText) findViewById(R.id.cardanoCalculator);

        Button CalculateButton = (Button) findViewById(R.id.CalculateButton);

        // Guardamos el valor de la preferencia

        String btc = bitcoinCalculator.getText().toString();
        if(btc.equals("")==false) {
            editor.putString("Bitcoin", btc);
        }
        String eth = ethereumCalculator.getText().toString();
        if(eth.equals("")==false) {
            editor.putString("Ethereum", eth);
        }
        String link = chainlinkCalculator.getText().toString();
        if(link.equals("")==false) {
            editor.putString("Chainlink", link);
        }
        String car = cardanoCalculator.getText().toString();
        if(car.equals("")==false) {
            editor.putString("Cardano", car);
        }
        editor.apply();

        // Creamos el Intent que va a lanzar la segunda activity (SecondActivity)
        Intent intent = new Intent(this, FavouriteActivity.class);

        // Iniciamos la nueva actividad
        startActivity(intent);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_edit);
    }

    public void favourites_to_home(View view){
        ImageButton HomeButtonF = (ImageButton) findViewById(R.id.HomeButtonFE);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void favourites_to_profile(View view){
        ImageButton FavouriteButtonF = (ImageButton) findViewById(R.id.FavouritesButtonFE);

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
