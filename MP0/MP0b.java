public class MP0b {

/**
* Find the first occurrence of x in an array a.
*
* @param x
* value to find
* @param a
* array with values to search from
* @return lowest i such that a[i]==x, or -1 if x not found in a.
*/

    public static void main ( String[] args ) {
        // create an array to search from
        // note the declaration of the array & the initialization
        int[] a = new int[] { 1, 5, 6, 2, 3, 9, 99, 72, 11, 12 };

        // call the search function
        System.out.println( find( 3, a ) );
	System.out.println(recursiveLocater( 11, a ) );

	createSearchArray();

	// call the timing function
	timeToSearch( 5999, array );
    }

    public static int find(int x, int[] a) {
        return linearSearch(x, a);
    }

 
    private static int linearSearch(int x, int[] a) {
        // notice the use of the length field wrt the array a
        for (int i = 0; i < a.length; ++i) {
            if (x == a[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int recursiveLocater(int x, int[] a){
	int initialIndex = 0;

	return recursiveSearch(x, a, initialIndex);
    }


    private static int recursiveSearch(int x, int[] a, int i){
	if(x == a[i]){
	    return i;
	}

	else if(i >= a.length){
	    return -1;
	}

	else
	    i++;

	return recursiveSearch( x, a, i );
    }

    public static void createSearchArray(){
    	//Creates the array and then searches using both methods and timing each algorithm	
	int arrayValueLocation;
	int[] array = new int[10000];	

	for(arrayValueLocation=0; arrayValueLocation<10000; arrayValueLocation++){
	    array[arrayValueLocation] = arrayValueLocation + 1;
   	}
    }

    //returns the amount of time to find a given number in the array
    public static void timeToSearch( int numberToFind, int[] arrayToSearch ){
	
	long startTime = 0;
        long endTime = 0;


        System.out.print("\n\nTiming method for linear search to find 5999:");
        startTime = System.currentTimeMillis();
	find( numberToFind, arrayToSearch );
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");

        System.out.print("\nTiming method for recursive search to find 5999:");
        startTime = System.currentTimeMillis();
	recursiveLocater( numberToFind, arrayToSearch );
        endTime = System.currentTimeMillis();
        System.out.println(" " + (endTime - startTime) + " milliseconds");

	return;
    }
}




  	
