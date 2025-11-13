package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a set of integers stored internally as an {@link ArrayList}.
 * Ensures that no duplicate elements are stored and provides
 * standard set operations like union, intersection, and difference.
 */
public class IntegerSet {
    // Use an ArrayList to store the elements of the set.
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set, removing all elements.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements currently in the set.
     *
     * @return the int size of the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this IntegerSet to another object for equality.
     * Two IntegerSets are equal if they contain all of the same values,
     * regardless of order.
     *
     * @param o the object to compare with
     * @return true if the objects are equal (same elements), false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntegerSet otherSet = (IntegerSet) o;
        
        // If sizes are different, they are not equal.
        if (this.length() != otherSet.length()) {
            return false;
        }
        
        // Check if this set contains all elements of the other set.
        // Since sizes are equal and sets don't have duplicates,
        // a one-way 'containsAll' check is sufficient.
        return this.set.containsAll(otherSet.set);
    }

    /**
     * Checks if the set contains the specified value.
     *
     * @param value the integer value to check for
     * @return true if the value is in the set, false otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     *
     * @return the maximum integer value in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find largest element in an empty set.");
        }
        return Collections.max(set);
    }

    /**
     * Returns the smallest item in the set.
     *
     * @return the minimum integer value in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find smallest element in an empty set.");
        }
        return Collections.min(set);
    }

    /**
     * Adds an item to the set. If the item is already present,
     * the set remains unchanged.
     *
     * @param item the integer to add
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set. If the item is not present,
     * the set remains unchanged.
     *
     * @param item the integer to remove
     */
    public void remove(int item) {
        // Integer.valueOf(item) ensures Java calls
        // remove(Object) instead of remove(int index)
        set.remove(Integer.valueOf(item));
    }

    /**
     * Performs a set union operation. This method modifies the current set
     * to contain all unique elements present in either this set or the other set.
     *
     * @param other the IntegerSet to union with this set
     */
    public void union(IntegerSet other) {
        for (Integer item : other.set) {
            this.add(item); // Now 'add' method handles duplicate checking
        }
    }

    /**
     * Performs a set intersection operation. This method modifies the current set
     * to contain only the elements that are present in both this set and the other set.
     *
     * @param other the IntegerSet to intersect with this set
     */
    public void intersect(IntegerSet other) {
        // retainAll keeps only the elements contained in the other collection
        set.retainAll(other.set);
    }

    /**
     * Performs a set difference operation (this \ other). This method modifies
     * the current set to remove any elements that are also found in the other set.
     *
     * @param other the IntegerSet whose elements will be removed from this set
     */
    public void diff(IntegerSet other) {
        // removeAll removes all elements contained in the other collection
        set.removeAll(other.set);
    }

    /**
     * Performs a set complement operation (other \ this). This method modifies
     * the current set to become the set of elements that are in the other set
     * but not in this set's original state.
     *
     * @param other the IntegerSet to compare against
     */
    public void complement(IntegerSet other) {
        // Store the original elements of 'this' set
        List<Integer> originalThis = new ArrayList<>(this.set);
        
        // Clear 'this' set
        this.set.clear();
        
        // Add all elements from 'other' set to 'this' set
        this.set.addAll(other.set);
        
        // Remove the elements that were originally in 'this' set
        this.set.removeAll(originalThis);
    }

    /**
     * Checks if the set is empty.
     *
     * @return true if the set contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a string representation of the set in the format [elem1, elem2, etc].
     * The order of elements is based on the internal ArrayList.
     *
     * @return a string representation of the set
     */
    @Override
    public String toString() {
        return set.toString();
    }
}