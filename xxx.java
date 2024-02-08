import java.io.*;
import java.util.Scanner;

class Books {
    private String name;
    private String author;
    private int year;
    protected String type;
    protected String isbn;

    public Books() {}

    public Books(String name, String author, String type, int year, String isbn) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
        this.type = type;

        try {
            FileWriter bookAdd = new FileWriter("Books.txt", true);
            bookAdd.write(name + "\t" + author + "\t" + type + "\t" + isbn + "\t" + year + "\t" + "\n");
            bookAdd.close();

            FileWriter types = new FileWriter(getTypeFileName(type), true);
            types.write(name + "\t" + isbn + "\n");
            types.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bookDelete(String isbn) {
        try {
        	
        	
            BufferedReader old = new BufferedReader(new FileReader("Books.txt"));
            BufferedWriter bookDelete = new BufferedWriter(new FileWriter("swap.txt", true));

            String line;
            int isbnControl = 0;
            int control = 0;

            while ((line = old.readLine()) != null) {
                if (control == 0) {
                    if (!line.contains(isbn)) {
                        bookDelete.write(line + "\n");
                    } else {
                        control++;
                        isbnControl++;
                    }
                } else {
                    bookDelete.write(line + "\n");
                }
            }

            if (isbnControl == 0) {
                System.out.println("There is no book which has this isbn");
            } else {
                System.out.println("Book has been deleted");
            }

            old.close();
            bookDelete.close();

            File oldFile = new File("Books.txt");
            File newFile = new File("swap.txt");
            oldFile.delete();
            newFile.renameTo(new File("Books.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTypeFileName(String type) {
        return switch (type) {
            case "horror" -> "HorrorBooks.txt";
            case "science-fiction" -> "scienceFictionBooks.txt";
            case "adventure" -> "AdventureBooks.txt";
            case "fantasy" -> "FantasyBooks.txt";
            default -> "MixBooks.txt";
        };
    }
}

class Member {
    private String name;
    private String surname;
    protected String id;

    public Member() {}

    public Member(String name, String surname, String id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public boolean membershipCheck(String id) {
        try {
            BufferedReader membershipCheck = new BufferedReader(new FileReader("Members.txt"));
            String line;

            while ((line = membershipCheck.readLine()) != null) {
                if (line.contains(id)) {
                    membershipCheck.close();
                    return true;
                }
            }

            membershipCheck.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addSubscription(String id) {
        if (!membershipCheck(id)) {
            try {
                FileWriter addMember = new FileWriter("Members.txt", true);
                addMember.write(name + "\t" + surname + "\t" + id + "\n");
                addMember.close();
                System.out.println("Student added");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There is a student with this id!");
        }
    }

    public void membershipDelete(String id) {
        if (membershipCheck(id)) {
            try {String line;
            
                BufferedReader old = new BufferedReader(new FileReader("Members.txt"));
                BufferedWriter memberDelete = new BufferedWriter(new FileWriter("Gecici.txt", true));

                

                BufferedReader oldLease = new BufferedReader(new FileReader("Rental.txt"));
                BufferedWriter newLease = new BufferedWriter(new FileWriter("geciciKira.txt", true));

                while ((line = oldLease.readLine()) != null) {
                    if (!line.contains(id)) {
                        newLease.write(line + "\n");
                    }
                }

                oldLease.close();
                newLease.close();

                File oldLeaseFile = new File("Rental.txt");
                File newLeaseFile = new File("geciciKira.txt");
                oldLeaseFile.delete();
                newLeaseFile.renameTo(new File("Rental.txt"));
                int counter = 0;
                
                while ((line = old.readLine()) != null) {
                	if(counter == 0) {
                		if (!line.contains(id)) {
                        memberDelete.write(line + "\n");
                    }else {
                    	counter++;
                    }
                	}else {
                		memberDelete.write(line + "\n");
                	}
                    
                }

                old.close();
                memberDelete.close();

                File oldFile = new File("Members.txt");
                File newFile = new File("Gecici.txt");
                oldFile.delete();
                newFile.renameTo(new File("Members.txt"));

                System.out.println("Member has been deleted");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There is no Member which has this id");
        }
    }
}

class Rent extends Member {
    private String isbn;
   // private string id;

    public Rent() {}

    public Rent(String id, String isbn) {
        super("", "", id);
        this.id = id;
        this.isbn = isbn;
    }

    public void consistency() {
        int mainFileControl = 0;
        int rentalFileControl = 0;

        if (membershipCheck(id)) {
            try {
                BufferedReader mainFile = new BufferedReader(new FileReader("Books.txt"));
                BufferedWriter forRent = new BufferedWriter(new FileWriter("Rental.txt", true));
                BufferedReader rentalControl = new BufferedReader(new FileReader("Rental.txt"));

                String line;

                while ((line = mainFile.readLine()) != null) {
                    if (line.contains(isbn))
                        mainFileControl++;
                }

                while ((line = rentalControl.readLine()) != null) {
                    if (line.contains(isbn))
                        rentalFileControl++;
                }

                mainFile.close();
                rentalControl.close();

                if (mainFileControl > rentalFileControl) {
                    System.out.println("Member can rent this book");
                    forRent.write(id + "\t" + isbn + "\n");

                } else if (mainFileControl == 0) {
                    System.out.println("No such book was found");
                } else {
                    System.out.println("All copies of this book are leased");
                }

                forRent.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No member found with this id");
        }
    }
    

    public void rentalReturn() {
        boolean rentalFound = false;
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader old = new BufferedReader(new FileReader("Rental.txt"));
             BufferedWriter forRent = new BufferedWriter(new FileWriter("GeciciRental.txt", true))) {

            String line = scanner.nextLine();

            while ((line = old.readLine()) != null) {
            	
            	String[] parts = line.split("\t");
            	String info1 = parts[0]; // İlk sütun
            	String info2 = parts[1]; // İkinci sütun
                String cleanedIsbn = isbn.trim();

                if (!rentalFound) {
                    if (!(info1.equals(id) && info2.equals(cleanedIsbn))) {
                        forRent.write(line + "\n");
                    } else {
                        rentalFound = true;
                        continue;
                    }
                } else {
                    forRent.write(line + "\n");
                }
            }
            old.close();
            forRent.close();
            File oldFile = new File("Rental.txt");
            File newFile = new File("GeciciRental.txt");
            oldFile.delete();
            newFile.renameTo(new File("Rental.txt"));
            if (rentalFound) {
                System.out.println("The book was returned");
            } else {
                System.out.println("No such rental was found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }
}

