package edu.UC.PhD.CodeProject.nicholdw.testCase;

import java.lang.reflect.InvocationTargetException;

import edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.EvolutionOperator;
import edu.UC.PhD.CodeProject.nicholdw.evolutionOperators.Schema;

public class TestCaseQuery {
	private EvolutionOperator evolutionOperator;
	private String sql;
	
	public TestCaseQuery(EvolutionOperator evolutionOperator) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String name = evolutionOperator.getClass().getName();
		this.evolutionOperator = evolutionOperator.getClass().getConstructor(Schema.class).newInstance(evolutionOperator);
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public EvolutionOperator getEvolutionOperator() {return evolutionOperator;}


}



