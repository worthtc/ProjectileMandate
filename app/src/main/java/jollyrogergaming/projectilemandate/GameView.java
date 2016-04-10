package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Kelton on 4/9/2016.
 */
public class GameView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final String TAG = "GameActivity";
    private int mTouchX;
    private int mTouchY;
    private House[] houses = new House[4];

    private class House {
        private float houseX, houseY;
        private boolean active;

        public House(float x, float y) {
            this.houseX = x;
            this.houseY = y;
            this.active = true;
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawRect(this.houseX, this.houseY, this.houseX + 100, this.houseY + 30, paint);
        }
    };

    public GameView(Context context){
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        houses[0] = new House(80, 650);
        houses[1] = new House(350, 650);
        houses[2] = new House(740, 650);
        houses[3] = new House(1010, 650);
    }

    // Android calls this to redraw the view, after invalidate()
    @Override
    protected void onDraw(Canvas canvas)    {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw(); X = " + mX + " Y = " + mY);

        //Draw background, feel free to delete or change color. Here just in case we want a background color.
        canvas.drawARGB(255, 88, 42, 114);

        //Draw a circle at user touch
        mPaint.setColor(0xFF000000);
        canvas.drawRect(this.getWidth()/2 - 7, 610, this.getWidth() / 2 + 7, 660, mPaint);

        mPaint.setColor(0xFF00FFFF);
        canvas.drawCircle(this.getWidth() / 2, 665, 30, mPaint);

        mPaint.setColor(0xFF00FF00);
        canvas.drawRect(0, this.getHeight() * 9 / 10, this.getWidth(), this.getHeight(), mPaint);

        mPaint.setColor(0xFFFF0000);
        for(int i=0; i<houses.length; i++) {
            if(houses[i].active) {
                houses[i].draw(canvas, mPaint);
            }
        }

        mPaint.setColor(0xFFFFFFFF);
        canvas.drawCircle(mTouchX, mTouchY, 50, mPaint);

        //Log.d(TAG, "onSensorChanged() " + mLightLevel);
    }

    // Sets the mTouchX and mTouchY whenever a user touches the screen.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            action = "ACTION_DOWN";
            mTouchX = (int) current.x;
            mTouchY = (int) current.y;
            //Log.i(TAG, action + " at x =" + current.x + ", y =" + current.y);
            //Log.i(TAG, "x = " + mTouchX + ", y = " + mTouchY);
        }
        return true;
    }


}
