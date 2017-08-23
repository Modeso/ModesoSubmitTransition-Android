package ch.modeso.progressbutton;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import ch.modeso.progressbuttonlibrary.ProgressButton;

public class MainActivity extends AppCompatActivity {

    ProgressButton mLoginButton;
    TextView mSignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = (ProgressButton) findViewById(R.id.loginButton);
        mLoginButton.setIndeterminateProgressMode(true);

        Spannable wordToSpan = new SpannableString(getString(R.string.signup_text));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 23, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mSignUpTextView = (TextView) findViewById(R.id.signup_textView);
        mSignUpTextView.setText(wordToSpan);
    }

    public void onClickLoginButton(View view) {

        if (mLoginButton.isIdle()) {
            mLoginButton.showProgress();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginButton.onActionComplete(SecondActivity.class, true);
                }
            }, 3000);
        } else if (mLoginButton.isErrorOrCompleteOrCancelled()) {
            mLoginButton.showIdle();
        }
    }
}
