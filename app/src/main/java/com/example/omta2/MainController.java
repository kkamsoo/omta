package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends AppCompatActivity {
    Context context;
    Spinner nationSpinner, industrySpinner, tradeSpinner;
    Button backButton;

    ArrayList<NewsData> newsDataList;
    ArrayList<SuccessData> successDataList;
    ArrayList<NationData> nationDataList;
    ArrayList<ScamData> scamDataList;
    ArrayList<ProductData> productDataList;

    ListView listView;
    ListViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        this.context = this;

        // 비동기 통신
        GetNewsAPI newsAPI = new GetNewsAPI("미국");
        GetSuccessAPI successAPI = new GetSuccessAPI("미국");
        GetScamAPI scamAPI = new GetScamAPI("미국");
        GetNationAPI nationAPI = new GetNationAPI(context, "VN");
        GetProductAPI productAPI = new GetProductAPI("미국");

        // 백그라운드 실행
        newsAPI.execute();
        successAPI.execute();
        scamAPI.execute();
        nationAPI.execute();
        productAPI.execute();

        // 상단 타이틀 텍스트 설정
        TextView menuTitle = (TextView) findViewById(R.id.menutitle);
        menuTitle.setText(getIntent().getStringExtra("menuTitle"));

        // 뒤로가기 버튼 이벤트 처리
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(v -> onBackPressed());
        
        // 리스트뷰 생성
        listView = (ListView) findViewById(R.id.listView);

        // 카테고리별 데이터리스트 초기값 설정
        newsDataList = (ArrayList<NewsData>) getIntent().getSerializableExtra("NewsList");
        successDataList = (ArrayList<SuccessData>) getIntent().getSerializableExtra("SuccessList");
        nationDataList = (ArrayList<NationData>) getIntent().getSerializableExtra("NationList");
        scamDataList = (ArrayList<ScamData>) getIntent().getSerializableExtra("ScamList");
        productDataList = (ArrayList<ProductData>) getIntent().getSerializableExtra("ProductList");

        if(menuTitle.getText().equals("해외 시장 뉴스")) {
            listAdapter = new ListViewAdapter(context, newsDataList);
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("기업 성공 사례")) {
            listAdapter = new ListViewAdapter(context, successDataList);
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("국가 정보")) {
            listAdapter = new ListViewAdapter(context, nationDataList);
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("무역 사기 사례")) {
            listAdapter = new ListViewAdapter(context, scamDataList);
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("상품 DB")) {
            listAdapter = new ListViewAdapter(context, productDataList);
            listView.setAdapter(listAdapter);
        }
        // 탭 이벤트 처리
        TabLayout tabLayout = findViewById(R.id.tablayout);
        int tabIndex = getIntent().getIntExtra("TabIndex", 0); // 탭 인덱스 가져오기
        tabLayout.getTabAt(tabIndex).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    runOnUiThread(() -> {
                        listAdapter.data = newsAPI.newsList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
                else if(tab.getPosition() == 1) {
                    runOnUiThread(() -> {
                        listAdapter.data = successAPI.successList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
                else if(tab.getPosition() == 2) {
                    runOnUiThread(() -> {
                        listAdapter.data = scamAPI.scamList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
                else if(tab.getPosition() == 3) {
                    runOnUiThread(() -> {
                        listAdapter.data = nationAPI.nationList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
                else if(tab.getPosition() == 4) {
                    runOnUiThread(() -> {
                        listAdapter.data = productAPI.productList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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
                if(position > 0) {
                    String nation = nationSpinner.getItemAtPosition(position).toString();
                    GetNewsAPI newsAPI = new GetNewsAPI(nation);
                    newsAPI.nation = nation;
                    newsAPI.execute();

                    runOnUiThread(() -> {
                        listAdapter.data = newsAPI.newsList;
                        listAdapter.notifyDataSetChanged();
                        listView.setAdapter(listAdapter);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // 리스트뷰에 있는 데이터 클릭시 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListDetailController.class);
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
}