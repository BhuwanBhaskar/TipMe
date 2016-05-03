package com.android.bhuwan.tipme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final double STANDARD_TIP = 0.15;
    private static final double TOTAL_PERCENT = 100.0;
    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private double billAmount;
    private double customPercent = 0.18;
    private TextView amountDisplayTextView;
    private TextView customTipPercentTextView;
    private TextView percent15TipTextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountDisplayTextView = (TextView) findViewById(R.id.amountDisplayTextView);
        customTipPercentTextView = (TextView) findViewById(R.id.percentCustomTextView);
        percent15TipTextView = (TextView) findViewById(R.id.percent15TipTextView);
        total15TextView = (TextView) findViewById(R.id.total15TextView);
        tipCustomTextView = (TextView) findViewById(R.id.percentCustomTipTextView);
        totalCustomTextView = (TextView) findViewById(R.id.totalCustomTextView);

        amountDisplayTextView.setText(currencyFormat.format(billAmount));

        update15Percent();
        updateCustomPercent();

        EditText amountText = (EditText) findViewById(R.id.amountEditText);

        //it listens to new values as they are being entered in editText
        amountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try{
                    billAmount = Double.parseDouble(s.toString()) / TOTAL_PERCENT;
                }catch(NumberFormatException e){
                    billAmount = 0.0;
                }

                amountDisplayTextView.setText(currencyFormat.format(billAmount));
                update15Percent();
                updateCustomPercent();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        SeekBar customPercentSeekBar = (SeekBar) findViewById(R.id.tipSeekBar);

        //it listens to new values given by seek bar as it is dragged
        customPercentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customPercent = progress / TOTAL_PERCENT;
                updateCustomPercent();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateCustomPercent() {
        customTipPercentTextView.setText(percentFormat.format(customPercent));

        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));
    }

    private void update15Percent() {
        double percent15Tip = billAmount * STANDARD_TIP;
        double totalBill = billAmount + percent15Tip;

        percent15TipTextView.setText(currencyFormat.format(percent15Tip));
        total15TextView.setText(currencyFormat.format(totalBill));
    }

}
