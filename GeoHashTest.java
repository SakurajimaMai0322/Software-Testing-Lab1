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
        assertEquals("wsqqr", GeoHash.right("wsqqq"));
    }

    @Test
    public void testMoveLeft() {
        assertEquals("wsqqm", GeoHash.left("wsqqq"));
    }

    @Test
    public void testMoveTop() {
        assertEquals("wsqqw", GeoHash.top("wsqqq"));
    }

    @Test
    public void testMoveBottom() {
        assertEquals("wsqqn", GeoHash.bottom("wsqqq"));
    }

    @Test
    public void testGetNeighboursForAndy() {
        java.util.List<String> neighbors = GeoHash.neighbours("wsqqq");
        assertEquals(8, neighbors.size());
        assertTrue(neighbors.contains(GeoHash.right("wsqqq")));
    }

    @Test
    public void testGridAsStringOutput() {
        String grid = GeoHash.gridAsString("wsqqq", 1, java.util.Collections.<String>emptySet());
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

    @Test
    public void testAdjacentHashAtLongitudeBorder() {
        String hashAtEdge = GeoHash.encodeHash(0, 180, 5);
        String result = GeoHash.adjacentHash(hashAtEdge, Direction.RIGHT);
        assertNotNull(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeHashInvalidLatitude() {
        GeoHash.encodeHash(95.0, 121.0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeHashInvalidLength() {
        GeoHash.encodeHash(25.0, 121.0, 0);
    }

    @Test
    public void testHashContainsPoint() {
        String hash = "wsqqq";
        LatLong centre = GeoHash.decodeHash(hash);
        assertTrue(GeoHash.hashContains(hash, centre.getLat(), centre.getLon()));
    }

    @Test
    public void testCoverBoundingBox() {
        Coverage coverage = GeoHash.coverBoundingBox(25.1, 121.4, 25.0, 121.6);
        assertNotNull(coverage);
        assertTrue(coverage.getHashes().size() > 0);
    }

    @Test
    public void testStepsBetweenSame() {
        long steps = GeoHash.encodeHashToLong(25.0, 121.0, 5);
        assertEquals(5, (int)(steps & 0xf));
    }

    @Test
    public void testHashLengthToCoverBoundingBox() {
        int length = GeoHash.hashLengthToCoverBoundingBox(25.1, 121.4, 25.0, 121.6);
        assertTrue(length >= 0 && length <= 12);
    }

    @Test
    public void testLatLongGetLat() {
        LatLong ll = new LatLong(25.0, 121.0);
        assertEquals(25.0, ll.getLat(), 0.001);
    }

    // MUT 6: GeoHash.adjacentHash(String hash, Direction direction, int steps)

    @Test
    public void testAdjacentHashWithZeroSteps() {
        assertEquals("wsqqq", GeoHash.adjacentHash("wsqqq", Direction.RIGHT, 0));
    }

    @Test
    public void testAdjacentHashWithPositiveSteps() {
        assertEquals(GeoHash.right("wsqqq"), GeoHash.adjacentHash("wsqqq", Direction.RIGHT, 1));
    }

    @Test
    public void testAdjacentHashWithNegativeSteps() {
        assertEquals(GeoHash.left("wsqqq"), GeoHash.adjacentHash("wsqqq", Direction.RIGHT, -1));
    }
}