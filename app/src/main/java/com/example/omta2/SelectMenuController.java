package com.example.omta2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SelectMenuController extends AppCompatActivity {
    APIController apiController = new APIController();
    ArrayList<NewsData> newsList = new ArrayList<>();
    ArrayList<SuccessData> successList = new ArrayList<>();
    ArrayList<NationData> nationList = new ArrayList<>();
    ArrayList<ScamData> scamList = new ArrayList<>();
    ArrayList<ProductData> productList = new ArrayList<>();

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

        new Thread(() -> {
            try {
                newsList = apiController.getNewsFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                successList = apiController.getSuccessFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                nationList = apiController.getNationFromAPI("VN");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                scamList = apiController.getScamFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                productList = apiController.getProductFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        newsButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("NewsList", newsList);
            intent.putExtra("menuTitle", "해외 시장 뉴스");
            intent.putExtra("TabIndex", 0);
            startActivity(intent);
        });

        successButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("SuccessList", successList);
            intent.putExtra("menuTitle", "기업 성공 사례");
            intent.putExtra("TabIndex", 1);
            startActivity(intent);
        });

        scamButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("ScamList", scamList);
            intent.putExtra("menuTitle", "무역 사기 사례");
            intent.putExtra("TabIndex", 2);
            startActivity(intent);
        });

        nationButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("NationList", nationList);
            intent.putExtra("menuTitle", "국가 정보");
            intent.putExtra("TabIndex", 3);
            startActivity(intent);
        });

        productButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainController.class);
            intent.putExtra("ProductList", productList);
            intent.putExtra("menuTitle", "상품 DB");
            intent.putExtra("TabIndex", 4);
            startActivity(intent);
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
