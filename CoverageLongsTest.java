package com.github.davidmoten.geo;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoverageLongsTest {

    @Test
    public void testCoverageLongsGetHashesOnlyReturnsCountElements() {
        CoverageLongs coverage = new CoverageLongs(new long[] {1L, 2L, 3L}, 2, 1.5);

        assertArrayEquals(new long[] {1L, 2L}, coverage.getHashes());
    }

    @Test
    public void testCoverageLongsGetRatio() {
        CoverageLongs coverage = new CoverageLongs(new long[] {5L}, 1, 1.5);

        assertEquals(1.5, coverage.getRatio(), 0.0001);
    }

    @Test
    public void testCoverageLongsGetCount() {
        CoverageLongs coverage = new CoverageLongs(new long[] {5L, 6L}, 2, 1.5);

        assertEquals(2, coverage.getCount());
    }

    @Test
    public void testCoverageLongsToString() {
        CoverageLongs coverage = new CoverageLongs(new long[] {5L}, 1, 1.5);

        assertTrue(coverage.toString().contains("Coverage"));
        assertTrue(coverage.toString().contains("ratio=1.5"));
    }

    @Test
    public void testCoverageLongsGetHashLengthWhenCountIsZero() {
        CoverageLongs coverage = new CoverageLongs(new long[] {5L, 6L}, 0, 1.0);
        assertEquals(0, coverage.getHashLength());
    }

    @Test
    public void testCoverageLongsGetHashLengthWhenCountIsNotZero() {
        CoverageLongs coverage = new CoverageLongs(new long[] {5L}, 1, 1.0);
        assertEquals(5, coverage.getHashLength());
    }
}