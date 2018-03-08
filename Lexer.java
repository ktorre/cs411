import java_cup.runtime.*;
import java.io.File;
import java.io.FileReader;


class Lexer  implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

    private ComplexSymbolFactory sf;
    public Lexer( java.io.Reader s, ComplexSymbolFactory sf ) {
	this( s );
	this.sf=sf;
    }
    public Symbol symbol( String text, int code ) {
	return sf.newSymbol( text, code );
    }
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer  (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer  (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer  () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int yy_state_dtrans[] = {
		0,
		56
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NOT_ACCEPT,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NOT_ACCEPT,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NOT_ACCEPT,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"56:8,52:2,53,56:2,51,56:18,52,32,49,56:2,28,33,56,38,39,26,24,36,25,37,27,4" +
"5,44:9,56,35,29,30,31,56:2,47:4,48,47,54:17,46,54:2,40,50,41,56,55,56,5,1,9" +
",11,4,15,21,23,16,54,8,3,17,6,2,18,54,7,10,14,12,22,19,13,20,54,42,34,43,56" +
":2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,161,
"0,1,2,1:3,3,1,4,5,6,7,8,9,1:6,10,11,12,13,14,1,15,1:9,16,1,14,17,14:16,18,1" +
",19,20,21,22,23,24,25,24,26,24,19,27,28,29,1,30,31,32,33,34,35,36,37,23,38," +
"39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63," +
"64,65,66,67,68,69,70,71,72,73,74,75,14,76,77,78,79,80,81,82,83,84,85,86,87," +
"88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,14,105,106,107,108," +
"109,110,111,112,113,114")[0];

	private int yy_nxt[][] = unpackFromString(115,57,
"1,2,150:2,116,150,153,154,150,155,156,157,150:2,117,98,59,150,158,159,150:2" +
",118,150,3,4,5,6,7,8,9,10,11,12,60,13,14,15,16,17,18,19,20,21,22,61,150:3,6" +
"7,72,-1,23:2,150,72:2,-1:58,150,160,150:4,119,150:16,-1:20,120:2,150:3,-1:5" +
",150,120,-1:27,25,26,-1:59,27,-1:56,28,-1:56,29,-1:56,30,-1:59,31,-1:76,33," +
"-1:56,34,-1:56,35,-1:40,36,-1:6,22:2,-1:63,23:2,-1:4,150:23,-1:20,120:2,150" +
":3,-1:5,150,120,-1:2,26:50,-1,26,-1,26:3,-1:4,74,-1:39,36:2,-1:2,74,-1:9,15" +
"0:3,152,150:19,-1:20,120:2,150:3,-1:5,150,120,-1,1,64:25,83,85,64:24,69,23," +
"64:3,-1,68,-1:2,68:2,-1:3,68,-1,68,-1:3,68,-1:28,68:2,-1,68:2,-1:9,150:5,71" +
",150:8,24,150,128,150:6,-1:20,120:2,150:3,-1:5,150,120,-1:35,32,-1:35,58,-1" +
":23,36,-1:6,22:2,58,-1:54,62:2,-1:12,65:48,37,70,65:2,-1,65:3,-1,64:25,87,8" +
"5,64:25,-1,64:3,-1,150:6,38,150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2,64:2" +
"5,87,85,64:24,69,23,64:3,-1,65:48,63,70,65,77,79,65:3,-1,150:13,39,150:9,-1" +
":20,120:2,150:3,-1:5,150,120,-1:2,64:25,73,85,64:25,-1,64:3,-1:24,81:2,-1:1" +
"8,62:2,-1:12,150:3,40,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,64:25,87,7" +
"6,64:25,-1,64:3,-1,65:48,37,70,65,77,79,65:3,-1,150:3,41,150:19,-1:20,120:2" +
",150:3,-1:5,150,120,-1:51,65,-1,79:2,-1:4,150:10,42,150:12,-1:20,120:2,150:" +
"3,-1:5,150,120,-1:2,150:7,43,150:15,-1:20,120:2,150:3,-1:5,150,120,-1:2,64:" +
"25,73,57,64:25,-1,64:3,-1,150:9,44,150:13,-1:20,120:2,150:3,-1:5,150,120,-1" +
":2,64:25,-1,76,64:25,-1,64:3,-1,150:3,45,150:19,-1:20,120:2,150:3,-1:5,150," +
"120,-1:2,64:25,73,-1,64:25,-1,64:3,-1,150:5,46,150:17,-1:20,120:2,150:3,-1:" +
"5,150,120,-1:2,150:5,47,150:17,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:20,4" +
"8,150:2,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:3,49,150:19,-1:20,120:2,150" +
":3,-1:5,150,120,-1:2,150:5,50,150:17,-1:20,120:2,150:3,-1:5,150,120,-1:2,15" +
"0:9,51,150:13,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:5,52,150:17,-1:20,120" +
":2,150:3,-1:5,150,120,-1:2,150:19,53,150:3,-1:20,120:2,150:3,-1:5,150,120,-" +
"1:2,150:3,54,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:9,55,150:13,-1:" +
"20,120:2,150:3,-1:5,150,120,-1:2,150,66,150:2,127,150:18,-1:20,120:2,150:3," +
"-1:5,150,120,-1:2,150:9,75,150:13,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:1" +
"1,78,150:11,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:15,80,150:7,-1:20,120:2" +
",150:3,-1:5,150,120,-1:2,150:4,82,150:18,-1:20,120:2,150:3,-1:5,150,120,-1:" +
"2,150:9,84,150:13,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:9,78,150:13,-1:20" +
",120:2,150:3,-1:5,150,120,-1:2,150:2,86,150:20,-1:20,120:2,150:3,-1:5,150,1" +
"20,-1:2,150:2,88,150:20,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:6,89,150:16" +
",-1:20,120:2,150:3,-1:5,150,120,-1:2,150:5,90,150:17,-1:20,120:2,150:3,-1:5" +
",150,120,-1:2,150:2,91,150:20,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:4,92," +
"150:18,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:10,93,150:12,-1:20,120:2,150" +
":3,-1:5,150,120,-1:2,150:2,94,150:20,-1:20,120:2,150:3,-1:5,150,120,-1:2,15" +
"0:4,95,150:18,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:8,96,150:14,-1:20,120" +
":2,150:3,-1:5,150,120,-1:2,150:13,97,150:9,-1:20,120:2,150:3,-1:5,150,120,-" +
"1:2,150:2,99,150:9,121,150:10,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:6,100" +
",150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2,150,101,150:21,-1:20,120:2,150:" +
"3,-1:5,150,120,-1:2,150:3,102,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,15" +
"0:13,132,150:9,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:18,133,150:4,-1:20,1" +
"20:2,150:3,-1:5,150,120,-1:2,150:4,134,150:8,135,150:9,-1:20,120:2,150:3,-1" +
":5,150,120,-1:2,150:4,103,150:18,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:6," +
"136,150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:11,137,150:11,-1:20,120:" +
"2,150:3,-1:5,150,120,-1:2,150:2,104,150:20,-1:20,120:2,150:3,-1:5,150,120,-" +
"1:2,150:17,151,150:5,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:15,138,150:7,-" +
"1:20,120:2,150:3,-1:5,150,120,-1:2,150:15,105,150:7,-1:20,120:2,150:3,-1:5," +
"150,120,-1:2,150:2,139,150:20,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:3,140" +
",150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:4,141,150:18,-1:20,120:2,15" +
"0:3,-1:5,150,120,-1:2,150:10,106,150:12,-1:20,120:2,150:3,-1:5,150,120,-1:2" +
",150:11,107,150:11,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:15,108,150:7,-1:" +
"20,120:2,150:3,-1:5,150,120,-1:2,109,150:22,-1:20,120:2,150:3,-1:5,150,120," +
"-1:2,150:5,143,150:17,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:3,110,150:19," +
"-1:20,120:2,150:3,-1:5,150,120,-1:2,150:5,111,150:17,-1:20,120:2,150:3,-1:5" +
",150,120,-1:2,150:6,144,150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:3,14" +
"6,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:13,112,150:9,-1:20,120:2,1" +
"50:3,-1:5,150,120,-1:2,150:6,113,150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2" +
",150:14,147,150:8,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:16,148,150:6,-1:2" +
"0,120:2,150:3,-1:5,150,120,-1:2,150:4,114,150:18,-1:20,120:2,150:3,-1:5,150" +
",120,-1:2,150:3,149,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:5,115,15" +
"0:17,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:2,142,150:20,-1:20,120:2,150:3" +
",-1:5,150,120,-1:2,150:6,145,150:16,-1:20,120:2,150:3,-1:5,150,120,-1:2,150" +
":3,122,150:19,-1:20,120:2,150:3,-1:5,150,120,-1:2,150:3,123,150:19,-1:20,12" +
"0:2,150:3,-1:5,150,120,-1:2,150:2,124,150:20,-1:20,120:2,150:3,-1:5,150,120" +
",-1:2,150:13,125,150:9,-1:20,120:2,150:3,-1:5,150,120,-1:2,150,126,150:21,-" +
"1:20,120:2,150:3,-1:5,150,120,-1:2,150:6,129,150:16,-1:20,120:2,150:3,-1:5," +
"150,120,-1:2,150:22,130,-1:20,120:2,150:3,-1:5,150,120,-1:2,150,131,150:21," +
"-1:20,120:2,150:3,-1:5,150,120,-1");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

    return sf.newSymbol( "EOF", sym.EOF );
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return symbol( "_id", sym._id ); }
					case -3:
						break;
					case 3:
						{ return symbol( "_plus", sym._plus ); }
					case -4:
						break;
					case 4:
						{ return symbol( "_minus", sym._minus ); }
					case -5:
						break;
					case 5:
						{ return symbol( "_multiplication", sym._multiplication ); }
					case -6:
						break;
					case 6:
						{ return symbol( "_division", sym._division ); }
					case -7:
						break;
					case 7:
						{ return symbol( "_mod", sym._mod ); }
					case -8:
						break;
					case 8:
						{ return symbol( "_less", sym._less ); }
					case -9:
						break;
					case 9:
						{ return symbol( "_assignop", sym._assignop ); }
					case -10:
						break;
					case 10:
						{ return symbol( "_greater", sym._greater ); }
					case -11:
						break;
					case 11:
						{ return symbol( "_not", sym._not ); }
					case -12:
						break;
					case 12:
						{
	System.out.println( "Token not implemented yet: " + sym.error );
}
					case -13:
						break;
					case 13:
						{ return symbol( "_semicolon", sym._semicolon ); }
					case -14:
						break;
					case 14:
						{ return symbol( "_comma", sym._comma ); }
					case -15:
						break;
					case 15:
						{ return symbol( "_period", sym._period ); }
					case -16:
						break;
					case 16:
						{ return symbol( "_leftparen", sym._leftparen ); }
					case -17:
						break;
					case 17:
						{ return symbol( "_rightparen", sym._rightparen ); }
					case -18:
						break;
					case 18:
						{ return symbol( "_leftbracket", sym._leftbracket ); }
					case -19:
						break;
					case 19:
						{ return symbol( "_rightbracket", sym._rightbracket ); }
					case -20:
						break;
					case 20:
						{ return symbol( "_leftbrace", sym._leftbrace ); }
					case -21:
						break;
					case 21:
						{ return symbol( "_rightbrace", sym._rightbrace ); }
					case -22:
						break;
					case 22:
						{ return symbol( "_intconstant", sym._intconstant ); }
					case -23:
						break;
					case 23:
						{}
					case -24:
						break;
					case 24:
						{ return symbol( "_if", sym._if ); }
					case -25:
						break;
					case 25:
						{ yybegin( COMMENT ); }
					case -26:
						break;
					case 26:
						{}
					case -27:
						break;
					case 27:
						{ return symbol( "_lessequal", sym._lessequal ); }
					case -28:
						break;
					case 28:
						{ return symbol( "_equal", sym._equal ); }
					case -29:
						break;
					case 29:
						{ return symbol( "_greaterequal", sym._greaterequal ); }
					case -30:
						break;
					case 30:
						{ return symbol( "_notequal", sym._notequal ); }
					case -31:
						break;
					case 31:
						{ return symbol( "_and", sym._and ); }
					case -32:
						break;
					case 32:
						{ return symbol( "_or", sym._or ); }
					case -33:
						break;
					case 33:
						{ return symbol( "_semicolon", sym._semicolon ); }
					case -34:
						break;
					case 34:
						{ return symbol( "_leftbrace", sym._leftbrace ); }
					case -35:
						break;
					case 35:
						{ return symbol( "_rightbrace", sym._rightbrace ); }
					case -36:
						break;
					case 36:
						{ return symbol( "_doubleconstant", sym._doubleconstant ); }
					case -37:
						break;
					case 37:
						{ return symbol( "_stringconstant", sym._stringconstant ); }
					case -38:
						break;
					case 38:
						{ return symbol( "_for", sym._for ); }
					case -39:
						break;
					case 39:
						{ return symbol( "_int", sym._int ); }
					case -40:
						break;
					case 40:
						{ return symbol( "_else", sym._else ); }
					case -41:
						break;
					case 41:
						{ return symbol( "_booleanconstant", sym._booleanconstant ); }
					case -42:
						break;
					case 42:
						{ return symbol( "_void", sym._void ); }
					case -43:
						break;
					case 43:
						{ return symbol( "_break", sym._break ); }
					case -44:
						break;
					case 44:
						{ return symbol( "_class", sym._class ); }
					case -45:
						break;
					case 45:
						{ return symbol( "_while", sym._while); }
					case -46:
						break;
					case 46:
						{ return symbol( "_readln", sym._readln ); }
					case -47:
						break;
					case 47:
						{ return symbol( "_return", sym._return ); }
					case -48:
						break;
					case 48:
						{ return symbol( "_string", sym._string ); }
					case -49:
						break;
					case 49:
						{ return symbol( "_double", sym._double ); }
					case -50:
						break;
					case 50:
						{ return symbol( "_boolean", sym._boolean); }
					case -51:
						break;
					case 51:
						{ return symbol( "_extends", sym._extends ); }
					case -52:
						break;
					case 52:
						{ return symbol( "_println", sym._println ); }
					case -53:
						break;
					case 53:
						{ return symbol( "_newarray", sym._newarray ); }
					case -54:
						break;
					case 54:
						{ return symbol( "_interface", sym._interface ); }
					case -55:
						break;
					case 55:
						{ return symbol( "_implemens", sym._implements ); }
					case -56:
						break;
					case 56:
						{}
					case -57:
						break;
					case 57:
						{ yybegin( YYINITIAL ); }
					case -58:
						break;
					case 59:
						{ return symbol( "_id", sym._id ); }
					case -59:
						break;
					case 60:
						{
	System.out.println( "Token not implemented yet: " + sym.error );
}
					case -60:
						break;
					case 61:
						{ return symbol( "_intconstant", sym._intconstant ); }
					case -61:
						break;
					case 62:
						{ return symbol( "_doubleconstant", sym._doubleconstant ); }
					case -62:
						break;
					case 63:
						{ return symbol( "_stringconstant", sym._stringconstant ); }
					case -63:
						break;
					case 64:
						{}
					case -64:
						break;
					case 66:
						{ return symbol( "_id", sym._id ); }
					case -65:
						break;
					case 67:
						{
	System.out.println( "Token not implemented yet: " + sym.error );
}
					case -66:
						break;
					case 68:
						{ return symbol( "_intconstant", sym._intconstant ); }
					case -67:
						break;
					case 69:
						{}
					case -68:
						break;
					case 71:
						{ return symbol( "_id", sym._id ); }
					case -69:
						break;
					case 72:
						{
	System.out.println( "Token not implemented yet: " + sym.error );
}
					case -70:
						break;
					case 73:
						{}
					case -71:
						break;
					case 75:
						{ return symbol( "_id", sym._id ); }
					case -72:
						break;
					case 76:
						{}
					case -73:
						break;
					case 78:
						{ return symbol( "_id", sym._id ); }
					case -74:
						break;
					case 80:
						{ return symbol( "_id", sym._id ); }
					case -75:
						break;
					case 82:
						{ return symbol( "_id", sym._id ); }
					case -76:
						break;
					case 84:
						{ return symbol( "_id", sym._id ); }
					case -77:
						break;
					case 86:
						{ return symbol( "_id", sym._id ); }
					case -78:
						break;
					case 88:
						{ return symbol( "_id", sym._id ); }
					case -79:
						break;
					case 89:
						{ return symbol( "_id", sym._id ); }
					case -80:
						break;
					case 90:
						{ return symbol( "_id", sym._id ); }
					case -81:
						break;
					case 91:
						{ return symbol( "_id", sym._id ); }
					case -82:
						break;
					case 92:
						{ return symbol( "_id", sym._id ); }
					case -83:
						break;
					case 93:
						{ return symbol( "_id", sym._id ); }
					case -84:
						break;
					case 94:
						{ return symbol( "_id", sym._id ); }
					case -85:
						break;
					case 95:
						{ return symbol( "_id", sym._id ); }
					case -86:
						break;
					case 96:
						{ return symbol( "_id", sym._id ); }
					case -87:
						break;
					case 97:
						{ return symbol( "_id", sym._id ); }
					case -88:
						break;
					case 98:
						{ return symbol( "_id", sym._id ); }
					case -89:
						break;
					case 99:
						{ return symbol( "_id", sym._id ); }
					case -90:
						break;
					case 100:
						{ return symbol( "_id", sym._id ); }
					case -91:
						break;
					case 101:
						{ return symbol( "_id", sym._id ); }
					case -92:
						break;
					case 102:
						{ return symbol( "_id", sym._id ); }
					case -93:
						break;
					case 103:
						{ return symbol( "_id", sym._id ); }
					case -94:
						break;
					case 104:
						{ return symbol( "_id", sym._id ); }
					case -95:
						break;
					case 105:
						{ return symbol( "_id", sym._id ); }
					case -96:
						break;
					case 106:
						{ return symbol( "_id", sym._id ); }
					case -97:
						break;
					case 107:
						{ return symbol( "_id", sym._id ); }
					case -98:
						break;
					case 108:
						{ return symbol( "_id", sym._id ); }
					case -99:
						break;
					case 109:
						{ return symbol( "_id", sym._id ); }
					case -100:
						break;
					case 110:
						{ return symbol( "_id", sym._id ); }
					case -101:
						break;
					case 111:
						{ return symbol( "_id", sym._id ); }
					case -102:
						break;
					case 112:
						{ return symbol( "_id", sym._id ); }
					case -103:
						break;
					case 113:
						{ return symbol( "_id", sym._id ); }
					case -104:
						break;
					case 114:
						{ return symbol( "_id", sym._id ); }
					case -105:
						break;
					case 115:
						{ return symbol( "_id", sym._id ); }
					case -106:
						break;
					case 116:
						{ return symbol( "_id", sym._id ); }
					case -107:
						break;
					case 117:
						{ return symbol( "_id", sym._id ); }
					case -108:
						break;
					case 118:
						{ return symbol( "_id", sym._id ); }
					case -109:
						break;
					case 119:
						{ return symbol( "_id", sym._id ); }
					case -110:
						break;
					case 120:
						{ return symbol( "_id", sym._id ); }
					case -111:
						break;
					case 121:
						{ return symbol( "_id", sym._id ); }
					case -112:
						break;
					case 122:
						{ return symbol( "_id", sym._id ); }
					case -113:
						break;
					case 123:
						{ return symbol( "_id", sym._id ); }
					case -114:
						break;
					case 124:
						{ return symbol( "_id", sym._id ); }
					case -115:
						break;
					case 125:
						{ return symbol( "_id", sym._id ); }
					case -116:
						break;
					case 126:
						{ return symbol( "_id", sym._id ); }
					case -117:
						break;
					case 127:
						{ return symbol( "_id", sym._id ); }
					case -118:
						break;
					case 128:
						{ return symbol( "_id", sym._id ); }
					case -119:
						break;
					case 129:
						{ return symbol( "_id", sym._id ); }
					case -120:
						break;
					case 130:
						{ return symbol( "_id", sym._id ); }
					case -121:
						break;
					case 131:
						{ return symbol( "_id", sym._id ); }
					case -122:
						break;
					case 132:
						{ return symbol( "_id", sym._id ); }
					case -123:
						break;
					case 133:
						{ return symbol( "_id", sym._id ); }
					case -124:
						break;
					case 134:
						{ return symbol( "_id", sym._id ); }
					case -125:
						break;
					case 135:
						{ return symbol( "_id", sym._id ); }
					case -126:
						break;
					case 136:
						{ return symbol( "_id", sym._id ); }
					case -127:
						break;
					case 137:
						{ return symbol( "_id", sym._id ); }
					case -128:
						break;
					case 138:
						{ return symbol( "_id", sym._id ); }
					case -129:
						break;
					case 139:
						{ return symbol( "_id", sym._id ); }
					case -130:
						break;
					case 140:
						{ return symbol( "_id", sym._id ); }
					case -131:
						break;
					case 141:
						{ return symbol( "_id", sym._id ); }
					case -132:
						break;
					case 142:
						{ return symbol( "_id", sym._id ); }
					case -133:
						break;
					case 143:
						{ return symbol( "_id", sym._id ); }
					case -134:
						break;
					case 144:
						{ return symbol( "_id", sym._id ); }
					case -135:
						break;
					case 145:
						{ return symbol( "_id", sym._id ); }
					case -136:
						break;
					case 146:
						{ return symbol( "_id", sym._id ); }
					case -137:
						break;
					case 147:
						{ return symbol( "_id", sym._id ); }
					case -138:
						break;
					case 148:
						{ return symbol( "_id", sym._id ); }
					case -139:
						break;
					case 149:
						{ return symbol( "_id", sym._id ); }
					case -140:
						break;
					case 150:
						{ return symbol( "_id", sym._id ); }
					case -141:
						break;
					case 151:
						{ return symbol( "_id", sym._id ); }
					case -142:
						break;
					case 152:
						{ return symbol( "_id", sym._id ); }
					case -143:
						break;
					case 153:
						{ return symbol( "_id", sym._id ); }
					case -144:
						break;
					case 154:
						{ return symbol( "_id", sym._id ); }
					case -145:
						break;
					case 155:
						{ return symbol( "_id", sym._id ); }
					case -146:
						break;
					case 156:
						{ return symbol( "_id", sym._id ); }
					case -147:
						break;
					case 157:
						{ return symbol( "_id", sym._id ); }
					case -148:
						break;
					case 158:
						{ return symbol( "_id", sym._id ); }
					case -149:
						break;
					case 159:
						{ return symbol( "_id", sym._id ); }
					case -150:
						break;
					case 160:
						{ return symbol( "_id", sym._id ); }
					case -151:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
