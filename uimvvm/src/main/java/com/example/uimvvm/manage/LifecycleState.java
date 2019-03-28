package com.example.uimvvm.manage;


import com.example.uimvvm.LifecycleEvent;

enum LifecycleState {

    NONE(0),

    CREATED(1),

    VIEW_CREATED(2),

    STARTED(3),

    RESUMED(4),

    PAUSED(5),

    STOPPED(6),

    VIEW_DESTROYED(7),

    DESTROYED(8);

    private final int rawValue;

    LifecycleState(int rawValue) {
        this.rawValue = rawValue;
    }

    static LifecycleState fromEvent(LifecycleEvent e) {
        switch (e) {
            case ON_CREATE:
                return CREATED;
            case ON_CREATE_VIEW:
                return VIEW_CREATED;
            case ON_START:
                return STARTED;
            case ON_RESUME:
                return RESUMED;
            case ON_PAUSE:
                return PAUSED;
            case ON_STOP:
                return STOPPED;
            case ON_DESTROY_VIEW:
                return VIEW_DESTROYED;
            case ON_DESTROY:
                return DESTROYED;
            default:
                return NONE;
        }
    }

    int getRawValue() {
        return rawValue;
    }
}
