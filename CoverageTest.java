package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import static org.junit.Assert.*;

public class CoverageTest {

    @Test
    public void testCoverageGetHashes() {
        Set<String> hashes = new HashSet<String>();
        hashes.add("wsqqq");

        Coverage coverage = new Coverage(hashes, 1.5);

        assertEquals(hashes, coverage.getHashes());
    }

    @Test
    public void testCoverageToString() {
        Set<String> hashes = new HashSet<String>();
        hashes.add("wsqqq");

        Coverage coverage = new Coverage(hashes, 1.5);

        assertTrue(coverage.toString().contains("wsqqq"));
        assertTrue(coverage.toString().contains("1.5"));
    }

    @Test
    public void testGetRatio() {
        Set<String> hashes = new HashSet<String>();
        hashes.add("dr5ru");
        Coverage coverage = new Coverage(hashes, 1.5);
        assertEquals(1.5, coverage.getRatio(), 0.0001);
    }

    @Test
    public void testGetHashLengthEmpty() {
        Coverage coverage = new Coverage(Collections.<String>emptySet(), 1.0);
        assertEquals(0, coverage.getHashLength());
    }

    // MUT 4: Coverage.getHashLength()

    @Test
    public void testCoverageGetHashLengthEmptySet() {
        Coverage coverage = new Coverage(Collections.<String>emptySet(), 1.0);
        assertEquals(0, coverage.getHashLength());
    }

    @Test
    public void testCoverageGetHashLengthNonEmptySet() {
        Set<String> hashes = new HashSet<String>();
        hashes.add("wsqqq");

        Coverage coverage = new Coverage(hashes, 1.5);
        assertEquals(5, coverage.getHashLength());
    }

}