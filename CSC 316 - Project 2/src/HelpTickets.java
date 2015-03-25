import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Gitesh Agarwal
 * 
 */
public class HelpTickets {

	public static PriorityQueue queue;

	public static int id;

	public HelpTickets() {
		queue = new PriorityQueue();
		id = 1;
	}

	/**
	 * @param coms
	 * @return
	 * @throws Warning
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

	public String insert(int priority) throws Warning {
		Ticket ticket = new Ticket(id, priority);
		queue.insert(ticket);
		String out = "id = " + id++;
		return out;
	}

	public String remove(int id) throws Warning {
		String pos = query(id);
		Ticket ticket = queue.remove(id);
		String out = ticket.getPriority() + ", " + pos;
		return out;
	}

	public String removeHighest() throws Warning {
		Ticket ticket = queue.removeHighest();
		String out = "id = " + ticket.getId() + ", " + ticket.getPriority();
		return out;
	}

	public String query(int id) throws Warning {
		int pos = queue.query(id);
		String out = "pos = " + pos;
		return out;
	}

	public static void main(String[] args) {
		HelpTickets ui = new HelpTickets();
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
