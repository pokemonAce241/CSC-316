import java.util.ArrayList;

/**
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class BSTQueue implements Queue {

	private BSTMap<Integer, Ticket> map;

	private ArrayList<Integer> key;

	public BSTQueue() {
		map = new BSTMap<Integer, Ticket>(comparator);
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

		int k = key.get(id);
		Ticket ticket = map.remove(k);
		if (ticket == null)
			throw new Warning("there is no ticket with id = " + id
					+ " in the queue");
		return ticket;
	}

	public Ticket removeHighest() throws Warning {
		if (map.isEmpty())
			throw new Warning("removal attempted when queue is empty");

		Ticket ticket = map.lastValue();
		map.remove(ticket.getPriority());
		return ticket;
	}

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
