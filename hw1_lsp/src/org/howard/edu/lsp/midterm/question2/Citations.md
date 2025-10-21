https://stackoverflow.com/questions/4592252/circle-implementation-how-to-model-in-order-to-fetch-this-information

AI Usage: Google Gemini

Me: Every time I run this Main.java file, the output for the exception doesn't seem to be catching it. Can you please figure out why it's not running the way it's supposed to.

Gemini: Hello! I'd be happy to help you debug this. It's common for small issues to creep in. Let's look at the output together. What results are you seeing? Many common issues include 
- 'javac' is not recognized...: This error means you don't have the Java Development Kit (JDK) installed or it's not configured in your system's PATH environment variable. You'll need to install the JDK and set up the PATH.

Error: Could not find or load main class...: This usually happens for one of two reasons:

You are not in the correct directory when running the java command. Make sure you are in the root folder that corresponds to your package structure.

You used slashes (/) instead of dots (.) in the class name (e.g., java org/howard/edu/lsp/midterm/question2/Main). You must use dots for the java command.

Me: When I changed the radius to 0.0, the code doesn't seem to catch any exception and runs without printing any exception message.

Gemini: Great catch! This suggests the area(double radius) method isn't throwing an exception when the radius is exactly 0. Let's look at the if condition in that method. What does it check for?

Me: Oh I realized, I accidentally typed < instead of <=. It's all good now.

