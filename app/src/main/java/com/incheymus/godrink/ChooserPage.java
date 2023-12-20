package com.incheymus.godrink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooserPage extends AppCompatActivity {

    private SeekBar rangeBar;
    private TextView rangePlaceHolder;
    private Button btnFinnishChooser;
    private Button btnGoBack;
    private ListView preferencesListView;

    private String[] preferencesList = {"Terrace Bars", "Karaoke Bars", "Irish Pubs", "Rooftop Bars",
            "Uncovered Nightclubs", "Techno Nightclubs", "Funk Nightclubs"
            ,"HappyHour Bars", "Popular Nightclubs", "After Party Nightclubs"};

    private ArrayList<String> selectedList = new ArrayList<>();

    private int rangeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_page);

        rangeBar = (SeekBar) findViewById(R.id.rangeBar);
        rangePlaceHolder = (TextView) findViewById(R.id.rangeIndicator);
        btnFinnishChooser = (Button) findViewById(R.id.btnFinnishChooser);
        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        preferencesListView = (ListView) findViewById(R.id.preferencesListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_preferences_list_view, R.id.preference, preferencesList);
        preferencesListView.setAdapter(arrayAdapter);

        preferencesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedList.contains(preferencesList[i])) {
                    view.setBackgroundResource(R.color.white);
                    for (int j = 0; j < selectedList.size(); j++) {
                        if (selectedList.get(j).equals(preferencesList[i])) {
                            selectedList.remove(j);
                            return;
                        }
                    }
                } else {
                    view.setBackgroundResource(R.color.background);
                    selectedList.add(preferencesList[i]);
                }
            }
        });


        rangeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int range, boolean b) {
                rangeSelected = range;
                rangePlaceHolder.setText("Your range is " + range + "KM");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnFinnishChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooserPage.this, RecommendedPage.class);
                intent.putStringArrayListExtra("selectedList", selectedList);
                intent.putExtra("range", rangeSelected);

                if(selectedList.size() > 3 || selectedList.size() == 0) {
                    showErrorPopUp();
                } else {
                    startActivity(intent);
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooserPage.this, LandingPage.class));
            }
        });
    }

    public void showErrorPopUp() {
        LinearLayout errorLinearLayout = findViewById(R.id.errorLayout);
        View view = LayoutInflater.from(ChooserPage.this).inflate(R.layout.error_popup, errorLinearLayout);
        Button errorButton = view.findViewById(R.id.btnOkError);

        AlertDialog.Builder builder = new AlertDialog.Builder(ChooserPage.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}