package databind;


import android.databinding.BindingAdapter;
import android.text.Spanned;
import android.widget.TextView;

/**
 * BindingAdapter 使用介绍
 * 1.databinding框架view与ui的绑定包括以下几步：
 *      对view的binding表达式求值、寻找合适的bindingAdapter为view赋值、优先使用用户自定义的bindingAdapter
 * 2.通过BindingAdapter来拦截view与数据的绑定过程并作为一个中间环节可以处理
 * 3.系统自定义了很多基础的bindingAdapter
 */
public class MyBindingAdapter {
   @BindingAdapter("android:text")
    public static void mySetText(TextView view, CharSequence text) {
       final CharSequence oldText = view.getText();
       if (text == oldText || (text == null && oldText.length() == 0)) {
           return;
       }
       if (text instanceof Spanned) {
           if (text.equals(oldText)) {
               return; // No change in the spans, so don't set anything.
           }
       } else if (!haveContentsChanged(text, oldText)) {
           return; // No content changes, so don't set anything.
       }
       CharSequence upperText = text.toString() + "我的自定义BindingAdapter控制器";
       view.setText(upperText);
   }

    private static boolean haveContentsChanged(CharSequence str1, CharSequence str2) {
        if ((str1 == null) != (str2 == null)) {
            return true;
        } else if (str1 == null) {
            return false;
        }
        final int length = str1.length();
        if (length != str2.length()) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return true;
            }
        }
        return false;
    }
}
