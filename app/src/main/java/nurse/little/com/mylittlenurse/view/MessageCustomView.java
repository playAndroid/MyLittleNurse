package nurse.little.com.mylittlenurse.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import nurse.little.com.mylittlenurse.R;

/**
 * 录入信息的自定义控件
 * Created by user on 2016/3/14.
 */
public class MessageCustomView extends LinearLayout {

    private TextView title;
    private EditText content;
    private String contentText;
    private String titleText;
    private int textType;//内容类型
    //    private TextView unit;
//    private String unitText;
    public static final int TEXTTYPE_STR = 0;
    public static final int TEXTTYPE_NUM = 1;

    public MessageCustomView(Context context) {
        this(context, null);
    }


    public MessageCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        contentText = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "contentText");
//        titleText = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "titleText");
        /**
         * 获取自定义属性 官方推荐写法
         */

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomTitleView_titleText:
                    titleText = a.getString(i);
                    break;
                case R.styleable.CustomTitleView_contentText:
                    contentText = a.getString(i);
                    break;
                case R.styleable.CustomTitleView_contentTextType:
                    textType = a.getInt(i, 0);
                    break;
            }
        }
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.common_message, this);
        title = (TextView) view.findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
//        unit = (TextView) findViewById(R.id.unit);

        if (!TextUtils.isEmpty(titleText)) {
            title.setText(titleText);
//            Log.e("hlk", "--------->" + titleText);
        }
        if (!TextUtils.isEmpty(contentText)) {
            content.setHint(contentText);
        }
        switch (textType) {
            case TEXTTYPE_STR:
                break;
            case TEXTTYPE_NUM:
                break;
        }
    }

    public EditText getContent() {
        return content;
    }

}
