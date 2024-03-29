package quiz.live.online.arksoft.com.onlinequiz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class Home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=(BottomNavigationView )findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment=null;

                switch (menuItem.getItemId()){

                    case R.id.action_category:
                        selectedFragment=CategoryFragment.newInstance();
                        break;

                    case R.id.action_ranking:
                        selectedFragment=RankingFragment.newInstance();
                        break;
                }

                FragmentTransaction fragmentTransaction=    getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.frame_layout,selectedFragment);
                fragmentTransaction.commit();
                return true;
            }



        });

        setDefaultFragment();


    }

    private void setDefaultFragment() {

        FragmentTransaction fragmentTransaction=    getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout,CategoryFragment.newInstance());
        fragmentTransaction.commit();

    }
}
