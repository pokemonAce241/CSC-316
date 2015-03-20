/**
 * @author Gitesh Agarwal
 * 
 */
public class Ticket {

	private int id;

	private int priority;

	private int count;

	public Ticket(int id, int priority) {
		this.id = id;
		this.priority = priority;
		count = 1;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

}