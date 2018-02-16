/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsapp;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class AsteroidsApp extends Application {

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();
    private List<GameObject> ruter = new ArrayList<>();

    private GameObject player;
    private StackPane entities;
    private Tile[][] board = new Tile[29][29];
    
     public static BufferedImage makeBufferedImage(BufferedImage i){
      BufferedImage result;
      PixelGrabber pg = new PixelGrabber(i, 0, 0, 1, 1, false);
      boolean alpha;

      if(i instanceof BufferedImage)
      {
         return((BufferedImage)i);
      }
      try
      {
         pg.grabPixels();
         alpha = pg.getColorModel().hasAlpha();
      }
      catch(InterruptedException e)
      {
         alpha = false;
      }

      result = new BufferedImage
      (
         i.getWidth(null),
         i.getHeight(null),
         alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
      );
      result.getGraphics().drawImage(i,0,0,null);
      return(result);
   }

    public Image[] getSprites(int antall) {
        BufferedImage source = null;
        BufferedImage source2 = null;
        try {
            source2 = ImageIO.read(new File("C:\\Users\\faete\\Documents\\NetBeansProjects\\zeGame\\src\\zegame\\magecity.png"));
            source= makeBufferedImage(source2);
                
        } catch (IOException ex) {
            System.out.println("problem med sprite()!!");
            //Logger.getLogger(ZeGame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        Image[] sprites = new Image[antall];
        Image sprite;
        int z = 0;
        System.out.println("height: "+source.getHeight());
        System.out.println("width: "+source.getWidth());
        for (int y = 0; y < source.getHeight()-32; y += 32) {
            
            for (int x = 0; x < source.getWidth(); x += 32) {
              
                System.out.println("x: "+x+" y: "+y);
               
                    sprites[z] = SwingFXUtils.toFXImage(source.getSubimage(x, y, 32, 32), null);
                    System.out.println("count: "+z);
                    z++;
              
            
                
            }
        }
        return sprites;
    }
    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(900, 900);
        Image[] bilder = new Image[360];
        bilder = getSprites(360);
        int[] gameboard = new int[]{
        231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,11,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,99,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,107,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,231,
        231,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,284,231,
        231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231,231};

        
        root.setStyle("-fx-background-color: red");
        TilePane grid = new TilePane(); 
        Rectangle[][] test = new Rectangle[30][30];
        int count=0;
        BufferedImage testbilde = null;
        Image image = null;
        int gbValue;
        //bakgrunn
        for(int i=0; i<test.length;i++){
           for(int j=0; j < test.length; j++){
               Rectangle tile = new Rectangle(30,30);
                tile.setX(j * 30);
                tile.setY(i * 30);
                tile.setFill(new ImagePattern(bilder[81]));
                root.getChildren().add(tile);
           }
        }
        for(int i=0; i<test.length;i++){
           for(int j=0; j < test.length; j++){
                if(gameboard[count]>0){
                    gbValue = gameboard[count]-1;
                }else{ gbValue=65;}

                testbilde = SwingFXUtils.fromFXImage(bilder[gbValue], null);
                image = SwingFXUtils.toFXImage(testbilde, null);

               
                Rute tile = new Rute();
               
                tile.GameTile(image, 30, 30);
                tile.setId( gbValue );
//                tile.setX(j * 30);
//                tile.setY(i * 30);


                //tile.setFill(new ImagePattern(image));
                test[i][j] = tile.getTile();
                System.out.println("id: "+tile.getId());
                addTile(tile, j*30, i*30);
                //root.getChildren().add(tile.getTile());
                count++;  
           }     
        }   
        player = new Player();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }
    private void addTile(GameObject rute, double x, double y) {
        ruter.add(rute);
        rute.getView().setTranslateX(x-30);
        rute.getView().setTranslateY(y-30);
        root.getChildren().add(rute.getView());
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
            }
        }
        for (GameObject rute : ruter) {
            int id = rute.getId();
            System.out.println(id);
           if(id == 230){
                if (rute.isColliding(player)) {
                    player.setVelocity( new Point2D(-player.getVelocity().getX() , -player.getVelocity().getY()));
                    
                    break;
                    //root.getChildren().removeAll(bullet.getView(), enemy.getView());
                
            }
           }
        }
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);
        
        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update();

        if (Math.random() < 0.02) {
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }
    }

    private static class Player extends GameObject {
        Player() {
            super(new Rectangle(40, 20, Color.BLUE));
        }
    }

    private static class Enemy extends GameObject {
        Enemy() {
            super(new Circle(15, 15, 15, Color.RED));
        }
    }
    
    private static class Rute extends GameObject {
        Rute() {
                    super(new Rectangle(30, 30));
        }
    }

    private static class Bullet extends GameObject {
        Bullet() {
            super(new Circle(5, 5, 5, Color.BROWN));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            }
        });
        stage.show();
    }
     private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(200, 200);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(72));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);


        }
   }
    public static void main(String[] args) {
        launch(args);
    }
}