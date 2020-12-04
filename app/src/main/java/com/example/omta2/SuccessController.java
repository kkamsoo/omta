package com.example.omta2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SuccessController extends AppCompatActivity {
    EditText edit;
    TextView newsTitle, newsContent;
    EditText selectNation;
    XmlPullParser xpp;

    String nation = null;
    String newsTitleData, newsContentData;
    AlertDialog.Builder myAlertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_form);

        newsTitle = (TextView)findViewById(R.id.newstitle);
        newsContent = (TextView)findViewById(R.id.newscontent);
        this.myAlertBuilder = new AlertDialog.Builder(SuccessController.this);
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
                    getSuccessStory(nation);
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

    public void getSuccessStory(String nation) throws IOException {
        String key ="W%2BPdBC2wddBhjfEMD4iaIw2V64C9eF40jJZU2Z8R669h9As3wQy3r7LLv0GCV%2FSxq4P7LM4P9T4y0kR%2FM8M8iA%3D%3D";
        String queryUrl="http://apis.data.go.kr/B410001/compSucsCaseService/compSucsCase?ServiceKey=" + key + "&type=xml&numOfRows=1&search1=" + nation;

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

                        if (titleCheck == false && tag.equals("titl")) titleCheck = true;
                        if (contentCheck == false && tag.equals("bdtCntnt")) contentCheck = true;

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
}
