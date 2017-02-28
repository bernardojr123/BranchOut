package com.example.bernardojr.branchout.gui;

        import android.support.design.widget.TabLayout;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import com.example.bernardojr.branchout.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentPagerAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.home_act_viewpager);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1, false);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.home_act_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}