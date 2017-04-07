package sic.asm.code.directives;

import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveStorageRes extends Node {

	private int value;
	private String symbol;
	
	public DirectiveStorageRes(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	public DirectiveStorageRes(Mnemonic mnemonic, String symbol) {
		super(mnemonic);
		this.symbol = symbol;
	}
	
	@Override
	public int length() {
		if(mnemonic.opcode == Opcode.BYTE) {
			return value;
		}
		return 3 * value;
	}

	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + ((symbol == null) ? Integer.toString(value) : symbol);
	}
	
	@Override
	public String getOperands() {
		return ((symbol == null) ? Integer.toString(value) : symbol);
	}

}
