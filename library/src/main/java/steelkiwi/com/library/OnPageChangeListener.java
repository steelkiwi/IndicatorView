package steelkiwi.com.library;

import android.support.v4.view.ViewPager;
import android.util.Log;

/**
 * Created by yaroslav on 4/28/17.
 */

public class OnPageChangeListener implements ViewPager.OnPageChangeListener {

    private static final String TAG = "TAG";

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d(TAG, "onPageScrolled: ");
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected: ");
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged: ");
    }
}
