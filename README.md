# walker

![](https://github.com/gotokatsuya/walker/blob/master/walker.gif)


Easy to create cool walkthrough page.


### Usage

```java
public class PageFragment extends WalkerFragment {

    public static final String TAG = PageFragment.class.getSimpleName();

    public static final int PAGE_POSITION = 0;

    private WalkerLayout walkerLayout;

    public static PageFragment newInstance() {
        Bundle args = new Bundle();
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        walkerLayout = (WalkerLayout) view.findViewById(R.id.walker);

        // Adjust speed
        walkerLayout.setSpeed(new PointF(1.0f, 0.0f));
        walkerLayout.setSpeedVariance(new PointF(1.2f, 0.0f));

        // Enable Alpha
        walkerLayout.setEnableAlphaAnimation(true);

        // Basic animate (Linear, Curve, Zoom, InOut)
        walkerLayout.setAnimationType(WalkerLayout.AnimationType.InOut);

        // Custom animation
        walkerLayout.setAnimationType(WalkerLayout.AnimationType.Custom);
        walkerLayout.setCustomAnimationListener(new WalkerLayout.CustomAnimationListener() {
            @Override
            public void animate(int index, float offset, WalkerLayout.Direction direction) {
                View child = walkerLayout.getChildAt(index);
                String tag = String.valueOf(child.getTag());
                switch (tag) {
                    case "1":
                        child.setRotation((180.0f) * (1.0f - offset));
                        break;
                    case "2":
                        child.setTranslationX(0.0f);
                        child.setTranslationY((1.0f - offset) * 200);
                        break;
                }
            }
        });

        // tag list that view does not animate.
        walkerLayout.setIgnoredViewTags(Arrays.asList("1", "2"));

        // setup layout (MUST call
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
```

### Gradle

```
repositories {
    jcenter()
}

dependencies {
    compile 'com.goka:walker:1.0.0'
}
```
