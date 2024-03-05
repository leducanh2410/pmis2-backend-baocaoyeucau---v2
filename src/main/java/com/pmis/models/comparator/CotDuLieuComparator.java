package com.pmis.models.comparator;

import com.pmis.models.model.data_exploitation.CotDuLieu;

import java.util.Comparator;

public class CotDuLieuComparator implements Comparator<CotDuLieu> {
    @Override
    public int compare(CotDuLieu o1, CotDuLieu o2) {
        return o1.getSTT() - o2.getSTT();
    }
}
