package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
    	// Method overloading allows a single method name to be used for different
        // but related tasks, which makes the code more intuitive and readable to programmers.
        // It provides an unified API for calculating the area rather than 
    	// forcing users to remember multiple method names.
    	
        // Calculate and print areas
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        System.out.println(); // Separation

        // Exception handling for invalid dimensions
        try {
            System.out.println("Attempting to calculate area with a negative dimension...");
            AreaCalculator.area(-2.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}
