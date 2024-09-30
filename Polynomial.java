public class Polynomial{
    //fields
    public double [] coefficents;
    //methods
    public Polynomial(){
        coefficents = new double[]{0};
    }
    public Polynomial(double [] c){
        coefficents = c;
    }
    public Polynomial add(Polynomial poly){
        double [] coefficents1 = coefficents;
        double [] coefficents2= poly.coefficents;
        int len;
        double [] smaller;
        double [] bigger;
        if(coefficents1.length> coefficents2.length){
            len = coefficents1.length -1;
            bigger = coefficents1;
            smaller = coefficents2;
        }
        else{
             len = coefficents2.length -1;
             bigger = coefficents2;
             smaller = coefficents1;
        }
        for (int i = 0; i < len; i++){
            bigger[i] = bigger[i]+smaller[i];
        }
        return new Polynomial(bigger); 
    }
    public double evaluate(double x){
        double sum = coefficents[0];
        for (int i = 1; i < coefficents.length; i++){
            sum += coefficents[i]*(Math.pow(x,i));
        }
        return sum;
    }
    public boolean hasRoot(double x){
        return(evaluate(x) == 0); 
    }
}