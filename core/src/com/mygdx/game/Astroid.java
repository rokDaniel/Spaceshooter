package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Astroid extends MovableObject{

    TextureRegion atroidTexture;
    final float xMove;
    final float yMove;

    public Astroid(float width, float height, float xCenter, float yCenter,int movementSpeed, TextureRegion texture) {
        this.atroidTexture = texture;
        this.movementSpeed = movementSpeed;
        this.boundingBox = new Rectangle(xCenter - width / 2,  yCenter - height / 2,width,height);
        this.direction = new Vector2(-1,-1);
        xMove = SpaceShooterGame.random.nextFloat() *this.movementSpeed *-5;
        yMove = SpaceShooterGame.random.nextFloat() *this.movementSpeed *-6;
    }

    public void draw(Batch batch){
        batch.draw(atroidTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

}
