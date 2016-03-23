package com.goka.walker;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public abstract class WalkerFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public static final String TAG = WalkerFragment.class.getSimpleName();

    protected abstract int getPagePosition();

    protected abstract WalkerLayout getWalkerLayout();

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        WalkerLayout walkerLayout = getWalkerLayout();
        if (walkerLayout != null) {
            int pagePosition = getPagePosition();
            if (position >= pagePosition) {
                walkerLayout.walk(1.0f - positionOffset, WalkerLayout.Direction.Right);
            } else if (position < pagePosition) {
                walkerLayout.walk(positionOffset, WalkerLayout.Direction.Left);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
