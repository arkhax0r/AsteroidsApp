/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroidsapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class GameObject {

    Node view;
    Node lastview;
    private Point2D velocity = new Point2D(0, 0);
    private Rectangle tile;
    private boolean alive = true;
    private boolean collision = false;
    private boolean nocollision = true;
    private boolean release = false;
    private boolean player = false;
    private int id;
    long gameTime = System.currentTimeMillis();
    
    
    public GameObject(Node view) {
        this.view = view;
    }
    public Node getGameObject(){
        return this.view;
    }
    public void GameTile(Image z, int x, int y){
        this.tile = new Rectangle(x,y);
        this.tile.setFill(new ImagePattern(z)); 
        this.tile.setTranslateX(x);
        this.tile.setTranslateY(y);
        this.view = this.tile;
    }

    public void setId(int id){
        this.id = id;
    }
    public void setPlayer(){
        this.player=true;
    }
    public int getId(){
        return this.id;
    }
    public Rectangle getTile(){
        return this.tile;
    }
    
    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }
    public void setView(Node x){
        this.view = x;
    }
    public void lastView(){
        if(this.collision == false){
            this.lastview  = view; 
        }
    }
    public Node getLastView() {
        return lastview;
    }
    public boolean isAlive() {
        return alive;
    }
    
    public boolean isDead() {
        return !alive;
    }
    

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
  
    public double getRotate() {
        return view.getRotate();
    }
  
    public void rotateRight() {
        view.setRotate(view.getRotate() + 10);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 10);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(GameObject other) {
//       System.out.println("collision");
//        this.release = true;
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent()); 
    }
    public void setCollision(){
        this.collision=false;
    }
    public void moveLeft(){
        setVelocity(new Point2D(-6,0));
    }
    public void moveRight(){
        setVelocity(new Point2D(6,0));
    }
    public void moveJump(){
        setVelocity(new Point2D(0,-6));
    }
  
    public void gravity(){
        setVelocity(new Point2D(0,2));
    }
    
}