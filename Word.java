/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CryptarithmeticSolver;

/**
 *
 * @author Jithin
 */
public class Word {
    String name;
    int length;

    public String getName() {
        return name;
    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
    
     Word(String word){
         name = word;
         length = word.length();
     }
    
}
