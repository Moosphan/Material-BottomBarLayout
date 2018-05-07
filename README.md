# Material-BottomBarLayout
A material navigation bar library which has pretty animations and different ways of arrangement.

>![preview](https://github.com/Moosphan/Material-BottomBarLayout/blob/c1c2a24ba500c9090e48df025ab027eaa206e971/MaterialBottomBar/art/bottom_bar.gif)

## Usage

[ ![Download](https://api.bintray.com/packages/moosphon/maven/Material-BottomBarLayout/images/download.svg) ](https://bintray.com/moosphon/maven/Material-BottomBarLayout/_latestVersion)

### In gradle:

```
compile 'com.moos:Material-BottomBarLayout:1.0'
```

### In xml:

```
<com.moos.library.BottomBarLayout
    android:id="@+id/bottom_bar"
    android:layout_width="match_parent"
    android:layout_height="56dp"
    app:tabs_arrange_way="horizontal"
    android:elevation="2dp"
    android:layout_alignParentBottom="true">

</com.moos.library.BottomBarLayout>
```



### In java:

```
private BottomTabView tab_home, tab_look, tab_mine;
......

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomBarLayout bottomBarLayout = findViewById(R.id.bottom_bar);
       
        tab_home = new BottomTabView(this);
        tab_home.setTabIcon(R.drawable.home);
        tab_home.setTabTitle("Home");

        tab_look = new BottomTabView(this);
        tab_look.setTabIcon(R.drawable.activity);
        tab_look.setTabTitle("Discover");

        tab_mine = new BottomTabView(this);
        tab_mine.setTabIcon(R.drawable.user);
        tab_mine.setTabTitle("Mine");
        tab_mine.setUnreadCount(100);


        bottomBarLayout
                .addTab(tab_home)
                .addTab(tab_look)
                .addTab(tab_mine)
                .create(new BottomBarLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(BottomTabView tab) {
                        //you can switch the fragment here
                        Log.e(TAG, "onTabSelected: ====="+tab.getTabPosition() );
                    }

                    @Override
                    public void onTabUnselected(BottomTabView tab) {

                    }

                    @Override
                    public void onTabReselected(BottomTabView tab) {

                    }
                });
    }
```

### Bind with ViewPager:

```
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new CardViewPagerAdapter(data));
        bottomBarLayout.bindViewPager(viewPager);
```

### API Details

- **BottomTabView**

  Description:the tab item of bottom bar, like `TabItem`.

  ​

  The methods document:

  | Method                   | Usage                                                       |
  | ------------------------ | ----------------------------------------------------------- |
  | getTabPosition           | get the position of  current tab                            |
  | setTabIcon               | set the icon res for tab                                    |
  | setTabTitle              | set the title for tab                                       |
  | setTabIconSize           | set the icon size for tab                                   |
  | setTabTitleSize          | set title text size for tab                                 |
  | setTabPadding            | set padding for each tab                                    |
  | setSelectedColor         | set selected color for tab                                  |
  | setUnselectedColor       | set unselected color for tab                                |
  | setTabIconOnly           | weather show the tab title                                  |
  | setTabTitleOnly          | weather show the tab icon                                   |
  | setBubbleBackground      | set bg for unread bubble view                               |
  | setBubbleSize            | set size for bubble, only update the 'vertical size'        |
  | setUnreadTextSize        | set size for unread text                                    |
  | setUnreadTextColor       | set color for unread text                                   |
  | setUnreadTextPadding     | set padding of unread text, only for padding left and right |
  | setUnreadTextMarginTop   | set margin top to unreadText                                |
  | setUnreadTextMarginRight | set margin right to unreadText                              |
  | getTabIconView           | get the tab icon's imageView                                |
  | getTabTextView           | get the tab title view                                      |
  | getTabContainer          | get the container of tab(LinearLayout)                      |

- **BottomBarLayout**

  Description:the tabs' container, like `TabLayout`.

  ​

  The methods document:

  | Method                             | Usage                                     |
  | ---------------------------------- | ----------------------------------------- |
  | addTab(BottomTabView  tab)         | add the tab for container                 |
  | getCurrentTabPosition              | get current selected tab's position       |
  | getTabCount                        | get the count of tabs in container        |
  | bindViewPager(ViewPager viewPager) | bind the viewPager and scroll with it     |
  | create                             | set call back of tab's selected operation |




## To-do

- support `horizontal` and `vertical` style.
- more animations for tabs.
- bind with viewpager or other slide views.
- change bottomBarLayout background when selected.
- support different states of tab icons

## About me

Welcome to improve it with me and give me some issues.

Blog:<http://moos.club/>

E-mail:moosphon@gmail.com

Twitter:[![Twitter](https://img.shields.io/twitter/url/https/github.com/Moosphan/Material-ProgressView.svg?style=social)](https://twitter.com/intent/tweet?text=Wow:&url=https%3A%2F%2Fgithub.com%2FMoosphan%2FMaterial-ProgressView)

Buy  me a coffee:



![coffee](https://github.com/Moosphan/Material-ProgressView/blob/master/MaterialProgressView-master/image/a_coffee.png)


## License

```
Copyright 2018 moosphon

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

