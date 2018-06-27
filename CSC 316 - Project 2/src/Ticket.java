/**
 * This is a object representation of a ticket in the program.
 * 
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class Ticket {

	/**
	 * The id of the ticket
	 */
	private int id;

	/**
	 * The priority of the ticket
	 */
	private int priority;

	/**
	 * The constructor for the ticket object.
	 * 
	 * @param id
	 *            The id of the ticket
	 * @param priority
	 *            The priority of the ticket
	 */
	public Ticket(int id, int priority) {
		this.id = id;
		this.priority = priority;
	}

	/**
	 * Returns the tickets id.
	 * 
	 * @return id Returns the tickets id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the tickets priority.
	 * 
	 * @return the priority Returns the tickets priority.
	 */
	public int getPriority() {
		return priority;
	}

}