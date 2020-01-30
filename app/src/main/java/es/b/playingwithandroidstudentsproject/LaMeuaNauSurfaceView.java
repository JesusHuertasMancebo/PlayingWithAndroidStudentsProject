package es.b.playingwithandroidstudentsproject;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;

public class LaMeuaNauSurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    //Constructores
    public LaMeuaNauSurfaceView(Context context) {
        super(context);
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
}
