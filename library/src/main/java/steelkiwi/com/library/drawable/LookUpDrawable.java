package steelkiwi.com.library.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;

/**
 * Created by yaroslav on 4/28/17.
 */

public class LookUpDrawable extends IndicatorDrawable {

    // indicator item bounds
    private Rect itemBounds;
    // position to move by y axis
    private int positionY;

    public LookUpDrawable(Context context) {
        super(context);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        itemBounds = getBounds();
        String position = String.valueOf(getPosition());
        canvas.drawRoundRect(itemBounds.left, itemBounds.top - getPositionY(),
                itemBounds.right, itemBounds.bottom - getPositionY(), getCornerRadius(), getCornerRadius(), indicatorPaint);
        canvas.drawText(position, itemBounds.centerX() - (measureWidth(position) / 2),
                itemBounds.centerY() + (measureHeight(position) / 2) - getPositionY(), textPaint);
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
