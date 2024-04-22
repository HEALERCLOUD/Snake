package Classes;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;



public class SnakeGame extends JPanel{

    private class Tile{
        int x;
        int y;

        private Tile(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    final int tileSize = 25;

    Tile snakeHead;
    public SnakeGame(int boardWidth,int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.black);

        snakeHead = new Tile(5,5);
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
        //snake
        g.setColor(Color.blue);
        g.fillRect(snakeHead.x * tileSize,snakeHead.y * tileSize,tileSize,tileSize);
//        g.drawLine(25,0,25,600);
    }
}
