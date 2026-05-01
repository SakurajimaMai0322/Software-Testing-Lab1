package com.github.davidmoten.geo.mem;

import junit.framework.TestCase;
import com.google.common.base.Optional;
import org.junit.Test;

import static org.junit.Assert.*;

public class InfoTest{

    @Test
    public void testInfoAccessorsWithPresentId() {
        Info<String, Integer> info = new Info<String, Integer>(
                25.0,
                121.0,
                1000L,
                "value",
                Optional.of(1)
        );

        assertEquals(25.0, info.lat(), 0.0001);
        assertEquals(121.0, info.lon(), 0.0001);
        assertEquals(1000L, info.time());
        assertEquals("value", info.value());
        assertTrue(info.id().isPresent());
        assertEquals(Integer.valueOf(1), info.id().get());
    }

    @Test
    public void testInfoAccessorsWithAbsentId() {
        Info<String, Integer> info = new Info<String, Integer>(
                25.0,
                121.0,
                1000L,
                "value",
                Optional.<Integer>absent()
        );

        assertFalse(info.id().isPresent());
    }

    @Test
    public void testInfoToString() {
        Info<String, Integer> info = new Info<String, Integer>(
                25.0,
                121.0,
                1000L,
                "value",
                Optional.of(1)
        );

        assertTrue(info.toString().contains("lat=25.0"));
        assertTrue(info.toString().contains("lon=121.0"));
        assertTrue(info.toString().contains("time=1000"));
        assertTrue(info.toString().contains("value=value"));
    }

}