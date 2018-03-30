package com.oranj.pokemon.repository;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oranj.pokemon.domain.Trainer;

public class TrainerRepository {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private File jsonDB;

	public TrainerRepository() throws URISyntaxException {
		// application default constructor
		jsonDB = new File(Thread.currentThread().getContextClassLoader().getResource("trainers.db.json").toURI());
	}
	
	public TrainerRepository(File jsonDB) {
		// for special case unit testing
		this.jsonDB = jsonDB;
	}
	
	public void saveNew(Trainer newRecord) throws Exception {
		List<Trainer> allRecords = loadAllRecords();
		Trainer lastTrainer = allRecords.get(allRecords.size()-1);
		newRecord.setId(lastTrainer.getId()+1);
		allRecords.add(newRecord);
		updateRepo(allRecords);
	}
	
	public Trainer get(long id) throws Exception {
		List<Trainer> allRecords = loadAllRecords();
		for (int i=0; i<allRecords.size(); i++) {
			Trainer existingRecord = allRecords.get(i);
			if (existingRecord.getId() == id) {
				return existingRecord;
			}
		}
		throw new RuntimeException("Record not found!");
	}

	public void updateRepo(List<Trainer> allRecords)
			throws Exception {
		System.out.println(objectMapper.writeValueAsString(allRecords));
		objectMapper.writeValue(jsonDB, allRecords);
	}
		
	public void updateExisting(Trainer recordToUpdate) throws Exception {
		List<Trainer> allRecords = loadAllRecords();
		for (int i=0; i<allRecords.size(); i++) {
			Trainer existingRecord = allRecords.get(i);
			if (existingRecord.getId() == recordToUpdate.getId()) {
				allRecords.remove(i);
				allRecords.add(i, recordToUpdate);
				updateRepo(allRecords);
				return;
			}
		}
		throw new RuntimeException("Failed to find existing element!");
	}
	
	public List<Trainer> loadAllRecords() throws Exception {
		return objectMapper.readValue(jsonDB, new TypeReference<List<Trainer>>(){});
	}

}
