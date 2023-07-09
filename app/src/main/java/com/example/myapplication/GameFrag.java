package com.example.myapplication;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameFrag extends Fragment {

    private TextView letterTextView, answerTextView;
    private char[] skyLetters = {'b', 'd', 'f', 'h', 'k', 'l', 't'};
    private char[] grassLetters = {'g', 'j', 'p', 'q', 'y'};
    private char[] rootLetters = {'a', 'c', 'e', 'i', 'm', 'n', 'o', 'r', 's', 'u', 'v', 'w', 'x', 'z'};
    private String answerString = "";
    private int questionCounter = 0;
    private DbHelper dbHelper;

    public GameFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        letterTextView = view.findViewById(R.id.letter_text_view);
        letterTextView.setText(getRandomLetter());

        answerTextView = view.findViewById(R.id.answer_text_view);

        Button skyButton = view.findViewById(R.id.sky_button);
        skyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Sky Letter");
            }
        });

        Button grassButton = view.findViewById(R.id.grass_button);
        grassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Grass Letter");
            }
        });

        Button rootButton = view.findViewById(R.id.root_button);
        rootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Root Letter");
            }
        });

        dbHelper = new DbHelper(getContext());

        return view;
    }

    private void checkAnswer(String selectedAnswer) {
        if (answerString.equals(selectedAnswer)) {
            answerTextView.setText("Awesome, your answer is right");
            dbHelper.addScore(new Score(questionCounter + 1, 1)); // Update the database with the score
        } else {
            answerTextView.setText("Incorrect! The answer is " + answerString);
            dbHelper.addScore(new Score(questionCounter + 1, 0)); // Update the database with the score
        }

        questionCounter++;

        if (questionCounter == 5) {
            dbHelper.close(); // Close the database connection
            // Move to the ResultFragment
            MainActivity activity = (MainActivity) requireActivity();
            activity.showResultFragment();
        } else {
            // Wait for 1 seconds and create a new question
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    letterTextView.setText(getRandomLetter());
                    answerTextView.setText("");
                }
            }, 1000); // 1000 milliseconds = 1 seconds
        }
    }

    private String getRandomLetter() {
        java.util.Random random = new java.util.Random();
        int category = random.nextInt(3);
        char letter;
        switch (category) {
            case 0:
                letter = skyLetters[random.nextInt(skyLetters.length)];
                answerString = "Sky Letter";
                break;
            case 1:
                letter = grassLetters[random.nextInt(grassLetters.length)];
                answerString = "Grass Letter";
                break;
            default:
                letter = rootLetters[random.nextInt(rootLetters.length)];
                answerString = "Root Letter";
                break;
        }
        return String.valueOf(letter);
    }
}
