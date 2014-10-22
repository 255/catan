package shared.model;

import shared.definitions.ResourceType;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class ResourceBank implements IResourceBank {
    public static final int NUM_RESOURCES = 5;

    private int m_wood;
    private int m_brick;
    private int m_sheep;
    private int m_wheat;
    private int m_ore;

    public ResourceBank() {
        setWood(0);
        setBrick(0);
        setSheep(0);
        setWheat(0);
        setOre(0);
    }

    public ResourceBank(int wood, int brick, int sheep, int wheat, int ore) {
        setWood(wood);
        setBrick(brick);
        setSheep(sheep);
        setWheat(wheat);
        setOre(ore);
    }

    @Override
    public int getWood() {
        return m_wood;
    }

    public void setWood(int wood) {
        this.m_wood = wood;
    }

    @Override
    public int getBrick() {
        return m_brick;
    }

    public void setBrick(int brick) {
        this.m_brick = brick;
    }

    @Override
    public int getSheep() {
        return m_sheep;
    }

    public void setSheep(int sheep) {
        this.m_sheep = sheep;
    }

    @Override
    public int getWheat() {
        return m_wheat;
    }

    public void setWheat(int wheat) {
        this.m_wheat = wheat;
    }

    @Override
    public int getOre() {
        return m_ore;
    }

    public void setOre(int ore) {
        this.m_ore = ore;
    }

    @Override
    public int getCount(ResourceType resourceType) {
        switch (resourceType) {
            case WOOD:  return m_wood;
            case BRICK: return m_brick;
            case SHEEP: return m_sheep;
            case WHEAT: return m_wheat;
            case ORE:   return m_ore;
        }
        return 0;
    }

    @Override
    public void setCount(ResourceType resourceType, int amount) {
        switch (resourceType) {
            case WOOD:  m_wood  = amount; break;
            case BRICK: m_brick = amount; break;
            case SHEEP: m_sheep = amount; break;
            case WHEAT: m_wheat = amount; break;
            case ORE:   m_ore   = amount; break;
        }
    }

    /**
     * Get the total amount of resources a player has.
     *
     * @return the amount of resources
     */
    @Override
    public int getCount() {
        return m_wood + m_brick + m_sheep + m_wheat + m_ore;
    }

    @Override
    public void add(IResourceBank resources) {
        setWood(m_wood + resources.getWood());
        setBrick(m_brick + resources.getBrick());
        setSheep(m_sheep + resources.getSheep());
        setWheat(m_wheat + resources.getWheat());
        setOre(m_ore + resources.getOre());
    }

    @Override
    public IResourceBank subtract(IResourceBank resources) {
        IResourceBank bundle = new ResourceBank(resources.getWood(), resources.getBrick(), resources.getSheep(),
                resources.getWheat(), resources.getOre());

        if (m_wood >= resources.getWood()) {
            setWood(m_wood - resources.getWood());
        } else {
            bundle.setWood(m_wood);
            setWood(0);
        }

        if (m_brick >= resources.getBrick()) {
            setBrick(m_brick - resources.getBrick());
        } else {
            bundle.setBrick(m_brick);
            setBrick(0);
        }

        if (m_sheep >= resources.getSheep()) {
            setSheep(m_sheep - resources.getSheep());
        } else {
            bundle.setSheep(m_sheep);
            setSheep(0);
        }

        if (m_wheat >= resources.getWheat()) {
            setWheat(m_wheat - resources.getWheat());
        } else {
            bundle.setWheat(m_wheat);
            setWheat(0);
        }

        if (m_ore >= resources.getOre()) {
            setOre(m_ore - resources.getOre());
        } else {
            bundle.setOre(m_ore);
            setOre(0);
        }
        return bundle;
    }

    /**
     * Add one of the specified resource to the resource bank.
     *
     * @param resource the type of resource to increment
     */
    @Override
    public void increment(ResourceType resource) {
        switch (resource) {
            case WOOD:  m_wood++; break;
            case BRICK: m_brick++; break;
            case SHEEP: m_sheep++; break;
            case WHEAT: m_wheat++; break;
            case ORE:   m_ore++; break;
        }
    }

    /**
     * Subtract one of the specified resource from the resource bank.
     *
     * @param resource the bundle of resources to remove from the bank
     */
    @Override
    public void decrement(ResourceType resource) {
        switch (resource) {
            case WOOD:  m_wood--; break;
            case BRICK: m_brick--; break;
            case SHEEP: m_sheep--; break;
            case WHEAT: m_wheat--; break;
            case ORE:   m_ore--; break;
        }
    }

    @Override
    public boolean canAfford(IResourceBank purchase) {
        if (m_wood >= purchase.getWood() && m_brick >= purchase.getBrick() && m_sheep >= purchase.getSheep()
                && m_wheat >= purchase.getWheat() && m_ore >= purchase.getOre()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IResourceBank negate() {
        return new ResourceBank(
                -m_wood,
                -m_brick,
                -m_sheep,
                -m_wheat,
                -m_ore
        );
    }
}
