package cc.makepower.depandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class BigImgView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {
    private final GestureDetector gestureDetector;
    private final Scroller scroller;
    private final BitmapFactory.Options bitMapOptions;
    private final Rect mRect;
    private int imageViewWidth;
    private int imageViewHeight;

    private int viewWidth;
    private int viewHeight;
    private BitmapRegionDecoder bitmapRegionDecoder;
    private float scale; //缩放因子  对应控件加载的宽度   对应加载图片的高度
    private Bitmap mBitMap;

    public BigImgView(Context context) {
        this(context, null);
    }

    public BigImgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRect=new Rect();//内存复用只加载当前视图里面的内存 实现内存复用 对应当前控件需要加载图片的宽高
        //内存复用参数
        bitMapOptions = new BitmapFactory.Options();
        gestureDetector = new GestureDetector(context, this);
        scroller = new Scroller(context);
        setOnTouchListener(this);
    }

    public void setImage(InputStream is) {
        bitMapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, bitMapOptions);


        //得到图片的真实宽高
        imageViewWidth = bitMapOptions.outWidth;
        imageViewHeight = bitMapOptions.outHeight;


        bitMapOptions.inMutable = true;//开启内存复用

        //2*imageViewWidth*imageViewWidth  是这张图片全部加载需要的内存
        bitMapOptions.inPreferredConfig = Bitmap.Config.RGB_565;


        bitMapOptions.inJustDecodeBounds = false;
        try {
            //创建解码器
            bitmapRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth=getMeasuredWidth();
        viewHeight=getMeasuredHeight();

        mRect.left=0;
        mRect.top=0;
        mRect.right=imageViewWidth;
        scale=viewWidth/(float)imageViewWidth;
        mRect.right=imageViewWidth;
        mRect.bottom= (int) (imageViewHeight/scale);

    }

    @Override
    public void computeScroll() {
       if (scroller.isFinished()){
           return;
       }
       if (scroller.computeScrollOffset()){
           mRect.top=scroller.getCurrY();
           mRect.bottom=scroller.getCurrY()+(int) (viewHeight/scale);
           invalidate();
       }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmapRegionDecoder==null){
            return;
        }

        //内存复用，第二次的内存和上一次的bitmap内存使用情况一致
        bitMapOptions.inBitmap=mBitMap;
        mBitMap=  bitmapRegionDecoder.decodeRegion(mRect,bitMapOptions);

        Matrix matrix=new Matrix();
        matrix.setScale(scale,scale);
        canvas.drawBitmap(mBitMap,matrix,null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
       if (!scroller.isFinished()){
           scroller.forceFinished(true);
       }
       return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        mRect.offset(0, (int) distanceY);
        //避免滑出
        if (mRect.bottom>imageViewHeight){
            mRect.bottom=imageViewHeight;
            mRect.top= imageViewHeight-(int) (viewHeight/scale);
        }else if(mRect.top<0){
            mRect.top=0;
            mRect.bottom= (int) (viewHeight/scale);
        }
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        scroller.fling(0,mRect.top,0,(int)-velocityY,0,0,0,imageViewHeight-(int) (viewHeight/scale));
        return false;
    }


}
