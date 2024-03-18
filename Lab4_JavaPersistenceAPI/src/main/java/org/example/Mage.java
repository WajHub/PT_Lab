package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mage {
    @Id
    private String Name;
    private int level;

    @ManyToOne
    private Tower tower;

    @Override
    public String toString() {
        return "Mage{" +
                "Name='" + Name + '\'' +
                ", level=" + level +
                ", tower=" + (tower == null ? "null" : tower.getName()) +
                '}';
    }
}
