package Classes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
//import java.util.Timer;
import javax.swing.*;
import javax.swing.Timer;



public class SnakeGame extends JPanel implements ActionListener{

//    int gameTick = 0;
    private class Tile{
        int x;
        int y;

        private Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    Random random;
    int boardWidth;
    int boardHeight;
    final int DELAY = 300;
    final int tileSize = 25;
    Tile snakeHead;//snake
    Tile food;//food
    Timer gameLoop; //game logic
    public SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);

        random = new Random();
        snakeHead = new Tile(randomLocation(),randomLocation());
        food = new Tile(randomLocation(),randomLocation());

        gameLoop = new Timer(DELAY,this);
        gameLoop.start();
    }
    //override  paintComponent() method
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //grid
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
    }
    private int randomLocation(){ return random.nextInt(boardWidth/tileSize); } //this will give as a random location for food and also for snake head
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

//    private int fp(){
//        return gameTick++ * 25;
//    }

}
