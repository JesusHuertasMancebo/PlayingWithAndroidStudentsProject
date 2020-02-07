package es.b.playingwithandroidstudentsproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;

public class LaMeuaNauSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    //Thread
    private NauAnimationThread nauAnimationThread = null;

    //Background
    //Imatge per al fondo de pantalla
    //Paint background = new Paint();

    //ArrayList<String> balls = new ArrayList<String>();

    //Bola
    Paint ball = new Paint();

    //Imatge per a la Nau
    Bitmap nau = BitmapFactory.decodeResource(getResources(),R.drawable.ic_nave);

    //Laser Nau
    Paint laserNau;

    //Valors Bola
    private float x = 300;
    private float y = 300 ;
    private static int radio = 40;
    private static int colorBola = Color.CYAN;

    //Valors Velocitat de la Bola
    private int xDirection = 30;
    private int yDirection = 30;

    //Valors Nau
    private Drawable mIcon;
    private float xNau, yNau;
    private float ultimaXNau, ultimaYNau;

    //Valors làser
    private float xLaser,xLaser0, yLaser, yLaser0;
    private int yDirectionLaser = -10;

    private float ample = 15;
    private float alt = 300;
    Boolean dispararLaser = false;

    //Constructors
    public LaMeuaNauSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

       laserNau = new Paint();
    }

    public LaMeuaNauSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LaMeuaNauSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LaMeuaNauSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //Mètodes
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //Li donem una posició a la nau
        xNau = (int) (0.25 * getWidth());
        yNau = (int) ((float) 0.90 * getHeight());
        if (nauAnimationThread!=null) {
            return;
        }
        nauAnimationThread = new NauAnimationThread(getHolder());
        nauAnimationThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        nauAnimationThread.stop = true;
    }

    public void newDraw(Canvas canvas){
        //Pintar el Background
        //Al final hem fet un bitmap per al background per a introduir una imatge
        Bitmap background = BitmapFactory.decodeResource(getResources(),R.drawable.img_espacio);
        float scale = (float)background.getHeight()/(float)getHeight();
        int newWidth = Math.round(background.getWidth()/scale);
        int newHeight = Math.round(background.getHeight()/scale);
        Bitmap scaled = Bitmap.createScaledBitmap(background,newWidth,newHeight,true);

        //if (canvas == null) {return;}
        canvas.drawBitmap(scaled,0,0,null);

        //Pintar la Bola
        ball.setColor(colorBola);
        canvas.drawCircle(x,y,radio,ball);

        //Pintar la Nau
        canvas.drawBitmap(nau,(float) xNau -170, (float) yNau,null);

        //Pintar el làser de la nau
        if (dispararLaser) {
            //Laser
            laserNau.setColor(Color.RED);
            canvas.drawRect(xLaser,yLaser, xLaser + ample, yLaser - alt, laserNau);
        }else {
            //Amaguem el làser
            laserNau.setColor(Color.WHITE);
        }
    }

    //Thread
    public class NauAnimationThread extends Thread{
        public boolean stop = false;
        private SurfaceHolder surfaceHolder;
        public NauAnimationThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
        }
        public void run() {
            while (!stop) {

                //Moviment laser
                //yLaser -= yDirection;
                    if (dispararLaser){
                        //Soles necesitem la y perque volem que el laser es desplaçe cap amunt
                        //També representa la velocitat del làser
                        yLaser -= 150;

                        System.out.println("La Y " + y);
                        System.out.println("yDIRECTION " + yDirection);
                        System.out.println("YLASER " + yLaser);
                        System.out.println("xNAU " + xNau);
                        System.out.println("yNAU " + yNau);
                        //Fem que rebote dalt
                        /*if (yLaser < 0) {
                            //Fem que desaparega la bala
                            dispararLaser = false;
                        }*/
                    }
                // Moviment de la bola
                x += xDirection;
                y += yDirection;
                // Rebot a l´esquerre
                if (x < radio) {
                    x = radio;
                    xDirection = -xDirection;
                }
                // Rebot a la dreta
                if (x > getWidth() - radio) {
                    x = getWidth() - radio;
                    xDirection = -xDirection;
                }
                // Rebot dalt
                if (y < radio) {
                    y = radio;
                    yDirection = -yDirection;
                }
                // Rebot baix
                if (y > getHeight() / 3 - radio) {
                    yDirection = -yDirection;
                }

                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        newDraw(c);
                    }
                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
    public boolean onTouchEvent(MotionEvent motionEvent){
        final int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: {
                //Per a recordar
                ultimaXNau = motionEvent.getX();
                ultimaYNau = motionEvent.getY();

                //La nau anirà al punt on apretem en la pantalla per el seu eix X
                xNau = motionEvent.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //Calculem la distancia menejada
                final float dx = motionEvent.getX() - ultimaXNau;
                final float dy = motionEvent.getY() - ultimaYNau;
                //Soles menejem en horizontal
                xNau += dx;
                //Evitem que la nau vaja per fora de la pantalla
                if (xNau < 0) {
                    xNau = 0;
                }
                if (xNau > getWidth()){
                    xNau = 750;
                }
                //Recordem la posicio on hem tocat per al pròxim moviment
                ultimaXNau = motionEvent.getX();
                ultimaYNau = motionEvent.getY();
                //Re pintar
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //Quan disparem la bola apareixerà en la posició 0
                xLaser = xNau;
                yLaser = yNau;
                //yLaser = yDirection;

                System.out.println("yLaser ACTION_UP " + yLaser);

                if(yLaser > x - radio && yLaser < x + radio){

                        x = (int) 0;

                }
                //I fem que dispare
                dispararLaser = true;
                break;
            }
        }
        return true;
    }
}
