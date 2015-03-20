import java.util.Scanner;

/**
 * @author Gitesh Agarwal
 * 
 */
public class UI {

	public static PriorityQueue queue;

	public static int id;

	public UI() {
		queue = new PriorityQueue();
		id = 1;
	}

	/**
	 * @param coms
	 */
	public String process(String[] coms) {
		if (coms.length == 1) {
			if (coms[0].equals("*"))
				return removeHighest();
		} else if (coms.length == 2) {
			if (coms[0].equals("+"))
				return insert(Integer.parseInt(coms[1]));
			else if (coms[0].equals("-"))
				return remove(Integer.parseInt(coms[1]));
			else if (coms[0].equals("?"))
				return query(Integer.parseInt(coms[1]));
		}

		// TODO: Warning
		return null;
	}

	public String insert(int priority) {
		Ticket ticket = new Ticket(id, priority);
		queue.insert(ticket);
		String out = "id = " + id++;
		return out;
	}

	public String remove(int id) {
		String pos = query(id);
		Ticket ticket = queue.remove(id);		
		String out = ticket.getPriority() + ", " + pos;
		return out;
	}

	public String removeHighest() {
		Ticket ticket = queue.removeHighest();
		String out = "id = " + ticket.getId() + ", " + ticket.getPriority();
		return out;
	}

	public String query(int id) {
		int pos = queue.query(id);
		String out = "pos = " + pos;
		return out;
	}

	public static void main(String[] args) {
		UI ui = new UI();
		Scanner scan = new Scanner(System.in);
		String in = scan.nextLine();
		while (!in.isEmpty()) {
			String[] coms = in.split(" ");
			String out = ui.process(coms);
			System.out.println(in + "\n\t" + out);
			in = scan.nextLine();
		}
		scan.close();
	}
}
