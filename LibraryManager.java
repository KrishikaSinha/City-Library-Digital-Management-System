import java.io.*;
import java.util.*;

public class LibraryManager {

    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();
    private Scanner sc = new Scanner(System.in);

    // ------------ File Handling -------------
    private final String BOOK_FILE = "books.txt";
    private final String MEMBER_FILE = "members.txt";

    public LibraryManager() {
        loadFromFile();
    }

    // Load saved records
    public void loadFromFile() {
        try {

            File bookFile = new File(BOOK_FILE);
            File memberFile = new File(MEMBER_FILE);

            if (bookFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(bookFile));
                books = (Map<Integer, Book>) ois.readObject();
                ois.close();
            }

            if (memberFile.exists()) {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(memberFile));
                members = (Map<Integer, Member>) ois.readObject();
                ois.close();
            }

        } catch (Exception e) {
            System.out.println("No previous data loaded.");
        }
    }

    // Save data before exit
    public void saveToFile() {
        try {
            ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(BOOK_FILE));
            oos1.writeObject(books);
            oos1.close();

            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(MEMBER_FILE));
            oos2.writeObject(members);
            oos2.close();

        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    // -------------- Menu Options -----------------

    public void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        books.put(id, new Book(id, title, author, category));
        System.out.println("Book added successfully.");
    }

    public void addMember() {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        members.put(id, new Member(id, name, email));
        System.out.println("Member added successfully.");
    }

    public void issueBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid book or member ID.");
            return;
        }
        if (book.isIssued()) {
            System.out.println("Book already issued.");
            return;
        }

        book.markAsIssued();
        member.addIssuedBook(bookId);
        System.out.println("Book issued successfully.");
    }

    public void returnBook() {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid book or member ID.");
            return;
        }

        book.markAsReturned();
        member.returnIssuedBook(bookId);
        System.out.println("Book returned successfully.");
    }

    public void searchBooks() {
        sc.nextLine();
        System.out.print("Enter search keyword: ");
        String key = sc.nextLine();

        books.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(key.toLowerCase())
                        || b.getAuthor().toLowerCase().contains(key.toLowerCase())
                        || b.getCategory().toLowerCase().contains(key.toLowerCase()))
                .forEach(Book::displayBookDetails);
    }

    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());
        Collections.sort(list);
        list.forEach(Book::displayBookDetails);
    }

    public void mainMenu() {

        while (true) {
            System.out.println("\n===== City Library Digital Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> sortBooks();
                case 7 -> {
                    saveToFile();
                    System.out.println("Data saved. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        new LibraryManager().mainMenu();
    }
}
