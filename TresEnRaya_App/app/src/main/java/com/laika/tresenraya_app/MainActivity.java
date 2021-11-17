package com.laika.tresenraya_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtVictoria;
    Button reiniciar;
    Button score;
    Integer[] botones = new Integer[]{
            //introduzco y ordeno los botones en mi array
            R.id.boton1, R.id.boton2, R.id.boton3,
            R.id.boton4, R.id.boton5, R.id.boton6,
            R.id.boton7, R.id.boton8, R.id.boton9,

    }; //array que hace referncia a los botones
    int[] tablero = new int[]{ //array que hace referncia al tablero
       0, 0, 0,      //cada posición vacía será un 0
       0, 0, 0,
       0, 0, 0,
    };
    int estado = 0; //variable que informa del estado de nuestra partida, 0 si no hay ningún ganador y seguimos jugando
                    //1 si ganamos, -1 si gana la máquina, 2 si es empate

    int fichasPuestas = 0; //contador para saber cuantas fichas llevamos puestas

    int turno = 1; //indica el turno en cada partida, 1 para el jugador, -1 para la maquina

    int[] posGanadora = new int[]{-1, -1, -1};


    //Contadores para el score
    String contGanar = "0";
    String contPerder = "0";
    String contEmpate = "0";

    //Drawables para cada ficha
    int circulo = (R.drawable.circulobien1);
    int cruz = (R.drawable.cruzbien1);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVictoria = (TextView)  findViewById(R.id.txtganar); //llamo a mi textview has ganado
        txtVictoria.setVisibility(View.INVISIBLE); //Lo hago invisible

        reiniciar = (Button) findViewById(R.id.btnReiniciar);
         reiniciar.setVisibility(View.INVISIBLE);

        score = (Button) findViewById(R.id.btnScore);
        score.setVisibility(View.INVISIBLE);

        //Ligo mi boton score con enviarDatos para que al pulsarlo envíe los datos de los contadores al activity score
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatos();
            }
        });




    }

    public  void setReiniciar(View v){

        //Vuelvo a reiniciar todo a su estado inicial

        for(int i = 0; i< botones.length; i++){
           Button boton = findViewById(botones[i]);
           boton.setBackgroundResource(0);
           boton.setBackgroundColor(R.style.botonStandard);

        }

        tablero = new int[]{ //array que hace referncia al tablero
                0, 0, 0,      //cada posición vacía será un 0
                0, 0, 0,
                0, 0, 0,
        };


        estado = 0;
        fichasPuestas= 0;
        turno = 1;
        txtVictoria.setVisibility(View.INVISIBLE);
        reiniciar.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
    }

    //Cuando pulse el botón siguiente pasará al activity score
    public void siguiente(View view){
        Intent siguiente2 = new Intent(this, ScoreActivity.class);
        startActivity(siguiente2);



    }

    //Envío los datos de los contadores al activity score
    private void enviarDatos(){

        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("datosGanar", contGanar);
        intent.putExtra("datosPerder", contPerder);
        intent.putExtra("datosEmpate", contEmpate);
        startActivity(intent);
    }


    //Funcion para marcar x en el tablero
    public void ponerFicha(View v){

        if (estado == 0 ){//si no hay ganador perdedor o empate, es decir si estamos jugando

            turno = 1; //es turno del jugador
            int indice = Arrays.asList(botones).indexOf(v.getId());//Creo esta variable para ver el botón que estoy pulsando

            if(tablero[indice] == 0 ){//si la posicion del tablero no esta ocupada

                v.setBackgroundResource(cruz);//añado a mi view/boton la cruz
                tablero[indice] = 1; //inserto en el tablero la cruz (1)

                fichasPuestas += 1; //sumo ficha colocada en el tablero

                estado = comprobarEstado(); //comprobamos el estado de la partida

                terminarPartida();

                if(estado == 0){ // si seguimos jugando (nadie gana)

                    turno = -1; //turno de la máquina
                    ia(); //llamo a mi función ia
                    fichasPuestas += 1; //la máquina ha colocado ficha
                    estado = comprobarEstado(); //comprobamos el estado de la partida
                    terminarPartida();
                }
            }




        }

    }

    Button botonIA;
    //Función para marcar circulos aleatoriamemte
    public void ia(){
        Random ran = new Random();

        int indiceIA = ran.nextInt(tablero.length); //Genera un número aleatorio entre 0 y longitud de mi tablero(9)-1

        while (tablero[indiceIA]!=0){//Mientras la posicion es distinta de cero (no está ocupada)
            indiceIA = ran.nextInt(tablero.length);//coloco el círculo en una posición aleatoria
        }

        botonIA = (Button) findViewById(botones[indiceIA]); //asigno esta posición al boton por su id
        botonIA.setBackgroundResource(circulo); //asigno a este botón el dibujo circulo
        tablero[indiceIA] = -1; //el círculo será el -1
    }

    //Función para comprobar el estado de la partida
    public int comprobarEstado() {

        int nuevoEstado = 0; //seguimos jugando

        //posiciones ganadoras horizontalmente
        if (Math.abs(tablero[0] + tablero[1] + tablero[2]) == 3) { //valor absoluto porque tanto si gano yo (3) como si gana la maquina (-3)
            posGanadora = new int[]{0, 1, 2};
            nuevoEstado = 1 * turno; // multiplico x1 porque si turno de maquin(-1) *1 = -1, y si turno jugador(1)*1 = 1

        } else if (Math.abs(tablero[3] + tablero[4] + tablero[5]) == 3) {
            posGanadora = new int[]{3, 4, 5};
            nuevoEstado = 1 * turno;
        } else if (Math.abs(tablero[6] + tablero[7] + tablero[8]) == 3) {
            posGanadora = new int[]{6, 7, 8};
            nuevoEstado = 1 * turno;

        } else if (Math.abs(tablero[0] + tablero[3] + tablero[6]) == 3) {//posiciones ganadora verticalemnte
            posGanadora = new int[]{0, 3, 6};
            nuevoEstado = 1 * turno;
        } else if (Math.abs(tablero[1] + tablero[4] + tablero[7]) == 3) {
            posGanadora = new int[]{1, 4, 7};
            nuevoEstado = 1 * turno;
        } else if (Math.abs(tablero[2] + tablero[5] + tablero[8]) == 3) {
            posGanadora = new int[]{2, 5, 8};
            nuevoEstado = 1 * turno;
        } else if (Math.abs(tablero[0] + tablero[4] + tablero[8]) == 3) {//posiciones diagonales
            posGanadora = new int[]{0, 4, 8};
            nuevoEstado = 1 * turno;
        } else if (Math.abs(tablero[2] + tablero[4] + tablero[6]) == 3) {
            posGanadora = new int[]{2, 4, 6};
            nuevoEstado = 1 * turno;
        } else if (fichasPuestas == 9) { //si hay empate
            nuevoEstado = 2;
        }

        return nuevoEstado;
    }

    public void terminarPartida(){
        int fichaVictoria;
        if (estado == 1 || estado == -1){
            if (estado ==1){ //si gana el jugador
                txtVictoria.setVisibility(View.VISIBLE);
                txtVictoria.setTextColor(Color.GREEN);
                txtVictoria.setText("Has ganado :)");
                reiniciar.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
                fichaVictoria = R.drawable.cruzmal1; //asigno las cruces verdes para resaltar la jugada

                //incremento el marcador de victoria
                int parseGanar= Integer.parseInt(contGanar);
                parseGanar++;
                contGanar=String.valueOf(parseGanar);

            }else{ //si gana la IA
                txtVictoria.setVisibility(View.VISIBLE);
                txtVictoria.setTextColor(Color.RED);
                txtVictoria.setText("Has perdido ;(");

                reiniciar.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
                //Incremento el contador de perder
                int parsePerder= Integer.parseInt(contPerder);
                parsePerder++;
                contPerder=String.valueOf(parsePerder);

                fichaVictoria = R.drawable.circulomal1; // asigno los circulos verdes para resaltar la jugada
            }
            //con el for cambio las fichas de la posición ganadora por las verdes
            for(int i= 0; i < posGanadora.length; i++ ){
                Button b = findViewById(botones[posGanadora[i]]);
                b.setBackgroundResource(fichaVictoria);
            }
        }else if(estado == 2){//si hay empate
            txtVictoria.setVisibility(View.VISIBLE);
            txtVictoria.setText("Has empatado! :O");

            reiniciar.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            //Incremento el contador de empate
            int parseEmpate= Integer.parseInt(contEmpate);
            parseEmpate++;
            contEmpate=String.valueOf(parseEmpate);

        }
    }



    @Override //Guardo los estados de los elementos de mi partida
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);



            outState.putInt("estadoPartida", estado);
            outState.putInt("Fichaspuestas", fichasPuestas);
            outState.putInt("turno", turno);



            outState.putIntArray("tablero", tablero);
            outState.putIntArray("posicion ganadora",posGanadora);
            outState.putString("contGanar", contGanar);
            outState.putString("contPerder", contPerder);
            outState.putString("contEmpate", contEmpate);


        //necesito recorrer el array porque es de Integers
        for(int i = 0; i < botones.length; i++){
             outState.putInt("botones", botones[i]);
        }




    }
    @Override//Restauro el estado de los elementos de mi partida
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        estado = savedInstanceState.getInt("estadoPartida");
        fichasPuestas = savedInstanceState.getInt("Fichaspuestas");
        turno = savedInstanceState.getInt("turno");
        tablero = savedInstanceState.getIntArray("tablero");
        posGanadora = savedInstanceState.getIntArray("posicion ganadora");


        contGanar = savedInstanceState.getString("contGanar");
        contPerder = savedInstanceState.getString("contPerder");
        contEmpate = savedInstanceState.getString("contEmpate");




        //vuelvo a repintar todos los elementos según quien gana
        if(savedInstanceState.getInt("estadoPartida")==1){ //gana jugador
            txtVictoria.setVisibility(View.VISIBLE);
            txtVictoria.setText("Has ganado! :)");
            txtVictoria.setTextColor(Color.GREEN);
            reiniciar.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            //para no perder los drwables en cada posición debo recorrer el array
            for(int i = 0; i < botones.length; i++){
                Button boton = findViewById(botones[i]); //asigno botón según mi array
                if(tablero[i]==1){ //si hay fichas del jugador en el tablero
                    boton.setBackgroundResource(cruz);//redibujo la cruz
                }if(tablero[i]==-1) { // si hay fichas de la máquina en el tablero
                    boton.setBackgroundResource(circulo); //redibujo el círculo
                }if((savedInstanceState.getInt("estadoPartida")==1)&&tablero[i]==1){//gana jugador
                    boton.setBackgroundResource(R.drawable.cruzmal1); //redibujo las cruces ganadoras(verdes)
            }
                         }

        }else if (savedInstanceState.getInt("estadoPartida")==-1){ //gana máquina
            txtVictoria.setVisibility(View.VISIBLE);
            txtVictoria.setTextColor(Color.RED);
            txtVictoria.setText("Has perdido ;(");
            reiniciar.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            for(int i = 0; i < botones.length; i++){
                Button boton = findViewById(botones[i]);
                if(tablero[i]==1) {
                    boton.setBackgroundResource(cruz);

                }if(tablero[i]==-1){
                    boton.setBackgroundResource(circulo);
                }if((savedInstanceState.getInt("estadoPartida")==-1)&&tablero[i]==-1){
                boton.setBackgroundResource(R.drawable.circulomal1);
            }
            }

            }else if(savedInstanceState.getInt("estadoPartida")== 2){ //hay empate
            txtVictoria.setVisibility(View.VISIBLE);
            txtVictoria.setText("Has empatado! :O");
            reiniciar.setVisibility(View.VISIBLE);
            score.setVisibility(View.VISIBLE);
            for(int i = 0; i < botones.length; i++) {
                Button boton = findViewById(botones[i]);
                boton.setBackgroundResource(cruz);
                if(tablero[i]==1){
                    boton.setBackgroundResource(cruz);
                }if(tablero[i]==-1){
                    boton.setBackgroundResource(circulo);
                }
            }
            }else{
            for(int i = 0; i < botones.length; i++) { //nadie ha ganado aún. Se sigue jugando
                Button boton = findViewById(botones[i]);

                if(tablero[i]==1){
                    boton.setBackgroundResource(cruz);
                }if(tablero[i]==-1){
                    boton.setBackgroundResource(circulo);
                }if(tablero[i]==0){
                    boton.setBackgroundResource(0);
                    boton.setBackgroundColor(R.style.botonStandard);
                }
            }
        }





    }
}