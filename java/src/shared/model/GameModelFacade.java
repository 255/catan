package shared.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.Collection;
import java.util.Set;

/**
 * handles all the manipulations of the game object
 */
public class GameModelFacade implements IGameModelFacade {
    private static GameModelFacade m_theFacade = null;

    // The one and only game pointer
    private IGame m_game;

    /**
     * This is a private constructor that is called only when the GameModelFacade has not been initialized yet
     */
    private GameModelFacade() {
        m_game = new Game();
    }

    /**
     * This static function is called to get the current instance of the GameModelFacade
     *
     * @return the current instance of the GameModelFacade
     */
    public static GameModelFacade instance() {
        if (m_theFacade == null) {
            m_theFacade = new GameModelFacade();
        }
        return m_theFacade;
    }

    @Override
    public IGame getGame() {
        assert m_game != null : "Attempted to get a null Game object!";
        return m_game;
    }

    @Override
    public void setGame(IGame game) {
        m_game = game;
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
        if (!GameModelFacade.instance().getGame().isLocalPlayersTurn()) {
            return false;
        }

        // road placement rules are different for initial roads
        if (isFreeRound()) {
            return GameModelFacade.instance().getGame().getMap().canPlaceInitialRoad(GameModelFacade.instance().getGame().getLocalPlayer(), edge);
        }

        // if playing, check the map with normal rules
        if (GameModelFacade.instance().getGame().getGameState() == GameState.PLAYING) {
            return GameModelFacade.instance().getGame().getMap().canPlaceRoad(GameModelFacade.instance().getGame().getLocalPlayer(), edge);
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
        if (!GameModelFacade.instance().getGame().isLocalPlayersTurn()) {
            return false;
        }

        // check if map is open (same logic for initial round and normal playing
        if (!GameModelFacade.instance().getGame().getMap().canPlaceSettlement(GameModelFacade.instance().getGame().getLocalPlayer(), vertex)) {
            return false;
        }

        // check if a normal gameplay
        if (GameModelFacade.instance().getGame().getGameState() == GameState.PLAYING) {
            return GameModelFacade.instance().getGame().getLocalPlayer().canBuySettlement();
        }

        // assert that (if it's a free round) the player has enough pieces
        assert !isFreeRound() || GameModelFacade.instance().getGame().getLocalPlayer().getPieceBank().availableSettlements() > 0;

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

        if (GameModelFacade.instance().getGame().getGameState() != GameState.PLAYING) {
            return false;
        }

        // is there a settlement at the vertex?
        boolean mapOpen = GameModelFacade.instance().getGame().getMap().canPlaceCity(GameModelFacade.instance().getGame().getLocalPlayer(), vertex);
        // does the player have enough resources?
        boolean haveResourcesAndPiece = GameModelFacade.instance().getGame().getLocalPlayer().canBuyCity();

        return mapOpen && (isFreeRound() || haveResourcesAndPiece);
    }

    /**
     * Returns the info for the current player
     *
     * @return the PlayerInfo object initialized with all the player card/piece counts
     */
    @Override
    public IPlayer getCurrentPlayer() {
        assert GameModelFacade.instance().getGame().getCurrentPlayer() != null;
        return GameModelFacade.instance().getGame().getCurrentPlayer();
    }

    /**
     * Get a list of players around a hex that can be robbed (all with towns except local player)
     *
     * @param location the location being robbed
     * @return a collection of players (may be empty)
     */
    @Override
    public Collection<IPlayer> getRobbablePlayers(HexLocation location) {
        return GameModelFacade.instance().getGame().getMap().getRobbablePlayersOnTile(location, GameModelFacade.instance().getGame().getLocalPlayer());
    }

    /**
     * Returns the info for the current player's resource counts
     *
     * @return the ResourceBank object containing the counts for the current player
     */
    @Override
    public IResourceBank getPlayerResources() {
        assert GameModelFacade.instance().getGame().getLocalPlayer().getResources() != null;

        return GameModelFacade.instance().getGame().getLocalPlayer().getResources();
    }

    /**
     * Returns the ports the current player has
     *
     * @return the ports that the current player has
     */
    @Override
    public Set<PortType> getPlayerPorts() {
        Set<PortType> ports = GameModelFacade.instance().getGame().getMap().getPlayersPorts(GameModelFacade.instance().getGame().getLocalPlayer());

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
        ILog chat = GameModelFacade.instance().getGame().getChatHistory();

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
        ILog gameplay = GameModelFacade.instance().getGame().getGameplayLog();

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
        return GameModelFacade.instance().getGame().localPlayerIsPlaying() && GameModelFacade.instance().getGame().getLocalPlayer().canBuyCity();
    }

    /**
     * Have enough money to buy a road and a piece to use.
     *
     * @return true if can buy road, false if not
     */
    @Override
    public boolean canBuyRoad() {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying() && GameModelFacade.instance().getGame().getLocalPlayer().canBuyRoad();
    }

    /**
     * Have enough money to buy a settlement and a piece to use.
     *
     * @return true if can buy a settlement, false if not
     */
    @Override
    public boolean canBuySettlement() {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying() && GameModelFacade.instance().getGame().getLocalPlayer().canBuySettlement();
    }

    /**
     * Have enough money to buy a dev card and a card is available to buy
     *
     * @return true if can buy
     */
    @Override
    public boolean canBuyDevCard() {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying() && GameModelFacade.instance().getGame().getLocalPlayer().canAfford(Prices.DEV_CARD)
                && GameModelFacade.instance().getGame().getDevCards().getCount() > 0;
    }

    /**
     * Return true if the player has enough resources for a trade currently being offered to them.
     *
     * @return true if can accept trade, false if not enough resources (or no trade is offered currently)
     */
    @Override
    public boolean canAcceptTrade() {
        ITradeOffer tradeOffer = GameModelFacade.instance().getGame().getTradeOffer();

        // is there a trade offer?
        if (tradeOffer == null) {
            return false;
        }

        // these shouldn't matter, but testing anyway
        if (GameModelFacade.instance().getGame().getGameState() != GameState.PLAYING || !GameModelFacade.instance().getGame().getCurrentPlayer().equals(tradeOffer.getSender())) {
            return false;
        }

        // check that the trade is for the player and that they can afford it
        return  GameModelFacade.instance().getGame().getLocalPlayer().equals(tradeOffer.getReceiver())
                && GameModelFacade.instance().getGame().getLocalPlayer().canAffordTrade(tradeOffer.getOffer());
    }

    /**
     * Whether the local player can play any dev cards.
     *
     * @return true if the user can play a dev card
     */
    @Override
    public boolean canPlayDevCard() {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying() && GameModelFacade.instance().getGame().getLocalPlayer().canPlayDevCard();
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonopoly(ResourceType resource) {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying()
                && GameModelFacade.instance().getGame().getLocalPlayer().canPlayDevCard(DevCardType.MONOPOLY);
    }

    /**
     * Get whether the local player can play this specific dev card.
     * @param robberDestination new location of the robber
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlaySoldier(HexLocation robberDestination) {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying()
                && GameModelFacade.instance().getGame().getLocalPlayer().canPlayDevCard(DevCardType.SOLDIER) // has and can play card
                && !robberDestination.equals(GameModelFacade.instance().getGame().getMap().getRobber()); // moved robber
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
        IResourceBank bank = GameModelFacade.instance().getGame().getResourceBank();
        return GameModelFacade.instance().getGame().localPlayerIsPlaying()
                && GameModelFacade.instance().getGame().getLocalPlayer().canPlayDevCard(DevCardType.YEAR_OF_PLENTY)
                && bank.getCount(r1) > 0 && bank.getCount(r2) > 0;
    }

    /**
     * Get whether the local player can play this specific dev card.
     *
     * @return true if the user can play this card
     */
    @Override
    public boolean canPlayMonument() {
        return GameModelFacade.instance().getGame().localPlayerIsPlaying()
                && GameModelFacade.instance().getGame().getLocalPlayer().canPlayDevCard(DevCardType.MONUMENT);
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

        ICatanMap map = GameModelFacade.instance().getGame().getMap();
        IPlayer player = GameModelFacade.instance().getGame().getLocalPlayer();

        return GameModelFacade.instance().getGame().localPlayerIsPlaying()
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
        return GameModelFacade.instance().getGame().getLocalPlayer().getColor();
    }

    @Override
    public boolean playerHasLongestRoad(IPlayer player) {
        return player.equals(GameModelFacade.instance().getGame().getLongestRoad());
    }

    @Override
    public boolean playerHasLargestArmy(IPlayer player) {
        return player.equals(GameModelFacade.instance().getGame().getLargestArmy());
    }

    @Override
    public boolean isPlayersTurn(IPlayer player) {
        return player.equals(GameModelFacade.instance().getGame().getCurrentPlayer());
    }

    /**
     * Create a new game object.
     */
    @Override
    public void newGame() {
        m_game = new Game();
    }

    // this method is just for determining from the GameState if it is a free round
    @Override
    public boolean isFreeRound() {
        GameState gs = GameModelFacade.instance().getGame().getGameState();
        return (gs == GameState.FIRST_ROUND || gs == GameState.SECOND_ROUND);
    }
}
