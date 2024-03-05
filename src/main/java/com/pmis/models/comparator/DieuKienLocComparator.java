package com.pmis.models.comparator;

import com.pmis.models.model.data_exploitation.DieuKienLoc;

import java.util.Comparator;

public class DieuKienLocComparator implements Comparator<DieuKienLoc> {
    @Override
    public int compare(DieuKienLoc o1, DieuKienLoc o2) {
        return o1.getSTT() - o2.getSTT();
    }
}
