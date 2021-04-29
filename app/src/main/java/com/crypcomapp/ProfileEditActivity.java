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

public class ProfileEditActivity extends AppCompatActivity {


    public void savePref(View view) {
        // Creamos colecciÃ³n de preferencias
        String sharedPrefFile = "com.crypcomappprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Obtenemos un editor de preferencias
        SharedPreferences.Editor editor = mPreferences.edit();

        // Obtenemos referencias a los elementos del interfaz grafico
        EditText username = (EditText) findViewById(R.id.username);
        EditText bitcoinWallet = (EditText) findViewById(R.id.bitcoinWallet);
        EditText ethereumWallet = (EditText) findViewById(R.id.ethereumWallet);
        EditText chainlinkWallet = (EditText) findViewById(R.id.chainlinkWallet);
        EditText dogeWallet = (EditText) findViewById(R.id.dogeWallet);
        EditText polkadotWallet = (EditText) findViewById(R.id.polkadotWallet);
        EditText cardanoWallet = (EditText) findViewById(R.id.cardanoWallet);

        Button SaveButton = (Button) findViewById(R.id.EditButton);

        // Guardamos el valor de la preferencia
        String us = username.getText().toString();
        if(us.equals("")==false) {
            editor.putString("Username", us);
        }
        String btc = bitcoinWallet.getText().toString();
        if(btc.equals("")==false) {
            editor.putString("Bitcoin", btc);
        }
        String eth = ethereumWallet.getText().toString();
        if(eth.equals("")==false) {
            editor.putString("Ethereum", eth);
        }
        String link = chainlinkWallet.getText().toString();
        if(link.equals("")==false) {
            editor.putString("Chainlink", link);
        }
        String Doge = dogeWallet.getText().toString();
        if(Doge.equals("")==false) {
            editor.putString("Doge", Doge);
        }
        String pol = polkadotWallet.getText().toString();
        if(pol.equals("")==false) {
            editor.putString("Polkadot", pol);
        }
        String car = cardanoWallet.getText().toString();
        if(car.equals("")==false) {
            editor.putString("Cardano", car);
        }
        editor.apply();

        // Creamos el Intent que va a lanzar la segunda activity (SecondActivity)
        Intent intent = new Intent(this, ProfileActivity.class);

        // Iniciamos la nueva actividad
        startActivity(intent);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        // Obtener referencia al TextView que visualizara el saludo

    }
    public void profile_to_home(View view){
        ImageButton HomeButtonP = (ImageButton) findViewById(R.id.HomeButtonPE);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void profile_to_favourites(View view){
        ImageButton FavouriteButtonP = (ImageButton) findViewById(R.id.FavouritesButtonPE);

        Intent intent = new Intent(this, FavouriteActivity.class);
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
