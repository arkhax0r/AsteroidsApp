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
import static java.lang.System.exit;
import static java.lang.System.out;
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
import static javafx.application.Platform.exit;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
    private int scale = 30;
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
        //System.out.println("height: "+source.getHeight());
        //System.out.println("width: "+source.getWidth());
        for (int y = 0; y < source.getHeight()-32; y += 32) {
            
            for (int x = 0; x < source.getWidth(); x += 32) {
              
                //System.out.println("x: "+x+" y: "+y);
               
                    sprites[z] = SwingFXUtils.toFXImage(source.getSubimage(x, y, 32, 32), null);
                    //System.out.println("count: "+z);
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
        Rectangle[][] test = new Rectangle[scale][scale];
        int count=0;
        BufferedImage testbilde = null;
        Image image = null;
        int gbValue;
        //bakgrunn
        for(int i=0; i<test.length;i++){
           for(int j=0; j < test.length; j++){
               Rectangle tile = new Rectangle(scale,scale);
                tile.setX(j * scale);
                tile.setY(i * scale);
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
               
                tile.GameTile(image, scale, scale);
                tile.setId( gbValue );
//                tile.setX(j * 30);
//                tile.setY(i * 30);


                //tile.setFill(new ImagePattern(image));
                test[i][j] = tile.getTile();
                //System.out.println("id: "+tile.getId());
                addTile(tile, j*scale, i*scale);
                //root.getChildren().add(tile.getTile());
                count++; 
                
           }     
        }   
        player = new Player();
        player.setVelocity(new Point2D(1, 0));
        player.setPlayer();
        
        
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
        rute.getView().setTranslateX(x);
        rute.getView().setTranslateY(y);
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
        
            player.lastView();
            Node last = player.getLastView();
            double lastx = player.getLastView().getTranslateX()+1;
            double lasty = player.getLastView().getTranslateY();
            //System.out.println(id);
           if(id == 230 && player.isColliding(rute) ){
               
                    System.out.println("collision");
                    player.setVelocity( new Point2D( 0, 0 )  );
                
                    rute.setCollision();
                    player.setCollision();
                  
                    break;
//                    double playerx = player.getView().getBoundsInParent().getMinX();
//                    double rutex = rute.getView().getBoundsInParent().getMinX();
//                    double rutexmax = rute.getView().getBoundsInParent().getMaxX();
//                    double playery = player.getView().getBoundsInParent().getMinY();
//                    double rutey = rute.getView().getBoundsInParent().getMinY();
////                    System.out.println("collision!: "+rutex);
////                    exit();
//                    System.out.println("playerx: "+playerx+"  rutex: "+rutex);
//                    System.out.println("playery: "+playery+"  rutey: "+rutey);
//                    //System.out.println(player.getVelocity().getX());
//                    //negativ x-retning
//                    if( rutex < playerx && rutex == 0.0 ){
//                        player.setVelocity( new Point2D( 0, player.getVelocity().getY() )  );
//                        player.getView().setTranslateX( rute.getTile().getX() + scale + 1 );
//                    }
//                    if( playerx > rutex && rutexmax == 900 ){
//                        player.setVelocity( new Point2D( 0, player.getVelocity().getY()) );
//                        //player.getView().setTranslateX( 839 );
//                        
//                    }
                    //negativ x-retning
//                    if( rutex < playerx){
//                        player.getView().setTranslateY(rutey+31);
//                    }
                    
                    
                    //player.getView().setLayoutY(80);
                    //break;
                    //root.getChildren().removeAll(bullet.getView(), enemy.getView());
                
            
           }
           
        }
        
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);
        
        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update();

     
    }

    private static class Player extends GameObject {
        Player() {
            super(new Rectangle(30, 30, Color.BLUE));
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
            keyPressed(e);
        });
        stage.show();
    }
    public void keyPressed(KeyEvent arg0) {

    if (arg0.getCode() == KeyCode.RIGHT )
        {
            player.setCollision();
            player.moveRight();
            
        }
    if (arg0.getCode() == KeyCode.LEFT )
        {
            player.getGameObject().s
            player.moveLeft();
            
        }
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