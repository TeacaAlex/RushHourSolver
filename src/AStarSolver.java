import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AStarSolver {
	
	//euristica care este folosita de AStar
	AStarHeuristic h = null;
	
	public AStarSolver (AStarHeuristic h) {
		this.h = h;
	}
	
	/* rezultatul este intors intr-un rezultat de tip AStarResult */
	public class AStarResult {
        public AStarResult(List<Board> solution, int states, int uniqueStates) {
            this.solution = solution;
            this.states = states;
            this.uniqueStates = uniqueStates;
        }
        
        public final List<Board> solution;
        public final int states;
        public final int uniqueStates;
    }
	
	
	
	public AStarResult solve(Board board) {
		// multimea open in care sunt salvate starile neexplorate
		ArrayList<Board> open = new ArrayList<Board>();
		// multimea closed in care sunt salvate starile explorate
		HashSet<Board> closed = new HashSet<Board>();
		int numStates = 1; //numarul de stari prin care trece, practic toate starile expandate
		
		//starea initiala
		Board n0 = board;
		n0.f = h.distanceToGoal(n0);
		n0.g = 0;
		n0.setPrevBoard(null);
		
		if (n0.f == Integer.MAX_VALUE) {
            return null;
        }
		
		open.add(n0);
		
		while(!open.isEmpty()) {
			//extrage starea cea mai buna, dupa f
			Collections.sort(open, new Comp());
			Board nod = open.get(0);
			//il scot din open
			open.remove(0);
			// daca starea e finala return AStarResult
			if(nod.getIsFinished() == true) {
				return new AStarResult(reconstructSolution(nod),
						numStates, open.size() + closed.size());
			}//open + closed reprezinta numarul de stari unice prin care a trecut AStar
			//il bag in closed
			closed.add(nod);
			ArrayList<Move> moves = nod.getMoves();//construiesc toate mutarile posibile
			for(int i = 0 ; i < moves.size(); i ++) {
				//explorez toate starile
				numStates ++;
				Move move = moves.get(i);
				Board succ = nod.applyMove(move);//noua stare
				
				//calculez g si h
				int g_succ = nod.g + 1;
				int f_succ = g_succ + h.distanceToGoal(succ);
				boolean c ;
				boolean o ;
				
				o = open.contains(succ);
				c = closed.contains(succ);
				//daca succ nu este nici in open si nici in closed
				if(c == false && o == false) {//nod nou
					succ.g = g_succ;
					succ.f = f_succ;
					succ.setPrevBoard(nod);
					open.add(succ);//il adaug la open
				}
				else {
					if(g_succ < succ.g) {//verific daca nu am gasit alta cale mai buna
						succ.g = g_succ;//daca da resetez g si f
						succ.f = f_succ;
						succ.setPrevBoard(nod);
						if(c == true) {//daca era in closed il redeschid
							closed.remove(succ);
							open.add(succ);
						}
					}
				}
			}	
		}
		//daca AStar nu a gasit nici o solutie, intorc null
		return new AStarResult(null, numStates, open.size() + closed.size());
	}
	
	/* reconstruieste solutia mergand din parinte in parinte pana la starea initiala */
	private List<Board> reconstructSolution(Board nodFinished) {
        List<Board> solution = new ArrayList<Board>();    
        Board current = nodFinished;
        while (true) {
        	solution.add(0, current);
            Board prev = current.getPrevBoard();
            if (prev == null) {
                break;
            }
            current = prev;
        }
        
        return solution;
    }
	
	/* comparator care compare dupa f = g + h */
	public class Comp implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			Board b1 = (Board)arg0;
			Board b2 = (Board)arg1;
			return b1.f - b2.f;
		}		
	}
	
}
