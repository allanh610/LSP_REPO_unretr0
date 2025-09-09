package org.howard.edu.lsp.assignment2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a single product record, including the transformed fields.
 * Using a record for concise, immutable data representation.
 */
record Product(
    int productId,
    String name,
    BigDecimal price,
    String category,
    String priceRange // This field will be populated during transformation
) {
    /**
     * Creates a new Product instance with a modified price.
     * @param newPrice The new price to set.
     * @return A new Product object with the updated price.
     */
    public Product withPrice(BigDecimal newPrice) {
        return new Product(this.productId, this.name, newPrice, this.category, this.priceRange);
    }

    /**
     * Creates a new Product instance with a modified category.
     * @param newCategory The new category to set.
     * @return A new Product object with the updated category.
     */
    public Product withCategory(String newCategory) {
        return new Product(this.productId, this.name, this.price, newCategory, this.priceRange);
    }

    /**
     * Creates a new Product instance with a modified name.
     * @param newName The new name to set.
     * @return A new Product object with the updated name.
     */
    public Product withName(String newName) {
        return new Product(this.productId, newName, this.price, this.category, this.priceRange);
    }

    /**
     * Creates a new Product instance with the priceRange field populated.
     * @param newPriceRange The calculated price range.
     * @return A new Product object with the price range.
     */
    public Product withPriceRange(String newPriceRange) {
        return new Product(this.productId, this.name, this.price, this.category, newPriceRange);
    }

    /**
     * Formats the Product record into a CSV string for output.
     * @return A comma-separated string.
     */
    public String toCsvString() {
        return String.join(",",
            String.valueOf(productId),
            name,
            price.toPlainString(), // Use toPlainString to avoid scientific notation
            category,
            priceRange
        );
    }
}



public class ETLPipeline {

    private static final String INPUT_DIR = "data";
    private static final String OUTPUT_DIR = "data";
    private static final String INPUT_FILENAME = "products.csv";
    private static final String OUTPUT_FILENAME = "transformed_products.csv";

    public static void main(String[] args) {
        // Define relative paths for input and output files
        String inputFile = INPUT_DIR + File.separator + INPUT_FILENAME;
        String outputFile = OUTPUT_DIR + File.separator + OUTPUT_FILENAME;

        long rowsRead = 0;
        long rowsTransformed = 0;

        // --- EXTRACT ---
        List<Product> products = new ArrayList<>();
        String header;

        File file = new File(inputFile);
        if (!file.exists()) {
            System.err.println("Error: Input file not found at " + inputFile);
            System.exit(1);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            header = reader.readLine(); // Read header
            String line;
            while ((line = reader.readLine()) != null) {
                rowsRead++;
                String[] fields = line.split(",");
                Product p = new Product(
                    Integer.parseInt(fields[0]),
                    fields[1],
                    new BigDecimal(fields[2]),
                    fields[3],
                    null // PriceRange is initially null
                );
                products.add(p);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading or parsing input file: " + e.getMessage());
            System.exit(1);
            return; // For compiler, as exit terminates
        }
        
        // --- TRANSFORM ---
        List<Product> transformedProducts = products.stream()
            .map(ETLPipeline::transformProduct)
            .collect(Collectors.toList());
        rowsTransformed = transformedProducts.size();

        // --- LOAD ---
        try {
            // Ensure the output directory exists
            new File(OUTPUT_DIR).mkdirs(); 
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                // Write the new header with the added column
                writer.write(header + ",PriceRange");
                writer.newLine();
                
                for (Product p : transformedProducts) {
                    writer.write(p.toCsvString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
            System.exit(1);
        }

        // --- SUMMARY ---
        System.out.println("ETL Process Completed Successfully.");
        System.out.println("---------------------------------");
        System.out.println("Rows read: " + rowsRead);
        System.out.println("Rows transformed: " + rowsTransformed);
        System.out.println("Output file written to: " + outputFile);
    }

    /**
     * Applies all transformation logic to a single Product record.
     * The order of operations is critical.
     * @param product The original product record.
     * @return The fully transformed product record.
     */
    private static Product transformProduct(Product product) {
        Product currentProduct = product;
        String originalCategory = product.category();

        // 1. Convert product name to UPPERCASE
        currentProduct = currentProduct.withName(currentProduct.name().toUpperCase());

        // 2. Apply 10% discount for "Electronics"
        if (originalCategory.equalsIgnoreCase("Electronics")) {
            BigDecimal discountedPrice = currentProduct.price()
                .multiply(new BigDecimal("0.90"))
                .setScale(2, RoundingMode.HALF_UP);
            currentProduct = currentProduct.withPrice(discountedPrice);
        }

        // 3. Recategorize to "Premium Electronics" if conditions are met
        if (originalCategory.equalsIgnoreCase("Electronics") &&
            currentProduct.price().compareTo(new BigDecimal("500.00")) > 0) {
            currentProduct = currentProduct.withCategory("Premium Electronics");
        }

        // 4. Add the new PriceRange field based on the FINAL price
        String priceRange;
        BigDecimal finalPrice = currentProduct.price();
        if (finalPrice.compareTo(new BigDecimal("10.00")) <= 0) {
            priceRange = "Low";
        } else if (finalPrice.compareTo(new BigDecimal("100.00")) <= 0) {
            priceRange = "Medium";
        } else if (finalPrice.compareTo(new BigDecimal("500.00")) <= 0) {
            priceRange = "High";
        } else {
            priceRange = "Premium";
        }
        currentProduct = currentProduct.withPriceRange(priceRange);
        
        return currentProduct;
    }
}