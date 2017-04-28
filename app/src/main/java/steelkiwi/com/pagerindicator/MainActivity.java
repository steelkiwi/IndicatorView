package steelkiwi.com.pagerindicator;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import steelkiwi.com.library.IndicatorType;
import steelkiwi.com.library.view.IndicatorView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> images = new ArrayList<>();
        images.add(R.color.color1);
        images.add(R.color.color2);
        images.add(R.color.color3);
        images.add(R.color.color1);
        images.add(R.color.color2);
        images.add(R.color.color3);
        images.add(R.color.color1);
        images.add(R.color.color2);
        images.add(R.color.color3);

        TutorialAdapter adapter = new TutorialAdapter(this);

        final IndicatorView indicator = (IndicatorView) findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        adapter.setTutorialImage(images);
        indicator.attachViewPager(viewPager);
    }
}
