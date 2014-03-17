import java.util.ArrayList;

class MP0c {

/**
* Find all prime numbers up to a limit.
*
* @param n
* Largest number to search
* @param printPrimes
* If true, print the primes found to output.
*
* @return number of primes <= n
* @effects If printPrimes is true, prints all primes <= n to output.
*/
    static int findPrimes(int n, boolean printPrimes) {
        boolean isPrime = true;
        int numPrimes = 0;

        for (int i = 2; i <= n; i++) {	// i and j are a counter that starts at two, as 1 is not prime by definition
            isPrime = true;		// initially, we assume the number the be prime

            for (int j = 2; j < i; j++) {	// in this loop we run thorugh every number less than i, and if j divides
                if (i % j == 0) {		// evenly into i, then i is not a prime number, so break out of loop
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {			// enter this conditional loop only if the number is prime,
                ++numPrimes;			// increase the count of primes, and print the prime number to console
                if (printPrimes) {
                    System.out.println(i);
                }
            }
        }
        return numPrimes;
    }

/**
* Find all prime numbers up to a limit.
*
* Improves on the original algorithm by limiting the number of values we
* have to check our number against --we only need to check up to the
* square root of the number.
*
* @param n
* Largest number to search
* @param printPrimes
* If true, print the primes found to output.
*
* @return number of primes <= n
* @effects If printPrimes is true, prints all primes <= n to output.
*/
    static int findPrimesQuickly(int n, boolean printPrimes) {
        // Consider using a labeled continue
        // Reference: http://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html
	
	boolean isPrime = true;
	int numPrimes = 0;

	for (int i = 2; i <= n; i++) {
		isPrime = true;
		double sqrti = Math.sqrt(i);	
		
		for (int j = 2; j <= sqrti; j++) {   //essentially the same algorithm, but j only increments up to k = sqrt(i)
			 if (i % j == 0) {
                    		isPrime = false;
                   		 break;
               		 }
            	}

            	if (isPrime) {              
                	++numPrimes;            
                	if (printPrimes) {
                   		System.out.println(i);
			}
           	 }
        }
	return numPrimes;
    }

/**
* Find all prime numbers up to a limit.
*
* Improves on the faster algorithm by limiting the number of values we
* have to check our number against --we only need to check PRIMES up to the
* square root of the number.
*
* @param n
* Largest number to search
* @param printPrimes
* If true, print the primes found to output.
*
* @return number of primes <= n
* @effects If printPrimes is true, prints all primes <= n to output.
*/

//Using a similar algorithm as before, but now using array to store prime numbers, and subsequently check against.

    static int findPrimesMoreQuickly(int n, boolean printPrimes) {
	boolean isPrime = true;
	int numPrimes = 0;
	ArrayList<Integer> primeArray = new ArrayList<Integer>(1);
	primeArray.add(2); //Add 2 to the array of primes to get loop started

	for ( int i = 2; i <= n; i++) {
		isPrime = true;
		double sqrti = Math.sqrt(i);
		
		// This loop runs through the values in the array and compares them to i
		for (int x = 0; (x <= primeArray.size()) && (primeArray.get(x) <= sqrti); x++) {
			if (i % primeArray.get(x) == 0){
				isPrime = false;
                                break;
                         }
		}

		if (isPrime) {
			primeArray.add( i );
			++numPrimes;
			
			if (printPrimes) {
				System.out.println(i);
			}
		}
			
	}
	return numPrimes;
    }


    public static void main(String[] args) {
        int numPrimes;

        // Find and print all primes between 0 and 50.

        System.out.println("Running method findPrimes:");
        numPrimes = findPrimes(50, true);
        System.out.println(numPrimes + " primes <= 50");

        System.out.println("\nRunning method findPrimesQuickly:");
        numPrimes = findPrimesQuickly(50, true);
        System.out.println(numPrimes + " primes <= 50");

        System.out.println("\nRunning method findPrimesMoreQuickly:");
        numPrimes = findPrimesMoreQuickly(50, true);
        System.out.println(numPrimes + " primes <= 50");

        // Time how long it takes to find primes.

        long startTime = 0;
        long endTime = 0;

        System.out.print("\n\nTiming method findPrimes:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimes(40000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 40000");

        System.out.print("\nTiming method findPrimesFaster:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimesQuickly(40000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 40000");

        System.out.print("\nTiming method findPrimesEvenFaster:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimesMoreQuickly(40000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 40000");

        System.out.print("\n\nTiming method findPrimes:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimes(80000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 80000");

        System.out.print("\nTiming method findPrimesFaster:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimesQuickly(80000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 80000");

        System.out.print("\nTiming method findPrimesEvenFaster:");
        startTime = System.currentTimeMillis();
        numPrimes = findPrimesMoreQuickly(80000, false);
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");
        System.out.println(numPrimes + " primes <= 80000");
    }

}
