package org.example;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Mage implements Comparable{
    private static Sort sort = Sort.NONE;
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power) {
        this.name = name;
        this.level = level;
        this.power = power;
        if(sort.equals(Sort.NATURAL)) this.apprentices = new TreeSet<>();
        else if (sort.equals(Sort.ALTERNATIVE)) this.apprentices = new TreeSet<>(new AlternativeComperator());
        else  this.apprentices = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mage mage = (Mage) o;
        return level == mage.level && Double.compare(power, mage.power) == 0 && Objects.equals(name, mage.name) && Objects.equals(apprentices, mage.apprentices);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, level, power, apprentices);
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", power=" + power +
                '}';
    }

    public static void setSort(Sort sort) {
        Mage.sort = sort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public void setApprentices(Set<Mage> apprentices) {
        this.apprentices = apprentices;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getPower() {
        return power;
    }

    public Set<Mage> getApprentices() {
        return apprentices;
    }

    public void addApprentices(Mage mage){
        this.apprentices.add(mage);
    }

    @Override
    public int compareTo(Object o) {
        if(o==null) return 0;
        if(o.getClass()!=this.getClass()) return 0;
        Mage mage = (Mage) o;
        if(!this.getName().equals(mage.getName())) return CharSequence.compare(this.getName(), mage.getName());
        if(this.getLevel() != (mage.getLevel())) return this.getLevel() - (mage.getLevel());
        return Double.compare(this.getPower(),mage.getPower());
    }

    public void printRecursive(int depth){
        depth++;
        for(Mage apprentice: apprentices){
            for(int i=0;i<depth;i++) System.out.print('-');
            System.out.println(apprentice);
            apprentice.printRecursive(depth);
        }

    }
}
