package com.example.quizy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";
    private TextView scoreTextView;
    private Button restartButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        // Initialize UI components
        scoreTextView = findViewById(R.id.score_text_view);
        restartButton = findViewById(R.id.restart_button);
        
        // Receive quiz score from intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("QUIZ_SCORE", 0);
        int totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 5);
        
        // Display the score
        String scoreText = "Your score: " + score + "/" + totalQuestions;
        scoreTextView.setText(scoreText);
        
        Log.d(TAG, "Quiz completed with score: " + score + "/" + totalQuestions);
        
        // Set up restart button click listener
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to restart the quiz
                Intent restartIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(restartIntent);
                finish(); // Close ResultActivity
            }
        });
    }
} 