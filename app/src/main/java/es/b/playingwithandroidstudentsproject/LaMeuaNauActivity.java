package es.b.playingwithandroidstudentsproject;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class LaMeuaNauActivity extends MainMenu {

    public static LaMeuaNauSurfaceView laMeuaNauSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_la_meua_nau);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        laMeuaNauSurfaceView = new LaMeuaNauSurfaceView(this);
        setContentView(laMeuaNauSurfaceView);

    }

}