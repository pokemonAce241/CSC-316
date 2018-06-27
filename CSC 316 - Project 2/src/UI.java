
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Gitesh Agarwal, Mohamad Saleh, Jason Benckert
 * 
 */
public class UI {

	/**
	 * 
	 */
	public static PriorityQueue queue;

	/**
	 * 
	 */
	public static int id;

	/**
	 * 
	 */
	public UI() {
		queue = new PriorityQueue();
		id = 1;
	}

	/**
	 * @param coms
	 */
	public String process(String[] coms) {
		if (coms[0].equals("+"))
			return insert(Integer.parseInt(coms[1]));
		else if (coms[0].equals("*"))
			return removeHighest();
		else if (coms[0].equals("-"))
			return remove(Integer.parseInt(coms[1]));
		else if (coms[0].equals("?"))
			return query(Integer.parseInt(coms[1]));

		// TODO: Warning
		return null;
	}

	/**
	 * @param priority
	 * @return
	 */
	public String insert(int priority) {
		Ticket ticket = new Ticket(id, priority);
		queue.insert(ticket);
		String out = "id = " + id++;
		return out;
	}

	/**
	 * @param id
	 * @return
	 */
	public String remove(int id) {
		String pos = query(id);
		Ticket ticket = queue.remove(id);
		String out = ticket.getPriority() + ", " + pos;
		return out;
	}

	/**
	 * @return
	 */
	public String removeHighest() {
		Ticket ticket = queue.removeHighest();
		String out = "id = " + ticket.getId() + ", " + ticket.getPriority();
		return out;
	}

	/**
	 * @param id
	 * @return
	 */
	public String query(int id) {
		int pos = queue.query(id);
		String out = "pos = " + pos;
		return out;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UI ui = new UI();
		Scanner scan = new Scanner(System.in);
		if (args.length == 1) {
			try {
				scan = new Scanner(new File(args[0]));
			} catch (FileNotFoundException e) {
				// TODO: Warning
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

			String out = ui.process(coms);
			System.out.println("\t" + out);
			try {
				in = scan.nextLine();
			} catch (NoSuchElementException e) {
				// TODO: Warning
				return;
			}
		}
		scan.close();
	}
}
