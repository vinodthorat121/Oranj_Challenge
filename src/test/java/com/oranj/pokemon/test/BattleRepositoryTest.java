package com.oranj.pokemon.test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionAssert;

import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.repository.BattleRepository;

import static java.util.Arrays.asList;

public class BattleRepositoryTest {
	
	private Path backup;

	@Before
	public void before() throws IOException, URISyntaxException {
		backup = File.createTempFile("battles_backup.db", "json").toPath();
		Files.copy(TestUtils.pathOfResource("battles_2.db.json"), backup, StandardCopyOption.REPLACE_EXISTING);
	}
	
	@After
	public void after() throws IOException, URISyntaxException {
		Files.copy(backup, TestUtils.pathOfResource("battles_2.db.json"), StandardCopyOption.REPLACE_EXISTING);
	}
	
	@Test
	public void loadAllRecords_parsesJson() throws Exception {
		BattleRepository battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_2.db.json"));
		List<Battle> allRecordsActual = battleRepository.loadAllRecords();	
		
		List<Battle> allRecordsExpected = new ArrayList<Battle>();
		Battle inProgressBattle = new Battle(1, 5, 28, 2, 84, false);
		Battle endedBattle = new Battle(2, 3, 6, true);
		allRecordsExpected.add(inProgressBattle);
		allRecordsExpected.add(endedBattle);
		
		//ReflectionAssert.assertReflectionEquals(allRecordsExpected, allRecordsActual);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(allRecordsExpected,allRecordsActual);
	}
	
	@Test
	public void getActiveMatch_getsActiveMatchForTrainerId() throws Exception {
		BattleRepository battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_2.db.json"));
		Battle actualActiveMatch = battleRepository.getActiveMatch(5);
		
		Battle expectedActiveMatch = new Battle(1, 5, 28, 2, 84, false);
		
		if(actualActiveMatch.equals(expectedActiveMatch)) {
			System.out.println("true");
		}
		
		
		//ReflectionAssert.assertReflectionEquals(asList(expectedActiveMatch), asList(actualActiveMatch));
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(expectedActiveMatch,actualActiveMatch);
	}
	
	@Test
	public void saveNew_insertsNewRecordAtEnd() throws Exception {
		
		BattleRepository battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_2.db.json"));
		battleRepository.saveNew(new Battle(99, 100));
		List<Battle> allRecordsActual = battleRepository.loadAllRecords();
		
		List<Battle> allRecordsExpected = new ArrayList<Battle>();
		Battle inProgressBattle = new Battle(1, 5, 28, 2, 84, false);
		Battle endedBattle = new Battle(2, 3, 6, true);
		Battle newBattle = new Battle(99, 100);
		newBattle.setId(3);
		allRecordsExpected.add(inProgressBattle);
		allRecordsExpected.add(endedBattle);
		allRecordsExpected.add(newBattle);
		
		//ReflectionAssert.assertReflectionEquals(allRecordsExpected, allRecordsActual);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(allRecordsExpected,allRecordsActual);

	}
	
	@Test
	public void getById_FetchesCorrectBattle() throws Exception {
		BattleRepository battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_2.db.json"));
		Battle actualBattle = battleRepository.get(2);
		Battle expectedBattle = new Battle(2, 3, 6, true);
		//ReflectionAssert.assertReflectionEquals(expectedBattle, actualBattle);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(expectedBattle, actualBattle);

	}
	
	@Test
	public void updateExisting_UpdatesBattleRecord() throws Exception {
		BattleRepository battleRepository = new BattleRepository(TestUtils.loadFromResources("battles_2.db.json"));
		
		Battle updatedBattle = new Battle(1, 5, 2, true);
		battleRepository.updateExisting(updatedBattle);
		Battle actualBattleAfterUpdate = battleRepository.get(1);
		Battle expectedBattleAfterUpdate = new Battle(1, 5, 2, true);
		//ReflectionAssert.assertReflectionEquals(expectedBattleAfterUpdate, actualBattleAfterUpdate);
		org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(expectedBattleAfterUpdate, actualBattleAfterUpdate);

	}
	
}
