package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    ArrayList<SuccessData> successDataList;
    ArrayList<NationData> nationDataList;
    ArrayList<ScamData> scamDataList;
    ArrayList<ProductData> productDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // 상단 타이틀 텍스트 설정
        TextView menuTitle = (TextView) findViewById(R.id.menutitle);
        menuTitle.setText(getIntent().getStringExtra("menuTitle"));

        // 뒤로가기 버튼 이벤트 처리
        backButton = findViewById(R.id.backbutton);
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

        // 카테고리별 데이터리스트 받아오기
        newsDataList = (ArrayList<NewsData>) getIntent().getSerializableExtra("NewsList");
        successDataList = (ArrayList<SuccessData>) getIntent().getSerializableExtra("SuccessList");
        nationDataList = (ArrayList<NationData>) getIntent().getSerializableExtra("NationList");
        scamDataList = (ArrayList<ScamData>) getIntent().getSerializableExtra("ScamList");
        productDataList = (ArrayList<ProductData>) getIntent().getSerializableExtra("ProductList");

        // 리스트뷰 생성
        ListViewAdapter listAdapter = null;
        ListView listView = (ListView) findViewById(R.id.listView);

        if(menuTitle.getText().equals("해외 시장 뉴스")) {
            listAdapter = new ListViewAdapter(this, newsDataList, "NewsData");
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("기업 성공 사례")) {
            listAdapter = new ListViewAdapter(this, successDataList, "SuccessData");
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("국가 정보")) {
            listAdapter = new ListViewAdapter(this, nationDataList, "NationData");
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("무역 사기 사례")) {
            listAdapter = new ListViewAdapter(this, scamDataList, "ScamData");
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("상품 DB")) {
            listAdapter = new ListViewAdapter(this, productDataList, "ProductData");
            listView.setAdapter(listAdapter);
        }
        
        // 리스트뷰에 있는 데이터 클릭시 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailController.class);
                Object item = listView.getAdapter().getItem(i);

                if(menuTitle.getText().equals("해외 시장 뉴스")) {
                    NewsData data = (NewsData) item;
                    intent.putExtra("item",  data);
                }
                else if(menuTitle.getText().equals("기업 성공 사례")) {
                    SuccessData data = (SuccessData) item;
                    intent.putExtra("item",  data);
                }

                else if(menuTitle.getText().equals("국가 정보")) {
                    NationData data = (NationData) item;
                    intent.putExtra("item",  data);
                }
                else if(menuTitle.getText().equals("무역 사기 사례")) {
                    ScamData data = (ScamData) item;
                    intent.putExtra("item",  data);
                }
                else if(menuTitle.getText().equals("상품 DB")) {
                    ProductData data = (ProductData) item;
                    intent.putExtra("item",  data);
                }
                intent.putExtra("category", menuTitle.getText());

                startActivity(intent);
            }
        });
    }

    public void initializeNewsData() {
        newsDataList = new ArrayList<>();
        NewsData newsData = new NewsData("", "");
        newsDataList.add(newsData);
    }
}