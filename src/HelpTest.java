import static org.junit.Assert.*;
import org.junit.*;

/**
 * @author petrosky
 *
 * Class to test the help function
 */
public class HelpTest {
	
	//No bombs and no revealed tiles
	@Test
	public void noBombsNoTilesRevealed() {
		Field f = new Field(2, 1, new int[] {}, new int[] {});
		f.help();
		Tile[][] t = f.testTiles();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//No bombs and all tiles revealed
	@Test
	public void noBombsAllTilesRevealed() {
		Field f = new Field(2, 1, new int[] {}, new int[] {});
		Tile[][] t = f.testTiles();
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				t[x][y].reveal();
			}
		}
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//One bomb and no revealed tiles
	@Test
	public void oneBombNoTilesRevealed() {
		Field f = new Field(2, 1, new int[] {1}, new int[] {1});
		f.help();
		Tile[][] t = f.testTiles();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//Multiple bombs and no revealed tiles
	@Test
	public void multBombsNoTilesRevealed() {
		Field f = new Field(2, 1, new int[] {0,1}, new int[] {0,1});
		f.help();
		Tile[][] t = f.testTiles();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
		
	//One bomb and one revealed tile
	@Test
	public void oneBombOneTileRevealed() {
		Field f = new Field(2, 1, new int[] {0}, new int[] {0});
		Tile[][] t = f.testTiles();
		t[1][1].reveal();
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//One bomb and all tiles revealed
	@Test
	public void oneBombAllTilesRevealed() {
		Field f = new Field(2, 1, new int[] {0}, new int[] {0});
		Tile[][] t = f.testTiles();
		t[0][1].reveal();
		t[1][0].reveal();
		t[1][1].reveal();
		f.help();
		assertTrue(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//Multiple bombs and one tile revealed
	@Test
	public void multBombOneTileRevealed() {
		Field f = new Field(2, 1, new int[] {0,1}, new int[] {0,1});
		Tile[][] t = f.testTiles();
		t[0][1].reveal();
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//Multiple bombs and all tiles revealed
	@Test
	public void multBombAllTilesRevealed() {
		Field f = new Field(2, 1, new int[] {0,1}, new int[] {0,1});
		Tile[][] t = f.testTiles();
		t[0][1].reveal();
		t[1][0].reveal();
		f.help();
		assertTrue(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertTrue(t[1][1].getFlagged());
	}
	
	//Three bombs and one tile revealed
	@Test
	public void threeBombsAllTilesRevealed() {
		Field f = new Field(2, 1, new int[] {0,0,1}, new int[] {0,1,0});
		Tile[][] t = f.testTiles();
		t[1][1].reveal();
		f.help();
		assertTrue(t[0][0].getFlagged());
		assertTrue(t[0][1].getFlagged());
		assertTrue(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//All bombs and no tiles revealed
	@Test
	public void allBombsNoTilesRevealed() {
		Field f = new Field(2, 1, new int[] {0,0,1,1}, new int[] {0,1,0,1});
		Tile[][] t = f.testTiles();
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//All bombs and all tiles revealed
	@Test
	public void allBombsAllRevealed() {
		Field f = new Field(2, 1, new int[] {0,0,1,1}, new int[] {0,1,0,1});
		Tile[][] t = f.testTiles();
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				t[x][y].reveal();
			}
		}
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
	//Calling help if game has not started or has ended
	@Test
	public void helpWhileNotPlaying() {
		Field f = new Field(2, 1, new int[] {0,1}, new int[] {0,1});
		Tile[][] t = f.testTiles();
		t[0][1].reveal();
		t[1][0].reveal();
		f.changePlay();
		f.help();
		assertFalse(t[0][0].getFlagged());
		assertFalse(t[0][1].getFlagged());
		assertFalse(t[1][0].getFlagged());
		assertFalse(t[1][1].getFlagged());
	}
	
}
