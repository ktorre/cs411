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
<YYINITIAL> "newarraw" { return new Yytoken( 12, yytext() ); }
<YYINITIAL> "println" { return new Yytoken( 13, yytext() ); }
<YYINITIAL> "readln" { return new Yytoken( 14, yytext() ); }
<YYINITIAL> "return" { return new Yytoken( 15, yytext() ); }
<YYINITIAL> "string" { return new Yytoken( 16, yytext() ); }
<YYINITIAL> "true" { return new Yytoken( 17, yytext() ); }
<YYINITIAL> "void" { return new Yytoken( 18, yytext() ); }
<YYINITIAL> "while" { return new Yytoken( 19, yytext() ); }

<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}
<YYINITIAL> \n {}

<YYINITIAL> . {
	System.out.println( "Token not implemented yet: " + yytext() );
	
}
