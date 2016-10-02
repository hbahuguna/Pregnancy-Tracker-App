package com.example.hbahuguna.pregnancytipsntools.app.quiz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.R;
import com.example.hbahuguna.pregnancytipsntools.app.data.DataAdapter;
import com.example.hbahuguna.pregnancytipsntools.app.planner.PlannerFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by himanshu on 10/1/16.
 */
public class QuizFragment extends Fragment {
    public static final String QUIZ_URI = "QUIZ_URI";
    private static String TAG = "QuizFragment";

    List<Question> quesList;
    int score=0;
    int qid=0;
    Question nextQ;
    Question currentQ;
    TextView txtQuestion;
    TextView result;
    RadioButton rda, rdb, rdc, rdd, rde, rdf;
    Button butNext;
    private Uri mUri;
    private View mRootView;
    private int sexScore = 0;
    private int cognitiveScore = 0;
    private int appetiteScore = 0;
    private int bodyImageScore = 0;
    private int overAllScore = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(PlannerFragment.PLANNER_URI);
        }
        mRootView = inflater.inflate(R.layout.fragment_quiz, container, false);
        ButterKnife.bind(this.getActivity());
        DataAdapter mDbHelper = new DataAdapter(this.getActivity());
        mDbHelper.createDatabase();
        mDbHelper.open();
        quesList = new ArrayList<>();
        Cursor cursor = mDbHelper.getData("_id, question, option1, option2, option3, option4, option5, category, option6 ", "quest");
        if (cursor.moveToFirst()){
            do{
                Question q = new Question(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8));
                quesList.add(q);
           } while(cursor.moveToNext());
        }

        result=(TextView)mRootView.findViewById(R.id.textResult);
        result.setMovementMethod(new ScrollingMovementMethod());
        result.setText("This quiz is about understanding yourself and all the experiences that make up pregnancy." +
                "This quiz is designed to help you get a sense of how you are experiencing your pregnancy: " +
                "how you feel about yourself, your journey and the promise of parenthood." +
                "An overall score will be calculated at the end to help you navigate the pregnancy." +
                "Take the quiz as often as you like, especially as you grow and learn about yourself during these nine months." +
                "Your score may change along the way--and thats because you will too.");
        txtQuestion=(TextView)mRootView.findViewById(R.id.textView1);
        rda=(RadioButton)mRootView.findViewById(R.id.radio0);
        rdb=(RadioButton)mRootView.findViewById(R.id.radio1);
        rdc=(RadioButton)mRootView.findViewById(R.id.radio2);
        rdd=(RadioButton)mRootView.findViewById(R.id.radio3);
        rde=(RadioButton)mRootView.findViewById(R.id.radio4);
        rdf=(RadioButton)mRootView.findViewById(R.id.radio5);
        butNext=(Button)mRootView.findViewById(R.id.button1);
        txtQuestion.setVisibility(View.GONE);
        rda.setVisibility(View.GONE);
        rdb.setVisibility(View.GONE);
        rdc.setVisibility(View.GONE);
        rdd.setVisibility(View.GONE);
        rde.setVisibility(View.GONE);
        rdf.setVisibility(View.GONE);
        butNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qid < 19){
                    nextQ=quesList.get(qid);
                    if(qid > 0)
                        currentQ=quesList.get(qid-1);
                    if(currentQ != null) {
                        RadioGroup grp=(RadioGroup) mRootView.findViewById(R.id.radioGroup1);
                        RadioButton answer=(RadioButton)mRootView.findViewById(grp.getCheckedRadioButtonId());

                        if (currentQ.getCategory().equals("sex")) {
                            sexScore += currentQ.getScore(answer.getText().toString());
                        } else if (currentQ.getCategory().equals("cognitive")) {
                            cognitiveScore += currentQ.getScore(answer.getText().toString());
                        } else if (currentQ.getCategory().equals("appetite")) {
                            if (qid != 11) {
                                appetiteScore += currentQ.getScore(answer.getText().toString());
                            } else {
                                appetiteScore *= currentQ.getScore(answer.getText().toString());
                            }
                        } else if (currentQ.getCategory().equals("bodyImage")) {
                            bodyImageScore += currentQ.getScore(answer.getText().toString());
                        }
                    }
                    setQuestionView();
                } else {
                    overAllScore = sexScore * 3 + cognitiveScore * 3 + appetiteScore + bodyImageScore ;
                    setResultView();
                }
            }
        });
        return mRootView;
    }

    private void setQuestionView() {
        result.setVisibility(View.GONE);
        txtQuestion.setVisibility(View.VISIBLE);
        rda.setVisibility(View.VISIBLE);
        rdb.setVisibility(View.VISIBLE);
        rdc.setVisibility(View.VISIBLE);
        rdd.setVisibility(View.VISIBLE);
        rde.setVisibility(View.VISIBLE);
        rdf.setVisibility(View.VISIBLE);
        txtQuestion.setText(nextQ.getQUESTION());
        rda.setText(nextQ.getOPTA());
        rdb.setText(nextQ.getOPTB());
        rdc.setText(nextQ.getOPTC());
        rdd.setText(nextQ.getOPTD());
        rde.setText(nextQ.getOPTE());
        rdf.setVisibility(View.GONE);
        if (nextQ.getOPTF() != null) {
            rdf.setVisibility(View.VISIBLE);
            rdf.setText(nextQ.getOPTF());
        }
        qid++;
    }

    private void setResultView() {
        result.setVisibility(View.VISIBLE);
        String resultString = "YOUR SEX SCORE IS " + sexScore + ":\n";
        if(sexScore >= 3 && sexScore <= 6) {
            resultString += "Your interest in sex may have gone up since you got pregnant. This is due to the harmones.\n\n";
        } else if(sexScore >= 7 && sexScore <= 11) {
            resultString += "Your interest in sex is about what it was before you got pregnant. Enjoy!\n\n";
        } else {
            resultString += "You are showing less in interest in sex than you may used to be.If you are feeling a much " +
                    "lower interest in sex even during the second trimester, you might want to chaeckout some tips for " +
                    "adding sensuality into your life.\n\n";
        }
        resultString += "YOUR COGNITIVE SCORE IS " + cognitiveScore + ":\n";
        if(cognitiveScore >= 3 && cognitiveScore <= 6) {
            resultString += "Congrats! Even though you pregnant, you have sharp focus.\n\n";
        } else if(cognitiveScore >= 7 && cognitiveScore <= 11) {
            resultString += "You may feel less indecisive than before, and you may have trouble remembering things." +
                    "This is quite normal during pregnancy.\n\n";
        } else {
            resultString += "You seem to be having a lot trouble thinking since you got pregnant " +
                    "If you feel that you have slipped quite a bit, then you need to review aspects of your lifestyle\n\n";
        }
        resultString += "YOUR CRAVING AND APPETITE SCORE IS " + appetiteScore + ":\n";
        if(appetiteScore >= 4 && appetiteScore <= 30) {
            resultString += "You are eating well, If you feel some nausea, its quite normal.\n\n";
        } else if(appetiteScore >= 31 && appetiteScore <= 50) {
            resultString += "Nausea is affecting your eating.Its important to listen to your body." +
                    "You can make some simple diet changes to feel better.\n\n";
        } else if(appetiteScore >= 51 && appetiteScore <= 70) {
            resultString += "You are experiencing moderate nausea, which is affecting the way you eat." +
                    "You may be having some cravings. It may be time to work on you diet to give your baby the nutrients " +
                    " needed for healthy brain and body development.\n\n";
        } else {
            resultString += "Nausea is having a great affect on what you eat." +
                    "If you are in the first trimester remember its that when nausea is worst. You will need to make best " +
                    "choices about foods.\n\n";
        }
        resultString += "YOUR BODY IMAGE SCORE IS " + bodyImageScore + ":\n";
        if(bodyImageScore >= 8 && bodyImageScore <= 16) {
            resultString += "You dont seem to be much worried about your body. Make sure though that you are still in shape.\n\n";
        } else if(bodyImageScore >= 17 && bodyImageScore <= 32) {
            resultString += "You have a small amount of concern about your body image. Make sure that you eat balanced " +
                    "meals and nutritious foods." +
                    "Remember exercise is good for you and your baby.\n\n";
        } else {
            resultString += "You have quite a bit concern about your body." +
                    "Its really crucial that you exercise and eat balanced, nutritious meals during pregnancy.\n\n";
        }
        resultString += "YOUR OVERALL SCORE IS " + overAllScore + ":\n";
        if (overAllScore >= 29 && overAllScore <= 70) {
            resultString += "Congratulations! You have acheived some real balance in your pregnant life." +
                    "There is a lot ahead of you but you are quite centered so far";
        } else if (overAllScore > 70 && overAllScore <= 150) {
            resultString += "Like most pregnant women, there are days when you have it together and days when things seem beyond your grasp.";
        } else if (overAllScore > 150 && overAllScore <= 238) {
            resultString += "Between your nausea, your inablity to think straight, and your negative feelings about your body," +
                    "you are probably looking at pregnancy more like a never ending traffic jam than an olympic gymnastic event." +
                    "All the same, you are in the middle of one of the life's peak experiences." +
                    "You should take a look at the tips section of the app!";
        }
        result.setText(resultString);
        txtQuestion.setVisibility(View.GONE);
        rda.setVisibility(View.GONE);
        rdb.setVisibility(View.GONE);
        rdc.setVisibility(View.GONE);
        rdd.setVisibility(View.GONE);
        rde.setVisibility(View.GONE);
        rdf.setVisibility(View.GONE);
        butNext.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Quality Of Life Quiz");
    }
}
