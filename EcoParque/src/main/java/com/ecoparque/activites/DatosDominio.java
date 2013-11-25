package com.ecoparque.activites;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ecoparque.R;
import com.ecoparque.asyncTasks.ParseWebTask;
import com.ecoparque.fragments.DesconectarFragment;


public class DatosDominio extends Activity {
    private EditText urlEmpresa;
    private static String JSONurl = "http://freegeoip.net/json/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_dominio);

        Intent intent = getIntent();
        String url = intent.getStringExtra("urlEmpresa");
        urlEmpresa = (EditText) findViewById(R.id.url_empresa);
        urlEmpresa.setText(url);

        url = JSONurl + urlEmpresa.getText().toString();
        new ParseWebTask(DatosDominio.this).execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.datos_dominio, menu);
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