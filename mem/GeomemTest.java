package com.github.davidmoten.geo.mem;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.junit.Test;

import static org.junit.Assert.*;

public class GeomemTest {

    private int count(Iterable<Info<String, String>> iterable) {
        int count = 0;
        for (Info<String, String> info : iterable) {
            count++;
        }
        return count;
    }

    private Info<String, String> first(Iterable<Info<String, String>> iterable) {
        for (Info<String, String> info : iterable) {
            return info;
        }
        return null;
    }

    @Test
    public void testFindReturnsAddedRecordInsideBoundingBox() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        geomem.add(25.0336, 121.5648, 1000L, "taipei101", "id1");

        Iterable<Info<String, String>> results =
                geomem.find(25.1, 121.5, 25.0, 121.6, 0L, 2000L);

        assertEquals(1, count(results));

        Info<String, String> info = first(results);
        assertNotNull(info);
        assertEquals(25.0336, info.lat(), 0.0001);
        assertEquals(121.5648, info.lon(), 0.0001);
        assertEquals(1000L, info.time());
        assertEquals("taipei101", info.value());
        assertTrue(info.id().isPresent());
        assertEquals("id1", info.id().get());
    }

    @Test
    public void testFindReturnsEmptyWhenNoDataExists() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Iterable<Info<String, String>> results =
                geomem.find(25.1, 121.5, 25.0, 121.6, 0L, 2000L);

        assertEquals(0, count(results));
    }

    @Test
    public void testFindUsesStartInclusiveAndFinishExclusive() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        geomem.add(25.0336, 121.5648, 1000L, "inside-start", "id1");
        geomem.add(25.0336, 121.5648, 2000L, "at-finish", "id2");

        Iterable<Info<String, String>> results =
                geomem.find(25.1, 121.5, 25.0, 121.6, 1000L, 2000L);

        assertEquals(1, count(results));

        Info<String, String> info = first(results);
        assertNotNull(info);
        assertEquals("inside-start", info.value());
    }

    @Test
    public void testAddWithoutExplicitIdUsesValueAsId() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        geomem.add(25.0336, 121.5648, 1000L, "value-as-id");

        Iterable<Info<String, String>> results =
                geomem.find(25.1, 121.5, 25.0, 121.6, 0L, 2000L);

        assertEquals(1, count(results));

        Info<String, String> info = first(results);
        assertNotNull(info);
        assertTrue(info.id().isPresent());
        assertEquals("value-as-id", info.id().get());
    }

    @Test
    public void testAddWithAbsentIdStillCanBeFoundByLocationAndTime() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Info<String, String> info = new Info<String, String>(
                25.0336,
                121.5648,
                1000L,
                "no-id-value",
                Optional.<String>absent()
        );

        geomem.add(info);

        Iterable<Info<String, String>> results =
                geomem.find(25.1, 121.5, 25.0, 121.6, 0L, 2000L);

        assertEquals(1, count(results));

        Info<String, String> result = first(results);
        assertNotNull(result);
        assertEquals("no-id-value", result.value());
        assertFalse(result.id().isPresent());
    }

    @Test
    public void testCreateRegionFilterReturnsTrueForInsidePoint() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                25.0336,
                121.5648,
                1000L,
                "inside",
                Optional.of("id1")
        );

        assertTrue(filter.apply(info));
    }

    @Test
    public void testCreateRegionFilterIncludesBottomAndRightEdges() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                25.0,
                121.6,
                1000L,
                "bottom-right-edge",
                Optional.of("id1")
        );

        assertTrue(filter.apply(info));
    }

    @Test
    public void testCreateRegionFilterExcludesTopEdge() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                25.1,
                121.5648,
                1000L,
                "top-edge",
                Optional.of("id1")
        );

        assertFalse(filter.apply(info));
    }

    @Test
    public void testCreateRegionFilterExcludesLeftEdge() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                25.0336,
                121.5,
                1000L,
                "left-edge",
                Optional.of("id1")
        );

        assertFalse(filter.apply(info));
    }

    @Test
    public void testCreateRegionFilterExcludesPointBelowBottom() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                24.9,
                121.5648,
                1000L,
                "below-bottom",
                Optional.of("id1")
        );

        assertFalse(filter.apply(info));
    }

    @Test
    public void testCreateRegionFilterExcludesPointRightOfBoundingBox() {
        Geomem<String, String> geomem = new Geomem<String, String>();

        Predicate<Info<String, String>> filter =
                geomem.createRegionFilter(25.1, 121.5, 25.0, 121.6);

        Info<String, String> info = new Info<String, String>(
                25.0336,
                121.7,
                1000L,
                "right-outside",
                Optional.of("id1")
        );

        assertFalse(filter.apply(info));
    }
}