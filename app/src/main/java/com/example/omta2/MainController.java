package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainController extends AppCompatActivity {
    Spinner nationSpinner, industrySpinner, tradeSpinner;
    Button backButton;

    APIController apiController = new APIController(); // API컨트롤러 객체 생성;
    ArrayList<NewsData> newsDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // 상단 타이틀 텍스트 설정
        TextView menuTitle = (TextView) findViewById(R.id.menutitle);
        menuTitle.setText(getIntent().getStringExtra("menuTitle"));

        // 뒤로가기 버튼 이벤트 처리
        backButton = findViewById(R.id.backbutton);
        newsDataList = (ArrayList<NewsData>) getIntent().getSerializableExtra("NewsList");
        backButton.setOnClickListener(v -> onBackPressed());

        // 스피너 이벤트 처리
        nationSpinner = findViewById(R.id.nationspinner);
        industrySpinner = findViewById(R.id.industryspinner);
        tradeSpinner = findViewById(R.id.tradespinner);

        // 국가 선택 스피너 어댑터
        ArrayAdapter nationAdapter = ArrayAdapter.createFromResource(this, R.array.nations, R.layout.spinner_layout);
        nationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        nationSpinner.setAdapter(nationAdapter);
        // 산업분류 선택 스피너 어댑터
        ArrayAdapter industryAdapter = ArrayAdapter.createFromResource(this, R.array.industrys, R.layout.spinner_layout);
        industryAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        industrySpinner.setAdapter(industryAdapter);
        // 무역관 선택 스피너 어댑터
        ArrayAdapter tradeAdapter = ArrayAdapter.createFromResource(this, R.array.trades, R.layout.spinner_layout);
        tradeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        tradeSpinner.setAdapter(tradeAdapter);

        nationSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        industrySpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tradeSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        nationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 리스트뷰 어댑터
        ListView listView = (ListView) findViewById(R.id.listView);
        final ListViewAdapter listAdapter = new ListViewAdapter(this, newsDataList);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener((parent, v, position, id) -> Toast.makeText(getApplicationContext(),
                "클릭", Toast.LENGTH_LONG).show());
    }

    public void initializeNewsData() {
        newsDataList = new ArrayList<NewsData>();
        NewsData newsData = new NewsData("", "");
        newsDataList.add(newsData);
    }
}