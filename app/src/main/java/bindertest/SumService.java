package bindertest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.lifecycledemo.IMyAidlInterface;
import com.example.mylog.MLog;

public class SumService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    private IMyAidlInterface.Stub myBinder = new IMyAidlInterface.Stub() {
        @Override
        public int sum(int a, int b) throws RemoteException {
            MLog.d(a + "");
            MLog.d(b + "");
            return a + b;
        }
    };

}
