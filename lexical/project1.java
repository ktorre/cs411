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


class Yylex {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Yylex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Yylex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Yylex () {
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
	private final int yy_state_dtrans[] = {
		0
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
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NOT_ACCEPT,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NOT_ACCEPT,
		/* 98 */ YY_NOT_ACCEPT,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NOT_ACCEPT,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NOT_ACCEPT,
		/* 103 */ YY_NOT_ACCEPT,
		/* 104 */ YY_NOT_ACCEPT,
		/* 105 */ YY_NOT_ACCEPT,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NOT_ACCEPT,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NOT_ACCEPT,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NOT_ACCEPT,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NOT_ACCEPT,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NOT_ACCEPT,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NOT_ACCEPT,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NOT_ACCEPT,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NOT_ACCEPT,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NOT_ACCEPT,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NOT_ACCEPT,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NOT_ACCEPT,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NOT_ACCEPT,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NOT_ACCEPT,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NOT_ACCEPT,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NOT_ACCEPT,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NOT_ACCEPT,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NOT_ACCEPT,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,130,
"45:8,46:2,47,45:2,0,45:18,46,32,44,45:2,28,33,45,38,39,26,24,36,25,37,27,45" +
":11,35,29,30,31,45:28,40,45,41,45:3,5,1,9,11,4,15,21,23,16,45,8,3,17,6,2,18" +
",45,7,10,14,12,22,19,13,20,45,42,34,43,45:2,48:2")[0];

	private int yy_rmap[] = unpackFromString(1,167,
"0,1,2:5,3,2,4,5,2:9,6,2:8,7,2,8,2:10,9,10,2:9,11,2,12,13,14,15,16,17,18,19," +
"20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,7:2,37,38,39,40,41,42,43" +
",44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68" +
",69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93" +
",94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113," +
"114,115,116,117,118,119,120,121")[0];

	private int yy_nxt[][] = unpackFromString(122,49,
"-1,1,54:2,56,58,60,136,54,62,64,66,54:2,68,70,72,54,139,74,54:2,138,54,2,3," +
"4,5,6,7,8,9,10,76,78,11,12,13,14,15,16,17,18,19,80,54,20:2,21,-1:2,53,-1:4," +
"55,-1:120,24,-1:48,25,-1:48,26,-1:64,20:2,-1:2,81:43,29,81:2,-1:6,159,-1:4," +
"96,-1:48,119,-1:48,150,-1:41,141,-1:50,137,-1:47,57,-1:9,59,-1:45,82,-1:48," +
"135,-1:52,83,-1:38,61,-1:63,154,-1:32,65,-1:50,84,-1:8,155,-1:48,67,-1:39,8" +
"5,-1:45,69,-1:53,163,-1:48,140,-1:53,86,-1:38,71,-1:2,73,-1:50,30,-1:47,75," +
"-1:4,22,-1:3,23,-1,77,-1:34,144,-1:68,142,-1:39,31,-1:67,27,-1:33,88,-1:64," +
"28,-1:30,89,-1:36,32,-1:48,146,-1:55,157,-1:47,94,-1:39,161,-1:51,33,-1:47," +
"97,-1:51,98,-1:53,34,-1:45,35,-1:61,102,-1:34,162,-1:51,36,-1:42,37,-1:46,1" +
"48,-1:50,108,-1:58,109,-1:38,38,-1:49,110,-1:54,111,-1:43,112,-1:48,39,-1:4" +
"8,40,-1:63,41,-1:31,42,-1:59,149,-1:50,115,-1:34,116,-1:51,43,-1:52,44,-1:4" +
"0,117,-1:51,118,-1:53,121,-1:42,122,-1:50,45,-1:60,46,-1:50,47,-1:30,151,-1" +
":55,123,-1:53,124,-1:40,125,-1:46,48,-1:49,152,-1:57,128,-1:40,166,-1:52,12" +
"9,-1:48,49,-1:52,131,-1:48,50,-1:39,132,-1:49,133,-1:56,51,-1:48,52,-1:44,1" +
"60,-1:42,63,-1:49,91,-1:45,156,-1:53,79,-1:53,87,-1:39,143,-1:61,145,-1:36," +
"100,-1:54,95,-1:41,99,-1:51,101,-1:49,104,-1:47,114,-1:47,120,-1:45,126,-1:" +
"52,127,-1:48,130,-1:48,134,-1:47,93,-1:55,147,-1:52,90,-1:35,103,-1:51,105," +
"-1:49,107,-1:57,92,-1:35,106,-1:52,113,-1:57,158,-1:37,153,-1:57,164,-1:44," +
"165,-1:38");

	public Yytoken yylex ()
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
				return null;
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
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -2:
						break;
					case 2:
						{ return new Yytoken( 20, yytext() ); }
					case -3:
						break;
					case 3:
						{ return new Yytoken( 21, yytext() ); }
					case -4:
						break;
					case 4:
						{ return new Yytoken( 22, yytext() ); }
					case -5:
						break;
					case 5:
						{ return new Yytoken( 23, yytext() ); }
					case -6:
						break;
					case 6:
						{ return new Yytoken( 24, yytext() ); }
					case -7:
						break;
					case 7:
						{ return new Yytoken( 25, yytext() ); }
					case -8:
						break;
					case 8:
						{ return new Yytoken( 29, yytext() ); }
					case -9:
						break;
					case 9:
						{ return new Yytoken( 27, yytext() ); }
					case -10:
						break;
					case 10:
						{ return new Yytoken( 33, yytext() ); }
					case -11:
						break;
					case 11:
						{ return new Yytoken( 35, yytext() ); }
					case -12:
						break;
					case 12:
						{ return new Yytoken( 36, yytext() ); }
					case -13:
						break;
					case 13:
						{ return new Yytoken( 37, yytext() ); }
					case -14:
						break;
					case 14:
						{ return new Yytoken( 38, yytext() ); }
					case -15:
						break;
					case 15:
						{ return new Yytoken( 39, yytext() ); }
					case -16:
						break;
					case 16:
						{ return new Yytoken( 40, yytext() ); }
					case -17:
						break;
					case 17:
						{ return new Yytoken( 41, yytext() ); }
					case -18:
						break;
					case 18:
						{ return new Yytoken( 42, yytext() ); }
					case -19:
						break;
					case 19:
						{ return new Yytoken( 43, yytext() ); }
					case -20:
						break;
					case 20:
						{}
					case -21:
						break;
					case 21:
						
					case -22:
						break;
					case 22:
						{ return new Yytoken( 48, yytext() ); }
					case -23:
						break;
					case 23:
						{ return new Yytoken( 8, yytext() ); }
					case -24:
						break;
					case 24:
						{ return new Yytoken( 26, yytext() ); }
					case -25:
						break;
					case 25:
						{ return new Yytoken( 28, yytext() ); }
					case -26:
						break;
					case 26:
						{ return new Yytoken( 30, yytext() ); }
					case -27:
						break;
					case 27:
						{ return new Yytoken( 31, yytext() ); }
					case -28:
						break;
					case 28:
						{ return new Yytoken( 32, yytext() ); }
					case -29:
						break;
					case 29:
						{ return new Yytoken( 46, "stringconstant" ); }
					case -30:
						break;
					case 30:
						{ return new Yytoken( 7, yytext() ); }
					case -31:
						break;
					case 31:
						{ return new Yytoken( 10, yytext() ); }
					case -32:
						break;
					case 32:
						{ return new Yytoken( 4, yytext() ); }
					case -33:
						break;
					case 33:
						{ return new Yytoken( 17, yytext() ); }
					case -34:
						break;
					case 34:
						{ return new Yytoken( 18, yytext() ); }
					case -35:
						break;
					case 35:
						{ return new Yytoken( 1, yytext() ); }
					case -36:
						break;
					case 36:
						{ return new Yytoken( 2, yytext() ); }
					case -37:
						break;
					case 37:
						{ return new Yytoken( 6, yytext() ); }
					case -38:
						break;
					case 38:
						{ return new Yytoken( 19, yytext() ); }
					case -39:
						break;
					case 39:
						{ return new Yytoken( 14, yytext() ); }
					case -40:
						break;
					case 40:
						{ return new Yytoken( 15, yytext() ); }
					case -41:
						break;
					case 41:
						{ return new Yytoken( 16, yytext() ); }
					case -42:
						break;
					case 42:
						{ return new Yytoken( 3, yytext() ); }
					case -43:
						break;
					case 43:
						{ return new Yytoken( 0, yytext() ); }
					case -44:
						break;
					case 44:
						{ return new Yytoken( 5, yytext() ); }
					case -45:
						break;
					case 45:
						{ return new Yytoken( 13, yytext() ); }
					case -46:
						break;
					case 46:
						{ return new Yytoken( 34, yytext() ); }
					case -47:
						break;
					case 47:
						{ return new Yytoken( 12, yytext() ); }
					case -48:
						break;
					case 48:
						{ return new Yytoken( 11, yytext() ); }
					case -49:
						break;
					case 49:
						{ return new Yytoken( 9, yytext() ); }
					case -50:
						break;
					case 50:
						{ return new Yytoken( 44, yytext() ); }
					case -51:
						break;
					case 51:
						{ return new Yytoken( 45, yytext() ); }
					case -52:
						break;
					case 52:
						{ return new Yytoken( 47, yytext() ); }
					case -53:
						break;
					case 54:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -54:
						break;
					case 56:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -55:
						break;
					case 58:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -56:
						break;
					case 60:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -57:
						break;
					case 62:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -58:
						break;
					case 64:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -59:
						break;
					case 66:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -60:
						break;
					case 68:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -61:
						break;
					case 70:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -62:
						break;
					case 72:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -63:
						break;
					case 74:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -64:
						break;
					case 76:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -65:
						break;
					case 78:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -66:
						break;
					case 80:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -67:
						break;
					case 136:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -68:
						break;
					case 138:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -69:
						break;
					case 139:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -70:
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
