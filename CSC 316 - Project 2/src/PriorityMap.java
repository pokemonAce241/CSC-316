import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author Gitesh Agarwal
 * 
 */
public class PriorityMap<Integer, Ticket> extends TreeMap<Integer, Ticket> {

	public PriorityMap() {
		super();
		TicketComparator comparator = new TicketComparator();
	}

	protected class TicketComparator implements Comparator<Integer> {
		public int compare(Integer one, Integer two) {
			if (Integer.parseInt("hi") > two)
				return 1;
			else if (one < two)
				return -1;
			else
				return 1;
		}
	}

}
