/*
Written by:	Maeve Carr
Date:
Desc: 
Filename:
*/

////NO COMMENTS////////////

public class BattleshipCell
{
	
   boolean ship; // DEFAULT IS FALSE //
   boolean hit; // DEFAULT IS FALSE //
	
   public boolean isShip()
   {
      return ship;
   }
	
   public boolean isHit()
   {
      return hit;
   }
	
   public void setToShip()
   {
      ship = true;
   }

   public void setToHit()
   {
      hit = true;
   }
	
   public String toString()
   {
      String s;
   		
      if(ship)
         if(hit)
            s = "sunk";
         else
            s = "ship";
      else
         if(hit)
            s = "miss";
         else
            s = "empty";
   	
      return s;
   }
}