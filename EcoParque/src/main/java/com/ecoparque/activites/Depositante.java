package com.ecoparque.activites;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.ecoparque.R;
import com.ecoparque.fragments.DesconectarFragment;

public class Depositante extends Activity {

    private EditText ident, peso;
    private Button btnDep;
    private String from;
    private CheckBox material, aceites, neveras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_depositante);

        Intent intent = getIntent();
        String message = intent.getStringExtra(SeleccionUsuario.EXTRA_MESSAGE);
        from = intent.getStringExtra("from");

        ident = (EditText) findViewById(R.id.dep_ident);
        ident.setText(message);


        btnDep = (Button) findViewById(R.id.depositar);
        btnDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Depositante.this, Resultados.class);
                intent.putExtra(SeleccionUsuario.EXTRA_MESSAGE, ident.getText().toString());
                if (material.isChecked())
                    intent.putExtra("matInf", "true");
                else
                    intent.putExtra("matInf", "false");
                if (neveras.isChecked())
                    intent.putExtra("neveras", "true");
                else
                    intent.putExtra("neveras", "false");

                if (aceites.isChecked())
                    intent.putExtra("aceites", "true");
                else
                    intent.putExtra("aceites", "false");
                intent.putExtra("peso", peso.getText().toString());
                intent.putExtra("from", from);
                startActivity(intent);
            }
        });

        peso = (EditText) findViewById(R.id.text_peso);
        peso.addTextChangedListener(mTextEditorWatcher);

        material = (CheckBox) findViewById(R.id.dep_chk_matInf);
        material.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    peso.setEnabled(true);

                } else if (!aceites.isChecked() && !neveras.isChecked()) {
                    btnDep.setEnabled(false);
                    peso.setText("");
                    peso.setEnabled(false);
                }

            }
        });

        aceites = (CheckBox) findViewById(R.id.dep_chk_aceites);
        aceites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    peso.setEnabled(true);
                } else if (!material.isChecked() && !neveras.isChecked()) {
                    btnDep.setEnabled(false);
                    peso.setText("");
                    peso.setEnabled(false);
                }
            }
        });

        neveras = (CheckBox) findViewById(R.id.dep_chk_nev);
        neveras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    peso.setEnabled(true);
                } else if (!aceites.isChecked() && !material.isChecked()) {
                    btnDep.setEnabled(false);
                    peso.setText("");
                    peso.setEnabled(false);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.depositante, menu);
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

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {

            if (!peso.getText().toString().isEmpty())
                btnDep.setEnabled(true);
            else
                btnDep.setEnabled(false);
        }


    };


}
