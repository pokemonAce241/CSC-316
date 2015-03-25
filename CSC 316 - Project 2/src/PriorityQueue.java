import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Gitesh Agarwal
 * 
 */
public class PriorityQueue {

	private PriorityMap<Integer, Ticket> map;

	private ArrayList<Integer> key;

	public PriorityQueue() {
		TicketComparator comparator = new TicketComparator();
		map = new PriorityMap<Integer, Ticket>(comparator);
		key = new ArrayList<Integer>();
		key.add(0);
	}

	public void insert(Ticket ticket) throws Warning {
		Ticket t = map.put(ticket.getPriority(), ticket);
		if (t == null)
			throw new Warning("a ticket with priority " + ticket.getPriority()
					+ "is already in the queue");
		key.add(ticket.getPriority());
	}

	public Ticket remove(int id) throws Warning {
		if (map.isEmpty())
			throw new Warning("removal attempted when queue is empty");

		try {
			int k = key.get(id);
			Ticket ticket = map.remove(k);
			return ticket;
		} catch (IndexOutOfBoundsException e) {
			throw new Warning("there is no ticket with id = " + key
					+ " in the queue");
		}
	}

	public Ticket removeHighest() throws Warning {
		if (map.isEmpty())
			throw new Warning("removal attempted when queue is empty");

		Ticket ticket = map.lastValue();
		map.remove(ticket.getPriority());
		return ticket;
	}

	public int query(int id) {
		int priority = key.get(id);
		int r = map.getPosition(priority);
		return r;
	}

	protected class TicketComparator implements Comparator<Integer> {
		public int compare(Integer one, Integer two) {
			if (one > two)
				return 1;
			else if (one < two)
				return -1;
			else
				return 0;
		}
	}

}
