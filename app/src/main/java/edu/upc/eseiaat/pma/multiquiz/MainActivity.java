package edu.upc.eseiaat.pma.multiquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int ids_answers[] = {
            R.id.answer1,R.id.aswer2,R.id.answer3,R.id.answer4
    };
    private int correct_answer;
    private int current_question;
    private boolean[] answeriscorrect;
    private int[] answer;
    private String[] all_questions;
    private TextView text_question;
    private RadioGroup group;
    private Button btn_check,btn_prev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_question = (TextView) findViewById(R.id.text_question);
        group = (RadioGroup) findViewById(R.id.rgroup);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_prev = (Button) findViewById(R.id.btn_prev);
        all_questions = getResources().getStringArray(R.array.All_questions);
        answeriscorrect = new boolean[all_questions.length];
        answer = new int[all_questions.length];
        for(int i=0; i<answer.length; i++){
            answer[i]=-1;
        }
        current_question = 0;
        showQuestion();


        //TODO:Cuando clican el boton deberia pasar ala siguiente pregunta


        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();

                if(current_question< all_questions.length-1){
                    current_question++;
                    showQuestion();}
                else{
                    int correctas=0, incorrectas=0;
                    for(boolean b: answeriscorrect){
                        if(b) correctas++;
                        else incorrectas++;
                    }
                    String resultado = String.format("Correctas : %d -- incorrectas: %d",correctas,incorrectas);
                    Toast.makeText(MainActivity.this, resultado , Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
                if(current_question>0){
                    current_question--;
                    showQuestion();
                }
            }
        });
        //TODO:Molaria tener un boton para pregunta anterior
    }

    private void checkAnswer() {
        int id = group.getCheckedRadioButtonId();
        int respuesta = -1;
        for (int i =0 ; i < ids_answers.length; i++) {
            if (ids_answers[i] == id) {
                respuesta = i;
            }
        }

        answeriscorrect[current_question] = (respuesta == correct_answer);
        answer[current_question] = respuesta;
    }

    private void showQuestion() {
        String q = all_questions[current_question];
        String[] parts = q.split(";");

        group.clearCheck();
        text_question.setText(parts[0]);
        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            String ans = parts[i+1];
            if(ans.charAt(0) == '*'){
                correct_answer = i;
                ans = ans.substring(1);
            }
            rb.setText(ans);
            if (answer[current_question]==i){
                rb.setChecked(true);
            }

            if(current_question==0){
                btn_prev.setVisibility(View.GONE);}
                else{
                    btn_prev.setVisibility(View.VISIBLE);
                }
            if(current_question == all_questions.length){
                btn_check.setText(R.string.Finish);
            }
            else{
                btn_check.setText(R.string.next);
            }
            }

        }

    }

