package com.example.contactlistproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //apanhar o recycleview do activity xlm
        RecyclerView recyclerView = findViewById(R.id.Recycle_Contact);
        Setupcontact();
        //criar um adapter depois da nossa funcao senao nao vai mostrar nada
        Contact_RecicycleviewAdapter adapter = new Contact_RecicycleviewAdapter(this,DB.contactList );
        //vamos indexalo ao nosso recycleview
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //menu

        //floating button
        FloatingActionButton addNewContactFAB = findViewById(R.id.floatbtn);
        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NovoContato.class));
            }
        });
    }


    //mostrar os itens no menu mostrar menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    //no click o que fazer com os itens
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav:
                Intent favorityIntent = new Intent(MainActivity.this, Fav.class);
                startActivity(favorityIntent);
                return true;
            case R.id.definicoes:
                showSettings();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    //definicoes do telemovel
    private void showSettings(){
        Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
        if(settingsIntent.resolveActivity(getPackageManager())!= null){
            startActivity(settingsIntent);
        }else{
            Log.d("ImplicitIntents","Can't handle this!");
        }
    }
    //funcao para apanhar os item do array e para percorrer a lista e addicianar os elementos ao meu recycleview
    private void Setupcontact(){
        String [] email = getResources().getStringArray(R.array.email);
        String [] num = getResources().getStringArray(R.array.numero);
        String [] nomes = getResources().getStringArray(R.array.nome);


        for (int i =0; i < nomes.length; i++) {
            DB.contactList.add(new Contato(nomes[i], num[i], email[i]));
        }
    }
}