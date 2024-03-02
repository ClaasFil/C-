Hospital Management System with Smart Pointers

This folder features a C++ implementation of a Hospital Management System, showcasing the use of smart pointers to manage dynamic memory safely and efficiently.
Core Highlights

    Smart Memory Management: Utilizes std::shared_ptr for shared ownership of Health Care Providers and std::weak_ptr for patients to prevent circular references and memory leaks.
    Dynamic Resource Allocation: Demonstrates how smart pointers can automatically manage the lifecycle of dynamically allocated objects, reducing the need for manual memory management.
    Safe Access to Resources: Implements checks to ensure that std::weak_ptr is locked before accessing, showcasing best practices for using weak pointers to access resources that might have been deleted.
    Exception Safety: The system employs smart pointers to enhance exception safety by ensuring proper resource cleanup in the face of exceptions, without the need for explicit delete calls.
    Resource Optimizations: Features techniques for optimizing resource usage, such as using std::make_shared for efficient memory allocation of shared objects.

This project serves as a practical example of how to leverage smart pointers in C++ to build robust and efficient systems, with a focus on real-world application in a hospital management context.