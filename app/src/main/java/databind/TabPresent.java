package databind;

import android.support.annotation.NonNull;

import com.example.uidatabinding.databinding.core.AbsPresenter;

public class TabPresent extends AbsPresenter<TabStore> {


    public void setTab(@NonNull TabModel.Tab tab) {
        store.rootModel.tabObservableField.set(tab);
    }

    public void initData() {
        store.rootModel.tabObservableField.set(TabModel.Tab.Special);
    }

    public void clickTab(TabModel.Tab tab) {
        setTab(tab);
    }
}
