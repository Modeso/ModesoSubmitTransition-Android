package ch.modeso.modesosubmittransition;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import ch.modeso.modesosubmittransition.SubmitActionListener.SubmitActionListener;


/**
 * Created by RANIA on 17-Aug-17.
 */

public class SubmitTransitionButton extends CircularProgressButton implements View.OnClickListener{

    Context mContext;
    private Class mTargetClass;
    private boolean mTargetWithoutHistory = false;
    private SubmitActionListener mSubmitActionListener;
    private long mAnimationDuration = 1000;

    public SubmitTransitionButton(Context context) {
        super(context);
        mContext  = context;
        setOnClickListener(this);
    }

    public SubmitTransitionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext  = context;
        setOnClickListener(this);
    }

    public SubmitTransitionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext  = context;
        setOnClickListener(this);
    }

    /**
     *
     * @param targetClass activity class that want to open after action success
     */
    public <T> void setTarget(final Class<T> targetClass){
        mTargetClass = targetClass;
    }

    public boolean isTargetWithoutHistory() {
        return mTargetWithoutHistory;
    }

    public void setTargetWithoutHistory(boolean mOpenTargetWithoutHistory) {
        this.mTargetWithoutHistory = mOpenTargetWithoutHistory;
    }

    public void setSubmitAction(SubmitActionListener mSubmitActionListener) {
        this.mSubmitActionListener = mSubmitActionListener;
    }

    public void setAnimationDuration(long animationDuration){
        this.mAnimationDuration = animationDuration;
    }

    @Override
    public void onClick(View v) {
        if (this.isIdle()) {
            hideSoftKeyboard(mContext, this.getRootView());
            if(mSubmitActionListener == null){
                return;
            }
            this.showProgress();

            //Delay for 1 second to see UI animation if the call response was fast
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSubmitActionListener.executeAction();
                }
            }, 1000);
        }else if (this.isErrorOrCompleteOrCancelled()) {
            this.showIdle();
        }
    }

    /**
     * This method called after action finished with failure
     */
    public void onFailure(){
        this.showError();
    }

    /**
     * This method called after action finished successfully
     */
    public void onSuccess(){
        this.stopProgress();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        float scaleX = 1+(6*width/this.getHeight());
        float scaleY = 1+(4*height/this.getHeight());

        ObjectAnimator disappearAnim = ObjectAnimator.ofFloat(this,"alpha",1f,0.8f);
        disappearAnim.setDuration(mAnimationDuration);

        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this,"scaleX",scaleX);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this,"scaleY",scaleY);
        animScaleX.setDuration(mAnimationDuration);
        animScaleY.setDuration(mAnimationDuration);

        AnimatorSet allAnimationSet = new AnimatorSet();
        allAnimationSet.playTogether(disappearAnim,animScaleX,animScaleY);
        allAnimationSet.setStartDelay(400);
        allAnimationSet.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openAnotherActivity();
            }
        }, 800);
    }

    private void openAnotherActivity(){
        if(mTargetClass != null) {
            Intent intent = new Intent(mContext, mTargetClass);
            if (isTargetWithoutHistory()) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public static void hideSoftKeyboard(Context context, View focusLayout) {
        Activity activity = (Activity) context;
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
