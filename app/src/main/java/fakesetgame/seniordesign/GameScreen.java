package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Tile;
import fakesetgame.seniordesign.model.TileSet;
import fakesetgame.seniordesign.util.SystemUiHider;
import fakesetgame.seniordesign.view.ShadedImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity implements View.OnClickListener {

    private static final String TAG = "GameScreen";
    private static final int TILES = 9;
    private static final int SETS = 6;
    private static final int TILES_IN_A_SET = 3;

    private ShadedImageView[] tiles = new ShadedImageView[TILES];
    private ImageView[][] found = new ImageView[SETS][TILES_IN_A_SET];
    private Board board = null;
    private Set<Tile> selectedTiles = new HashSet<Tile>();

    // Won't need this after Game class is done
    private Set<Set<Tile>> foundSets = new HashSet<Set<Tile>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        // Init board imageviews
        for (int i = 0; i < TILES; i++) {
            tiles[i] = (ShadedImageView) findViewById(getResources().getIdentifier(
                    "Tile" + Integer.valueOf(i + 1).toString(),
                    "id",
                    getPackageName()));
            tiles[i].setTag(R.id.TILE_INDEX, i);
            tiles[i].setOnClickListener(this);

            if (tiles[i] == null)
                Log.e(TAG, String.format("Failed to set tiles[%d]. Value is null.", i));
        }

        // Init found set imageviews
        for (int set = 0; set < found.length; set++) {
            for (int tile = 0; tile < found[set].length; tile++) {
                found[set][tile] = (ImageView) findViewById(getResources().getIdentifier(
                        "FoundSet" + Integer.valueOf(set + 1).toString() + "_" + Integer.valueOf(tile + 1).toString(),
                        "id",
                        getPackageName()));

                if (found[set][tile] == null)
                    Log.e(TAG, String.format("Failed to set found[%d][%d]. Value is null.", set, tile));
            }
        }

        NewGame();
    }

    private void setTileSelected(int tileIndex, boolean selected){
        if(tileIndex < 0 || tileIndex > TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        if(getTileSelected(tileIndex) == selected)
            return;

        tiles[tileIndex].setShaded(selected);
        Log.d(TAG, String.format("Setting tile %d shading = %s", tileIndex, Boolean.valueOf(selected).toString()));

        if(selected){
            selectedTiles.add(board.getTile(tileIndex));

            // this should call Game class, but it isn't finished yet
            if(selectedTiles.size()==3){
                Tile[] tiles = selectedTiles.toArray(new Tile[3]);
                attemptSet(tiles[0], tiles[1], tiles[2]);
                clearTileSelection();
            }
        }
        else{
            selectedTiles.remove(board.getTile(tileIndex));
        }
    }

    private boolean getTileSelected(int tileIndex){
        if(tileIndex < 0 || tileIndex > TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        return tiles[tileIndex].getShaded();
    }

    private void clearTileSelection(){
        selectedTiles.clear();
        for(int i=0; i<TILES; i++)
            setTileSelected(i, false);
    }

    private boolean attemptSet(Tile tile1, Tile tile2, Tile tile3){
        if(TileSet.isValidSet(tile1, tile2, tile3)){
            if(foundSets.add(new HashSet<Tile>(Arrays.asList(tile1, tile2, tile3)))) {
                int set = foundSets.size() - 1;

                found[set][0].setImageDrawable(tile1.getDrawable(this));
                found[set][1].setImageDrawable(tile2.getDrawable(this));
                found[set][2].setImageDrawable(tile3.getDrawable(this));

                return true;
            }
        }
        return false;
    }

    public void onClick(View view){
        Object o = view.getTag(R.id.TILE_INDEX);
        if(view instanceof ImageView && o instanceof Integer){
            if(foundSets.size() == SETS)
                return;

            int idx = (Integer)o;
            Log.d(TAG, "Event: onClick for Tile " + idx);

            setTileSelected(idx, !getTileSelected(idx));
        }
    }

    private void NewGame() {
        board = Board.generateRandom(SETS);
        clearTileSelection();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setImageDrawable(board.getTile(i).getDrawable(this));
            Log.d(TAG, String.format("Set tile image for tiles[%d]", i));
        }

        foundSets.clear();
        for(int set = 0; set < SETS; set++)
            for(int tile = 0; tile < TILES_IN_A_SET; tile++)
                found[set][tile].setImageDrawable(getResources().getDrawable(R.drawable.tile_blank));
    }
}
