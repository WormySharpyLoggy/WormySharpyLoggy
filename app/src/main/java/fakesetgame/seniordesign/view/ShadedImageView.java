package fakesetgame.seniordesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A custom ImageView that provides shading, for use in visually
 * distinguishing selected game tiles from unselected tiles, without
 * producing a set of shaded tile images.
 */
public class ShadedImageView extends ImageView {
    public ShadedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean shaded = false;

    public boolean getShaded() {
        return shaded;
    }

    public void setShaded(boolean shaded) {
        if (this.shaded != shaded) {
            this.shaded = shaded;
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if(shaded) {
            final RectF rectF = new RectF();
            final Paint paint = new Paint();
            paint.setARGB(65, 0, 0, 0);

            rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

            canvas.drawRect(rectF, paint);
        }
    }
}
