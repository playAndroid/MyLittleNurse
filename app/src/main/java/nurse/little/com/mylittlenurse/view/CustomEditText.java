package nurse.little.com.mylittlenurse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnFocusChangeListener;
/**
 * 自定义EditText(EditText附上焦点后显示在字符串的后面)
 */
public class CustomEditText extends EditText implements OnFocusChangeListener{

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setOnFocusChangeListener(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			setSelection(getText().length());
		}
	}
}
