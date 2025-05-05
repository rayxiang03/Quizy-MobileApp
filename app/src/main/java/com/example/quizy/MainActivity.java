package com.example.quizy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
    private TextView questionTextView;
    private Button trueButton, falseButton;
    private Question question;
    private User user; // Add User object
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionTextView = findViewById(R.id.question_text_view);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
// Initialize a question
        question = new Question("The sky is blue.", true);
        user = new User("Faris"); //Added a name
        questionTextView.setText(question.getQuestionText());
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
    }
    private void checkAnswer(boolean userAnswer) {

        if (userAnswer == question.isAnswerTrue()) {
            Toast.makeText(this, user.getName()+ " , Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, user.getName()+ " , Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }
}

