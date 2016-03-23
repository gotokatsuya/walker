package com.goka.sample;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goka.walker.WalkerFragment;
import com.goka.walker.WalkerLayout;

import java.util.Arrays;

public class FirstPageFragment extends WalkerFragment {

    public static final String TAG = FirstPageFragment.class.getSimpleName();

    public static final int PAGE_POSITION = 0;
    private WalkerLayout walkerLayout;

    public static FirstPageFragment newInstance() {
        Bundle args = new Bundle();
        FirstPageFragment fragment = new FirstPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        walkerLayout = (WalkerLayout) view.findViewById(R.id.walker);
        walkerLayout.setSpeedVariance(new PointF(0.0f, 2.0f));
        walkerLayout.setAnimationType(WalkerLayout.AnimationType.InOut);
        walkerLayout.setIgnoredViewTags(Arrays.asList("1", "2"));
        walkerLayout.setup();
    }

    @Override
    protected int getPagePosition() {
        return PAGE_POSITION;
    }

    @Override
    protected WalkerLayout getWalkerLayout() {
        return walkerLayout;
    }
}
