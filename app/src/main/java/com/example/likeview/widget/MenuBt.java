package com.example.likeview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.likeview.R;
/**
 * 时间：2018/9/10 11:04
 * 姓名：韩晓康
 * 功能：
 */
public class MenuBt extends LinearLayout {
    private Context context;
    private RelativeLayout llView;
    private ImageView ivStart;
    private int ivStartRes;
    private int[] img = new int[]{R.mipmap.heart, R.mipmap.heart1, R.mipmap.heart2, R.mipmap.heart3, R.mipmap.heart3};
    private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ClickItem clickItem;
    private int position;//1 左下 2左上 3右下 4 右上 指的是添加图片的位置
    private int circlrR;//基准圆形半径
    private boolean isStart = true;//控制执行 放射动画还是收缩动画
    float mX = 0;
    float mY = 0;
    private ImageView childAt;//itemView

    public MenuBt(Context context) {
        this(context, null);
    }

    public MenuBt(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MenuBt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.menu_bt, this, true);
        llView = (RelativeLayout) view.findViewById(R.id.ll_view);
        ivStart = (ImageView) view.findViewById(R.id.iv_start);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.MenuBt);
        if (array!=null) {
            position=array.getInteger(R.styleable.MenuBt_position,4);
            ivStartRes=array.getResourceId(R.styleable.MenuBt_bitmap,R.mipmap.heart);
        }
        array.recycle();
        addView();
        startClick();
    }

    public void setImg(int[] img) {
        this.img = img;
    }

    public void setClickItem(ClickItem clickItem) {
        this.clickItem = clickItem;
    }

    private void startClick() {
        ivStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = llView.getVisibility();
                if (visibility == 0) {
                    isStart = false;
                } else {
                    isStart = true;
                    llView.setVisibility(VISIBLE);
                }
                start();
            }
        });
    }

    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:01
     * 描述  添加子View
     */
    private void addView() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        switch (position) {
            case 1:
                llView.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
                break;
            case 2:
                llView.setGravity(Gravity.TOP | Gravity.LEFT);
                lp.gravity = Gravity.TOP | Gravity.LEFT;
                break;
            case 3:
                llView.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
                lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
                break;
            case 4:
                llView.setGravity(Gravity.TOP | Gravity.RIGHT);
                lp.gravity = Gravity.TOP | Gravity.RIGHT;
                break;
        }
        ivStart.setLayoutParams(lp);
        ivStart.setImageResource(ivStartRes);
        llView.removeAllViews();
        for (int i = 0; i < img.length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            final int finalI = i;
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickItem.clickItem(finalI);
                    start();
                }
            });
            llView.addView(imageView);
            imageView.setImageResource(img[i]);
        }
    }
    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:02
     * 描述  确定移动的位置
     */
    private void start() {
        for (int i = 0; i < img.length; i++) {
            childAt = (ImageView) llView.getChildAt(i);
            circlrR = (getWidth() / 2 - 50);
            switch (position) {
                case 1:
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 2:
                    mX = (float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = (float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 3:
                    mX = -(float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = -(float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));
                    break;
                case 4:
                    mX = -(float) (circlrR * (Math.cos(Math.PI / 2 / (img.length - 1) * i)));
                    mY = (float) ((getWidth() / 2 - 50) * (Math.sin(Math.PI / 2 / (img.length - 1) * i)));

                    break;
            }
            startAnimator();
        }
    }
    /**
     * 作者  韩晓康
     * 时间  2018/9/11 17:02
     * 描述  动画开始
     */
    private void startAnimator() {
        if (!isStart) {
            animator = ObjectAnimator.ofFloat(childAt, "translationY", mY, 0);
            animator1 = ObjectAnimator.ofFloat(childAt, "translationX", mX, 0);
        } else {
            animator1 = ObjectAnimator.ofFloat(childAt, "translationX", 0, mX);
            animator = ObjectAnimator.ofFloat(childAt, "translationY", 0, mY);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        animatorSet.playTogether(animator, animator1);
        animatorSet.start();
        if (!isStart) {
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llView.setVisibility(INVISIBLE);
                }
            });
        }

    }

}
