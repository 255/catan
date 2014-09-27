package shared.model;

import shared.definitions.ResourceType;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class ResourceBundle implements IResourceBundle {
    private int m_wood;
    private int m_brick;
    private int m_sheep;
    private int m_wheat;
    private int m_ore;

    public ResourceBundle(int wood, int brick, int sheep, int wheat, int ore) {
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
}
