package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    public PlayerShip(float movementSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, float width, float height, float xCentre, float yCentre, TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture,int lives) {
        super(movementSpeed, shield, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots, width, height, xCentre, yCentre, shipTexture, shieldTexture, laserTexture,lives);
    }

    @Override
    public Laser[] fireLAser() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + (boundingBox.width * 0.07f),boundingBox.y + (boundingBox.height *0.45f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);
        laser[1] = new Laser(boundingBox.x + (boundingBox.width * 0.93f),boundingBox.y + (boundingBox.height *0.45f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);

        timeSinceLastShot = 0;

        return laser;
    }

    public void gotBoost(int type){
        switch(type){
            case 0: lives++; break;
            case 1: {this.timeBetweenShots/=3;
            this.laserMovementSpeed*=2;

            Thread t = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                        this.sleep(5000);
                        PlayerShip.this.setSpeedBoost();
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            break;}
            case 2: shield++; break;
        }
    }
    public void setSpeedBoost(){
        this.timeBetweenShots *= 3;
        this.laserMovementSpeed /= 2;
    }

}
