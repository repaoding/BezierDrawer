package cn.wave.drawer;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;

/**
 * created by sfx on 2018/1/31.
 */

public class WaveDrawerListener implements DrawerLayout.DrawerListener {

    private static final String TAG = "WaveDrawerListener";

    private void log(String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        log(slideOffset + " " + drawerView.getClass().getName());
        if (drawerView instanceof WaveNavigationView) {
            ((WaveNavigationView) drawerView).setOffset(slideOffset);
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        log(" onDrawerOpened " + drawerView.getClass().getName());
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        log(" onDrawerClosed " + drawerView.getClass().getName());
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        log(" onDrawerStateChanged : " + newState);
    }
}
