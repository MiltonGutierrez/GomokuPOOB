package test;
import domain.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GomokuTest {
	private Gomoku board = Gomoku.getGomoku();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void shouldCreateRivals1() {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.createRivals();
		for(Player p: board.getPlayers().values()) {
			assertTrue(p instanceof Human);
		}
		assertEquals(board.getTurn(), board.returnP1());
	}
	
	@Test
	void shouldCreateRivals2() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setOpponent("pve");
		board.setMachineType("Expert");
		board.createRivals();
		assertTrue(board.loadPlayer("Mutsia") instanceof Human);
		assertTrue(board.loadPlayer("machine") instanceof Machine);
		assertEquals(board.getTurn(), board.returnP1());
	}
	
	
}
