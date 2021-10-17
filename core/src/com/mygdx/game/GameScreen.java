package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;

public class GameScreen implements Screen {
    enum gameState{
        RUN,
        PAUSE
    }

    DeviceContext callBackObject;
    DialogPopupInCore popUpCallBack;
    gameState state;
    //Level
    private int levelNum;
    //screen
    private Camera camera;
    private Viewport viewport;


    //graphics
    private SpriteBatch batch;
    private Texture explosionTexture;
    private BitmapFont hebFont;

    //private Texture background;
    private TextureAtlas textureAtlas;
    private TextureRegion[] backgrounds;
    private float backgroundHeight;
    private TextureRegion ufoyellow,ufogreen,ufored,ufoblue,astroidTexture, playerShipTextureRegion, playerShieldTextureRegion, enemyShipGreen1TextureRegion, enemyShipGreen2TextureRegion, enemyShipBlack1TextureRegion, enemyShipBlack2TextureRegion, enemyShipOrange1TextureRegion, enemyShipOrange2TextureRegion, enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion;
    private TextureRegion[] boosters;
    //timing
      private float[] backgroundOffsets = {0, 0, 0, 0};
      private float backgroundMaxScrollingSpeed;
      private float timeBetweenEnemySpawn = 4;
      private float enemySpawnTimer = 0;
      private float astroidTimer = 0;
      private float boostTimer = 0;

    //world parameters
    private final float WORLD_WIDTH;
    private final float WORLD_HEIGHT;
    private final float TOUCH_MOVEMENT_THRESHOLD = 10f;
    private int NumOfEnemyShipsThisLevel;
    private boolean bossDefeated;

    //game objects
    private PlayerShip playerShip;
    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;
    private LinkedList<Explosion> explosionList;
    private LinkedList<Boost> boostsList;
    private LinkedList<Astroid> astroidList;
    private int score = 0;

    //Enemy ships info:
    private EnemyShipStats EnemyGreen1;
    private EnemyShipStats EnemyGreen2;
    private EnemyShipStats EnemyBlack1;
    private EnemyShipStats EnemyBlack2;
    private EnemyShipStats EnemyOrange1;
    private EnemyShipStats EnemyOrange2;


    private EnemyShipStats bossLevel1Stats,BossLevel2Stats,BossLevel3Stats,BossLevel4Stats,BossLevel5Stats,BossLevel6Stats;

    private Boss boss;


    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudRightX,hudLeftX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;
    String lang,langChangeFlag;

    GameScreen(int levelNum,DeviceContext deviceContex,DialogPopupInCore diaPopup) {
        state = gameState.RUN;
        this.levelNum =levelNum;
        this.popUpCallBack =diaPopup;
        WORLD_HEIGHT = deviceContex.getHeight();
        WORLD_WIDTH = deviceContex.getWidth();

        this.callBackObject = deviceContex;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        //Set up the texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundHeight = WORLD_HEIGHT * 2;
        backgroundMaxScrollingSpeed = (WORLD_HEIGHT) / 4;

        //initialize texture regions
        playerShipTextureRegion = textureAtlas.findRegion("playerShip1_blue");
        enemyShipGreen1TextureRegion = textureAtlas.findRegion("enemyGreen1");
        enemyShipGreen2TextureRegion = textureAtlas.findRegion("enemyGreen4");
        enemyShipBlack1TextureRegion = textureAtlas.findRegion("enemyBlack1");
        enemyShipBlack2TextureRegion = textureAtlas.findRegion("enemyBlack3");
        enemyShipOrange1TextureRegion = textureAtlas.findRegion("enemyRed3");
        enemyShipOrange2TextureRegion = textureAtlas.findRegion("enemyRed5");
        playerShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        ufoblue = textureAtlas.findRegion("ufoBlue");
        ufored = textureAtlas.findRegion("ufoRed");
        ufogreen = textureAtlas.findRegion("ufoGreen");
        ufoyellow = textureAtlas.findRegion("ufoYellow");


        //enemyShieldTextureRegion.flip(false,true);
        playerLaserTextureRegion = textureAtlas.findRegion("laserBlue03");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserRed03");
        explosionTexture = new Texture("explosion.png");

        boosters = new TextureRegion[3];
        boosters[0]= textureAtlas.findRegion("powerupBlue");
        boosters[1] =textureAtlas.findRegion("powerupBlue_bolt");
        boosters[2] =textureAtlas.findRegion("powerupBlue_shield");

        astroidTexture = textureAtlas.findRegion("meteorBrown_big4");
        //set up game objects
        playerShip = new PlayerShip(1000,3,5, 100, 1000,1f,WORLD_HEIGHT / 10,WORLD_HEIGHT / 10, WORLD_WIDTH / 2, WORLD_HEIGHT / 4, playerShipTextureRegion, playerShieldTextureRegion, playerLaserTextureRegion,5);
        enemyShipList = new LinkedList<>();
        explosionList = new LinkedList<>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        boostsList = new LinkedList<>();
        astroidList =new LinkedList<>();

        //EnemyShip info initialize
        this.EnemyGreen1 = new EnemyShipStats(800, 0, 5, 100, 600, 2.3f, WORLD_HEIGHT / 10, WORLD_HEIGHT / 10, SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipGreen1TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,100,1);
        this.EnemyGreen2 = new EnemyShipStats(800, 0, 5, 100, 600, 1.5f, WORLD_HEIGHT / 10, WORLD_HEIGHT / 10, SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipGreen2TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,300,1);
        this.EnemyBlack1 = new EnemyShipStats(500,0,5,200,1000,2f,WORLD_HEIGHT / 10,WORLD_HEIGHT / 10,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipBlack1TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,200,2);
        this.EnemyBlack2 = new EnemyShipStats(600,0,5,200,1000,1.5f,WORLD_HEIGHT / 10,WORLD_HEIGHT / 10,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipBlack2TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,400,2);
        this.EnemyOrange1 = new EnemyShipStats(700,0,5,200,1000,1.5f,WORLD_HEIGHT / 10,WORLD_HEIGHT / 10,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipOrange1TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,500,3);
        this.EnemyOrange2 = new EnemyShipStats(800,0,5,200,1000,1f,WORLD_HEIGHT / 10,WORLD_HEIGHT / 10,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 10) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 10), enemyShipOrange2TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,600,3);


        this.bossLevel1Stats = new EnemyShipStats(150,0,15,200,1500,2.5f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), ufogreen, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,15);
        this.BossLevel2Stats = new EnemyShipStats(200,0,15,250,1500,2f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), ufoblue, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,18);
        this.BossLevel3Stats = new EnemyShipStats(200,0,15,300,1200,1.5f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), ufoyellow, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,20);
        this.BossLevel4Stats = new EnemyShipStats(200,0,15,300,1200,1.5f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), ufored, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,22);
        this.BossLevel5Stats = new EnemyShipStats(200,0,15,300,1200,1.5f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), enemyShipOrange1TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,24);
        this.BossLevel6Stats = new EnemyShipStats(200,0,15,300,1200,1.5f,WORLD_HEIGHT / 4,WORLD_HEIGHT / 4,SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_HEIGHT / 4) + 5), WORLD_HEIGHT - (WORLD_HEIGHT / 4), enemyShipOrange2TextureRegion, enemyShieldTextureRegion, enemyLaserTextureRegion,2000,26);

        bossDefeated = false;
        NumOfEnemyShipsThisLevel = 0;

        batch = new SpriteBatch();

        prepareHUD();
    }

    private void prepareHUD() {
        lang = popUpCallBack.getLanguageOfDevice();
        langChangeFlag = lang;
        if(lang.equals("English")) {
            font = generateFont("Chopsic-K6Dp.ttf", FreeTypeFontGenerator.DEFAULT_CHARS);
        }
        else {
            font = generateFont("varelaroundregular.ttf", "אבגדהוזחטיכלמנצעפצקרשתץףךםן0123456789");
        }

        font.getData().setScale(1.1f);

        hudVerticalMargin = font.getCapHeight();
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }

    @Override
    public void render(float deltaTime) {
        if(state == gameState.RUN) {
            batch.begin();
            //scrolling background
            renderBackground(deltaTime);
            detectInput(deltaTime);
            backgroundSpeed();

            playerShip.update(deltaTime);
            spawnEnemyShips(deltaTime);
            spawnBoosts();
            AstroidSpawn(deltaTime);

            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();

            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();
                moveEnemies(enemyShip, deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.update(deltaTime);
                enemyShip.draw(batch);
            }

            //player ship
            playerShip.draw(batch);

            //booster
            ListIterator<Boost> boostsIterator = boostsList.listIterator();
            while (boostsIterator.hasNext()) {
                Boost boost = boostsIterator.next();
                moveBooster(boost, boostsIterator);
                boost.draw(batch);
            }

            //astroid
            ListIterator<Astroid> astroidIterator = astroidList.listIterator();
            while (astroidIterator.hasNext()) {
                Astroid astroid = astroidIterator.next();
                astroidMovement(astroid, astroidIterator);
                astroid.draw(batch);
            }

            //lasers
            renderLasers(deltaTime);

            //detect collisions
            detectCollisions();

            //explosions
            renderExplotions(deltaTime);

            //hud rendering
            updateAndRenderHUD();

            batch.end();
        }
        else if(state == gameState.PAUSE){
            batch.begin();
            renderBackground(deltaTime);
            batch.end();
        }
    }

    private void updateAndRenderHUD() {

        font.draw(batch, popUpCallBack.getTextFromResources("score"), hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, popUpCallBack.getTextFromResources("shield"), hudCentreX, hudRow1Y, hudSectionWidth, Align.center, false);
        font.draw(batch, popUpCallBack.getTextFromResources("lives"), hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);

        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield),  hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.lives), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }

    private void moveEnemies(EnemyShip enemyShip, float deltaTime) {
        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -enemyShip.boundingBox.x;
        downLimit = WORLD_HEIGHT / 2 - enemyShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - enemyShip.boundingBox.x - enemyShip.boundingBox.width;
        upLimit = WORLD_HEIGHT - enemyShip.boundingBox.y - enemyShip.boundingBox.height;

        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;

        if (xMove > 0) xMove = Math.min(xMove, rightLimit);
        else xMove = Math.max(xMove,leftLimit);

        if (yMove > 0) yMove = Math.min(yMove, upLimit);
        else yMove = Math.max(yMove,downLimit);

        enemyShip.translate(xMove,yMove);
    }

    private void spawnEnemyShips(float deltaTime){
        if (enemyShipList.size() <= 3) {
            enemySpawnTimer += deltaTime;
            if (enemySpawnTimer > timeBetweenEnemySpawn) {
                switch (levelNum){
                    case 1: {
                        levelEnemySpawn(EnemyGreen1,bossLevel1Stats,7);
                        break;
                    }
                    case 2:{
                        levelEnemySpawn(EnemyBlack1,BossLevel2Stats,10);
                        break;
                    }
                    case 3:{
                        levelEnemySpawn(EnemyGreen2,BossLevel3Stats,12);
                        break;
                    }
                    case 4:{
                        levelEnemySpawn(EnemyBlack2,BossLevel4Stats,15);
                        break;
                    }
                    case 5:{
                        levelEnemySpawn(EnemyOrange1,BossLevel5Stats,15);
                        break;
                    }
                    case 6:{
                        levelEnemySpawn(EnemyOrange2,BossLevel6Stats,20);
                        break;
                    }
                }
                enemySpawnTimer -= timeBetweenEnemySpawn;
            }

        }
    }

    private void spawnBoosts() {
        Random r = new Random();
        if (Boost.boostTimer(0.002)) {
            int rand = r.nextInt(3);
            boostsList.add(new Boost(rand,WORLD_WIDTH / 15, WORLD_WIDTH / 15, SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - (WORLD_WIDTH / 15) + 5), WORLD_HEIGHT, 10, boosters[rand]));
        }
    }
    private void moveBooster(Boost b,ListIterator<Boost> boostsIterator){
        b.translate(0,-1*b.movementSpeed);
        if(b.boundingBox.y < 0){
            boostsIterator.remove();
        }
    }

    private void detectInput(float deltaTime) {

        float leftLimit, rightLimit, upLimit, downLimit;
        leftLimit = -playerShip.boundingBox.x;
        downLimit = -playerShip.boundingBox.y;
        rightLimit = WORLD_WIDTH - playerShip.boundingBox.x - playerShip.boundingBox.width;
        upLimit = WORLD_HEIGHT / 2 - playerShip.boundingBox.y - playerShip.boundingBox.height;

        if (Gdx.input.isTouched()) {
            //get the screen position of the touch
            float xTouchPixels = Gdx.input.getX();
            float yTouchPixels = Gdx.input.getY();

            //convert to world position
            Vector2 touchPoint = new Vector2(xTouchPixels, yTouchPixels);
            touchPoint = viewport.unproject(touchPoint);

            //calculate the x and y differences
            //center of player ship
            Vector2 playerShipCentre = new Vector2(
                    playerShip.boundingBox.x + playerShip.boundingBox.width/2,
                    playerShip.boundingBox.y + playerShip.boundingBox.height/2);

            float touchDistance = touchPoint.dst(playerShipCentre);

            if (touchDistance > TOUCH_MOVEMENT_THRESHOLD) {
                float xTouchDifference = touchPoint.x - playerShipCentre.x;
                float yTouchDifference = touchPoint.y - playerShipCentre.y;

                //scale to the maximum speed of the ship
                float xMove = xTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;
                float yMove = yTouchDifference / touchDistance * playerShip.movementSpeed * deltaTime;

                if (xMove > 0) xMove = Math.min(xMove, rightLimit);
                else xMove = Math.max(xMove,leftLimit);

                if (yMove > 0) yMove = Math.min(yMove, upLimit);
                else yMove = Math.max(yMove,downLimit);

                playerShip.translate(xMove,yMove);
            }
        }

    }

    private void detectCollisions() {
        //for each player laser check whether it intersects enemy ship
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();

        while(laserListIterator.hasNext()){
            Laser laser= laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()){
                EnemyShip enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.boundingBox)){
                    //contact with ship
                    if (enemyShip.hitAndCheckDestroy()){
                        enemyShipListIterator.remove();
                        explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));
                        score += enemyShip.score;
                        if(enemyShip.equals(boss)){
                            SpaceShooterGame.winningDialog(levelNum,score);
                            state = gameState.PAUSE;
                        }
                    }
                    laserListIterator.remove();
                    break;
                }

            }
        }

        //for each enemy laser check whether it intersects player ship
        laserListIterator = enemyLaserList.listIterator();

        while(laserListIterator.hasNext()){
            Laser laser= laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)){
                //contact with ship
                if (playerShip.hitAndCheckDestroy()){
                    explosionList.add(new Explosion(explosionTexture, new Rectangle(playerShip.boundingBox), 1.6f));
                    SpaceShooterGame.GameOverDialog(1,score);
                    state = gameState.PAUSE;
                    score = 0;
                }
                laserListIterator.remove();
            }
        }
        //booster collision with player
        ListIterator<Boost> boostListIterator= boostsList.listIterator();
        while(boostListIterator.hasNext()){
            Boost boost = boostListIterator.next();
            if(playerShip.intersects(boost.boundingBox)){
                playerShip.gotBoost(boost.type);
                boostListIterator.remove();
            }
        }

        ListIterator<Astroid> astroidListIterator = astroidList.listIterator();
        while(astroidListIterator.hasNext()){
            Astroid astroid = astroidListIterator.next();
            if(playerShip.intersects(astroid.boundingBox)){
                if(playerShip.hitAndCheckDestroy()){
                    SpaceShooterGame.GameOverDialog(1,score);
                    state = gameState.PAUSE;
                    score = 0;
                }
                explosionList.add(new Explosion(explosionTexture,new Rectangle(playerShip.boundingBox),1.6f));
                astroidListIterator.remove();
            }
        }

    }

    private void renderExplotions(float deltaTime){
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while(explosionListIterator.hasNext()){
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()){
                explosionListIterator.remove();
            }
            else{
                explosion.draw(batch);
            }
        }
    }

    private void renderLasers(float deltaTime){
        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()){
            Laser[] lasers = playerShip.fireLAser();
            for(Laser laser: lasers){
                playerLaserList.add(laser);
            }
        }
        //enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()){
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()){
                Laser[] lasers = enemyShip.fireLAser();
                for(Laser laser: lasers){
                    enemyLaserList.add(laser);
                }
            }
        }

        //draw lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y += laser.movementSpeed * deltaTime;

            //remove old lasers
            if (laser.boundingBox.y > WORLD_HEIGHT){
                iterator.remove();
            }
        }

        iterator = enemyLaserList.listIterator();
        while(iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(batch);
            laser.boundingBox.y -= laser.movementSpeed * deltaTime;

            //remove old lasers
            if (laser.boundingBox.y + laser.boundingBox.height < 0){
                iterator.remove();
            }
        }
    }

    private void renderBackground(float deltaTime){
        if(state == gameState.RUN) {
            backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
            backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
            backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
            backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;
        }

        for (int layer = 0; layer < backgroundOffsets.length; layer ++){
            if (backgroundOffsets[layer] > WORLD_HEIGHT){
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, backgroundHeight);
        }
    }

    private void AstroidSpawn(float deltaTime){
        astroidTimer+=deltaTime;
        if(astroidTimer > 10){
            astroidList.add(new Astroid(WORLD_HEIGHT / 7, WORLD_HEIGHT / 7, WORLD_WIDTH, WORLD_HEIGHT*3/4, 5, astroidTexture));
            astroidTimer = 0;
        }
    }
    private void astroidMovement(Astroid astroid,ListIterator<Astroid> itr){
        astroid.translate(astroid.xMove,astroid.yMove);
        if(astroid.boundingBox.y < 0 || astroid.boundingBox.x <0){
            itr.remove();
        }
    }


    public void backgroundSpeed(){
        switch (levelNum){
            case 1: {
                break;
            }
            case 2:{
                backgroundMaxScrollingSpeed = (WORLD_HEIGHT)/2;
                break;
            }
            case 3:{
                backgroundMaxScrollingSpeed = (WORLD_HEIGHT);
                break;
            }
            case 4:{
                backgroundMaxScrollingSpeed = (WORLD_HEIGHT)*1.2f;
                break;
            }
            case 5:{
                backgroundMaxScrollingSpeed = (WORLD_HEIGHT)*1.5f;
                break;
            }
            case 6:{
                backgroundMaxScrollingSpeed = (WORLD_HEIGHT)*2f;
                break;
            }
        }
    }

    private void levelEnemySpawn(EnemyShipStats thisLevelShip,EnemyShipStats thisLevelBoss,int numOfShips){
        if(NumOfEnemyShipsThisLevel <=numOfShips ) {
            enemyShipList.add(new EnemyShip(thisLevelShip));
            NumOfEnemyShipsThisLevel++;
        }
        if(enemyShipList.size() == 0 && NumOfEnemyShipsThisLevel>numOfShips){
            boss = new Boss(thisLevelBoss);
            enemyShipList.add(boss);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {

    }



    private BitmapFont generateFont(String fontName, String characters) {

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = 48;
        parameter.borderWidth = 3.6f;
        parameter.color = new Color(1, 1, 1, 0.3f);
        parameter.borderColor = new Color(0, 0, 0, 0.3f);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal(fontName) );
        BitmapFont font = generator.generateFont(parameter);

        generator.dispose();

        return font;
    }
}
