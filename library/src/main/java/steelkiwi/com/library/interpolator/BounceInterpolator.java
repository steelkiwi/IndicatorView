package steelkiwi.com.library.interpolator;

import android.view.animation.Interpolator;

/**
 * Created by yaroslav on 5/3/17.
 */

public class BounceInterpolator implements Interpolator {

    private double amplitude;
    private double frequency;

    public BounceInterpolator(double amplitude, double frequency) {
        this.amplitude = amplitude;
        this.frequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / amplitude) * Math.cos(frequency * time) + 1);
    }
}
