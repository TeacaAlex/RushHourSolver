import java.io.*;
import java.util.*;

public class Board {
	private boolean isFinished;
	private ArrayList<Automobile> automobiles;
	public int n;
	public int m;
	char[][] board;
	Board prevBoard = null;
	int f = 0;
	int g = 0;
	
	/* constructor care construieste un board din date membru */
	public Board(ArrayList<Automobile> automobiles, char[][] board, int n, int m, boolean isFinished) {
		this.automobiles = automobiles;
		this.board = board;
		this.n = n;
		this.m = m;
		this.isFinished = isFinished;
	}
	
	/* constructor care construieste un board dintr-un string care reprezinta tabla */
	public Board(String in) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(in));
			String line = br.readLine();
			String nr1 = line.split(" ")[0];
			String nr2 = line.split(" ")[1];
			n = Integer.parseInt(nr1);
			m = Integer.parseInt(nr2);
			
			/* citesc tabla intr-o matrice[][] */
			board = new char[n+1][m+1];
			br.readLine();
			int c;
			int i = 1;
			int j = 1;
			while((c = br.read()) != -1) {
				if((char)c == '\n') {
					i++;
					j = 1;
				}
				else if((char)c == ' ') {
					
				}
				else {
					board[i][j] = (char)c;
					j++;
				}
			}
			br.close();
			
		}
		catch(Exception e){}
		
		/* tranversez tabla pe verticala si orizontala pentru a construi vectorul de masini */
		automobiles = new ArrayList<Automobile>();
		isFinished = true;
		for(int i = 2 ; i < n ; i ++) {
			int j = 2;
			while(j < m) {
				if(board[i][j] == '<' && board[i][j+1] == '>') {
					Automobile auto = new Automobile(new AutomobileType(1), new Position(i, j));
					automobiles.add(auto);
					j = j + 2;
				}//type = <>
				else if(board[i][j] == '<' && board[i][j+2] == '>') {
					Automobile auto = new Automobile(new AutomobileType(3), new Position(i, j));
					automobiles.add(auto);
					j = j + 3;
				}//type = <->
				else if(board[i][j] == '<' && board[i][j+1] == '-' && board[i][j+2] == '-' && board[i][j+3] == '>') {
					Automobile auto = new Automobile(new AutomobileType(5), new Position(i, j));
					automobiles.add(auto);
					j = j + 4;
				}//type = <-->
				else if(board[i][j] == '?' && board[i][j+1] == '?' && board[i][j+2] == '?') {
					Automobile auto = new Automobile(new AutomobileType(-1), new Position(i, j));
					automobiles.add(auto);
					j = j + 3;
					isFinished = false;				
				}
				else if(board[i][j] == '?' && board[i][j+1] == '?') {
					Automobile auto = new Automobile(new AutomobileType(0), new Position(i, j));
					automobiles.add(auto);
					j = j + 2;
					isFinished = false;
				}//tpye = ??
				else {
					j++;
				}
			}
		}
		
		for(int j = 2 ; j < m ; j ++) {
			int i = 2;
			while(i < n) {
				if(board[i][j] == '^' && board[i+1][j] == 'v') {
					Automobile auto = new Automobile(new AutomobileType(2), new Position(i, j));
					automobiles.add(auto);
					i = i + 2;
				}//type = ^v
				else if(board[i][j] == '^' && board[i+2][j] == 'v') {
					Automobile auto = new Automobile(new AutomobileType(4), new Position(i, j));
					automobiles.add(auto);
					i = i + 3;
				}//type = ^|v
				else if(board[i][j] == '^' && board[i+1][j] == '|' && board[i+2][j] == '|' && board[i+3][j] == 'v') {
					Automobile auto = new Automobile(new AutomobileType(6), new Position(i, j));
					automobiles.add(auto);
					i = i + 4;
				}//type = ^||v
				else {
					i++;
				}
			}
		}
	}
	
	/* intoarce vectorul de masini caracterestic unei stari */
	public ArrayList<Automobile> getAutomobiles() {
		return automobiles;
	}
	
	/* intoarce toate mutarile posibile unei stari */
	public ArrayList<Move> getMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		if(isFinished == false) {
			FreeCells freecell = new FreeCells(n, m, automobiles);
			for(int i = 0 ; i < automobiles.size(); i ++) {
				addMoves(i, moves, freecell);
			}
		}
		return moves;
	}
	
	/* aplica o mutare starii curente si intoarce noua stare */
	public Board applyMove(Move move) {
		int index = -1;
		for(int i = 0 ; i < automobiles.size() ; i ++)
			if(move.getStart().getOx() == automobiles.get(i).getPosition().getOx()
				&& move.getStart().getOy() == automobiles.get(i).getPosition().getOy()) {
				index = i;
				break;
			}
		
		if(move.getIsFinished() == true) {
			ArrayList<Automobile> newAutomobiles = new ArrayList<Automobile>(automobiles);
			Automobile redCar = automobiles.get(index);
			newAutomobiles.remove(index);
			char[][] nboard = new char[n+1][m+1];
			for(int i = 1 ;i <= n ; i ++)
				for(int j = 1 ; j <= m ; j ++)
					nboard[i][j] = board[i][j];
			
			if(redCar.getType().type == 0) {
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() + 1] = '-';
			}
			else {
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() + 1] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() + 2] = '-';
			}
			
			Board newBoard = new Board(newAutomobiles, nboard, n, m, true);
			return newBoard;
		}
		else {
			ArrayList<Automobile> newAutomobiles = new ArrayList<Automobile>(automobiles);
			Automobile oldAuto = automobiles.get(index);
			newAutomobiles.remove(index);
			newAutomobiles.add(index, new Automobile(oldAuto.getType(), move.getStop()));
			char[][] nboard = new char[n+1][m+1];
			for(int i = 1 ;i <= n ; i ++)
				for(int j = 1 ; j <= m ; j ++)
					nboard[i][j] = board[i][j];
			switch(oldAuto.getType().type) {
			case 0:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() +1] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '?';
				nboard[move.getStop().getOx()][move.getStop().getOy()+1] = '?';
				break;
			case -1:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() +1] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() +2] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '?';
				nboard[move.getStop().getOx()][move.getStop().getOy()+1] = '?';
				nboard[move.getStop().getOx()][move.getStop().getOy()+2] = '?';
				break;
			case 1:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy() +1] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '<';
				nboard[move.getStop().getOx()][move.getStop().getOy()+1] = '>';
				break;
			case 2:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+1][move.getStart().getOy()] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '^';
				nboard[move.getStop().getOx() +1][move.getStop().getOy()] = 'v';
				break;
			case 3:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy()+1] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy()+2] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '<';
				nboard[move.getStop().getOx()][move.getStop().getOy()+1] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()+2] = '>';
				break;
			case 4:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+1][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+2][move.getStart().getOy()] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '^';
				nboard[move.getStop().getOx()+1][move.getStop().getOy()] = '|';
				nboard[move.getStop().getOx()+2][move.getStop().getOy()] = 'v';
				break;
			case 5:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy()+1] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy()+2] = '-';
				nboard[move.getStart().getOx()][move.getStart().getOy()+3] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '<';
				nboard[move.getStop().getOx()][move.getStop().getOy()+1] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()+2] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()+3] = '>';
				break;
			case 6:
				nboard[move.getStart().getOx()][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+1][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+2][move.getStart().getOy()] = '-';
				nboard[move.getStart().getOx()+3][move.getStart().getOy()] = '-';
				nboard[move.getStop().getOx()][move.getStop().getOy()] = '^';
				nboard[move.getStop().getOx()+1][move.getStop().getOy()] = '|';
				nboard[move.getStop().getOx()+2][move.getStop().getOy()] = '|';
				nboard[move.getStop().getOx()+3][move.getStop().getOy()] = 'v';
				break;
			}
			
			Board newBoard = new Board(newAutomobiles, nboard, n, m, false);
			return newBoard;
		}
	}
	
	/* returneaza daca starea este terminata */
	public boolean getIsFinished() {
		return isFinished;
	}
	
	/* seteaza o stare ca finala */
	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	
	/* adauga vectorului moves mutarile posibile masinii i */
	public void addMoves(int i, ArrayList<Move> moves, FreeCells freecell) {
		AutomobileType type = automobiles.get(i).getType();
		Position pos = automobiles.get(i).getPosition();
		char direction ;
		
		if(type.type == 0 || type.type == -1 || type.type == 1 || type.type == 3 || type.type == 5) {
			//int size = (type.type == 3) ? 3 : 2;
			int size;
			if(type.type == 0 || type.type == 1)
				size = 2;
			else if(type.type == 3 || type.type == -1)
				size = 3;
			else
				size = 4;
			
			int nrFreeR = freecell.getFreeCellsR(new Position(pos.getOx(), pos.getOy() + size -1));
			if(type.type == 0 && pos.getOy() == m-size)
				moves.add(new Move(pos, 'R'));
			if(type.type == -1 && pos.getOy() == m-size)
				moves.add(new Move(pos, 'R'));
			
			for(int k = 1 ; k <= nrFreeR ; k ++)
				moves.add(new Move(pos, new Position(pos.getOx(), pos.getOy() + k), false, 'R'));
			
			int nrFreeL = freecell.getFreeCellsL(pos);
			for(int k = 1 ; k <= nrFreeL ; k ++)
				moves.add(new Move(pos, new Position(pos.getOx(), pos.getOy() -k), false, 'L'));
		}
		else {
			//int size = (type.type == 4) ? 3 :2;
			int size;
			if(type.type == 4)
				size = 3;
			else if(type.type == 2)
				size = 2;
			else
				size = 4;
			
			int nrFreeU = freecell.getFreeCellsU(pos);
			for(int k = 1 ; k <= nrFreeU ; k ++)
				moves.add(new Move(pos, new Position(pos.getOx() - k, pos.getOy()), false, 'U'));
			
			int nrFreeD = freecell.getFreeCellsD(new Position(pos.getOx() + size-1, pos.getOy()));
			for(int k = 1 ; k <= nrFreeD ; k ++)
				moves.add(new Move(pos, new Position(pos.getOx() + k, pos.getOy()), false, 'D'));
		}
	}
	
	
	/* returneaza reprezentarea starii */
	public String toString() {
		String s = "";
		for(int i = 1 ; i <= n ; i ++) {
			for(int j = 1; j <= m ; j ++)
				s = s + board[i][j] + " ";
			s = s + "\n";
		}
		return s;
	}
	
	/* seteaza parintele starii curente */
	public void setPrevBoard(Board prevBoard) {
		this.prevBoard = prevBoard;
	}
	
	/* returneaza parintele starii */
	public Board getPrevBoard() {
		return prevBoard;
	}
	
	

	/* verifica egalitatea a doua stari */
	@Override
	public boolean equals(Object other) {
		if (this == other)
            return true;
        
        Board otherBoard = (Board)other;
        
        for(int i = 1 ; i <= n ; i ++)
        	for(int j = 1 ; j <= m ; j ++)
        		if(board[i][j] != otherBoard.board[i][j])
        			return false;
        return true;	
	}

	/* intoarce hashul unei stari */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}