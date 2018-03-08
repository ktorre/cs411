import java_cup.runtime.*;
import java.io.File;
import java.io.FileReader;

%%
%cup
%state COMMENT
ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
WHITE_SPACE_CHAR=[\n\ \t\b\012]
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
%class lexer 
%{

    public static void main( String argv[] ) throws java.io.IOException {
	ComplexSymbolFactory s = new ComplexSymbolFactory();
	lexer l = new lexer( System.in, s );
    }


    private ComplexSymbolFactory sf;
    public lexer( java.io.InputStream s, ComplexSymbolFactory sf ) {
	this( s );
	this.sf=sf;
    }

    public Symbol symbol( String text, int code ) {
	return sf.newSymbol( text, code );
    }

%}
	
%eofval{
    return sf.newSymbol( "EOF", sym.EOF );
%eofval}

%%
<YYINITIAL> "_boolean" { return symbol( "_plus", sym._plus ); }
<YYINITIAL> "_break" { return symbol( "-", sym._minus); }

