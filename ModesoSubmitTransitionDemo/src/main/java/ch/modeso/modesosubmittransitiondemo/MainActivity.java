package ch.modeso.modesosubmittransitiondemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import ch.modeso.modesosubmittransition.ProgressButton;
import ch.modeso.modesosubmittransition.SubmitActionListener.SubmitActionListener;

public class MainActivity extends AppCompatActivity implements SubmitActionListener{

    ProgressButton mLoginButton;
    TextView mSignUpTextView;
    LinearLayout mContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginButton = (ProgressButton) findViewById(R.id.loginButton);
        mLoginButton.setTarget(SecondActivity.class);
        mLoginButton.setTargetWithoutHistory(true);
        mLoginButton.setSubmitAction(this);

        setSignUpText();
    }

    private void setSignUpText(){
        Spannable wordToSpan = new SpannableString(getString(R.string.signup_text));
        wordToSpan.setSpan(new ForegroundColorSpan(Color.WHITE), 23, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignUpTextView = (TextView) findViewById(R.id.signup_textView);
        mSignUpTextView.setText(wordToSpan);
    }

    @Override
    public void executeAction() {

        // Her user can execute any action
        // For example calling Google URL using Volley
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mLoginButton.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mLoginButton.onFailure();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
