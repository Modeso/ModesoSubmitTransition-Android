package ch.modeso.modesosubmittransition;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by RANIA on 17-Aug-17.
 */

public class ProgressButton extends CircularProgressButton {

    Context mContext;

    public ProgressButton(Context context) {
        super(context);
        mContext  = context;
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext  = context;
    }

    public ProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext  = context;
    }

    public <T> void onActionComplete(final Class<T> className, final boolean noHistory){

        this.stopProgress();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        float scaleX = 1+(6*width/this.getHeight());
        float scaleY = 1+(4*height/this.getHeight());

        ObjectAnimator disappearAnim = ObjectAnimator.ofFloat(this,"alpha",1f,0.7f);
        disappearAnim.setDuration(1000);

        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this,"scaleX",scaleX);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this,"scaleY",scaleY);
        animScaleX.setDuration(1000);
        animScaleY.setDuration(1000);

        AnimatorSet allAnimationSet = new AnimatorSet();
        allAnimationSet.playTogether(disappearAnim,animScaleX,animScaleY);
        allAnimationSet.start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                openAnotherActivity(className, noHistory);

            }
        }, 400);
    }

    private <T>  void openAnotherActivity(final Class<T> className, final boolean noHistory){
        if(className != null) {
            Intent intent = new Intent(mContext, className);
            if (noHistory) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}
