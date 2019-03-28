package databind;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.lifecycledemo.BR;


public class User extends BaseObservable {
    private String name;
    private String age;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Bindable
    public String sex;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
