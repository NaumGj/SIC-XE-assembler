package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.code.Registers;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF2rr extends Node {
	
	private int reg1;
	private int reg2;

	public InstructionF2rr(Mnemonic mnemonic, int reg1, int reg2) {
		super(mnemonic);
		this.reg1 = reg1;
		this.reg2 = reg2;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + Registers.getRegName(reg1) + ", " + Registers.getRegName(reg2);
	}
	
	@Override
	public int length() {
		return 2;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(mnemonic.opcode);
		buf[loc + 1] = (byte)(reg1 << 4 | (reg2 & 0x0F));
	}
	
	@Override
	public String getOperands() {
		return String.format("%1s, %1s", Registers.getRegName(reg1), Registers.getRegName(reg2));
	}

}