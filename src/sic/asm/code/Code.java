package sic.asm.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sic.asm.code.directives.DirectiveOrg;
import sic.asm.code.directives.DirectiveStorageData;
import sic.asm.code.directives.DirectiveStorageRes;
import sic.asm.code.instructions.InstructionLiteral;

/**
 * Podporni razred za predmet Sistemska programska oprema.
 * @author jure
 */
public class Code {
	
	public static final int MAX_ADDR = 0xFFFFF;
	public static final int MAX_WORD = 1 << 20;

	private String name;
	private int start;
	private int end;
	private int locCtr;
	private int prevLocCtr;
	private int nextLocCtr;
	private int txtCtr;
	private int orgCtr;

	private List<Node> program = new ArrayList<Node>();
	private List<Reloc> relocs = new ArrayList<Reloc>();
	private int baseReg;
	private Map<String, Integer> symbolTable = new HashMap<String, Integer>();
	public static List<DirectiveStorageData> literals = new ArrayList<DirectiveStorageData>();
	public static int litCtr = 0;
	
	public int getTxtCtr() {
		return txtCtr;
	}
	public void setTxtCtr(int txtCtr) {
		this.txtCtr = txtCtr;
	}
	public List<DirectiveStorageData> getLiterals() {
		return literals;
	}
	public void setLiterals(List<DirectiveStorageData> literals) {
		this.literals = literals;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getLocCtr() {
		return locCtr;
	}
	public void setLocCtr(int locCtr) {
		this.locCtr = locCtr;
	}
	public int getPrevLocCtr() {
		return prevLocCtr;
	}
	public void setPrevLocCtr(int prevLocCtr) {
		this.prevLocCtr = prevLocCtr;
	}
	public int getNextLocCtr() {
		return nextLocCtr;
	}
	public void setNextLocCtr(int nextLocCtr) {
		this.nextLocCtr = nextLocCtr;
	}
	public List<Node> getProgram() {
		return program;
	}
	public void setProgram(List<Node> program) {
		this.program = program;
	}
	public List<Reloc> getRelocs() {
		return relocs;
	}
	public void setRelocs(List<Reloc> relocs) {
		this.relocs = relocs;
	}
	
	public void append(Node instruction) {
		if(instruction instanceof InstructionLiteral) {
			literals.add(instruction.toDataDirective());
			instruction = instruction.getInstruction();
		}
		program.add(instruction);
		instruction.enter(this);
		instruction.activate(this);
		instruction.leave(this);
	}
	
	public void print() {
		for(Node instruction : program) {
			System.out.println(instruction.toString());
		}
	}
	
	public void begin(){
		locCtr = nextLocCtr = start;
		orgCtr = -1;
		baseReg = -1;
	}
	
	public int getOrgCtr() {
		return orgCtr;
	}
	public void setOrgCtr(int orgCtr) {
		this.orgCtr = orgCtr;
	}
	public void end() {
		locCtr = nextLocCtr = end;
		baseReg = -1;
	}
	
	public void defineSymbol(String sym, int val) {
		symbolTable.put(sym, val);
	}
	
	public int resolveSymbol(String symbol) throws SemanticError {
		Integer val = symbolTable.get(symbol);
		if (val == null) {
			throw new SemanticError(String.format("Symbol %s not defined", symbol));
		}
		return val;
	}
	
	public int getBaseReg() {
		return baseReg;
	}
	public void setBaseReg(int baseReg) {
		this.baseReg = baseReg;
	}
	public Map<String, Integer> getSymbolTable() {
		return symbolTable;
	}
	public void setSymbolTable(Map<String, Integer> symbolTable) {
		this.symbolTable = symbolTable;
	}
	public boolean isPCRelative(int resolved) {
		return ((resolved - nextLocCtr) >= -2048) && ((resolved - nextLocCtr) <= 2047);
	}
	
	public boolean isBaseRelative(int resolved) {
		return baseReg != -1 && (resolved - baseReg) >=0 && (resolved - baseReg) <= 4095;
	}
	
	public void addReloc(Reloc reloc) {
		relocs.add(reloc);
	}
	
	public void resolve() throws SemanticError {
		for(Node instruction : program) {
			instruction.enter(this);
			instruction.resolve(this);
			instruction.leave(this);
		}
	}
	
	public String emitRawText() {
		StringBuilder obj =  new StringBuilder();
		for(Node instruction : program) {
//			System.out.println(instruction.mnemonic);
//			System.out.println(instruction.emitRawText());
			instruction.enter(this);
			obj.append(instruction.emitRawText());
			instruction.leave(this);
		}
//		System.out.println(obj.toString());
		return obj.toString();
	}
	
	public String emitLstText() {
		StringBuilder lst =  new StringBuilder();
		for(Node instruction : program) {
			instruction.enter(this);
			lst.append(instruction.emitLstText(this));
			instruction.leave(this);
		}
		return lst.toString();
	}
	
	public String emitObjCode() {
		StringBuilder obj =  new StringBuilder();
		StringBuilder txtBuf = new StringBuilder();
		obj.append(String.format("H%-6s%06X%06X\n", name, start, end - start));
		for(Node instruction : program) {
			instruction.enter(this);
			txtBuf.append(instruction.emitRawText());
			if(txtBuf.length() > 56 || (instruction instanceof DirectiveStorageRes) || (instruction instanceof DirectiveOrg)) {
				if(txtBuf.length() > 0){
					obj.append(String.format("T%06X%02X", txtCtr, txtBuf.length()/2));
					obj.append(txtBuf.toString() + '\n');
					txtBuf.setLength(0);
				}
				txtCtr = locCtr + instruction.length();
			}
			instruction.leave(this);
		}
		if(txtBuf.length() > 0){
			obj.append(String.format("T%06X%02X", txtCtr, txtBuf.length()/2));
			obj.append(txtBuf.toString() + '\n');
			txtBuf.setLength(0);
		}
		for (Reloc reloc : relocs) {
			obj.append(String.format("M%06X%02X\n", reloc.getctr(), reloc.getnibbles()));
		}
		obj.append(String.format("E%06X\n", start));
//		System.out.println(obj.toString());
		return obj.toString();
	}
	
}
