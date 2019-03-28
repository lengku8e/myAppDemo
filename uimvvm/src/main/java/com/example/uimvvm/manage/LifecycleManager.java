package com.example.uimvvm.manage;


import com.example.uimvvm.LifecycleEvent;

/**
 * 处理 UIComponent 生命周期
 * 接口由 UIComponentManage 内部使用，并保证线程一致性
 */
class LifecycleManager {

    private final ViewManager viewManager;

    LifecycleManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    void transform(UIComponentDelegate delegate, LifecycleState targetState) throws IllegalTransformError {
        while (true) {
            int gap = targetState.getRawValue() - delegate.getLifecycle().getRawValue();
            if (gap > 0) {
                toNextState(delegate);
            } else if (gap < 0) {
                toPreviousState(delegate);
            } else {
                break;
            }
        }
    }

    private void toNextState(UIComponentDelegate delegate)
            throws IllegalTransformError {

        LifecycleState currentState = delegate.getLifecycle();

        switch (currentState) {
            case NONE:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_CREATE);
                delegate.setLifecycle(LifecycleState.CREATED);
                break;
            case CREATED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_CREATE_VIEW);
                delegate.setLifecycle(LifecycleState.VIEW_CREATED);
                viewManager.updateComponent(delegate, delegate.getHost().getView());
                break;
            case VIEW_CREATED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_START);
                delegate.setLifecycle(LifecycleState.STARTED);
                break;
            case STARTED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_RESUME);
                delegate.setLifecycle(LifecycleState.RESUMED);
                break;
            case RESUMED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_PAUSE);
                delegate.setLifecycle(LifecycleState.PAUSED);
                break;
            case PAUSED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_STOP);
                delegate.setLifecycle(LifecycleState.STOPPED);
                break;
            case STOPPED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_DESTROY_VIEW);
                delegate.setLifecycle(LifecycleState.VIEW_DESTROYED);
                break;
            case VIEW_DESTROYED:
                // DESTROY VIEW 后，保留 VIEW 的位置
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_DESTROY);
                delegate.setLifecycle(LifecycleState.DESTROYED);
                break;
            case DESTROYED:
                throw new IllegalTransformError("Transforms toNextState: DESTROYED -> ?");
            default:
                throw new IllegalTransformError("Transforms toNextState: UNKNOWN STATE " + currentState);
        }
    }

    private void toPreviousState(UIComponentDelegate delegate)
            throws IllegalTransformError {

        final LifecycleState lifecycleState = delegate.getLifecycle();

        switch (lifecycleState) {
            case PAUSED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_RESUME);
                delegate.setLifecycle(LifecycleState.RESUMED);
                break;
            case STOPPED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_START);
                delegate.setLifecycle(LifecycleState.STARTED);
                break;
            case VIEW_DESTROYED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_CREATE_VIEW);
                delegate.setLifecycle(LifecycleState.VIEW_CREATED);
                viewManager.updateComponent(delegate, delegate.getHost().getView());
                break;
            case DESTROYED:
                delegate.getHost().onLifecycleEvent(LifecycleEvent.ON_CREATE);
                delegate.setLifecycle(LifecycleState.CREATED);
                break;
            default:
                throw new IllegalTransformError(
                        "Transforms toPreviousState: " + lifecycleState + " CAN NOT BE PREVIOUS");
        }
    }
}
