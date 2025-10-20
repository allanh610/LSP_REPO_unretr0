package org.howard.edu.lsp.midterm.question2;

/**
 * A class for calculating the area of different shapes.
 */
public class AreaCalculator {

    /**
     * Area of a circle
     *
     * @param radius | circle radius
     * @return the area of the circle
     * @throws IllegalArgumentException if the radius is less than or equal to 0
     */
    public static double area(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be a positive number.");
        }
        return Math.PI * radius * radius;
    }

    /**
     * Area of a rectangle
     *
     * @param width | rectangle width
     * @param height | rectangle height
     * @return the area of the rectangle
     * @throws IllegalArgumentException if width or height is less than or equal to 0
     */
    public static double area(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive numbers.");
        }
        return width * height;
    }

    /**
     * Area of a triangle
     *
     * @param base | triangle base
     * @param height | triangle height
     * @return the area of the triangle
     * @throws IllegalArgumentException if base or height is less than or equal to 0
     */
    public static double area(int base, int height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Base and height must be positive numbers.");
        }
        return 0.5 * base * height;
    }

    /**
     * Area of a square
     *
     * @param side | square length
     * @return the area of the square
     * @throws IllegalArgumentException if the side is less than or equal to 0
     */
    public static double area(int side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be a positive number.");
        }
        return (double) side * side;
    }
}
