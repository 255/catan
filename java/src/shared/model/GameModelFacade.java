package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * handles all the manipulations of the game object
 */
public class GameModelFacade implements IGameModelFacade {
    private static GameModelFacade m_theFacade = null;

    /**
     * This is a private constructor that is called only when the GameModelFacade has not been initialized yet
     */
    private GameModelFacade() {}

    /**
     * This static function is called to get the current instance of the GameModelFacade
     *
     * @return the current instance of the GameModelFacade
     */
    public static GameModelFacade getInstance() {
        if (m_theFacade == null) {
            m_theFacade = new GameModelFacade();
        }
        return m_theFacade;
    }

    /**
     * Takes an edge location and determines if a road can be placed on it
     * This does NOT check if the player can afford the road.
     *
     * @param edge the location of the side of a terrain hex
     * @return a boolean value that reports if the user can place a road
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edge) {
        if (!Game.getInstance().isLocalPlayersTurn()) {
            return false;
        }

        // road placement rules are different for initial roads
        if (isFreeRound()) {
            return Game.getInstance().getMap().canPlaceInitialRoad(Game.getInstance().getLocalPlayer(), edge);
        }

        // if playing, check the map with normal rules
        if (Game.getInstance().getGameState() == GameState.PLAYING) {
            return Game.getInstance().getMap().canPlaceRoad(Game.getInstance().getLocalPlayer(), edge);
        }

        return false;
    }

    /**
     * Takes a vertex location and determines if a settlement can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a settlement
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertex) {
        assert vertex != null;

        // check if it's the player's turn
        if (!Game.getInstance().isLocalPlayersTurn()) {
            return false;
        }

        // check if map is open (same logic for initial round and normal playing
        if (!Game.getInstance().getMap().canPlaceSettlement(Game.getInstance().getLocalPlayer(), vertex)) {
            return false;
        }

        // check if a normal gameplay
        if (Game.getInstance().getGameState() == GameState.PLAYING) {
            return Game.getInstance().getLocalPlayer().canBuySettlement();
        }

        // assert that (if it's a free round) the player has enough pieces
        assert !isFreeRound() || Game.getInstance().getLocalPlayer().getPieceBank().availableSettlements() > 0;

        // if it's a free round, they can place
        return isFreeRound();
    }

    /**
     * Takes a vertex location and determines if a city can be placed on it
     *
     * @param vertex the location of a corner/intersection of terrain hexes
     * @return a boolean value that reports if the user can place a city
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertex) {
        assert vertex != null;

        if (Game.getInstance().getGameState() != GameState.PLAYING) {
            return false;
        }

        // is there a settlement at the vertex?
        boolean mapOpen = Game.getInstance().getMap().canPlaceCity(Game.getInstance().getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResourcesAndPiece = Game.getInstance().getLocalPlayer().canBuyCity();

        return mapOpen && (isFreeRound() || haveResourcesAndPiece);
    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public IPlayer getCurrentPlayer() {
        assert Game.getInstance().getCurrentPlayer() != null;
        return Game.getInstance().getCurrentPlayer();
    }

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     *
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    @Override
    public Collection<IPlayer> getRobbablePlayers(HexLocation location) {
        return Game.getInstance().getMap().getRobbablePlayersOnTile(location, Game.getInstance().getLocalPlayer());
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        assert Game.getInstance().getLocalPlayer().getResources() != null;

        return Game.getInstance().getLocalPlayer().getResources();
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Set<PortType> getPlayerPorts() {
        Set<PortType> ports = Game.getInstance().getMap().getPlayersPorts(Game.getInstance().getLocalPlayer());

        assert ports != null;

        return ports;
    }

    /**
     * Returns the chat history of the game
     *
     * @return the log initialized with all the chat messages
     */
    @Override
    public ILog getChatHistory() {
        ILog chat = Game.getInstance().getChatHistory();

        assert chat != null;

        return chat;
    }

    /**
     * Returns the move/action history of the game
     *
     * @return the log initialized with all the move/action messages
     */
    @Override
    public ILog getMoveHistory() {
        ILog gameplay = Game.getInstance().getGameplayLog();

        assert gameplay != null;

        return gameplay;
    }

    /**
     * Have enough money to buy a city and have place to put it and a piece to use.
     *
     * @return true if can buy city, false if not
     */
    @Override
    public boolean canBuyCity() {
        return Game.getInstance().localPlayerIsPlaying() && Game.getInstance().getLocalPlayer().canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return Game.getInstance().localPlayerIsPlaying() && Game.getInstance().getLocalPlayer().canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return Game.getInstance().localPlayerIsPlaying() && Game.getInstance().getLocalPlayer().canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     */
    @Override
    public boolean canBuyDevCard() {
        return Game.getInstance().localPlayerIsPlaying() && Game.getInstance().getLocalPlayer().canAfford(Prices.DEV_CARD)
                && Game.getInstance().getDevCards().getCount() > 0;
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade() {
        ITradeOffer tradeOffer = Game.getInstance().getTradeOffer();

        // is there a trade offer?
        if (tradeOffer == null) {
            return false;
        }

        // these shouldn't matter, but testing anyway
        if (Game.getInstance().getGameState() != GameState.PLAYING || !Game.getInstance().getCurrentPlayer().equals(tradeOffer.getSender())) {
            return false;
        }

        // check that the trade is for the player and that they can afford it
        return  Game.getInstance().getLocalPlayer().equals(tradeOffer.getReceiver())
                && Game.getInstance().getLocalPlayer().canAffordTrade(tradeOffer.getOffer());
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     */
    @Override
    public boolean canPlayDevCard() {
        return Game.getInstance().localPlayerIsPlaying() && Game.getInstance().getLocalPlayer().canPlayDevCard();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(ResourceType resource) {
        return Game.getInstance().localPlayerIsPlaying()
                && Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.MONOPOLY);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination) {
        return Game.getInstance().localPlayerIsPlaying()
                && Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.SOLDIER) // has and can play card
                && !robberDestination.equals(Game.getInstance().getMap().getRobber()); // moved robber
    }

    /**
     * Get whether the local player can play this specific dev card.
     * NOTE: The preconditions specified in the document seem wrong to me. You need BOTH resources to play the card?
     *       What if there is only one kind of card left.
     * @return true if the user can play this card
     * @param r1 resource
     * @param r2 resource
     */
    @Override
    public boolean canPlayYearOfPlenty(ResourceType r1, ResourceType r2) {
        IResourceBank bank = Game.getInstance().getResourceBank();
        return Game.getInstance().localPlayerIsPlaying()
                && Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.YEAR_OF_PLENTY)
                && bank.getCount(r1) > 0 && bank.getCount(r2) > 0;
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonument() {
        return Game.getInstance().localPlayerIsPlaying()
                && Game.getInstance().getLocalPlayer().canPlayDevCard(DevCardType.MONUMENT);
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     * @param edge1 first road
     * @param edge2 second road
     */
    @Override
    public boolean canPlayRoadBuilding(EdgeLocation edge1, EdgeLocation edge2) {
        assert edge1 != null && edge2 != null;

        ICatanMap map = Game.getInstance().getMap();
        IPlayer player = Game.getInstance().getLocalPlayer();

        return Game.getInstance().localPlayerIsPlaying()
                && player.canPlayDevCard(DevCardType.ROAD_BUILD)
                && map.canPlaceTwoRoads(player, edge1, edge2);
    }

    /**
     * Get the local player's color.
     *
     * @return the local player's color
     */
    @Override
    public CatanColor getLocalColor() {
        return Game.getInstance().getLocalPlayer().getColor();
    }

    @Override
    public boolean playerHasLongestRoad(IPlayer player) {
        return player.equals(Game.getInstance().getLongestRoad());
    }

    @Override
    public boolean playerHasLargestArmy(IPlayer player) {
        return player.equals(Game.getInstance().getLargestArmy());
    }

    @Override
    public boolean isPlayersTurn(IPlayer player) {
        return player.equals(Game.getInstance().getCurrentPlayer());
    }

    // this method is just for determining from the GameState if it is a free round
    @Override
    public boolean isFreeRound() {
        GameState gs = Game.getInstance().getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }
}
