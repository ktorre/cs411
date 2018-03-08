/*  
    
    Alex Winger
    Kevin De La Torre
    Luke Walsh

    CS411 
    Project #1
    Due: 1/30/2018
    
    Purpose Of This Program:
	The purpose of this program is take in an input file containing code,
	the program will read through and identify tokens from each keyword in
	the code. It will assign these tokens a numerical value, and store the
	keyword or id inside of a trie structure. It will also output the tokens
	in the order that they were inputted to the program along with the 
	contents inside of the trie structure. The two possible forms of input
	are either line by line through the command line, and the program will
	act as an interpreter to return the tokens in each line or through a file
	passed as an argument, and the program will return the complete list of
	tokens.

    Tips For The Program:
	Comments are considered // to denote that the rest of a line is a comment
	or /* begins a multi-line comment that is completed when the program 
	encounters the same symbol reversed. There are 46 possible tokens, with the
	first 18 being keywords that create tokens of the same name, the next 23 are
	operators or values that are stored as the english word for the operator or
	value, and the remaining 5 tokens are for constants and ids which will vary
	depending on what the value of each is. Anything that is not recognized as a
	token will be outputted stating that it is not a token. When outputting from
	a file, the tokens correspond with the appropriate line.

    How To Run The Program:
	1.  Use the appropriate Main.java file to create the necessary files to run
	    program1.lex and store all class files inside a folder name JLex.
	2.  We have included a Makefile to assist with running programs. Next simply
	    type make in the command line.
	3.  Run the newly created file program1.java either with or without a file
	    as an argument.
	4.  To exit from interpreter mode press ^C at any time, and the program should
	    complete on its own if you attach a file as an argument.

*/

import java.io.FileReader;
import java.io.File;

class project1 {
	public static void main( String argv[] ) throws java.io.IOException {
		Trie table = new Trie();	//Holds the Trie table for the keywords and IDs
		//If there are arguments then use them for the filenames
		if ( argv.length > 0 ) {
			for ( int i = 0; i < argv.length; i++ ) {
				File file = new File( argv[ i ] );
				FileReader fr = new FileReader( file );
				//if the file works then create a Yylex and Yytoken object
				Yylex yy = new Yylex( fr );
				Yytoken t;
				//a while loop for to scan the file for every token
				while ( ( t = yy.yylex() ) != null )  {
					//if the token is a keyword then add it to the trie table
					if ( ( t.t_id >= 0 && t.t_id <= 17 ) || t.t_id == 46 )
						table.inputWord( t.toString() );
					//if the token is an id then print out id instead of the previous word
					if ( t.t_id == 46 ) {
						System.out.print( "id" + " " );
					} else {
				   		System.out.print( t + " " );
					}
			    	}
		    	}
		} else {
			//if no file is found, then enter interpreter mode
			System.out.println( "INTERPRETER MODE" );
			Yylex yy = new Yylex( System.in );
			Yytoken t;
			//loops continuously for user input
			while ( ( t = yy.yylex() ) != null ) {
				//enters the token into the trie structure if it is a
				//keyword or id
				if ( ( t.t_id >= 0 && t.t_id <= 17 ) || t.t_id == 46 )
					table.inputWord( t.toString() );
				//print out the token
				System.out.println( t );
			}
		}
		//print out the contents of the trie structure
		System.out.println( "\n" + table );
	}
}

class Yytoken {
    //Yytokens hold the number of the token and the text of the toke
	Yytoken ( int id, String text ) {
		t_id = id;
		t_text = text;
	}
	
	public int t_id; // Token ID
	private String t_text; // Matching token string
	public String toString() {
		//return "Token #" + t_id + ": " + t_text;
		return t_text;
	}
}

%%
ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\0]
WHITE_SPACE_CHAR=[\n\ \t\b\0]
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*

%%

<YYINITIAL> "boolean" { return new Yytoken( 0, yytext() ); }
<YYINITIAL> "break" { return new Yytoken( 1, yytext() ); }
<YYINITIAL> "class" { return new Yytoken( 2, yytext() ); }
<YYINITIAL> "double" { return new Yytoken( 3, yytext() ); }
<YYINITIAL> "else" { return new Yytoken( 4, yytext() ); }
<YYINITIAL> "extends" { return new Yytoken( 5, yytext() ); }
<YYINITIAL> "for" { return new Yytoken( 6, yytext() ); }
<YYINITIAL> "if" { return new Yytoken( 7, yytext() ); }
<YYINITIAL> "implements" { return new Yytoken( 8, yytext() ); }
<YYINITIAL> "int" { return new Yytoken( 9, yytext() ); }
<YYINITIAL> "interface" { return new Yytoken( 10, yytext() ); }
<YYINITIAL> "newarray" { return new Yytoken( 11, yytext() ); }
<YYINITIAL> "println" { return new Yytoken( 12, yytext() ); }
<YYINITIAL> "readln" { return new Yytoken( 13, yytext() ); }
<YYINITIAL> "return" { return new Yytoken( 14, yytext() ); }
<YYINITIAL> "string" { return new Yytoken( 15, yytext() ); }
<YYINITIAL> "void" { return new Yytoken( 16, yytext() ); }
<YYINITIAL> "while" { return new Yytoken( 17, yytext() ); }
<YYINITIAL> "+" { return new Yytoken( 18, "plus" ); }
<YYINITIAL> "-" { return new Yytoken( 19, "minus" ); }
<YYINITIAL> "*" { return new Yytoken( 20, "multiplication" ); }
<YYINITIAL> "/" { return new Yytoken( 21, "division" ); }
<YYINITIAL> "%" { return new Yytoken( 22, "mod" ); }
<YYINITIAL> "<" { return new Yytoken( 23, "less" ); }
<YYINITIAL> "<=" { return new Yytoken( 24, "lessequal" ); }
<YYINITIAL> ">" { return new Yytoken( 25, "greater" ); }
<YYINITIAL> ">=" { return new Yytoken( 26, "greaterequal" ); }
<YYINITIAL> "==" { return new Yytoken( 27, "equal" ); }
<YYINITIAL> "!=" { return new Yytoken( 28, "notequal" ); }
<YYINITIAL> "&&" { return new Yytoken( 29, "and" ); }
<YYINITIAL> "||" { return new Yytoken( 30, "or" ); }
<YYINITIAL> "!" { return new Yytoken( 31, "not" ); }
<YYINITIAL> "=" { return new Yytoken( 32, "assignop" ); }
<YYINITIAL> ";" { return new Yytoken( 33, "semicolon" ); }
<YYINITIAL> "," { return new Yytoken( 34, "comma" ); }
<YYINITIAL> "." { return new Yytoken( 35, "period" ); }
<YYINITIAL> "(" { return new Yytoken( 36, "leftparen" ); }
<YYINITIAL> ")" { return new Yytoken( 37, "rightparen" ); }
<YYINITIAL> "[" { return new Yytoken( 38, "leftbracket" ); }
<YYINITIAL> "]" { return new Yytoken( 39, "rightbracket" ); }
<YYINITIAL> "{" { return new Yytoken( 40, "leftbrace" ); }
<YYINITIAL> "}" { return new Yytoken( 41, "rightbrace" ); }
<YYINITIAL> ({DIGIT}+)|(("0x"|"0X")({DIGIT}|[a-f]|[A-F])({DIGIT}|[a-f]|[A-F])*) { return new Yytoken( 42, "intconstant" ); }
<YYINITIAL> {DIGIT}{DIGIT}*"."{DIGIT}*(("E"|"e")("+"|"-")?{DIGIT})?{DIGIT}* { return new Yytoken( 43, "doubleconstant" ); }
<YYINITIAL> \"{STRING_TEXT}\" { return new Yytoken( 44, "stringconstant" ); }
<YYINITIAL> "true"|"false" { return new Yytoken( 45, "booleanconstant" ); }
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|"_")* { return new Yytoken( 46, yytext() ); }

<YYINITIAL> ^"//".*\n {}
<YYINITIAL> "//".* {}
<YYINITIAL> "/*"({COMMENT_TEXT}|\n)*"*/"\n {}
<YYINITIAL> "/*"({COMMENT_TEXT}|\n)*"*/"{WHITE_SPACE_CHAR}*\n {}

<YYINITIAL> ^\n {}
<YYINITIAL> {WHITE_SPACE_CHAR}*\n { System.out.println( "" ); }
<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}

<YYINITIAL> . {
	System.out.println( "Token not implemented yet: " + yytext() );
	
}
