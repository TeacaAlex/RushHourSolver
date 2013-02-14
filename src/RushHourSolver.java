import java.io.*;
import java.util.*;

public class RushHourSolver {
	
	public static void main(String args[]) {
		Board board = new Board(args[0]);
		//AStarSolver rezolva tabla board cu euristica BlockersEstimation
		AStarSolver.AStarResult result = new AStarSolver(new BlockersEstimation()).solve(board);
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
			bw.write((result.solution.size()-2) + "");
			bw.newLine();
			for(int i = 1 ; i < result.solution.size() -1 ; i ++) {
				bw.write(result.solution.get(i).toString());
				bw.newLine();
			}
			bw.close();
		}
		catch(Exception ex) {}
	}
}
