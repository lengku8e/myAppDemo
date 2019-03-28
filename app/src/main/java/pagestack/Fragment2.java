package pagestack;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lifecycledemo.R;

public class Fragment2 extends Fragment {
    private static final String TYPE = "type";
    private static final String DATE = "date";
    private static final int GROUP = -1;
    private static final int ITEM = -2;

    private ArrayList<HashMap<String, Object>> item = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // 创建view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list, null);

        return view;
    }

    // 创建view完毕
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView lv = (ListView) view.findViewById(R.id.lv);
        MyAdaper adapter = new MyAdaper(getActivity(), -1);

        item = new ArrayList<HashMap<String, Object>>();

        String[] groups = { "家人", "朋友", "同学", "同事" };
        String[] date = { "张三", "李四", "王五", "狗儿" };
        for (String str : groups) {
            HashMap<String, Object> group_map = new HashMap<String, Object>();

            group_map.put(TYPE, GROUP);
            group_map.put(DATE, str);
            item.add(group_map);
            for (String n : date) {

                HashMap<String, Object> date_map = new HashMap<String, Object>();

                date_map.put(TYPE, ITEM);
                date_map.put(DATE, n);
                item.add(date_map);

            }

        }
        lv.setAdapter(adapter);
    }

    private class MyAdaper extends ArrayAdapter {
        private LayoutInflater flater = null;
        private Context context;

        public MyAdaper(Context context, int resource) {
            super(context, resource);
            this.context = context;
            flater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public int getItemViewType(int position) {
            HashMap<String, Object> map = item.get(position);
            return (Integer) map.get(TYPE);
        }

        @Override
        public int getViewTypeCount() {

            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            // 根据不同的view type加载不同的布局文件。
            switch (type) {
                case GROUP:

                    convertView = flater.inflate(android.R.layout.simple_list_item_1, null);
                    TextView tv1 = (TextView) convertView.findViewById(android.R.id.text1);
                    tv1.setText(item.get(position).get(DATE) + "");
                    tv1.setBackgroundColor(Color.RED);
                    tv1.setTextSize(20);

                    break;
                case ITEM:
                    convertView = flater.inflate(android.R.layout.simple_list_item_1, null);
                    TextView tv2 = (TextView) convertView.findViewById(android.R.id.text1);
                    tv2.setText(item.get(position).get(DATE) + "");

                    break;

                default:
                    break;
            }

            return convertView;
        }

    }
}