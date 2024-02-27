package org.example;

import java.util.Comparator;

public class AlternativeComperator implements Comparator {
    @Override
    public int compare(Object o, Object o2) {
        if(o==null) return 0;
        else if(o2==null) return 0;
        if(o.getClass()== Mage.class && o2.getClass()==Mage.class){
            Mage mage1 = (Mage) o;
            Mage mage2 = (Mage) o2;
            if(mage1.getLevel()!=mage2.getLevel()) return mage1.getLevel()-mage2.getLevel();
            else if (!mage1.getName().equals(mage2.getName()))
                return CharSequence.compare(mage1.getName(),mage2.getName());
            else return Double.compare(mage1.getLevel(),mage2.getLevel());
        }
        return 0;
    }
}
