package org.bank.menu;

import static org.bank.util.ApplicationContext.*;

public class Main {
    public void mainMenu() {
        print(Strings.mainMenu);
        while (true) {
            switch(scanner.next()) {
                case "1" -> login();
                case "2" -> signUp();
            }  
        }
    }

    private void signUp() {
    }

    private void login() {
        
    }

    public void print (Object o) {
        System.out.println(o);
    }
}
