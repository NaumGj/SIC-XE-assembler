package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.code.Registers;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF2rn extends Node {
	
	private int reg;
	private int number;

	public InstructionF2rn(Mnemonic mnemonic, int reg, int number) {
		super(mnemonic);
		this.reg = reg;
		this.number = number;
	}

	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + Registers.getRegName(reg) + ", " + Integer.toString(number);
	}
	
	@Override
	public int length() {
		return 2;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(mnemonic.opcode);
		buf[loc + 1] = (byte)(reg << 4 | (number & 0x0F));
	}
	
	@Override
	public String getOperands() {
		return String.format("%s, %1s", Integer.toString(this.number), Registers.getRegName(reg));
	}

}