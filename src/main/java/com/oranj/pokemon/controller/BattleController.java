package com.oranj.pokemon.controller;

import com.oranj.pokemon.domain.Battle;
import com.oranj.pokemon.domain.TurnRequest;
import com.oranj.pokemon.service.BattleService;

public class BattleController {

	private BattleService battleService;
	
	public BattleController(BattleService battleService) {
		this.battleService = battleService;
	}
	
	public Battle joinBattle(long requestingTrainerId) throws Exception {
		return battleService.findMatch(requestingTrainerId);
    }
    
	public Battle performMove(TurnRequest turnRequest) throws Exception {
    		battleService.validateTurn(turnRequest);
		return battleService.performTurn(turnRequest);
    }
	
}
