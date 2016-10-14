package com.example.hbahuguna.pregnancytipsntools.app.quiz;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hbahuguna.pregnancytipsntools.app.R;
import com.example.hbahuguna.pregnancytipsntools.app.data.BabyContract;
import com.example.hbahuguna.pregnancytipsntools.app.planner.PlannerFragment;
import com.example.hbahuguna.pregnancytipsntools.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by himanshu on 10/1/16.
 */
public class QuizFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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
        Utils.toolBar(mRootView, (AppCompatActivity) getActivity());
        ButterKnife.bind(this.getActivity());


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
        Utils.showAd(mRootView);
        qid++;
    }

    private void setResultView() {
        result.setVisibility(View.VISIBLE);
        String resultString = "YOUR SEX SCORE IS " + sexScore + ":\n";
        if(sexScore >= 3 && sexScore <= 6) {
            resultString += getString(R.string.sex3to6);
        } else if(sexScore >= 7 && sexScore <= 11) {
            resultString += getString(R.string.sex7to11);
        } else {
            resultString += getString(R.string.sex11up);
        }
        resultString += "\n\nYOUR COGNITIVE SCORE IS " + cognitiveScore + ":\n";
        if(cognitiveScore >= 3 && cognitiveScore <= 6) {
            resultString += getString(R.string.cognitive3to6);
        } else if(cognitiveScore >= 7 && cognitiveScore <= 11) {
            resultString += getString(R.string.cognitive7to11);
        } else {
            resultString += getString(R.string.cognitive11up);
        }
        resultString += "\n\nYOUR CRAVING AND APPETITE SCORE IS " + appetiteScore + ":\n";
        if(appetiteScore >= 4 && appetiteScore <= 30) {
            resultString += getString(R.string.appetite4to30);
        } else if(appetiteScore >= 31 && appetiteScore <= 50) {
            resultString += getString(R.string.appetite31to50);
        } else if(appetiteScore >= 51 && appetiteScore <= 70) {
            resultString += getString(R.string.appetite51to70);
        } else {
            resultString += getString(R.string.appetite71up);
        }
        resultString += "\n\nYOUR BODY IMAGE SCORE IS " + bodyImageScore + ":\n";
        if(bodyImageScore >= 8 && bodyImageScore <= 16) {
            resultString += getString(R.string.bodyimage8to16);
        } else if(bodyImageScore >= 17 && bodyImageScore <= 32) {
            resultString += getString(R.string.bodyimage17to32);
        } else {
            resultString += getString(R.string.bodyImage32up);
        }
        resultString += "\n\nYOUR OVERALL SCORE IS " + overAllScore + ":\n";
        if (overAllScore >= 29 && overAllScore <= 70) {
            resultString += getString(R.string.overall29to70);
        } else if (overAllScore > 70 && overAllScore <= 150) {
            resultString += getString(R.string.overall71to150);
        } else if (overAllScore > 150 && overAllScore <= 238) {
            resultString += getString(R.string.overall150up);
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
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] quest = {BabyContract.QuestEntry.COLUMN_ID,BabyContract.QuestEntry.COLUMN_QUESTION,
                BabyContract.QuestEntry.COLUMN_OPTION1,BabyContract.QuestEntry.COLUMN_OPTION2,
                BabyContract.QuestEntry.COLUMN_OPTION3,BabyContract.QuestEntry.COLUMN_OPTION4,
                BabyContract.QuestEntry.COLUMN_OPTION5,BabyContract.QuestEntry.COLUMN_CATEGORY,BabyContract.QuestEntry.COLUMN_OPTION6};
        return new CursorLoader(
                getActivity(),
                BabyContract.QuestEntry.CONTENT_URI,
                quest,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        quesList = new ArrayList<>();
        if (data.moveToFirst()){
            do{
                Question q = new Question(data.getInt(0),data.getString(1),data.getString(2),data.getString(3),
                        data.getString(4),data.getString(5),data.getString(6),data.getString(7),data.getString(8));
                quesList.add(q);
            } while(data.moveToNext());
        }
        result=(TextView)mRootView.findViewById(R.id.textResult);
        result.setMovementMethod(new ScrollingMovementMethod());
        result.setText(getString(R.string.quiz_intro));
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
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }


}
