package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.code.Registers;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF2r extends Node {
	
	private int reg;

	public InstructionF2r(Mnemonic mnemonic, int reg) {
		super(mnemonic);
		this.reg = reg;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + Registers.getRegName(reg);
	}
	
	@Override
	public int length() {
		return 2;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(mnemonic.opcode);
		buf[loc + 1] = (byte)(reg << 4);
	}

	@Override
	public String getOperands() {
		return String.format("%1s", Registers.getRegName(reg));
	}

}