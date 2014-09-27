package shared.model;

import shared.definitions.ResourceType;

/**
 * Created by jeffreybacon on 9/25/14.
 */
public class ResourceBank implements IResourceBank{
    private int m_wood;
    private int m_brick;
    private int m_sheep;
    private int m_wheat;
    private int m_ore;

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
    public void add(IResourceBundle resources) {
        setWood(m_wood + resources.getWood());
        setBrick(m_brick + resources.getBrick());
        setSheep(m_sheep + resources.getSheep());
        setWheat(m_wheat + resources.getWheat());
        setOre(m_ore + resources.getOre());
    }

    @Override
    public IResourceBundle subtract(IResourceBundle resources) {
        if (m_wood >= resources.getWood()) {
            setWood(m_wood - resources.getWood());
        } else {
            resources.setWood(m_wood);
            setWood(0);
        }

        if (m_brick >= resources.getBrick()) {
            setBrick(m_brick - resources.getBrick());
        } else {
            resources.setBrick(m_brick);
            setBrick(0);
        }

        if (m_sheep >= resources.getSheep()) {
            setSheep(m_sheep - resources.getSheep());
        } else {
            resources.setSheep(m_sheep);
            setSheep(0);
        }

        if (m_wheat >= resources.getWheat()) {
            setWheat(m_wheat - resources.getWheat());
        } else {
            resources.setWheat(m_wheat);
            setWheat(0);
        }

        if (m_ore >= resources.getOre()) {
            setOre(m_ore - resources.getOre());
        } else {
            resources.setOre(m_ore);
            setOre(0);
        }
        return resources;
    }
}
