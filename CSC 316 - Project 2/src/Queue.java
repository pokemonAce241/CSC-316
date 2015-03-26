import java.util.Comparator;

/**
 * 
 */

/**
 * @author Gitesh Agarwal
 * 
 */
public interface Queue {

	public static final TicketComparator comparator = new TicketComparator();

	public void insert(Ticket ticket) throws Warning;

	public Ticket remove(int id) throws Warning;

	public Ticket removeHighest() throws Warning;

	public int query(int id) throws Warning;

	public static final class TicketComparator implements Comparator<Integer> {
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
