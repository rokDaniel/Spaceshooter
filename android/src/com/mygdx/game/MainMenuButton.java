package com.mygdx.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.Button;

public class MainMenuButton extends Button {
    public MainMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Path path = getPath(50, true, true, true, true);
        int grad[] = {Color.BLACK,Color.TRANSPARENT};
        paint.setStyle(Paint.Style.STROKE);
        paint.setShader(new RadialGradient(getWidth()/2,getHeight()/2,getWidth()/2,grad,null,Shader.TileMode.MIRROR));
        paint.setStrokeWidth(30);
        canvas.drawPath(path, paint);
    }

    private Path getPath(float radius, boolean topLeft, boolean topRight,boolean bottomRight, boolean bottomLeft) {
        final Path path = new Path();
        final float[] radii = new float[8];
        if (topLeft) {
            radii[0] = radius;
            radii[1] = radius;
        }
        if (topRight) {
            radii[2] = radius;
            radii[3] = radius;
        }
        if (bottomRight) {
            radii[4] = radius;
            radii[5] = radius;
        }
        if (bottomLeft) {
            radii[6] = radius;
            radii[7] = radius;
        }
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()),radii, Path.Direction.CW);
        return path;
    }
}
