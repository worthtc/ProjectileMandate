package jollyrogergaming.projectilemandate;

/**
 * Created by Kelton on 4/10/2016.
 * simple class that creates and modifies the projectile on the screen
 */

public class Projectile {
    /**
     * varibles used for the projectile class
     */
    private int x_pos;
    private int y_pos;
    private int speed;
    private int destx;
    private int desty;
    private int explosionLifetime;

    public Projectile(int x, int y, int x2, int y2){
        speed = 5;
        x_pos = x;
        y_pos = y;
        destx = x2;
        desty = y2;
        explosionLifetime = 60;
    }

    /**
     * Constructor used for projectile
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @param s
     */
    public Projectile(int x, int y, int x2, int y2, int s){
        speed = s;
        x_pos = x;
        y_pos = y;
        destx = x2;
        desty = y2;
        explosionLifetime = 60;
    }

    /**
     * Calculates the new position for the projectile
     */
    public void calcNewPos(){
        int deltaX = destx - x_pos;
        int deltaY = desty - y_pos;

        double angle = Math.atan2(deltaY, deltaX);

        int dx = (int)Math.ceil((Math.cos(angle) * speed));
        int dy = (int)Math.ceil((Math.sin(angle) * speed));

        x_pos += dx;
        y_pos += dy;
    }

    /**
     * Check if the projectile has arrived
     * @return true if it has arrived
     */
    public boolean checkArrived(){
        if(Math.abs(x_pos-destx) < speed && Math.abs(y_pos-desty) < speed){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Getters for projectile
     * @return
     */
    public int getX_pos() {
        return x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }


    public int getDestx() {
        return destx;
    }



    public int getDesty() {
        return desty;
    }


    public int getExplosionLifetime() {
        return explosionLifetime;
    }

    /**
     * Setter for explosion lifetime
     * @param explosionLifetime - what to set the explosion lifetime
     */
    public void setExplosionLifetime(int explosionLifetime) {
        this.explosionLifetime = explosionLifetime;
    }
}
