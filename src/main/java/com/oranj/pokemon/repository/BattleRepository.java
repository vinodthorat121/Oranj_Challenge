package com.oranj.pokemon.repository;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oranj.pokemon.domain.Battle;

public class BattleRepository {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private File jsonDB;

	public BattleRepository() throws URISyntaxException {
		// application default constructor
		jsonDB = new File(Thread.currentThread().getContextClassLoader().getResource("battles.db.json").toURI());
	}
	
	public BattleRepository(File jsonDB) {
		// for special case unit testing
		this.jsonDB = jsonDB;
	}

	public Battle getActiveMatch(long requestingTrainerId) throws Exception {
		// YOUR CODE HERE (don't delete these comments):
		// be sure:
		// 1.) battle is active (no victor declared)
		// 2.) battle contains requested trainer
		//Battle code
		
		List<Battle> allRecords = loadAllRecords();
		for(Battle battle: allRecords) {
			if( (battle.getTrainer1Id()==requestingTrainerId ||
					battle.getTrainer2Id()==requestingTrainerId) &&
					battle.getIsTrainer1Victor() == null)
			    return battle;
		}
		return null;
	}
	
	public void saveNew(Battle newRecord) throws Exception {
		List<Battle> allRecords = loadAllRecords();
		Battle lastBattle = allRecords.get(allRecords.size()-1);
		newRecord.setId(lastBattle.getId()+1);
		allRecords.add(newRecord);
		updateRepo(allRecords);
	}
	
	public Battle get(long id) throws Exception {
		List<Battle> allRecords = loadAllRecords();
		for (int i=0; i<allRecords.size(); i++) {
			Battle existingRecord = allRecords.get(i);
			if (existingRecord.getId() == id) {
				return existingRecord;
			}
		}
		throw new RuntimeException("Record not found!");
	}

	protected void updateRepo(List<Battle> allRecords)
			throws Exception {
		objectMapper.writeValue(jsonDB, allRecords);
	}
		
	public void updateExisting(Battle recordToUpdate) throws Exception {
		List<Battle> allRecords = loadAllRecords();
		for (int i=0; i<allRecords.size(); i++) {
			Battle existingRecord = allRecords.get(i);
			if (existingRecord.getId() == recordToUpdate.getId()) {
				allRecords.remove(i);
				allRecords.add(i, recordToUpdate);
				updateRepo(allRecords);
				return;
			}
		}
		throw new RuntimeException("Failed to find existing element!");
	}
	
	public List<Battle> loadAllRecords() throws Exception {
		return objectMapper.readValue(jsonDB, new TypeReference<List<Battle>>(){});
	}

}
