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

	public void insert(Ticket ticket) {
		map.put(ticket.getPriority(), ticket);
		key.add(ticket.getPriority());
	}

	public Ticket remove(int id) {
		Ticket ticket = map.get(key.get(id));
		map.remove(ticket.getPriority());
		return ticket;
	}

	public Ticket removeHighest() {
		Ticket ticket = map.lastValue();
		map.remove(ticket.getPriority());
		return ticket;
	}

	public int query(int id) {
		int priority = key.get(id);
		return map.getPosition(priority);
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
