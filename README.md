# IndicatorView

View to indicate current page of the view pager

#View

#Download

Download via Gradle:

```gradle
soon
```

#Usage

Add IndicatorView to your xml layout

```xml
<steelkiwi.com.library.view.IndicatorView
    android:id="@+id/indicator"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    app:iv_bar_color="@android:color/black"
    app:iv_corner_radius="@dimen/radius"
    app:iv_idle_color="@color/item_idle_color"
    app:iv_select_color="@color/item_select_color"
    app:iv_size="@dimen/item_size"
    app:iv_text_color="@android:color/white"
    app:iv_text_size="@dimen/text_size"
    app:iv_action="hung_down|look_up"/>
```

You can customize view like you want, through this attributes

 * app:iv_bar_color - background color for indicator bar
 * app:iv_corner_radius - corner radius for each indicator item
 * app:iv_idle_color - color for idle state of the items
 * app:iv_select_color - color for selected state of the items
 * app:iv_size - indicator item size
 * app:iv_text_color - indicator item text color
 * app:iv_text_size - indicator item text size
 * app:iv_action - indicator item show action type

Don`t forget to initialize IndicatorView and attach ViewPager to it

```java
IndicatorView indicator = (IndicatorView) findViewById(R.id.indicator);
ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
indicator.attachViewPager(viewPager);
```

# License

```
Copyright © 2017 SteelKiwi, http://steelkiwi.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```