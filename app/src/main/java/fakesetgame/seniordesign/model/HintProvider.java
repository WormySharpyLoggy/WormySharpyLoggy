package fakesetgame.seniordesign.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Chris on 10/22/2014.
 */
public class HintProvider {
    private final Game game;

    public HintProvider(Game game) {
        this.game = game;
    }

    private Set<Set<Tile>> hintedSets = new HashSet<Set<Tile>>();
    private List<Tile> hintTiles = null;
    private Set<Tile> providedHints = null;

    public boolean wasHintProvided(Set<Tile> tileSet) {
        return hintedSets.contains(tileSet);
    }

    public Set<Tile> getHint() {
        if (game.isGameOver())
            return null;

        // If the hint we're working on has already been found,
        // clear it from memory
        if (hintTiles != null && game.isFound(hintTiles)) {
            hintTiles = null;
            providedHints = null;
        }

        // If we're not working on a hint, pick a set of tiles
        // that hasn't been found
        if (hintTiles == null) {
            // need to find a new set to make hints for
            for (Set<Tile> set : game.board.getSets()) {
                if (!game.isFound(set)) {
                    hintedSets.add(set);
                    hintTiles = new ArrayList<Tile>(set);
                    providedHints = new HashSet<Tile>();
                    break;
                }
            }

            if (hintTiles == null)
                throw new RuntimeException("We haven't found all the sets, but still couldn't find a set to make a hint for. This shouldn't happen.");
        }

        if (providedHints.size() < hintTiles.size() - 1)
            providedHints.add(hintTiles.get(providedHints.size()));

        return providedHints;
    }
}
