package base;

public interface BaseActivityInter {

    enum PageStyle {
        WHITE, BLACK
    }

    boolean supportFullScreen();

    int statusBarColor();

    PageStyle pageStyle();
}
