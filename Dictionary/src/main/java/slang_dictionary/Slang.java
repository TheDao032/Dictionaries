/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slang_dictionary;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thedao
 */
public class Slang {
    public HashMap<String, String> slang;
    public HashMap<String, String> backup;
    public ArrayList<String> slang_history;

    public Slang() {
        this.slang = new HashMap<>();
        this.backup = new HashMap<>();
        this.slang_history = new ArrayList<>();
        String path = "slang.txt";
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("`");
                this.slang.put(values[0], values[1]);
                this.backup.put(values[0], values[1]);
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void add(String key, String value) throws IOException {
        this.slang.put(key, value);
        String path = "slang.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        String item = key + '`' + value;
        this.slang.keySet().stream().filter(i -> (i.equals(key))).forEachOrdered((String i) -> {
            Scanner scan = new Scanner(System.in);
            System.out.println("There Aready Has This Key Word, Do You Want To Override It Or Create A New One: O OR C 'O: Override', 'C: Create A New One'");
            String Choice = scan.nextLine();
            if ("C".equals(Choice)) {
                byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8"));
                String result = key + generatedString;
                Slang.this.slang.put(result, value);
                try {
                    writer.write(item);
                } catch (IOException ex) {
                    Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Slang.this.slang.put(key, value);
                Slang.this.slang.entrySet().stream().map(m -> m.getKey() + '`' + m.getValue()).forEachOrdered(itemResult -> {
                    try {
                        writer.write(itemResult);
                    } catch (IOException ex) {
                        Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });
        
    }
    
    public void searchWithSlang(String key) {
        this.slang.keySet().stream().filter(i -> (i.contains(key))).map(i -> {
            System.out.println("key:" + i + "value:" + this.slang.get(i));
            return i;
        }).forEachOrdered(i -> {
            this.slang_history.add(i);
        });
    }
    
    public void searchWithDefinition(String value) {
        this.slang.entrySet().stream().filter(entry -> (entry.getValue().contains(value))).map(entry -> {
            System.out.println("value:" + value);
            return entry;
        }).forEachOrdered(entry -> {
            System.out.println(entry.getKey());
        });
    }
    
    public void historySearch() {
        this.slang_history.forEach(item -> {
            System.out.println(item);
        });
    }
    
    public void editSlang() {
        Slang.this.slang.entrySet().stream().map(m -> m).forEachOrdered(itemResult -> {
            System.out.println(itemResult.getKey() + ':' + itemResult.getValue());
        });
        
        Scanner scan = new Scanner(System.in);
        System.out.println("input key: ");
        String key = scan.nextLine();
        scan = new Scanner(System.in);
        System.out.println("input new definition for key: ");
        String value = scan.nextLine();
        this.slang.put(key, value);
    }
    
    public void deleteSlang() {
        Slang.this.slang.entrySet().stream().map(m -> m).forEachOrdered(itemResult -> {
            System.out.println(itemResult.getKey() + ':' + itemResult.getValue());
        });
        
        Scanner scan = new Scanner(System.in);
        System.out.println("input key: ");
        String key = scan.nextLine();
        int confirm;
        scan = new Scanner(System.in);
        System.out.println("confirm: input 1: yes, 0: no");
        confirm = Integer.parseInt(scan.nextLine());
        if (confirm == 1) {
            this.slang.remove(key);
        }
    }
    
    public void reset() {
        this.slang = this.backup;
    }
    
    public void randomSlang() {
        Random generator = new Random();
        Object[] keys = this.slang.keySet().toArray();
        Object randomKey = keys[generator.nextInt(keys.length)];
        System.out.println("key: " + randomKey + "value: " + this.slang.get(randomKey));
    }
}
