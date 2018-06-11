package entity;

import java.util.Objects;

public class Flower implements Entity {

    private int id;
    private String name;
    private String stem;
    private String leaf;

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

    public String getStem() {
        return stem;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;

        result = result * prime + id;
        result = result * prime + ((name == null) ? 0 : name.hashCode());
        result = result * prime + ((stem == null) ? 0 : stem.hashCode());
        result = result * prime + ((leaf == null) ? 0 : leaf.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Flower flower = (Flower) obj;
        return Objects.equals(id, flower.id) &&
                Objects.equals(name, flower.name) &&
                Objects.equals(stem, flower.stem) &&
                Objects.equals(leaf, flower.leaf) ;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", name=" + name +
                ", stem=" + stem +
                ", leaf=" + leaf +
                '}';
    }
}
