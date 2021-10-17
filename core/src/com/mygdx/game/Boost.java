package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Boost extends MovableObject{
    TextureRegion boostTexture;
    int type;

    public static boolean boostTimer(double probability){
        Random r = new Random();
        double rand = r.nextDouble();
        if(rand < probability){
            return true;
        }
        return false;
    }

    public Boost(int type,float width, float height, float xCenter, float yCenter,int movementSpeed, TextureRegion texture) {
        this.direction = new Vector2(0,-1);
        this.boostTexture = texture;
        this.movementSpeed = movementSpeed;
        this.boundingBox = new Rectangle(xCenter - width / 2,  yCenter - height / 2,width,height);
        this.type = type;
    }

    public void draw(Batch batch){
        batch.draw(boostTexture, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

}
