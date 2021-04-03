package com.crypcomapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EthereumActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethereum);

        // Obtener referencia al TextView que visualizara el saludo

    }

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
