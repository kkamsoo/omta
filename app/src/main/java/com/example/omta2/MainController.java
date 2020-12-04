package com.example.omta2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainController extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form);

        Button newsButton = (Button) findViewById(R.id.overseasNews);
        Button nationButton = (Button) findViewById(R.id.nationInformation);
        Button successButton = (Button) findViewById(R.id.successStory);
        Button scamButton = (Button) findViewById(R.id.scamStory);
        Button productButton = (Button) findViewById(R.id.productDB);
        Button exitButton = (Button) findViewById(R.id.exit);

        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsController.class);
                startActivity(intent);
            }
        });
        nationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsController.class);
                startActivity(intent);
            }
        });
        successButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsController.class);
                startActivity(intent);
            }
        });
        scamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsController.class);
                startActivity(intent);
            }
        });
        productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewsController.class);
                startActivity(intent);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainController.this)
                        .setTitle("Application 종료")
                        .setMessage("어플리케이션을 종료하시겠습니까?")
                        .setNegativeButton("NO", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainController.this, "어플리케이션이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });
    }
}