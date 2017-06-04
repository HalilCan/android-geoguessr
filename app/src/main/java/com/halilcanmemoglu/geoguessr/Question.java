package com.halilcanmemoglu.geoguessr;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by hcm on 24.05.2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mVisible;

    public Question(int textResId, boolean answerTrue, boolean visible) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mVisible = visible;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }


    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }
}
