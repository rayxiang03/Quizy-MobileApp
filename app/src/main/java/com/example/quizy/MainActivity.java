package com.example.quizy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
    private static final String TAG = "QuizyApp";
    private TextView questionTextView;
    private Button trueButton, falseButton, prevButton;
    private Question[] questionBank = new Question[] {
            new Question("The sky is blue.", true),
            new Question("2 + 2 equals 5.", false),
            new Question("The earth is flat.", false),
            new Question("Fire is cold.", false),
            new Question("Water boils at 100°C.", true)
    };
    private int currentIndex = 0;
    private int score = 0;
    private boolean[] answeredQuestions = new boolean[5]; // Track which questions have been answered

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        prevButton = findViewById(R.id.prev_button);
        
        updateQuestion();
        
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                moveToNextQuestion();
            }
        });
        
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                moveToNextQuestion();
            }
        });
        
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPrevQuestion();
            }
        });
    }
    
    private void updateQuestion() {
        questionTextView.setText(questionBank[currentIndex].getQuestionText());
    }
    
    private void moveToNextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.length;
        Log.d(TAG, "Current index: " + currentIndex);
        updateQuestion();
        Toast.makeText(this, "Next Question Loading...", Toast.LENGTH_SHORT).show();
        
        // Check if quiz is completed
        checkQuizCompletion();
    }
    
    private void moveToPrevQuestion() {
        currentIndex = (currentIndex - 1 + questionBank.length) % questionBank.length;
        Log.d(TAG, "Current index: " + currentIndex);
        updateQuestion();
        Toast.makeText(this, "Previous Question Loading...", Toast.LENGTH_SHORT).show();
    }
    
    private void checkAnswer(boolean userAnswer) {
        boolean correctAnswer = questionBank[currentIndex].isAnswerTrue();
        
        // Only count score if this question hasn't been answered before
        if (!answeredQuestions[currentIndex]) {
            answeredQuestions[currentIndex] = true;
            if (userAnswer == correctAnswer) {
                score++;
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Answer was Correct. Score: " + score);
            } else {
                Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Answer was Incorrect. Score: " + score);
            }
        } else {
            // Question already answered, just show feedback
            if (userAnswer == correctAnswer) {
                Toast.makeText(this, "Correct! (Already answered)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Incorrect! (Already answered)", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    private void checkQuizCompletion() {
        // Check if all questions have been answered
        boolean allAnswered = true;
        for (boolean answered : answeredQuestions) {
            if (!answered) {
                allAnswered = false;
                break;
            }
        }
        
        if (allAnswered) {
            // Quiz completed, navigate to ResultActivity
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("QUIZ_SCORE", score);
            intent.putExtra("TOTAL_QUESTIONS", questionBank.length);
            startActivity(intent);
            finish(); // Close MainActivity to prevent back navigation
        }
    }
}