/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import slang_dictionary.Slang;

/**
 *
 * @author thedao
 */
public class Main {
    public static void Menu(Slang newSlang) throws IOException {
        System.out.println("Menu");
        System.out.println("0. Exit");
        System.out.println("1. Search With Slang");
        System.out.println("2. Search With Definition");
        System.out.println("3. History Search With Slang");
        System.out.println("4. Add New Slang");
        System.out.println("5. Edit Slang Word");
        System.out.println("6. Delete Slang Word");
        System.out.println("7. Reset Slang List");
        System.out.println("8. Random Slang Word");
        System.out.println("9. Mini Game 1 (Give Slang Chose Correct Definition)");
        System.out.println("10. Mini Game 2 (Give Definition Chose Correct Slang)");
        
        
        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Input Option:");
            int option = Integer.parseInt(scan.nextLine());
            if (option != 0) {
                switch (option) {
                    case 1:
                        System.out.println("Input Key To Search:");
                        String key = scan.nextLine();
                        newSlang.searchWithSlang(key);
                        break;
                    case 2:
                        System.out.println("Input Definition To Search:");
                        String value = scan.nextLine();
                        newSlang.searchWithDefinition(value);
                        break;
                    case 3:
                        newSlang.historySearch();
                        break;
                    case 4:
                        System.out.println("Input New Key:");
                        String newKey = scan.nextLine();
                        System.out.println("Input New Definition:");
                        String newvValue = scan.nextLine();
                        newSlang.addSlang(newKey, newvValue);
                        break;
                    case 5:
                        newSlang.editSlang();
                        break;
                    case 6:
                        newSlang.deleteSlang();
                        break;
                    case 7:
                        newSlang.reset();
                        break;
                    case 8:
                        newSlang.randomSlang();
                        break;
                    case 9:
                        newSlang.miniGameSlang();
                        break;
                    case 10:
                        newSlang.miniGameDefinition();
                        break;
                    default:
                        break;
                }
            } else {
                break;
            }
        } while (true);
    }
    
    public static void main(String[] args) throws IOException {
        Slang newSlang = new Slang();
        Menu(newSlang);
    }
}
