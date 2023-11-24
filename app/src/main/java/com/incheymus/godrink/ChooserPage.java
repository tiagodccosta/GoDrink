package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class ChooserPage extends AppCompatActivity {

    private SeekBar rangeBar;
    private TextView rangePlaceHolder;
    private Button btnFinnishChooser;
    private Button btnGoBack;
    private Button btnError;
    private CardView errorPopUp;
    private ListView preferencesListView;

    private String[] preferencesList = {"Terrace Bars", "Karaoke Bars", "Irish Pubs", "Rooftop Bars",
            "Uncovered Nightclubs", "Techno Nightclubs", "Funk Nightclubs"
            ,"HappyHour Bars", "Popular Nightclubs", "After Party Nightclubs"};

    private String[] userPreferences;
    private boolean isSelected = false;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_page);

        rangeBar = (SeekBar) findViewById(R.id.rangeBar);
        rangePlaceHolder = (TextView) findViewById(R.id.rangeIndicator);
        btnFinnishChooser = (Button) findViewById(R.id.btnFinnishChooser);
        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        btnError = (Button) findViewById(R.id.btnOkError);
        errorPopUp = (CardView) findViewById(R.id.errorPopUp);
        preferencesListView = (ListView) findViewById(R.id.preferencesListView);

        preferencesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_preferences_list_view, R.id.preference, preferencesList);
        preferencesListView.setAdapter(arrayAdapter);

        preferencesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isSelected) {
                    view.setBackgroundResource(R.color.background);
                    isSelected = true;
                } else if(isSelected){
                    view.setBackgroundResource(R.color.white);
                    isSelected = false;
                }
            }
        });


        rangeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int range, boolean b) {
                rangePlaceHolder.setText("Your range is " + range + "KM");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorPopUp.setVisibility(View.GONE);
            }
        });

        btnFinnishChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooserPage.this, RecommendedPage.class));
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooserPage.this, LandingPage.class));
            }
        });
    }
}