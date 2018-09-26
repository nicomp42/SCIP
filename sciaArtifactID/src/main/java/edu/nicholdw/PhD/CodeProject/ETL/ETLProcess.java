package edu.nicholdw.PhD.CodeProject.ETL;

import edu.UC.PhD.CodeProject.nicholdw.query.QueryDefinition;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryType;
import edu.UC.PhD.CodeProject.nicholdw.queryType.QueryTypeSelect;

public class ETLProcess {
	private String name;
	private String description;
	private ETLSteps etlSteps;
	private ETLConnections etlConnections;
	
	public ETLProcess() {
		etlSteps = new ETLSteps();
		etlConnections = new ETLConnections();
	}
	public void processTableInputSteps() {
		for (ETLStep etlStep : etlSteps) {
			if (etlStep.getStepType().equals("TableInput")) {
				// Look up the connection 
				ETLConnection etlConnection = etlConnections.getConnection(etlStep.getConnection());
				// Parse the query
				etlStep.setQueryDefinition(new QueryDefinition(etlConnection.getServer(), 
						                                       etlConnection.getUserName(), 
						                                       etlConnection.getPasswordDefinedExternally(),
						                                       new QueryTypeSelect(),
						                                       "",				// No query name 'cause it's from an ETLStep 
						                                       etlStep.getSql(),
						                                       etlConnection.getDatabase()));
				etlStep.getQueryDefinition().crunchIt();
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ETLSteps getETLSteps() {
		return etlSteps;
	}

	public void setEtlSteps(ETLSteps etlSteps) {
		this.etlSteps = etlSteps;
	}

	public ETLConnections getETLConnections() {
		return etlConnections;
	}

	public void setEtlConnections(ETLConnections etlConnections) {
		this.etlConnections = etlConnections;
	}
	
}
