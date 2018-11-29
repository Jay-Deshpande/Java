// This program manages graphical tiles displayed in a window allowing
// the user to click on tiles.

import java.util.*;
import java.awt.*;

public class TileManager {
   private ArrayList<Tile> tileList;
   
   // Initializes a new list
   public TileManager() {
      tileList = new ArrayList<Tile>();
   }
   
   // Adds the given tile to the end of the list
   // rect: given tile
   public void addTile(Tile rect) {
      tileList.add(rect);
   }
   
   // Draws all of the tiles in the list
   // g: Graphics object used by DrawingPanel
   public void drawAll(Graphics g) {
      for (int i = 0; i < tileList.size(); i++) {
         tileList.get(i).draw(g);
      }
   }
   
   // Moves the topmost clicked tile to the end of the list
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   public void raise(int x, int y) {
      raiseOrLower(x, y, tileList.size() - 1);
   }
   
   // Moves the topmost clicked tile to the beginning of the list
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   public void lower(int x, int y) {
      raiseOrLower(x, y, 0);
   }
   
   // Deletes the topmost clicked tile from the list
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   public void delete(int x, int y) {
      int tileIndex = isTileClicked(x, y);
      if (tileIndex != -1) {
         tileList.remove(tileIndex);
      }
   }
   
   // Deletes all tiles containing mouseclick coordinates from the list
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   public void deleteAll(int x, int y) {
      for (int i = 0; i < tileList.size(); i++) {
         delete(x, y);
      }
   }
   
   // Randomly reorders tiles in the list
   // Moves every tile in list to a random position on the window
   // width: width of the DrawingPanel window
   // height: height of the DrawingPanel window
   public void shuffle(int width, int height) {
      Collections.shuffle(tileList);
      Random r = new Random();
      for (int i = 0; i < tileList.size(); i++) {
         Tile currentTile = tileList.get(i);
         int maxX = width - currentTile.getWidth();
         int maxY = height - currentTile.getHeight();
         currentTile.setX(r.nextInt(maxX));
         currentTile.setY(r.nextInt(maxY));
      }
   }
   
   // Returns the list index of a clicked tile
   // If no tile was clicked returns -1
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   private int isTileClicked(int x, int y) {
      for (int i = tileList.size() - 1; i >= 0; i--) {
         Tile currentTile = tileList.get(i);
         int tileX = currentTile.getX();
         int tileY = currentTile.getY();
         int maxX = tileX + currentTile.getWidth();
         int maxY = tileY + currentTile.getHeight();
         if (tileX <= x && x <= maxX && tileY <= y && y <= maxY) {
            return i;
         }
      }
      return -1;
   }
   
   // Puts tile that user clicked to the top or bottom of the list
   // x: x coordinate of user mouseclick
   // y: y coordinate of user mouseclick
   // i: index of the list where tile is put
   private void raiseOrLower(int x, int y, int i) {
      int tileIndex = isTileClicked(x, y);
      if (tileIndex != -1) {
         Tile currentTile = tileList.get(tileIndex);
         tileList.remove(tileIndex);
         tileList.add(i, currentTile);
      }
   }
}