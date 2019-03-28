package databind;

import com.example.lifecycledemo.HomeActivity;
import com.example.lifecycledemo.databinding.MainLayoutBinding;
import com.example.uidatabinding.databinding.core.AbsStore;

public class TabStore extends AbsStore<HomeActivity> {

    public TabPresent tabPresent;
    public MainLayoutBinding binding;
    public TabModel rootModel;

    public TabStore(HomeActivity homeActivity) {
        super(homeActivity);
        bindData();
    }

    private void bindData() {
        binding.setModel(rootModel);
        binding.setTabPresent(tabPresent);
        tabPresent.initData();
    }

    public TabStore(HomeActivity homeActivity, boolean autoBinding, boolean autoPresenter, boolean autoModel) {
        super(homeActivity, autoBinding, autoPresenter, autoModel);
    }


}
