package steelkiwi.com.library.factory;

import android.content.Context;

import steelkiwi.com.library.utils.IndicatorType;
import steelkiwi.com.library.drawable.HangDownDrawable;
import steelkiwi.com.library.drawable.IndicatorDrawable;
import steelkiwi.com.library.drawable.LookUpDrawable;

/**
 * Created by yaroslav on 4/28/17.
 */

public class DrawableFactory {

    public static IndicatorDrawable createDrawable(final Context context, IndicatorType type) {
        switch (type) {
            case HANG_DOWN:
                return new HangDownDrawable(context);
            case LOOK_OUT:
                return new LookUpDrawable(context);
            default:
                return new HangDownDrawable(context);
        }
    }
}
