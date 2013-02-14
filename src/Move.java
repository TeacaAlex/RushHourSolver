
public class Move {
	private Position start;
	private Position stop;
	private boolean isFinished;
	private char direction;
	/*
	 * o mutare este formata din pozitia din care pleaca masina, unde se opreste, directia
	 * si daca este mutare finala
	 */
	public Move(Position start, Position stop, boolean isFinished, char direction) {
		this.start = start;
		this.stop = stop;
		this.isFinished = isFinished;
		this.direction = direction;
	}
	
	public Move(Position start, char direction) {
		this.start = start;
		this.isFinished = true;
		this.stop = new Position(0, 0);
		this.direction = direction;
	}
	
	public boolean getIsFinished() {
		return isFinished;
	}
	
	public Position getStart() {
		return start;
	}
	
	public Position getStop() {
		return stop;
	}
	
	public char getDirection() {
		return direction;
	}
	
	public String toString() {
		return "(" + start.getOx() + ", " + start.getOy() + ") " + direction + " (" + stop.getOx() + ", " + stop.getOy() + ")";
	}
}
