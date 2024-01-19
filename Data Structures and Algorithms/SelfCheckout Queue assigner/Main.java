import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    /**
     * Identify the customer based on the time needed to be served to go in which queues normal or express.
     * @param args arguments
     * @author Pal Patel 0000865048
     */
    public static void main(String[] args) {
        //Declaring the queues
        LinkedQueue<Customer>[] expressLines;
        LinkedQueue<Customer>[] normalLines;
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/CustomerData.txt"));

        //Read the first line to get parameters (f, n, x)
            int f = scanner.nextInt();
            int n = scanner.nextInt();
            int x = scanner.nextInt();
        //Initializing the Queues
            expressLines = new LinkedQueue[f];
            normalLines = new LinkedQueue[n];
        // Create express lines
            for (int i = 0; i < f; i++) {
                expressLines[i] = new LinkedQueue<>();
            }
        // Create normal lines
            for (int i = 0; i < n; i++) {
                normalLines[i] = new LinkedQueue<>();
            }

            int[] times = new int[n + f];
            int maxTime= Integer.MIN_VALUE;

            // Read number of customers
            int numOfCustomers = scanner.nextInt();
            int currentLineLeastCustomers, normalLineLeastCustomers;
            // Read items for each customer and add them to the queues
            for (int i = 0; i < numOfCustomers; i++) {
                int items = scanner.nextInt();
                //new Customer
                Customer customer = new Customer(items);
                // time taken to serve that customer
                int time = customer.calculationTimeOfServing();

                // if items are less than or equals to x
                if (items <= x){
                    currentLineLeastCustomers = findLeast(times,0);
                    //check total fast lanes
                    if (currentLineLeastCustomers < f)
                        expressLines[currentLineLeastCustomers].enqueue(customer);
                    else
                        // add to the normal lane instead
                        normalLines[currentLineLeastCustomers - f].enqueue(customer);

                    // add the times in the times array
                    times[currentLineLeastCustomers] += time;

                }else {
                    //find the normal lane with the least customers
                    normalLineLeastCustomers = findLeast(times, f);
                    normalLines[normalLineLeastCustomers - f].enqueue(customer);
                    times[normalLineLeastCustomers] += time;
                }
            }

            // calculating the maximum time to clear the whole store
            for (int number : times) {
                if (number > maxTime) {
                    maxTime = number;
                }
            }
            System.out.println("====================================  Part A  =====================================\n");
            for (int i = 0; i < expressLines.length; i++) System.out.println("CheckOut(Express) # "  + (i+1) + " Total Time: " +times[i] + "s -->" + expressLines[i]);
            for (int i = 0; i < normalLines.length; i++) System.out.println("CheckOut(Normal ) # " + (i+1) + " Total Time: " + times[i + expressLines.length] +"s -->" + normalLines[i]);
            System.out.println("\n");
            System.out.println("Total time to Serve all the customers in all the queues: " + maxTime + "s.");
            System.out.println("\n\n");

            // =========================== Part B ====================================
            System.out.println("====================================  Part B  ====================================\n");
            simulateCheckout(expressLines,normalLines,maxTime);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



    }

    /**
     * Simulation of the checkout with expressLines and normalLines.
     * @param expressLines ExpressLines queue to simulate
     * @param normalLines NormalLines queue to simulate
     */
    private static void simulateCheckout(LinkedQueue<Customer>[] expressLines, LinkedQueue<Customer>[] normalLines, int maxTime) {
        int time = 0;
        System.out.print("Time(s)|");

        // Header for expressLines
        for (int i = 1; i <= expressLines.length; i++) {
            System.out.printf(" ELane %d |", i);
        }

        // Header for normalLines
        for (int i = 1; i <= normalLines.length; i++) {
            System.out.printf(" NLane %d |", i);
        }

        System.out.println();
        while (time <= maxTime) {
            if (time % 30 == 0 || time==maxTime) {
                    System.out.printf("%5d  |", time);

                    // Display sizes for expressLines
                    for (int i = 0; i < expressLines.length; i++) {
                        System.out.printf(" %5d   |", expressLines[i].size());
                    }

                    // Display sizes for normalLines
                    for (int i = 0; i < normalLines.length; i++) {
                        System.out.printf(" %5d   |", normalLines[i].size());
                    }
                    System.out.println();

                for (LinkedQueue<Customer> expressLine : expressLines) {
                    // if the expressLine is not empty
                    if (!expressLine.isEmpty()) {
                        Customer customer = expressLine.peek();
                        //remaining time
                        int remainingTime = customer.getTimeToServe() - 30;
                        if (remainingTime <= 0) {
                            expressLine.dequeue();
                            // remove the remaining time from the next customer in the queue.
                            if (remainingTime < 0 && !expressLine.isEmpty()) {
                                Customer nextCustomer = expressLine.peek();
                                nextCustomer.setTimeToServe(nextCustomer.getTimeToServe() + remainingTime);
                            }
                        } else {
                            //set the remaining time to serve of the customer
                            customer.setTimeToServe(remainingTime);
                        }
                    }
                }

                for (LinkedQueue<Customer> normalLine : normalLines) {
                    // if the normalLine is not empty
                    if (!normalLine.isEmpty()) {
                        Customer customer = normalLine.peek();
                        //remaining time
                        int remainingTime = customer.getTimeToServe() - 30;
                        if (remainingTime <= 0) {
                            normalLine.dequeue();
                            // remove the remaining time from the next customer in the queue.
                            if (remainingTime < 0 && !normalLine.isEmpty()) {
                                Customer nextCustomer = normalLine.peek();
                                nextCustomer.setTimeToServe(nextCustomer.getTimeToServe() + remainingTime);
                            }
                        } else {
                            //set the remaining time to serve of the customer
                            customer.setTimeToServe(remainingTime);
                        }
                    }
                }

            }
            time += 1; // Increment time by 1 second
        }
    }

//    private static void simulateCheckout(LinkedQueue<Customer>[] expressLines, LinkedQueue<Customer>[] normalLines, int maxTime) {
//        int time = 0;
//        System.out.print("Time(s)|");
//
//        // Header for expressLines
//        for (int i = 1; i <= expressLines.length; i++) {
//            System.out.printf(" Line %d |", i);
//        }
//
//        // Header for normalLines
//        for (int i = 1; i <= normalLines.length; i++) {
//            System.out.printf(" Line %d |", expressLines.length + i);
//        }
//
//        System.out.println();
//        while (time <= maxTime) {
//            if (time % 30 == 0 || time==maxTime) {
//                System.out.printf("%5d  |", time);
//
//            // Display sizes for expressLines
//                for (int i = 0; i < expressLines.length; i++) {
//                    System.out.printf(" %5d  |", expressLines[i].size());
//                }
//
//            // Display sizes for normalLines
//                for (int i = 0; i < normalLines.length; i++) {
//                    System.out.printf(" %5d  |", normalLines[i].size());
//                }
//                System.out.println();
////                System.out.printf("%3d     | %3d    | %3d    | %3d\n", time, expressLines[0].size(), normalLines[0].size(), normalLines[1].size());
//            }
//
//            for (LinkedQueue<Customer> expressLine : expressLines) {
//                // if the expressLine is not empty
//                if (!expressLine.isEmpty()) {
//                    Customer customer = expressLine.peek();
//                    //remaining time
//                    int remainingTime = customer.getTimeToServe() - 30;
//                    if (remainingTime <= 0) {
//                        expressLine.dequeue();
//                        // remove the remaining time from the next customer in the queue.
//                        if (remainingTime < 0 && !expressLine.isEmpty()) {
//                            Customer nextCustomer = expressLine.peek();
//                            nextCustomer.setTimeToServe(nextCustomer.getTimeToServe() + remainingTime);
//                        }
//                    } else {
//                        //set the remaining time to serve of the customer
//                        customer.setTimeToServe(remainingTime);
//                    }
//                }
//            }
//
//            for (LinkedQueue<Customer> normalLine : normalLines) {
//                // if the normalLine is not empty
//                if (!normalLine.isEmpty()) {
//                    Customer customer = normalLine.peek();
//                    //remaining time
//                    int remainingTime = customer.getTimeToServe() - 30;
//                    if (remainingTime <= 0) {
//                        normalLine.dequeue();
//                        // remove the remaining time from the next customer in the queue.
//                        if (remainingTime < 0 && !normalLine.isEmpty()) {
//                            Customer nextCustomer = normalLine.peek();
//                            nextCustomer.setTimeToServe(nextCustomer.getTimeToServe() + remainingTime);
//                        }
//                    } else {
//                        //set the remaining time to serve of the customer
//                        customer.setTimeToServe(remainingTime);
//                    }
//                }
//            }
//            time += 30;
//        }
//    }
//
    /**
     *
     * @param times total times
     * @param begin where to begin for finding the least time( express===> begin=0 and  normal===> begin=after the length of express line)
     * @return index of the element in the array which has least value
     */
    private static int findLeast(int[] times, int begin) {

        int least = Integer.MAX_VALUE, leastIndex = 0;
        for (int i = begin; i < times.length; i++) {
            if (times[i] < least){
                leastIndex = i;
                least = times[i];
            }
        }
        return leastIndex;
    }
}
