package com.example.hieu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Color;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= findViewById(R.id.button1);
        button2= findViewById(R.id.button2);
        button3= findViewById(R.id.button3);
        button4= findViewById(R.id.button4);
        button5= findViewById(R.id.button5);
        button6= findViewById(R.id.button6);
        button7= findViewById(R.id.button7);
        button8= findViewById(R.id.button8);
        button9= findViewById(R.id.button9);
        button10= findViewById(R.id.button10);
        button11= findViewById(R.id.button11);
        button9.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button9.setBackgroundColor(Color.parseColor("#0DDC12"));
            }
        }));
    }}