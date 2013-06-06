package tut.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView{

    private Paint textPaint = new Paint();
	private int X;
	private int Y;
    
	public DrawView(Context context) {
		super(context);
		// Create out paint to use for drawing
        textPaint.setARGB(255, 200, 0, 0);
        textPaint.setTextSize(60);
        /* This call s necessary, or else the 
         * draw method will not be called. 
         */
        setWillNotDraw(false);
	}
	
	@Override
    protected void onDraw(Canvas canvas){
		// A Simple Text Render to test the display
        canvas.drawText("Hello World!", 50, 50, textPaint);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN: {

                X = (int) ev.getX();
                Y = (int) ev.getY();
                invalidate();

                break;
            }

            case MotionEvent.ACTION_MOVE: {

                    X = (int) ev.getX();
                    Y = (int) ev.getY();
                    invalidate();
                    break;

            }           

            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
	}
	
}
