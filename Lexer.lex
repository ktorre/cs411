import java_cup.runtime.*;
import java.io.File;
import java.io.FileReader;

%%
%cup
ALPHA=[A-Za-z]
DIGIT=[0-9]
NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012]
WHITE_SPACE_CHAR=[\n\ \t\b\012]
STRING_TEXT=(\\\"|[^\n\"]|\\{WHITE_SPACE_CHAR}+\\)*
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
%class Lexer 
%{
    private ComplexSymbolFactory sf;
    public Lexer( java.io.Reader s, ComplexSymbolFactory sf ) {
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
<YYINITIAL> "boolean" { return symbol( "_boolean", sym._boolean); }
<YYINITIAL> "break" { return symbol( "_break", sym._break ); }
<YYINITIAL> "class" { return symbol( "_class", sym._class ); }
<YYINITIAL> "double" { return symbol( "_double", sym._double ); }
<YYINITIAL> "else" { return symbol( "_else", sym._else ); }
<YYINITIAL> "extends" { return symbol( "_extends", sym._extends ); }
<YYINITIAL> "for" { return symbol( "_for", sym._for ); }
<YYINITIAL> "if" { return symbol( "_if", sym._if ); }
<YYINITIAL> "implements" { return symbol( "_implemens", sym._implements ); }
<YYINITIAL> "int" { return symbol( "_int", sym._int ); }
<YYINITIAL> "interface" { return symbol( "_interface", sym._interface ); }
<YYINITIAL> "newarray" { return symbol( "_newarray", sym._newarray ); }
<YYINITIAL> "println" { return symbol( "_println", sym._println ); }
<YYINITIAL> "readln" { return symbol( "_readln", sym._readln ); }
<YYINITIAL> "return" { return symbol( "_return", sym._return ); }
<YYINITIAL> "string" { return symbol( "_string", sym._string ); }
<YYINITIAL> "void" { return symbol( "_void", sym._void ); }
<YYINITIAL> "while" { return symbol( "_while", sym._while); }
<YYINITIAL> "+" { return symbol( "_plus", sym._plus ); }
<YYINITIAL> "-" { return symbol( "_minus", sym._minus ); }
<YYINITIAL> "*" { return symbol( "_multiplication", sym._multiplication ); }
<YYINITIAL> "/" { return symbol( "_division", sym._division ); }
<YYINITIAL> "%" { return symbol( "_mod", sym._mod ); }
<YYINITIAL> "<" { return symbol( "_less", sym._less ); }
<YYINITIAL> "<=" { return symbol( "_lessequal", sym._lessequal ); }
<YYINITIAL> ">" { return symbol( "_greater", sym._greater ); }
<YYINITIAL> ">=" { return symbol( "_greaterequal", sym._greaterequal ); }
<YYINITIAL> "==" { return symbol( "_equal", sym._equal ); }
<YYINITIAL> "!=" { return symbol( "_notequal", sym._notequal ); }
<YYINITIAL> "&&" { return symbol( "_and", sym._and ); }
<YYINITIAL> "||" { return symbol( "_or", sym._or ); }
<YYINITIAL> "!" { return symbol( "_not", sym._not ); }
<YYINITIAL> "=" { return symbol( "_assignop", sym._assignop ); }
<YYINITIAL> ";" { return symbol( "_semicolon", sym._semicolon ); }
<YYINITIAL> "," { return symbol( "_comma", sym._comma ); }
<YYINITIAL> "." { return symbol( "_period", sym._period ); }
<YYINITIAL> "(" { return symbol( "_leftparen", sym._leftparen ); }
<YYINITIAL> ")" { return symbol( "_rightparen", sym._rightparen ); }
<YYINITIAL> "[" { return symbol( "_leftbracket", sym._leftbracket ); }
<YYINITIAL> "]" { return symbol( "_rightbracket", sym._rightbracket ); }
<YYINITIAL> "{" { return symbol( "_leftbrace", sym._leftbrace ); }
<YYINITIAL> "}" { return symbol( "_rightbrace", sym._rightbrace ); }
<YYINITIAL> ({DIGIT}+)|(("0x"|"0X")({DIGIT}|[a-f]|[A-F])({DIGIT}|[a-f]|[A-F])*) { return symbol( "_intconstant", sym._intconstant ); }
<YYINITIAL> {DIGIT}{DIGIT}*"."{DIGIT}*(("E"|"e")("+"|"-")?{DIGIT})?{DIGIT}* { return symbol( "_doubleconstant", sym._doubleconstant ); }
<YYINITIAL> \"{STRING_TEXT}\" { return symbol( "_stringconstant", sym._stringconstant ); }
<YYINITIAL> "true"|"false" { return symbol( "_booleanconstant", sym._booleanconstant ); }
<YYINITIAL> {ALPHA}({ALPHA}|{DIGIT}|"_")* { return symbol( "_id", sym._id ); }

<YYINITIAL> ^"//".*\n {}
<YYINITIAL> "//".* {}
<YYINITIAL> "/*"({COMMENT_TEXT}|\n)*"*/"\n {}
<YYINITIAL> "/*"({COMMENT_TEXT}|\n)*"*/"{WHITE_SPACE_CHAR}*\n {}

<YYINITIAL> ^\n {}
<YYINITIAL> {WHITE_SPACE_CHAR}*\n { }
<YYINITIAL> {NONNEWLINE_WHITE_SPACE_CHAR}+ {}

<YYINITIAL> . {
	System.out.println( "Token not implemented yet: " + sym.error );

}
