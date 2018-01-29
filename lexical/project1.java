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
<<<<<<< HEAD
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NOT_ACCEPT,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
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
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"45:8,48:2,49,45:2,0,45:18,48,32,44,45:2,28,33,45,38,39,26,24,36,25,37,27,47" +
":10,45,35,29,30,31,45:2,46:26,40,45,41,45:3,5,1,9,11,4,15,21,23,16,46,8,3,1" +
"7,6,2,18,46,7,10,14,12,22,19,13,20,46,42,34,43,45:2,50:2")[0];

	private int yy_rmap[] = unpackFromString(1,169,
"0,1,2:5,3,2,4,5,6,2:9,7,2,8:2,2:5,9,8,10,8:10,11,12,8:9,9,13,14,15,9,16,2,1" +
"7,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,4" +
"2,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,8,64,65,66" +
",67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91" +
",92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,8,107,108,109,110,111," +
"112,113,114,115,116,117,118,119,120,121,122")[0];

	private int yy_nxt[][] = unpackFromString(123,51,
"-1,1,152:2,104,157,158,159,152,160,161,162,152:2,105,82,55,152,163,164,152:" +
"2,106,152,2,3,4,5,6,7,8,9,10,11,56,12,13,14,15,16,17,18,19,20,58,60,152,60," +
"21:2,22,-1,152,165,152:4,107,152:16,-1:22,152,108,-1:84,25,-1:50,26,-1:50,2" +
"7,-1:53,28,-1:65,21:2,-1:2,152:23,-1:22,152,108,-1:4,54:43,30,54:4,-1:3,152" +
":3,155,152:4,132,152:14,-1:22,152,108,-1:4,152:8,143,152:14,-1:22,152,108,-" +
"1:4,152:8,168,152:14,-1:22,152,108,-1:4,152:5,59,152:4,23,152:3,24,152,117," +
"152:6,-1:22,152,108,-1:37,29,-1:17,152:6,31,152:16,-1:22,152,108,-1:4,152:1" +
"3,32,152:9,-1:22,152,108,-1:4,152:3,33,152:19,-1:22,152,108,-1:4,152:3,34,1" +
"52:19,-1:22,152,108,-1:4,152:10,35,152:12,-1:22,152,108,-1:4,152:7,36,152:1" +
"5,-1:22,152,108,-1:4,152:9,37,152:13,-1:22,152,108,-1:4,152:3,38,152:19,-1:" +
"22,152,108,-1:4,152:3,39,152:19,-1:22,152,108,-1:4,152:5,40,152:17,-1:22,15" +
"2,108,-1:4,152:5,41,152:17,-1:22,152,108,-1:4,152:20,42,152:2,-1:22,152,108" +
",-1:4,152:3,43,152:19,-1:22,152,108,-1:4,152:5,44,152:17,-1:22,152,108,-1:4" +
",152:9,45,152:13,-1:22,152,108,-1:4,152:5,46,152:17,-1:22,152,108,-1:4,152:" +
"17,47,152:5,-1:22,152,108,-1:4,152:19,48,152:3,-1:22,152,108,-1:4,152:3,49," +
"152:19,-1:22,152,108,-1:4,152:9,50,152:13,-1:22,152,108,-1:4,152:13,51,152:" +
"9,-1:22,152,108,-1:4,152:13,52,152:9,-1:22,152,108,-1:4,152:13,53,152:9,-1:" +
"22,152,108,-1:4,152,57,152:2,116,152:18,-1:22,152,108,-1:4,152:9,61,152:13," +
"-1:22,152,108,-1:4,152:11,62,152:11,-1:22,152,108,-1:4,152:15,63,152:7,-1:2" +
"2,152,108,-1:4,152:4,64,152:18,-1:22,152,108,-1:4,152:9,65,152:13,-1:22,152" +
",108,-1:4,152:9,66,152:13,-1:22,152,108,-1:4,152:2,67,152:20,-1:22,152,108," +
"-1:4,152:2,68,152:20,-1:22,152,108,-1:4,152:6,69,152:16,-1:22,152,108,-1:4," +
"152:5,70,152:17,-1:22,152,108,-1:4,152:2,71,152:20,-1:22,152,108,-1:4,152:4" +
",72,152:18,-1:22,152,108,-1:4,152:10,73,152:12,-1:22,152,108,-1:4,152:2,74," +
"152:20,-1:22,152,108,-1:4,152,75,152:21,-1:22,152,108,-1:4,152:4,76,152:18," +
"-1:22,152,108,-1:4,152:8,77,152:14,-1:22,152,108,-1:4,152:13,78,152:9,-1:22" +
",152,108,-1:4,152:5,79,152:17,-1:22,152,108,-1:4,152:5,80,152:17,-1:22,152," +
"108,-1:4,152:5,81,152:17,-1:22,152,108,-1:4,152:2,83,152:9,109,152:10,-1:22" +
",152,108,-1:4,152:6,84,152:16,-1:22,152,108,-1:4,152,85,152:21,-1:22,152,10" +
"8,-1:4,152:3,86,152:19,-1:22,152,108,-1:4,152:13,121,152:9,-1:22,152,108,-1" +
":4,152:9,153,152:13,-1:22,152,108,-1:4,152:18,122,152:4,-1:22,152,108,-1:4," +
"152:4,123,152:8,124,152:9,-1:22,152,108,-1:4,152:4,87,152:18,-1:22,152,108," +
"-1:4,152:6,125,152:16,-1:22,152,108,-1:4,152:11,126,152:11,-1:22,152,108,-1" +
":4,152:2,88,152:20,-1:22,152,108,-1:4,152:17,154,152:5,-1:22,152,108,-1:4,1" +
"52:15,127,152:7,-1:22,152,108,-1:4,152:15,89,152:7,-1:22,152,108,-1:4,152:2" +
",128,152:20,-1:22,152,108,-1:4,152:3,129,152:19,-1:22,152,108,-1:4,152:4,13" +
"1,152:18,-1:22,152,108,-1:4,152:10,90,152:12,-1:22,152,108,-1:4,152:11,91,1" +
"52:11,-1:22,152,108,-1:4,152:15,92,152:7,-1:22,152,108,-1:4,93,152:22,-1:22" +
",152,108,-1:4,152:5,134,152:17,-1:22,152,108,-1:4,152:3,94,152:19,-1:22,152" +
",108,-1:4,152:5,95,152:17,-1:22,152,108,-1:4,152:20,135,152:2,-1:22,152,108" +
",-1:4,152:6,136,152:16,-1:22,152,108,-1:4,152,138,152:21,-1:22,152,108,-1:4" +
",152:3,139,152:19,-1:22,152,108,-1:4,152:13,96,152:9,-1:22,152,108,-1:4,152" +
":5,97,152:17,-1:22,152,108,-1:4,152:6,98,152:16,-1:22,152,108,-1:4,152:14,1" +
"40,152:8,-1:22,152,108,-1:4,152:5,141,152:17,-1:22,152,108,-1:4,152:16,142," +
"152:6,-1:22,152,108,-1:4,152:4,99,152:18,-1:22,152,108,-1:4,152:9,144,152:1" +
"3,-1:22,152,108,-1:4,152:3,145,152:19,-1:22,152,108,-1:4,152,146,152:21,-1:" +
"22,152,108,-1:4,152:13,147,152:9,-1:22,152,108,-1:4,152:5,100,152:17,-1:22," +
"152,108,-1:4,152:5,148,152:17,-1:22,152,108,-1:4,152:4,101,152:18,-1:22,152" +
",108,-1:4,152:9,149,152:13,-1:22,152,108,-1:4,152:13,150,152:9,-1:22,152,10" +
"8,-1:4,152:4,102,152:18,-1:22,152,108,-1:4,152:4,103,152:18,-1:22,152,108,-" +
"1:4,152:15,130,152:7,-1:22,152,108,-1:4,152:2,133,152:20,-1:22,152,108,-1:4" +
",152:6,137,152:16,-1:22,152,108,-1:4,152:13,151,152:9,-1:22,152,108,-1:4,15" +
"2:9,110,152:13,-1:22,152,108,-1:4,152:3,111,152:19,-1:22,152,108,-1:4,152:3" +
",112,152:19,-1:22,152,108,-1:4,152:2,113,152:20,-1:22,152,108,-1:4,152:13,1" +
"14,152:9,-1:22,152,108,-1:4,152,115,152:21,-1:22,152,108,-1:4,152:6,118,152" +
":16,-1:22,152,108,-1:4,152:22,119,-1:22,152,108,-1:4,152,120,152:21,-1:22,1" +
"52,108,-1:4,152:9,156,152:13,-1:22,152,108,-1:4,152:5,166,152:17,-1:22,152," +
"108,-1:4,152,167,152:21,-1:22,152,108,-1:3");
=======
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
>>>>>>> 8f98340c27ff9ba9dd506e4aa1c1002bad3edaac

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
						{ return new Yytoken( 49, "id" ); }
					case -2:
						break;
					case 2:
<<<<<<< HEAD
						{ return new Yytoken( 20, "plus" ); }
					case -3:
						break;
					case 3:
						{ return new Yytoken( 21, "minus" ); }
					case -4:
						break;
					case 4:
						{ return new Yytoken( 22, "multiplication" ); }
					case -5:
						break;
					case 5:
						{ return new Yytoken( 23, "division" ); }
					case -6:
						break;
					case 6:
						{ return new Yytoken( 24, "mod" ); }
					case -7:
						break;
					case 7:
						{ return new Yytoken( 25, "less" ); }
					case -8:
						break;
					case 8:
						{ return new Yytoken( 29, "equal" ); }
					case -9:
						break;
					case 9:
						{ return new Yytoken( 27, "greater" ); }
					case -10:
						break;
					case 10:
						{ return new Yytoken( 33, "not" ); }
					case -11:
						break;
					case 11:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -12:
						break;
					case 12:
						{ return new Yytoken( 35, "semicolon" ); }
					case -13:
						break;
					case 13:
						{ return new Yytoken( 36, "comma" ); }
					case -14:
						break;
					case 14:
						{ return new Yytoken( 37, "period" ); }
					case -15:
						break;
					case 15:
						{ return new Yytoken( 38, "leftparen" ); }
					case -16:
						break;
					case 16:
						{ return new Yytoken( 39, "rightparen" ); }
					case -17:
						break;
					case 17:
						{ return new Yytoken( 40, "leftbracket" ); }
					case -18:
						break;
					case 18:
						{ return new Yytoken( 41, "rightbracket" ); }
					case -19:
						break;
					case 19:
						{ return new Yytoken( 42, "leftbrace" ); }
					case -20:
						break;
					case 20:
						{ return new Yytoken( 43, "rightbrace" ); }
					case -21:
						break;
					case 21:
						{}
					case -22:
						break;
					case 22:
						
					case -23:
						break;
					case 23:
						{ return new Yytoken( 48, yytext() ); }
					case -24:
						break;
					case 24:
						{ return new Yytoken( 8, yytext() ); }
					case -25:
						break;
					case 25:
						{ return new Yytoken( 26, "lessequal" ); }
					case -26:
						break;
					case 26:
						{ return new Yytoken( 28, "greaterequal" ); }
					case -27:
						break;
					case 27:
						{ return new Yytoken( 30, "notequal" ); }
					case -28:
						break;
					case 28:
						{ return new Yytoken( 31, "and" ); }
					case -29:
						break;
					case 29:
						{ return new Yytoken( 32, "or" ); }
					case -30:
						break;
					case 30:
						{ return new Yytoken( 46, "stringconstant" ); }
					case -31:
						break;
					case 31:
						{ return new Yytoken( 7, yytext() ); }
					case -32:
						break;
					case 32:
						{ return new Yytoken( 10, yytext() ); }
					case -33:
						break;
					case 33:
						{ return new Yytoken( 4, yytext() ); }
					case -34:
						break;
					case 34:
						{ return new Yytoken( 17, yytext() ); }
					case -35:
						break;
					case 35:
						{ return new Yytoken( 18, yytext() ); }
					case -36:
						break;
					case 36:
						{ return new Yytoken( 1, yytext() ); }
					case -37:
						break;
					case 37:
						{ return new Yytoken( 2, yytext() ); }
					case -38:
						break;
					case 38:
						{ return new Yytoken( 6, yytext() ); }
					case -39:
						break;
					case 39:
						{ return new Yytoken( 19, yytext() ); }
					case -40:
						break;
					case 40:
						{ return new Yytoken( 14, yytext() ); }
					case -41:
						break;
					case 41:
						{ return new Yytoken( 15, yytext() ); }
					case -42:
						break;
					case 42:
						{ return new Yytoken( 16, yytext() ); }
					case -43:
						break;
					case 43:
						{ return new Yytoken( 3, yytext() ); }
					case -44:
						break;
					case 44:
						{ return new Yytoken( 0, yytext() ); }
					case -45:
						break;
					case 45:
						{ return new Yytoken( 5, yytext() ); }
					case -46:
						break;
					case 46:
						{ return new Yytoken( 13, yytext() ); }
					case -47:
						break;
					case 47:
						{ return new Yytoken( 34, "assignop" ); }
					case -48:
						break;
					case 48:
						{ return new Yytoken( 12, yytext() ); }
					case -49:
						break;
					case 49:
						{ return new Yytoken( 11, yytext() ); }
					case -50:
						break;
					case 50:
						{ return new Yytoken( 9, yytext() ); }
					case -51:
						break;
					case 51:
						{ return new Yytoken( 44, "intconstant" ); }
					case -52:
						break;
					case 52:
						{ return new Yytoken( 45, "doubleconstant" ); }
					case -53:
						break;
					case 53:
						{ return new Yytoken( 47, "booleanconstant" ); }
					case -54:
						break;
					case 55:
						{ return new Yytoken( 49, "id" ); }
					case -55:
						break;
					case 56:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -56:
						break;
					case 57:
						{ return new Yytoken( 49, "id" ); }
					case -57:
						break;
					case 58:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -58:
						break;
					case 59:
						{ return new Yytoken( 49, "id" ); }
					case -59:
						break;
					case 60:
						{
	System.out.println( "Token not implemented yet: " + yytext() );
}
					case -60:
						break;
					case 61:
						{ return new Yytoken( 49, "id" ); }
					case -61:
						break;
					case 62:
						{ return new Yytoken( 49, "id" ); }
					case -62:
						break;
					case 63:
						{ return new Yytoken( 49, "id" ); }
					case -63:
						break;
					case 64:
						{ return new Yytoken( 49, "id" ); }
					case -64:
						break;
					case 65:
						{ return new Yytoken( 49, "id" ); }
					case -65:
						break;
					case 66:
						{ return new Yytoken( 49, "id" ); }
					case -66:
						break;
					case 67:
						{ return new Yytoken( 49, "id" ); }
					case -67:
						break;
					case 68:
						{ return new Yytoken( 49, "id" ); }
					case -68:
						break;
					case 69:
						{ return new Yytoken( 49, "id" ); }
					case -69:
						break;
					case 70:
						{ return new Yytoken( 49, "id" ); }
					case -70:
						break;
					case 71:
						{ return new Yytoken( 49, "id" ); }
					case -71:
						break;
					case 72:
						{ return new Yytoken( 49, "id" ); }
					case -72:
						break;
					case 73:
						{ return new Yytoken( 49, "id" ); }
					case -73:
						break;
					case 74:
						{ return new Yytoken( 49, "id" ); }
					case -74:
						break;
					case 75:
						{ return new Yytoken( 49, "id" ); }
					case -75:
						break;
					case 76:
						{ return new Yytoken( 49, "id" ); }
					case -76:
						break;
					case 77:
						{ return new Yytoken( 49, "id" ); }
					case -77:
						break;
					case 78:
						{ return new Yytoken( 49, "id" ); }
					case -78:
						break;
					case 79:
						{ return new Yytoken( 49, "id" ); }
					case -79:
						break;
					case 80:
						{ return new Yytoken( 49, "id" ); }
					case -80:
						break;
					case 81:
						{ return new Yytoken( 49, "id" ); }
					case -81:
						break;
					case 82:
						{ return new Yytoken( 49, "id" ); }
					case -82:
						break;
					case 83:
						{ return new Yytoken( 49, "id" ); }
					case -83:
						break;
					case 84:
						{ return new Yytoken( 49, "id" ); }
					case -84:
						break;
					case 85:
						{ return new Yytoken( 49, "id" ); }
					case -85:
						break;
					case 86:
						{ return new Yytoken( 49, "id" ); }
					case -86:
						break;
					case 87:
						{ return new Yytoken( 49, "id" ); }
					case -87:
						break;
					case 88:
						{ return new Yytoken( 49, "id" ); }
					case -88:
						break;
					case 89:
						{ return new Yytoken( 49, "id" ); }
					case -89:
						break;
					case 90:
						{ return new Yytoken( 49, "id" ); }
					case -90:
						break;
					case 91:
						{ return new Yytoken( 49, "id" ); }
					case -91:
						break;
					case 92:
						{ return new Yytoken( 49, "id" ); }
					case -92:
						break;
					case 93:
						{ return new Yytoken( 49, "id" ); }
					case -93:
						break;
					case 94:
						{ return new Yytoken( 49, "id" ); }
					case -94:
						break;
					case 95:
						{ return new Yytoken( 49, "id" ); }
					case -95:
						break;
					case 96:
						{ return new Yytoken( 49, "id" ); }
					case -96:
						break;
					case 97:
						{ return new Yytoken( 49, "id" ); }
					case -97:
						break;
					case 98:
						{ return new Yytoken( 49, "id" ); }
					case -98:
						break;
					case 99:
						{ return new Yytoken( 49, "id" ); }
					case -99:
						break;
					case 100:
						{ return new Yytoken( 49, "id" ); }
					case -100:
						break;
					case 101:
						{ return new Yytoken( 49, "id" ); }
					case -101:
						break;
					case 102:
						{ return new Yytoken( 49, "id" ); }
					case -102:
						break;
					case 103:
						{ return new Yytoken( 49, "id" ); }
					case -103:
						break;
					case 104:
						{ return new Yytoken( 49, "id" ); }
					case -104:
						break;
					case 105:
						{ return new Yytoken( 49, "id" ); }
					case -105:
						break;
					case 106:
						{ return new Yytoken( 49, "id" ); }
					case -106:
						break;
					case 107:
						{ return new Yytoken( 49, "id" ); }
					case -107:
						break;
					case 108:
						{ return new Yytoken( 49, "id" ); }
					case -108:
						break;
					case 109:
						{ return new Yytoken( 49, "id" ); }
					case -109:
						break;
					case 110:
						{ return new Yytoken( 49, "id" ); }
					case -110:
						break;
					case 111:
						{ return new Yytoken( 49, "id" ); }
					case -111:
						break;
					case 112:
						{ return new Yytoken( 49, "id" ); }
					case -112:
						break;
					case 113:
						{ return new Yytoken( 49, "id" ); }
					case -113:
						break;
					case 114:
						{ return new Yytoken( 49, "id" ); }
					case -114:
						break;
					case 115:
						{ return new Yytoken( 49, "id" ); }
					case -115:
						break;
					case 116:
						{ return new Yytoken( 49, "id" ); }
					case -116:
						break;
					case 117:
						{ return new Yytoken( 49, "id" ); }
					case -117:
						break;
					case 118:
						{ return new Yytoken( 49, "id" ); }
					case -118:
						break;
					case 119:
						{ return new Yytoken( 49, "id" ); }
					case -119:
						break;
					case 120:
						{ return new Yytoken( 49, "id" ); }
					case -120:
						break;
					case 121:
						{ return new Yytoken( 49, "id" ); }
					case -121:
						break;
					case 122:
						{ return new Yytoken( 49, "id" ); }
					case -122:
						break;
					case 123:
						{ return new Yytoken( 49, "id" ); }
					case -123:
						break;
					case 124:
						{ return new Yytoken( 49, "id" ); }
					case -124:
						break;
					case 125:
						{ return new Yytoken( 49, "id" ); }
					case -125:
						break;
					case 126:
						{ return new Yytoken( 49, "id" ); }
					case -126:
						break;
					case 127:
						{ return new Yytoken( 49, "id" ); }
					case -127:
						break;
					case 128:
						{ return new Yytoken( 49, "id" ); }
					case -128:
						break;
					case 129:
						{ return new Yytoken( 49, "id" ); }
					case -129:
						break;
					case 130:
						{ return new Yytoken( 49, "id" ); }
					case -130:
						break;
					case 131:
						{ return new Yytoken( 49, "id" ); }
					case -131:
						break;
					case 132:
						{ return new Yytoken( 49, "id" ); }
					case -132:
						break;
					case 133:
						{ return new Yytoken( 49, "id" ); }
					case -133:
						break;
					case 134:
						{ return new Yytoken( 49, "id" ); }
					case -134:
						break;
					case 135:
						{ return new Yytoken( 49, "id" ); }
					case -135:
						break;
					case 136:
						{ return new Yytoken( 49, "id" ); }
					case -136:
						break;
					case 137:
						{ return new Yytoken( 49, "id" ); }
					case -137:
						break;
					case 138:
						{ return new Yytoken( 49, "id" ); }
					case -138:
						break;
					case 139:
						{ return new Yytoken( 49, "id" ); }
					case -139:
						break;
					case 140:
						{ return new Yytoken( 49, "id" ); }
					case -140:
						break;
					case 141:
						{ return new Yytoken( 49, "id" ); }
					case -141:
						break;
					case 142:
						{ return new Yytoken( 49, "id" ); }
					case -142:
						break;
					case 143:
						{ return new Yytoken( 49, "id" ); }
					case -143:
						break;
					case 144:
						{ return new Yytoken( 49, "id" ); }
					case -144:
						break;
					case 145:
						{ return new Yytoken( 49, "id" ); }
					case -145:
						break;
					case 146:
						{ return new Yytoken( 49, "id" ); }
					case -146:
						break;
					case 147:
						{ return new Yytoken( 49, "id" ); }
					case -147:
						break;
					case 148:
						{ return new Yytoken( 49, "id" ); }
					case -148:
						break;
					case 149:
						{ return new Yytoken( 49, "id" ); }
					case -149:
						break;
					case 150:
						{ return new Yytoken( 49, "id" ); }
					case -150:
						break;
					case 151:
						{ return new Yytoken( 49, "id" ); }
					case -151:
						break;
					case 152:
						{ return new Yytoken( 49, "id" ); }
					case -152:
						break;
					case 153:
						{ return new Yytoken( 49, "id" ); }
					case -153:
						break;
					case 154:
						{ return new Yytoken( 49, "id" ); }
					case -154:
						break;
					case 155:
						{ return new Yytoken( 49, "id" ); }
					case -155:
						break;
					case 156:
						{ return new Yytoken( 49, "id" ); }
					case -156:
						break;
					case 157:
						{ return new Yytoken( 49, "id" ); }
					case -157:
						break;
					case 158:
						{ return new Yytoken( 49, "id" ); }
					case -158:
						break;
					case 159:
						{ return new Yytoken( 49, "id" ); }
					case -159:
						break;
					case 160:
						{ return new Yytoken( 49, "id" ); }
					case -160:
						break;
					case 161:
						{ return new Yytoken( 49, "id" ); }
					case -161:
						break;
					case 162:
						{ return new Yytoken( 49, "id" ); }
					case -162:
						break;
					case 163:
						{ return new Yytoken( 49, "id" ); }
					case -163:
						break;
					case 164:
						{ return new Yytoken( 49, "id" ); }
					case -164:
						break;
					case 165:
						{ return new Yytoken( 49, "id" ); }
					case -165:
						break;
					case 166:
						{ return new Yytoken( 49, "id" ); }
					case -166:
						break;
					case 167:
						{ return new Yytoken( 49, "id" ); }
					case -167:
						break;
					case 168:
						{ return new Yytoken( 49, "id" ); }
					case -168:
=======
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
>>>>>>> 8f98340c27ff9ba9dd506e4aa1c1002bad3edaac
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
