package com.nicholaschirkevich.game.model;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.nicholaschirkevich.game.enums.CollisionPasserCarType;
import com.nicholaschirkevich.game.interfaces.GenerateHoleAfterLadle;
import com.nicholaschirkevich.game.model.abstarct_model.Model;
import com.nicholaschirkevich.game.userdata.PasserCarDataType;
import com.nicholaschirkevich.game.util.Constants;
import com.nicholaschirkevich.game.util.GameManager;
import com.nicholaschirkevich.game.util.RandomUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Nikolas on 13.02.2016.
 */
public class PasserCar extends com.nicholaschirkevich.game.model.side_objects.Car implements Model {
    public static final int GENERATE_CAR_DISTANCE = 100;
    public static final int DEFAULT_DISTANCE = 90;
    public static final int APPEND_DISTANCE = 170;
    public static final int DEFAULT_MOVEMENT = 10;
    public static final int DEFAULT_SECOND_DISTANCE = 200;
    public static final float ANGLE = 0.0f;
    public static final int APPEND_Y = 600;
    public static final int COEFFICIENT = 2;
    public static final int RANDOM_MAX = 70;
    public static final int RANDOM_MIN = 0;
    public static final int BLOCK_TIME = 12;
    public static final int DEFAULT_Y = 200;
    public static final int COLLISION_TURN_SPEED = 880;
    public static final int LEFT_BORDER = 40;
    public static final int SPEED_COEFFICIENT = 56;
    private Random rand;
    private static World world;

    private float angelt = 0;
    private float angeltCrashLadle = 0;

    private int defaultX;
    private int defaultY;
    public Body body;
    private float sideCollisionTime = 0;
    private static boolean isBlocks = false;
    private static int bloksCount = 0;
    private static float blocksTime = 6;
    private static boolean bloksIsLeftStart = false;
    private static float timeBloks = 0;
    private static boolean isFromGarage = false;
    private static float timer = 0;
    private GenerateHoleAfterLadle generateHoleAfterLadleInterface;
    private float stateTime;

    public PasserCar(World world, int x, int y, int movement, String textureSrc, GenerateHoleAfterLadle generateHoleAfterLadle) {
        super(x, y, movement, textureSrc);
        defaultX = x;
        defaultY = y;
        rand = new Random();
        generateHoleAfterLadleInterface = generateHoleAfterLadle;
        isLeft = rand.nextBoolean();
        if (isLeft)
            position.x = Constants.getCarPostitionXLeft(carAnimation.getKeyFrames()[0].getRegionWidth());
        else
            position.x = Constants.getCarPostitionXRight(carAnimation.getKeyFrames()[0].getRegionWidth());

        this.world = world;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((carAnimation.getKeyFrames()[0].getRegionX() + carAnimation.getKeyFrames()[0].getRegionWidth() / 2) / Constants.PIXELS_TO_METERS
                ,
                ((carAnimation.getKeyFrames()[0].getRegionY() + carAnimation.getKeyFrames()[0].getRegionHeight() / 2) - 500) / Constants.PIXELS_TO_METERS);

        body = world.createBody(bodyDef);
        body.setLinearDamping(Constants.PASSER_CAR_LINEAR_DUMPING);
        body.setAngularDamping(Constants.PASSER_CAR_ANGULAR_DUMPING);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(carAnimation.getKeyFrames()[0].getRegionWidth() / 2 / Constants.PIXELS_TO_METERS, carAnimation.getKeyFrames()[0].getRegionHeight()
                / 2 / Constants.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0001f;
        fixtureDef.restitution = 1f;


        fixtureDef.filter.categoryBits = Constants.WORLD_ENTITY;
        fixtureDef.filter.maskBits = Constants.MY_CAR_FILTER_ENTITY;

        PasserCarDataType passerCarDataType = new PasserCarDataType();
        passerCarDataType.setBounds(bounds);
        body.setUserData(passerCarDataType);
        body.createFixture(fixtureDef);
        stateTime = 0f;

    }


    public PasserCar(World world, int x, int y, int movement, boolean ifLeft, String textureSrc, GenerateHoleAfterLadle generateHoleAfterLadle) {
        super(x, y, movement, textureSrc);
        defaultX = x;
        defaultY = y;

        generateHoleAfterLadleInterface = generateHoleAfterLadle;
        isLeft = ifLeft;
        if (isLeft)
            position.x = Constants.getCarPostitionXLeft(carAnimation.getKeyFrames()[0].getRegionWidth());
        else
            position.x = Constants.getCarPostitionXRight(carAnimation.getKeyFrames()[0].getRegionWidth());

        this.world = world;


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((carAnimation.getKeyFrames()[0].getRegionX() + carAnimation.getKeyFrames()[0].getRegionWidth() / 2) / Constants.PIXELS_TO_METERS
                ,
                (carAnimation.getKeyFrames()[0].getRegionY() + carAnimation.getKeyFrames()[0].getRegionHeight() / 2) / Constants.PIXELS_TO_METERS);

        body = world.createBody(bodyDef);
        body.setLinearDamping(Constants.PASSER_CAR_LINEAR_DUMPING);
        body.setAngularDamping(Constants.PASSER_CAR_ANGULAR_DUMPING);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(carAnimation.getKeyFrames()[0].getRegionWidth() / 2 / Constants.PIXELS_TO_METERS, carAnimation.getKeyFrames()[0].getRegionHeight()
                / 2 / Constants.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.0001f;
        fixtureDef.restitution = 1f;

        fixtureDef.filter.categoryBits = Constants.WORLD_ENTITY;
        fixtureDef.filter.maskBits = Constants.MY_CAR_FILTER_ENTITY | Constants.ROAD_SIDE_LEFT;
        PasserCarDataType passerCarDataType = new PasserCarDataType();
        passerCarDataType.setBounds(bounds);
        body.setUserData(passerCarDataType);
        body.createFixture(fixtureDef);
        stateTime = 0f;

    }

    public static boolean isBlocks() {
        return isBlocks;
    }

    public boolean getIsLeft() {
        return isLeft;
    }

    @Override
    public void draw(SpriteBatch sb) {

    }


    @Override
    public void update(float dt) {

        PasserCarDataType passerCarDataType = (PasserCarDataType) body.getUserData();

        if (passerCarDataType.getCollisionPasserCarType().equals(passerCarDataType.getCollisionPasserCarType().SIDE_COLLISION)) {
            sideCollisionTime += dt;

            if (!isLeft && position.x > DEFAULT_SECOND_DISTANCE) {
                passerCarDataType.setIsAfterHoleCollision(true);
            }
            if (isLeft && position.x < LEFT_BORDER) {
                passerCarDataType.setIsAfterHoleCollision(true);
            }

            if (isLeft) {

                position.add(-COLLISION_TURN_SPEED * dt, -DEFAULT_MOVEMENT * dt, 0);
            } else {

                position.add((COLLISION_TURN_SPEED) * dt, -DEFAULT_MOVEMENT * dt, 0);
            }

            if (((PasserCarDataType) body.getUserData()).isContact()) {
                bounds.setPosition(body.getPosition().x, body.getPosition().y);
                position.x = body.getPosition().x;
                position.y = body.getPosition().y;

            } else {
                bounds.setPosition(position.x, position.y);
                body.setTransform(position.x, position.y, angeltCrashLadle);

            }
        }


        if (passerCarDataType.isGodMode()) {
            Rectangle intersaction = new Rectangle();


            if (!passerCarDataType.isBefore()) {
                passerCarDataType.setCollisionPasserCarType(CollisionPasserCarType.SIDE_COLLISION);

            } else {
                passerCarDataType.setCollisionPasserCarType(CollisionPasserCarType.COUNTER_COLLISION);
                position.set(position.x, position.y, 0);


                bounds.setPosition(position.x, position.y);
                body.setTransform(position.x, position.y, ANGLE);
                setPosition(position.x, position.y);
            }

        } else if (passerCarDataType.isCollisionThrons()) {


        } else if (!passerCarDataType.isBlow() && !passerCarDataType.isLadleCollision()) {
            position.add(0, (-GameManager.getCurrentSpeed() + SPEED_COEFFICIENT) * dt, 0);

            bounds.setPosition(position.x, position.y);
            body.setTransform(position.x, position.y, ANGLE);
            setPosition(position.x, position.y);
            stateTime += dt;
        } else if (passerCarDataType.isBlow()) {


            timer += dt;


            if (timer > 0.4) {
                GameManager.pauseGame = true;

                timer = 0;
            }

        } else if (passerCarDataType.isLadleCollision()) {


            stateTime += dt;

        }

    }

    public void setDefaultPosition(int x) {
        position.set(0, x, 0);

        bounds.setPosition(position.x, position.y);
        body.setTransform(position.x, position.y, ANGLE);


    }

    public static Float getPosYLastCar(ArrayList<PasserCar> passerCars) {
        return passerCars.size() != 0 ? passerCars.get(passerCars.size() - 1).getPosition().y : DEFAULT_Y;
    }

    public static boolean getIsLeftLastCar(ArrayList<PasserCar> passerCars) {
        return passerCars.size() != 0 ? passerCars.get(passerCars.size() - 1).getIsLeft() : false;
    }


    public static void updateCars(ArrayList<PasserCar> passerCars, Camera camera, float dt, GenerateHoleAfterLadle generateHoleAfterLadle) {

        if (isBlocks == false)
            PasserCar.updatePasserCars(passerCars, camera, dt, generateHoleAfterLadle);
        else {
            PasserCar.generatePasserCarsBloks(passerCars, camera, dt, generateHoleAfterLadle);
        }
        PasserCar.generateBlocks(dt);

    }

    public void setPosition() {

        int randomY = rand.nextInt((RANDOM_MAX - RANDOM_MIN) + 1) + RANDOM_MIN;
        isLeft = rand.nextBoolean();
        if (isLeft)
            position.x = Constants.CAR_POS_X_LEFT - carAnimation.getKeyFrames()[0].getRegionHeight() / COEFFICIENT;
        else
            position.x = Constants.CAR_POS_X_RIGHT - carAnimation.getKeyFrames()[0].getRegionHeight() / COEFFICIENT;
        position.add(0, defaultY + randomY + APPEND_Y, 0);

        bounds.setPosition(position.x, position.y);
        body.setTransform(position.x, position.y, ANGLE);
    }

    @Override
    public void turn() {

    }

    @Override
    public void turn(boolean left) {

    }

    public void dispose() {


    }

    public static void generateBlocksCount() {
        bloksCount = RandomUtil.getRandBocksCount();

    }

    public static void generateBlocks(float dt) {

        timeBloks += dt;
        if (timeBloks > blocksTime) {
            isBlocks = true;
            timeBloks = 0;
            bloksIsLeftStart = RandomUtil.getRandomBoolean();
            generateBlocksCount();
            blocksTime = BLOCK_TIME;
        }
    }

    public static void generatePasserCarsBloks(ArrayList<PasserCar> passerCars, Camera camera, float dt, GenerateHoleAfterLadle generateHoleAfterLadle) {

        if (!GameManager.isStopGeneratePasserCars()) {
            if (bloksCount > 0) {

                if (passerCars.size() != 0 && camera.viewportHeight - passerCars.get(passerCars.size() - 1).getPosition().y > GENERATE_CAR_DISTANCE) {

                    passerCars.add(new PasserCar(world, DEFAULT_DISTANCE, (int) camera.viewportHeight + APPEND_DISTANCE, DEFAULT_MOVEMENT, bloksIsLeftStart, RandomUtil.getRandomOtherCarType().getKey(), generateHoleAfterLadle));
                    if (bloksIsLeftStart) bloksIsLeftStart = false;
                    else bloksIsLeftStart = true;

                    bloksCount--;


                }

            } else isBlocks = false;
        } else {
            if (isBlocks) {
                isBlocks = false;
                bloksCount = 0;
            }

        }
        for (int i = 0; i < passerCars.size(); i++) {

            if (passerCars.get(i).getPosition().y < 0) {
                if (passerCars.get(i).body != null)
                    world.destroyBody(passerCars.get(i).body);
                passerCars.remove(i);

            }
            if (passerCars.size() != 0 && ((PasserCarDataType) passerCars.get(i).body.getUserData()).isLadleCollision()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                break;
            }
            if (passerCars.size() != 0 && ((PasserCarDataType) passerCars.get(i).body.getUserData()).isCollisionWhithPasser()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                break;

            }

            if ((((PasserCarDataType) passerCars.get(i).body.getUserData()).getCollisionPasserCarType().equals(CollisionPasserCarType.SIDE_COLLISION)) && ((PasserCarDataType) passerCars.get(i).body.getUserData()).isAfterHoleCollision()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                generateHoleAfterLadle.addAchives();
                break;
            }

            if (passerCars.size() != 0) passerCars.get(i).update(dt);
        }

    }


    public static void updatePasserCars(ArrayList<PasserCar> passerCars, Camera camera, float dt, GenerateHoleAfterLadle generateHoleAfterLadle) {

        if (!GameManager.isStopGeneratePasserCars()) {
            if (passerCars.size() != 0 && camera.viewportHeight - passerCars.get(passerCars.size() - 1).getPosition().y > Constants.passerCarDistance) {
                passerCars.add(new PasserCar(world, DEFAULT_DISTANCE, (int) camera.viewportHeight + DEFAULT_SECOND_DISTANCE, DEFAULT_MOVEMENT, RandomUtil.getRandomOtherCarType().getKey(), generateHoleAfterLadle));
            }

        }
        if (!GameManager.isStopGeneratePasserCars() && passerCars.size() == 0) {
            passerCars.add(new PasserCar(world, DEFAULT_DISTANCE, (int) camera.viewportHeight + DEFAULT_SECOND_DISTANCE, DEFAULT_MOVEMENT, RandomUtil.getRandomOtherCarType().getKey(), generateHoleAfterLadle));
        }
        for (int i = 0; i < passerCars.size(); i++) {

            if (((PasserCarDataType) passerCars.get(i).body.getUserData()).isLadleCollision()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                break;
            }
            if (((PasserCarDataType) passerCars.get(i).body.getUserData()).isCollisionWhithPasser()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                generateHoleAfterLadle.onCollisionWithPasserCar();
                break;
            }

            if ((((PasserCarDataType) passerCars.get(i).body.getUserData()).getCollisionPasserCarType().equals(CollisionPasserCarType.SIDE_COLLISION)) && ((PasserCarDataType) passerCars.get(i).body.getUserData()).isAfterHoleCollision()) {
                passerCars.get(i).generateHoleAfterLadleInterface.generateHoleAfterLadle(passerCars.get(i).body.getPosition().x, passerCars.get(i).body.getPosition().y);
                world.destroyBody(passerCars.get(i).body);
                passerCars.get(i).remove();
                passerCars.remove(i);
                generateHoleAfterLadle.addAchives();
                break;
            }

            if (passerCars.get(i).getPosition().y < 0) {
                world.destroyBody(passerCars.get(i).body);
                passerCars.remove(i);
            }

            if (passerCars.size() != 0) passerCars.get(i).update(dt);
        }

    }


    public float getStateTime() {
        return stateTime;
    }

    public Animation getPasserCarAnimation() {
        return carAnimation;
    }
}
