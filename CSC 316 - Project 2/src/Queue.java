import java.util.Comparator;

/**
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public interface Queue {
	/**
	 * A comparator used to compare the values of two ticket objects.
	 */
	public static final TicketComparator comparator = new TicketComparator();

	/**
	 * This function takes a ticket object adds it to the classes map and array
	 * list. If either the map or queue already has a ticket with the same
	 * priority as the ticket in the parameter a warning is thrown.
	 * 
	 * @param ticket
	 *            The ticket that is being added to the class.
	 * @throws Warning
	 *             A waring is thrown with a particular message if either the
	 *             map or queue already has a ticket with the same priority as
	 *             the ticket in the parameter
	 * 
	 */
	public void insert(Ticket ticket) throws Warning;

	/**
	 * This function removes the ticket that matches the id parameter from the
	 * map and queue. A warning is thrown if their is either no ticket with the
	 * same id as in the parameter or if the map is empty.
	 * 
	 * @param id
	 *            the id of the ticket you want to remove.
	 * @return The ticket that was removed from the queue.
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 */
	public Ticket remove(int id) throws Warning;

	/**
	 * This function removes the ticket with the highest priority from the map
	 * and queue. A warning is thrown if the map is empty.
	 * 
	 * @return The ticket that was removed from the queue.
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 */
	public Ticket removeHighest() throws Warning;

	/**
	 * This function finds the ticket that matches the id parameter from the
	 * queue and prints its position in the map. A warning is thrown if their is
	 * either no ticket with the same id as in the parameter or if the map is
	 * empty.
	 * 
	 * @param id
	 *            the id of the ticket you want to query.
	 * @return Returns the position of the ticket that was queried.
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 */
	public int query(int id) throws Warning;

	/**
	 * This class helps the program make comparisons between ticket objects.
	 * 
	 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
	 */
	public static final class TicketComparator implements Comparator<Integer> {
		/**
		 * This method is used to compare the two ints in the parameter.
		 * 
		 * @param one
		 *            first integer to compare to second.
		 * @param two
		 *            second integer to compare to first.
		 * @return An integer less than 0, 0, or greater than 0, if the first
		 *         integer is less than, equal to, or greater than the second
		 *         integer, respectively.
		 */
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
