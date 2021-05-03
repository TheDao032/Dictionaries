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
    public HashMap<Integer, String> bindkey;
    public ArrayList<String> slang_history;

    public Slang() {
        this.slang = new HashMap<>();
        this.backup = new HashMap<>();
        this.bindkey = new HashMap<>();
        this.slang_history = new ArrayList<>();
        String path = "slang.txt";
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            int i = 1;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("`");
                if (values.length < 2) {
                    continue;
                }
                this.slang.put(values[0], values[1]);
                this.backup.put(values[0], values[1]);
                this.bindkey.put(i, values[0]);
                i++;
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void addSlang(String key, String value) throws IOException {
        String path = "slang.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        this.slang.keySet().stream().filter(i -> (i.equals(key))).forEachOrdered((String i) -> {
            Scanner scan = new Scanner(System.in);
            System.out.println("There Aready Has This Key Word, Do You Want To Override It Or Create A New One: O OR C 'O: Override', 'C: Create A New One'");
            String Choice = scan.nextLine();
            if ("C".equals(Choice) || "c".equals(Choice)) {
                byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("UTF-8"));
                String result = key + generatedString;
                Slang.this.slang.put(result, value);
                try {
                    Slang.this.slang.entrySet().stream().map(m -> m.getKey() + '`' + m.getValue() + '\n').forEachOrdered(itemResult -> {
                        try {
                            writer.write(itemResult);
                        } catch (IOException ex) {
                            Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Slang.this.slang.put(key, value);
                Slang.this.slang.entrySet().stream().map(m -> m.getKey() + '`' + m.getValue() + '\n').forEachOrdered(itemResult -> {
                    try {
                        writer.write(itemResult);
                    } catch (IOException ex) {
                        Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return;
        });
        this.slang.put(key, value);
        Slang.this.slang.entrySet().stream().map(m -> m.getKey() + '`' + m.getValue() + '\n').forEachOrdered(itemResult -> {
            try {
                writer.write(itemResult);
            } catch (IOException ex) {
                Logger.getLogger(Slang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        writer.close();
    }
    
    public void searchWithSlang(String key) {
        this.slang.keySet().stream().filter(i -> (i.contains(key))).map(i -> {
            System.out.println("key:" + i + ", value:" + this.slang.get(i));
            return i;
        }).forEachOrdered(i -> {
            this.slang_history.add(i);
        });
    }
    
    public void searchWithDefinition(String value) {
        System.out.println("value:" + value);
        this.slang.entrySet().stream().filter(entry -> (entry.getValue().contains(value))).map(entry -> {
            return entry;
        }).forEachOrdered(entry -> {
            System.out.println("key:" + entry.getKey());
        });
    }
    
    public void historySearch() {
        this.slang_history.forEach(item -> {
            System.out.println(item);
        });
    }
    
    public void editSlang() {
        Slang.this.bindkey.entrySet().stream().map(m -> m).forEachOrdered(itemResult -> {
            int number = itemResult.getKey();
            String key = itemResult.getValue();
            System.out.println("Number: " + Integer.toString(number) + ", Key: '" + key + "'");
        });
        
        Scanner scan = new Scanner(System.in);
        System.out.println("input Number Of Key You Want To Edit: ");
        int number = Integer.parseInt(scan.nextLine());
        scan = new Scanner(System.in);
        System.out.println("input new definition for key: ");
        String value = scan.nextLine();
        this.slang.put(this.bindkey.get(number), value);
    }
    
    public void deleteSlang() {
        Slang.this.bindkey.entrySet().stream().map(m -> m).forEachOrdered(itemResult -> {
            int number = itemResult.getKey();
            String key = itemResult.getValue();
            System.out.println("Number: " + Integer.toString(number) + ", Key: '" + key + "'");
        });
        
        Scanner scan = new Scanner(System.in);
        System.out.println("input Number Of Key You Want To Delete: ");
        int number = Integer.parseInt(scan.nextLine());
        int confirm;
        scan = new Scanner(System.in);
        System.out.println("confirm: input 1: yes, 0: no");
        confirm = Integer.parseInt(scan.nextLine());
        if (confirm == 1) {
            this.slang.remove(this.bindkey.get(number));
        }
    }
    
    public void reset() {
        this.slang = this.backup;
    }
    
    public void randomSlang() {
        Random generator = new Random();
        Object[] keys = this.slang.keySet().toArray();
        Object randomKey = keys[generator.nextInt(keys.length)];
        System.out.println("key: " + randomKey + ", value: " + this.slang.get(randomKey.toString()));
    }
    
    public void miniGameSlang() {
        
        Random generator = new Random();
        int[] answer = {1, 2, 3, 4};
        Object[] keys = this.slang.keySet().toArray();
        do {
            HashMap<Integer, String> QA = new HashMap<>();
            for (int i = 1; i <= 4; i++) {
                int randomAnswer;
                Object randomKeys;
                do {
                    randomAnswer = answer[generator.nextInt(answer.length)];
                    if (QA.containsKey(randomAnswer)) {
                        continue;
                    }
                    break;
                } while (true);
                do {
                    randomKeys = keys[generator.nextInt(keys.length)];
                    if (QA.containsValue(randomKeys.toString())) {
                        continue;
                    }
                    break;
                } while (true);
                QA.put(randomAnswer, randomKeys.toString());
            }

            int answerNumber = answer[generator.nextInt(answer.length)];

            String answerValue = this.slang.get(QA.get(answerNumber));

            System.out.println("Definition: " + answerValue);
            for (int i = 1; i <= 4; i++) {
                System.out.println("Number: " + Integer.toString(i) + ", Key: " + QA.get(i));
            }
            Scanner scan = new Scanner(System.in);
            System.out.println("Chose Number Of Key For Definition: ");
            int result = Integer.parseInt(scan.nextLine());

            if (result == answerNumber) {
                System.out.println("Congratulation, Your Choice Is Correct!!!");
            } else {
                System.out.println("Opps, Your Choice Is Incorrect");
            }
            
            System.out.println("Do You Still Want To Play Mini Game, input 'Y' OR 'y': to keep playing, Other To Quit The Game");
            
            scan = new Scanner(System.in);
            String isPlay = scan.nextLine();
            if ("Y".equals(isPlay) || "y".equals(isPlay)) {
            } else {
                break;
            }

        } while (true);
    }
    public void miniGameDefinition() {
        
        Random generator = new Random();
        int[] answer = {1, 2, 3, 4};
        Object[] keys = this.slang.keySet().toArray();
       
        do {
            HashMap<Integer, String> QA = new HashMap<>();
            String resultKey = keys[generator.nextInt(keys.length)].toString();
            int firstAnswer = answer[generator.nextInt(answer.length)];
            String firstValues = this.slang.get(resultKey);
            QA.put(firstAnswer, firstValues);
            for (int i = 1; i <= 3; i++) {
                int randomAnswer;
                Object randomValues;
                String answerKey;
                do {
                    randomAnswer = answer[generator.nextInt(answer.length)];
                    if (QA.containsKey(randomAnswer)) {
                        continue;
                    }
                    break;
                } while (true);
                do {
                    randomValues = this.slang.get(keys[generator.nextInt(keys.length)].toString());
                    if (QA.containsValue(randomValues.toString())) {
                        continue;
                    }
                    break;
                } while (true);
                QA.put(randomAnswer, randomValues.toString());
            }

            System.out.println("Key: " + resultKey);
            for (int i = 1; i <= 4; i++) {
                System.out.println("Number: " + Integer.toString(i) + ", Value: " + QA.get(i));
            }
            Scanner scan = new Scanner(System.in);
            System.out.println("Chose Number Of Definition For Key: ");
            int result = Integer.parseInt(scan.nextLine());

            if (result == firstAnswer) {
                System.out.println("Congratulation, Your Choice Is Correct!!!");
            } else {
                System.out.println("Opps, Your Choice Is Incorrect");
            }
            
            System.out.println("Do You Still Want To Play Mini Game, input 'Y' OR 'y': to keep playing, Other To Quit The Game");
            
            scan = new Scanner(System.in);
            String isPlay = scan.nextLine();
            if ("Y".equals(isPlay) || "y".equals(isPlay)) {
                continue;
            } else {
                break;
            }
        } while (true);
    }
}
