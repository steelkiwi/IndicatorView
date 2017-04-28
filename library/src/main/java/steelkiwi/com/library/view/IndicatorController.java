package steelkiwi.com.library.view;

import android.support.v4.view.ViewPager;

/**
 * Created by yaroslav on 4/28/17.
 */

public interface IndicatorController {
    void rotate(final int position);
    void rotateBack(final int position);
    void showUp(final int position);
    void showDown(final int position);
    void onPageChanged(int position);
    void attachViewPager(ViewPager viewPager);
}
