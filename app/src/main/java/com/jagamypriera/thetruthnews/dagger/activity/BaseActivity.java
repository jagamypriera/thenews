package com.jagamypriera.thetruthnews.dagger.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.jagamypriera.thetruthnews.fragment.FragmentChangeObservable;
import com.jagamypriera.thetruthnews.R;
import com.jagamypriera.thetruthnews.dagger.Injector;
import com.jagamypriera.thetruthnews.dagger.activity.modules.ActivityModule;
import com.jagamypriera.thetruthnews.dagger.activity.modules.WidgetModule;
import com.jagamypriera.thetruthnews.fragment.FragmentModule;
import com.jagamypriera.thetruthnews.fragment.newslist.NewsListView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by jagamypriera on 11/1/17.
 */
//@SuppressWarnings("all")
public class BaseActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private int menuId = 0;
    private static ActivityComponent activityComponent;
    @Inject
    FragmentChangeObservable fragmentChangeObserver;
    @Inject FragmentManager manager;
    @Inject
    NewsListView newsDetail;
    public static ActivityComponent getActivityComponent() {
        return activityComponent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        activityComponent = Injector.getApplicationComponent().addActivityModules(
                        new ActivityModule(this),
                        new WidgetModule(),
                        new FragmentModule());
        Injector.getActivityComponent().inject(this);
        setContentView(R.layout.activity_all);
        ButterKnife.bind(this);
        final int[] previousHash = {0};
        fragmentChangeObserver.getFragment().subscribe(new Consumer<Fragment>() {
            @Override
            public void accept(Fragment fragment) throws Exception {
                Timber.d(fragment.hashCode()+"");
                if(previousHash[0] ==fragment.hashCode()) return;
                previousHash[0] =fragment.hashCode();
                replace(fragment, fragment.getClass().getCanonicalName());
            }
        });
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        fragmentChangeObserver.setFragment(new NewsListView());
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menuId == 0) return false;
        getMenuInflater().inflate(menuId, menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void replace(Fragment fragment, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (manager.findFragmentByTag(tag) == null) transaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out)
                .add(R.id.fragment_container, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss();
        else manager.popBackStackImmediate(tag, 0);


    }

    @Override
    public void onBackPressed() {
        int fragmentCount = manager.getBackStackEntryCount();
        if (fragmentCount > 1) {
            FragmentManager.BackStackEntry backEntry = manager.getBackStackEntryAt(fragmentCount - 1);
            String tag = backEntry.getName();
            manager.popBackStackImmediate();
        } else finish();
    }

    @Override
    public void onBackStackChanged() {

    }
}
