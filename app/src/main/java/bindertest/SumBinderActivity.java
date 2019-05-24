package bindertest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifecycledemo.IMyAidlInterface;
import com.example.lifecycledemo.MyApplication;
import com.example.lifecycledemo.R;
import com.example.mylog.MLog;


public class SumBinderActivity extends Activity {

    private IMyAidlInterface iMyAidlInterface = null;

    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binder_test_activity);
        TextView textView1 = findViewById(R.id.button1);
        TextView textView2 = findViewById(R.id.button2);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum();
            }
        });
    }

    private void sum() {
        int a  = 0;
        try {
            a = iMyAidlInterface.sum(1, 2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        MLog.d(a + "SumBinderActivity" );

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MLog.d("serviceDisconnected");
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MLog.d("onServiceDisconnected");
        }
    };

    private void bindService() {
        Intent intent = new Intent(this, SumService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Toast.makeText(MyApplication.getInstance().context, "绑定成功", Toast.LENGTH_SHORT).show();
    }

    public void unbindService(){
        try {
            unbindService(serviceConnection);
            serviceConnection=null;//这里必须代码置空
            Toast.makeText(MyApplication.getInstance().context, "解绑成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MyApplication.getInstance().context, "未绑定", Toast.LENGTH_SHORT).show();
        }
    }





}
