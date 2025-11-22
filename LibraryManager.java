import java.io.*;
import java.util.*;

public class LibraryManager {

    private Map<Integer, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();

    private final String BOOK_FILE = "books.dat";
    private final String MEMBER_FILE = "members.dat";

    public LibraryManager() {
        loadFromFile();
    }

    // ---------------- Load data ----------------
    public void loadFromFile() {
        try {
            File f1 = new File(BOOK_FILE);
            File f2 = new File(MEMBER_FILE);

            if (f1.exists()) {
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(f1));
                books = (Map<Integer, Book>) ois.readObject();
                ois.close();
            }

            if (f2.exists()) {
                ObjectInputStream ois =
                        new ObjectInputStream(new FileInputStream(f2));
                members = (Map<Integer, Member>) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            System.out.println("No old data found.");
        }
    }

    // ---------------- Save data ----------------
    public void saveToFile() {
        try {
            ObjectOutputStream o1 =
                    new ObjectOutputStream(new FileOutputStream(BOOK_FILE));
            o1.writeObject(books);
            o1.close();

            ObjectOutputStream o2 =
                    new ObjectOutputStream(new FileOutputStream(MEMBER_FILE));
            o2.writeObject(members);
            o2.close();

        } catch (Exception e) {
            System.out.println("Error saving data!");
        }
    }

    // ---------------- Add Book ----------------
    public void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        Book b = new Book(id, title, author, category);
        books.put(id, b);

        System.out.println("Book Added Successfully!");
    }

    // ---------------- Add Member ----------------
    public void addMember(Scanner sc) {
        System.out.print("Enter Member ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        Member m = new Member(id, name, email);
        members.put(id, m);

        System.out.println("Member Added Successfully!");
    }

    // ---------------- Issue Book ----------------
    public void issueBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        System.out.print("Enter Member ID: ");
        int memberId = sc.nextInt();

        if (!books.containsKey(bookId)) {
            System.out.println("Book not found!");
            return;
        }
        if (!members.containsKey(memberId)) {
            System.out.println("Member not found!");
            return;
        }
        Book b = books.get(bookId);

        if (b.isIssued()) {
            System.out.println("Book already issued!");
            return;
        }

        b.markAsIssued();
        members.get(memberId).addIssuedBook(bookId);

        System.out.println("Book Issued Successfully!");
    }

    // ---------------- Return Book ----------------
    public void returnBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        if (!books.containsKey(id)) {
            System.out.println("Book not found!");
            return;
        }

        books.get(id).markAsReturned();

        for (Member m : members.values()) {
            m.returnIssuedBook(id);
        }

        System.out.println("Book Returned Successfully!");
    }

    // ---------------- Search Books ----------------
    public void searchBooks(Scanner sc) {
        System.out.print("Enter search term: ");
        sc.nextLine();
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.getTitle().toLowerCase().contains(key) ||
                b.getAuthor().toLowerCase().contains(key) ||
                b.getCategory().toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }

    // ---------------- Sort Books ----------------
    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");

        Scanner sc = new Scanner(System.in);
        int ch = sc.nextInt();

        if (ch == 1)
            Collections.sort(list);
        else if (ch == 2)
            list.sort(Comparator.comparing(Book::getAuthor));

        for (Book b : list)
            b.displayBookDetails();
    }
}
