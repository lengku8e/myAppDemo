package databind;

import android.databinding.ObservableField;
import android.support.annotation.Nullable;

import com.example.uidatabinding.databinding.core.AbsModel;

public class TabModel extends AbsModel<TabStore> {

    public final ObservableField<Tab> tabObservableField = new ObservableField<>();

    public enum Tab {
        Taxi(0),
        Express(1),
        Special(2);

        public final int position;

        Tab(int position) {
            this.position = position;
        }

        @Nullable
        public static Tab fromPosition(int position) {
            switch (position) {
                case 0:
                    return Taxi;
                case 1:
                    return Express;
                case 2:
                    return Special;
                default:
                    return null;
            }
        }
    }

}
