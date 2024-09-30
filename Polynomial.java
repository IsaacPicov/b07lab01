import java.util.*; 
import java.io.*;

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
       try{
        Scanner poly = new Scanner(file);
        String polyString = poly.nextLine();
        poly.close();
        String[] intermidiate = polyString.split("(?=[+-])");
        int [] exps = new int[intermidiate.length]; 
        double [] vals = new double[intermidiate.length]; 
        for(int i = 0; i < intermidiate.length; i++){
            String toSplit = intermidiate[i].trim();
            if (toSplit.equals("-x")) {
                vals[i] = -1;
                exps[i] = 1;
            } else if (toSplit.equals("+x")) {
                vals[i] = 1;
                exps[i] = 1;
            } else if (toSplit.equals("-")) {
                vals[i] = -1;
                exps[i] = 0;
            } else if (toSplit.equals("+")) {
                vals[i] = 1;
                exps[i] = 0;
            } else if (!toSplit.contains("x")) {
                vals[i] = Double.parseDouble(toSplit);
                exps[i] = 0;
            } else {
                // Handle cases like "+3x2" or "-2x"
                String[] split = toSplit.split("x");
                
                if (split[0].equals("+") || split[0].isEmpty()) {
                    vals[i] = 1;
                } else if (split[0].equals("-")) {
                    vals[i] = -1;
                } else {
                    vals[i] = Double.parseDouble(split[0]);
                }

                if (split.length == 1 || split[1].isEmpty()) {
                    exps[i] = 1; // "x" means x^1
                } else {
                    exps[i] = Integer.parseInt(split[1]);
                }
            }
        }
        
        this.exp = exps;
        this.coefficents = vals;
        }
        catch(FileNotFoundException e){
            System.out.println("The file could not be found: " + e.getMessage());
        }
    }
    //other methods 

public Polynomial add (Polynomial poly2){
    HashMap<Integer, Double> addMap = new HashMap<>();
    for(int i = 0; i < exp.length; i++){
        addMap.put(exp[i], coefficents[i]);
    }
    for(int i = 0; i < poly2.exp.length;i++){
        if(addMap.containsKey(poly2.exp[i])){
            double val = addMap.get(poly2.exp[i]);
            addMap.remove(poly2.exp[i]);
            addMap.put(poly2.exp[i], val+poly2.coefficents[i]);
        }
    }
    ArrayList<Integer> expArrayList = new ArrayList<>();
    ArrayList<Double> coArrayList = new ArrayList<>();
    for(Map.Entry<Integer, Double> entry: addMap.entrySet()){
        if (!(entry.getValue()).equals(0)){
            expArrayList.add(entry.getKey());
            coArrayList.add(entry.getValue()); 
        }
    }
    Integer [] integerExp = expArrayList.toArray(new Integer[0]);
    Double [] doubleCo = coArrayList.toArray(new Double[0]);
    int[] intExp = new int[integerExp.length];
    double [] realCo = new double[doubleCo.length];
    for(int i = 0; i<integerExp.length;i++){
        intExp[i] = integerExp[i];
        realCo[i] = doubleCo[i];
    }
    return new Polynomial(realCo, intExp);
}


public double evaluate(double x){
        double sum = 0;
        for (int i = 0; i < coefficents.length; i++){
            sum += coefficents[i]*(Math.pow(x,exp[i]));
        }
        return sum;
    }


public boolean hasRoot(double x){
        return(evaluate(x) == 0); 
    }

public Polynomial multiply (Polynomial poly){
        HashMap <Integer, Double> result = new HashMap<>();
        for (int i = 0; i < exp.length; i++){
            int deg = exp[i]; 
            double val = coefficents[i];  
            for (int j = 0; j < poly.exp.length; j++){
                int poly2Deg = poly.exp[j]; 
                double poly2Coeff = poly.coefficents[j];
                deg += poly2Deg;
                val *= poly2Coeff; 
            }
            result.put(deg, val); 
        }
        ArrayList<Integer> expArrayList = new ArrayList<>();
        ArrayList<Double> coArrayList = new ArrayList<>();
        for(Map.Entry<Integer, Double> entry: result.entrySet()){
            if (!(entry.getValue()).equals(0)){
                expArrayList.add(entry.getKey());
                coArrayList.add(entry.getValue()); 
            }
        }
        Integer [] integerExp = expArrayList.toArray(new Integer[0]);
        Double [] doubleCo = coArrayList.toArray(new Double[0]);
        int[] intExp = new int[integerExp.length];
        double [] realCo = new double[doubleCo.length];
        for(int i = 0; i<integerExp.length;i++){
            intExp[i] = integerExp[i];
            realCo[i] = doubleCo[i];
        }
        return new Polynomial(realCo, intExp);
    }



//order matters in the input 
public void saveToFile(String fileName){
        String poly = "";
        for(int i = 0; i < exp.length; i++){
            if(exp[i] == 0){
                poly = String.valueOf(coefficents[i]);
            }
            else if(poly.length() == 0 & coefficents[i] > 0){
                poly = String.valueOf(coefficents[i]) + 'x' + String.valueOf(exp[i]);
            }
            else if(coefficents[i] < 0){
                poly += '-' + String.valueOf(coefficents[i]) + 'x' + String.valueOf(exp[i]);
            }
            else{
                poly += '+' + String.valueOf(coefficents[i]) + 'x' + String.valueOf(exp[i]);
            }
        }
        try{
        FileWriter myFile = new FileWriter(fileName,false);
        myFile.write(poly);
        myFile.close(); 
        }
        catch(IOException e){
            System.out.println("An I/O error occurred: " + e.getMessage());
        }
        
    }
}
