package de.digitalemil.eagleandroid;

import de.digitalemil.eagleandroid.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView hunt;
    EagleView view;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
       
        hunt= (ImageView) findViewById(R.id.hunt);
 //	      hunt.setScaleX(2);
        hunt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	view = new EagleView(getApplicationContext());
                setContentView(view);
            }
        });
        

        ImageView rl=  (ImageView) findViewById(R.id.imageView1);
   //     System.err.println("---- RIBBON LEFT------"+rl.getMaxHeight()+" "+rl.getMaxWidth());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
   
}
