import java.util.Comparator;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Gitesh Agarwal
 * 
 */
public class PriorityQueue {

	private TreeMap<Integer, Ticket> map;

	public PriorityQueue() {
		TicketComparator comparator = new TicketComparator();
		map = new TreeMap<Integer, Ticket>(comparator);
	}

	public void insert(Ticket ticket) {
		map.put(ticket.getPriority(), ticket);
	}

	public Ticket remove(int id) {
		Ticket ticket = map.get(id);
		map.remove(ticket.getPriority());
		return ticket;
		//TODO: Need to fix
	}

	public Ticket removeHighest() {
		Ticket ticket = map.lastEntry().getValue();
		map.remove(ticket.getPriority());
		return ticket;
	}

	public int query(int id) {
		Ticket ticket = map.get
		//TODO: Need to fix
	}

	protected class TicketComparator implements Comparator<Integer> {
		public int compare(Integer one, Integer two) {
			if (one > two)
				return 1;
			else if (one < two)
				return -1;
			else
				return 1;
		}
	}

}
