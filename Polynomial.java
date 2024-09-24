import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

public class Polynomial{
    //fields
    public double [] coefficents;
    public int [] exp; 
    //methods
    //constructors


    public Polynomial(){
        coefficents = new double[]{0}; 
        exp = new int[]{0}; 
    }



    public Polynomial(double [] coefficents, int [] exp){
        this.coefficents = coefficents;
        this.exp = exp; 
    }



    public Polynomial(File file){
        String poly = new Scanner(file);
        String[] intermidiate = poly.split("-|\\+");
        int [] exps = new int[intermidiate.length]; 
        double [] vals = new double[intermidiate.length]; 
        for(int i = 0; i < intermidiate.length; i++){
            String toSplit = intermidiate[i];
            if(toSplit.length() == 1){
                vals[i] = parseDouble(toSplit);
                exps[i] = 0;
            }
            else{
                String [] split = toSplit.split("");
                vals[i] = parseDouble(split[0]);
                exps[i] = parseInt(split[1]);
            }
        }
        HashMap<Integer, Double> sorting= new HashMap<>(); 
        for(int i =0; i < vals.length; i++){
            sorting.put(exp[i], vals[i]); 
        }
        exp = Arrays.sort(exps);
        coefficents = new double[vals.length];
        for(int i = 0; i < coefficents_copy.length; i++){
            coefficents[i] = sorting.get(exp[i]); 
        }
    }
    //other methods
   
//store list of sums
//store list of exponents 


public Polynomial add(Polynomial poly2){
    int [] poly2_exp = poly2.exp;
    double [] poly2_co = poly2.coefficents;
    max1 = get_biggest(exp);
    max2= get_biggest(poly2_exp);
    max = math.max(max1, max2) +1; 
    ArrayList<Double> sum = new ArrayList<Double>(); 
    for(int i = 0; i < max; i++){
        sum.add(0); 
    }
    for(int i = 0; i < exp.length; i++){
        sum.set(exp[i], sum.get(exp[i]) + coefficents[i]);
    }
    for(int i = 0; i< poly2_exp.length; i++){
        sum.set(poly2_exp[i], sum.get(poly2_exp[i])+ poly2_co[i]);
    }
    //combining arrays
    ArrayList<Integer> allExp = new ArrayList<>(Arrays.asList(poly2_exp)); 
    for(int i = 0; i< exp.length; i++){
        if(!allExp.contains(exp[i])){
            allExp.add(exp[i]);
        }
    }
    allExp.sort(null); 
    int [] allExpArr = allExp.toArray();
    double [] finalSum = new double[allExpArr.length];
    for(int i = 0; i < allExpArr.length; i++){
        finalSum[i] = sum.get(allExpArr[i]); 
    }
    return new Polynomial(finalSum, allExpArr); 
    }
    


private int get_biggest(int [] exp){
        int max = exp[0];
        for(int i = 0; i < exp.length;i++){
            if(exp[i] > max){
                max = exp[i];
            }
        }
        return max;
    }


//order doesn't matter
public double evaluate(double x){
        double sum = 0;
        for (int i = 0; i < coefficents.length; i++){
            sum += coefficents[i]*(Math.pow(x,exp[i]));
        }
        return sum;
    }


//order doesn't matter
public boolean hasRoot(double x){
        return(evaluate(x) == 0); 
    }

//order matters in the return statement
public Polynomial multiply (Polynomial poly){
        HashMap <Integer, Double> result = new HashMap<>();
        for (i = 0; i < exp.length; i++){
            int deg = exp[i]; 
            double val = coefficents[i];  
            for (j = 0; j < poly.exp.length; j++){
                int poly2Deg = poly.exp[j]; 
                double poly2Coeff = poly.coefficents[j];
                deg += poly2Deg;
                val *= poly2Coeff; 
            }
            result.put(deg, val); 
        }
        ArrayList<Integer> exps = new ArrayList<>(Arrays.asList(keys.result));
        exps.sort(null); 
        Double [] co = new Double[exps.size()];
        for(int i = 0; i < co.length; i++){
            co[i] = result.get(exps.get(i)); 
        }
        int [] expsArr = exps.toArray(); 
        return new Polynomial(co, expsArr); 
    }


//order matters in the input 
public void saveToFile(String fileName){
        String poly = "";
        HashMap<Integer, Double> sorting= new HashMap<>(); 
        for(int i =0; i < coefficents.length; i++){
            sorting.put(exp[i], coefficents[i]); 
        }
        double [] coefficents_copy = new double[coefficents.length];
        int [] exp_copy = Arrays.sort(exp);
        for(int i = 0; i < coefficents_copy.length; i++){
            coefficents_copy[i] = sorting.get(exp_copy[i]); 
        }
        for(i = 0; i < exp.length; i++){
            if(exp_copy[i] == 0){
                poly = String(coefficents_copy[i]);
            }
            else if(poly.length() == 0 & coefficents_copy[i] > 0){
                poly = String(coefficents_copy[i]) + 'x' + String(exp_copy[i]);
            }
            else if(coefficents_copy[i] < 0){
                poly += '-' + String(coefficents_copy[i]) + 'x' + String(exp_copy[i]);
            }
            else{
                poly += '+' + String(coefficents_copy[i]) + 'x' + String(exp_copy[i]);
            }
        }
        FileWriter myFile = new FileWriter("%s",false, fileName);
        myFile.write(poly);
        myFile.close(); 
        
    }
}
