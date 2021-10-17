package com.mygdx.game;

public class Boss extends EnemyShip {
    public Boss(EnemyShipStats shipStats) {
        super(shipStats);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public Laser[] fireLAser() {
        Laser[] laser1 = super.fireLAser();
        Laser[] laserRes = new Laser[4];
        laserRes[0] = laser1[0];
        laserRes[1] = laser1[1];
        laserRes[2] = new Laser(boundingBox.x + (boundingBox.width * 0.38f),boundingBox.y + (boundingBox.height *0.35f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);
        laserRes[3] = new Laser(boundingBox.x + (boundingBox.width * 0.62f),boundingBox.y + (boundingBox.height *0.35f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);
        return laserRes;
    }
}
