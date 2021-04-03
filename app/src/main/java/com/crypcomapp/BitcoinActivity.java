package com.crypcomapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BitcoinActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);

        // Obtener referencia al TextView que visualizara el saludo

    }
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
