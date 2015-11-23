/**
 * Created by Mldz on 2015-11-23.
 */


public final class RapidMiner {

    private RapidMiner(){}

    public static double dodajDziesieciPi(double a){
        return a + 10 + Math.PI;
    }

    public static double sredniaZTablicy(double... a){
        double sr=0;
        for (double d:a){
            sr+=d;
        }
        return sr/(a.length);
    }

    public static String dodajNapis(String a){
        StringBuilder sb = new StringBuilder(a);
        sb.append("Naspis Z JAVA!");
        System.out.println(sb);
        return sb.toString();
    }

    public static double sredniaDodajLiczbeiWyswietlNapis(double b, String s, double... a){
        double ret = sredniaZTablicy(a);
        ret += b;
        System.out.println(">JAVA< + " + s);
        return  ret;
    }


}
