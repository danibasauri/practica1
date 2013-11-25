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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ecoparque.R;
import com.ecoparque.fragments.DesconectarFragment;
import com.ecoparque.objects.Validador;

public class SeleccionUsuario extends Activity {
    private EditText editTextIdent;
    private Button botonIniciar;
    public final static String EXTRA_MESSAGE = "com.ecoparque.activites.SeleccionUsuario.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_seleccion_usuario);
        editTextIdent = (EditText) findViewById(R.id.input_ident);
        botonIniciar = (Button) findViewById(R.id.iniciar_deposito);
        botonIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton ciudadano = (RadioButton) findViewById(R.id.radio_ciudadano);
                String identificador = editTextIdent.getText().toString();
                if (ciudadano.isChecked()) {

                    Intent intent = new Intent(SeleccionUsuario.this, Depositante.class);

                    intent.putExtra(EXTRA_MESSAGE, identificador);
                    intent.putExtra("from", "ciudadano");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SeleccionUsuario.this, DatosEmpresa.class);

                    intent.putExtra(EXTRA_MESSAGE, identificador);
                    startActivity(intent);
                }
            }
        });
        editTextIdent.addTextChangedListener(mTextEditorWatcher);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_ciudadano:
                if (checked) {
                    //PONER VISIBLE NIF
                    TextView textView = (TextView) findViewById(R.id.text_NIF);
                    textView.setVisibility(View.VISIBLE);

                    editTextIdent.setText("");
                    editTextIdent.setHint("NIF");
                    //OCULTAR CIF
                    TextView textView1 = (TextView) findViewById(R.id.text_CIF);
                    textView1.setVisibility(View.GONE);
                    break;
                }
            case R.id.radio_empresa:
                if (checked)


                {
                    //PONER VISIBLE CIF
                    TextView textView = (TextView) findViewById(R.id.text_CIF);
                    textView.setVisibility(View.VISIBLE);
                    editTextIdent.setText("");
                    editTextIdent.setHint("CIF");
                    //OCULTAR NIF
                    TextView textView1 = (TextView) findViewById(R.id.text_NIF);
                    textView1.setVisibility(View.GONE);
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.seleccion_usuario, menu);
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
            Validador validador = new Validador();

            botonIniciar.setEnabled(true);

            if (editTextIdent.getHint().toString().equalsIgnoreCase("NIF"))

            {
                if (validador.validarNIF(s.toString())) {


                    botonIniciar.setEnabled(true);

                } else {

                    botonIniciar.setEnabled(false);
                }
            } else {
                if (validador.validarCIF(s.toString())) {


                    botonIniciar.setEnabled(true);
                } else {

                    botonIniciar.setEnabled(false);

                }
            }
        }

    };
}
