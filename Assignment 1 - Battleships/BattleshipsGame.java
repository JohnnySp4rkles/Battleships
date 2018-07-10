/*
Written by:	Maeve Carr
Date:
Desc: 
Filename:
*/

/////NO COMMENTS - FIGURE IT OUT -- OKAY!!


import java.util.*;

public class BattleshipsGame
{
   // INITIALISE VARIABLES //
   private BattleshipCell[][] grid; // DECLARING A 2DIM ARRAY CALLED @grid OF TYPE BattleshipCell//
   private int lives;
   private int hits; 
   private int noOfShips; 
	
	// CONSTRUCTOR //
   public BattleshipsGame() // DEFAULT CONSTRUCTOR //
   {
      grid = new BattleshipCell[4][4]; // SETTING THE SIZE OF THE 2DIM ARRAY -- 4 X 4 //
   	
      initGrid(); // CALLING METHOD initGrid() //
      lives = 7;
      setNoOfShips(6); // CALLING METHOD setNoOfShips() //
   }
   
   public BattleshipsGame(int livesIn, int shipsIn) // @PARAM MANUALLY SET NO_OF_LIVES || @PARAM MANUALLY SET NO_OF_SHIPS //
   {
      grid = new BattleshipCell[4][4];
   	
      initGrid(); // CALLING METHOD initGrid() //
      lives = livesIn;
      setNoOfShips(shipsIn);
   }

   // METHODS //
   public void initGrid() // NESTED FOR LOOP TO CREATE GRID //
   {
      for(int r = 0; r < 4; r++) // r = row || c = column //
         for(int c = 0; c < 4; c++)
            grid[r][c] = new BattleshipCell(); // EACH GRID IS A REFERENCE TO AN OBJECT OF TYPE: BattleshipCell //
   }
   
   public void showGrid()
   {
      for(int r = 0; r < 4; r++)
      {
         for(int c = 0; c < 4; c++)
            System.out.print(grid[r][c] +" "); // PRINTS THE CONTENTS OF EACH GRID TO CONSOLE //
         System.out.println();
      }
   }
	public int getNOOfShips()
   {
      return noOfShips;
   }
   public void setNoOfShips(int noOfShipsIn) // @PARAM NUMBER OF SHIPS TO BE IN THE GAME //
   {
      Random noGen = new Random();
      noOfShips=noOfShipsIn;
      int count = 0;	
      do
      {
         int r = noGen.nextInt(4); // RANDOM NUMBER FOR ROW //
         int c = noGen.nextInt(4); // RANDOM NUMBER FOR COLUMN //
         if(!checkIfShip( r,  c)) // checkIfShip = METHOD. IF THE OBJECT IN R && C IS NOT A SHIP OBJ, DO THIS //
         {
            grid[r][c].setToShip(); //setToShip = METHOD. SET THE CURRENT GRID TO A SHIP.
            count++; 
         }
      }while(count < noOfShips); // LOOP UNTIL COUNT IS LESS THAN THE NUMBER OF SHIPS REQ //
   }
	
   public boolean checkIfShip(int r, int c) // @PARAM ROW INT || @PARAM COLUMN INT //
   {
      return grid[r][c].isShip();
   }
	
   public int getLives()
   {
      return lives;
   }
	
   public int getHits()
   {
      return hits;
   }	
	
   public String shoot(int r, int c)  
   {												
      String s; 
      
      if(grid[r][c].isHit()) // isHit METHOD FROM BattleShipCell || IF THIS CELL IS ALREADY CHOSEN //
         s = "Already chosen";  
      else
      {
         if(grid[r][c].isShip()) // IF THIS CELL IS A SHIP //
         {
            s = "HIT - ship sunk!";
            hits++;
         }
         else // IF NOT A SHIP THEN MISS //
         {
            s = "Miss!";
            lives--;
         }
         grid[r][c].setToHit(); // SET THAT CELL TO HIT //
      }
      return s;
   }
}//end class



