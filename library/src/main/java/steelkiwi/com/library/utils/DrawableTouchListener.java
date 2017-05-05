package steelkiwi.com.library.utils;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import steelkiwi.com.library.drawable.IndicatorDrawable;

/**
 * Created by yaroslav on 5/5/17.
 */

public abstract class DrawableTouchListener implements View.OnTouchListener {

    private List<IndicatorDrawable> drawables;
    private int startIndex;
    private int size;
    private int currentPosition;
    private int previousPosition;

    public DrawableTouchListener(List<IndicatorDrawable> drawables) {
        this.drawables = drawables;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if(drawables != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            final int x = (int) event.getX();
            final int y = (int) event.getY();
            for(int i = startIndex; i < size; i++) {
                final IndicatorDrawable drawable = drawables.get(i);
                final Rect rect = drawable.getBounds();
                if (rect.contains(x, y)) {
                    previousPosition = currentPosition;
                    currentPosition = i;
                    return onDrawableTouch(currentPosition, previousPosition);
                }
            }
        }
        return false;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public abstract boolean onDrawableTouch(int currentPosition, int previousPosition);
}
