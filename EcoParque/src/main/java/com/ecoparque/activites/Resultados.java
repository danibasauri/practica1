package com.ecoparque.activites;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecoparque.R;
import com.ecoparque.fragments.DatePickerFragment;
import com.ecoparque.fragments.DesconectarFragment;
import com.ecoparque.objects.Constantes;
import com.ecoparque.objects.NetInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

public class Resultados extends FragmentActivity {
    private LinearLayout coste;
    private Button enviarMail, registroNuevo, editarFecha;
    private TextView ident, matInf, aceites, neveras, cantResiduos, txtCalculo,
            txtPrecio, txtIVA, txtTotal, iva, total, fecha, puntoLimpio, txtCoste;
    private String contenidoMensaje;
    NetInfo netInfo = new NetInfo(Resultados.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Constantes appState = ((Constantes) this.getApplication());
        setContentView(R.layout.fragment_resultados);

        Intent intent = getIntent();
        String activity = intent.getStringExtra("from");
        String matInfIntent = intent.getStringExtra("matInf");
        String neverasIntent = intent.getStringExtra("neveras");
        String aceitesIntent = intent.getStringExtra("aceites");
        String message = intent.getStringExtra(SeleccionUsuario.EXTRA_MESSAGE);
        Integer cont = 0;

        ident = (TextView) findViewById(R.id.str_id_depositante);
        ident.setText(message);
        iva = (TextView) findViewById(R.id.IVA);
        total = (TextView) findViewById(R.id.total);

        cantResiduos = (TextView) findViewById(R.id.cant_residuos);

        puntoLimpio = (TextView) findViewById(R.id.deposito_punto_limpio);
        puntoLimpio.setText(appState.getPuntoLimpio());

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));

        contenidoMensaje = "Depositante:" + "\t" + ident.getText().toString() + "\n";
        contenidoMensaje += "Residuos depositados: \n";

        matInf = (TextView) findViewById(R.id.mat_informatico);
        if (matInfIntent.equalsIgnoreCase("true")) {
            matInf = (TextView) findViewById(R.id.mat_informatico);
            matInf.setVisibility(View.VISIBLE);
            contenidoMensaje += "\t" + matInf.getText().toString() + "\n";
            cont++;
        }

        neveras = (TextView) findViewById(R.id.neveras);
        if (neverasIntent.equalsIgnoreCase("true")) {
            neveras = (TextView) findViewById(R.id.neveras);
            neveras.setVisibility(View.VISIBLE);
            contenidoMensaje += "\t" + neveras.getText().toString() + "\n";
            cont++;
        }

        aceites = (TextView) findViewById(R.id.aceites);
        if (aceitesIntent.equalsIgnoreCase("true")) {
            aceites = (TextView) findViewById(R.id.aceites);
            aceites.setVisibility(View.VISIBLE);
            contenidoMensaje += "\t" + aceites.getText().toString() + "\n";
            cont++;
        }

        if (cont == 1) {
            cantResiduos.setText("1 residuo depositado:");
        } else {
            cantResiduos.setText(cont + " residuos depositados:");
        }


        if (activity.equalsIgnoreCase("empresa")) {

            coste = (LinearLayout) findViewById(R.id.layout_coste);
            coste.setVisibility(View.VISIBLE);

            String peso = intent.getStringExtra("peso");
            txtCalculo = (TextView) findViewById(R.id.txt_calculo);
            txtCalculo.setText(peso + "Kg * 2,4€/Kg");

            txtPrecio = (TextView) findViewById(R.id.precio_calculo);
            BigDecimal resultadoInt = BigDecimal.valueOf(Integer.parseInt(peso) * 2.4);
            txtPrecio.setText("€ " + String.valueOf(resultadoInt.setScale(2, RoundingMode.CEILING)));

            txtIVA = (TextView) findViewById(R.id.precio_iva);
            BigDecimal resultadoIVA = BigDecimal.valueOf(resultadoInt.intValue() * 0.21);
            txtIVA.setText("€ " + String.valueOf(resultadoIVA.setScale(2, RoundingMode.CEILING)));

            txtTotal = (TextView) findViewById(R.id.precio_total);
            BigDecimal resultadoTotal = resultadoInt.add(resultadoIVA);
            txtTotal.setText("€ " + String.valueOf(resultadoTotal.setScale(2, RoundingMode.CEILING)));

            contenidoMensaje += "\n";
            contenidoMensaje += txtCalculo.getText().toString() + "\t" + txtPrecio.getText().toString() + "\n";
            contenidoMensaje += iva.getText().toString() + "\t" + txtIVA.getText().toString() + "\n";
            contenidoMensaje += total.getText().toString() + "\t" + txtTotal.getText().toString() + "\n";

            editarFecha = (Button) findViewById(R.id.editar_fecha);
            editarFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "datePicker");


                }
            });

        }

        enviarMail = (Button) findViewById(R.id.enviar_mail);
        enviarMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (netInfo.isConnected()) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_SUBJECT, "Resultados depósito");
                    email.putExtra(Intent.EXTRA_TEXT, contenidoMensaje);
                    email.setType("message/rfc822");
                    startActivity(email);
                } else {
                    Toast.makeText(getApplicationContext(), "Comprueba tu conexión a internet", 1000).show();
                }
            }
        });

        registroNuevo = (Button) findViewById(R.id.registrar_deposito);
        registroNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resultados.this, SeleccionPuntoLimpio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "Depósito registrado con éxito!", 1000).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resultados, menu);
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
