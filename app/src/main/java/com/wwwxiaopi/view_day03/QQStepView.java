package com.wwwxiaopi.view_day03;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 高小皮 on 2017/5/31.
 */

public class QQStepView extends View {


    private int mOuterColor = Color.RED;

    private int mInterColor = Color.RED;

    private int mBordWidth = 20;  ///px
    private int mStepTextSize = 20;
    private int mStepTextColor = Color.BLACK;
    private Paint mOutpaint, mInpaint, mTextPaint;

    //总共的
    private int mStepMax = 0;
    //当前的
    private int mCurrentStep = 0;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.自定义属性
        //2.确定自定义属性，编写attrs.xml
        //3.在布局中使用
        //4.在自定义View中获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInterColor = array.getColor(R.styleable.QQStepView_interColor, mInterColor);
        mBordWidth = (int) array.getDimension(R.styleable.QQStepView_bordWidth, mBordWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();


        mOutpaint = new Paint();
        mOutpaint.setAntiAlias(true);
        mOutpaint.setStrokeWidth(mBordWidth);
        mOutpaint.setColor(mOuterColor);
        mOutpaint.setStrokeCap(Paint.Cap.ROUND);//圆弧
        mOutpaint.setStyle(Paint.Style.STROKE);//画笔实心

        mInpaint = new Paint();
        mInpaint.setAntiAlias(true);
        mInpaint.setStrokeWidth(mBordWidth);
        mInpaint.setColor(mInterColor);
        mInpaint.setStrokeCap(Paint.Cap.ROUND);//圆弧
        mInpaint.setStyle(Paint.Style.STROKE);//画笔实心
//        mTextPaint
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);


    }
    //5.onMeasure()

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //调用者在布局文件中  wrap_content 宽度高度不一致
        //获取模式 ST_MOST 40dp
        //宽度不一致 取最小值 确保是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width > height ? height : width, width > height ? height : width);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.1 画圆弧 分析 ：圆弧闭合了 边缘没显示 描边有宽度 圆弧

        RectF rectF = new RectF(mBordWidth / 2, mBordWidth / 2, getWidth() - mBordWidth / 2, getHeight() - mBordWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOutpaint);

        //6.2内圆弧 怎么画肯定不能写死 百分比 是用户设置 从外面传
        if (mStepMax == 0) return;
        float sweepAngle = (float)mCurrentStep / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInpaint);
        //6.3 文字
        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        mTextPaint.getTextBounds(stepText, 0, stepText.length(), textBounds);
        int dx = getWidth() / 2 - textBounds.width() / 2;
        //基线 baseLine
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);

    }

    //7.其他 写动画 synchronized考虑多线程会出问题  可以不加
    public synchronized void setStepMax(int setStepMax) {
        this.mStepMax = setStepMax;
    }

    public synchronized void setmCurrentStep(int currentStep) {
        this.mCurrentStep = currentStep;
        //不断绘制
        invalidate();
    }

}
