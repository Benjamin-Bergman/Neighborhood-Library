# Neighborhood Library Tracking System
A system for managing a local library.
It runs a TUI (Terminal User Interface) using [Lanterna](https://github.com/mabe02/lanterna).
It supports keyboard navigation.

# Screenshots
Home screen:

![image](https://github.com/Benjamin-Bergman/Neighborhood-Library/assets/166551442/715fcf07-54e6-4982-a225-47d9f7735d3f)

Checking out books:

![image](https://github.com/Benjamin-Bergman/Neighborhood-Library/assets/166551442/ae30e879-9daa-4373-83b2-1413ca58f74d)

Returning books:

![image](https://github.com/Benjamin-Bergman/Neighborhood-Library/assets/166551442/f039b99c-1c34-4f8c-ad8c-c5fc46af8205)

# Building

Build the project with `mvn package` or use your favorite IDE.

# Running

The program will not run from cmd on Windows, this is a [known bug in Lanterna](https://github.com/mabe02/lanterna/issues/593).
Here are a few workarounds:
* Run the program directly from your IDE
* Use `javaw.exe` instead of `java.exe`
* Double-click the `.jar` file from file explorer
* Run via Git Bash
* Run via WSL

The first four workarounds will cause Lanterna to create a terminal emulator window.
