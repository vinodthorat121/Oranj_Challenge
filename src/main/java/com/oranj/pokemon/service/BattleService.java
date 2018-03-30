package com.oranj.pokemon.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import com.oranj.pokemon.constant.Move;
import com.oranj.pokemon.constant.Status;
import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.domain.Trainer;
import com.oranj.pokemon.domain.TurnRequest;
import com.oranj.pokemon.domain.item.Inventory;
import com.oranj.pokemon.domain.item.Item;
import com.oranj.pokemon.domain.monsters.Pokemon;
import com.oranj.pokemon.repository.BattleRepository;
import com.oranj.pokemon.repository.TrainerRepository;

public class BattleService {
	
	private BattleRepository battleRepository;
	
	private BlockingQueue<Long> awaitingTrainers;

	private TrainerRepository trainerRepository;
	
	private Trainer trainer1;
	
	private Trainer trainer2;
	
	public BattleService(BattleRepository battleRepository, TrainerRepository trainerRepository, Trainer trainer1, Trainer trainer2) {
		this.battleRepository = battleRepository;
		this.trainerRepository = trainerRepository;
		this.trainer1 = trainer1;
		this.trainer2 = trainer2;
	}
	
	public synchronized Battle initBattle(Trainer trainer1, Trainer trainer2) throws Exception {
		Battle battle = new Battle(trainer1.getId(), trainer2.getId());
		battleRepository.saveNew(battle);
		return battle;
	}

	public Battle findMatch(long requestingTrainerId) throws Exception {
		if (battleRepository.getActiveMatch(requestingTrainerId) != null) {
			throw new RuntimeException("User is already in a match!");
		}
		awaitingTrainers.add(requestingTrainerId);
		Long opposingTrainerId = null;
		while (true) {
			Thread.sleep(5000);
			if (awaitingTrainers.size() == 0);
			Set<Long> combattantIds = new HashSet<Long>();
			awaitingTrainers.drainTo(combattantIds, 2);
			for (Long combattantId : combattantIds) {
				if (combattantId != requestingTrainerId) {
					opposingTrainerId = combattantId;
					break;
				}
			}
			break;
		}
		Battle newBattle = new Battle(requestingTrainerId, opposingTrainerId);
		battleRepository.saveNew(newBattle);
		return newBattle;
	}

	public Battle performTurn(TurnRequest turnRequest) throws Exception {
		Battle battle = battleRepository.get(turnRequest.getBattleId());
		switch (turnRequest.getTurnAction()) {
			case ITEM:
				applyItem(turnRequest.getActivatedItemId(), battle);
				break;	
			case PERFORM_MOVE:
				applyMove(turnRequest.getMove(), battle);
				break;
			case SWITCH:
				applySwitch(turnRequest.getSwitchedInPokemonId(), battle);
				break;
			default:
				throw new RuntimeException("Unrecognized action type!");
		}
		checkVictory(battle);
		battle.toggleTurn();
		return battle;
	}

	private void checkVictory(Battle battle) throws Exception {
		if (!getIdleTrainer(battle).getPokemon().stream().anyMatch(pokemon -> pokemon.getHp() > 0)) {
			if (battle.getIsTrainer1Turn()) {
				battle.setIsTrainer1Victor(true);
			} else {
				battle.setIsTrainer1Victor(false);
			}
			getActiveTrainer(battle).markVictory();
			getIdleTrainer(battle).markLoss();
		}
		trainerRepository.updateExisting(getActiveTrainer(battle));
		trainerRepository.updateExisting(getIdleTrainer(battle));
		battleRepository.updateExisting(battle);
	}

	public void applyItem(long activatedItemId, Battle battle) throws Exception {
		Trainer activeTrainer = getActiveTrainer(battle);
		Inventory activeTrainerInventory = activeTrainer.getInventory();
		Item item = activeTrainerInventory.consumeItem(activatedItemId);
		item.apply(battle, activeTrainer, getIdleTrainer(battle));
	}
	
	public void applyMove(Move move, Battle battle) throws Exception {
		// YOUR CODE HERE (don't delete these comments):
		// be sure (hit as many of these requirements as possible):
		// 1.) active pokemon knows the move requested
		// 2.) idle pokemon has health lowered by move.damage as result of attack
		// 3.) BONUS: super effective moves do double damage to idle pokemon (hint - move.elementType.isSuperEffectiveAgainst())
		// 4.) BONUS: move applies move.status to idle pokemon, if applicable
		
		Pokemon activePokemon = getActivePokemon(battle), idlePokemon = getIdlePokemon(battle);
		Move[] oldMove = activePokemon.getMoves();
		Move[] newMove = new Move[oldMove.length+1];
		for(int i=0;i<oldMove.length;i++){
			newMove[i] = oldMove[i];
		}
		newMove[oldMove.length] = move;

		int deltaIdleHp = -move.getDamage();
		if(move.getType().isSuperEffectiveAgainst(idlePokemon.getTypes()))
			deltaIdleHp *= 2;
		int newHp = idlePokemon.getHp()+deltaIdleHp;
		idlePokemon.setHp(Math.max(0, newHp));

		List<Status> newStatus = new ArrayList<>();
		boolean needAdd = true;
		if(move.getStatusInduced() != null){
			for(Status stat: idlePokemon.getStatuses()){
				newStatus.add(stat);
				if(stat == move.getStatusInduced())
					needAdd = false;
			}
			if(needAdd) {
				newStatus.add(move.getStatusInduced());
				idlePokemon.setStatuses(newStatus.toArray(new Status[0]));
			}
		}

	}
	
	public Pokemon getIdlePokemon(Battle battle) throws Exception {
		long idlePokemonId;
		if (battle.getIsTrainer1Turn()) {
			idlePokemonId = battle.getTrainer2SelectedPokemonId();
		} else {
			idlePokemonId = battle.getTrainer1SelectedPokemonId();
		}
		Trainer idleTrainer = getIdleTrainer(battle);
		for (Pokemon pokemon : idleTrainer.getPokemon()) {
			if (pokemon.getId() == idlePokemonId) {
				return pokemon;
			}
		}
		throw new RuntimeException("Could not fetch idle pokemon!");
	}

	public Pokemon getActivePokemon(Battle battle) throws Exception {
		long activePokemonId;
		if (battle.getIsTrainer1Turn()) {
			activePokemonId = battle.getTrainer1SelectedPokemonId();
		} else {
			activePokemonId = battle.getTrainer2SelectedPokemonId();
		}
		Trainer activeTrainer = getActiveTrainer(battle);
		for (Pokemon pokemon : activeTrainer.getPokemon()) {
			if (pokemon.getId() == activePokemonId) {
				return pokemon;
			}
		}
		throw new RuntimeException("Could not fetch active pokemon!");
	}

	public void applySwitch(long swappedInPokemonId, Battle battle) throws Exception {
		if (!getActiveTrainer(battle).hasPokemon(swappedInPokemonId)) {
			throw new RuntimeException("Trainer does not have this pokemon in party!");
		}
		else {
			battle.switchInPokemon(swappedInPokemonId);
		}
	}

	public void validateTurn(TurnRequest turnRequest) throws Exception {
		Battle activeMatch = battleRepository.getActiveMatch(turnRequest.getRequestingTrainerId());
		if (activeMatch.getId() != turnRequest.getBattleId()) {
			throw new RuntimeException("Invalid battle id!");
		}
	}
	
	public Trainer getActiveTrainer(Battle battle) throws Exception {
		if (battle.getIsTrainer1Turn()) {
			return trainer1;
		}
		return trainer2;
	}
	
	private Trainer getIdleTrainer(Battle battle) throws Exception {
		if (battle.getIsTrainer1Turn()) {
			return trainer2;
		}
		return trainer1;
	}
	
}
