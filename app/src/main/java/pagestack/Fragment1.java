package pagestack;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lifecycledemo.R;

public class Fragment1 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 创建view   也可以直接在这里面获取view中的控件。
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.image, null);

        return view;
    }

    // 创建view完毕  可以直接在onCreateView中获取view中控件。
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        iv.setImageResource(R.mipmap.ic_launcher);
    }
}