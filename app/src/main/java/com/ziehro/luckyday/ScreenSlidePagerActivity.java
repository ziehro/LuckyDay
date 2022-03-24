package com.ziehro.luckyday;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePagerActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager2 viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);

        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private List<Zodiac> zodiacs;
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            this.zodiacs = this.intDatas();
        }

        private List<Zodiac> intDatas()  {
            Zodiac emp1 = new Zodiac("Aries", "Mar 21 - Apr 19", "H");
            Zodiac emp2 = new Zodiac("Taurus", "Apr 20 - May 20", "Project Manager");
            Zodiac emp3 = new Zodiac("Gemini", "May 21 - Jun 20", "President of Sales");
            Zodiac emp4 = new Zodiac("Cancer", "Jun 21 - Jul 22", "Web Designer");
            Zodiac emp5 = new Zodiac("Leo", "Jul 23 - Aug 22", "Project Manager");
            Zodiac emp6 = new Zodiac("Virgo", "Aug 23 - Sep 22", "President of Sales");
            Zodiac emp7 = new Zodiac("Libra", "Sep 23 - Oct 22", "Web Designer");
            Zodiac emp8 = new Zodiac("Scorpio", "Oct 23 - Nov 21", "Project Manager");
            Zodiac emp9 = new Zodiac("Sagittarius", "Nov 22 - Dec 21", "President of Sales");
            Zodiac emp10 = new Zodiac("Capricorn", "Dec 22 - Jan 19", "Web Designer");
            Zodiac emp11 = new Zodiac("Aquarius", "Jan 20 - Feb 18", "Project Manager");
            Zodiac emp12 = new Zodiac("Pisces", "Feb 19 - Mar 20", "President of Sales");

            List<Zodiac> list = new ArrayList<Zodiac>();
            list.add(emp1);
            list.add(emp2);
            list.add(emp3);
            list.add(emp4);
            list.add(emp5);
            list.add(emp6);
            list.add(emp7);
            list.add(emp8);
            list.add(emp9);
            list.add(emp10);
            list.add(emp11);
            list.add(emp12);
            return list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Zodiac zodiac = this.zodiacs.get(position);
            return new ScreenSlidePageFragment(zodiac);
        }


        @Override
        public int getItemCount() {
            return this.zodiacs.size();
        }

       /* @Override
        public Fragment createFragment(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }*/
    }


}