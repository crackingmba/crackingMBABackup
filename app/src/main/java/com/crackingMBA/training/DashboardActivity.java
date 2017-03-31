package com.crackingMBA.training;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Integer gotoTab;

private  TabLayout tabLayout;
    private String[] tabTitles;
    private TypedArray tabIcons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //initilizing db

        if(null != getIntent() && null!=getIntent().getExtras()){
            String tab = getIntent().getExtras().getString("gotoTab");
            if(null!=tab && ""!=tab){
                gotoTab = Integer.parseInt(tab);
            }
        }

        tabTitles = getResources().getStringArray(R.array.tab_items);
        tabIcons = getResources()
                .obtainTypedArray(R.array.tab_icons);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if(null != gotoTab){
            tabLayout.getTabAt(gotoTab).select();
            gotoTab = null;
        }
        setupTabIcons();
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                if(0 == position){
                    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    tabLayout.setupWithViewPager(mViewPager);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


       /* Intent dashboardIntent=new Intent(this,FragmentDemoActivity.class);
        startActivity(dashboardIntent);*/

        // enabling action bar app icon and behaving it as toggle button

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Class fragmentClass;
            Fragment fragment=null;
                try {

                    switch (position){
                        case 0:

                            fragmentClass = HomeFragment.class;
                            break;
                        case 1:
                            fragmentClass = PreparationFragment.class;
                            break;
                        case 2:
                            fragmentClass = MockTestFragment.class;
                            break;
                        case 3:
                            fragmentClass = MyDashboardFragment.class;
                            break;
                        default:
                            fragmentClass = HomeFragment.class;
                            break;
                    }

                    fragment = (Fragment) fragmentClass.newInstance();

                }catch (Exception e){
                    e.printStackTrace();

                }
            return fragment;

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
    private void setupTabIcons() {
        for(int i=0;i<tabIcons.length();i++){
            tabLayout.getTabAt(i).setIcon(tabIcons.getResourceId(i,-1));
        }
        tabLayout.setBackgroundColor(getResources().getColor(R.color.tabBGColor));
        tabLayout.setTabTextColors(getResources().getColor(R.color.tabFontColor),getResources().getColor(R.color.tabFocusFontColor));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


}
