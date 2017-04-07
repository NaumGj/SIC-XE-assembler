package sic.asm.code.instructions;

import sic.asm.code.Node;
import sic.asm.code.Registers;
import sic.asm.mnemonics.Mnemonic;

public class InstructionF3 extends Node {

	public InstructionF3(Mnemonic mnemonic) {
		super(mnemonic);
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
	}
	
	@Override
	public int length() {
		return 3;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)((mnemonic.opcode & 0xFC) | 0x3);
		buf[loc + 1] = (byte)0;
		buf[loc + 2] = (byte)0;
	}

}