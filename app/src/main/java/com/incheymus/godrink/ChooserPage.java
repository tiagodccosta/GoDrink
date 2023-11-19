package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ChooserPage extends AppCompatActivity {

    private SeekBar rangeBar;
    private TextView rangePlaceHolder;
    private ScrollView scrollView;
    private Button btnFinnishChooser;
    private Button btnGoBack;
    private Button btnError;
    private CardView errorPopUp;
    private LinearLayout chooserPageScrollViewLinearLayout;

    private Boolean isSelected = true;
    private int counterPreferences = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_page);

        rangeBar = (SeekBar) findViewById(R.id.rangeBar);
        rangePlaceHolder = (TextView) findViewById(R.id.rangeIndicator);
        scrollView = (ScrollView) findViewById(R.id.placesToChooseScrollView);
        btnFinnishChooser = (Button) findViewById(R.id.btnFinnishChooser);
        btnGoBack = (Button) findViewById(R.id.btnGoBack);
        btnError = (Button) findViewById(R.id.btnOkError);
        errorPopUp = (CardView) findViewById(R.id.errorPopUp);
        chooserPageScrollViewLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutChooserPage);


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
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                //for(int i = 0; i < chooserPageScrollViewLinearLayout.getChildCount(); i++) {
                    //TextView child = (TextView) chooserPageScrollViewLinearLayout.getChildAt(i);
                    //if(child.getBackground().getConstantState() == btnFinnishChooser.getBackground().getConstantState()) {
                        //counterPreferences++;
                //}
                //}
                if(counterPreferences > 3 || counterPreferences == 0) {
                    errorPopUp.setVisibility(View.VISIBLE);
                } else {
                    startActivity(new Intent(ChooserPage.this, RecommendedPage.class));
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


    public void changeColorIfSelected(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSelected) {
                    view.setBackgroundResource(R.color.background);
                    isSelected = true;
                    counterPreferences++;
                } else {
                    view.setBackgroundResource(R.color.white);
                    isSelected = false;
                    counterPreferences--;
                }
            }
        });
        System.out.println("Number of Preferences: " + counterPreferences);
    }
}