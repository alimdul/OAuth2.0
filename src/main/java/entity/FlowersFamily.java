package entity;

import java.util.Objects;

public class FlowersFamily implements Entity {

    private int id;
    private String name;
    private String floweringTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloweringTime() {
        return floweringTime;
    }

    public void setFloweringTime(String floweringTime) {
        this.floweringTime = floweringTime;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;

        result = result * prime + id;
        result = result * prime + ((name == null) ? 0 : name.hashCode());
        result = result * prime + ((floweringTime == null) ? 0 : floweringTime.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FlowersFamily family = (FlowersFamily) obj;
        return Objects.equals(id, family.id) &&
                Objects.equals(name, family.name) &&
                Objects.equals(floweringTime, family.floweringTime) ;
    }

    @Override
    public String toString() {
        return "FlowersFamily{" +
                "id=" + id +
                ", name=" + name +
                ", floweringTime=" + floweringTime +
                '}';
    }
}
