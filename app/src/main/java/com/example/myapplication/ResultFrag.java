package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class ResultFrag extends Fragment {

    private LinearLayout resultLayout;
    private TextView finalResultTextView;
    private TextView finalResultTextViewRow, finalResultTextViewRow2, finalResultTextViewRow3, finalResultTextViewRow4, finalResultTextViewRow5;
    private DbHelper dbHelper;

    public ResultFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        finalResultTextViewRow = view.findViewById(R.id.result1);
        finalResultTextViewRow2 = view.findViewById(R.id.result2);
        finalResultTextViewRow3 = view.findViewById(R.id.result3);
        finalResultTextViewRow4 = view.findViewById(R.id.result4);
        finalResultTextViewRow5 = view.findViewById(R.id.result5);
        finalResultTextView = view.findViewById(R.id.final_result_text_view);
        dbHelper = new DbHelper(getActivity());

        List<Score> scores = dbHelper.getAllScores();

        // Calculate the final result
        int totalQuestions = scores.size();
        int correctAnswers = 0;
        for (Score score : scores) {
            if (score.getResult() == 1) {
                correctAnswers++;
            }
        }
        int finalResult = (correctAnswers * 100) / totalQuestions;

        // Display the final result
        String resultText = "Final Result: " + correctAnswers + "/" + totalQuestions + " (" + finalResult + "%)";
        finalResultTextView.setVisibility(View.VISIBLE);
        finalResultTextView.setText(resultText);

        int index = 0;
        for (Score score : scores) {
            if (index == 0) {
                finalResultTextViewRow.setVisibility(View.VISIBLE);
                finalResultTextViewRow.setText(getFormattedText(score));
            } else if (index == 1) {
                finalResultTextViewRow2.setVisibility(View.VISIBLE);
                finalResultTextViewRow2.setText(getFormattedText(score));
            } else if (index == 2) {
                finalResultTextViewRow3.setVisibility(View.VISIBLE);
                finalResultTextViewRow3.setText(getFormattedText(score));
            } else if (index == 3) {
                finalResultTextViewRow4.setVisibility(View.VISIBLE);
                finalResultTextViewRow4.setText(getFormattedText(score));
            } else if (index == 4) {
                finalResultTextViewRow5.setVisibility(View.VISIBLE);
                finalResultTextViewRow5.setText(getFormattedText(score));
            }
            index++;
        }

        return view;
    }

    private String getFormattedText(Score score) {
        String questionNumberText = "Question " + score.getQuestionNumber();
        String answerResultText = score.getResult() == 1 ? "Correct" : "Incorrect";
        return questionNumberText + " " + answerResultText;
    }
}
