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
        images.add(R.color.color1);//1
        images.add(R.color.color1);//2
        images.add(R.color.color1);//3
        images.add(R.color.color1);//4
        images.add(R.color.color1);//5
        images.add(R.color.color1);//6
        images.add(R.color.color1);//7
        images.add(R.color.color1);//8
        images.add(R.color.color1);//9
        images.add(R.color.color1);//10
        images.add(R.color.color1);//11
        images.add(R.color.color1);//12
        images.add(R.color.color1);//13
        images.add(R.color.color1);//14

        images.add(R.color.color1);//1
        images.add(R.color.color1);//2
        images.add(R.color.color1);//3
        images.add(R.color.color1);//4
        images.add(R.color.color1);//5
        images.add(R.color.color1);//6
        images.add(R.color.color1);//7
        images.add(R.color.color1);//8
        images.add(R.color.color1);//9
        images.add(R.color.color1);//10
        images.add(R.color.color1);//11
        images.add(R.color.color1);//12
        images.add(R.color.color1);//13
        images.add(R.color.color1);//14

        TutorialAdapter adapter = new TutorialAdapter(this);

        final IndicatorView indicator = (IndicatorView) findViewById(R.id.indicator);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        adapter.setTutorialImage(images);
        indicator.attachViewPager(viewPager);
    }
}
