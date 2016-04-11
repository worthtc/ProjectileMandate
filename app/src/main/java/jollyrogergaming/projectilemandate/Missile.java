package jollyrogergaming.projectilemandate;

import android.graphics.RectF;

/**
 * Created by Josh on 4/10/2016.
 */
public class Missile {

        private int x_pos;
        private int y_pos;
        private int speed;
        private int destx;
        private int desty;
        private RectF rectF;


        public Missile(int x, int y, int w, int h, int s){
            speed = s;
            rectF = new RectF(x,y,w,h);
        }
        public int getDesty() {
            return desty;
        }

        public void setDesty(int desty) {
            this.desty = desty;
        }

        public int getDestx() {
            return destx;
        }

        public void setDestx(int destx) {
            this.destx = destx;
        }



        public RectF getRectF() {
            return rectF;
        }

        public int getX_pos() {
            return x_pos;
        }

        public int getY_pos() {
            return y_pos;
        }

        public int getSpeed() {
            return speed;
        }


    }


