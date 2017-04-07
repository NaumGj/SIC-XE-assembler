package sic.asm.code;

public class Registers {
	
	public static final int A_NO = 0;
	public static final int X_NO = 1;
	public static final int L_NO = 2;
	public static final int B_NO = 3;
	public static final int S_NO = 4;
	public static final int T_NO = 5;
	public static final int F_NO = 6;

	public static String getRegName(int regno) {
		switch (regno) {
			case A_NO:
				return "A";
			case X_NO:
				return "X";
			case L_NO:
				return "L";
			case B_NO:
				return "B";
			case S_NO:
				return "S";
			case T_NO:
				return "T";
			case F_NO:
				return "F";
			default:
				return "";
		}
	}
	
}
