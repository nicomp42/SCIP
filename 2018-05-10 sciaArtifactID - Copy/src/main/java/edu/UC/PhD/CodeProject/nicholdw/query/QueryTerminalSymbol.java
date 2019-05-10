package edu.UC.PhD.CodeProject.nicholdw.query;

/**
 * Anything found by the listener method "AntlrMySQLListener.visitTerminal()"
 * @author nicomp
 *
 */
public class QueryTerminalSymbol extends QueryComponent {

	private String terminalSymbolName;
	
	@Override
	public String toString() {return terminalSymbolName;}

	public QueryTerminalSymbol(String terminalSymbolName) throws Exception {
		setTerminalSymbolName(terminalSymbolName);
	}
	public String getTerminalSymbolName() {	return terminalSymbolName;}
	public void setTerminalSymbolName(String terminalSymbolName) throws Exception {
		if (terminalSymbolName == null || terminalSymbolName.trim().length() == 0) {
			throw new Exception("QueryTerminalSymbol.setTerminalSymbolName(): length must be > 0.");
		}
		this.terminalSymbolName = terminalSymbolName.trim();
	}
}
