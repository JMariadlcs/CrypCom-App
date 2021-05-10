package com.crypcomapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    public void home_to_favourites(View view){
        ImageButton FavouriteButton = (ImageButton) findViewById(R.id.FavouritesButton);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }
    public void home_to_profile(View view){
        ImageButton ProfileButton = (ImageButton) findViewById(R.id.ProfileButton);

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void home_to_crypto1(View view){
        ImageButton crypto1Button = (ImageButton) findViewById(R.id.crypto1Button);

        Intent intent = new Intent(this, BitcoinActivity.class);
        startActivity(intent);
    }

    public void home_to_crypto2(View view){
        ImageButton crypto2Button = (ImageButton) findViewById(R.id.crypto2Button);

        Intent intent = new Intent(this, EthereumActivity.class);
        startActivity(intent);
    }

    public void home_to_crypto3(View view){
        ImageButton crypto3Button = (ImageButton) findViewById(R.id.crypto3Button);

        Intent intent = new Intent(this, ChainlinkActivity.class);
        startActivity(intent);
    }

    public void home_to_crypto4(View view){
        ImageButton crypto4Button = (ImageButton) findViewById(R.id.crypto4Button);

        Intent intent = new Intent(this, CardanoActivity.class);
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