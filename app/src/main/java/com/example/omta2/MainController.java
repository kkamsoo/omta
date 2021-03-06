package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class MainController extends AppCompatActivity {
    Context context;
    Spinner nationSpinner;
    Button backButton;

    ListView listView;
    ListViewAdapter listAdapter;
    ArrayAdapter nationAdapter;

    // 비동기 통신 API 변수
    GetNewsAPI newsAPI;
    GetSuccessAPI successAPI;
    GetScamAPI scamAPI;
    GetNationAPI nationAPI;
    GetProductAPI productAPI;

    private int nationPosition;
    private int tabIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        this.context = this;

        // 비동기 통신 생성자
        newsAPI = new GetNewsAPI(context, "");
        successAPI = new GetSuccessAPI(context, "");
        scamAPI = new GetScamAPI(context, "");
        nationAPI = new GetNationAPI(context, "VN");
        productAPI = new GetProductAPI(context, "");

        // 백그라운드 실행
        newsAPI.execute();
        successAPI.execute();
        scamAPI.execute();
        nationAPI.execute();
        productAPI.execute();

        // 상단 타이틀 텍스트 설정
        TextView menuTitle = findViewById(R.id.menutitle);
        menuTitle.setText(getIntent().getStringExtra("menuTitle"));

        // 리스트뷰 생성
        listView = findViewById(R.id.listView);

        // 초기값 설정
        if(menuTitle.getText().equals("해외 시장 뉴스")) {
            listAdapter = new ListViewAdapter(context, newsAPI.newsList);
            newsAPI.listAdapter = listAdapter;
            listAdapter.notifyDataSetChanged();
            listView.setAdapter(listAdapter);
        }
        else if(menuTitle.getText().equals("기업 성공 사례")) {
            listAdapter = new ListViewAdapter(context, successAPI.successList);
            successAPI.listAdapter = listAdapter;
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
        else if(menuTitle.getText().equals("국가 정보")) {
            listAdapter = new ListViewAdapter(context, nationAPI.nationList);
            nationAPI.listAdapter = listAdapter;
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
        else if(menuTitle.getText().equals("무역 사기 사례")) {
            listAdapter = new ListViewAdapter(context, scamAPI.scamList);
            scamAPI.listAdapter = listAdapter;
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
        else if(menuTitle.getText().equals("상품 DB")) {
            listAdapter = new ListViewAdapter(context, productAPI.productList);
            productAPI.listAdapter = listAdapter;
            listView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
        }
        // 뒤로가기 버튼 이벤트 처리
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(v -> onBackPressed());

        // 스피너
        nationSpinner = findViewById(R.id.nationspinner);

        // 제목, 날짜입력 텍스트 이벤트 처리
        EditText titleText = findViewById(R.id.titletext);
        EditText dateText = findViewById(R.id.datetext);

        titleText.setOnEditorActionListener((textView, i, keyEvent) -> {
            // 국가가 선택되지 않았으면 토스트메시지 출력
            if (nationPosition == 0) {
                Toast.makeText(context, "국가를 선택해주세요.", Toast.LENGTH_SHORT).show();
                textView.clearFocus();
                textView.setFocusable(false);
                textView.setFocusableInTouchMode(true);
                textView.setFocusable(true);

                return true;
            }

            String nation = nationSpinner.getItemAtPosition(nationPosition).toString();
            String subString;
            // 국가선택 문자열 처리
            if(tabIndex == 3) subString = nation.substring(nation.length()-3, nation.length()-1);
            else subString = nation.substring(0, nation.length()-4);
            getAPI(menuTitle.getText().toString(), subString, titleText.getText().toString(), dateText.getText().toString(), nationPosition);

            return true;
        });

        dateText.setOnEditorActionListener((textView, i, keyEvent) -> {
            // 국가가 선택되지 않았으면 토스트메시지 출력
            if (nationPosition == 0) {
                Toast.makeText(context, "국가를 선택해주세요.", Toast.LENGTH_SHORT).show();
                textView.clearFocus();
                textView.setFocusable(false);
                textView.setFocusableInTouchMode(true);
                textView.setFocusable(true);

                return true;
            }
            String nation = nationSpinner.getItemAtPosition(nationPosition).toString();
            String subString;
            // 국가선택 문자열 처리
            if(tabIndex == 3) subString = nation.substring(nation.length()-3, nation.length()-1);
            else subString = nation.substring(0, nation.length()-4);
            getAPI(menuTitle.getText().toString(), subString, titleText.getText().toString(), dateText.getText().toString(), nationPosition);

            return true;
        });

        // 탭 이벤트 처리
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabIndex = getIntent().getIntExtra("TabIndex", 0); // 탭 인덱스 가져오기
        tabLayout.getTabAt(tabIndex).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    runOnUiThread(() -> {
                        menuTitle.setText("해외 시장 뉴스"); // 메뉴타이틀 설정
                        tabIndex = 0;
                        // 스피너 초기값 설정
                        nationSpinner.setSelection(0);
                        titleText.setText("");
                        dateText.setText("");
                        listAdapter.data = newsAPI.newsList;
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    });
                }
                else if(tab.getPosition() == 1) {
                    runOnUiThread(() -> {
                        menuTitle.setText("기업 성공 사례");
                        tabIndex = 1;
                        // 스피너 초기값 설정
                        nationSpinner.setSelection(0);
                        titleText.setText("");
                        dateText.setText("");
                        listAdapter.data = successAPI.successList;
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    });
                }
                else if(tab.getPosition() == 2) {
                    runOnUiThread(() -> {
                        menuTitle.setText("무역 사기 사례");
                        tabIndex = 2;
                        // 스피너 초기값 설정
                        nationSpinner.setSelection(0);
                        titleText.setText("");
                        dateText.setText("");
                        listAdapter.data = scamAPI.scamList;
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    });
                }
                else if(tab.getPosition() == 3) {
                    runOnUiThread(() -> {
                        menuTitle.setText("국가 정보");
                        tabIndex = 3;
                        // 스피너 초기값 설정
                        nationSpinner.setSelection(0);
                        titleText.setText("");
                        dateText.setText("");
                        listAdapter.data = nationAPI.nationList;
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    });
                }
                else if(tab.getPosition() == 4) {
                    runOnUiThread(() -> {
                        menuTitle.setText("상품 DB");
                        tabIndex = 4;
                        // 스피너 초기값 설정
                        nationSpinner.setSelection(0);
                        titleText.setText("");
                        dateText.setText("");
                        listAdapter.data = productAPI.productList;
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
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

        // 국가 선택 스피너 어댑터
        nationAdapter = ArrayAdapter.createFromResource(context, R.array.englishNations, R.layout.spinner_layout);
        nationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        nationSpinner.setAdapter(nationAdapter);

        nationSpinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        titleText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        nationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    nationPosition = position;
                    String nation = nationSpinner.getItemAtPosition(nationPosition).toString();
                    String subString;
                    // 국가선택 문자열 처리
                    if(tabIndex == 3) subString = nation.substring(nation.length()-3, nation.length()-1);
                    else subString = nation.substring(0, nation.length()-4);
                    
                    getAPI(menuTitle.getText().toString(), subString, "", "", position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // 리스트뷰에 있는 데이터 클릭시 이벤트 처리
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
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
        });
    }
    // API 호출 메소드
    public void getAPI(String apiName, String nation, String title, String date, int position) {
        // 해외 시장뉴스 
        if(apiName.equals("해외 시장 뉴스") && position > 0) {
            // 백그라운드에서 실행되던 NewsAPI 종료
            newsAPI.cancel(false);
            // 새로운 NewsAPI 호출
            GetNewsAPI newsAPI = new GetNewsAPI(context, listAdapter, nation, title, date);
            newsAPI.execute();
        }
        // 성공사례
        else if(apiName.equals("기업 성공 사례") && position > 0) {
            successAPI.cancel(false);
            GetSuccessAPI successAPI = new GetSuccessAPI(context, listAdapter, nation, title, date);
            successAPI.execute();
        }
        // 국가 정보
        else if(apiName.equals("국가 정보") && position > 0) {
            nationAPI.cancel(false);
            GetNationAPI nationAPI = new GetNationAPI(context, listAdapter, nation);
            nationAPI.execute();
        }
        // 무역 사기
        else if(apiName.equals("무역 사기 사례") && position > 0) {
            scamAPI.cancel(false);
            GetScamAPI scamAPI = new GetScamAPI(context, listAdapter, nation, title, date);
            scamAPI.execute();
        }
        // 상품 DB
        else if(apiName.equals("상품 DB") && position > 0) {
            productAPI.cancel(false);
            GetProductAPI productAPI = new GetProductAPI(context, listAdapter, nation, title, date);
            productAPI.execute();
        }
    }
}