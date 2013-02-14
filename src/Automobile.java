
public class Automobile {
	/*
	 * o masina este caracterizata de tip si de pozitia din parcare
	 */
	private AutomobileType type;
	private Position position;
	
	Automobile(AutomobileType type, Position position) {
		this.type = type;
		this.position = position;
	}
	
	public AutomobileType getType() {
		return type;
	}
	
	public void setType(AutomobileType type) {
		this.type = type;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public String toString() {
		return type.type + ", (" + position.getOx() + ", " + position.getOy() + ")";
	}
	
	public boolean isEqual(Object other) {
        if (this == other)
            return true;
        
        Automobile otherVehicle = (Automobile)other;
        
        return ((type.type == otherVehicle.type.type) &&
                (position == otherVehicle.position));
    }
	
	public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof Automobile)) {
            return false;
        }
        Automobile otherVehicle = (Automobile)other;
        
        return ((type.type == otherVehicle.type.type) &&
                (position == otherVehicle.position));
    }
	
	
}
