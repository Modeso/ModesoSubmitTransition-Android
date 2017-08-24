package ch.modeso.progressbuttonlibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        ObjectAnimator disappearAnim = ObjectAnimator.ofFloat(this,"alpha",1f,0f);
        disappearAnim.setDuration(1000);

        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(this,"scaleX",height);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(this,"scaleY",height);
        animScaleX.setDuration(1000);
        animScaleY.setDuration(1000);


        AnimatorSet openMenuAnimatorSet = new AnimatorSet();
        openMenuAnimatorSet.playTogether(disappearAnim,animScaleX,animScaleY);

        if(className != null) {
            Intent intent = new Intent(mContext, className);
            if(noHistory) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

        openMenuAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

//                if(className != null) {
//                    Intent intent = new Intent(mContext, className);
//                    if(noHistory) {
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    }
//                    mContext.startActivity(intent);
//                    ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        openMenuAnimatorSet.start();
    }
}
