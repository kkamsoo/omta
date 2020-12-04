package com.example.omta2;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;

public class NewsController extends AppCompatActivity {
    EditText edit;
    TextView newsTitle, newsContent;
    EditText selectNation;
    XmlPullParser xpp;
    Spinner spinner;

    String nation = null;
    String newsTitleData, newsContentData;
    AlertDialog.Builder myAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form);

        spinner = findViewById(R.id.spinner);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this, R.array.nations, android.R.layout.simple_spinner_dropdown_item);
        //R.array.test는 저희가 정의해놓은 1월~12월 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter); //어댑터에 연결해줍니다.


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        selectNation = findViewById(R.id.selectnation);
        newsTitle = (TextView)findViewById(R.id.newstitle);
        newsContent = (TextView)findViewById(R.id.newscontent);
        this.myAlertBuilder = new AlertDialog.Builder(NewsController.this);
        this.myAlertBuilder.setTitle("알림");
    }

    public void enterNation(View v){
        myAlertBuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Pressed Cancle",
                        Toast.LENGTH_SHORT).show();
            }
        });

        switch (v.getId()){
            case R.id.enternation:
                nation = selectNation.getText().toString();
                runNews();
                break;
        }
    }

    public void runNews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getNews(nation);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(newsTitleData == null || newsContentData == null) newsTitle.setText("다시 검색해주세요.");
                        else {
                            newsTitle.setText(newsTitleData);
                            newsContent.setText(newsContentData);
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(selectNation.getWindowToken(), 0);
                        }
                    }
                });
            }
        }).start();
    }

    public void getNews(String nation) throws IOException {
        String key = "W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl = "http://apis.data.go.kr/B410001/ovseaMrktNewsService/ovseaMrktNews?ServiceKey=" + key + "&type=xml&numOfRows=1&search1=" + nation;
        try {
            Connection conn = Jsoup.connect(queryUrl);
            Document doc = conn.get();
            Elements newsTitle = doc.select("newsTitl");
            newsTitleData = newsTitle.text();

            Elements eles = doc.select("newsBdt");
            for (Element ele : eles) {
                Elements subnode = ele.select("data");
                newsContentData += subnode.text();
            }
            SpannableString spanText = new SpannableString(Html.fromHtml(newsContentData, Html.FROM_HTML_MODE_COMPACT));
            newsContentData = spanText.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
        /*
        try{
            URL url= new URL(queryUrl);
            InputStream is= url.openStream();

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") );

            String tag;
            xpp.next();

            int eventType= xpp.getEventType();
            boolean titleCheck = false;
            boolean contentCheck = false;

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();

                        if (titleCheck == false && tag.equals("newsTitl")) titleCheck = true;
                        if (contentCheck == false && tag.equals("newsBdt")) contentCheck = true;

                        if (titleCheck == true && tag.equals("data")) {
                            xpp.next();
                            Spanned tmp = Html.fromHtml(xpp.getText());
                            newsTitleData = tmp.toString() + "\n";
                            titleCheck = false;
                        }
                        if (contentCheck == true && tag.equals("data")) {
                            xpp.next();
                            Spanned tmp = Html.fromHtml(xpp.getText());
                            newsContentData = "\n" + tmp.toString();
                            contentCheck = false;
                        }
                    case XmlPullParser.TEXT:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    */
}