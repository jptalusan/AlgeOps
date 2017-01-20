package com.freelance.jptalusan.algeops.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

public class AddActivity extends BaseOpsActivity {
    private Button newQuestion;
    private TextView firstPartEq;
    private TextView secondPartEq;
    private String firstPart;
    private String secondPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newQuestion     = (Button) findViewById(R.id.newQuestionButton);
        firstPartEq     = (TextView) findViewById(R.id.firstEqTextView);
        secondPartEq    = (TextView) findViewById(R.id.secondEqTextView);

        newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Equation eq = EquationGeneration.generateEquation("MAIN");
                firstPart = eq.getPart(1);
                secondPart = eq.getPart(2);

                firstPartEq.setText(firstPart);
                secondPartEq.setText(secondPart);
            }
        });
    }
}
