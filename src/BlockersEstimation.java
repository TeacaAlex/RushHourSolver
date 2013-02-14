import java.util.ArrayList;

/* euristica folosita de AStar calculeaza numarul de masini 
 * intalnite pe orizontala aflata in calea masinii RED,le pune intr-o coada, 
 * dupa care calculeaza pentru fiecare numarul de masini care sta in calea acestora
 */
public class BlockersEstimation implements AStarHeuristic {
	public int distanceToGoal(Board board) {
		
		if(board.getIsFinished()) {
			return 0;
		}
		
		//determin pozitia masinii RED
		Position redCar = null;
		ArrayList<Automobile> automobiles = board.getAutomobiles();
		int pos = -1;
		for(int i = 0 ; i < automobiles.size() ; i ++) {
			if(automobiles.get(i).getType().type == 0 || automobiles.get(i).getType().type == -1) {
				redCar = automobiles.get(i).getPosition();
				pos = i;
				break;
			}
		}
		
		
		int x = redCar.getOx();
		int y = redCar.getOy();
		int nr = 0;
		
		//calculez marimea ?? sau ???
		int size;
		if(automobiles.get(pos).getType().type == 0)
			size = 2;
		else
			size = 3;
		
		//innumar masinile aflate in fata ei, si le pun intr un vector
		ArrayList<Automobile> blockingCars = new ArrayList<Automobile>();
		FreeCells freeCells = new FreeCells(board.n, board.m, automobiles);
		for(int i = y + size ; i < board.m ; i ++)
			if(!freeCells.isFree(new Position(x, i))) {
				Automobile block = getCar2(board, x, i);
				if(block != null) {
					nr ++;
					blockingCars.add(block);
				}
			}
		
		for(int i = 0 ; i < blockingCars.size(); i ++) {
			//pentru fiecare masina, calculez numarul de masini care 
			//le blocheaza si insumez toate aceste valori
			Automobile auto = blockingCars.get(i);
			int type = auto.getType().type;
			int x2 = auto.getPosition().getOx();
			int y2 = auto.getPosition().getOy();
			if(type == 2) {
				if(x2 == x) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position down = new Position(x2+2, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2)) || freeCells.isFree(down))
						nr = nr + 0;
					else 
						nr = nr + 1;
				}
				else {
					Position up = new Position(x2-1, y2);
					Position down1 = new Position(x2+2, y2);
					Position down2 = new Position(x2+3, y2);
					if((freeCells.isFree(down1) && freeCells.isFree(down2)) || freeCells.isFree(up))
						nr = nr + 0;
					else 
						nr = nr + 1;
				}
			}
			if(type == 4) {
				
				if(x2 == x) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position up3 = new Position(x2-3, y2);
					Position down = new Position(x2+3, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2) && freeCells.isFree(up3)) || freeCells.isFree(down))
						nr = nr + 0;
					else
						nr = nr + 1;
				}
				else if(x2 == x-1) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position down1 = new Position(x2+3, y2);
					Position down2 = new Position(x2+4, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2)) || (freeCells.isFree(down1) && freeCells.isFree(down2)))
						nr = nr + 0;
					else if(freeCells.isFree(up1) || freeCells.isFree(up2))
						nr = nr + 1;
					else if(freeCells.isFree(down1) || freeCells.isFree(down2))
						nr = nr + 1;
					else
						nr = nr + 2;
				}
				else {
					Position up = new Position(x2-1, y2);
					Position down1 = new Position(x2+3, y2);
					Position down2 = new Position(x2+4, y2);
					Position down3 = new Position(x2+5, y2);
					if((freeCells.isFree(down1) && freeCells.isFree(down2) && freeCells.isFree(down3)) || freeCells.isFree(up))
						nr = nr + 0;
					else
						nr = nr + 1;
				}
			}
			if(type == 6) {
				if(x2 == x) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position up3 = new Position(x2-3, y2);
					Position up4 = new Position(x2-4, y2);
					Position down = new Position(x2+4, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2) && freeCells.isFree(up3) && freeCells.isFree(up4)) || freeCells.isFree(down))
						nr = nr + 0;
					else
						nr = nr + 1;
				}
				else if(x2 == x-1) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position up3 = new Position(x2-3, y2);
					Position down1 = new Position(x2+4, y2);
					Position down2 = new Position(x2+5, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2) && freeCells.isFree(up3)) || (freeCells.isFree(down1) && freeCells.isFree(down2)))
						nr = nr + 0;
					else if(freeCells.isFree(down1) || freeCells.isFree(down2))
						nr = nr + 1;
					else if(freeCells.isFree(up1) || freeCells.isFree(up2) || freeCells.isFree(up3))
						nr = nr + 1;
					else
						nr = nr + 2;
				}
				else if(x2 == x-2) {
					Position up1 = new Position(x2-1, y2);
					Position up2 = new Position(x2-2, y2);
					Position down1 = new Position(x2+4, y2);
					Position down2 = new Position(x2+5, y2);
					Position down3 = new Position(x2+6, y2);
					if((freeCells.isFree(up1) && freeCells.isFree(up2)) || (freeCells.isFree(down1) && freeCells.isFree(down2) && freeCells.isFree(down3)))
						nr = nr + 0;
					else if (freeCells.isFree(up1) || freeCells.isFree(up2))
						nr = nr + 1;
					else if (freeCells.isFree(down1) || freeCells.isFree(down2) || freeCells.isFree(down3))
						nr = nr + 1;
					else
						nr = nr + 2;
				}
				else {
					Position up = new Position(x2-1, y2);
					Position down1 = new Position(x2+4, y2);
					Position down2 = new Position(x2+5, y2);
					Position down3 = new Position(x2+6, y2);
					Position down4 = new Position(x2+7, y2);
					if((freeCells.isFree(down1) && freeCells.isFree(down2) && freeCells.isFree(down3) && freeCells.isFree(down4)) || freeCells.isFree(up))
						nr = nr + 0;
					else
						nr = nr + 1;
				}
				
				
			}
		}
		//return 0;
		return nr;	
	}
	
	/* retunreaza masina aflata pe pozita (i, j) */
	public Automobile getCar2(Board board, int i, int j) {
		ArrayList<Automobile> automobiles = board.getAutomobiles();
		for(int k = 0 ; k < automobiles.size() ; k ++) {
			int type = automobiles.get(k).getType().type;
			int x = automobiles.get(k).getPosition().getOx();
			int y = automobiles.get(k).getPosition().getOy();
			switch(type) {
			case 1:
				if(i == x && (j == y || j == y+1))
					return automobiles.get(k);
				break;
			case 2:
				if(j == y && (i == x || i == x+1))
					return automobiles.get(k);
				break;
			case 3:
				if(i == x && (j == y || j == y+1 || j == y+2))
					return automobiles.get(k);
				break;
			case 4:
				if(j == y && (i == x || i == x+1 || i == x+2))
					return automobiles.get(k);
				break;
			case 5:
				if(i == x && (j == y || j == y+1 || j == y+2 || j == y+3))
					return automobiles.get(k);
				break;
			case 6:
				if(j == y && (i == x || i == x+1 || i == x+2 || i == x+3))
					return automobiles.get(k);
				break;
			}
		}
		return null;
	}
}
