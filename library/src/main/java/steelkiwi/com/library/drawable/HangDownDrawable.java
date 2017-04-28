package steelkiwi.com.library.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by yaroslav on 4/28/17.
 */

public class HangDownDrawable extends IndicatorDrawable {
    private static final int DEFAULT_ANGLE = 0;

    // indicator item bounds
    private Rect itemBounds;
    // angle for rotating canvas
    private int angle;

    public HangDownDrawable(Context context) {
        super(context);
        setAngle(DEFAULT_ANGLE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        itemBounds = getBounds();
        String position = String.valueOf(getPosition());
        canvas.save();
        canvas.rotate(getAngle(), itemBounds.left + getCornerRadius(), itemBounds.top + getCornerRadius());
        canvas.drawRoundRect(itemBounds.left, itemBounds.top, itemBounds.right, itemBounds.bottom, getCornerRadius(), getCornerRadius(), indicatorPaint);
        canvas.drawText(position, itemBounds.centerX() - (measureWidth(position) / 2), itemBounds.centerY() + (measureHeight(position) / 2), textPaint);
        canvas.restore();
    }

    int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
