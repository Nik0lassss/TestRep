package com.nicholaschirkevich.game.unput;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.nicholaschirkevich.game.interfaces.CarTurnInterface;
import com.nicholaschirkevich.game.util.Constants;
import com.nicholaschirkevich.game.util.GameManager;

/**
 * Created by Nikolas on 07.06.2016.
 */
public class GestureListnener implements GestureDetector.GestureListener {
    private CarTurnInterface carTurnInterface;
    private float deltaX = 0, deltaY = 0;
    private float touchPosX = 0, touchPosY = 0;
    public float getDeltaX() {
        return deltaX;
    }

    public float getDeltaY() {
        return deltaY;
    }


    public GestureListnener(CarTurnInterface carTurnInterface) {
        this.carTurnInterface = carTurnInterface;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        touchPosX = x;
        touchPosY = y;
        System.out.println("touchPosX " + touchPosX);
        System.out.println("touchPosY " + touchPosY);
        if (GameManager.isTouchControl() && !(x < Constants.xTouchBourder && y < Constants.yTouchBourder)) {
            carTurnInterface.turn();
        }

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {


        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!GameManager.isTouchControl() && !(touchPosX < Constants.xTouchBourder && touchPosY < Constants.yTouchBourder)) {
            if (Math.abs(deltaX) > 10 && Math.abs(deltaY) < 10) {
                if (deltaX < 0)
                    carTurnInterface.turn(true);
                else if(deltaX>0) carTurnInterface.turn(false);
            }
        }
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

}
