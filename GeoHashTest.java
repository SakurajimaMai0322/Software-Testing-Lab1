package com.github.davidmoten.geo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeoHashTest {

    @Test
    public void testEncodeTaipei101() {
        String hash = GeoHash.encodeHash(25.0336, 121.5648, 5);
        assertEquals("wsqqq", hash);
    }

    @Test
    public void testDecodeToLatLong() {
        LatLong res = GeoHash.decodeHash("wsqqq");
        assertTrue(res.getLat() > 25.0);
    }

    @Test
    public void testHeightWithLevel5() {
        double height = GeoHash.heightDegrees(5);
        assertTrue(height > 0);
    }

    @Test
    public void testWidthWithLevel5() {
        double width = GeoHash.widthDegrees(5);
        assertTrue(width > 0);
    }

    @Test
    public void testMoveRight() {
        assertEquals("wsqqx", GeoHash.right("wsqqq"));
    }

    @Test
    public void testMoveLeft() {
        assertEquals("wsqqw", GeoHash.left("wsqqq"));
    }

    @Test
    public void testMoveTop() {
        assertEquals("wsqqw", GeoHash.top("wsqqq"));
    }

    @Test
    public void testMoveBottom() {
        assertEquals("wsqqp", GeoHash.bottom("wsqqq"));
    }

    @Test
    public void testGetNeighboursForAndy() {
        java.util.List<String> neighbors = GeoHash.neighbours("wsqqq");
        assertEquals(8, neighbors.size());
        assertTrue(neighbors.contains(GeoHash.right("wsqqq")));
    }

    @Test
    public void testGridAsStringOutput() {
        String grid = GeoHash.gridAsString("wsqqq", 1, java.util.Collections.EMPTY_SET);
        assertNotNull(grid);
        assertTrue(grid.contains("wsqqq"));
    }

    @Test
    public void testDecodeNegativeBase32() {
        long result = Base32.decodeBase32("-29jw");
        assertEquals(-75324L, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCharIndexWithInvalidChar() {
        Base32.getCharIndex('i');
    }
}