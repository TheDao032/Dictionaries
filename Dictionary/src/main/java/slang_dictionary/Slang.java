/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slang_dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 *
 * @author thedao
 */
public class Slang {
    public HashMap<String, String> slang;

    public Slang() {
        this.slang = new HashMap<String, String>();
        String path = "slang.txt";
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("`");
                this.slang.put(values[0], values[1]);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        
    }
    
    public void add(String key, String value){
        this.slang.put(key, value);
    }
}
