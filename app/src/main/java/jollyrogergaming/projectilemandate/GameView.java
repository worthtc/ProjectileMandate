package jollyrogergaming.projectilemandate;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Kelton on 4/9/2016.
 */
public class GameView extends View {
    /**
     * variables used for Game view
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final String TAG = "GameActivity";
    private static final String SOUNDS_FOLDER = "sounds";
    private static final int MAX_SOUNDS = 10;

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
    private float mFdegree;
    private int mFireAngle;
    private Sound mSound;
    private SoundPool mSoundPool;
    private AssetManager mAssets;

    private boolean mIsGameOver;


    // For the toast, can remove later
    private Context mContext;

    private House[] houses = new House[4];



    int x ;
    int y ;
    private int xpos1,ypos1,xpos2,ypos2;

    GameActivity obj2;

    /**
     * Defualt constructor
     * @param context
     */
    public GameView(Context context){
        this(context, null, false, false);

    }

    /**
     * Default constructor for GameView
     * @param context
     * @param attrs
     * @param hard
     * @param color
     */
    public GameView(Context context, AttributeSet attrs, boolean hard , boolean color){
        super(context, attrs);
        mIsGameHard = hard;
        mColorScheme = color;

        HOUSE_WIDTH = this.getWidth()/5;
        houses[0] = new House(0, 0);
        houses[1] = new House(0, 0);
        houses[2] = new House(0, 0);
        houses[3] = new House(0, 0);
        mMaxProjectiles = 5;
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
        mFdegree = 0;
        mFireAngle = 15;

        // Hard mode values
        if(mIsGameHard){
            //Log.i(TAG, "Hard mode");
            mMissileSpeed = 6;
            mMissileMaxSpeed = 16;
            mMissileFrequency = 100;
            mMissileMinFrequency = 10;
            mMisileSpeedUpTime = 250;
        }

        // For the toast, can remove later
        mContext = context;



        mAssets = context.getAssets();
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0); //Depreciated but needed for API 16
        String[] soundNames;
        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
        } catch (IOException e){
            Log.e(TAG, "Could not list assets", e);
            return;
        }
        for( String filename : soundNames ){
            try{
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Log.d(TAG, "GameView: " + filename);
                mSound = new Sound(assetPath);
                Log.d(TAG, "GameView: " + mSound);
                load(mSound);
            } catch (IOException e){
                Log.e(TAG, "Could not load sound", e);
            }
        }


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


        //Draw background, feel free to delete or change color. Here just in case we want a background color.
        if( mColorScheme ) {
            canvas.drawARGB(255, 88, 42, 114); //Dark Color Scheme
        }
        else{
            canvas.drawARGB(0xFF, 0x99, 0xCC, 0xFF);//Light Color Scheme
        }


        mPaint.setColor(0xFF00FFFF);

        //Set color of Turret Gun
        if( mColorScheme ) {
            mPaint.setColor(0xFF000000);//Dark Color Scheme
        }
        else{
            mPaint.setColor(0xFF000000);//Light Color Scheme
        }
        int deltaX= (this.getWidth() / 2) - mTouchX ;
        int deltaY= mGroundHeight - mTouchY  ;
        double angle = Math.atan2(deltaY, deltaX);
        float a = (float) angle;
        double mDegree = (float)(a * 180)/3.14;

        if(mDegree > 180 - mFireAngle || mDegree < -90) {
            mFdegree = (float) 180 - mFireAngle;
        }else if(mDegree < 0 + mFireAngle){
            mFdegree = (float) 0 + mFireAngle;
        }else{
            mFdegree = (float) mDegree;
        }
        //Log.d("Angle debug tag"," the angle is " + Float.toString(fdegree));
        canvas.save();
        canvas.rotate(mFdegree - 90, this.getWidth() / 2, mGroundHeight);
        canvas.drawRect((this.getWidth() / 2 - 7), mGroundHeight - 50, (this.getWidth() / 2 + 7), mGroundHeight, mPaint);
        canvas.restore();
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

                    /*MediaPlayer mp = new MediaPlayer();

                    try {
                        mp.setDataSource("app/res/sound/explosion.mp3");
                        mp.prepare();
                        mp.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    iterator.remove();
                }else{
                    if(p.getExplosionLifetime() >= 60 ){
                        play(mSound);
                    }
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

                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mProjectileSize, mPaint);
                //Set Color of Player Projectile Interior
                if( mColorScheme ) {
                    mPaint.setColor(0xFFFFFFFF); //Dark Color Scheme
                }
                else{
                    mPaint.setColor(0xFF000000);//Light Color Scheme
                }

                canvas.drawCircle(p.getX_pos(), p.getY_pos(), mProjectileSize/2, mPaint);
                p.calcNewPos();

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


                play(mSound);
                //Log.i(TAG, "Missile ARRIVED");

                iterator.remove();
                // **Check Collision with houses here**
                for(int i = 0; i < houses.length;i++) {

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
            int deltaX= (this.getWidth() / 2) - mTouchX ;
            int deltaY= mGroundHeight - mTouchY  ;
            double angle = Math.atan2(deltaY, deltaX);
            float a = (float) angle;
            double mDegree = (float)(a * 180)/3.14;
            //Creates the projectiles
            Log.d(TAG, "Angle " + mDegree);
            if (mProjectiles.size() < mMaxProjectiles) {
                int xDest = 0;
                int yDest = 0;
                if(mDegree > 180 - mFireAngle || mDegree < -90) {
                    //xDest = mTouchX;
                    //yDest = (int)(Math.tan(mDegree)*(mTouchX));

                }else if(mDegree < 0 + mFireAngle){
                    //xDest = mTouchX;
                    //yDest = (int)(Math.tan(mDegree)*(mTouchX));

                }else{
                    xDest = mTouchX;
                    yDest = mTouchY;
                    //This should be bellow the else statement if the above code works
                    mProjectiles.add(new Projectile(this.getWidth() / 2, mGroundHeight, xDest, yDest, mProjectileSpeed));
                }
                Log.d(TAG, "X Dest " + xDest);
                Log.d(TAG, "Y Dest " + yDest);
            }
        }
        return true;
    }



    /**
     * House object
     */
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

    /**
     *
     * @param min
     * @param max
     * @return
     */
    int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    /**
     * Checks to see if game is over
     * @return
     */
    public boolean isGameOver(){
        return mIsGameOver;
    }

    /**
     * gets the score
     * @return
     */
    public int getScore(){
        return mScore;
    }

    private void load( Sound sound ) throws IOException{
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        Log.d(TAG, "GameView: " + sound.getAssetPath());
        Log.d(TAG, "GameView: " + afd);
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public void play(Sound sound){
        Integer soundId = sound.getSoundId();
        if( soundId == null ){
            return;
        }
        mSoundPool.play( soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release(){
        mSoundPool.release();
    }


}
