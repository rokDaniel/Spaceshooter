package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;


public class EnemyShip extends Ship {


    int score;
    float timeSinceLastDirectionChange = 0f;
    float directionChangeFrequency = 0.75f;

    public EnemyShip(EnemyShipStats shipStats) {
        super(shipStats.movementSpeed,shipStats.shield,shipStats.laserWidth,shipStats.laserHeight,shipStats.laserMovementSpeed,shipStats.timeBetweenShots,shipStats.width,shipStats.height,shipStats.xCentre,shipStats.yCentre,shipStats.shipTexture,shipStats.shieldTexture,shipStats.laserTexture,shipStats.lives);
        this.score = shipStats.score;
        this.lives =shipStats.lives;
        direction = new Vector2(0, -1);
    }

    public Vector2 getDirectionVector() {
        return direction;
    }

    private void randomizeDirectionVector(){
        double bearing = SpaceShooterGame.random.nextDouble() * 6.283185; //0 to 2*PI
        direction.x = (float)Math.sin(bearing);
        direction.y = (float)Math.cos(bearing);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeSinceLastDirectionChange += deltaTime;

        if (timeSinceLastDirectionChange > directionChangeFrequency){
            randomizeDirectionVector();
            timeSinceLastDirectionChange -= directionChangeFrequency;
        }
    }

    @Override
    public Laser[] fireLAser() {
        Laser[] laser = new Laser[2];
        laser[0] = new Laser(boundingBox.x + (boundingBox.width * 0.18f),boundingBox.y + (boundingBox.height *0.45f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);
        laser[1] = new Laser(boundingBox.x + (boundingBox.width * 0.82f),boundingBox.y + (boundingBox.height *0.45f), laserWidth, laserHeight, laserMovementSpeed, laserTexture);

        timeSinceLastShot = 0;

        return laser;
    }

    boolean subtractLives(int liveToSub){
        this.lives -= liveToSub;
        if(lives==0)
            return true;
        else
            return false;
    }
}
