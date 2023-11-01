package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class ChooserPage extends AppCompatActivity {

    private SeekBar rangeBar;
    private TextView rangePlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_page);

        rangeBar = (SeekBar) findViewById(R.id.rangeBar);
        rangePlaceHolder = (TextView) findViewById(R.id.rangeIndicator);

        rangeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int range, boolean b) {
                rangePlaceHolder.setText("Your range is " + range + "KM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}