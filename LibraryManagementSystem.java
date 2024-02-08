import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void allbooks() {
        System.out.println("HorrorBooks           [1]");
        System.out.println("science-FictionBooks  [2]");
        System.out.println("Adventure Books       [3]");
        System.out.println("Fantasy Books         [4]");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        String fileName = switch (choice) {
            case 1 -> "HorrorBooks.txt";
            case 2 -> "scienceFictionBooks.txt";
            case 3 -> "AdventureBooks.txt";
            case 4 -> "FantasyBooks.txt";
            default -> {
                System.out.println("You Entered Wrong Number");
                yield null;
            }
        };

        if (fileName != null) {
            try {
                BufferedReader read = new BufferedReader(new FileReader(fileName));
                String line;

                while ((line = read.readLine()) != null) {
                    System.out.println(line);
                }

                read.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
    }

    public static void advice() {
        try {
            BufferedReader allBooks = new BufferedReader(new FileReader("Books.txt"));
            String line;

            while ((line = allBooks.readLine()) != null) {
                System.out.println(line);
            }

            allBooks.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }

    public static void main(String[] args) {
        int numeral;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("**************************************************");
            System.out.println("**** please select the action you want to do! ****");
            System.out.println("**************************************************");
            System.out.println("Adding Books        [1]");
            System.out.println("Deleting a Book     [2]");
            System.out.println("New User            [3]");
            System.out.println("User Deletion       [4]");
            System.out.println("Book Rentals        [5]");
            System.out.println("Book Returns        [6]");
            System.out.println("Books Types         [7]");
            System.out.println("book Recommendation [8]");
            System.out.println("Exit                [9]");
            System.out.println();

            numeral = scanner.nextInt();

            switch (numeral) {
                case 1 -> BookAdd();
                case 2 -> BooksDelete();
                case 3 -> memberadd();
                case 4 -> memberDelete();
                case 5 -> rent();
                case 6 -> borrow();
                case 7 -> allbooks();
                case 8 -> advice();
                case 9 -> System.exit(0);
                default -> System.out.println("Please enter a number between 1-9");
            }
        }
    }

    public static void BookAdd() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine(); // Consume newline character

        System.out.print("Book Title: ");
        String bookName = scanner.nextLine();

        System.out.print("Author Name: ");
        String authorName = scanner.nextLine();

        System.out.print("Type: ");
        String types = scanner.nextLine();

        System.out.print("Year: ");
        int year = scanner.nextInt();

        scanner.nextLine(); // Consume newline character

        System.out.print("Isbn: ");
        String isbn = scanner.nextLine();

        System.out.println();

        Books newBook = new Books(bookName, authorName, types, year, isbn);
        System.out.println("Book added");
        System.out.println();
    }

    public static void BooksDelete() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ISBN number of the book you want to delete: ");
        scanner.nextLine(); // Consume newline character

        String isbn = scanner.nextLine();
        System.out.println();

        Books deleteBook = new Books();
        deleteBook.bookDelete(isbn);
        System.out.println();
    }

    public static void memberadd() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add member system:");
        System.out.print("New Member Name: ");
        String memberName = scanner.nextLine();

        System.out.print("New Member Surname: ");
        String memberSurname = scanner.nextLine();

        System.out.print("New Member ID: ");
        String memberId = scanner.nextLine();

        Member newMember = new Member(memberName, memberSurname, memberId);
        newMember.addSubscription(memberId);
        System.out.println();
    }

    public static void memberDelete() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the ID of the member you want to delete: ");
        scanner.nextLine(); // Consume newline character

        String memberId = scanner.nextLine();

        Member memberDelete = new Member("", "", "");
        memberDelete.membershipDelete(memberId);

        System.out.println();
    }

    public static void rent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter your student number: ");
        

        String memberId = scanner.nextLine();

        System.out.print("Please enter book ISBN: ");
        String isbn = scanner.nextLine();

        Rent rent = new Rent(memberId, isbn);
        rent.consistency();

        System.out.println();
    }

    public static void borrow() {

Scanner scanner = new Scanner(System.in);

        System.out.println("Book Return: ");
        System.out.print("Please enter the number of the returning student: ");
        String memberId = scanner.nextLine();

        System.out.print("Enter Book ISBN: ");
        String isbn = scanner.nextLine();

        Rent rebate = new Rent(memberId, isbn);
        rebate.rentalReturn();

        System.out.println();
    }
}
