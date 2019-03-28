package lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

public class MainPresenter implements IPresenter{

    private static final String TAG = "myPresenter";

    public MainPresenter(Context context){

    }

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onCreate" + this.getClass().toString());
    }

    @Override
    public void onStart(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onStart" + this.getClass().toString());
    }

    @Override
    public void onResume(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onResume" + this.getClass().toString());
    }

    @Override
    public void onPause(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onPause" + this.getClass().toString());
    }

    @Override
    public void onStop(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onStop" + this.getClass().toString());
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "BasePresenter.onDestroy" + this.getClass().toString());
    }
}
