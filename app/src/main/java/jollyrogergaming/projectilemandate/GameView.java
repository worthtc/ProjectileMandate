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
    private int mMissileMinFrequency;
    private int mMissileSpeedCountdown;
    private int mMissileSize;
    private int mGroundHeight;
    private int mScreenWidth;
    private int mMissileSpeed;
    private int mMissileMaxSpeed;
    private int mProjectileSpeed;
    private int mScore;
    private int HOUSE_WIDTH;
    private boolean mIsGameHard;
    private boolean mColorScheme;
    private int mMisileSpeedUpTime;

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
        this(context, null, false, false);
        for(int i = 0; i < 100 ; i++) {
            createMissile();
            moveMissile();
        }
    }

    public GameView(Context context, AttributeSet attrs, boolean hard , boolean color){
        super(context, attrs);
        mIsGameHard = hard;
        mColorScheme = color;

        HOUSE_WIDTH = this.getWidth()/5;
        houses[0] = new House(0, 0);
        houses[1] = new House(0, 0);
        houses[2] = new House(0, 0);
        houses[3] = new House(0, 0);
        mMaxProjectiles = 2;
        mProjectiles = new ArrayList<Projectile>();
        mMissiles = new ArrayList<Projectile>();
        mExplosionRadius = 50;
        mProjectileSize = 20;
        mMissileFrequency = 200;
        mMissileMinFrequency = 50;
        mMissileCountdown = 20;
        mMissileSize = 15;
        mMissileSpeed = 5;
        mMissileMaxSpeed = 8;
        mMissileSpeedCountdown = 80;
        mProjectileSpeed = 10;
        mScore = 0;
        mMisileSpeedUpTime = 1000;

        // Hard mode values
        if(mIsGameHard){
            Log.i(TAG, "Hard mode");
            mMissileSpeed = 6;
            mMissileMaxSpeed = 16;
            mMissileFrequency = 100;
            mMissileMinFrequency = 10;
            mMisileSpeedUpTime = 250;
        }

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
        HOUSE_WIDTH = (int)(mScreenWidth * 0.15);

        // This should probably be done outside of the game loop somewhere
        houses[0].setHouseY(mGroundHeight);
        houses[1].setHouseY(mGroundHeight);
        houses[2].setHouseY(mGroundHeight);
        houses[3].setHouseY(mGroundHeight);
        houses[0].setHouseX(mScreenWidth / 20);
        houses[1].setHouseX((mScreenWidth / 20) * 2 + HOUSE_WIDTH);
        houses[2].setHouseX((mScreenWidth / 20) * 6 + HOUSE_WIDTH * 2);
        houses[3].setHouseX((mScreenWidth / 20) * 7 + HOUSE_WIDTH * 3);

        //Log.d(TAG, "onDraw(); X = " + mX + " Y = " + mY);

        //Draw background, feel free to delete or change color. Here just in case we want a background color.
        if( mColorScheme ) {
            canvas.drawARGB(255, 88, 42, 114); //Dark Color Scheme
        }
        else{
            canvas.drawARGB(0xFF, 0x99, 0xCC, 0xFF);//Light Color Scheme
        }
        //Draw a circle at user touch
//        mPaint.setColor(0xFFFFFFFF);
//        canvas.drawCircle(mTouchX, mTouchY, 50, mPaint);

        ;

        //Set color of Turret Gun
        if( mColorScheme ) {
            mPaint.setColor(0xFF000000);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF000000);//Light Color Scheme
        }
        canvas.drawRect(this.getWidth() / 2 - 7, mGroundHeight - 50, this.getWidth() / 2 + 7, mGroundHeight, mPaint);
        //Set color of Turret Base Exterior
        if( mColorScheme ) {
            mPaint.setColor(0xFF00FFFF);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF00FFFF);//Light Color Scheme
        }
        canvas.drawCircle(this.getWidth() / 2, mGroundHeight, 30, mPaint);
        //Set color of Turret Base Interior
        if( mColorScheme ) {
            mPaint.setColor(0xFF0000FF);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF0000FF);//Light Color Scheme
        }
        canvas.drawCircle(this.getWidth() / 2, mGroundHeight, 15, mPaint);
        //Set color of Ground Exterior
        if( mColorScheme ) {
            mPaint.setColor(0xFF00FF00);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF99FFCC);;//Light Color Scheme
        }
        canvas.drawRect(0, mGroundHeight, this.getWidth(), this.getHeight(), mPaint);
        //Set color of Ground Interior
        if( mColorScheme ) {
            mPaint.setColor(0xFF000000);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF669999);//Light Color Scheme
        }
        canvas.drawRect(20, mGroundHeight+20, this.getWidth()-mScreenWidth/20, this.getHeight()-20, mPaint);

        // Draws houses

        //Set color of House Exterior
        if( mColorScheme ) {
            mPaint.setColor(0xFFFF0000);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFFFF0000);//Light Color Scheme 000066
        }
        for(int i=0; i<houses.length; i++) {
            if(houses[i].active) {
                houses[i].draw(canvas, mPaint);
            }
        }

        // Draw player projectiles and explosions
        for (Iterator<Projectile> iterator = mProjectiles.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            if(p.checkArrived()){
                if(p.getExplosionLifetime() <= 0) {
                    iterator.remove();
                }else{
                    int explosionSize = mExplosionRadius * (60 - p.getExplosionLifetime()) / 60;
                    //Set Color of Explosion Exterior
                    if( mColorScheme ) {
                        mPaint.setColor(0xFFFF0084);//Dark Color Scheme
                    }
                    else{
                        mPaint.setColor(0xFF669999);//Light Color Scheme
                    }
                    canvas.drawCircle(p.getDestx(), p.getDesty(), explosionSize, mPaint);
                    //Set Color of Explosion Middle
                    if( mColorScheme ) {
                        mPaint.setColor(0xFF0000FF);//Dark Color Scheme
                    }
                    else{
                        mPaint.setColor(0xFFFFFFFF);//Light Color Scheme
                    }
                    canvas.drawCircle(p.getDestx(), p.getDesty(), explosionSize * 2 / 3, mPaint);
                    //Set Color of Explosion Interior
                    if( mColorScheme ) {
                        mPaint.setColor(0xFF08E300);//Dark Color Scheme
                    }
                    else{
                        mPaint.setColor(0xFF000000);//Light Color Scheme
                    }
                    canvas.drawCircle(p.getDestx(), p.getDesty(), explosionSize *1/3, mPaint);
                    p.setExplosionLifetime(p.getExplosionLifetime() - 1);
                    for (Iterator<Projectile> iteratorMissiles = mMissiles.iterator(); iteratorMissiles.hasNext();) {
                        Projectile missile = iteratorMissiles.next();
                        double xDif = p.getDestx() - missile.getX_pos();
                        double yDif = p.getDesty() - missile.getY_pos();
                        double distanceSquared = xDif * xDif + yDif * yDif;
                        boolean collision = distanceSquared < ((explosionSize) * (explosionSize) + mExplosionRadius/12);
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
                //Set Color of Projectile Exterior
                if( mColorScheme ) {
                    mPaint.setColor(0xFF00FF00); //Dark Color Scheme
                }
                else{
                    mPaint.setColor(0xFF00FF00);//Light Color Scheme
                }
                //canvas.drawRect(p.getX_pos(), p.getY_pos(), 64, 64, mPaint);
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mProjectileSize, mPaint);
                //Set Color of Player Projectile Interior
                if( mColorScheme ) {
                    mPaint.setColor(0xFFFFFFFF); //Dark Color Scheme
                }
                else{
                    mPaint.setColor(0xFF000000);//Light Color Scheme
                }
                //canvas.drawRect(p.getX_pos(), p.getY_pos(), 64, 64, mPaint);
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mProjectileSize/2, mPaint);
                p.calcNewPos();
                //Log.i(TAG, "x = " + p.getX_pos() + ", y = " + p.getY_pos());
            }
        }

        //Speeds up missiles
        if (mMissileSpeedCountdown <= 0){
            if(mMissileSpeed < mMissileMaxSpeed) {
                mMissileSpeed += 1;
            }
            mMissileSpeedCountdown = mMisileSpeedUpTime;
        }

        // Creates new missiles every mMissileFrequency frames
        if (mMissileCountdown <= 0){
            if(mMissileFrequency > mMissileMinFrequency) {
                mMissileFrequency -= 2;
            }
            mMissileCountdown = mMissileFrequency;
            mMissiles.add(new Projectile(randomWithRange(0, mScreenWidth), 0, randomWithRange(0, mScreenWidth), mGroundHeight, randomWithRange(mMissileSpeed/2, mMissileSpeed)));
        }

        // Counts down by 1 every frame
        mMissileCountdown -= 1;
        mMissileSpeedCountdown -=1;

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
                //Set Color of Enemy Missile Exterior
                if( mColorScheme ) {
                    mPaint.setColor(0xFFF7F702); //Dark Color Scheme
                }
                else{
                    mPaint.setColor(0xFF996633);//Light Color Scheme
                }
                //canvas.drawRect(p.getX_pos(), p.getY_pos(), 64, 64, mPaint);
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mMissileSize, mPaint);
                Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                //Set Color Of Enemy Missile Interior
                if( mColorScheme ) {
                    whitePaint.setColor(0xFFFF0000); //Dark Color Scheme
                }
                else{
                    whitePaint.setColor(0xFFFFFFFF); //Light Color Scheme
                }
                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mMissileSize-mMissileSize/3, whitePaint);
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
            Paint whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            //Set Color Of House Interior
            if( mColorScheme ) {
                whitePaint.setColor(0xFFFFFFFF); //Dark Color Scheme
            }
            else{
                whitePaint.setColor(0xFF000000);//Light Color Scheme
            }
            canvas.drawRect(this.houseX+10, this.houseY - 20, this.houseX + HOUSE_WIDTH - 10, this.houseY, whitePaint);
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
