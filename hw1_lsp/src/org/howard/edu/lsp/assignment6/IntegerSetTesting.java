package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * JUnit 5 test class for IntegerSet class
 */
public class IntegerSetTesting {

    private IntegerSet setA;
    private IntegerSet setB;

    /**
     * Sets up two empty IntegerSet objects before each test
     */
    @BeforeEach
    void setUp() {
        setA = new IntegerSet();
        setB = new IntegerSet();
    }

    @Test
    @DisplayName("Test clear(): set should be empty after clear")
    void testClear() {
        setA.add(1);
        setA.add(2);
        assertFalse(setA.isEmpty(), "Set should not be empty before clear");
        setA.clear();
        assertTrue(setA.isEmpty(), "Set should be empty after clear");
        assertEquals(0, setA.length(), "Length should be 0 after clear");
    }

    @Test
    @DisplayName("Test length(): should return correct number of elements")
    void testLength() {
        assertEquals(0, setA.length(), "Empty set length should be 0");
        setA.add(10);
        assertEquals(1, setA.length(), "Set length should be 1");
        setA.add(20);
        assertEquals(2, setA.length(), "Set length should be 2");
    }

    @Test
    @DisplayName("Test length(): adding duplicates should not change length")
    void testLengthWithDuplicates() {
        setA.add(10);
        setA.add(10);
        setA.add(10);
        assertEquals(1, setA.length(), "Adding duplicates should not increase length");
    }

    @Test
    @DisplayName("Test equals(Object o): should be true for sets with same elements, regardless of order")
    void testEquals() {
        setA.add(1);
        setA.add(2);
        setB.add(2);
        setB.add(1);
        assertTrue(setA.equals(setB), "Sets with same elements in different order should be equal");
        assertTrue(setB.equals(setA), "Equality should be symmetric");
    }

    @Test
    @DisplayName("Test equals(Object o): should be true for identical sets and self")
    void testEqualsSelfAndIdentical() {
        setA.add(1);
        setA.add(2);
        setB.add(1);
        setB.add(2);
        assertTrue(setA.equals(setA), "Set should be equal to itself");
        assertTrue(setA.equals(setB), "Sets with same elements in same order should be equal");
    }

    @Test
    @DisplayName("Test equals(Object o): should be false for sets with different elements")
    void testNotEqualsDifferentElements() {
        setA.add(1);
        setA.add(2);
        setB.add(1);
        setB.add(3);
        assertFalse(setA.equals(setB), "Sets with different elements should not be equal");
    }

    @Test
    @DisplayName("Test equals(Object o): should be false for sets with different sizes")
    void testNotEqualsDifferentSizes() {
        setA.add(1);
        setA.add(2);
        setB.add(1);
        setB.add(2);
        setB.add(3);
        assertFalse(setA.equals(setB), "Sets with different sizes should not be equal");
    }
    
    @Test
    @DisplayName("Test equals(Object o): should be false for null or different types")
    void testNotEqualsNullOrDifferentType() {
        setA.add(1);
        assertFalse(setA.equals(null), "Set should not be equal to null");
        assertFalse(setA.equals("String"), "Set should not be equal to a String");
    }

    @Test
    @DisplayName("Test contains(int value): should return true for present elements, false otherwise")
    void testContains() {
        setA.add(10);
        setA.add(20);
        assertTrue(setA.contains(10), "Should contain 10");
        assertTrue(setA.contains(20), "Should contain 20");
        assertFalse(setA.contains(30), "Should not contain 30");
    }

    @Test
    @DisplayName("Test largest(): should return the maximum value in the set")
    void testLargest() {
        setA.add(10);
        setA.add(5);
        setA.add(20);
        assertEquals(20, setA.largest(), "Largest element should be 20");
    }

    @Test
    @DisplayName("Test largest(): should throw IllegalStateException if set is empty")
    void testLargestThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            setA.largest();
        });
        assertEquals("Cannot find largest element in an empty set.", exception.getMessage());
    }

    @Test
    @DisplayName("Test smallest(): should return the minimum value in the set")
    void testSmallest() {
        setA.add(10);
        setA.add(5);
        setA.add(20);
        assertEquals(5, setA.smallest(), "Smallest element should be 5");
    }

    @Test
    @DisplayName("Test smallest(): should throw IllegalStateException if set is empty")
    void testSmallestThrowsException() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            setA.smallest();
        });
        assertEquals("Cannot find smallest element in an empty set.", exception.getMessage());
    }

    @Test
    @DisplayName("Test add(int item): should add unique elements")
    void testAdd() {
        setA.add(5);
        assertTrue(setA.contains(5), "Set should contain 5 after adding it");
        assertEquals(1, setA.length(), "Length should be 1 after adding one element");
    }

    @Test
    @DisplayName("Test add(int item): should not add duplicate elements")
    void testAddDuplicate() {
        setA.add(5);
        setA.add(5);
        assertEquals(1, setA.length(), "Length should remain 1 after adding a duplicate");
    }

    @Test
    @DisplayName("Test remove(int item): should remove an existing element")
    void testRemove() {
        setA.add(10);
        setA.add(20);
        assertTrue(setA.contains(10), "Set should contain 10 before remove");
        setA.remove(10);
        assertFalse(setA.contains(10), "Set should not contain 10 after remove");
        assertEquals(1, setA.length(), "Length should be 1 after removing one element");
    }

    @Test
    @DisplayName("Test remove(int item): should do nothing if element is not present")
    void testRemoveNonExistent() {
        setA.add(10);
        setA.add(20);
        setA.remove(30);
        assertEquals(2, setA.length(), "Length should not change after removing non-existent element");
    }

    @Test
    @DisplayName("Test union(IntegerSet other): should contain all unique elements from both sets")
    void testUnion() {
        setA.add(1);
        setA.add(2);
        setB.add(2);
        setB.add(3);
        setA.union(setB); // Should become [1, 2, 3]

        assertEquals(3, setA.length(), "Union set length should be 3");
        assertTrue(setA.contains(1), "Union set should contain 1");
        assertTrue(setA.contains(2), "Union set should contain 2");
        assertTrue(setA.contains(3), "Union set should contain 3");
    }

    @Test
    @DisplayName("Test intersect(IntegerSet other): should contain only common elements")
    void testIntersect() {
        setA.add(1);
        setA.add(2);
        setA.add(3);
        setB.add(2);
        setB.add(3);
        setB.add(4);
        setA.intersect(setB); // Should become [2, 3]

        assertEquals(2, setA.length(), "Intersection set length should be 2");
        assertTrue(setA.contains(2), "Intersection set should contain 2");
        assertTrue(setA.contains(3), "Intersection set should contain 3");
        assertFalse(setA.contains(1), "Intersection set should not contain 1");
    }

    @Test
    @DisplayName("Test diff(IntegerSet other): should remove elements found in other set")
    void testDiff() {
        setA.add(1);
        setA.add(2);
        setA.add(3);
        setB.add(2);
        setB.add(3);
        setB.add(4);
        setA.diff(setB); // Should become [1]

        assertEquals(1, setA.length(), "Difference set length should be 1");
        assertTrue(setA.contains(1), "Difference set should contain 1");
        assertFalse(setA.contains(2), "Difference set should not contain 2");
    }
    
    @Test
    @DisplayName("Test complement(IntegerSet other): should become elements in other but not in this")
    void testComplement() {
        setA.add(1);
        setA.add(2);
        setA.add(3);
        setB.add(2);
        setB.add(3);
        setB.add(4);
        setA.complement(setB); // Should become [4] (B \ A)

        assertEquals(1, setA.length(), "Complement set length should be 1");
        assertTrue(setA.contains(4), "Complement set should contain 4");
        assertFalse(setA.contains(2), "Complement set should not contain 2");
    }
    
    @Test
    @DisplayName("Test complement(IntegerSet other): complement with self should be empty")
    void testComplementWithSelf() {
        setA.add(1);
        setA.add(2);
        setA.complement(setA); // Should become empty (A \ A)
        
        assertTrue(setA.isEmpty(), "Complement with self should result in an empty set");
    }

    @Test
    @DisplayName("Test isEmpty(): should be true for new set, false after adding")
    void testIsEmpty() {
        assertTrue(setA.isEmpty(), "New set should be empty");
        setA.add(1);
        assertFalse(setA.isEmpty(), "Set should not be empty after adding an element");
    }

    @Test
    @DisplayName("Test toString(): should return correct string format")
    void testToString() {
        assertEquals("[]", setA.toString(), "Empty set string should be '[]'");
        setA.add(1);
        assertEquals("[1]", setA.toString(), "Set with one element");
        setA.add(2);
        assertEquals("[1, 2]", setA.toString(), "Set with two elements");
    }
}