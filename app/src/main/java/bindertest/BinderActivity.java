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

import com.example.lifecycledemo.MyApplication;
import com.example.lifecycledemo.R;
import com.example.mylog.MLog;

import static bindertest.CalculateService.DESCRIPTOR;

public class BinderActivity extends Activity {

    private IBinder binder;

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
                mulInvoked();
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MLog.d("onServiceConnected");
            binder = service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            MLog.d("onServiceDisconnected");
            binder = null;
        }
    };

    private void bindService() {
        Intent intent = new Intent(this, CalculateService.class);
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

    /**乘法运算*/
    public void mulInvoked(){
        if (binder == null){
            Toast.makeText(this, "未连接服务端或服务端被异常杀死", Toast.LENGTH_SHORT).show();
        } else{
            android.os.Parcel data = android.os.Parcel.obtain();//Parcel.obtain()：获得一个新的parcel ，相当于new一个对象；
            android.os.Parcel reply = android.os.Parcel.obtain();
            int result;
            try{
                data.writeInterfaceToken(DESCRIPTOR);//写入服务的标志，与enforceInterface（）配套使用；
                data.writeInt(11);
                data.writeInt(12);
                binder.transact(0x110, data, reply, 0);//transact(int code, Parcel data, Parcel reply, int flags)
                reply.readException();//在Parcel队头读取，若读取值为异常，则抛出该异常；否则，程序正常运行。
                result = reply.readInt();
                Toast.makeText(this, result + "", Toast.LENGTH_SHORT).show();
            } catch (RemoteException e){
                e.printStackTrace();
            } finally{
                reply.recycle();
                data.recycle();
            }
        }
    }


}
