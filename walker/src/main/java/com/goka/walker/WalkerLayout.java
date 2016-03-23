package com.goka.walker;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class WalkerLayout extends RelativeLayout {

    public static final String TAG = WalkerLayout.class.getSimpleName();

    private PointF speed = new PointF(0.0f, 0.0f);
    private PointF speedVariance = new PointF(0.0f, 0.0f);
    private boolean enableAlphaAnimation = false;
    private AnimationType animationType = AnimationType.Linear;
    private PointF[] childSpeeds;
    private List<String> ignoredViewTags = new ArrayList<>();
    private CustomAnimationListener customAnimationListener;

    public WalkerLayout(Context context) {
        super(context);
    }

    public WalkerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WalkerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup() {
        int count = getChildCount();
        this.childSpeeds = new PointF[count];
        for (int i = 0; i < count; i++) {
            speed.x += speedVariance.x;
            speed.y += speedVariance.y;
            childSpeeds[i] = speed;
        }
    }

    public PointF getSpeed() {
        return speed;
    }

    public void setSpeed(PointF speed) {
        this.speed = speed;
    }

    public PointF getSpeedVariance() {
        return speedVariance;
    }

    public void setSpeedVariance(PointF speedVariance) {
        this.speedVariance = speedVariance;
    }

    public boolean isEnableAlphaAnimation() {
        return enableAlphaAnimation;
    }

    public void setEnableAlphaAnimation(boolean enableAlphaAnimation) {
        this.enableAlphaAnimation = enableAlphaAnimation;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(AnimationType animation) {
        this.animationType = animation;
    }

    public PointF[] getChildSpeeds() {
        return childSpeeds;
    }

    public void setChildSpeeds(PointF[] childSpeeds) {
        this.childSpeeds = childSpeeds;
    }

    public List<String> getIgnoredViewTags() {
        return ignoredViewTags;
    }

    public void setIgnoredViewTags(List<String> ignoredViewTags) {
        this.ignoredViewTags = ignoredViewTags;
    }

    public CustomAnimationListener getCustomAnimationListener() {
        return customAnimationListener;
    }

    public void setCustomAnimationListener(CustomAnimationListener customAnimationListener) {
        this.customAnimationListener = customAnimationListener;
    }

    public void walk(float offset, Direction direction) {
        for (int i = 0; i < childSpeeds.length; i++) {
            switch (animationType) {
                case Linear:
                    animationLinear(i, offset, direction);
                    break;

                case Zoom:
                    animationZoom(i, offset, direction);
                    break;

                case Curve:
                    animationCurve(i, offset, direction);
                    break;

                case InOut:
                    animationInOut(i, offset, direction);
                    break;

                case Custom:
                    if (customAnimationListener != null) {
                        customAnimationListener.animate(i, offset, direction);
                    }
                    break;
            }

            if (enableAlphaAnimation) {
                animationAlpha(i, offset, direction);
            }
        }
    }

    private void animationAlpha(int index, float offset, Direction direction) {
        View view = getChildAt(index);
        String tag = String.valueOf(view.getTag());
        if (!ignoredViewTags.contains(tag)) {
            view.setAlpha(offset);
        }
    }

    private void animationCurve(int index, float offset, Direction direction) {
        if (childSpeeds.length <= 0) {
            return;
        }
        float dx = 0.0f;
        float dy = (1.0f - offset) * 10;
        switch (direction) {
            case Left:
                dx = (1.0f - offset) * 10;
                dy = (1.0f - offset) * 10;
                break;
            case Right:
                dx = (offset - 1.0f) * 10;
                dy = (offset - 1.0f) * 10;
                break;
        }

        translation(index, (((float) Math.pow(dx, 3) - (dx * 25)) * childSpeeds[index].x), ((float) Math.pow(dy, 3) - (dy * 20)) * childSpeeds[index].y);
    }

    private void animationZoom(int index, float offset, Direction direction) {
        float scale = (1.0f - offset);
        scale(index, 1.0f - scale, 1.0f - scale);
    }

    private void animationLinear(int index, float offset, Direction direction) {
        if (childSpeeds.length <= 0) {
            return;
        }
        float dx = 0.0f;
        float dy = (1.0f - offset) * 100;
        switch (direction) {
            case Left:
                dx = (1.0f - offset) * 100;
                break;
            case Right:
                dx = (offset - 1.0f) * 100;
                break;
        }
        translation(index, dx * childSpeeds[index].x, dy * childSpeeds[index].y);
    }

    private void animationInOut(int index, float offset, Direction direction) {
        if (childSpeeds.length <= 0) {
            return;
        }
        float dx = 0.0f;
        float dy = (1.0f - offset);
        switch (direction) {
            case Left:
                dx = (1.0f - offset);
                break;
            case Right:
                dx = (offset - 1.0f);
                break;
        }
        translation(index, dx * childSpeeds[index].x * 100, dy * childSpeeds[index].y * 100);
    }

    public void scale(int index, float scaleX, float scaleY) {
        View view = getChildAt(index);
        String tag = String.valueOf(view.getTag());
        if (!ignoredViewTags.contains(tag)) {
            view.setScaleX(scaleX);
            view.setScaleY(scaleY);
        }
    }

    public void translation(int index, float translationX, float translationY) {
        View view = getChildAt(index);
        String tag = String.valueOf(view.getTag());
        if (!ignoredViewTags.contains(tag)) {
            view.setTranslationX(translationX);
            view.setTranslationY(translationY);
        }
    }

    public enum Direction {
        Right,
        Left,
        None,
    }

    public enum AnimationType {
        Linear,
        Curve,
        Zoom,
        InOut,
        Custom
    }

    public interface CustomAnimationListener {
        void animate(int index, float offset, Direction direction);
    }

}
