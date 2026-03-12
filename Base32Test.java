package com.github.davidmoten.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Base32Test {

    @Test
    public void testEncodeWithBirthday() {
        String result = Base32.encodeBase32(20030322L, 5);
        assertNotNull(result);
        assertEquals(5, result.length());
    }

    @Test
    public void testPadWithPhoneNumber() {
        String phone = "0910101010";
        String result = Base32.padLeftWithZerosToLength(phone, 12);
        assertEquals("00" + phone, result);
    }

    @Test
    public void testGetCharIndexForAndy() {
        int index = Base32.getCharIndex('b');
        assertEquals(10, index);
    }
}