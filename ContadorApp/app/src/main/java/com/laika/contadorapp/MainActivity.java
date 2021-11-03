package com.laika.contadorapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    int contador =0; //Declaro e inicializo mi contador a 0
    TextView texto; // Creo un objeto TextView al que posteriormente relacionaré al de mi XML

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Llamo a mi xml

        texto = findViewById(R.id.txtNum); // Añado a mi variable texto mi textview txtNum mediante
                                                        //finViewById que busca el elemento de mi layout por su id

    }
    //Creo el método donde voy a hacer que funcione mi contador
    //En mi xml relaciono el onClick de mi botón con mi método pulso
    public void  pulso(View view){
        contador++; //Contador = contador + 1
        texto.setText(String.valueOf(contador)); // Añado el resultado del contador a mi textview

    }
    //Llamo al método onSaveInstanceState para guardar mi último estado
    @Override
    protected void onSaveInstanceState(@NonNull Bundle estado) {
        super.onSaveInstanceState(estado);
        estado.putInt("posicion", contador);
    }
    //Cargo el último estado en el que estaba mi actividad
    @Override
    protected void onRestoreInstanceState(Bundle estado) {
        super.onRestoreInstanceState(estado);
        contador = estado.getInt("posicion");
        texto.setText(String.valueOf(contador));
    }
}