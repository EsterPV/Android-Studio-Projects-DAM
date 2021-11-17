package com.laika.tresenraya_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
    }
    public void siguiente(View view){
        Intent siguiente2 = new Intent(this, MainActivity.class);
        startActivity(siguiente2);



    }
}
