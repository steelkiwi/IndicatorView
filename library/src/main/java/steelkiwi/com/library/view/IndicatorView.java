package steelkiwi.com.library.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import java.util.ArrayList;
import java.util.List;

import steelkiwi.com.library.IndicatorType;
import steelkiwi.com.library.drawable.DrawableDecorator;
import steelkiwi.com.library.OnPageChangeListener;
import steelkiwi.com.library.R;
import steelkiwi.com.library.drawable.HangDownDrawable;
import steelkiwi.com.library.drawable.IndicatorDrawable;
import steelkiwi.com.library.drawable.LookUpDrawable;
import steelkiwi.com.library.factory.DrawableFactory;
import steelkiwi.com.library.interpolator.BounceInterpolator;

/**
 * Created by yaroslav on 4/26/17.
 */

public class IndicatorView extends View implements IndicatorController {
    private static final int DEFAULT_POSITION = 0;
    private static final int DELAY = 1200;
    private static final int ROTATE_ANGLE = 45;
    private static final double AMPLITUDE = .3; // amplitude for interpolator
    private static final double FREQUENCY = 10; // frequency of rotating
    // list of indicators
    private List<IndicatorDrawable> drawables = new ArrayList<>();
    private Paint paint;
    // type indicator showing
    private IndicatorType type;
    // canvas parameters
    private int canvasWidth;
    private int canvasHeight;
    // indicator item size
    private int indicatorSize;
    // indicator max size
    private int maxIndicatorSize;
    // indicator corner radius
    private int indicatorCornerRadius;
    // max indicator corner radius
    private int maxIndicatorCornerRadius;
    // margin between elements
    private int indicatorMargin;
    // color for selected state
    private int indicatorSelectColor;
    // color for idle state
    private int indicatorIdleColor;
    // color for text
    private int indicatorTextColor;
    // size for text
    private float indicatorTextSize;
    // previous item position to handle swipe direction
    private int previousPosition;
    // background bar height
    private int indicatorBarHeight;
    // color of the indicator bar
    private int indicatorBarColor;
    // left and right bar offset
    private int indicatorBarOffset;

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private void init(AttributeSet attrs) {
        prepareIndicatorItemsSize();
        prepareAttributes(attrs);
        preparePaint();
    }

    private void prepareAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        // prepare indicator size
        setIndicatorSize(typedArray.getDimensionPixelSize(R.styleable.IndicatorView_iv_size, getResources().getDimensionPixelSize(R.dimen.indicator_size)));
        // prepare indicator color for selected state
        setIndicatorSelectColor(typedArray.getColor(R.styleable.IndicatorView_iv_select_color,
                ContextCompat.getColor(getContext(), R.color.indicator_selected_color)));
        // prepare indicator color for idle state
        setIndicatorIdleColor(typedArray.getColor(R.styleable.IndicatorView_iv_idle_color,
                ContextCompat.getColor(getContext(), R.color.indicator_idle_color)));
        // prepare indicator color bar
        setIndicatorBarColor(typedArray.getColor(R.styleable.IndicatorView_iv_bar_color,
                ContextCompat.getColor(getContext(), R.color.indicator_bar_color)));
        // prepare indicator text color
        setIndicatorTextColor(typedArray.getColor(R.styleable.IndicatorView_iv_text_color,
                ContextCompat.getColor(getContext(), R.color.indicator_text_color)));
        // prepare indicator text size
        setIndicatorTextSize(typedArray.getDimensionPixelSize(R.styleable.IndicatorView_iv_text_size, getResources().getDimensionPixelSize(R.dimen.indicator_text_size)));
        // prepare indicator corner radius
        setIndicatorCornerRadius(typedArray.getDimensionPixelSize(R.styleable.IndicatorView_iv_corner_radius, getResources().getDimensionPixelSize(R.dimen.indicator_corner_radius)));
        // prepare default action for indicator
        setActionType(IndicatorType.fromId(typedArray.getInt(R.styleable.IndicatorView_iv_action, 0)));
        typedArray.recycle();
    }

    private void preparePaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getIndicatorBarColor());
    }

    private void prepareIndicatorItems(ViewPager viewPager) {
        int itemSize = viewPager.getAdapter().getCount();
        DrawableDecorator decorator = new DrawableDecorator();
        for(int i = 0; i < itemSize; i++) {
            IndicatorDrawable drawable = DrawableFactory.createDrawable(getContext(), getActionType());
            decorator.setDrawable(drawable)
                    .setBackgroundColor(getIndicatorIdleColor())
                    .setTextColor(getIndicatorTextColor())
                    .setTextSize(getIndicatorTextSize())
                    .setCornerRadius(getIndicatorCornerRadius())
                    .decorate();
            drawables.add(drawable);
        }
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                prepareDefaultTypeForShow();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void prepareDefaultTypeForShow() {
        setIndicatorSelectColor(DEFAULT_POSITION, getIndicatorSelectColor());
        switch (getActionType()) {
            case HANG_DOWN:
                rotate(DEFAULT_POSITION);
                break;
            case LOOK_OUT:
                showUp(DEFAULT_POSITION);
                break;
        }
    }

    private void prepareIndicatorItemsSize() {
        setIndicatorMargin(getResources().getDimensionPixelSize(R.dimen.indicator_margin));
        setIndicatorBarOffset(getResources().getDimensionPixelSize(R.dimen.indicator_bar_offset));
        setIndicatorBarHeight(getResources().getDimensionPixelSize(R.dimen.indicator_bar_height));
        maxIndicatorCornerRadius = getResources().getDimensionPixelSize(R.dimen.max_indicator_corner_radius);
        maxIndicatorSize = getResources().getDimensionPixelSize(R.dimen.max_indicator_item_size);
    }

    @Override
    public void rotate(final int position) {
        final IndicatorDrawable drawable = drawables.get(position);
        ValueAnimator angelAnimator = ValueAnimator.ofInt(0, ROTATE_ANGLE);
        angelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ((HangDownDrawable) drawable).setAngle(value);
                invalidate();
            }
        });
        angelAnimator.setDuration(DELAY);
        angelAnimator.setInterpolator(new BounceInterpolator(AMPLITUDE, FREQUENCY));
        angelAnimator.start();
    }

    @Override
    public void rotateBack(final int position) {
        final IndicatorDrawable drawable = drawables.get(position);
        ValueAnimator angelAnimator = ValueAnimator.ofInt(ROTATE_ANGLE, 0);
        angelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ((HangDownDrawable) drawable).setAngle(value);
                invalidate();
            }
        });
        angelAnimator.setDuration(DELAY);
        angelAnimator.setInterpolator(new OvershootInterpolator());
        angelAnimator.start();
    }

    @Override
    public void showUp(int position) {
        final IndicatorDrawable drawable = drawables.get(position);
        int top = (getMeasuredHeight() / 2) - getHalfIndicatorSize() - getIndicatorBarHeight();
        ValueAnimator angelAnimator = ValueAnimator.ofInt(top - getHalfIndicatorSize(), top);
        angelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ((LookUpDrawable) drawable).setPositionY(value);
                invalidate();
            }
        });
        angelAnimator.setDuration(DELAY);
        angelAnimator.setInterpolator(new OvershootInterpolator());
        angelAnimator.start();
    }

    @Override
    public void showDown(int position) {
        final IndicatorDrawable drawable = drawables.get(position);
        int top = (getMeasuredHeight() / 2) - getHalfIndicatorSize() - (getIndicatorBarHeight() * 2);
        ValueAnimator angelAnimator = ValueAnimator.ofInt(top, top - getHalfIndicatorSize() - 3);
        angelAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                ((LookUpDrawable) drawable).setPositionY(value);
                invalidate();
            }
        });
        angelAnimator.setDuration(DELAY);
        angelAnimator.setInterpolator(new OvershootInterpolator());
        angelAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();
        drawBarBackground(canvas);
        // draw indicator by type
        drawIndicator(canvas);
    }

    @Override
    public void attachViewPager(ViewPager viewPager) {
        prepareIndicatorItems(viewPager);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                onPageChanged(position);
            }
        });
    }

    private void drawBarBackground(final Canvas canvas) {
        // draw background line
        int left = canvasWidth / 2 - calculateAllItemsWidth() / 2;
        int right = left + calculateAllItemsWidth() - getIndicatorMargin();
        int top = getHalfCanvasHeight() - getHalfIndicatorSize() - getIndicatorBarHeight();
        int bottom = getHalfCanvasHeight() - getHalfIndicatorSize() + getIndicatorBarHeight();
        canvas.drawRoundRect(left - getIndicatorBarOffset(), top, right + getIndicatorBarOffset(), bottom, getIndicatorBarHeight(), getIndicatorBarHeight(), paint);
    }

    private void drawIndicator(final Canvas canvas) {
        int left = canvasWidth / 2 - calculateAllItemsWidth() / 2;
        int top = getHalfCanvasHeight() - getHalfIndicatorSize();
        int bottom = (getHalfCanvasHeight() - getHalfIndicatorSize()) + getIndicatorSize() - getIndicatorMargin();
        for (int i = 0; i < drawables.size(); i++) {
            IndicatorDrawable drawable = drawables.get(i);
            drawable.setPosition(i + 1);
            drawable.setBounds(left + getIndicatorSize() * i, top, (left + getIndicatorSize() + (getIndicatorSize() * i)) - getIndicatorMargin(), bottom);
            drawable.draw(canvas);
        }
    }

    @Override
    public void onPageChanged(int position) {
        if(getPreviousPosition() < position) {
            onSwipeRight(position);
        } else {
            onSwipeLeft(position);
        }
        setPreviousPosition(position);
    }

    private void onSwipeRight(int position) {
        // swipe right
        startActionByType(position);
        setIndicatorSelectColor(position, getIndicatorSelectColor());
        if (position > 0) {
            returnActionByType(position - 1);
            setIndicatorSelectColor(position - 1, getIndicatorIdleColor());
        }
    }

    private void onSwipeLeft(int position) {
        // swipe left
        startActionByType(position);
        setIndicatorSelectColor(position , getIndicatorSelectColor());
        if (position < drawables.size()) {
            returnActionByType(position + 1);
            setIndicatorSelectColor(position + 1, getIndicatorIdleColor());
        }
    }

    private void startActionByType(int position) {
        switch (getActionType()) {
            case HANG_DOWN:
                rotate(position);
                break;
            case LOOK_OUT:
                showUp(position);
                break;
        }
    }

    private void returnActionByType(int position) {
        switch (getActionType()) {
            case HANG_DOWN:
                rotateBack(position);
                break;
            case LOOK_OUT:
                showDown(position);
                break;
        }
    }

    private int calculateAllItemsWidth() {
        return getIndicatorSize() * drawables.size();
    }

    private int getHalfIndicatorSize() {
        return getIndicatorSize() / 2;
    }

    private int getHalfCanvasHeight() {
        return canvasHeight / 2;
    }

    private void setIndicatorSelectColor(int position, int color) {
        drawables.get(position).setSelectColor(color);
    }

    private int getPreviousPosition() {
        return previousPosition;
    }

    private void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    private int getIndicatorSelectColor() {
        return indicatorSelectColor;
    }

    private void setIndicatorSelectColor(int indicatorSelectColor) {
        this.indicatorSelectColor = indicatorSelectColor;
    }

    private int getIndicatorIdleColor() {
        return indicatorIdleColor;
    }

    private void setIndicatorIdleColor(int indicatorIdleColor) {
        this.indicatorIdleColor = indicatorIdleColor;
    }

    private int getIndicatorTextColor() {
        return indicatorTextColor;
    }

    private void setIndicatorTextColor(int indicatorTextColor) {
        this.indicatorTextColor = indicatorTextColor;
    }

    private float getIndicatorTextSize() {
        return indicatorTextSize;
    }

    private void setIndicatorTextSize(float indicatorTextSize) {
        this.indicatorTextSize = indicatorTextSize;
    }

    private int getIndicatorBarHeight() {
        return indicatorBarHeight;
    }

    private void setIndicatorBarHeight(int indicatorBarHeight) {
        this.indicatorBarHeight = indicatorBarHeight;
    }

    private int getIndicatorBarColor() {
        return indicatorBarColor;
    }

    private void setIndicatorBarColor(int indicatorBarColor) {
        this.indicatorBarColor = indicatorBarColor;
    }

    private int getIndicatorBarOffset() {
        return indicatorBarOffset;
    }

    private void setIndicatorBarOffset(int indicatorBarOffset) {
        this.indicatorBarOffset = indicatorBarOffset;
    }

    private int getIndicatorMargin() {
        return indicatorMargin;
    }

    private int getIndicatorSize() {
        if(indicatorSize > maxIndicatorSize) {
            return maxIndicatorSize;
        }
        return indicatorSize;
    }

    private void setIndicatorSize(int indicatorSize) {
        this.indicatorSize = indicatorSize;
    }

    private void setIndicatorMargin(int indicatorMargin) {
        this.indicatorMargin = indicatorMargin;
    }

    public int getIndicatorCornerRadius() {
        if(indicatorCornerRadius > maxIndicatorCornerRadius) {
            return maxIndicatorCornerRadius;
        }
        return indicatorCornerRadius;
    }

    public void setIndicatorCornerRadius(int indicatorCornerRadius) {
        this.indicatorCornerRadius = indicatorCornerRadius;
    }

    public IndicatorType getActionType() {
        return type;
    }

    public void setActionType(IndicatorType type) {
        this.type = type;
    }
}
