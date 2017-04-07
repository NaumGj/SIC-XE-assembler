package sic.asm.code.instructions;

import sic.asm.code.Code;
import sic.asm.code.Flags;
import sic.asm.code.Node;
import sic.asm.code.Registers;
import sic.asm.code.Reloc;
import sic.asm.code.SemanticError;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF3m extends Node {
	
	private Flags flags;
	private int value;
	private String symbol;
	private int resolved;

	public InstructionF3m(Mnemonic mnemonic, Flags flags, int value, String symbol) {
		super(mnemonic);
		this.flags = flags;
		this.value = value;
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		String returnString = (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
		if(flags.isImmediate()) {
			returnString += "#";
		}
		if(flags.isIndirect()) {
			returnString += "@";
		}
		returnString += ((symbol == null) ? Integer.toString(value) : symbol);
		if(flags.isIndexed()) {
			returnString += ", X";
		}
		return returnString;
	}
	
	@Override
	public void resolve(Code code) throws SemanticError {
		if(symbol == null) {
			resolved = value;
		} else {
			resolved = code.resolveSymbol(symbol);
		}
		if(flags.isImmediate() && symbol == null) {
			return;
		}
		if(code.isPCRelative(resolved)) {
//			System.out.println(resolved);
//			System.out.println(code.getLocCtr());
//			System.out.println(code.getNextLocCtr());
			flags.setP(true);
			resolved -= code.getNextLocCtr();
//			System.out.println(resolved);
			if (resolved >= 0) {
				resolved &= 0xFFF;
			} else {
				resolved = ~(-resolved - 1) & 0xFFF;
			}
		} else if(code.isBaseRelative(resolved)) {
			flags.setB(true);
			resolved -= code.getBaseReg();
			if (resolved >= 0) {
				resolved &= 0xFFF;
			} else {
				resolved += (1 << 12);
			}
		} else if(flags.isImmediate() && resolved >= -2048 && resolved <=2047) {
			Reloc reloc = new Reloc(code.getLocCtr() + 1, 3);
			code.addReloc(reloc);
		} else if(!flags.isImmediate() && resolved >=0 && resolved <= 4095){
			Reloc reloc = new Reloc(code.getLocCtr() + 1, 3);
			code.addReloc(reloc);
		} else {
			throw new SemanticError("Invalid addressing: " + this.toString());
		}
	}
	
	@Override
	public int length() {
		return 3;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		int n = flags.isN() ? 1 : 0;
		int i = flags.isI() ? 1 : 0;
		int x = flags.isX() ? 1 : 0;
		int b = flags.isB() ? 1 : 0;
		int p = flags.isP() ? 1 : 0;
		int e = flags.isE() ? 1 : 0;
		int ni = n << 1 | i;
		int xbpe = x << 3 | b << 2 | p << 1 | e;
		buf[loc] = (byte)((mnemonic.opcode & 0xFC) | ni);
		buf[loc + 1] = (byte)(xbpe << 4 | ((resolved >> 8) & 0x0F));
		buf[loc + 2] = (byte)(resolved & 0xFF);
	}
	
	@Override
	public String getOperands() {
		String operands = "";
		if(flags.isImmediate()) {
			operands += "#";
		}
		if(flags.isIndirect()) {
			operands += "@";
		}
		operands += ((symbol == null) ? Integer.toString(value) : symbol);
		if(flags.isIndexed()) {
			operands += ", X";
		}
		return String.format("%s", operands);
	}

}