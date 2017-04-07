package sic.asm.code.directives;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.SemanticError;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveOrg extends Node {
	
	private int value;
	private String symbol;
	private int resolved;

	public DirectiveOrg(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	public DirectiveOrg(Mnemonic mnemonic, String symbol) {
		super(mnemonic);
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + ((symbol == null) ? Integer.toString(value) : symbol);
	}
	
	public void leave(Code code) {
		if(code.getOrgCtr() < 0) {
			code.setOrgCtr(code.getNextLocCtr());
		}
		code.setLocCtr(resolved);
		code.setNextLocCtr(resolved);
	}
	
	@Override
	public void resolve(Code code) throws SemanticError{
		if(symbol == null) {
			resolved = value;
		} else {
			resolved = code.resolveSymbol(symbol);
		}
	}

}