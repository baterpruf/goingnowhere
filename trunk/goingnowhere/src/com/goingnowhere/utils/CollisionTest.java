package com.goingnowhere.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CollisionTest {

    static int WALL = 0x000000;
    static Pixmap pixmap;
    public CollisionTest(Pixmap pixmap){
        CollisionTest.pixmap=pixmap;
    }
    public static boolean collision (Rectangle r1, Rectangle r2) {
    if (r1.x < r2.x + r2.width && r1.x + r1.width > r2.x && r1.y < r2.y + r2.height && r1.y + r1.height > r2.y)
        return true;
    else
        return false;
    }
    
    public static boolean wallCollision (Rectangle rect) {
        Gdx.app.log("xy", rect.x+" "+rect.y);
        int a=(CollisionTest.pixmap.getPixel((int)rect.x, 1024-(int)rect.y)>>> 8) & 0xffffff;
        Gdx.app.log("xy", a+" ");
        int b=(pixmap.getPixel((int)(rect.x+rect.width), 1024-(int)rect.y)>>> 8) & 0xffffff;
        int c=(pixmap.getPixel((int)rect.x, 1024-(int)(rect.y+rect.height))>>> 8) & 0xffffff;
        int d=(pixmap.getPixel((int)(rect.x+rect.width), 1024-(int)(rect.y+rect.height))>>> 8) & 0xffffff;
        if (a==WALL || b==WALL || c==WALL || d==WALL){
            return true;
        }else{
            return false;
        }
    }

    public static boolean pointInRectangle (Rectangle r, Vector2 p) {
        return r.x <= p.x && r.x + r.width >= p.x && r.y <= p.y && r.y + r.height >= p.y;
    }
    
    public static boolean pointInRectangle (Rectangle r, float x, float y) {
        return r.x <= x && r.x + r.width >= x && r.y <= y && r.y + r.height >= y;
    }
}