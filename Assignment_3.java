/*
____________________________________________________________________________________

Name : Onkar Rajesh Shinde
Class : TE3
Roll NO : 31372
Batch : N3
____________________________________________________________________________________

*/
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
/* ----------------------------------------(1)----------------------------------- */
//Producer Consumer Starts here
class Q {
	int item;
	static Semaphore semCon = new Semaphore(0);
	static Semaphore semProd = new Semaphore(1);

	void get()
	{
		try {
			semCon.acquire();
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		System.out.println("Consumer consumed item : " + item);
		semProd.release();
	}
	void put(int item)
	{
		try {
			semProd.acquire();
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		this.item = item;

		System.out.println("Producer produced item : " + item);
		semCon.release();
	}
}
class Producer implements Runnable {
	Q q;
	Producer(Q q)
	{
		this.q = q;
		new Thread(this, "Producer").start();
	}

	public void run()
	{
		for (int i = 0; i < 5; i++)
			// producer put items
			q.put(i);
	}
}
class Consumer implements Runnable {
	Q q;
	Consumer(Q q)
	{
		this.q = q;
		new Thread(this, "Consumer").start();
	}

	public void run()
	{
		for (int i = 0; i < 5; i++)
			q.get();
	}
}
//Producer Consumer Ends here
/* ----------------------------------------(2)----------------------------------- */
//Dining Philosopher Problem Starts here


//Dining Philosopher Problem Ends here
/* ----------------------------------------(3)----------------------------------- */
//Reader - Writer Problem Starts here

//Reader - Writer Problem Ends here
/* ----------------------------------------(4)----------------------------------- *///Sleeping Barber Problem Starts here

//Sleeping Barber Problem Ends here

/* ----------------------------------(Main Program)----------------------------- */
public class Assignment_3 {
    static int philosopher = 5;
    static philosopher philosophers[] = new philosopher[philosopher];
    static chopstick chopsticks[] = new chopstick[philosopher];

    static class chopstick {

        public Semaphore mutex = new Semaphore(1);

        void grab() {
            try {
                mutex.acquire();
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        void release() {
            mutex.release();
        }

        boolean isFree() {
            return mutex.availablePermits() > 0;
        }

    }

    static class philosopher extends Thread {

        public int number;
        public chopstick leftchopstick;
        public chopstick rightchopstick;

        philosopher(int num, chopstick left, chopstick right) {
            number = num;
            leftchopstick = left;
            rightchopstick = right;
        }

        public void run(){

            while (true) {
                leftchopstick.grab();
                System.out.println("philosopher " + (number+1) + " grabs left chopstick.");
                rightchopstick.grab();
                System.out.println("philosopher " + (number+1) + " grabs right chopstick.");
                eat();
                leftchopstick.release();
                System.out.println("philosopher " + (number+1) + " releases left chopstick.");
                rightchopstick.release();
                System.out.println("philosopher " + (number+1) + " releases right chopstick.");
            }
        }

        void eat() {
            try {
                int sleepTime = ThreadLocalRandom.current().nextInt(0, 1000);
                System.out.println("philosopher " + (number+1) + " eats for " + sleepTime);
                Thread.sleep(sleepTime);
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
    //Sleeping barber
    public class SleepingBarber extends Thread {
    public static Semaphore customers = new Semaphore(0);
    public static Semaphore barber = new Semaphore(0);
    public static Semaphore accessSeats = new Semaphore(1);

    /* we denote that the number of chairs in this barbershop is 5. */
    public static final int CHAIRS = 5;
  /* we create the integer numberOfFreeSeats so that the customers
   can either sit on a free seat or leave the barbershop if there
   are no seats available */
    public static int numberOfFreeSeats = CHAIRS;
    /* THE CUSTOMER THREAD */
    class Customer extends Thread {
  /* we create the integer iD which is a unique ID number for every customer
     and a boolean notCut which is used in the Customer waiting loop */
        int iD;
        boolean notCut=true;
        /* Constructor for the Customer */
        public Customer(int i) {
            iD = i;
        }
        public void run() {
            while (notCut) {  // as long as the customer is not cut
                try {
                    accessSeats.acquire();  //tries to get access to the chairs
                    if (numberOfFreeSeats > 0) {  //if there are any free seats
                        System.out.println("Customer " + this.iD + " just sat down.");
                        numberOfFreeSeats--;  //sitting down on a chair
                        customers.release();  //notify the barber that there is a customer
                        accessSeats.release();  // don't need to lock the chairs anymore
                        try {
                            barber.acquire();  // now it's this customers turn but we have to wait if the barber is busy
                            notCut = false;  // this customer will now leave after the procedure
                            this.get_haircut();  //cutting...
                        } catch (InterruptedException ex) {}
                    }
                    else  {  // there are no free seats
                        System.out.println("There are no free seats. Customer " + this.iD + " has left the barbershop.");
                        accessSeats.release();  //release the lock on the seats
                        notCut=false; // the customer will leave since there are no spots in the queue left.
                    }
                }
                catch (InterruptedException ex) {}
            }
        }
        /* this method will simulate getting a hair-cut */
        public void get_haircut(){
            System.out.println("Customer " + this.iD + " is getting his hair cut");
            try {
                sleep(5050);
            } catch (InterruptedException ex) {}
        }
    }
    /* THE BARBER THREAD */
    class Barber extends Thread {
        public Barber() {}
        public void run() {
            while(true) {  // runs in an infinite loop
                try {
                    customers.acquire(); // tries to acquire a customer - if none is available he goes to sleep
                    accessSeats.release(); // at this time he has been awaken -> want to modify the number of available seats
                    numberOfFreeSeats++; // one chair gets free
                    barber.release();  // the barber is ready to cut
                    accessSeats.release(); // we don't need the lock on the chairs anymore
                    this.cutHair();  //cutting...
                } catch (InterruptedException ex) {}
            }
        }
        /* this method will simulate cutting hair */
        public void cutHair(){
            System.out.println("The barber is cutting hair");
            try {
                sleep(5000);
            } catch (InterruptedException ex){ }
        }
    }
}
    public static void main(String args[])
	{
        Scanner sc = new Scanner(System.in);
        Boolean cond = true;
        while(cond){
            System.out.println("Enter your choice: ");
            System.out.println("1.\tProducer - Consumer Problem ");
            System.out.println("2.\tDining Philospher Problem");
            System.out.println("3.\tReader - Writer Problem");
            System.out.println("4.\tSleeping Barber Problem");
            System.out.println("5.\tExit");
            System.out.println("------------------------------------");
            int x = sc.nextInt();
            switch(x){
                case 1:
                    //Producer - Consumer
                    Q q = new Q();
                    new Consumer(q);
                    new Producer(q);
                    break;
                case 2:
                    //Dining Philosopher
                    for (int i = 0; i < philosopher; i++) {
                        chopsticks[i] = new chopstick();
                    }
            
                    for (int i = 0; i < philosopher; i++) {
                        philosophers[i] = new philosopher(i, chopsticks[i], chopsticks[(i + 1) % philosopher]);
                        philosophers[i].start();
                    }
            
                    while (true) {
                        try {
                            // sleep 1 sec
                            Thread.sleep(1000);
            
                            // check for deadlock
                            boolean deadlock = true;
                            for (chopstick f : chopsticks) {
                                if (f.isFree()) {
                                    deadlock = false;
                                    break;
                                }
                            }
                            if (deadlock) {
                                Thread.sleep(1000);
                                System.out.println("Everyone Eats");
                                break;
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace(System.out);
                        }
                    }
            
                    System.out.println("Exit The Program!");
                    System.exit(0);
                    break;
                case 3:
                    //Reader Writer Problem
                    break;
                case 4:
                    //Sleeping Barber Problem
                    SleepingBarber barberShop = new SleepingBarber();  //Creates a new barbershop
                    barberShop.start();
                    break;
                case 5:
                    cond = false;
                    break;
                default:
                    System.out.println("Please Enter correct value");
            }
            System.out.println("\n------------------------------------\n");
        }
        

		
	}
    public void run(){
        Barber giovanni = new Barber();  //Giovanni is the best barber ever
        giovanni.start();  //Ready for another day of work
        /* This method will create new customers for a while */
        for (int i=1; i<16; i++) {
            Customer aCustomer = new Customer(i);
            aCustomer.start();
            try {
                sleep(2000);
            } catch(InterruptedException ex) {};
        }
    }  
}
