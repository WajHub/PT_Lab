package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tower {
    @Id
    private String name;

    private int height;

    @OneToMany
    private List<Mage> mages;

    @Override
    public String toString() {
        return "Tower{" +
                "name='" + name + '\'' +
                ", height=" + height +
                ", mages=" + mages +
                '}';
    }

    public void addMage(Mage mage){
        mages.add(mage);
    }

    public void deleteMage(Mage mage){
        mages.remove(mage);
    }
}
