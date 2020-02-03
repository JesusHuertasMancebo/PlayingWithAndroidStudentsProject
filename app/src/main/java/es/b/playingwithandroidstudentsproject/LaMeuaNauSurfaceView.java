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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

public class LaMeuaNauSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    //Thread
    private NauAnimationThread nauAnimationThread = null;

    //Background
    Paint background = new Paint();

    //Bola
    Paint ball = new Paint();

    //Imatge per a la Nau
    //Paint nau = new Paint();
    Bitmap nau = BitmapFactory.decodeResource(getResources(),R.drawable.ic_astronave);

    //Laser Nau
    Paint laserNau;

    //Valors Bola
    private int x = 300;
    private int y = 300 ;
    private static int radio = 40;
    private static int colorBola = Color.BLUE;

    //Valors Velocitat
    private int xDirection = 10;
    private int yDirection = 10;

    //Valors Nau
    private Drawable mIcon;
    private float xNau, yNau;
    private float ultimaXNau, ultimaYNau;

    //Valors làser
    private float xLaser, yLaser;
    private float ample = 20;
    private float alt = 80;
    private float velocitatLaser = -16;
    private float ultimXLaser, ultimYLaser;




    //Constructors
    public LaMeuaNauSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

       laserNau = new Paint();
       laserNau.setColor(Color.RED);
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
        background.setColor(Color.WHITE);
        canvas.drawRect(0,0,getWidth(),getHeight(),background);
        //canvas.drawBitmap(R.drawable.ic_astronave,0.0,nau,0.0);

        //Pintar la Bola
        ball.setColor(colorBola);
        canvas.drawCircle(x,y,radio,ball);

        //Pintar la Nau
        canvas.drawBitmap(nau,(float) xNau, (float) yNau,null);

        //Pintar el làser de la nau
        canvas.drawRect(xNau,yNau, xNau + ample, yNau + alt, laserNau);

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
                yLaser -= yDirection;

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

    private long timeDown = 0;
    private long timeUp;
    public boolean onTouchEvent(MotionEvent motionEvent){
        final int action = motionEvent.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN: {
                //Per a recordar
                ultimaXNau = motionEvent.getX();
                ultimaYNau = motionEvent.getY();

                yLaser = (int) motionEvent.getY();

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //Calculem la distancia menejada
                final float dx = motionEvent.getX() - ultimaXNau;
                final float dy = motionEvent.getY() - ultimaYNau;

                //Soles menejem en horizontal
                xNau += dx;

                //Recordem la posicio on hem tocat per al pròxim moviment
                ultimaXNau = motionEvent.getX();
                ultimaYNau = motionEvent.getY();

                //Re pintar
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                //final float dxLaser = motionEvent.getX() - ultimXLaser;
                //final float dyLaser = motionEvent.getX() - ultimYLaser;

                //El làser anirà en vertical
                //yLaser += dyLaser;



                timeUp = System.currentTimeMillis();

                if (timeUp - timeDown > 3000 && timeDown > 0) {
                    System.out.println("Simulation long click");
                    //Move the circle to the left top position
                    //x = 100;
                    yLaser = 100;
                } else {
                    yLaser = (int) motionEvent.getY();
                }


                //invalidate();
                break;
            }
        }
        return true;
    }

}
