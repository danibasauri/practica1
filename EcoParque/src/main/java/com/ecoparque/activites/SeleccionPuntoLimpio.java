package com.ecoparque.activites;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ecoparque.R;
import com.ecoparque.fragments.DesconectarFragment;
import com.ecoparque.objects.Constantes;
import com.ecoparque.objects.Item;
import com.ecoparque.objects.LazyAdapter;

import java.util.ArrayList;

public class SeleccionPuntoLimpio extends Activity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_seleccion_punto_limpio);
        final Constantes appState = (Constantes) getApplicationContext();
        ArrayList image_details = appState.getListData();
        list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(new LazyAdapter(this, image_details));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(SeleccionPuntoLimpio.this, SeleccionUsuario.class);
                Item puntoLimpio = (Item) list.getItemAtPosition(position);
                appState.setPuntoLimpio(puntoLimpio.getName());
                startActivity(intent);
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seleccion_punto_limpio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            DialogFragment newFragment = new DesconectarFragment();
            newFragment.show(getFragmentManager(), "dialogo");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}