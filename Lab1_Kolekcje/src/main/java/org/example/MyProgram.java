package org.example;

import java.util.ArrayList;
import java.util.List;

public class MyProgram {
    public static void main(String[] args) {
        Sort sort = Sort.NONE;
        if(args.length>0){
            if(args[0].equals("natural")) sort=Sort.NATURAL;
            else if(args[0].equals("alternative")) sort=Sort.ALTERNATIVE;
        }
        Mage.setSort(sort);

        List<Mage> mages = new ArrayList<>();

        Mage mage1 = new Mage("Mage" ,10,10.15);
        Mage mage2 = new Mage("Mage2" ,12,10.15);
        Mage mage3 = new Mage("AMage3" ,1,11.15);
        Mage mage4 = new Mage("BMage4" ,15,17.15);
        Mage mage5 = new Mage("Mage5" ,17,10.55);
        Mage mage6 = new Mage("DMage6" ,22,7.15);
        Mage mage7 = new Mage("Mage7" ,2,40.15);
        Mage mage8 = new Mage("ZXMage8" ,1,50.15);
        Mage mage9 = new Mage("AAMage9" ,11,50.15);
        Mage mage10 = new Mage("ZZMage10" ,6,40.15);
        Mage mage11 = new Mage("XMage11" ,15,17.15);
        Mage mage12 = new Mage("Mage12" ,10,11.15);
        Mage mage13 = new Mage("Mage13" ,10,11.15);
        Mage mage14 = new Mage("Mage14" ,10,11.15);
        Mage mage15 = new Mage("Mage15" ,10,11.15);
        mages.add(mage1);

        mage1.addApprentices(mage2);
        mage1.addApprentices(mage3);
        mage3.addApprentices(mage5);
        mage5.addApprentices(mage6);
        mage1.addApprentices(mage4);
        mage5.addApprentices(mage13);
        mage1.addApprentices(mage14);

        mages.add(mage7);
        mage7.addApprentices(mage8);
        mage7.addApprentices(mage9);
        mage9.addApprentices(mage10);
        mage9.addApprentices(mage11);
        mage10.addApprentices(mage12);
        mage7.addApprentices(mage15);

        for(Mage mage: mages){
            System.out.println(mage);
            mage.printRecursive(0);
        }




    }
}