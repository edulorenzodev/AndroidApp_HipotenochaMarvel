package com.example.edu.hipotenocha_marvel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements DialogoConfiguracion.RespuestaDialogoConfiguracion,

         View.OnClickListener, View.OnLongClickListener {

    private int dificultad = 0;
    public int personaje = 0;
    TextView texto;
    TableLayout tableLayout;
    public int c = 8;
    public int f = 8;
    Button tiledBoton;
    GridLayout main;
    boolean jugando = false;
    public static final int PRINCIPIANTE = 8;
    public static final int AMATEUR = 12;
    public static final int AVANZADO = 16;
    public static int hipotenochasMAX;
    MotorJuego motorJuego;
    int encontradas = 0;
    TypedArray arrayImagenes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (TextView) findViewById(R.id.principal);



        arrayImagenes = getResources().obtainTypedArray(R.array.imagenes);
        main = (GridLayout) findViewById(R.id.layout_principal); //esto es lo último
        rellenaBotones(PRINCIPIANTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                configuracion();
                return true;
            case R.id.elige_personaje:
                eligePersonaje();
                return true;
            case R.id.instrucciones:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.instrucciones);
                builder.setMessage(R.string.instrucciones_descripcion);
                builder.setPositiveButton(R.string.ok, null);
                builder.show();
                return true;
            case R.id.empieza:
                comenzar(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {

        // Obtenemos las coordenadas de la celda del texto del botón
        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);

        int resultado = motorJuego.compruebaCelda(x, y);

        if (resultado == -1) { // Hay hipotenocha
            // Mostrar hipotenocha muerta
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setTextColor(Color.BLACK);
            b.setText("X");
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);
            b.setBackground(arrayImagenes.getDrawable(personaje));
            mostrarAlerta(R.string.perdido);
            b.setScaleY(-1);
            // Fin juego
            TableLayout tl = (TableLayout) view.getParent().getParent();
            jugando = false;
            encontradas = 0;
            deshabilitaTablero(tl);
        }
        if (resultado == 0) { // No hay hipotenochas adyacentes
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setBackgroundColor(Color.GRAY);
            // Despejar adyacentes con 0
            despejaAdyacentes(view, x, y);
        }
        if (resultado > 0) { // Hay hipotenochas adyacentes
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setText(String.valueOf(resultado));
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
        }
    }
    @Override
    public boolean onLongClick(View view) {

        // Obtenemos las coordenadas de la celda del texto del botón
        int x = Integer.parseInt(((Button) view).getText().toString().split(",")[0]);
        int y = Integer.parseInt(((Button) view).getText().toString().split(",")[1]);

        int resultado = motorJuego.compruebaCelda(x, y);

        if (resultado == -1) { // Hay hipotenocha
            Button b = (Button) view;
            b.setPadding(0, 0, 0, 0);
            b.setBackground(arrayImagenes.getDrawable(personaje));
            mostrarAlerta(R.string.muybien );
            encontradas++;
            if (encontradas == hipotenochasMAX) { //Aquí!!!! no sé con cuánto va hipoMAX
                TableLayout tl = (TableLayout) view.getParent().getParent();
                jugando = false;
                encontradas = 0;
                mostrarAlerta(R.string.victoria);
                deshabilitaTablero(tl);
            }
        } else { // No hay hipotenocha
            Button b = (Button) view;
            b.setText(String.valueOf(resultado));
            b.setTextSize(20);
            b.setTextColor(Color.WHITE);
            b.setBackgroundColor(Color.GRAY);
            view.setEnabled(false);
            TableLayout tl = (TableLayout) view.getParent().getParent();
            jugando = false;
            encontradas = 0;
            mostrarAlerta(R.string.perdido);
            deshabilitaTablero(tl);
        }

        return true;
    }
    @Override
    public void onRespuestaDificultad(int i) {
        dificultad = i;
        switch (dificultad) {
            case 0:
                rellenaBotones(PRINCIPIANTE);
                jugando = false;
                break;
            case 1:
                rellenaBotones(AMATEUR);
                jugando = false;
                break;
            case 2:
                rellenaBotones(AVANZADO);
                jugando = false;
                break;
        }
    }
    public void configuracion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dificultad: ");
        builder.setSingleChoiceItems(R.array.niveles, 0, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int item) {

                dificultad = item;
                switch(dificultad) {
                    case 0:
                        rellenaBotones(PRINCIPIANTE);
                        jugando = false;
                        break;
                    case 1:
                        rellenaBotones(AMATEUR);
                        jugando = false;
                        break;
                    case 2:
                        rellenaBotones(AVANZADO);
                        jugando = false;
                        break;
                }

                //dialog.cancel();
            }
        });
        builder.setPositiveButton(R.string.volver, null); //ponemos el botón volver
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void eligePersonaje() {
        new DialogoPersonaje().show(getFragmentManager(), "Diálogo de elección de personaje");

    }

    public void comenzar(MenuItem item) {
        jugando = true;
        onRespuestaDificultad(dificultad);
        motorJuego = new MotorJuego(dificultad);
        motorJuego.jugar();
    }
    private void deshabilitaTablero(View view) {
        TableLayout tl = (TableLayout) view;
        // Recorremos la matriz de botones deshabilitando todos.
        for (int i = 0; i < tl.getChildCount(); i++) {
            TableRow tr = (TableRow) tl.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                Button b = (Button) tr.getChildAt(j);
                b.setEnabled(false);
            }
        }
    }
    private void despejaAdyacentes(View view, int x, int y) {
        // Recorremos los botones adyacentes y si también están a cero los despejamos
        for (int xt = -1; xt <= 1; xt++) {
            for (int yt = -1; yt <= 1; yt++) {
                if (xt != yt) {
                    if (motorJuego.compruebaCelda(x + xt, y + yt) == 0 && !motorJuego.getPulsadas(x + xt, y + yt)) {
                        Button b = (Button) traerBoton(x + xt, y + yt);
                        b.setBackgroundColor(Color.GRAY);
                        b.setClickable(false);
                        motorJuego.setPulsadas(x + xt, y + yt);
                        String[] coordenadas = b.getText().toString().split(",");
                        despejaAdyacentes(b, Integer.parseInt(coordenadas[0]),
                                Integer.parseInt(coordenadas[1]));
                    }
                }
            }
        }

    }
    private void mostrarAlerta(int texto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(texto)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // El usuario pulsa OK.
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
    Genera la matriz de botones inicial
     */
    public void rellenaBotones(int botones) {
        tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        tableLayout.setWeightSum(botones);

        for (int i = 0; i < botones; i++) {
            TableRow tr = new TableRow(this);
            tr.setGravity(Gravity.CENTER);
            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));

            for (int j = 0; j < botones; j++) {
                tiledBoton = new Button(this);
                tiledBoton.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tiledBoton.setId(View.generateViewId());
                tiledBoton.setText(i + "," + j);
                tiledBoton.setTextSize(0);
                tiledBoton.setOnClickListener(this);
                tiledBoton.setOnLongClickListener(this);
                tr.addView(tiledBoton);
            }
            tableLayout.addView(tr);
        }
        main.removeAllViews();
        main.addView(tableLayout);

        if (!jugando) deshabilitaTablero(tableLayout);
    }
    private View traerBoton(int x, int y) {
        Button b = null;
        // Recorremos la matriz de botones hasta encontrar una coincidencia con las coordenadas
        // buscadas.
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow tr = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                b = (Button) tr.getChildAt(j);
                if (b.getText().toString().equals(x + "," + y)) {
                    return b;
                }
            }
        }
        return null;
    }


}
