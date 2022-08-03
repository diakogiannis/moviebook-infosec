package com.diakogiannis.uel.masters.moviebook.enums;

import org.junit.Assert;
import org.junit.Test;

public class SortByEnumTest {

    @Test
    public void valueOfLabel() {
        Assert.assertTrue(SortByEnum.HATES.equals(SortByEnum.valueOfLabel(2)));
    }
}