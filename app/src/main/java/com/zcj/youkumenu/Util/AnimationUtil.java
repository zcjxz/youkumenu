package com.zcj.youkumenu.Util;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;


public class AnimationUtil {

    private static RotateAnimation closeAnimation;
    private static RotateAnimation openAnimation;
    public static int AnimationCount=0;
    private static MyAnimationListener listener;

    static {
        listener = new MyAnimationListener();
    }

    public static void closeMenu(RelativeLayout relativeLayout,int startOffest){
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(false);
        }
        closeAnimation = new RotateAnimation(0,-180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,1.0f);
        closeAnimation.setFillAfter(true);
        closeAnimation.setDuration(300);
        closeAnimation.setStartOffset(startOffest);
        closeAnimation.setAnimationListener(listener);
        relativeLayout.startAnimation(closeAnimation);
    }
    public static void openMenu(RelativeLayout relativeLayout,int startOffset){
        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            relativeLayout.getChildAt(i).setEnabled(true);
        }
        openAnimation = new RotateAnimation(-180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,1.0f);
        openAnimation.setFillAfter(true);
        openAnimation.setDuration(300);
        openAnimation.setStartOffset(startOffset);
        relativeLayout.startAnimation(openAnimation);
    }
    static class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
            AnimationCount++;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            AnimationCount--;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
