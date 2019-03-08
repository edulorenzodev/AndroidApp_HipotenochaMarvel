package com.example.edu.hipotenocha_marvel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Edu on 04/11/2017.
 */

public class DialogoPersonaje extends DialogFragment {

    //Declaro variable listener
    onCambioPersonajeListener actividad;

    //declaro imágenes y nombre personajes
    int imagenes[] = new int[]{R.drawable.batman, R.drawable.flash, R.drawable.ironman,
            R.drawable.lobezno, R.drawable.spiderman, R.drawable.msmarvel};
    String[] personajes;

    public class AdaptadorSeleccionHipotenocha extends ArrayAdapter<String> {
        public AdaptadorSeleccionHipotenocha(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
            View miFila = DialogoPersonaje.this.getActivity().getLayoutInflater().inflate(R.layout.fila_personalizada_personaje,
                    parent, false);
            ((TextView) miFila.findViewById(R.id.idNombreHeroe)).setText(DialogoPersonaje.this.personajes[position]);
            ((ImageView) miFila.findViewById(R.id.imagenHeroe)).setImageResource(DialogoPersonaje.this.imagenes[position]);
            return miFila;
        }
    }

    public interface onCambioPersonajeListener {
        void onCambioPersonaje(int i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.personajes = getActivity().getResources().getStringArray(R.array.nombresheroes);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View cuadroSeleccionHipotenocha = getActivity().getLayoutInflater().inflate(R.layout.personaje, null);
        final Spinner seleccion = (Spinner) cuadroSeleccionHipotenocha.findViewById(R.id.spinnerHeroe);
        seleccion.setAdapter(new AdaptadorSeleccionHipotenocha(getActivity(), R.layout.fila_personalizada_personaje, this.personajes));

        builder.setView(cuadroSeleccionHipotenocha);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                DialogoPersonaje.this.actividad.onCambioPersonaje(seleccion.getSelectedItemPosition());
            }
        });
        return builder.create();
    }
}
/*
public class DialogoPersonaje extends DialogFragment implements AdapterView.OnItemSelectedListener {
    // Declaración de variables
    RespSeleccPersonaje respSeleccPersonaje;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View miDialogo = inflater.inflate(R.layout.personaje, null);

        builder.setView(miDialogo)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // OK
                    }
                });

        AdaptadorSeleccion adaptadorSeleccion = new AdaptadorSeleccion(getActivity(),
                R.layout.personaje, getResources().getStringArray(R.array.nombresheroes));

        Spinner spinnerSelecPersonaje = (Spinner) miDialogo.findViewById(R.id.spinnerHeroe);
        spinnerSelecPersonaje.setAdapter(adaptadorSeleccion);
        spinnerSelecPersonaje.setOnItemSelectedListener(this);

        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        respSeleccPersonaje.onPersonajeSeleccionado(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /**
     * Adaptador para generar un elemento personalizado para el spinner de selección de personaje.
     */
/*
    public class AdaptadorSeleccion extends ArrayAdapter<String> {

        public AdaptadorSeleccion(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return crearFilaPersonalizada(position, convertView, parent);
        }

        /**
         * Crea una fila personalizada para el spinner de selección.
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
/*
        public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View miFila = inflater.inflate(R.layout.fila_personalizada_personaje, parent, false);

            TextView personaje = (TextView) miFila.findViewById(R.id.idNombreHeroe);
            String[] arrayNombres = getResources().getStringArray(R.array.nombresheroes);
            TypedArray arrayImagenes = getResources().obtainTypedArray(R.array.imagenes);


            personaje.setText(arrayNombres[position]);
            personaje.setCompoundDrawablesWithIntrinsicBounds(arrayImagenes.getDrawable(position), null, null, null);

            return miFila;
        }
    }

/*
    /**
     * Interfaz que permite devolver el personaje seleccionado.
     */
/*
    public interface RespSeleccPersonaje {
        void onPersonajeSeleccionado(int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        respSeleccPersonaje = (RespSeleccPersonaje) context;
    }
}
*/
