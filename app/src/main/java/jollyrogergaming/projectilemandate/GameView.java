package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kelton on 4/9/2016.
 */
public class GameView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final String TAG = "GameActivity";
    private int mTouchX;
    private int mTouchY;
    int x ;
    int y ;
    private int xpos1,ypos1,xpos2,ypos2;

    GameActivity obj2;
    private ArrayList<Missile> missile = new ArrayList<>();
    private ArrayList<Missile> missilemove = new ArrayList<>();
    private ArrayList<Missile> missilen = new ArrayList<>();


    public GameView(Context context){
        this(context, null);
        for(int i = 0; i < 100 ; i++) {
            createMissile();
            moveMissile();
        }
    }

    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        for(int i = 0; i < 100 ; i++) {
            createMissile();
            moveMissile();
        }
    }
    public void createMissile(){
        Random rand = new Random();
        int n = rand.nextInt(1000);
        Missile update_missile = new Missile(n,0,32,32,10);
        missile.add(update_missile);

        Missile new_missile = new Missile(n,1010,32,32,10);
        missilemove.add(new_missile);
    }
    // Android calls this to redraw the view, after invalidate()
    @Override
    protected void onDraw(Canvas canvas)    {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw(); X = " + mX + " Y = " + mY);

        //Draw background, feel free to delete or change color. Here just in case we want a background color.
        canvas.drawARGB(255, 88, 42, 114);

        //Draw a circle at user touch
        mPaint.setColor(0xFF00FF00);
        canvas.drawCircle(mTouchX, mTouchY, 50, mPaint);
        for(Missile m : missile) {
            mPaint.setColor(0xFF00FF00);
            canvas.drawRect(x,y,32,32,mPaint);

        }
        for (Missile m2 : missilemove){
            mPaint.setColor(0xFF00FF00);
            canvas.drawRect(x,y,32,32,mPaint);
        }
        for(Missile m : missilen) {
            mPaint.setColor(0xFF00FF00);
            canvas.drawRect(m.getRectF(),mPaint);

        }

        //Log.d(TAG, "onSensorChanged() " + mLightLevel);
    }
    public void moveMissile() {
        for(Missile m : missile) {
            xpos1 += m.getX_pos();
            ypos1 += m.getY_pos();
        }
        for (Missile m2 : missilemove){
            xpos2 += m2.getX_pos();
            ypos2 += m2.getY_pos();
        }
        x = xpos2 - xpos1;
        y = ypos2 ;
        int totalDis = ((x*x)+ (y*y))^(1/2);
        Missile newM = new Missile(x,y,32,32,10);
        missilen.add(newM);

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
