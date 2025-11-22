# City-Library-Digital-Management-System
A Java-based City Library Management System that uses file handling and collections to store books and members, issue and return books, and support searching and sorting. Data is saved in text files, using Maps, Lists, Comparable, Comparator, and buffered I/O for persistence and efficiency.

### Java Programming – Assignment 4  

---

## 🚀 Project Overview
The **City Library Digital Management System** is a Java-based console application designed to manage:

- Book records  
- Member records  
- Issuing & returning of books  
- Searching & sorting using Collections  
- Persistent storage using File Handling  

It simulates a real library environment using **OOP, Collections Framework, File I/O, Comparable/Comparator**, and **Serialization**.

---

## 🧩 Features

### 📘 Book Management
- Add new books  
- Track book category, author, issue status  
- Issue & return books  
- Sort books (Title / Author)  
- Search books by multiple criteria  

### 👤 Member Management
- Add new library members  
- Track issued books per member  
- Auto-update records when a book is issued/returned  

### 💾 Data Persistence
All records are saved automatically using:
- `ObjectInputStream`
- `ObjectOutputStream`
- `.dat` binary files  
So data is preserved even after the program closes.

---

## 📂 Project Structure
CITY-LIBRARY-DIGITAL-MANAGEMENT-SYSTEM/
│── Book.java

│── Member.java

│── LibraryManager.java

│── Main.java

│── books.dat 

│── members.dat

---

## 🛠️ Technologies Used
- **Java 17+**  
- **Object-Oriented Programming (OOP)**  
- **Java Collections Framework (Map, List)**  
- **Comparable & Comparator**  
- **Serialization (File I/O)**  
- **Scanner for Input Handling**  

---

## 📌 How to Run the Project

### **1️⃣ Compile all files**
```bash
javac *.java

