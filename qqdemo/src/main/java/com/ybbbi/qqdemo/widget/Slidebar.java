package com.ybbbi.qqdemo.widget;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ybbbi.qqdemo.R;
import com.ybbbi.qqdemo.Utils.StringUtils;
import com.ybbbi.qqdemo.view.fragment.ContactFragment;

import java.util.List;


/**
 * Created by fullcircle on 2017/1/3.
 */

public class Slidebar extends View {
    private String[] sections = {"搜", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint;
    private int x;
    private int viewHeight;
    private RecyclerView recyclerView;
    private TextView tv_float;
    private String startChar;
    private List<String> contacts;
    private ContactFragment.MyAdapter adapter;


    public Slidebar(Context context) {
        this(context, null);
    }

    public Slidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.GRAY);
        paint.setTextSize(getResources().getDimension(R.dimen.slide_text_size));
    }

    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        x = w / 2;
        viewHeight = h / 4 * 3;
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


                adapter = (ContactFragment.MyAdapter) recyclerView.getAdapter();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                startChar = sections[getIndex(event.getY())];
                tv_float.setText(startChar);
            case MotionEvent.ACTION_DOWN:
                contacts = adapter.getContacts();
                if(contacts !=null|| contacts.size()!=0){
                    smooth(contacts);
                }


                setBackgroundResource(R.color.gray);
                startChar = sections[getIndex(event.getY())];
                tv_float.setText(startChar);
                tv_float.setVisibility(View.VISIBLE);

                break;
            case MotionEvent.ACTION_UP:
                setBackgroundResource(R.color.transparent);
                tv_float.setVisibility(View.GONE);

                break;
        }
        return true;
    }


    /**
     * 根据联系人首字母滑动到对应位置
     * @param contacts
     */
    private void smooth(List<String> contacts) {
        for (int i = 0; i <contacts.size() ; i++) {
            if(StringUtils.getFirstChar(contacts.get(i)).equals(startChar)){
                recyclerView.smoothScrollToPosition(i);
                break;
            }
        }
    }

    /**
     * 根据y坐标获取字母索引
     * @param y 手当前触摸屏幕y坐标
     * @return result
     */


    private int getIndex(float y){
        int sectionheight = viewHeight / sections.length;
        int result =(int)(y-viewHeight/6)/sectionheight;
        if(y<=viewHeight/3/2){
        //将上部分空白处索引值返回为0
            return 0;
        }else if(y>=viewHeight/6*7){
        //将下部分空白处索引值返回为最后的索引值
            return sections.length-1;
        }else{

            return result<0?0:result>sections.length-1? sections.length-1:result;
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < sections.length; i++) {
            canvas.drawText(sections[i], x, viewHeight / sections.length * (i + 1) + viewHeight / 3 / 2, paint);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ViewGroup parent = (ViewGroup) getParent();
        if (tv_float == null) {
            recyclerView = parent.findViewById(R.id.recyclerview);
            tv_float = parent.findViewById(R.id.tv_float);

        }

    }

}
