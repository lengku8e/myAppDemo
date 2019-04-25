package bindertest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.example.mylog.MLog;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class CalculateService extends Service {


    public static final String DESCRIPTOR = "CalculateService";
    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        MLog.d("CalculateService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MLog.d("CalculateService", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        MLog.d("CalculateService", "onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        MLog.d("CalculateService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        MLog.d("CalculateService", "onRebind");
        super.onRebind(intent);
    }

    private class MyBinder extends Binder {


        @Override
        public void attachInterface(IInterface owner, String descriptor) {
            super.attachInterface(owner, descriptor);
        }

        @Override
        public String getInterfaceDescriptor() {
            return super.getInterfaceDescriptor();
        }

        @Override
        public boolean pingBinder() {
            return super.pingBinder();
        }

        @Override
        public boolean isBinderAlive() {
            return super.isBinderAlive();
        }

        @Override
        public IInterface queryLocalInterface(String descriptor) {
            return super.queryLocalInterface(descriptor);
        }

        @Override
        protected boolean onTransact(int code, Parcel data,Parcel reply, int flags) throws RemoteException {
            int arg0;
            int arg1;
            int result;
            data.enforceInterface(DESCRIPTOR);
            arg0 = data.readInt();//读取一个整数
            arg1 = data.readInt();

            switch (code){
                case 0x110:{
                    result = arg0 * arg1;
                    reply.writeNoException();//Parcel队头写入“无异常“
                    reply.writeInt(result);//写入一个整数
                    return true;
                }
                case 0x111:{
                    result = arg0 / arg1;
                    reply.writeNoException();//Parcel队头写入“无异常“
                    reply.writeInt(result);//写入一个整数
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void dump(FileDescriptor fd, String[] args) {
            super.dump(fd, args);
        }

        @Override
        public void dumpAsync( FileDescriptor fd,String[] args) {
            super.dumpAsync(fd, args);
        }

        @Override
        protected void dump(FileDescriptor fd,  PrintWriter fout,  String[] args) {
            super.dump(fd, fout, args);
        }

        @Override
        public void linkToDeath(IBinder.DeathRecipient recipient, int flags) {
            super.linkToDeath(recipient, flags);
        }

        @Override
        public boolean unlinkToDeath(IBinder.DeathRecipient recipient, int flags) {
            return super.unlinkToDeath(recipient, flags);
        }
    }


}
