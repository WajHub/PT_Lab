package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        EntityTransaction entityTransaction = em.getTransaction();

        // ---- init ----
        entityTransaction.begin();
        em.persist(new Mage("Hubert", 10, null));
        em.persist(new Mage("Mage", 24, null));
        em.persist(new Mage("Mage2", 7, null));
        em.persist(new Tower("Tower", 500, new ArrayList<>()));
        em.persist(new Tower("Drzewo", 250,  new ArrayList<>()));
        em.persist(new Tower("Tower2", 345,  new ArrayList<>()));
        // ---- update ----
        Mage mage = (Mage) em.createQuery("SELECT m FROM Mage m WHERE m.Name=\"Hubert\"").getSingleResult();
        Tower tower = (Tower) em.createQuery("SELECT t FROM Tower t WHERE t.name=\"Tower\"").getSingleResult();
        mage.setTower(tower);
        tower.addMage(mage);
        entityTransaction.commit();


        Scanner scanner = new Scanner(System.in);
        String instruction = "";
        while(!instruction.equals("exit")){
            System.out.print("Give me instruction: ");
            instruction = scanner.nextLine();
            entityTransaction.begin();
            if(instruction.equals("add")){
                System.out.println("Mage or Tower");
                instruction = scanner.nextLine();
                if(instruction.equals("mage")){
                    String name = "";
                    String towerName = "";
                    int level = 0;
                    System.out.println("Name:");
                    name = scanner.nextLine();
                    System.out.println("Level:");
                    level = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Tower name:");
                    towerName = scanner.nextLine();
                    if(towerName.length()>0){
                        Tower tower1 = em.createQuery("Select t From Tower t WHERE t.name= :name", Tower.class)
                                .setParameter("name", towerName)
                                .getSingleResult();
                        Mage mage1 = new Mage(name, level, tower1);
                        tower1.addMage(mage1);
                        em.persist(mage1);
                    }
                    else{
                        Mage mage1 = new Mage(name, level, null);
                        em.persist(mage1);
                    }
                }
                else if(instruction.equals("tower")){
                    String name = "";
                    int height = 0;
                    System.out.println("Name:");
                    name = scanner.nextLine();
                    System.out.println("Height:");
                    height = scanner.nextInt();
                    scanner.nextLine();
                    Tower tower1 = new Tower(name, height, new ArrayList<>());
                    em.persist(tower1);
                }
            }
            else if(instruction.equals("update")){

            }
            else if(instruction.equals("remove")){
                System.out.println("Mage or Tower");
                instruction = scanner.nextLine();
                if(instruction.equals("mage")){
                    System.out.println("Name: ");
                    instruction = scanner.nextLine();
                    Mage mage1 = em.createQuery("Select m From Mage m WHERE m.Name= :name", Mage.class)
                            .setParameter("name",instruction)
                            .getSingleResult();
                    Tower tower1 = mage1.getTower();
                    tower1.deleteMage(mage1);
                    mage1.setTower(null);
                    em.remove(mage1);
                }
                else if(instruction.equals("tower")){
                    System.out.println("Name: ");
                    instruction = scanner.nextLine();
                    Tower tower1 = em.createQuery("Select t From Tower t WHERE t.name= :name", Tower.class)
                            .setParameter("name", instruction)
                            .getSingleResult();
                    for(Mage mage1: tower1.getMages()){
                        mage1.setTower(null);
                    }
                    em.remove(tower1);
                }

            }
            else if(instruction.equals("display")){
                List<Mage> mages = em.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
                System.out.println(mages);
                List<Tower> towers = em.createQuery("select t FROM Tower t", Tower.class).getResultList();
                System.out.println(towers);
            }
            else if(instruction.equals("display tower")){
                String name = "";
                int minLevel = 0;
                System.out.println("Name:");
                name = scanner.nextLine();
                System.out.println("Min Level:");
                minLevel = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Mages");
                List<Mage> mages = em.createQuery("SELECT m FROM Mage m WHERE m.tower.name =: name and m.level >: minLevel")
                        .setParameter("name", name)
                        .setParameter("minLevel", minLevel)
                        .getResultList();
                for(Mage mage1: mages){
                    System.out.println(mage1);
                }
            }
            else System.out.println("Wrong instruction!");
            entityTransaction.commit();
        }
        em.close();
        emf.close();
    }
}