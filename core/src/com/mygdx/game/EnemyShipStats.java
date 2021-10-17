package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShipStats {
    float movementSpeed;
    int shield;
    float laserWidth;
    float laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float width;
    float height;
    float xCentre;
    float yCentre;
    TextureRegion shipTexture;
    com.badlogic.gdx.graphics.g2d.TextureRegion shieldTexture;
    TextureRegion laserTexture;
    int score;
    int lives;

    public EnemyShipStats(float movementSpeed, int shield, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots, float width, float height, float xCentre, float yCentre, TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTexture, int score, int lives) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenShots = timeBetweenShots;
        this.width = width;
        this.height = height;
        this.xCentre = xCentre;
        this.yCentre = yCentre;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
        this.laserTexture = laserTexture;
        this.score = score;
        this.lives = lives;
    }
}
