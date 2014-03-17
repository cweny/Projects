
public class MP0a {
	static public void main( String[] args ) {
		
		int n = 1001;
		
		seqGen(n);
		System.out.println("\n");
		recursiveSeqGen(n);
		System.out.println("\n");
		System.out.println("Largest number seen:" + largestNumSeen());
		System.out.println("\nLongest sequence seen:" + longestSeq());
		
	}

	/*
	 * The seqGen() takes an int n and modifies it. n=n/2 if n is even; n=3*n+1 if n is odd. These n are then printed and 
	 * a sequence is made. This sequence will eventually get to 1, where it stops.
	 * parameters: int n
	 */
	static public void seqGen(int n){
		
			if ( n < 1 ) {
				System.out.println( "Incorrect input. Terminating program." );
				return;
			}
			
			while ( n > 1 ) {
				System.out.print( n + " ");
				if ( n%2 == 0 ) {
					n = n/2;
				}
				else {
					n = 3*n + 1;
				}
			}
			
			System.out.println( n );
			
	}

	/*
	 * longestSeq returns the number that produces the longest sequence in [1,2001]
	 * parameters: none
	*/
	static public int longestSeq(){

		
		int n; //goes from 1 to 2001 to verify the sequence created by each
		int mostNumSeq = 0; //stores the longest sequence magnitude seen
		int thisNumSeq; //stores the current sequence magnitude that is being searched
		int longestSeq = 0; //stores the number n that gives the longest sequence magnitude
		
		for (int k = 1; k <= 2001; k++ ){
		n = k; // n goes into the seqGen() function. k shouldn't be modified
		thisNumSeq = 0; 

			if ( n < 1 ) {
				System.out.println( "Incorrect input. Terminating program." );
				return 0;
			}

			while ( n > 1 ) {
				thisNumSeq++; //the numbers in each sequence won't be printed. every time this while loop runs, the sequence magnitude increases by 1
				if ( n%2 == 0 ) {
					n = n/2;
				}
				else {
					n = 3*n + 1;
				}
			}
		
			if(thisNumSeq > mostNumSeq){ //if the sequence magnitude is longer than the longest that has been recorded, this sequence becomes the longest. the number that produces this longest sequence is also recorded
			mostNumSeq = thisNumSeq;
			longestSeq = k;
			}
		}
		
		return longestSeq;
		
	}
	/*
	 * largestNumSeen() returns the largest number in [1,2001]
	 * parameters: none
	 */

	static public int largestNumSeen(){
		int largestNum = 0;
		int n;
		int k;
		
		for (k = 1; k <= 2001; k++ ){
			n = k; //again, n goes into the seqGen() function
			if ( n < 1 ) {
				System.out.println( "Incorrect input. Terminating program." );
				return 0;
			}
		
			while ( n > 1 ) {

				if (n > largestNum) 
					largestNum = n; //The numbers in each sequence won't be printed. If any number is larger than the registered, it will become the new largest
				if ( n%2 == 0 ) {
					n = n/2;
				}
				else {
					n = 3*n + 1;
				}
			}
		}
		
		return largestNum;
		
	}

	/*
	 * recursive version of the seqGen() function
	 * parameters: int n, a number that creates a specific sequence
	 * no return
	 */
	static public void recursiveSeqGen(int n){
		
		if (n>1){
			System.out.print( n + " ");
			if ( n%2 == 0 ) {
				n = n/2;
				recursiveSeqGen(n); // recursiveseqGen() will be called again in either of the if-else expression
			}
			else {
				n = 3*n + 1;
				recursiveSeqGen(n);
			}
			if(n == 1) //the condition n=1 is there so that n is not printed more times than is necessary
				System.out.print( n + " ");
		}
		else if( n < 1){
			System.out.println( "Incorrect input. Terminating program." );
			
		}
	}
}
