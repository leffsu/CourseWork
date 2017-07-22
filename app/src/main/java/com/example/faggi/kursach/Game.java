package com.example.faggi.kursach;

class Game {

    private final static String ERROR_STANDARD = "ERROR";
    static String gameWord = ERROR_STANDARD;
    private static char ControlWord[] = new char[gameWord.length()];

    public Game() {


    }

    static void onStart() {

        gameWord = gameWord.toLowerCase();
        ControlWord = gameWord.toCharArray();

    }

    static boolean checkWord(String word) {
        // boolean array for checking
        boolean check[] = new boolean[word.length()];

        word = word.toLowerCase();
        char[] checkWord = word.toCharArray();
        for (int i = 0; i < checkWord.length; i++) {
            for (char aControlWord : ControlWord) { // string length vs char array length speed
                if (checkWord[i] == aControlWord) {
                    check[i] = true;
                }
            }
        }
        for (int i = 0; i < word.length(); i++) {
            if (!check[i])
                return false;
        }
        return true;
    }
}
