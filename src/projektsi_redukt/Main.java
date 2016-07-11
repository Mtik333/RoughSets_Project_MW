/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektsi_redukt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mateusz
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, URISyntaxException {
        // TODO code application logic here
        Scanner console = new Scanner (System.in);
        Logic logic = new Logic();
        String filePath=logic.usingCSV();
        System.out.println(filePath);
        ArrayList<Object> all_objects=logic.openCSV(filePath);
        all_objects.stream().forEach((x) -> {
            System.out.println("Object: "+x.getName()+"\nAttributes: "+x.getAttributes());
        });
        String disc_matrix[][]=logic.show_matrix(all_objects);
        String new_disc_matrix2[][]=new String[disc_matrix.length][disc_matrix.length];
        logic.copy_array(disc_matrix,new_disc_matrix2);
        logic.count_matrix(disc_matrix);
        //logic.quick_reduct2(disc_matrix);
        logic.all_reducts(new_disc_matrix2);
        List<String> test=logic.remove_redundant();
        if (!test.isEmpty()){
            System.out.println("Reducts: ");
            for (String x : test){
                System.out.println(x);
            }
            System.out.println("Core: ");
            logic.get_core();
        }
        else System.out.println("There's no reduct (and the core as well).");
        //System.out.println(logic.not_containing_strings("Plec,Wiek,Czas,HbA1c,Ch,", "Plec,Wiek,Czas,Rodzaj,HbA1c,Ch,"));
        //System.out.println(logic.not_containing_strings("Plec,Wiek,Czas,HbA1c,Ch,", "Plec,Wiek,Czas,Rodzaj,Ch,"));
    }
    
}
