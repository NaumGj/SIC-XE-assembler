package sic.asm.code;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Opcode {

	public static final int LDA = 0x00;
    public static final int LDX = 0x04;
    public static final int LDL = 0x08;
    public static final int STA = 0x0C;
    public static final int STX = 0x10;
    public static final int STL = 0x14;
    public static final int ADD = 0x18;
    public static final int SUB = 0x1C;
    public static final int MUL = 0x20;
    public static final int DIV = 0x24;
    public static final int COMP = 0x28;
    public static final int TIX = 0x2C;
    public static final int JEQ = 0x30;
    public static final int JGT = 0x34;
    public static final int JLT = 0x38;
    public static final int J = 0x3C;
    public static final int AND = 0x40;
    public static final int OR = 0x44;
    public static final int JSUB = 0x48;
    public static final int RSUB = 0x4C;
    public static final int LDCH = 0x50;
    public static final int STCH = 0x54;
    public static final int ADDF = 0x58;
    public static final int SUBF = 0x5C;
    public static final int MULF = 0x60;
    public static final int DIVF = 0x64;
    public static final int LDB = 0x68;
    public static final int LDS = 0x6C;
    public static final int LDF = 0x70;
    public static final int LDT = 0x74;
    public static final int STB = 0x78;
    public static final int STS = 0x7C;
    public static final int STF = 0x80;
    public static final int STT = 0x84;
    public static final int COMPF = 0x88;
    public static final int ADDR = 0x90;
    public static final int SUBR = 0x94;
    public static final int MULR = 0x98;
    public static final int DIVR = 0x9C;
    public static final int COMPR = 0xA0;
    public static final int SHIFTL = 0xA4;
    public static final int SHIFTR = 0xA8;
    public static final int RMO = 0xAC;
    public static final int SVC = 0xB0;
    public static final int CLEAR = 0xB4;
    public static final int TIXR = 0xB8;
    public static final int FLOAT = 0xC0;
    public static final int FIX = 0xC4;
    public static final int NORM = 0xC8;
    public static final int LPS = 0xD0;
    public static final int STI = 0xD4;
    public static final int RD = 0xD8;
    public static final int WD = 0xDC;
    public static final int TD = 0xE0;
    public static final int STSW = 0xE8;
    public static final int SSK = 0xEC;
    public static final int SIO = 0xF0;
    public static final int HIO = 0xF4;
    public static final int TIO = 0xF8;
    
    public static final int START       = 0;
    public static final int END         = 1;

    public static final int CSECT       = 2;
    public static final int USE         = 3;
    public static final int EXTREF      = 4;
    public static final int EXTDEF      = 5;

    public static final int ORG         = 6;
    public static final int LTORG       = 7;

    public static final int BASE        = 8;
    public static final int NOBASE      = 9;

    public static final int EQU         = 10;

    public static final int RESB        = 0;
    public static final int RESW        = 1;
    public static final int RESF        = 2;
    public static final int BYTE        = 3;
    public static final int WORD        = 4;
    public static final int FLOT        = 5;
    
    public static final Map<Integer, String> opcodeToMnemonic;
    static {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(FIX, "FIX");
        map.put(FLOAT, "FLOAT");
        map.put(NORM, "NORM");
        map.put(SIO, "SIO");
        map.put(HIO, "HIO");
        map.put(TIO, "TIO");
        map.put(CLEAR, "CLEAR");
        map.put(RMO, "RMO");
        map.put(ADDR, "ADDR");
        map.put(SUBR, "SUBR");
        map.put(MULR, "MULR");
        map.put(DIVR, "DIVR");
        map.put(SHIFTL, "SHIFTL");
        map.put(SHIFTR, "SHIFTR");
        map.put(COMPR, "COMPR");
        map.put(TIXR, "TIXR");
        map.put(SVC, "SVC");
        map.put(ADD, "ADD");
        map.put(ADDF, "ADDF");
        map.put(SUB, "SUB");
        map.put(SUBF, "SUBF");
        map.put(MUL, "MUL");
        map.put(MULF, "MULF");
        map.put(DIV, "DIV");
        map.put(DIVF, "DIVF");
        map.put(AND, "AND");
        map.put(OR, "OR");
        map.put(COMP, "COMP");
        map.put(COMPF, "COMPF");
        map.put(LDA, "LDA");
        map.put(LDB, "LDB");
        map.put(LDCH, "LDCH");
        map.put(LDF, "LDF");
        map.put(LDL, "LDL");
        map.put(LDS, "LDS");
        map.put(LDT, "LDT");
        map.put(LDX, "LDX");
        map.put(LPS, "LPS");
        map.put(STA, "STA");
        map.put(STB, "STB");
        map.put(STCH, "STCH");
        map.put(STF, "STF");
        map.put(STL, "STL");
        map.put(STS, "STS");
        map.put(STSW, "STSW");
        map.put(STT, "STT");
        map.put(STX, "STX");
        map.put(J, "J");
        map.put(JEQ, "JEQ");
        map.put(JGT, "JGT");
        map.put(JLT, "JLT");
        map.put(JSUB, "JSUB");
        map.put(RSUB, "RSUB");
        map.put(TD, "TD");
        map.put(RD, "RD");
        map.put(WD, "WD");
        map.put(TIX, "TIX");
        map.put(SSK, "SSK");
        opcodeToMnemonic = Collections.unmodifiableMap(map);
    }
    
    
}
