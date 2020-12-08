package com.example.omta2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ListDetailController extends AppCompatActivity {
    TextView detailTitle;
    TextView detailContent;

    String title;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaildata_layout);

        Button detailBackButton = findViewById(R.id.detailbackbutton);
        detailBackButton.setOnClickListener(v -> onBackPressed());

        detailTitle = findViewById(R.id.detailtitle);
        detailContent = findViewById(R.id.detailcontent);
        detailContent.setMovementMethod(new ScrollingMovementMethod());
        
        String category = getIntent().getStringExtra("category");

        if(category.equals("해외 시장 뉴스")) {
            NewsData data = (NewsData) getIntent().getSerializableExtra("item");
            title = data.getNewsTitl();
            content = data.getNewsBdt();
        }
        else if(category.equals("기업 성공 사례")) {
            SuccessData data = (SuccessData) getIntent().getSerializableExtra("item");
            title = data.getTitl();
            content = data.getBdtCntnt();
        }
        else if(category.equals("국가 정보")) {
            NationData data = (NationData) getIntent().getSerializableExtra("item");
            title = data.getNatnNm();
            content = data.getContent();
        }
        else if(category.equals("무역 사기 사례")) {
            ScamData data = (ScamData) getIntent().getSerializableExtra("item");
            title = data.getTitl();
            content = data.getBdtCntnt();
        }
        else if(category.equals("상품 DB")) {
            ProductData data = (ProductData) getIntent().getSerializableExtra("item");
            title = data.getTitl();
            content = data.getBdtCntnt();
        }
        detailTitle.setText(title);
        detailContent.setText(content);
    }
}
