import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The main ui of the balanced program to take user commands to add tickets,
 * remove tickets, print the position of a ticket, or remove the highest ticket
 * in a balanced map.
 * 
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class BalancedHelpTickets {

	/**
	 * The queue the tickets are stored.
	 */
	public static Queue queue;

	/**
	 * used to track the amount of tickets in the queue.
	 */
	public static int id;

	/**
	 * constructor for the UI class.
	 */
	public BalancedHelpTickets() {
		queue = new AVLQueue();
		id = 1;
	}

	/**
	 * This function takes a string command form the string array parameter and
	 * does a specific action depending on what the string command is.The
	 * function throws a warning if either the second command in the array is
	 * not a integer when needed or if the first command in the array is not a
	 * valid command.
	 * 
	 * @param coms
	 *            A string array with the needed command to process.
	 * @return string returns a specific string depending what action was done.
	 * @throws Warning
	 *             throws a warning if either the second command in the array is
	 *             not a integer when needed or if the first command in the
	 *             array is not a valid command.
	 */
	public String process(String[] coms) throws Warning {
		try {
			if (coms[0].equals("+"))
				return insert(Integer.parseInt(coms[1]));
		} catch (NumberFormatException e) {
			id--; // Decrease the id count if ticket insertion fails
			throw new Warning("priority " + coms[1] + " is not an integer");
		}

		try {
			if (coms[0].equals("*"))
				return removeHighest();
			else if (coms[0].equals("-"))
				return remove(Integer.parseInt(coms[1]));
			else if (coms[0].equals("?"))
				return query(Integer.parseInt(coms[1]));
		} catch (NumberFormatException e) {
			throw new Warning("id " + coms[1] + " is not an integer");
		}

		throw new Warning("invalid command " + coms[0] + " " + coms[1]);
	}

	/**
	 * This function takes a priority parameter, creates a new ticket object,
	 * and adds it to the queue. If either the map or queue already has a ticket
	 * with the same priority as the ticket in the parameter a warning is
	 * thrown.
	 * 
	 * @param priority
	 *            The ticket that is being added to the class.
	 * @return String returns a string message once a ticket has been added.
	 * @throws Warning
	 *             A waring is thrown with a particular message if either the
	 *             map or queue already has a ticket with the same priority as
	 *             the ticket in the parameter
	 */
	public String insert(int priority) throws Warning {
		Ticket ticket = new Ticket(id, priority);
		queue.insert(ticket);
		String out = "id = " + id++;
		return out;
	}

	/**
	 * This function removes the ticket that matches the id parameter from the
	 * map and queue. A warning is thrown if their is either no ticket with the
	 * same id as in the parameter or if the map is empty.
	 * 
	 * @param id
	 *            the id of the ticket you want to remove.
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 * 
	 * @return String returns a string message once a ticket has been removed.
	 */
	public String remove(int id) throws Warning {
		String pos = query(id);
		Ticket ticket = queue.remove(id);
		String out = ticket.getPriority() + ", " + pos;
		return out;
	}

	/**
	 * This function removes the ticket with the highest priority from the map
	 * and queue. A warning is thrown if the map is empty.
	 * 
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 * @return String returns a string message once the ticket with the highest
	 *         priority is removed.
	 */
	public String removeHighest() throws Warning {
		Ticket ticket = queue.removeHighest();
		String out = "id = " + ticket.getId() + ", " + ticket.getPriority();
		return out;
	}

	/**
	 * This function finds the ticket that matches the id parameter from the
	 * queue and prints its position in the map. A warning is thrown if their is
	 * either no ticket with the same id as in the parameter or if the map is
	 * empty.
	 * 
	 * @param id
	 *            the id of the ticket you want to remove.
	 * @throws Warning
	 *             A warning is thrown if their is either no ticket with the
	 *             same id as in the parameter or if the map is empty.
	 * @return String returns a string message once the ticket is found and its
	 *         position has been calculated.
	 */
	public String query(int id) throws Warning {
		int pos = queue.query(id);
		String out = "pos = " + pos;
		return out;
	}

	/**
	 * The main program for running the entire program.
	 * 
	 * @param args
	 *            Contains any arguments the program would need for the program.
	 */
	public static void main(String[] args) {
		BalancedHelpTickets ui = new BalancedHelpTickets();
		Scanner scan = new Scanner(System.in);
		if (args.length == 1) {
			try {
				scan = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) {
				// Continue...
			}
		}

		String in = scan.nextLine();
		while (!in.isEmpty()) {
			System.out.println(in);
			String[] coms = new String[2];
			int i = 0;
			Scanner line = new Scanner(in);
			while (line.hasNext())
				coms[i++] = line.next();
			line.close();

			String out;
			try {
				out = ui.process(coms);
			} catch (Warning ex) {
				out = ex.getMessage();
			}
			System.out.println("\t" + out);

			try {
				in = scan.nextLine();
			} catch (NoSuchElementException e) {
				return;
			}
		}
		scan.close();
	}
}
