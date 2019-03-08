package com.example.edu.hipotenocha_marvel;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Edu on 04/11/2017.
 */

public class DialogoConfiguracion extends DialogFragment {
    String[] dificultad = {"Principiante", "Amateur", "Avanzado"};
    RespuestaDialogoConfiguracion respuestaDificultad;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Elige la dificultad:")
                .setSingleChoiceItems(dificultad, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { //este evento controla los radioButtons
                        respuestaDificultad.onRespuestaDificultad(i);
                    }

                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // El usuario pulsa OK.
                    }
                });
        return builder.create();
    }

    /**
     * Interfaz que permite devolver la dificultad seleccionada.
     */
    public interface RespuestaDialogoConfiguracion {
        void onRespuestaDificultad(int i);
    }

    /**
     * se invoca cuando el fragmento de diálogo se añade a la actividad
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        respuestaDificultad = (RespuestaDialogoConfiguracion) context;
    }
}
