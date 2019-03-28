package widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.lifecycledemo.MyApplication;

import util.ScreenUtils;


public class EmptyTopLayout extends FrameLayout {
    public EmptyTopLayout(Context context) {
        super(context);
    }

    public EmptyTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(ScreenUtils.getStatusBarHeightFullScreen
                    (MyApplication.getInstance().context), MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
