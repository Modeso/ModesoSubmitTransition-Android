package ch.modeso.progressbutton;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ch.modeso.progressbuttonlibrary.ProgressButton;

public class MainActivity extends AppCompatActivity {

    ProgressButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginButton = (ProgressButton) findViewById(R.id.loginButton);
        mLoginButton.setIndeterminateProgressMode(true);
    }

    public void onClickLoginButton(View view) {

        if (mLoginButton.isIdle()) {
            mLoginButton.showProgress();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginButton.onActionComplete(new SecondActivity(), true);
                }
            }, 3000);
        } else if (mLoginButton.isErrorOrCompleteOrCancelled()) {
            mLoginButton.showIdle();
        }
    }
}
