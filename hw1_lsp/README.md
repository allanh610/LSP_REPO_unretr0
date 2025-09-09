A simple ETL pipeline designed to read product data from a CSV file, apply a series of transformations, and write the resulting data to a new CSV file. 

- data/ contains the input CSV file and is the output file destination.

- src/ contains Java source code.

The program makes the following assumptions about input data:
- The input file is named products.csv and is located in a data directory.
- Files are in CSV format.
- The first line of the CSV is a header row that'll be skipped during processing.
- Data fields themselves do not contain commas.
- Columns are in fixed order: ProductID, Name, Price, Category.
- Product ID can be parsed as an integer and Price as a decimal number.

Design Notes:
- No use of third-party libraries.
- java.math.BigDecimal used for all price calculations to prevent floating-point precision errors.
- Dataset is read into a List iin memory, transformed, and written out.

How To Run: 
1. Open your terminal/command prompt and cd into the directory to the root of the project folder
2. Run the javac command, pointing to the source file, to compile code and create .class files
3. Execute code using java command from the root directory

AI Usage:
- I asked Google Gemini about any libraries to use during this project and how to refactor my code after getting stuck trying to extract and transform the data. I didn't know about many Java libraries and Gemini showed me those import statements that are within the code. I however didn't believe that those could be used per the assignment requirements, so I asked. Snippets of the conversation are below:

"Is it possible to remove any of these import statements on lines 1-11 to make it run? I'm not sure if these statements are allowed within the code."

"No, it is not possible to write this specfic program as required without those import statements. They are fundamental to the program's operation for several reasons..." Gemini went into detail about the uses of all the import statements, including file handling, precise calculations, and data structures. Using this knowledge, I kept the import statements within the code.

External Source Usage:
Link: https://www.geeksforgeeks.org/java/import-statement-in-java/
Usage: To learn more about import statements within Java and which one is used for what 
