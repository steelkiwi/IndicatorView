package steelkiwi.com.library.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

/**
 * Created by yaroslav on 4/26/17.
 */

public abstract class IndicatorDrawable extends Drawable {

    private Context context;
    protected Paint indicatorPaint;
    protected Paint textPaint;
    // indicator text bounds
    private Rect textBounds;
    // indicator background
    private int backgroundColor;
    // indicator text color
    private int textColor;
    // indicator text size
    private float textSize;
    // indicator corner radius
    private int cornerRadius;
    // position for show it like text
    private int position;
    //
    private boolean isSelected;

    public IndicatorDrawable(Context context) {
        this.context = context;
    }

    public void init() {
        textBounds = new Rect();
        prepareItemsDrawComponents();
        prepareTextDrawComponents();
    }

    private void prepareItemsDrawComponents() {
        indicatorPaint = new Paint();
        indicatorPaint.setAntiAlias(true);
        indicatorPaint.setColor(getBackgroundColor());
        indicatorPaint.setStyle(Paint.Style.FILL);
    }

    private void prepareTextDrawComponents() {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(getTextColor());
        textPaint.setTextSize(getTextSize());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // implement in the child class
    }

    int measureWidth(String text) {
        return (int) textPaint.measureText(text);
    }

    int measureHeight(String text) {
        // Measure the text rectangle to get the height
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds.height();
    }

    @Override
    public void setAlpha(int i) {
        // no need
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // no need
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    int getBackgroundColor() {
        return backgroundColor;
    }

    void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    int getCornerRadius() {
        return cornerRadius;
    }

    void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    float getTextSize() {
        return textSize;
    }

    void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setSelectColor(int color) {
        indicatorPaint.setColor(color);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
