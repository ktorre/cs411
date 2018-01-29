import java.util.ArrayList;

public class Trie {

	public static final int DEFAULT_SIZE = 1000; // Default allocation size for symbol/next array
	public static final char DELIMITER = '@';

	private int[] switchArray;
	private char[] symbolArray;
	private int[] nextArray;
	private int nextEmpty; // Always stores the latest empty space in symbolArray

	//*************** CONSTRUCTOR ***************
	public Trie() {
		nextEmpty = 0;
		switchArray = new int[ 52 ];  // Going to be arranged [ A-Z, a-z ] because of ascii ease
		//symbolArray = new ArrayList<Character>( DEFAULT_SIZE );
		//nextArray = new ArrayList<Integer>( DEFAULT_SIZE );
		symbolArray = new char[ DEFAULT_SIZE ];
		nextArray = new int[ DEFAULT_SIZE ];

		// Fill switch array with -1
		for ( int i = 0; i < switchArray.length; i++ ) { switchArray[ i ] = -1; }
		
		for ( int i = 0; i < DEFAULT_SIZE; i++ ) {
			nextArray[ i ] = -1;
		}
	}


	//*************** GETTERS/SETTERS ***************
	private int getNext( int location ) {
		return nextArray[ location ];
	}

	private void setNext( int location, int value ) {
		nextArray[ location ] = value;
	}

	private char getSymbol( int location ) {
		return symbolArray[ location ];
	}

	private void setSymbol( int location, char value ) {
		symbolArray[ location ] = value;
	}


	//*************** PUBLIC FUNCTIONS ***************
	// Overload input function to allow for string input
	public int inputWord( String word ) {
		return inputWord( word.toCharArray() );
	}

	public int inputWord( char[] word ) {
		// Making it return an int so we can use it for error handling/reporting
		if(!containsWord( word )){
			int head = ( (int)word[ 0 ] - 65 );
			//Check for the 6 characters between 'Z' and 'a'
			if ( head > 25 && head < 32 )
				return -1; // If not in alphabet, throw error
			//Account for the 6 characters between 'Z' and 'a'
			if( head > 31 )
				head -= 6;
			if ( head < 0 || head > 51 )  { 
				return -1; // If not in alphabet, throw error
			} else {
				int symbolStartLocation = getSymbolLocation( head ); 
				fillSymbolArray( symbolStartLocation, word );
				return 0; // success
			}
		}
		else
			return 1;
	}
	
	public boolean containsWord( String word ) {
		return containsWord( word.toCharArray() );
	}
	
	public boolean containsWord( char[] word ) {
		int head = ( (int)word[ 0 ] - 65 );
		//Check for the 6 characters between 'Z' and 'a'
		if ( head > 25 && head < 32 )
			return false; // If not in alphabet, throw error
		//Check for the 6 characters between 'Z' and 'a'
		if( head > 31 )
			head -= 6;
		if ( head < 0 || head > 51 )
			return false; // If not in alphabet, throw error
		if( switchArray[ head ] == -1 )	//No words starting with this letter are present
			return false;
		return readSymbolArray( switchArray[ head ], word );
	}

	//*************** HELPER FUNCTIONS ***************
	private void fillSymbolArray( int location, char[] word ) {
		// Purpose: Fill the Symbol array following the Trie method starting at location
		
		// If word single char, it gets consumed in switch so we go to delimiter
		if ( word.length == 1 ) {
			setDelimiter( location );
		} else {
			for ( int i = 1; i <= word.length; i++ ) {
				if ( i == word.length ) { // If we're past last char in word, so time to put delimiter
					setDelimiter( location );
				} else if ( location == nextEmpty ) { // Regular empty symbol array
					setSymbol( location++, word[ i ] );
					nextEmpty = location;
				} else if ( word[ i ] != getSymbol( location ) ) {
					int tmp = checkNext( location );
					if ( tmp == -1 ) {
						setNext( location, nextEmpty );
						location = nextEmpty;
					} else {
						location = tmp;
					}
					i--; // Loop through again with different location now
				} else {
					location++; // If it get's here it means it's the right letter and it'll just continue to the next
				}
			}
		}
	}
	
	private int getSymbolLocation( int switchArrayLetter ) {
		// Purpose: Takes the location in the switch array and returns the location we should be going to in Symbol array
		if ( switchArray[ switchArrayLetter ] == -1 )
			switchArray[ switchArrayLetter ] = nextEmpty;
		return switchArray[ switchArrayLetter ];
	}

	private void setDelimiter( int location ) {
		// Purpose: Hops to end of Next Array and handles delimiter cases
		location = getEmptyNext( location );
		if ( location == nextEmpty ) {
			setSymbol( location, DELIMITER );
			nextEmpty++;
		} else {
			setNext( location, nextEmpty );
			setSymbol( nextEmpty++, DELIMITER );
		}
	}

	private int getEmptyNext( int location ) {
		// Purpose: Hops through the Next array until it gets to an empty spot in the array
		int nextHop = location;
		while ( nextHop != -1 ) { // Need to follow the next array hops till end
			location = nextHop;
			nextHop = checkNext( location );
		}
		return location;
	}

	private int checkNext( int location ) {
		// Purpose: Return the location of the Next array hop, or -1 if empty
		if ( getNext( location ) == -1 )
			return -1;
		//return nextArray.get( location );
		return getNext( location );
	}

	private boolean readSymbolArray( int location, char[] word ) {
		int letterNum = 1;	//Used to keep track of which letter is being checked
		
		/* 
		 * Move character by character of the word looking in the symbolArray
		 * for the same sequence of characters
		*/
		while( letterNum < word.length )
		{
			//If the characters are the same, increment both pointers
			if(word[ letterNum ] == symbolArray[ location ])
			{
				letterNum++;
				location++;
			}
			//Otherwise get the value of the nextArray at the current location
			else
			{
				location = nextArray[ location ];
				//If the nextArray is empty then the word is not there so return false
				if(location == -1)
					return false;
			}
		}
		
		/* 
		 * At the end of the word, look for the delimiter.
		 * Return true if the delimiter is at the current location
		 */
		do {
			if(symbolArray[ location ] == '@')
				return true;
			location = nextArray[ location ];	//Not the delimiter, get the next location
		} while(location != -1);
		return false;	//The delimiter was not found therefore the word is not here
	}

	//*************** OUTPUT ***************
	public String toString() {
		String output = "";
		for ( int i = 0; i < switchArray.length; i++ ) {
			if ( i < 26 )
				output += String.format("%2c ", (char)(i + 65));		//Left-justify the character in a field of size 3
			else
				output += String.format("%2c ", (char)(i + 71));		//Left-justify the character in a field of size 3
		}
		output += "\n";
		for ( int i = 0; i < switchArray.length; i++ ) {
			//if ( switchArray[ i ] != -1 )
				output += String.format("%2d ", switchArray[ i ]);		//Left-justify the switchArray value in a field of size 3
		}
		output += "\n\n";
		for ( int i = 0; i < nextEmpty; i++ ) {
			output += String.format("%2d ", i);		//Left-justify the value of i in a field of size 3
		}
		output += "\n";

		for ( int i = 0; i < nextEmpty; i++ ) {
			output += String.format("%2c ", getSymbol( i ));	//Left-justify the Symbol at location i in a field of size 3
		}
		output += "\n";
		for ( int i = 0; i < nextEmpty; i++ ) {
			if ( getNext( i ) == -1 )
				output += String.format("%2c ", '-');		//Left-justify a dash in a field of size 3
			else
				output += String.format("%2d ", getNext( i ));	//Left-justify the next value in a field of size 3
		}
	
		return output;
	}
	
	//*********TESTMAIN*************
	public static void main( String[] args ) {
		Trie test = new Trie();
		test.inputWord( "break" );
		test.inputWord( "break" );
		test.inputWord( "boolean" );
		test.inputWord( "double" );
		test.inputWord( "bit" );
		test.inputWord( "dot" );
		test.inputWord( "bot" );
		test.inputWord( "bre" );
		test.inputWord( "hippopotamus" );
		test.inputWord( "hippo" );
		test.inputWord( "hiptoss" );
		test.inputWord( "zebra" );
		System.out.println( test );
	}
}
