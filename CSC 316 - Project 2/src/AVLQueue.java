import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class is used to store entries in the order they were added from the user
 * and arranged based on user input. This class also implements the methods form the
 * Queue interface.
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class AVLQueue implements Queue {

	/**
	 * A AVL map used to store and organize any entries added to the queue.
	 */
	private AVLMap<Integer, Ticket> map;

	/**
	 * A Array list that stores entries in the order of their keys.
	 */
	private ArrayList<Integer> key;

	/**
	 * constructor for the the class.
	 */
	public AVLQueue() {
		map = new AVLMap<Integer, Ticket>(comparator);
		key = new ArrayList<Integer>();
		key.add(0);
	}

	
	/**
	 * This function takes a ticket object adds it to the classes
	 * map and array list. If either the map or queue already has
	 * a ticket with the same priority as the ticket in the parameter
	 * a warning is thrown.
	 * @param ticket
	 *              The ticket that is being added to the class.
	 * @throws warning
	 *                A waring is thrown with a particular message if
	 *                either the map or queue already has
	 * a ticket with the same priority as the ticket in the parameter
	 *                
	 */
	public void insert(Ticket ticket) throws Warning {
		Ticket t = map.put(ticket.getPriority(), ticket);
		if (t == null)
			throw new Warning("a ticket with priority " + ticket.getPriority()
					+ "is already in the queue");
		key.add(ticket.getPriority());
	}

	/**
	 * This function removes the ticket that matches the id parameter from the map and queue. 
	 * A warning is thrown if their is either no ticket with the same id as in the parameter
	 * or if the map is empty.
	 * @param id
	 *          the id of the ticket you want to remove.
	 * @throws Warning
	 *                A warning is thrown if their is either no ticket with the same id as in the parameter
	 * or if the map is empty.
	 */
	public Ticket remove(int id) throws Warning {
		if (map.isEmpty())
			throw new Warning("removal attempted when queue is empty");

		int k = key.get(id);
		Ticket ticket = map.remove(k);
		if (ticket == null)
			throw new Warning("there is no ticket with id = " + id
					+ " in the queue");
		return ticket;
	}
	/**
	 * This function removes the ticket with the highest priority from the map and queue. 
	 * A warning is thrown if the map is empty.
	 * @param id
	 *          the id of the ticket you want to remove.
	 * @throws Warning
	 *                A warning is thrown if their is either no ticket with the same id as in the parameter
	 * or if the map is empty.
	 */
	public Ticket removeHighest() throws Warning {
		if (map.isEmpty())
			throw new Warning("removal attempted when queue is empty");

		Ticket ticket = map.lastValue();
		map.remove(ticket.getPriority());
		return ticket;
	}
	/**
	 * This function finds the ticket that matches the id parameter from the queue and prints its position in the map. 
	 * A warning is thrown if their is either no ticket with the same id as in the parameter
	 * or if the map is empty.
	 * @param id
	 *          the id of the ticket you want to remove.
	 * @throws Warning
	 *                A warning is thrown if their is either no ticket with the same id as in the parameter
	 * or if the map is empty.
	 */
	public int query(int id) throws Warning {
		if (map.isEmpty())
			throw new Warning("query attempted when queue is empty");

		int k = key.get(id);
		int r = map.getPosition(k);
		if (r == -1)
			throw new Warning("there is no ticket with id = " + id
					+ " in the queue");
		return r;
	}

}
