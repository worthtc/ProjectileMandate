package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kelton on 4/9/2016.
 */
public class GameView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final String TAG = "GameActivity";
    private int mTouchX;
    private int mTouchY;
    private ArrayList<Projectile> mProjectiles;
    private ArrayList<Projectile> mMissiles;
    private int mMaxProjectiles;
    private int mExplosionRadius;
    private int mProjectileSize;
    private int mMissileFrequency;
    private int mMissileCountdown;
    private int mMissileSize;
    private int mGroundHeight;
    private int mScreenWidth;
    private int mMissileSpeed;
    private int mProjectileSpeed;
    private int mScore;
    private static final int HOUSE_WIDTH = 100;
    private boolean mIsGameOver;


    // For the toast, can remove later
    private Context mContext;

    private House[] houses = new House[4];



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
        houses[0] = new House(0, 0);
        houses[1] = new House(0, 0);
        houses[2] = new House(0, 0);
        houses[3] = new House(0, 0);
        mMaxProjectiles = 2;
        mProjectiles = new ArrayList<Projectile>();
        mMissiles = new ArrayList<Projectile>();
        mExplosionRadius = 50;
        mProjectileSize = 20;
        mMissileFrequency = 300;
        mMissileCountdown = 20;
        mMissileSize = 15;
        mMissileSpeed = 6;
        mProjectileSpeed = 10;
        mScore = 0;


        // For the toast, can remove later
        mContext = context;

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
        mScreenWidth = this.getWidth();
        mGroundHeight = this.getHeight() * 9 / 10;

        // This should probably be done outside of the game loop somewhere
        houses[0].setHouseY(mGroundHeight);
        houses[1].setHouseY(mGroundHeight);
        houses[2].setHouseY(mGroundHeight);
        houses[3].setHouseY(mGroundHeight);
        houses[0].setHouseX(((mScreenWidth/5) * 1) - (HOUSE_WIDTH/2));
        houses[1].setHouseX(((mScreenWidth/5) * 2) - (HOUSE_WIDTH/2));
        houses[2].setHouseX(((mScreenWidth/5) * 3) - (HOUSE_WIDTH/2));
        houses[3].setHouseX(((mScreenWidth/5) * 4) - (HOUSE_WIDTH/2));
        //Log.d(TAG, "onDraw(); X = " + mX + " Y = " + mY);

        //Draw background, feel free to delete or change color. Here just in case we want a background color.
        canvas.drawARGB(255, 88, 42, 114);

        //Draw a circle at user touch
//        mPaint.setColor(0xFFFFFFFF);
//        canvas.drawCircle(mTouchX, mTouchY, 50, mPaint);

        ;


        mPaint.setColor(0xFF000000);
        canvas.drawRect(this.getWidth() / 2 - 7, mGroundHeight-50, this.getWidth() / 2 + 7, mGroundHeight, mPaint);

        mPaint.setColor(0xFF00FFFF);
        canvas.drawCircle(this.getWidth() / 2, mGroundHeight, 30, mPaint);
        mPaint.setColor(0xFF00FF00);
        canvas.drawRect(0, mGroundHeight, this.getWidth(), this.getHeight(), mPaint);

        // Draws houses
        mPaint.setColor(0xFFFF0000);
        for(int i=0; i<houses.length; i++) {
            if(houses[i].active) {
                houses[i].draw(canvas, mPaint);
            }
        }

        // Draw player projectiles and explosions
        for (Iterator<Projectile> iterator = mProjectiles.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            if(p.checkArrived()){
                mPaint.setColor(0xFFFFFFFF);
                canvas.drawCircle(p.getDestx(), p.getDesty(), mExplosionRadius, mPaint);
                if(p.getExplosionLifetime() <= 0) {
                    iterator.remove();
                }else{
                    p.setExplosionLifetime(p.getExplosionLifetime() - 1);
                    for (Iterator<Projectile> iteratorMissiles = mMissiles.iterator(); iteratorMissiles.hasNext();) {
                        Projectile missile = iteratorMissiles.next();
                        double xDif = p.getDestx() - missile.getX_pos();
                        double yDif = p.getDesty() - missile.getY_pos();
                        double distanceSquared = xDif * xDif + yDif * yDif;
                        boolean collision = distanceSquared < (mExplosionRadius) * (mExplosionRadius);
                        if(collision){
                            iteratorMissiles.remove();
                            mScore++;
                            //Remove later
                            CharSequence text = "Score: " + mScore;
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(mContext, text, duration);
                            toast.show();
                            //
                        }
                    }
                }

            }else {
                mPaint.setColor(0xFFFFFFFF);
                //canvas.drawRect(p.getX_pos(), p.getY_pos(), 64, 64, mPaint);
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mProjectileSize, mPaint);
                p.calcNewPos();
                //Log.i(TAG, "x = " + p.getX_pos() + ", y = " + p.getY_pos());
            }
        }



        // Creates new missiles every mMissileFrequency frames
        if (mMissileCountdown <= 0){
            if(mMissileFrequency > 40) {
                mMissileFrequency -= 2;
            }
            mMissileCountdown = mMissileFrequency;
            mMissiles.add(new Projectile(randomWithRange(0, mScreenWidth), 0, randomWithRange(0, mScreenWidth), mGroundHeight, mMissileSpeed));
        }
        // Counts down by 1 every frame
        mMissileCountdown -= 1;

        // Draws missiles using projectile objects
        for (Iterator<Projectile> iterator = mMissiles.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            if(p.checkArrived()){
                Log.i(TAG, "Missile ARRIVED");
                iterator.remove();
                // **Check Collision with houses here**
                for(int i = 0; i < houses.length;i++) {
                    /*if (p.getX_pos() == houses[i].houseX && p.getY_pos() == houses[i].houseY){
                        houses[i].active = false;
                    }*/
                    if((p.getX_pos() >= houses[i].houseX) && (p.getX_pos() <= (houses[i].houseX + HOUSE_WIDTH))){
                        houses[i].active = false;
                    }
                    else if(((p.getX_pos() + mProjectileSize) >= houses[i].houseX) && ((p.getX_pos() + mProjectileSize) <= (houses[i].houseX + HOUSE_WIDTH))){ //Center of projectile is not in the rectangle, but the right edge of the projectile is
                        houses[i].active = false;
                    }
                    else if(((p.getX_pos() - mProjectileSize) >= houses[i].houseX) && ((p.getX_pos() - mProjectileSize) <= (houses[i].houseX + HOUSE_WIDTH))){ //Center of projectile is not in the rectangle, but the left edge of the projectile is
                        houses[i].active = false;
                    }
                }
            }else {
                mPaint.setColor(0xFFFFA500);
                //canvas.drawRect(p.getX_pos(), p.getY_pos(), 64, 64, mPaint);
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mMissileSize, mPaint);
                p.calcNewPos();
                //Log.i(TAG, "x = " + p.getX_pos() + ", y = " + p.getY_pos());
            }
        }

        boolean canGameContinue = false;
        for(int i = 0; i < houses.length;i++) {
            if( houses[i].active ){
                canGameContinue = true;
                break;
            }
        }
        if( !canGameContinue ){
            mIsGameOver = true;
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
            if (mProjectiles.size() < mMaxProjectiles) {
                mProjectiles.add(new Projectile(this.getWidth() / 2, mGroundHeight, mTouchX, mTouchY, mProjectileSpeed));
            }
        }
        return true;
    }


    // House object
    private class House {
        private float houseX, houseY;
        private boolean active;

        public House(float x, float y) {
            this.houseX = x;
            this.houseY = y;
            this.active = true;
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawRect(this.houseX, this.houseY - 30, this.houseX + HOUSE_WIDTH, this.houseY, paint);
        }

        public void setHouseY(int y){
            houseY = y;
        }
        public void setHouseX(int x){
            houseX = x;
        }
    };

    /*
    public void moveMissile() {
        Random rand = new Random();
        int n = rand.nextInt(1000);


        x = n;
        y = n;

        totalDis = Math.sqrt((x * x)+(y*y));
        missilePos.x += totalDis;
        missilePos.y = y ;


    }
    public void createMissile(){
        missilePos = new PointF(x,y);
        n = rand.nextInt(1000);
        missilePos.x = n;
        missilePos.y = 0;


    }
     */
    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public boolean isGameOver(){
        return mIsGameOver;
    }

    public int getScore(){
        return mScore;
    }

}
