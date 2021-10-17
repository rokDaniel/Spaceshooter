package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship extends MovableObject{
    //Ship characteristics
    int shield;
    int lives;

    //laser information
    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;



    //graphics
    TextureRegion shipTexture, shieldTexture, laserTexture;

    public Ship(float movementSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, float width, float height, float xCentre, float yCentre, TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture,int lives) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.boundingBox = new Rectangle(xCentre - width / 2,  yCentre - height / 2,width,height);
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        this.laserTexture = laserTexture;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this. timeBetweenShots = timeBetweenShots;
        this.lives = lives;
    }

    public void update(float deltaTime){
        timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser(){
        return timeSinceLastShot - timeBetweenShots >= 0;
    }

    public abstract  Laser[] fireLAser();

    public boolean intersects(Rectangle otherRectangle){
        return boundingBox.overlaps(otherRectangle);
    }

    public void draw(Batch batch){
        batch.draw(shipTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);

        if (shield > 0){
            batch.draw(shieldTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        }
    }

    public boolean hitAndCheckDestroy(){
        if (shield > 0){
            shield--;
            return false;
        }
        if(this.lives > 0){
            lives--;
            return false;
        }
        return true;
    }
}
