import java.util.ArrayList;

public class Trie {

	public static int DEFAULT_SIZE = 100; // Default allocation size for symbol/next array
	public static char DELIMITER = '@';

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
		//return nextArray.get( location );
		return nextArray[ location ];
	}

	private void setNext( int location, int value ) {
		//nextArray.set( location, value );
		nextArray[ location ] = value;
	}

	private char getSymbol( int location ) {
		//return symbolArray.get( location );
		return symbolArray[ location ];
	}

	private void setSymbol( int location, char value ) {
		//symbolArray.set( location, value );
		symbolArray[ location ] = value;
	}


	//*************** PUBLIC FUNCTIONS ***************
	// Overload input function to allow for string input
	public void inputWord( String word ) {
		inputWord( word.toCharArray() );
	}

	public int inputWord( char[] word ) {
		// Making it return an int so we can use it for error handling/reporting
		int head = ( (int)word[ 0 ] - 65 ); 
		if ( head < 0 || head > 51 )  { 
			return -1; // If not in alphabet, throw error
		} else {
			int symbolStartLocation = getSymbolLocation( head ); 
			fillSymbolArray( symbolStartLocation, word );
			return 0; // success
		}
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


	//*************** OUTPUT ***************
	public String toString() {
		// Yeah this parts gross af, I'll make it pretty for display and code later, but for now you can see the data
		String output = "";
		for ( int i = 0; i < switchArray.length; i++ ) {
			if ( switchArray[ i ] != -1 )
				output += " " + (char)(i + 65) + " ";
		}
		output += "\n";
		for ( int i = 0; i < switchArray.length; i++ ) {
			if ( switchArray[ i ] != -1 )
				output += " " + switchArray[ i ] + " ";
		}
		output += "\n";
		for ( int i = 0; i < nextEmpty; i++ ) {
			if ( getNext( i ) > 10 )
				output += " ";
			output += " " + i + " ";
		}
		output += "\n";

		for ( int i = 0; i < nextEmpty; i++ ) {
			if ( getNext( i ) > 10 && i < 10 )
				output += " ";
			output += " " + getSymbol( i ) + " ";
		}
		output += "\n";
		for ( int i = 0; i < nextEmpty; i++ ) {
			output += " ";
			if ( getNext( i ) == -1 )
				output += "-";
			else
				output += getNext( i );
			output += " ";
		}
	
		return output;
	}


	//*********TESTMAIN*************
	public static void main( String[] args ) {
		Trie test = new Trie();
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
		System.out.println( test );
	}
}
