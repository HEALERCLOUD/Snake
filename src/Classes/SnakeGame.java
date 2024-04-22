package Classes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Timer;
import javax.swing.*;
import javax.swing.Timer;



public class SnakeGame extends JPanel implements ActionListener,KeyListener{
    private class Tile{
        int x;
        int y;

        private Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
//    int i=1;
    Random random;
    int boardWidth;
    int boardHeight;
    final int DELAY = 100;
    final int tileSize = 25;
    Tile snakeHead;//snake
    ArrayList<Tile> snakeBody;
    Tile food;//food
    Timer gameLoop; //game logic

    int velocityX;
    int velocityY;
    public SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        random = new Random();
        snakeHead = new Tile(randomLocation(),randomLocation());
        snakeBody = new ArrayList<Tile>();
//        snakeBody.add(snakeHead);
        food = new Tile(randomLocation(),randomLocation());

        gameLoop = new Timer(DELAY,this);
        gameLoop.start();

        velocityX = 0;
        velocityY = 0;
    }
    //override  paintComponent() method
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid
//        g.setColor(Color.pink);
//        g.drawLine(1,2,70+ i++, 93);

        g.setColor(Color.white);
        for (int i=0;i<boardHeight/tileSize;i++){
            g.drawLine(i*tileSize,0,i*tileSize,boardHeight);
            g.drawLine(0,i*tileSize,boardWidth,i*tileSize );
        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x  * tileSize,food.y * tileSize,tileSize,tileSize);


        //snake
        g.setColor(Color.blue);
        g.fillRect(snakeHead.x * tileSize /*+ fp()*/,snakeHead.y * tileSize,tileSize,tileSize);
        for(int i=0;i<snakeBody.size();i++){
            g.setColor(Color.blue);
            g.fillRect(snakeBody.get(i).x * tileSize, snakeBody.get(i).y * tileSize,tileSize,tileSize);
        }
    }
    private void placeFood(){
        food.x = randomLocation();
        food.y = randomLocation();
    }
    private int randomLocation(){ return random.nextInt(boardWidth/tileSize); } //this will give as a random location for food and also for snake head
    private void move(){

        for(int i=snakeBody.size()-1;i>=0;i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //eating food
        if(collision(snakeHead,food)){
            snakeBody.add(new Tile(food.x,food.y));
            placeFood();
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }
    private boolean collision(Tile tile1,Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityX = 1;
            velocityY = 0;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
    }
    // we do not need those 2
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
