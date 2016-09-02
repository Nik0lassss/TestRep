package com.nicholaschirkevich.game.model.side_objects;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.nicholaschirkevich.game.enums.BushType;
import com.nicholaschirkevich.game.util.GameManager;

import java.util.Random;

/**
 * Created by Nikolas on 13.02.2016.
 */
public class Bushs extends BushsBasic {
    private Random rand;
    World world;


    private int defaultX;
    private int defaultY;
    public Body body;

    public Bushs(World world, int x, int y, int movement, boolean isLeft, String textureSrc) {
        super(x, y, movement, textureSrc);
        rand = new Random();
        int randomX = 0;

        if (bushType.equals(BushType.ROAD_1_TREE_1)) {
            randomX = rand.nextInt((30 + 35) + 1) - 35;
        } else randomX = rand.nextInt((30 + 15) + 1) - 15;
        defaultX = x;
        defaultY = y;


        if (isLeft) position.x = leftSide + randomX;
        else if (!isLeft) position.x = rightSide + randomX;


    }

    public Bushs(int x, int y, int movement, boolean isLeft, String textureSrc) {
        super(x, y, movement, textureSrc);
        rand = new Random();
        int randomX = 0;

        if (bushType.equals(BushType.ROAD_1_TREE_1)) {
            randomX = rand.nextInt((30 + 35) + 1) - 35;
        } else randomX = rand.nextInt((30 + 15) + 1) - 15;
        defaultX = x;
        defaultY = y;


        if (isLeft) position.x = leftSide + randomX;
        else if (!isLeft) position.x = rightSide + randomX;


    }

    @Override
    public void update(float dt) {
        position.add(0, (-GameManager.getCurrentSpeed()) * dt, 0);

        bounds.setPosition(position.x, position.y);


    }

    public void setPosition() {

        int randomY = rand.nextInt((50 - 0) + 1) + 0;
        isLeft = rand.nextBoolean();
        if (isLeft) position.x = leftSide;
        else position.x = rightSide;
        position.add(0, defaultY + randomY + 50, 0);

        bounds.setPosition(position.x, position.y);

    }

    @Override
    public void turn() {

    }

    public void dispose() {


    }
}
