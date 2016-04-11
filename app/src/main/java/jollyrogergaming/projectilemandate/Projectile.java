package jollyrogergaming.projectilemandate;

/**
 * Created by Kelton on 4/10/2016.
 */
public class Projectile {
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

    public void calcNewPos(){
        int deltaX = destx - x_pos;
        int deltaY = desty - y_pos;

        double angle = Math.atan2(deltaY, deltaX);

        int dx = (int)Math.ceil((Math.cos(angle) * speed));
        int dy = (int)Math.ceil((Math.sin(angle) * speed));

        x_pos += dx;
        y_pos += dy;
    }

    public boolean checkArrived(){
        if(Math.abs(x_pos-destx) < speed && Math.abs(y_pos-desty) < speed){
            return true;
        }else{
            return false;
        }
    }

    public int getX_pos() {
        return x_pos;
    }

    public void setX_pos(int x_pos) {
        this.x_pos = x_pos;
    }

    public int getY_pos() {
        return y_pos;
    }

    public void setY_pos(int y_pos) {
        this.y_pos = y_pos;
    }

    public int getDestx() {
        return destx;
    }

    public void setDestx(int destx) {
        this.destx = destx;
    }

    public int getDesty() {
        return desty;
    }

    public void setDesty(int desty) {
        this.desty = desty;
    }

    public int getExplosionLifetime() {
        return explosionLifetime;
    }

    public void setExplosionLifetime(int explosionLifetime) {
        this.explosionLifetime = explosionLifetime;
    }
}
