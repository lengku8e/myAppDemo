package pagestack;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lifecycledemo.R;

public class FrameActivityTest extends FragmentActivity implements View.OnClickListener {

    PageNavigator pageNavigator = new PageNavigator();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_layout);

        TextView iv1 = (TextView) findViewById(R.id.iv1);
        TextView iv2 = (TextView) findViewById(R.id.iv2);
        TextView iv3 = (TextView) findViewById(R.id.iv3);
        TextView iv4 = (TextView) findViewById(R.id.iv4);

        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        pageNavigator.setContainerIds((R.id.fl));
        pageNavigator.setContainerActivity(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv1:
                pageNavigator.navigator(new Fragment1());
                break;
            case R.id.iv2:
                pageNavigator.navigator(new Fragment2());
                break;
            case R.id.iv3:

                break;
            case R.id.iv4:

                break;

            default:
                break;
        }
    }



}
