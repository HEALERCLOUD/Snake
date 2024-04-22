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
    boolean gameOver = false;
    Random random;
    int boardWidth;
    int boardHeight;
    final int DELAY = 100;
    final int SPEEDER_DELAY = 50;
    final int SPEEDER_DURATION = 5000; //5 seconds in milliseconds

    final int tileSize = 25;
    Tile snakeHead;//snake
    ArrayList<Tile> snakeBody;
    Tile food;//food
    Tile speeder;
    boolean isSpeederOn;
    Timer gameLoop; //game logic

    Timer speederTimer;

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
        speeder = new Tile(randomLocation(),randomLocation());

        gameLoop = new Timer(DELAY,this);
        gameLoop.start();

        speederTimer = new Timer(SPEEDER_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop.setDelay(DELAY);
            }
        });

        isSpeederOn = false;

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
        //food
        g.setColor(Color.red);
        g.fillRect(food.x  * tileSize,food.y * tileSize,tileSize,tileSize);

        //speeder
        g.setColor(Color.magenta);
        g.fillOval(speeder.x * tileSize, speeder.y * tileSize,tileSize,tileSize );

        //snake
        g.setColor(Color.blue);
        g.fillRect(snakeHead.x * tileSize /*+ fp()*/,snakeHead.y * tileSize,tileSize,tileSize);
        for(int i=0;i<snakeBody.size();i++){
            g.setColor(Color.blue);
            g.fillRect(snakeBody.get(i).x * tileSize, snakeBody.get(i).y * tileSize,tileSize,tileSize);
        }

        //draw score
        g.setColor(Color.white);
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(!gameOver) g.drawString("Score : " + String.valueOf(snakeBody.size()),tileSize ,tileSize+tileSize);
        else {
            g.setFont(new Font("Arial",Font.PLAIN,32));
            g.setColor(Color.red);
            g.drawString("Final Score : " + String.valueOf(snakeBody.size()),boardWidth/2 - 100 ,boardHeight/2 - 100);
        }
    }
    private void placeFood(){
        food.x = randomLocation();
        food.y = randomLocation();
    }
    private void placeSpeeder(){
        speeder.x = randomLocation();
        speeder.y = randomLocation();
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
        if(collision(snakeHead,speeder) && !isSpeederOn){
            placeSpeeder();
            gameLoop.setDelay(SPEEDER_DELAY);
            speederTimer.start();
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //collision
        for (Tile tile : snakeBody)
            if (collision(snakeHead, tile)) {
                gameOver = true;
                break;
            }
        if(snakeHead.x*tileSize > boardWidth || snakeHead.y*tileSize > boardHeight
                || snakeHead.x*tileSize < 0 || snakeHead.y*tileSize < 0)
            gameOver = true;
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
        if(gameOver) gameLoop.stop();
    }
}
