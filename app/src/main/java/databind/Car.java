package databind;

import android.databinding.ObservableField;

public class Car {
    public ObservableField<String> name = new ObservableField<>();

    public void setName(String name) {
        this.name.set(name);
    }

}
