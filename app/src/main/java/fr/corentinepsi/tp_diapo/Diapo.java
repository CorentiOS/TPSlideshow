package fr.corentinepsi.tp_diapo;

public class Diapo {

    private int __rang = 0;
    private int __millis = 4000;


    public int getRang() {return __rang;}

    public void setRang(Integer Rang) {
        __rang = Rang;
    }

    public void addRang(int nbRang) {
        __rang += nbRang;

        if (__rang > 5) {
            __rang = 0;
        }
    }

    public void subsRang(int nbRang) {
        __rang -= nbRang;

        if (__rang < 0) {
            __rang = 5;
        }
    }

    public int getMillis() {return __millis;}

    public void setMillis(Integer Millis) {
        __millis = Millis;
    }


}
