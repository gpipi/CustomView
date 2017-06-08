package com.wwwxiaopi.view_day03;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {



    QQStepView mQQStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQQStepView= (QQStepView) findViewById(R.id.QQStepView);
        mQQStepView.setStepMax(4000);

        //属性动画 后面
        final ValueAnimator mValueAnimator = ObjectAnimator.ofFloat(0, 3500);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currenStep = (float) animation.getAnimatedValue();
                mQQStepView.setmCurrentStep((int) currenStep);
            }
        });
        mValueAnimator.start();

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValueAnimator.start();
            }
        });
    }

}
