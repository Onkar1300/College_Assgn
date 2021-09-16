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
		for (int i = 0; i < 3; i++)
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
		for (int i = 0; i < 3; i++)
			q.get();
	}
}
//Producer Consumer Ends here

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
                            boolean deadlock = true;
                            for (chopstick f : chopsticks) {
                                if (f.isFree()) {
                                    deadlock = false;
                                    break;
                                }
                            }
                            if (deadlock) {
                                Thread.sleep(1000);
                                System.out.println("Dinner is served");
                                break;
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace(System.out);
                        }
                    }
                    break;
                case 3:
                    //Reader Writer Problem
                    break;
                case 4:
                    //Sleeping Barber Problem
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
}
