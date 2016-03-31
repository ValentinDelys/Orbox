package com.dii.polytech.orbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;

public class DescriptorView extends ImageView {

    Point[] points = new Point[4];

    int groupId = -1;
    private ArrayList<ColorBall> colorballs = new ArrayList<ColorBall>(); // array that holds the balls
    private int balID = 0; // variable to know what ball is being dragged

    Paint paint;
    Canvas canvas;

    public DescriptorView(Context context) {
        super(context);

        paint = new Paint();
        setFocusable(true);
        canvas = new Canvas();
    }

    public DescriptorView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        setFocusable(true);
        canvas = new Canvas();
    }

    public DescriptorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public float getRatio(){
        return (float)(getDrawable().getIntrinsicWidth()/2)/getWidth();
    }

    public ArrayList<Point> getPoints(){
        ArrayList<Point> coordinates = new ArrayList();
        coordinates.add(new Point(points[0]));
        coordinates.add(new Point(points[2]));

        float ratio = getRatio();

        for(Point p : coordinates)
        {
            p.x = (int) ((p.x + (colorballs.get(0).getWidthOfBall()/2)) * ratio);
            p.y = (int) ((p.y + (colorballs.get(0).getHeightOfBall()/2)) * ratio);
        }

        return coordinates;
    }

    /* Manage events when touching the screen */
    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        Log.d("Right", String.valueOf(this.getRight()));
        Log.d("X", String.valueOf(event.getX()));

        //TODO Completer les conditions
        if(points[0] != null) {
            Log.d("X", String.valueOf(event.getX() + (colorballs.get(0).getWidthOfBall())));
            if (X < 0)
                X = 0;
            if (Y < 0)
                Y = 0;
            if(X + colorballs.get(0).getWidthOfBall()/2 > getWidth() ) X = getWidth() - colorballs.get(0).getWidthOfBall();
        }

        switch (eventaction) {

            /* Touch down, so check if the finger is on a ball */
            case MotionEvent.ACTION_DOWN:
                if (points[0] == null) {
                    //initialize rectangle.
                    points[0] = new Point();
                    points[0].x = X;
                    points[0].y = Y;

                    points[1] = new Point();
                    points[1].x = X;
                    points[1].y = Y + 150;

                    points[2] = new Point();
                    points[2].x = X + 150;
                    points[2].y = Y + 150;

                    points[3] = new Point();
                    points[3].x = X + 150;
                    points[3].y = Y;

                    balID = 2;
                    groupId = 1;

                    // declare each ball with the ColorBall class
                    for (Point pt : points) {
                        colorballs.add(new ColorBall(getContext(), R.drawable.gray_circle, pt));
                    }
                } else {
                    //resize rectangle
                    balID = -1;
                    groupId = -1;

                    for (int i = (colorballs.size()-1); i >= 0; i--) {
                        ColorBall ball = colorballs.get(i);
                        // check if inside the bounds of the ball (circle)
                        // get the center for the ball
                        int centerX = ball.getX() + ball.getWidthOfBall();
                        int centerY = ball.getY() + ball.getHeightOfBall();
                        paint.setColor(Color.CYAN);
                        // calculate the radius from the touch to the center of the ball
                        double radCircle = Math
                                .sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y)
                                        * (centerY - Y)));

                        if (radCircle < ball.getWidthOfBall()) {

                            balID = ball.getID();
                            if (balID == 1 || balID == 3) {
                                groupId = 2;
                            } else {
                                groupId = 1;
                            }
                            invalidate();
                            break;
                        }
                        invalidate();
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE: // touch drag with the ball

                if (balID > -1) {
                    // move the balls the same as the finger
                    colorballs.get(balID).setX(X);
                    colorballs.get(balID).setY(Y);

                    paint.setColor(Color.parseColor("#3F51B5"));
                    if (groupId == 1) {
                        colorballs.get(1).setX(colorballs.get(0).getX());
                        colorballs.get(1).setY(colorballs.get(2).getY());
                        colorballs.get(3).setX(colorballs.get(2).getX());
                        colorballs.get(3).setY(colorballs.get(0).getY());
                    } else {
                        colorballs.get(0).setX(colorballs.get(1).getX());
                        colorballs.get(0).setY(colorballs.get(3).getY());
                        colorballs.get(2).setX(colorballs.get(3).getX());
                        colorballs.get(2).setY(colorballs.get(1).getY());
                    }

                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                // touch drop - just do things here after dropping
                break;

            default:
                break;
        }

        // redraw the canvas
        invalidate();
        return true;

    }

    /* The method that draws the balls */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if(points[3] == null) //point4 null when user did not touch and move on screen.
            return;

        int left, top, right, bottom;

        left = points[0].x;
        top = points[0].y;
        right = points[0].x;
        bottom = points[0].y;

        for(Point p : points) {
            left = left > p.x ? p.x:left;
            top = top > p.y ? p.y:top;
            right = right < p.x ? p.x:right;
            bottom = bottom < p.y ? p.y:bottom;
        }

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(5);

        //draw stroke
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#303F9F"));
        paint.setStrokeWidth(2);

        canvas.drawRect(
                left + colorballs.get(0).getWidthOfBall() / 2,
                top + colorballs.get(0).getWidthOfBall() / 2,
                right + colorballs.get(2).getWidthOfBall() / 2,
                bottom + colorballs.get(2).getWidthOfBall() / 2, paint);

        //fill the rectangle
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#AA3F51B5"));

        //paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(0);
        canvas.drawRect(
                left + colorballs.get(0).getWidthOfBall() / 2,
                top + colorballs.get(0).getWidthOfBall() / 2,
                right + colorballs.get(2).getWidthOfBall() / 2,
                bottom + colorballs.get(2).getWidthOfBall() / 2, paint);

        // draw the balls on the canvas
        paint.setColor(Color.parseColor("#303F9F"));
        paint.setTextSize(18);
        paint.setStrokeWidth(0);

        for(ColorBall b : colorballs) {
            ColorBall ball = b;
            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), paint);

            //canvas.drawText(ball.getX() + (ball.getWidthOfBall() / 2) + ", " + ball.getY() + (ball.getHeightOfBall() / 2), ball.getX(), ball.getY(), paint);
        }
    }

    public static class ColorBall {

        Bitmap bitmap;
        Context mContext;
        Point point;
        int id;
        static int count = 0;

        public ColorBall(Context context, int resourceId, Point point) {
            if (count > 3) count = 0;
            this.id = count++;
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    resourceId);
            mContext = context;
            this.point = point;
        }

        public int getWidthOfBall() {
            return bitmap.getWidth();
        }

        public int getHeightOfBall() {
            return bitmap.getHeight();
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public int getX() {
            return point.x;
        }

        public int getY() {
            return point.y;
        }

        public int getID() {
            return id;
        }

        public void setX(int x) {
            point.x = x;
        }

        public void setY(int y) {
            point.y = y;
        }
    }
}


