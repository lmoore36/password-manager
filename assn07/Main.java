package assn07;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> passwordManager = new PasswordManager<>();

        String input;

        do {
            System.out.println("Enter Master Password");
            input = scanner.nextLine().trim();
        } while(!passwordManager.checkMasterPassword(input));

        while (true) {
            String command = scanner.nextLine().trim();

            if(command.compareTo("New password") == 0) {
                String website = scanner.nextLine().trim();
                String password = scanner.nextLine().trim();

                passwordManager.put(website,password);

                System.out.println("New password added");
            }

            else if(command.compareTo("Get password") == 0) {
                String website = scanner.nextLine().trim();


                if (passwordManager.get(website) == null) {
                    System.out.println("Account does not exist");
                }

                else{
                    System.out.println(passwordManager.get(website));
                }
            }

            else if(command.compareTo("Delete account") == 0) {
                String website = scanner.nextLine().trim();

                if (passwordManager.remove(website) == null) {
                    System.out.println("Account does not exist");
                }

                else{
                    passwordManager.remove(website);
                    System.out.println("Account deleted");
                }
            }

            else if(command.compareTo("Check duplicate password") == 0) {
                String website = scanner.nextLine().trim();
                ArrayList<String> duplicates = (ArrayList<String>) passwordManager.checkDuplicate(website);

                if (duplicates.isEmpty()) {
                    System.out.println("No account uses that password");
                }

                else {
                    System.out.println("Websites using that password:");
                    for (String matchingPassword : duplicates) {
                        System.out.println(matchingPassword);
                    }
                }
            }

            else if(command.compareTo("Get accounts") == 0) {
                System.out.println("Your accounts:");
                for (String websites : passwordManager.keySet()) {
                    System.out.println(websites);
                }
            }

            else if(command.compareTo("Generate random password") == 0) {
                int length = scanner.nextInt();

                System.out.println(passwordManager.generateRandomPassword(length));
            }

            else if(command.compareTo("Exit") == 0) {
                return;
            }

            else {
                System.out.println("Command not found");
            }
        }
    }
}
