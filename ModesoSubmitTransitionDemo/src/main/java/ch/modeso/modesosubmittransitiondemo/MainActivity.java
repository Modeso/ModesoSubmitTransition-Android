package ch.modeso.modesosubmittransitiondemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.modeso.modesosubmittransition.ProgressButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressButton mLoginButton;
    TextView mSignUpTextView;
    LinearLayout mContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginButton = (ProgressButton) findViewById(R.id.loginButton);
        Spannable wordToSpan = new SpannableString(getString(R.string.signup_text));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 23, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignUpTextView = (TextView) findViewById(R.id.signup_textView);
        mSignUpTextView.setText(wordToSpan);
        mContainerLayout = (LinearLayout) findViewById(R.id.containerLayout);
        mLoginButton.setOnClickListener(this);
    }

    public void onClick(View view) {

        hideSoftKeyboard(this, mContainerLayout);
        if (mLoginButton.isIdle()) {
            mLoginButton.showProgress();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginButton.onActionComplete(SecondActivity.class, true);
                    mContainerLayout.setVisibility(View.INVISIBLE);
                    mSignUpTextView.setVisibility(View.INVISIBLE);
//                    mLoginButton.showError();
                }
            }, 3000);
        } else if (mLoginButton.isErrorOrCompleteOrCancelled()) {
            mLoginButton.showIdle();
        }
    }

    public static void hideSoftKeyboard(Activity activity, View focusLayout) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm.isAcceptingText()) {
                if (focusLayout != null) {
                    focusLayout.requestFocus();
                }
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
