/*
 * Copyright (C) 2014 Chris St. Jacobs. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.view;

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
 *
 * @author Chris St. James
 * @version 1.0
 * @since June 30, 2015
 */
public class ShadedImageView extends ImageView {
	public ShadedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 0: not shaded
	// 1: normal gray shade
	// 2: red shade for first PowerSet set
	// 3: blue shade for second PowerSet set
	private int shaded = 0;
	private final RectF rectF = new RectF();
	private final Paint paint = new Paint();

	public boolean getShaded() {
		return shaded != 0;
	}

	public void setShaded(boolean shaded) {

		if ((this.shaded > 0) != shaded) {
			this.shaded = shaded ? 1 : 0;
			postInvalidate();
			}
	}

	public void setShaded(int shadeType) {
		if (this.shaded != shadeType) {
			this.shaded = shadeType > 0 ? shadeType : 0;
			postInvalidate();
		}
	}

	@Override
	protected void onDraw(@NonNull Canvas canvas) {
		if (canvas == null) { throw new NullPointerException("Canvas cannot be null."); }

		super.onDraw(canvas);

		if(shaded > 0) {
			if (shaded == 1) { paint.setARGB(65, 0, 0, 0);}
			else if (shaded == 2) { paint.setARGB(65, 192, 0, 0); }
			else { paint.setARGB(65, 0, 0, 192); }

			rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());

			canvas.drawRect(rectF, paint);
		}
	}
}