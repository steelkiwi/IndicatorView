package steelkiwi.com.library.drawable;

/**
 * Created by yaroslav on 4/28/17.
 */

public final class DrawableDecorator {
    private IndicatorDrawable drawable;

    public DrawableDecorator() {
    }

    public DrawableDecorator setDrawable(IndicatorDrawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public DrawableDecorator setBackgroundColor(int backgroundColor) {
        drawable.setBackgroundColor(backgroundColor);
        return this;
    }

    public DrawableDecorator setTextColor(int textColor) {
        drawable.setTextColor(textColor);
        return this;
    }

    public DrawableDecorator setTextSize(float size) {
        drawable.setTextSize(size);
        return this;
    }

    public DrawableDecorator setCornerRadius(int radius) {
        drawable.setCornerRadius(radius);
        return this;
    }

    public IndicatorDrawable decorate(){
        drawable.init();
        return drawable;
    }
}
