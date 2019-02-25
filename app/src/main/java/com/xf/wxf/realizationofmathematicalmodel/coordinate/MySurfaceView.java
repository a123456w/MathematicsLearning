package com.xf.wxf.realizationofmathematicalmodel.coordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 *  利用SurfaceView写出的数学方程随参数变化的运动图像
 *
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mHolder;
    Random mRandom = new Random();//随机数产生器
    //随机产生颜色值的hander
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (!mIsDrawing){
                return;
            }
            mPaint.setColor(Color.rgb(mRandom.nextInt(255),mRandom.nextInt(255),mRandom.nextInt(255)));
            mHandler.sendEmptyMessageDelayed(0,2000);
        }
    };
    private Canvas mCanvas;//绘图的画布
    private boolean mIsDrawing;//控制绘画线程的标志位
    private Paint mPaint,mPaintText,mPaintXY;
    private Axis mAxisX,mAxisY;//X轴和Y轴
    private double a=0;//a 为方程式的参数
    private float aFloat=0.1f;// 每次增加的数量级 也可表示图像运动的快慢
    private List<Spot> mSpotList=new ArrayList<>();//每次绘制的点的坐标集
    public static final int TIME_IN_FRAME = 17;//每 TIME_IN_FRAME 毫秒刷新一次屏幕
    public MySurfaceView(Context context) {
        super(context);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void initView() {
        mHolder = getHolder();//获取SurfaceHolder对象
        mHolder.addCallback(this);//注册SurfaceHolder的回调方法

        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        initPaint();
    }
    public void setAFloat(float aFloat) {
        mSpotList.clear();
        this.aFloat = aFloat;
    }
    public float getFloat() {
        return aFloat;
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        new Thread(this).start();
        mHandler.sendEmptyMessage(0);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing=false;
    }
    @Override
    public void run() {
        while (mIsDrawing) {

            /**取得更新之前的时间**/
            long startTime = System.currentTimeMillis();

            /**在这里加上线程安全锁**/
            synchronized (mHolder) {
                /**拿到当前画布 然后锁定**/
                mCanvas =mHolder.lockCanvas();
                draws(mCanvas);
                /**绘制结束后解锁显示在屏幕上**/
                mHolder.unlockCanvasAndPost(mCanvas);
            }

            /**取得更新结束的时间**/
            long endTime = System.currentTimeMillis();

            /**计算出一次更新的毫秒数**/
            int diffTime  = (int)(endTime - startTime);
            /**确保每次更新时间为30帧**/
            while(diffTime <=TIME_IN_FRAME) {
                diffTime = (int)(System.currentTimeMillis() - startTime);
                /**线程等待**/
                Thread.yield();
            }
        }
    }
    //绘图操作
    public void draws(Canvas mCanvas) {
        // draw sth绘制过程
        if (mCanvas==null)return;
        mCanvas.drawColor(Color.WHITE);
        drawCoordinate(mCanvas);
        setPoint();
    }

    private void setPoint() {
        a=a+aFloat;
        mSpotList.clear();
        inEquation();
    }

    private void drawCoordinate(final Canvas canvas) {
        canvas.drawText("a="+a,100,100,mPaintText);
        canvas.drawText("a的速度="+aFloat,100,200,mPaintText);
        drawX(canvas);
        drawY(canvas);
        drawSpot(canvas);
    }
    /**
     * 添加坐标点并连线
     * @param canvas
     */
    private void drawSpot(Canvas canvas) {
        for (int i=0;i<mSpotList.size()-1;i++) {
            Spot startSpot = mSpotList.get(i);
            Spot endSpot = mSpotList.get(i+1);
            canvas.drawLine(startSpot.getX()+mAxisY.getStartX(),-startSpot.getY()+ mAxisX.getStartY(),endSpot.getX()+ mAxisY.getStartX(),-endSpot.getY()+ mAxisX.getStartY(),mPaint);
//            canvas.drawPoint(startSpot.getX()+mAxisY.getStartX(),-startSpot.getY()+ mAxisX.getStartY(),mPaint);
        }
    }
    private void setXY(Spot spot){
        mSpotList.add(spot);
    }
    private void inEquation(){
        for (float i=-2;i<2;i=i+0.05f){
            float x=i;
            double y=Math.pow(x,2/3)+0.9*Math.pow((3.3-Math.pow(x,2)),0.5d)*Math.sin(a*Math.PI*x);
            setXY(new Spot(x*180,(float) y*200));
        }
    }
    /**
     *  画出X轴
     */
    private void drawX(Canvas canvas) {
        mAxisX=new Axis(50,getWidth()/2,getWidth()-50,getWidth()/2);
        canvas.drawLine(mAxisX.getStartX(),mAxisX.getStartY(),mAxisX.getEndX(),mAxisX.getEndY(),mPaintXY);
        canvas.drawLine(getWidth()-50,mAxisX.getStartY(),mAxisX.getEndX()-30,mAxisX.getEndY()-15,mPaintXY);
        canvas.drawLine(getWidth()-50,mAxisX.getStartY(),mAxisX.getEndX()-30,mAxisX.getEndY()+15,mPaintXY);
    }
    /**
     *  画出Y轴
     */
    private void drawY(Canvas canvas) {
        mAxisY=new Axis(getWidth()/2,50,getWidth()/2,getWidth()-50);
        canvas.drawLine(mAxisY.getStartX(),mAxisY.getStartY(),mAxisY.getEndX(),mAxisY.getEndY(),mPaintXY);
        canvas.drawLine(mAxisY.getStartX(),mAxisY.getStartY(),mAxisY.getEndX()-15,100,mPaintXY);
        canvas.drawLine(mAxisY.getStartX(),mAxisY.getStartY(),mAxisY.getEndX()+15,100,mPaintXY);
    }
    private void initPaint() {
        mPaint=new Paint();
        mPaintText=new Paint();
        mPaintXY=new Paint();

        mPaintText.setColor(Color.RED);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(20);

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setStrokeWidth((float) 2.5);

        mPaintXY.setAntiAlias(true);
        mPaintXY.setColor(Color.BLACK);
        mPaintXY.setTypeface(Typeface.DEFAULT);
//        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
