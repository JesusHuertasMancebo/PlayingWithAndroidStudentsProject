package es.b.playingwithandroidstudentsproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class LaMeuaNauActivity extends MainActivity {


    public static LaMeuaNauSurfaceView laMeuaNauSurfaceView;
    public static LaMeuaNauSurfaceView.NauAnimationThread nauAnimationThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_la_meua_nau);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        laMeuaNauSurfaceView = new LaMeuaNauSurfaceView(this);
        setContentView(laMeuaNauSurfaceView);

    }

    /*protected void onPause(){
        super.onPause();
        Log.d("SurfaceView", "onPause");

        //Evitem l´error al eixir de l´aplicació
        if(laMeuaNauSurfaceView != null){
            nauAnimationThread.stop = true;
        }
    }*/

}
