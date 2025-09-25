package org.howard.edu.lsp.assignment3;

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
 * Orchestrates the entire Extract, Transform, and Load (ETL) process.
 * Contains all necessary classes and logic to run the pipeline.
 * Uses static nested classes to encapsulate the logic for each ETL stage.
 */
public class OOETL {

    private static final String INPUT_FILE = "data" + File.separator + "products.csv";
    private static final String OUTPUT_FILE = "data" + File.separator + "transformed_products.csv";

    /**
     * The main entry point for the ETL application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // 1. Create objects for each stage of the pipeline
            CSVExtractor extractor = new CSVExtractor(INPUT_FILE);
            ProductTransformer transformer = new ProductTransformer();
            CSVLoader loader = new CSVLoader(OUTPUT_FILE);

            // 2. Execute the ETL process
            System.out.println("Starting ETL process from a single file...");

            List<Product> extractedProducts = extractor.extract();
            System.out.println("EXTRACT: Read " + extractedProducts.size() + " rows.");

            List<Product> transformedProducts = transformer.transform(extractedProducts);
            System.out.println("TRANSFORM: Transformed " + transformedProducts.size() + " rows.");

            String header = extractor.getHeader();
            loader.load(transformedProducts, header);
            System.out.println("LOAD: Wrote data to " + OUTPUT_FILE);

            System.out.println("\nETL Process Completed Successfully.");

        } catch (Exception e) {
            System.err.println("\nETL Process Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Extract" phase of the ETL pipeline.
     */
    static class CSVExtractor {
        private final String filePath;
        private String header;

        public CSVExtractor(String filePath) {
            this.filePath = filePath;
        }

        public List<Product> extract() throws IOException {
            List<Product> products = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                header = reader.readLine();
                if (header == null) {
                    throw new IOException("CSV file is empty or header is missing.");
                }
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] fields = line.split(",");
                    products.add(new Product(
                        Integer.parseInt(fields[0]),
                        fields[1],
                        new BigDecimal(fields[2]),
                        fields[3],
                        null
                    ));
                }
            }
            return products;
        }

        public String getHeader() {
            return this.header;
        }
    }

    /**
     * Handles the "Transform" phase of the ETL pipeline.
     */
    static class ProductTransformer {
        public List<Product> transform(List<Product> products) {
            return products.stream()
                .map(this::applyTransformations)
                .collect(Collectors.toList());
        }

        private Product applyTransformations(Product product) {
            Product currentProduct = product.withName(product.name().toUpperCase());
            String originalCategory = product.category();

            if (originalCategory.equalsIgnoreCase("Electronics")) {
                BigDecimal discountedPrice = currentProduct.price()
                    .multiply(new BigDecimal("0.90"))
                    .setScale(2, RoundingMode.HALF_UP);
                currentProduct = currentProduct.withPrice(discountedPrice);

                if (currentProduct.price().compareTo(new BigDecimal("500.00")) > 0) {
                    currentProduct = currentProduct.withCategory("Premium Electronics");
                }
            }
            return currentProduct.withPriceRange(calculatePriceRange(currentProduct.price()));
        }

        private String calculatePriceRange(BigDecimal price) {
            if (price.compareTo(new BigDecimal("10.00")) <= 0) return "Low";
            if (price.compareTo(new BigDecimal("100.00")) <= 0) return "Medium";
            if (price.compareTo(new BigDecimal("500.00")) <= 0) return "High";
            return "Premium";
        }
    }

    /**
     * Handles the "Load" phase of the ETL pipeline.
     */
    static class CSVLoader {
        private final String filePath;

        public CSVLoader(String filePath) {
            this.filePath = filePath;
        }

        public void load(List<Product> products, String header) throws IOException {
            File outputFile = new File(filePath);
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                writer.write(header + ",PriceRange");
                writer.newLine();
                for (Product p : products) {
                    writer.write(p.toCsvString());
                    writer.newLine();
                }
            }
        }
    }
}

/**
 * Represents a single, immutable product record.
 * This record encapsulates all data fields for a product, both original and transformed.
 *
 * @param productId  The unique identifier for the product.
 * @param name       The name of the product.
 * @param price      The price of the product.
 * @param category   The category of the product.
 * @param priceRange The calculated price range (e.g., "Low", "Medium").
 */
record Product(
    int productId,
    String name,
    BigDecimal price,
    String category,
    String priceRange
) {
    /**
     * Creates a new Product instance with a modified price.
     *
     * @param newPrice The new price to set.
     * @return A new Product object with the updated price.
     */
    public Product withPrice(BigDecimal newPrice) {
        return new Product(this.productId, this.name, newPrice, this.category, this.priceRange);
    }

    /**
     * Creates a new Product instance with a modified category.
     *
     * @param newCategory The new category to set.
     * @return A new Product object with the updated category.
     */
    public Product withCategory(String newCategory) {
        return new Product(this.productId, this.name, this.price, newCategory, this.priceRange);
    }

    /**
     * Creates a new Product instance with a modified name.
     *
     * @param newName The new name to set.
     * @return A new Product object with the updated name.
     */
    public Product withName(String newName) {
        return new Product(this.productId, newName, this.price, this.category, this.priceRange);
    }

    /**
     * Creates a new Product instance with the priceRange field populated.
     *
     * @param newPriceRange The calculated price range.
     * @return A new Product object with the price range.
     */
    public Product withPriceRange(String newPriceRange) {
        return new Product(this.productId, this.name, this.price, this.category, newPriceRange);
    }

    /**
     * Formats the Product record into a CSV-compatible string.
     *
     * @return A comma-separated string representing the product's data.
     */
    public String toCsvString() {
        return String.join(",",
            String.valueOf(productId),
            name,
            price.toPlainString(),
            category,
            priceRange
        );
    }
}
