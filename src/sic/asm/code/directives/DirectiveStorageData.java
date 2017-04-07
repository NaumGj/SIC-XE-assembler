package sic.asm.code.directives;

import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveStorageData extends Node {
	
	private byte[] value;
	private String label;
//	private String symbol;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public DirectiveStorageData(Mnemonic mnemonic, byte[] value) {
		super(mnemonic);
		this.value = value;
	}
	
//	public DirectiveStorageData(Mnemonic mnemonic, String symbol) {
//		super(mnemonic);
//		this.symbol = symbol;
//	}
	
	@Override
	public String toString() {
		String returnString = (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString();
		for (byte val : value) {
			returnString += Byte.toString(val);
		}
		return returnString;
	}
	
	@Override
	public int length() {
		if(mnemonic.opcode == Opcode.BYTE) {
			return value.length;
		}
		return (value.length + 2) / 3 * 3;
	}
	
	@Override
	public void emitCode(byte[] buf, int loc) {
		buf[loc] = (byte)(value[0]);
		if(mnemonic.opcode == Opcode.WORD){
			buf[loc + 1] = (byte)(value[1]);
			buf[loc + 2] = (byte)(value[2]);
		}
	}
	
	@Override
	public String getOperands() {
		String returnString = "";
		for (byte val : value) {
			if (val > 0) {
				returnString += Byte.toString(val);
			}
		}
		return returnString.isEmpty() ? "0" : returnString;
	}
		
}