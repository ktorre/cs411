import java.io.FileReader;
import java.io.File;

class project1 {
	public static void main( String argv[] ) throws java.io.IOException {
		if ( argv.length > 0 ) {
		    for ( int i = 0; i < argv.length; i++ ) {
			    File file = new File( argv[ 0 ] );
			    FileReader fr = new FileReader( file );
			    Yylex yy = new Yylex( fr );
			    Yytoken t;

			    while ( ( t = yy.yylex() ) != null )  {
				System.out.println( t );
			    }
		    }
		} else {
			System.out.println( "INTERPRETER MODE" );
			Yylex yy = new Yylex( System.in );
			Yytoken t;
			while ( ( t = yy.yylex() ) != null )
				System.out.println( t );
			}
		}
}

class Yytoken {
	Yytoken ( int id, String text ) {
		t_id = id;
		t_text = text;
	}
	
	private int t_id; // Token ID
	private String t_text; // Matching token string
	public String toString() {
		return "Token #" + t_id + ": " + t_text;
	}
}

%%
%state COMMENT
ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
WHITE_SPACE_CHAR=[\n\ \t\b\012]
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
<YYINITIAL> "=" { return new Yytoken( 200, "assignop" ); }
<YYINITIAL> "==" { return new Yytoken( 27, "equal" ); }
<YYINITIAL> "!=" { return new Yytoken( 28, "notequal" ); }
<YYINITIAL> "&&" { return new Yytoken( 29, "and" ); }
<YYINITIAL> "||" { return new Yytoken( 30, "or" ); }
<YYINITIAL> "!" { return new Yytoken( 31, "not" ); }
<YYINITIAL> "assignop" { return new Yytoken( 32, "assignop" ); }
<YYINITIAL> ";" { return new Yytoken( 33, "semicolon" ); }
<YYINITIAL> "," { return new Yytoken( 34, "comma" ); }
<YYINITIAL> "." { return new Yytoken( 35, "period" ); }
<YYINITIAL> "(" { return new Yytoken( 36, "leftparen" ); }
<YYINITIAL> ")" { return new Yytoken( 37, "rightparen" ); }
<YYINITIAL> "[" { return new Yytoken( 38, "leftbracket" ); }
<YYINITIAL> "]" { return new Yytoken( 39, "rightbracket" ); }
<YYINITIAL> "{" { return new Yytoken( 40, "leftbrace" ); }
<YYINITIAL> "}" { return new Yytoken( 41, "rightbrace" ); }
<YYINITIAL> {DIGIT}{DIGIT}* { return new Yytoken( 42, "intconstant" ); }
<YYINITIAL> {DIGIT}{DIGIT}*"."{DIGIT}*(("E"|"e")("+"|"-")?{DIGIT})?{DIGIT}* { return new Yytoken( 43, "doubleconstant" ); }
<YYINITIAL> \".*\" { return new Yytoken( 44, "stringconstant" ); }
<YYINITIAL> "true"|"false" { return new Yytoken( 45, "booleanconstant" ); }
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT})* { return new Yytoken( 46, "id" ); }
<YYINITIAL> ({DIGIT}{DIGIT}*)|(("0x"|"0X")({DIGIT}|[a-f]|[A-F])({DIGIT}|[a-f]|[A-F])*) { return new Yytoken(51, "intconstant" ); }

<YYINITIAL> "//".* {}
<YYINITIAL> "/*" { yybegin( COMMENT ); System.out.println( "Entering comment" ); }
<COMMENT> "*/" { yybegin( YYINITIAL ); System.out.println( "Exiting comment" ); }
<COMMENT> {COMMENT_TEXT} { System.out.println( "In comment" ); }

<YYINITIAL, COMMENT> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}
<YYINITIAL, COMMENT> \n {}

<YYINITIAL> . {
	System.out.println( "Token not implemented yet: " + yytext() );
	
}
