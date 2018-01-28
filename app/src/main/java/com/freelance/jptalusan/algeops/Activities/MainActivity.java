package com.freelance.jptalusan.algeops.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;

//http://stackoverflow.com/questions/22274545/add-drawable-in-relative-layout-programmatically
public class MainActivity extends BaseOpsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chooseAdd = (Button) findViewById(R.id.chooseAdd);
        Button chooseSub = (Button) findViewById(R.id.chooseSubtract);
        Button reset = (Button) findViewById(R.id.reset);

        reset.setVisibility(View.VISIBLE);

        chooseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(addIntent);
            }
        });

        chooseSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SubtractActivity.class);
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(addIntent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "Add and subtract levels have been reset to level 1.";
                Spannable centeredText = new SpannableString(text);
                centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                        0, text.length() - 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                Toast.makeText(MainActivity.this, centeredText, Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
                prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                prefs.edit().putInt(Constants.ADD_LEVEL, Constants.LEVEL_1).apply();
                prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, 0).apply();
            }
        });
    }
}
