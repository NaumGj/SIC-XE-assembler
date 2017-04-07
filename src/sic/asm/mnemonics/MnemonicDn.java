package sic.asm.mnemonics;

import sic.asm.code.Code;
import sic.asm.code.Node;
import sic.asm.code.Opcode;
import sic.asm.code.directives.DirectiveBase;
import sic.asm.code.directives.DirectiveEnd;
import sic.asm.code.directives.DirectiveEqu;
import sic.asm.code.directives.DirectiveOrg;
import sic.asm.code.directives.DirectiveStart;
import sic.asm.parsing.Parser;
import sic.asm.parsing.SyntaxError;


/**
 * Directive with one numeric operand.
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class MnemonicDn extends Mnemonic {
	
	private int opcode;

	public MnemonicDn(String mnemonic, int opcode, String hint, String desc) {
		super(mnemonic, opcode, hint, desc);
		this.opcode = opcode;
	}
	
	@Override
	public Node parse(Parser parser) throws SyntaxError {
		switch (opcode) {
			case Opcode.START:
				// number
				if (Character.isDigit(parser.lexer.peek()))
					return new DirectiveStart(this, parser.parseNumber(0, Code.MAX_ADDR));
				// symbol
				else if (Character.isLetter(parser.lexer.peek()))
					return new DirectiveStart(this, parser.parseSymbol());
				// otherwise: error
				else
					throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			case Opcode.END:
				// number
				if (Character.isDigit(parser.lexer.peek()))
					return new DirectiveEnd(this, parser.parseNumber(0, Code.MAX_ADDR));
				// symbol
				else if (Character.isLetter(parser.lexer.peek()))
					return new DirectiveEnd(this, parser.parseSymbol());
				// otherwise: error
				else
					throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			case Opcode.BASE:
				// number
				if (Character.isDigit(parser.lexer.peek()))
					return new DirectiveBase(this, parser.parseNumber(0, Code.MAX_ADDR));
				// symbol
				else if (Character.isLetter(parser.lexer.peek()))
					return new DirectiveBase(this, parser.parseSymbol());
				// otherwise: error
				else
					throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			case Opcode.EQU:
				// number
				if (Character.isDigit(parser.lexer.peek()))
					return new DirectiveEqu(this, parser.parseNumber(0, Code.MAX_ADDR));
				// symbol
				else if (Character.isLetter(parser.lexer.peek()))
					return new DirectiveEqu(this, parser.parseSymbol());
				// symbol
				else if (parser.lexer.peek() == '*')
					return new DirectiveEqu(this, Character.toString('*'));
				// otherwise: error
				else
					throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			case Opcode.ORG:
				// number
				if (Character.isDigit(parser.lexer.peek()))
					return new DirectiveOrg(this, parser.parseNumber(0, Code.MAX_ADDR));
				// symbol
				else if (Character.isLetter(parser.lexer.peek()))
					return new DirectiveOrg(this, parser.parseSymbol());
				// otherwise: error
				else
					throw new SyntaxError(String.format("Invalid character '%c", parser.lexer.peek()), parser.lexer.row, parser.lexer.col);
			default:
				throw new SyntaxError(String.format("Invalid opcode %d", opcode), parser.lexer.row, parser.lexer.col);
		}
	}

//	@Override
//	public String operandToString(Node instruction) {
//		Directive i = ((Directive)instruction);
//		return i.symbol != null ? i.symbol : Integer.toString(i.value);
//	}

}
