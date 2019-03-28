package pagestack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.ref.SoftReference;

public class PageNavigator {

    private int replaceContainerId = -1;
    private SoftReference<FragmentActivity> containerActivityRef;
    private FragmentManager mFragmentManager;


    public void setContainerIds(int replaceContainerId) {
        this.replaceContainerId = replaceContainerId;
    }

    public void setContainerActivity(FragmentActivity containerActivity) {
        containerActivityRef = new SoftReference<>(containerActivity);
        mFragmentManager = containerActivity.getSupportFragmentManager();
    }

    public void navigator(Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(replaceContainerId, fragment);
        ft.commit();
    }

}
