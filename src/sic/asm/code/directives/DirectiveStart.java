package sic.asm.code.directives;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.SemanticError;
import sic.asm.mnemonics.Mnemonic;

public class DirectiveStart extends Node {
	
	private int value;
	private String symbol;
	private int resolved;

	public DirectiveStart(Mnemonic mnemonic, int value) {
		super(mnemonic);
		this.value = value;
	}
	
	public DirectiveStart(Mnemonic mnemonic, String symbol) {
		super(mnemonic);
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return (this.getLabel().isEmpty() ? "" : this.getLabel()) + "\t" + mnemonic.toString() + "\t" + ((symbol == null) ? Integer.toString(value) : symbol);
	}
	
	@Override
	public void resolve(Code code) throws SemanticError{
		code.setName(this.label);
		if(symbol == null) {
			resolved = value;
		} else {
			resolved = code.resolveSymbol(symbol);
		}
		code.setStart(resolved);
	}
}