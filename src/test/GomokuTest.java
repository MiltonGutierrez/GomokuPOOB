package test;
import domain.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GomokuTest {
	private Gomoku board = Gomoku.getGomoku();
	
	@BeforeEach
	void setUp() throws Exception {
		board.setDimension(15);
	}

	@AfterEach
	void tearDown() throws Exception {
		board.resetAll();
		
	}

	@Test
	void shouldCreateRivals1() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.createRivals();
		assertTrue(board.loadPlayer("Mutsia") instanceof Human);
		assertTrue(board.loadPlayer("Miltown") instanceof Human);
	}
	
	@Test
	void shouldCreateRivals2() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("machine");
		board.setOpponent("pve");
		board.setMachineType("Agressive");
		board.createRivals();
		assertTrue(board.loadPlayer("Mutsia") instanceof Human);
		assertTrue(board.loadPlayer("machine") instanceof Machine);
	}
	@Test
	void shouldStartGame() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		assertTrue(board.ok());
		assertNotEquals(board.getTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Mutsia").returnTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Miltown").returnTokenMatrix(), null);
		assertNotEquals(board.loadPlayer("Mutsia").getTokenToUse(), null);
		assertNotEquals(board.loadPlayer("Miltown").getTokenToUse(), null);
		assertNotEquals(board.getTypesOfTokens(), null);
		assertEquals(board.getTurn(), "Mutsia");
	}
	
	@Test
	void shouldCreateMatrixBox() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		for(int i = 0; i < Gomoku.getGomoku().getDimension(); i++) {
			for(int j = 0; j < Gomoku.getGomoku().getDimension(); j++) {
				assertTrue(Gomoku.getGomoku().getBox(i, j) instanceof Box);
			}
		}
	}
	
	@Test
	void shouldAddTokenCreateNormal() throws GomokuException {
		assertTrue(board.addToken("Normal", "Mutsia", new int[] {0,0}) instanceof NormalToken);
		assertTrue(board.ok());
	}
	@Test
	void shouldAddTokenCreateHeavy() throws GomokuException {
		assertTrue(board.addToken("Heavy", "Mutsia", new int[] {0,3}) instanceof HeavyToken);
		assertTrue(board.ok());
	}
	@Test
	void shouldAddTokenCreateTemporal() throws GomokuException {
		assertTrue(board.addToken("Temporal", "Mutsia", new int[] {0,1}) instanceof TemporalToken);
		assertTrue(board.ok());
	}
	
	@Test
	void shouldAddTokenSetNormal() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t = board.addToken("Normal", "Mutsia", new int[] {0,0});
		assertTrue(board.ok());
		board.addToken("Mutsia", t, new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldAddTokenSetTemporal() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t3 = board.addToken("Temporal", "Mutsia", new int[] {0,3});
		assertTrue(board.ok());
		board.addToken("Mutsia", t3, new int[] {0,3});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldBeTemporalToken() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
	    board.loadPlayer("Mutsia").addTokenToUse("Temporal");
	    board.play(0, 0);
	    board.play(0, 1);
	    board.play(0, 2);
	    board.play(0, 3);
	    assertEquals(board.getToken(0, 0), null);
	}
	
	@Test
	void shouldAddTokenSetHeavy()  throws GomokuException{
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t2 = board.addToken("Heavy", "Mutsia", new int[] {0,1});
		assertTrue(board.ok());
		board.addToken("Mutsia", t2, new int[] {0,1});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldBeHeavyToken()  throws GomokuException{
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
	    board.startGame();
		Token t = board.addToken("Heavy", "Mutsia", new int[] {0,0});
		assertEquals(t.getValue(), 2);
	}
	
	@Test
	void shouldCreateNormalBox()  throws GomokuException{
		board.createBox("Normal", new int[] {0,0});
		assertTrue(board.ok());
		
	}
	
	@Test
	void shouldCreateGoldenBox() throws GomokuException {
		board.createBox("Golden", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldCreateTeleportingBox() throws GomokuException {
		board.createBox("Teleporting", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldCreateExplosiveBox()  throws GomokuException{
		board.createBox("Explosive", new int[] {0,0});
		assertTrue(board.ok());
	}
	
	@Test
	void shouldExplodeNearTokens()  throws GomokuException{
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
	    board.startGame();
	    Box b = board.createBox("Explosive", new int[] {0,0});
	    board.getBoxMatrix()[0][0] = b;
	    board.play(0,1);
	    board.play(1,0);
	    board.play(1,1);
	    board.play(0,0);
	    
	}
	
	@Test
	void shouldValidPlay() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		assertTrue(board.validPlay(0, 0));
	}
	
	@Test
	void shouldCalculateLastPositions() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		Token t = board.addToken("Normal", board.getTurn(),new int[] {0, 1}); //
		board.addToken("Mutsia", t, new int[] {0,1});
		board.getToken(0, 1).setIdentifier('D');
		board.calculateLastPositionTokens();
		assertEquals(board.getLastPositionTokens().size(), 1);
	}
	
	@Test
	void shouldCalculateLastPositions1() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.startGame();
		Token t = board.addToken("Temporal", board.getTurn(),new int[] {0, 1}); //
		board.addToken("Mutsia", t, new int[] {0,1});
		board.getToken(0, 1).setIdentifier('D');
		board.calculateLastPositionTokens();
		assertEquals(board.getLastPositionTokens().size(), 1);
	}
	
	@Test
	void shouldDiscountTokens() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("limited");
		board.createRivals();
		board.startGame();
		int tokensLeft = board.loadPlayer("Mutsia").getTokensLeft();
		board.play(1, 1);
		assertTrue(board.loadPlayer("Mutsia").getTokensLeft()-1 < tokensLeft);
	}
	
	@Test
	void shouldMachinePlaceToken() throws GomokuException {
		board.setNameP1("Jeidson");
		board.setNameP2("machine");
		board.setOpponent("pve");
		board.setMachineType("Agressive");
		board.createRivals();
		board.setGameMode("limited");
		board.startGame();
		int tokensLeft = board.loadPlayer("machine").getTokensLeft();
		board.play(1, 1);
		assertTrue(board.loadPlayer("machine").getTokensLeft() -1 < tokensLeft);
	}
	
	@Test
	void shouldMachineNotPlaceToken() throws GomokuException {
		board.setNameP1("Jeidson");
		board.setNameP2("machine");
		board.setOpponent("pve");
		board.setMachineType("Fearful");
		board.createRivals();
		board.setGameMode("limited");
		board.startGame();
		int tokensLeft = board.loadPlayer("machine").getTokensLeft();
		board.play(1, 1);
		System.out.println(board.loadPlayer("machine").getTokensLeft());
		assertTrue(board.loadPlayer("machine").getTokensLeft() == tokensLeft);
	}
	
	@Test
	public void ShouldSaveGomoku(){
        try {
            Path tempDir = Files.createTempDirectory("Gomoku_test");
            File saveFile = new File(tempDir.toFile(), "Gomoku_save.dat");
           board.save(saveFile);
            assertTrue(saveFile.exists());
        } catch (GomokuException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
	
	@Test
    public void ShouldLoadGomoku() {
        try {
            Path tempDir = Files.createTempDirectory("gomoku_test");
            File tempFile = new File(tempDir.toFile(), "gomoku_save.dat");
            board.save(tempFile);
            board.open01(tempFile);
            assertNotNull(board);
        } catch (GomokuException e) {
            fail(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
	
	@Test
	void shouldWin() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.play(10, 10);
		board.play(1, 2);
		board.play(11, 11);
		board.play(1, 3);
		board.play(9, 9);
		board.play(1, 4);
		board.play(13, 13);
		board.play(1, 5);
		assertTrue(board.getGomokuFinished());
	}
	
	@Test
	void shouldWin1() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.play(10, 10);
		board.play(1, 2);
		board.play(11, 11);
		board.play(1, 3);
		board.play(12, 12);
		board.play(1, 10);
		board.play(13, 13);
		board.play(1, 5);
		board.play(14, 14);
		assertTrue(board.getGomokuFinished());
	}
	@Test
	void shouldWin2() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.play(14, 14);
		board.play(1, 2);
		board.play(13, 13);
		board.play(1, 3);
		board.play(12, 12);
		board.play(1, 10);
		board.play(11, 11);
		board.play(1, 5);
		board.play(10, 10);
		assertTrue(board.getGomokuFinished());
	}
	
	@Test
	void shouldWin3() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.play(14, 14);
		board.play(1, 2);
		board.play(13, 13);
		board.play(1, 3);
		board.play(12, 12);
		board.play(1, 10);
		board.play(11, 11);
		board.play(1, 5);
		board.play(10, 10);
		assertTrue(board.getGomokuFinished());
	}
	@Test
	void shouldWin4() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.play(14, 14);
		board.play(2, 1);
		board.play(13, 13);
		board.play(3, 1);
		board.play(12, 12);
		board.play(4, 1);
		board.play(11, 11);
		board.play(5, 1);
		board.play(10, 5);
		assertTrue(board.getGomokuFinished());
	}
	@Test
	void shouldDelete() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		board.deleteToken(board.getToken(1, 1));
		assertTrue(board.getToken(1, 1) == null);
	}
	
	@Test
	void shouldNotDelete() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		try {
			board.deleteToken(board.getToken(2, 2));
		}catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	void shouldAddScore() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(100);
		board.setTokenPercentage(100);
		board.startGame();
		board.play(1, 1);
		assertTrue(board.loadPlayer("Mutsia").getPuntuacion() > 0);
	}
	@Test
	void shouldNotAddScore() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(100);
		board.setTokenPercentage(100);
		board.startGame();
		board.play(1, 1);
		assertTrue(board.loadPlayer("Miltown").getPuntuacion() == 0);
	}
	
	@Test
	void shouldNotAddScore1() throws GomokuException {
		board.resetAll();
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(0);
		board.setTokenPercentage(0);
		board.startGame();
		board.play(1, 1);
		assertTrue(board.loadPlayer("Mutsia").getPuntuacion() == 0);
	}
	
	@Test
	void shouldTeleport() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(100);
		board.setTokenPercentage(0);
		board.startGame();
		for(int i = 0; i< board.getDimension() -1 ; i++) {
			for (int j = 0; j< board.getDimension() - 1 ; j++) {
				if(board.getBox(i, j) instanceof TeleportingBox) {
					board.play(i, j);
					assertTrue(board.getToken(i, j) == null);
				}
			}
		}
	}
	
	@Test
	void shouldGolden() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(100);
		board.setTokenPercentage(0);
		board.startGame();
		int tokensLeft = board.loadPlayer("Mutsia").getTokensLeft();
		for(int i = 0; i< board.getDimension() -1 ; i++) {
			for (int j = 0; j< board.getDimension() - 1 ; j++) {
				if(board.getBox(i, j) instanceof GoldenBox) {
					board.play(i, j);
					break;
				}
			}
		}
		assertTrue(board.loadPlayer("Mutsia").getTokensLeft() == tokensLeft);
	}
	
	@Test
	void shouldExplosive() throws GomokuException {
		board.setNameP1("Mutsia");
		board.setNameP2("Miltown");
		board.setOpponent("pvp");
		board.setGameMode("normal");
		board.createRivals();
		board.setBoxPercentage(100);
		board.setTokenPercentage(0);
		board.startGame();
		int tokensLeft = board.loadPlayer("Mutsia").getTokensLeft();
		int xPos =0;
		int yPos =0;
		for(int i = 0; i< board.getDimension() -1 ; i++) {
			for (int j = 0; j< board.getDimension() - 1 ; j++) {
				board.play(i, j);
				if(board.getBox(i, j) instanceof ExplosiveBox) {
					board.play(i, j);
					xPos = i;
					yPos = j;
					break;
				}
			}
		}
		if(!(xPos + 1 >= board.getDimension())) {
    		assertTrue(board.getToken(xPos + 1, yPos) == null);
    		
    	}else if(!(xPos - 1 < 0)) {
    		assertTrue(board.getToken(xPos - 1, yPos) == null);
    		
    	}else if(!(yPos - 1 < 0)){
    		assertTrue(board.getToken(xPos, yPos - 1) == null);
    		
    	}else if(!(yPos + 1 >= board.getDimension())) {
    		assertTrue(board.getToken(xPos, yPos + 1) == null);
    	}
	}
	
}



