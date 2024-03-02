Custom Vector Class Implementation

This folder hosts a custom implementation of a Vector class in C++, designed to mimic some functionalities of the standard template library (STL) vector but with added features and custom exceptions for educational purposes.
Features

    Dynamic Array Management: Automatically resizes to accommodate new elements, with initial capacity set and adjustable according to usage.
    Element Access: Provides access to elements via operator[], with bounds checking throwing a custom VectorIndexOutOfBoundsException.
    Memory Efficiency: Includes a shrink_to_fit method to reduce memory usage by fitting the capacity to the current size.
    Iterators: Implements custom iterators (Iterator, ConstIterator, IteratorX) to traverse the Vector elements, with safety checks against dereferencing null pointers or accessing out-of-bounds elements.
    Exception Handling: Introduces specific exceptions for common vector errors (e.g., VectorEmptyException, VectorIndexOutOfBoundsException), enhancing error reporting and handling.
    Element Insertion and Removal: Supports adding elements to the end (push_back) and removing the last element (pop_back), with checks for empty vectors.
    Utility Functions: Includes methods to check size (size), capacity (capacity), and emptiness (empty), along with a clear function (clear) to reset the Vector.

This Vector class serves as a foundational building block for more complex data structures and algorithms, demonstrating the principles of dynamic array management, iterator design, and exception handling in C++.