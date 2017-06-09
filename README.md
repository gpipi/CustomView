# CustomView
仿QQ步数自定义View
==============
 #自定义当然要自定义属性
```Java
     <declare-styleable name="QQStepView">
        <!--外层颜色-->
        <attr name="outerColor" format="color" />
        <!--里层颜色-->
        <attr name="interColor" format="color" />
        <!--宽度-->
        <attr name="bordWidth" format="dimension" />
        <attr name="stepTextSize" format="dimension" />
        <attr name="stepTextColor" format="color" />

    </declare-styleable>
   ```
 #重写onMeasure()，保证宽度不一致 取最小值 确保是个正方
   
   
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
   
  #重写onDraw()  来画圆弧
    
    
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
    
## 一定要把这个 invalidate();方法加上  要不然会没有效果的
###这样就差不多了 直接下载在项目更清楚 
  

  






