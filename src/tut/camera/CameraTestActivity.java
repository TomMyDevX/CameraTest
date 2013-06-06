package tut.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.Toast;

import com.cam.libstools.CamInitSetting;

public class CameraTestActivity extends Activity {
	// Our variables 
	CameraPreview cv;
	DrawView dv;
	FrameLayout alParent;
	int isFocus=0;int isFlash=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Set the screen orientation to landscape, because 
         * the camera preview will be in landscape, and if we 
         * don't do this, then we will get a streached image.*/
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // making it full screen
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        	//	WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
    CamInitSetting caminit;
    public void Load(){
    	
    	
    	
    	
    	
    	// Try to get the camera 
        Camera c = getCameraInstance();
        
        caminit=  new CamInitSetting(c, this);
     //   caminit.setcamSetting();
        
        
        // If the camera was received, create the app
        if (c != null){
        	/* Create our layout in order to layer the 
        	 * draw view on top of the camera preview. 
        	 */
            alParent = new FrameLayout(this);
            alParent.setLayoutParams(new LayoutParams(
            		LayoutParams.FILL_PARENT,
            		LayoutParams.FILL_PARENT));
            
            // Create a new camera view and add it to the layout
            cv = new CameraPreview(this,c);
            alParent.addView(cv);
            
           // Create a new draw view and add it to the layout
           // dv = new DrawView(this);
           
            
            LayoutInflater inflater = (LayoutInflater)   this.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
            View view = inflater.inflate(R.layout.main, null);
            final ImageSwitcher focus_im_sw=(ImageSwitcher)view.findViewById(R.id.focus_im_sw);
            focus_im_sw.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					focus_im_sw.showNext();
                    ++isFocus;
                    if(isFocus%2==1){
                        caminit.playAutoFocus(true);
                    }else{
                        caminit.playAutoFocus(false);
                    }


				}
			});
            final ImageSwitcher flash_im_sw=(ImageSwitcher)view.findViewById(R.id.flash_im_sw);
            flash_im_sw.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    flash_im_sw.showNext();
                    ++isFlash;
                    if(isFlash%2==1){
                        caminit.playFlash(true);
                    }else{
                        caminit.playFlash(false);
                    }
                }
            });
            
            alParent.addView(view);
            // Set the layout as the apps content view 
            setContentView(alParent);
        }
        // If the camera was not received, close the app
        else {
        	Toast toast = Toast.makeText(getApplicationContext(), "Unable to find camera. Closing.", Toast.LENGTH_SHORT);
        	toast.show();
        	finish();
        }
    }
    
    /* This method is strait for the Android API */
    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        
        try {
            c = Camera.open();// attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        	e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }
    
    /* Override the onPause method so that we 
     * can release the camera when the app is closing.
     */
    @Override
    protected void onPause() {
        super.onPause();
        
        if (cv != null){
        	cv.onPause();
        	cv = null;
        }
    }
    
    /* We call Load in our Resume method, because 
     * the app will close if we call it in onCreate
     */
    @Override 
    protected void onResume(){
    	super.onResume();
    	
    	Load();
    }
}