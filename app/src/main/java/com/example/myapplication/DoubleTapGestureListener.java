package com.example.myapplication;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DoubleTapGestureListener extends GestureDetector.SimpleOnGestureListener {
    private Runnable onDoubleTapCallback;

    public DoubleTapGestureListener(Runnable onDoubleTapCallback) {
        this.onDoubleTapCallback = onDoubleTapCallback;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        if (onDoubleTapCallback != null) {
            onDoubleTapCallback.run();
        }
        return super.onDoubleTap(e);
    }
}
