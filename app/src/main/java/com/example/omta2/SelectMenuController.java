package com.example.omta2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

public class SelectMenuController extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmenu_layout);

        Button newsButton = (Button) findViewById(R.id.overseasNews);
        Button successButton = (Button) findViewById(R.id.successStory);
        Button nationButton = (Button) findViewById(R.id.nationInformation);
        Button scamButton = (Button) findViewById(R.id.scamStory);
        Button productButton = (Button) findViewById(R.id.productDB);
        Button exitButton = (Button) findViewById(R.id.exit);

        // 비동기로 데이터 받아오기
        GetNewsAPI newsAPI = new GetNewsAPI(this, "미국");
        GetSuccessAPI successAPI = new GetSuccessAPI(this, "미국");
        GetScamAPI scamAPI = new GetScamAPI(this, "미국");
        GetNationAPI nationAPI = new GetNationAPI(this, "미국");
        GetProductAPI productAPI = new GetProductAPI(this, "미국");

        // 백그라운드 실행
        newsAPI.execute();
        successAPI.execute();
        scamAPI.execute();
        nationAPI.execute();
        productAPI.execute();

        newsButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("NewsList", newsAPI.newsList);
            intent.putExtra("menuTitle", "해외 시장 뉴스");
            intent.putExtra("TabIndex", 0);
            startActivity(intent);
            newsAPI.cancel(false);
        });

        successButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("SuccessList", successAPI.successList);
            intent.putExtra("menuTitle", "기업 성공 사례");
            intent.putExtra("TabIndex", 1);
            startActivity(intent);
            successAPI.cancel(false);
        });

        scamButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("ScamList", scamAPI.scamList);
            intent.putExtra("menuTitle", "무역 사기 사례");
            intent.putExtra("TabIndex", 2);
            startActivity(intent);
            scamAPI.cancel(false);
        });

        nationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("NationList", nationAPI.nationList);
            intent.putExtra("menuTitle", "국가 정보");
            intent.putExtra("TabIndex", 3);
            startActivity(intent);
            nationAPI.cancel(false);
        });

        productButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("ProductList", productAPI.productList);
            intent.putExtra("menuTitle", "상품 DB");
            intent.putExtra("TabIndex", 4);
            startActivity(intent);
            productAPI.cancel(false);
        });

        exitButton.setOnClickListener(view -> new AlertDialog.Builder(SelectMenuController.this)
                .setTitle("Application 종료")
                .setMessage("어플리케이션을 종료하시겠습니까?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SelectMenuController.this, "어플리케이션이 종료되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("NO", null)
                .setIcon(android.R.drawable.ic_dialog_alert).show());
    }
}