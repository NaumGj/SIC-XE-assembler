package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF2n extends Node {
	
	private int number;

	public InstructionF2n(Mnemonic mnemonic, int number) {
		super(mnemonic);
		this.number = number;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + Integer.toString(number);
	}
	
	@Override
	public int length() {
		return 2;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(mnemonic.opcode);
		buf[loc + 1] = (byte)((number & 0x0F) << 4);
	}
	
	@Override
	public String getOperands() {
		return String.format("%s", Integer.toString(this.number));
	}
	
}