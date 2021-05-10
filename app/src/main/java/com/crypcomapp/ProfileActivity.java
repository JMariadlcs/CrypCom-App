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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Obtener referencia al TextView que visualizara el saludo
        TextView Usersave = (TextView)findViewById(R.id.Usersave);
        TextView bitcoinsave = (TextView)findViewById(R.id.bitcoinsave);
        TextView ethereumsave = (TextView)findViewById(R.id.ethereumsave);
        TextView chainlinksave = (TextView)findViewById(R.id.chainlinksave);
        TextView cardanosave = (TextView)findViewById(R.id.cardanosave);

        // Recuperamos la informacion salvada en la preferencia
        String sharedPrefFile = "com.crypcomappprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String Username = mPreferences.getString("Username", "Username");
        String Bitcoin = mPreferences.getString("Bitcoin", "0");
        String Ethereum = mPreferences.getString("Ethereum", "0");
        String Chainlink = mPreferences.getString("Chainlink", "0");
        String Doge = mPreferences.getString("Doge", "0");
        String Polkadot = mPreferences.getString("Polkadot", "0");
        String Cardano = mPreferences.getString("Cardano", "0");

        // Construimos el saludo a mostrar
        if(Username!=null || !Username.equals("")) {
            Usersave.setText(Username);
        }
        if(bitcoinsave!=null || !bitcoinsave.equals("")) {
            bitcoinsave.setText(Bitcoin);
        }
        if(ethereumsave!=null || !ethereumsave.equals("")) {
            ethereumsave.setText(Ethereum);
        }
        if(chainlinksave!=null || !chainlinksave.equals("")) {
            chainlinksave.setText(Chainlink);
        }

    }
    public void profile_to_home(View view){
        ImageButton HomeButtonP = (ImageButton) findViewById(R.id.HomeButtonP);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void profile_to_favourites(View view){
        ImageButton FavouriteButtonP = (ImageButton) findViewById(R.id.FavouritesButtonP);

        Intent intent = new Intent(this, FavouriteActivity.class);
        startActivity(intent);
    }

    public void profile_to_profileEdit(View view){
        Button EditButton = (Button) findViewById(R.id.EditButton);

        Intent intent = new Intent(this, ProfileEditActivity.class);
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
