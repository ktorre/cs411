class project1 {
	public static void main( String argv[] ) throws java.io.IOException {
		Yylex yy = new Yylex( System.in );
		Yytoken t;
		while ( ( t = yy.yylex() ) != null )
			System.out.println( t );
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
<YYINITIAL> "false" { return new Yytoken( 6, yytext() ); }
<YYINITIAL> "for" { return new Yytoken( 7, yytext() ); }
<YYINITIAL> "if" { return new Yytoken( 8, yytext() ); }
<YYINITIAL> "implements" { return new Yytoken( 9, yytext() ); }
<YYINITIAL> "int" { return new Yytoken( 10, yytext() ); }
<YYINITIAL> "interface" { return new Yytoken( 11, yytext() ); }
<YYINITIAL> "newarray" { return new Yytoken( 12, yytext() ); }
<YYINITIAL> "println" { return new Yytoken( 13, yytext() ); }
<YYINITIAL> "readln" { return new Yytoken( 14, yytext() ); }
<YYINITIAL> "return" { return new Yytoken( 15, yytext() ); }
<YYINITIAL> "string" { return new Yytoken( 16, yytext() ); }
<YYINITIAL> "true" { return new Yytoken( 17, yytext() ); }
<YYINITIAL> "void" { return new Yytoken( 18, yytext() ); }
<YYINITIAL> "while" { return new Yytoken( 19, yytext() ); }
<YYINITIAL> "plus" { return new Yytoken( 19, yytext() ); }
<YYINITIAL> "minus" { return new Yytoken( 19, yytext() ); }
<YYINITIAL> "multiplication" { return new Yytoken( 20, yytext() ); }
<YYINITIAL> "division" { return new Yytoken( 21, yytext() ); }
<YYINITIAL> "mod" { return new Yytoken( 22, yytext() ); }
<YYINITIAL> "less" { return new Yytoken( 23, yytext() ); }
<YYINITIAL> "lessequal" { return new Yytoken( 24, yytext() ); }
<YYINITIAL> "greater" { return new Yytoken( 25, yytext() ); }
<YYINITIAL> "greaterequal" { return new Yytoken( 26, yytext() ); }
<YYINITIAL> "equal" { return new Yytoken( 27, yytext() ); }
<YYINITIAL> "notequal" { return new Yytoken( 28, yytext() ); }
<YYINITIAL> "and" { return new Yytoken( 29, yytext() ); }
<YYINITIAL> "or" { return new Yytoken( 30, yytext() ); }
<YYINITIAL> "not" { return new Yytoken( 31, yytext() ); }
<YYINITIAL> "assignop" { return new Yytoken( 32, yytext() ); }
<YYINITIAL> "semicolon" { return new Yytoken( 33, yytext() ); }
<YYINITIAL> "comma" { return new Yytoken( 34, yytext() ); }
<YYINITIAL> "period" { return new Yytoken( 35, yytext() ); }
<YYINITIAL> "leftparen" { return new Yytoken( 36, yytext() ); }
<YYINITIAL> "rightparen" { return new Yytoken( 37, yytext() ); }
<YYINITIAL> "leftbracket" { return new Yytoken( 38, yytext() ); }
<YYINITIAL> "rightbracket" { return new Yytoken( 39, yytext() ); }
<YYINITIAL> "leftbrace" { return new Yytoken( 40, yytext() ); }
<YYINITIAL> "rightbrace" { return new Yytoken( 41, yytext() ); }
<YYINITIAL> "intconstant" { return new Yytoken( 42, yytext() ); }
<YYINITIAL> "doubleconstant" { return new Yytoken( 43, yytext() ); }
<YYINITIAL> "stringconstant" { return new Yytoken( 44, yytext() ); }
<YYINITIAL> "booleanconstant" { return new Yytoken( 45, yytext() ); }
<YYINITIAL> "id" { return new Yytoken( 46, yytext() ); }


<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}
<YYINITIAL> \n {}

<YYINITIAL> . {
	System.out.println( "Token not implemented yet: " + yytext() );
	
}
