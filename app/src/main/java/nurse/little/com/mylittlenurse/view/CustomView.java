package nurse.little.com.mylittlenurse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
/**
 * 自定义RelativeLayout(点击RelativeLayout时,让RelativeLayout中的EditText得到焦点)
 */
public class CustomView extends RelativeLayout{
	
	private CustomEditText editText;

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public boolean onTouchEvent(MotionEvent event) {
		for(int i=0;i<getChildCount();i++){
			if(getChildAt(i) instanceof CustomEditText) {
				editText = (CustomEditText) getChildAt(i);
				editText.requestFocus();
				editText.setSelection(editText.getText().length());
				break;
			}
		}
		return super.onTouchEvent(event);
	}
	
}
