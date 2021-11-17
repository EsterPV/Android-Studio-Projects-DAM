package com.laika.tresenraya_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScoreActivity extends AppCompatActivity {

    TextView victoria;
    TextView perder, empate;
    Button volver;
    String ganar, perdicion, empatacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        victoria = findViewById(R.id.txtGanadas);
        perder = findViewById(R.id.txtPerdidas);
        empate = findViewById(R.id.txtEmpates);
        volver = findViewById(R.id.btnVolver);
        //con bundle recibo los datos de los contadores del activity main
        Bundle bundle = this.getIntent().getExtras();


        ganar = bundle.getString("datosGanar");
        victoria.setText(victoria.getText()+ ganar );

        perdicion = bundle.getString("datosPerder");
        perder.setText(perder.getText()+ perdicion);

        empatacion = bundle.getString("datosEmpate");
        empate.setText(empate.getText()+ empatacion );


    }

    public  void volver (View v){
            Intent regresar = new Intent(this, MainActivity.class);

            startActivity(regresar);
    }


}
