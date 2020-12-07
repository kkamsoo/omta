package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    APIController apiController = new APIController(); // API컨트롤러 객체 생성;
    ArrayList<NewsData> newsDataList;
    ArrayList<SuccessData> successDataList;
    ArrayList<NationData> nationDataList;
    ArrayList<ScamData> scamDataList;
    ArrayList<ProductData> productDataList;

    ListView listView;
    ListViewAdapter newsListAdapter;
    ListViewAdapter nationListAdapter;
    ListViewAdapter successListAdapter;
    ListViewAdapter scamListAdapter;
    ListViewAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        new Thread(() -> {
            try {
                newsDataList = apiController.getNewsFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                successDataList = apiController.getSuccessFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        loagindDialog = ProgressDialog.show(this, "Connecting",
                "Loading. Please wait...", true, false);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    nationDataList = apiController.getNationFromAPI("VN");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        });
        thread.start();

        new Thread(() -> {
            try {
                scamDataList = apiController.getScamFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                productDataList = apiController.getProductFromAPI("미국");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        this.context = this;
        // 상단 타이틀 텍스트 설정
        TextView menuTitle = (TextView) findViewById(R.id.menutitle);
        menuTitle.setText(getIntent().getStringExtra("menuTitle"));

        // 뒤로가기 버튼 이벤트 처리
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(v -> onBackPressed());

        // 리스트뷰 생성
        listView = (ListView) findViewById(R.id.listView);

        // 탭 이벤트 처리
        TabLayout tabLayout = findViewById(R.id.tablayout);
        int tabIndex = getIntent().getIntExtra("TabIndex", 0); // 탭 인덱스 가져오기
        tabLayout.getTabAt(tabIndex).select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println(tab.getPosition() + "번");
                if(tab.getPosition() == 0) {
                    newsListAdapter = new ListViewAdapter(context, newsDataList, "NewsData");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsListAdapter.data = newsDataList;
                            newsListAdapter.notifyDataSetChanged();
                            listView.setAdapter(newsListAdapter);
                        }
                    });
                }
                else if(tab.getPosition() == 1) {
                    successListAdapter = new ListViewAdapter(context, successDataList, "SuccessData");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            successListAdapter.data = successDataList;
                            successListAdapter.notifyDataSetChanged();
                            listView.setAdapter(successListAdapter);
                        }
                    });
                }
                else if(tab.getPosition() == 2) {
                    scamListAdapter = new ListViewAdapter(context, scamDataList, "ScamData");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            scamListAdapter.data = scamDataList;
                            scamListAdapter.notifyDataSetChanged();
                            listView.setAdapter(scamListAdapter);
                        }
                    });
                }
                else if(tab.getPosition() == 3) {
                    nationListAdapter = new ListViewAdapter(context, nationDataList, "NationData");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nationListAdapter.data = nationDataList;
                            nationListAdapter.notifyDataSetChanged();
                            listView.setAdapter(nationListAdapter);
                        }
                    });
                }
                else if(tab.getPosition() == 4) {
                    productListAdapter = new ListViewAdapter(context, productDataList, "ProductData");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productListAdapter.data = productDataList;
                            productListAdapter.notifyDataSetChanged();
                            listView.setAdapter(productListAdapter);
                        }
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


        if(menuTitle.getText().equals("해외 시장 뉴스")) {
            newsListAdapter = new ListViewAdapter((Context) this, newsDataList, "NewsData");
            listView.setAdapter(newsListAdapter);
        }
        else if(menuTitle.getText().equals("기업 성공 사례")) {
            successListAdapter = new ListViewAdapter((Context) this, successDataList, "SuccessData");
            listView.setAdapter(successListAdapter);
        }
        else if(menuTitle.getText().equals("국가 정보")) {
            nationListAdapter = new ListViewAdapter((Context) this, nationDataList, "NationData");
            listView.setAdapter(nationListAdapter);
        }
        else if(menuTitle.getText().equals("무역 사기 사례")) {
            scamListAdapter = new ListViewAdapter((Context) this, scamDataList, "ScamData");
            listView.setAdapter(scamListAdapter);
        }
        else if(menuTitle.getText().equals("상품 DB")) {
            productListAdapter = new ListViewAdapter((Context) this, productDataList, "ProductData");
            listView.setAdapter(productListAdapter);
        }
        
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
    private ProgressDialog loagindDialog; // Loading Dialog


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            loagindDialog.dismiss();
        }
    };
}