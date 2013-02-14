import java.util.*;

/* matrice in care memorez pozitiile libere unei stari */
public class FreeCells {
	private boolean[][] free;
	private int n;
	private int m;
	
	/* constructor care construieste matricea din vectorul de masini */
	public FreeCells(int n, int m, ArrayList<Automobile> automobiles) {
		this.n = n;
		this.m = m;
		free = new boolean[n+1][m+1];
		for(int i = 1 ; i <= n ; i ++)
			for(int j = 1 ; j<= m ; j ++) {
				free[i][j] = true;
				if(i == 1 || i == n)
					free[i][j] = false;
				if(j == 1 || j == m)
					free[i][j] = false;
			}
		
		for(int i = 0 ; i < automobiles.size(); i ++) {
			Position pos = automobiles.get(i).getPosition();
			switch(automobiles.get(i).getType().type) {
			case 0:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()][pos.getOy()+1] = false;
				break;
			case -1:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()][pos.getOy()+1] = false;
				free[pos.getOx()][pos.getOy()+2] = false;
				break;
			case 1:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()][pos.getOy()+1] = false;
				break;
			case 3:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()][pos.getOy()+1] = false;
				free[pos.getOx()][pos.getOy()+2] = false;
				break;
			case 5:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()][pos.getOy()+1] = false;
				free[pos.getOx()][pos.getOy()+2] = false;
				free[pos.getOx()][pos.getOy()+3] = false;
				break;
			case 2:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()+1][pos.getOy()] = false;
				break;
			case 4:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()+1][pos.getOy()] = false;
				free[pos.getOx()+2][pos.getOy()] = false;
				break;
			case 6:
				free[pos.getOx()][pos.getOy()] = false;
				free[pos.getOx()+1][pos.getOy()] = false;
				free[pos.getOx()+2][pos.getOy()] = false;
				free[pos.getOx()+3][pos.getOy()] = false;
				break;
			}
		}
	}
	
	/* returneaza daca pozitia pos este libera */
	public boolean isFree(Position pos) {
		int x = pos.getOx();
		int y = pos.getOy();
		if(x >= 1 && x <= n && y >= 1 && y<= m)
			return free[pos.getOx()][pos.getOy()];
		else
			return false;
	}
	
	/* returneaza numarul de celule libere spre dreaptea de la pos */
	public int getFreeCellsR(Position pos) {
		if(pos.getOy() == m-1 )
			return 0;

		int nr = 0;
		for(int i = pos.getOy() + 1 ; i < m ; i ++) {
			if(free[pos.getOx()][i] == false)
				break;
			nr ++;
		}
		return nr;
	}
	
	/* returneaza numarul de celule libere spre stanga de la pos */
	public int getFreeCellsL(Position pos) {
		if(pos.getOy() == 2)
			return 0;
		
		int nr = 0;
		for(int i = pos.getOy()-1 ; i >= 2 ; i --) {
			if(free[pos.getOx()][i] == false)
				break;
			nr++;
		}
		return nr;
	}
	
	/* returneaza numarul de celule libere spre sus de la pos */
	public int getFreeCellsU(Position pos) {
		if(pos.getOx() == 2)
			return 0;
		
		int nr = 0;
		for(int i = pos.getOx() -1 ; i >=2 ; i --) {
			if(free[i][pos.getOy()] == false)
				break;
			nr++;
		}
		return nr;
	}
	
	/* returneaza numarul de celule libere spre jos de la pos */
	public int getFreeCellsD(Position pos) {
		if(pos.getOx() == m-1)
			return 0;
		
		int nr = 0;
		for(int i = pos.getOx() +1 ; i < m ; i ++) {
			if(free[i][pos.getOy()] == false)
				break;
			nr++;
		}
		return nr;
	}
}
