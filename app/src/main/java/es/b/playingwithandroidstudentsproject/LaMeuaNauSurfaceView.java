package es.b.playingwithandroidstudentsproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
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
    //Paleta
    Paint paleta = new Paint();

    //Nau
    //Paint nau = new Paint();

    //Valors Bola
    private int x, y;
    private static int radio = 40;
    private static int colorBola = Color.BLUE;

    //Valors Velocitat
    private int xDirection = 10;
    private int yDirection = 10;

    //Valors Paleta
    private float xPaleta, yPaleta;
    private float ample = 100;
    private float alt = 20;
    private float ultimaXPaleta, ultimaYPaleta;


    //Constructores
    public LaMeuaNauSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
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

    //Métodos
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void newDraw(Canvas canvas){
        //Background
        background.setColor(Color.WHITE);
        canvas.drawRect(0,0,getWidth(),getHeight(),background);
        //canvas.drawBitmap(R.drawable.ic_astronave,0.0,nau,0.0);

        //Bola
        ball.setColor(colorBola);
        canvas.drawCircle(x,y,radio,ball);

        //Paleta
        paleta.setColor(Color.GRAY);
        canvas.drawRect(xPaleta,yPaleta,xPaleta + ample, yPaleta + alt, paleta);

    }

    //Thread
    public class NauAnimationThread extends Thread{
        public boolean stop = false;
        private SurfaceHolder surfaceHolder;
        public NauAnimationThread(SurfaceHolder surfaceHolder){
            this.surfaceHolder = surfaceHolder;
        }

    }

}
