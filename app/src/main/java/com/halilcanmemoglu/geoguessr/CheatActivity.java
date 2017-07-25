package com.halilcanmemoglu.geoguessr;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CheatActivity extends AppCompatActivity {
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private TextView mBuildVersionView;
    private Button mShowAnswerButton;
    private TextView mCheatCountView;
    private int mCheatCount;

    private static final Integer API_LEVEL_INT = Build.VERSION.SDK_INT;
    private static final String EXTRA_ANSWER_IS_TRUE = "com.halilcanmemoglu.android.geoguessr.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.halilcanmemoglu.android.geoguessr.answer_shown";
    private static final int EXTRA_CHEAT_COUNT = 3;

    private String mApiLevelReport = "API Level: " + Integer.toString(API_LEVEL_INT);
    private String mCheatCountReport = "";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue, int cheatCount) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        intent.putExtra("cheatCount", cheatCount);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mCheatCount = getIntent().getIntExtra("cheatCount", 0);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mBuildVersionView = (TextView) findViewById(R.id.build_version_view);
        mCheatCountView = (TextView) findViewById(R.id.cheat_count_view);
        mCheatCountReport = "Cheats Left: " + Integer.toString(mCheatCount);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);

        mBuildVersionView.setText(mApiLevelReport);
        mCheatCountView.setText(mCheatCountReport);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mCheatCount > 0) {
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    } else {
                        mAnswerTextView.setText(R.string.false_button);
                    }
                    setAnswerShownResult(true);
                } else {
                    setAnswerShownResult(false);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal
                            (mShowAnswerButton, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
                mCheatCount -= 1;
                mCheatCountView.setText(mCheatCountReport);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setCheatCount(int currentCheatCount) {
        Intent data = new Intent();
        data.putExtra("cheatCount", currentCheatCount - 1);
        setResult(RESULT_OK, data);
    }

    public static int getCheatCount(Intent result) {
        return result.getIntExtra("cheatCount", cheatCount);
    }


}