/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektsi_redukt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Mateusz
 */
public class Logic {

    public static String loaded_attributes[]; //names of all attributes
    public int decision_maker;
    public int counter[];
    public boolean used_attributes[];
    public List<String> all_reducts = new ArrayList<>();
    public int reduct_counter = 0;
    public String core="";

    public String usingCSV() throws FileNotFoundException, IOException, URISyntaxException {
        Scanner console = new Scanner(System.in);
        String filePath="";
        System.out.println("If you want to oad example, write LOAD for load example - if you want to load your own example, enter filepath");
        filePath=console.nextLine();
        if (filePath.equalsIgnoreCase("load")){
            filePath=choose_example();
            if (filePath.equals("")){
                System.out.println("You didn't choose any of examples, so enter path to one you had on your disk");
                filePath = console.nextLine();
            }
            //filePath=filePath.replace("file:/","");
            //filePath=filePath.replaceAll("/", "\\\\").replaceAll("%20", " ");
        }

            //System.out.println("Enter filepath: ");
            //filePath = console.nextLine();
            if(!filePath.endsWith(".csv"))
            {
                System.out.println("Wrong file, program is being closed");
                System.exit(0);
            }
            File f = new File(filePath); 
            if (!f.exists()) { 
                System.out.println("File does not exists."); 
                System.exit(0);
            }
        System.out.println(filePath);
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        loaded_attributes = br.readLine().split(",");
        System.out.println("Attributes' names: ");
        int i = 0;
        for (String x : loaded_attributes) {
            System.out.print("Index: " + i + " Name: " + x + ";\n");
            i++;
        }
        System.out.println("Found " + loaded_attributes.length + " attributes. I guess the last one a decision-making.");
        //String next = console.next();
        //if (next.equalsIgnoreCase("YES")) {
            //System.out.println("Fine, thanks");
            decision_maker = loaded_attributes.length - 1;
        //    return filePath;
        //}
        return filePath;
    }

    public String choose_example() throws URISyntaxException{
        String filePath="";
        URL url=getClass().getResource("./examples");
        File dir=new File(url.toURI());
        File dir_list[]=dir.listFiles();
        if (dir_list!=null){
            for (File child: dir_list){
                System.out.println("Name of the example: "+child.toString().substring(child.toString().lastIndexOf("\\")));
                System.out.println("Choose this one? (enter YES if yes)");
                Scanner console = new Scanner(System.in);
                if (console.next().equalsIgnoreCase("yes")){
                    filePath=child.toString();
                    return filePath;
                }
            }
        }
        return filePath;
    }
    
    public ArrayList<Object> openCSV(String filePath) throws FileNotFoundException, IOException {
        ArrayList<Object> all_objects = new ArrayList<>();
        //String csv = "C:\\Users\\Mateusz\\OneDrive\\Studia\\Semestr IV\\Sztuczna inteligencja\\Pracownia\\7-8\\Do sprawozdania\\Zadanie2\\zadanie2-proba3.csv";
        BufferedReader br;
        String line;
        String cvsSplitBy = ",";
        System.out.println(filePath);
        br = new BufferedReader(new FileReader(filePath));
        line = br.readLine();
        int j = 0;
        String attributes_name[] = line.split(cvsSplitBy);
        while ((line = br.readLine()) != null) {
            String test[] = line.split(cvsSplitBy);
            int i = 0;
            ArrayList<Attribute> all_attributes = new ArrayList<Attribute>() {
            };
            for (String x : test) {
                Attribute attribute = new Attribute(attributes_name[i], x);
                if (i != test.length - 1) {
                    all_attributes.add(attribute);
                } else {
                    attribute.setDecisionMaking(true);
                    all_attributes.add(attribute);
                }
                i++;
            }
            Object new_object = new Object("Object" + j);
            new_object.setAttributes(all_attributes);
            j++;
            //roughSet.setName("Object "+i);
            //roughSet.setAttributes(all_attributes);
            //roughSet.toString();
            //System.out.println(new_object.getName() + "" + new_object.getAttributes());
            //new_object.toString();
            all_objects.add(new_object);
        }
        return all_objects;
    }

    public String[][] show_matrix(ArrayList<Object> objects) {
        String disc_matrix[][] = new String[objects.size()][objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            for (int j = i; j < objects.size(); j++) {
                if (j == i) {
                    disc_matrix[i][j] = "-";
                } else {
                    List<Attribute> o1 = objects.get(i).getAttributes();
                    List<Attribute> o2 = objects.get(j).getAttributes();
                    if (o1.get(decision_maker).getValue() == null ? o2.get(decision_maker).getValue() == null : o1.get(decision_maker).getValue().equals(o2.get(decision_maker).getValue())) {
                        //System.out.println("test");
                        disc_matrix[i][j] = "-";
                    } else {
                        for (int k = 0; k < o1.size(); k++) {
                            if (!o1.get(k).getName().equals(loaded_attributes[decision_maker]) && !o1.get(k).getValue().equals(o2.get(k).getValue())) {
                                if (disc_matrix[i][j] == null) {
                                    disc_matrix[i][j] = o1.get(k).getName();
                                } else {
                                    disc_matrix[i][j] = new StringBuilder(disc_matrix[i][j]).append("," + o1.get(k).getName()).toString();
                                }
                            }
                        }
                    }
                }
            }
        }
        //System.out.println("test");
        return disc_matrix;
    }

    public void count_matrix(String matrix[][]) {
        counter = new int[loaded_attributes.length];
        if (used_attributes==null)
            used_attributes=new boolean[counter.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[i].length; j++) {
                if (matrix[i][j] != null && !matrix[i][j].equals("-")) {
                    String[] attributes_cell = matrix[i][j].split(",");
                    for (int k = 0; k < attributes_cell.length; k++) {
                        for (int l = 0; l < loaded_attributes.length; l++) {
                            if (attributes_cell[k].equals(loaded_attributes[l])) {
                                counter[l]++;
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < counter.length; i++) {
            //System.out.println(loaded_attributes[i] + ": " + counter[i]);
        }
    }

    public void quick_reduct2(String matrix[][]) {
        used_attributes = new boolean[counter.length];
        int id = count_appearance();
        do {
            go_through_matrix(id, matrix);
            count_matrix(matrix);
            id = count_appearance();
        } while (!empty_matrix(matrix));
        for (int i = 0; i < counter.length; i++) {
            if (used_attributes[i]) {
                //System.out.println(loaded_attributes[i]);
                used_attributes[i] = false;
            }
        }
    }

//    public void quick_reduct(String matrix[][]){
//        used_attributes=new boolean[counter.length];
//        int id=count_appearance();
//        if (id!=-1)
//            go_through_matrix(id,matrix);
//        if (!empty_matrix(matrix)){
//            count_matrix(matrix);
//            id=count_appearance();
//            if (id!=-1)
//                go_through_matrix(id,matrix);
//            if (empty_matrix(matrix)){
//                for (int i=0; i<counter.length; i++){
//                    if (used_attributes[i])
//                        System.out.println(loaded_attributes[i]);
//                }
//            }
//        }
//        System.out.println("test2");
//    }
    public int count_appearance() {
        int max = 0;
        int id = -1;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] > max && !used_attributes[i]) {
                max = counter[i];
                id = i;
                used_attributes[id] = true;
            }
        }
        return id;
    }

    public void go_through_matrix(int id, String matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                if (!matrix[i][j].equals("-")) {
                    if (matrix[i][j].contains(loaded_attributes[id])) {
                        matrix[i][j] = "-";
                    }
                }
            }
        }
    }

    public boolean empty_matrix(String matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                if (!matrix[i][j].equals("-")) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> all_reducts(String matrix[][]) {
        count_matrix(matrix);
        String matrix_dup[][] = new String[matrix.length][matrix.length];
        copy_array(matrix, matrix_dup);
        boolean iter_used_attr[] = new boolean[used_attributes.length];
        copy_bool(used_attributes, iter_used_attr);
        String attributes = all_reducts_copy_appearance();
        String attributes_to_use[] = attributes.split(",");
        for (String x : attributes_to_use) {
            copy_array(matrix_dup, matrix);
            copy_bool(iter_used_attr, used_attributes);
            if (empty_matrix(matrix)) {
                if (empty_matrix(matrix_dup)) {
                    String reduct="";
                    for (int i = 0; i < counter.length; i++) {
                        if (used_attributes[i]) {
                            //System.out.println(loaded_attributes[i]);
                            reduct=reduct.concat(loaded_attributes[i]+",");
                        }
//                        if (x.equals(loaded_attributes[i])) {
//                            used_attributes[i] = true;
//                            System.out.println(loaded_attributes[i]);
//                        }
                    }
                    all_reducts.add(reduct);
                    
                } else {
                    copy_array(matrix_dup, matrix);
                    copy_bool(iter_used_attr, used_attributes);
                    all_reducts_go_through_matrix(x, matrix);
                    all_reducts(matrix);
                    if (empty_matrix(matrix)) {
                        copy_array(matrix_dup, matrix);
                        copy_bool(iter_used_attr, used_attributes);
                    }
                }
//                    copy_array(matrix_dup, matrix);
//                    copy_bool(iter_used_attr,used_attributes);
//                    all_reducts(matrix_dup);
            } else {
                for (int i = 0; i < counter.length; i++) {
                        if (x.equals(loaded_attributes[i]) && !used_attributes[i]) {
                            used_attributes[i] = true;
                        }
                    }
                all_reducts_go_through_matrix(x, matrix);
                all_reducts(matrix);
                if (empty_matrix(matrix)) {
                    copy_array(matrix_dup, matrix);
                    copy_bool(iter_used_attr, used_attributes);
                }
            }
        }
        return all_reducts;
    }

    public void copy_array(String matrix_old[][], String matrix_new[][]) {
        for (int i = 0; i < matrix_old.length; i++) {
            for (int j = i; j < matrix_old[i].length; j++) {
                matrix_new[i][j] = matrix_old[i][j];
            }
        }
    }

    public void copy_bool(boolean old[], boolean newer[]) {
        for (int i = 0; i < old.length; i++) {
            newer[i] = old[i];
        }
    }

    public String all_reducts_copy_appearance() {
        String line = "";
        float max = (float) 0.5;
        int id = -1;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] > max && !used_attributes[i] && i != decision_maker) {
                if (id!=-1)
                    used_attributes[id]=false;
                max = counter[i];
                id = i;
                used_attributes[id] = true;
            }
            if (counter[i] == max && i != decision_maker) {
                line = line.concat(loaded_attributes[i] + ",");
            }
        }
        return line;
    }

    public void all_reducts_go_through_matrix(String attr, String matrix[][]) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix.length; j++) {
                if (!matrix[i][j].equals("-")) {
                    if (matrix[i][j].contains(attr)) {
                        matrix[i][j] = "-";
                    }
                }
            }
        }
    }
    
    public List<String> remove_redundant(){
        java.util.Collections.sort(all_reducts);
        //System.out.println("test");
        for (int i=0; i<all_reducts.size(); i++){
            for (int j=i+1; j<all_reducts.size(); j++){
                if (all_reducts.get(i).equals(all_reducts.get(j)))
                    all_reducts.set(j, "");
            }
        }
        List<String> final_all_reducts=new ArrayList<>();
        List<String> all_reducts_dup=new ArrayList<>();
        for (String x:all_reducts){
            if (!x.equals(""))
                final_all_reducts.add(x);
        }
        all_reducts.clear();
        all_reducts.addAll(final_all_reducts);
        all_reducts_dup.addAll(final_all_reducts);
        final_all_reducts.clear();
        //System.out.println("test2");
        for (int i=0; i<all_reducts.size(); i++){
            for (int j=0; j<all_reducts_dup.size(); j++){
                if (i!=j){
                    if (!not_containing_strings(all_reducts.get(i), all_reducts_dup.get(j)) && !final_all_reducts.contains(all_reducts.get(i))){
                        if (all_reducts.get(i).length()<all_reducts.get(j).length()){
                            all_reducts_dup.remove(all_reducts_dup.get(j));
                            j--;
                            if (i!=0)
                                i--;
                        }
                        else {
                            all_reducts_dup.remove(all_reducts_dup.get(i));
                            if (i!=0)
                                i--;
                        }
                    }
                }
            }
            all_reducts.clear();
            all_reducts.addAll(all_reducts_dup);
        }
        
//        for (int i=0; i<all_reducts.size(); i++){
//            boolean comparator=true;
//            for (int j=0; j<all_reducts.size(); j++){
//                if (i!=j){
//                   if (all_reducts.get(i).contains(all_reducts.get(j)) || final_all_reducts.contains(all_reducts.get(i)))
//                       comparator=false;
//                   else if(!not_containing_strings(all_reducts.get(i), all_reducts.get(j))){
//                       all_reducts_dup.remove(all_reducts.get(j));
//                   }
//                }
//            }
//            if (comparator)
//                final_all_reducts.add(all_reducts.get(i));
//        }
        
//        for (int i=0; i<all_reducts.size(); i++){
//            for (String x:all_reducts){
//                if (!x.equals(all_reducts.get(i)) && !x.contains(all_reducts.get(i)) && !final_all_reducts.contains(x))
//                    final_all_reducts.add(x);
//            }
//        }
//        all_reducts.clear();
//        all_reducts.addAll(final_all_reducts);
        is_reduct_really();
        return all_reducts;
    }
    public void is_reduct_really(){
        if (all_reducts.size()==1){
            String line[]=all_reducts.get(0).split(",");
            if (line.length==loaded_attributes.length-1)
            {
                all_reducts.clear();
            }
        }
    }
    
    public void get_core(){
        for (int i=0; i<loaded_attributes.length; i++){
            boolean container=true;
            for (String x:all_reducts){
                if(!x.contains(loaded_attributes[i]))
                    container=false;
            }
            if (container){
                core=core.concat(loaded_attributes[i]+",");
            }
        }
        if (core.equals(""))
            System.out.println("No core");
        else System.out.println("Core: "+core);
    }
    
    public boolean not_containing_strings(String reduct1, String reduct2){
        boolean container=true;
        String divided_attr1[]=reduct1.split(",");
        String divided_attr2[]=reduct2.split(",");
        if (divided_attr1.length<divided_attr2.length){
            container=compare_strings_length(divided_attr1, divided_attr2);
        }
        if (divided_attr1.length>divided_attr2.length){
            container=compare_strings_length(divided_attr2, divided_attr1);
        }
        if (divided_attr1.length==divided_attr2.length){
            if (reduct1.equals(reduct2))
                container=false;
            else container=true;
        }
            
        return container;
    }
    public boolean compare_strings_length(String str_shorter[], String str_longer[]){
        int difference=0;
        boolean container=false;
        for (String x : str_longer){
                container=false;
                for (int i=0; i<str_shorter.length; i++){
                    if (x.equals(str_shorter[i])){
                        container=true;
                        continue;
                    }
                }
                if (!container)
                    difference++;
                    //return container;
            }
        if (difference>str_longer.length-str_shorter.length)
            container=true;
        else container=false;
        return container;
    }
}
