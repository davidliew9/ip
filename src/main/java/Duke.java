import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {
    List<Task> list = new ArrayList<>();
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Hi! I'm Duke your friendly neighbourhood chat bot");
        System.out.println("What can i do for you?");
        Duke duke = new Duke();
        duke.run();

    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String input = sc.nextLine();
            String checker;
            if (input.length() > 5) {
                checker = input.substring(0, 4);
            } else {
                checker = "nothing";
            }
            if (input.equals("list")) {
                showList();
            } else if (input.equals("bye")) {
                System.out.println("        Bye have a good day!");
                break;
            } else if(checker.equals("done")) {
                int num = Character.getNumericValue(input.charAt(5));
                done(num);
            } else {
                add(input);
            }
        }
    }

    public void done(int number) {
        try {
            if (number <= list.size()) {
                Task current = list.get(number - 1);
                current.markAsDone();
                System.out.println("        I have marked this as done:");
                System.out.println("        [" + current.isDone + "] " + current.description);
            } else {
                throw new DukeException("☹ OOPS!!! there is no such task");
            }
        } catch (DukeException e) {
            System.out.println("------------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
    }

    public void add(String input) {
        try {
            String[] type = input.split(" ", 2);
            Task task;
            if (type[0].equals("todo")) {
                if(type.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of a todo cannot be empty.");
                }
                task = new Todo(type[1]);
            } else if (type[0].equals("deadline")) {
                if(type.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of a deadline cannot be empty.");
                }
                String second = type[1];
                String[] secondSplit = second.split(" /by ", 2);
                task = new Deadline(secondSplit[0], secondSplit[1]);
            } else if (type[0].equals("event")) {
                if(type.length == 1) {
                    throw new DukeException("☹ OOPS!!! The description of a event cannot be empty.");
                }
                String second = type[1];
                String[] secondSplit = second.split(" /at ", 2);
                task = new Event(secondSplit[0], secondSplit[1]);
            } else {
                throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
            this.list.add(task);
            System.out.println("        Got it I have added this task:");
            System.out.println("        " + task.toString());
            System.out.println("        you now have " + list.size() + " tasks on the list");
        } catch (DukeException e) {
            System.out.println("------------------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("------------------------------------------------------");
        }
    }

    public void showList() {
        System.out.println("        Here are the tasks in your list:");
        int counter = 1;
        for (Task t : list) {
            System.out.println("        " + counter +  "." + t.toString());
            counter++;
        }
    }

    public static void echo(String input) {
        System.out.println("        " + input);
    }
}
