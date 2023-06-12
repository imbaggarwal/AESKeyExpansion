import java.security.Key;
import java.util.Arrays;

public class KeyExpansion {

    public static void main(String[] args) {

        String[][] initial ={
                {"14","29","1d","55"},
                {"d9","9f","57","9f"},
                {"e7","2d","d8","73"},
                {"15","d6","df","db"}
        };

        Key(initial);

    }

    public static String[] cyclicShift(String[] word){
        String temp = word[0];
        for(int i=1; i<word.length; i++){
            word[i-1] = word[i];
        }
        word[word.length-1] = temp;

        return word;
    }

    public static String[] subWord(String[] word){
        String[][] subsMatrix = {
                {"63","7c","77","7b","f2","6b","6f","c5","30","01","67","2b","fe","d7","ab","76"},
                {"ca","82","c9","7d","fa","59","47","f0","ad","d4","a2","af","9c","a4","72","c0"},
                {"b7","fd","93","26","36","3f","f7","cc","34","a5","e5","f1","71","d8","31","15"},
                {"04","c7","23","c3","18","96","05","9a","07","12","80","e2","eb","28","b2","75"},
                {"09","83","3c","1a","1b","6e","5a","a0","52","3b","d6","b3","29","e3","2f","84"},
                {"53","d1","00","ed","20","fc","b1","5b","6a","cb","be","39","4a","4c","58","cf"},
                {"d0","ef","aa","fb","43","4d","33","85","45","f9","02","7f","50","3c","9f","a8"},
                {"51","a3","40","8f","92","9d","38","f5","bc","b6","da","21","10","ff","f3","d2"},
                {"cd","0c","13","ec","5f","97","44","17","c4","a7","7e","3d","64","5d","19","73"},
                {"60","81","4f","dc","22","2a","90","88","46","ee","b8","14","de","5e","0b","db"},
                {"e0","32","3a","0a","49","06","24","5c","c2","d3","ac","62","91","95","e4","79"},
                {"ba","78","25","2e","1c","a6","b4","c6","e8","dd","74","1f","4b","bd","8b","8a"},
                {"e7","c8","37","6d","8d","d5","4e","a9","6c","56","f4","ea","65","7a","ae","08"},
                {"70","3e","b5","66","48","03","f6","0e","61","35","57","b9","86","c1","1d","9e"},
                {"e1","f8","98","11","69","d9","8e","94","9b","1e","87","e9","ce","55","28","df"},
                {"8c","a1","89","0d","bf","e6","42","68","41","99","2d","0f","b0","54","bb","16"}
        };

        for(int i=0; i< word.length; i++){
            int r, c;
            r = Integer.parseInt(String.valueOf(word[i].charAt(0)),16);
            c = Integer.parseInt(String.valueOf(word[i].charAt(1)),16);

            word[i] = subsMatrix[r][c];
        }

        return word;

    }

    public static String[] xorRcon(String Rcon, String[] word){

        word[0] = Integer.toHexString(Integer.parseInt(Rcon,16) ^ Integer.parseInt(word[0],16));

        return word;
    }

    public static String[] gFunction(String[] word, int round){
        String[] Rcon = {"01","02","04","08","10","20","40","80","1B","36"};

        cyclicShift(word);
        subWord(word);
        xorRcon(Rcon[round],word);

        return word;
    }

    public static String[][] keyExpansion(String[][] initialKey, int round){


        String[] w0 = new String[4];
        String[] w1 = new String[4];
        String[] w2 = new String[4];
        String[] w3 = new String[4];

        for(int i=0; i< initialKey.length; i++){
            for(int j=0; j<initialKey[i].length; j++){
                if(j==0){
                    w0[i] = initialKey[i][j];
                }
                else if(j==1){
                    w1[i] = initialKey[i][j];
                }
                else if(j==2){
                    w2[i] = initialKey[i][j];
                }
                else if(j==3){
                    w3[i] = initialKey[i][j];
                }
            }
        }

        String[] gWord = gFunction(w3,round);

        String[][] ExpandedKey = new String[4][4];

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(j==0){
                    ExpandedKey[i][j] = Integer.toHexString(Integer.parseInt(w0[i],16) ^ Integer.parseInt(gWord[i],16));
                }
                if(j==1){
                    ExpandedKey[i][j] = Integer.toHexString(Integer.parseInt(w1[i],16) ^ Integer.parseInt(ExpandedKey[i][j-1],16));
                }
                if(j==2){
                    ExpandedKey[i][j] = Integer.toHexString(Integer.parseInt(w2[i],16) ^ Integer.parseInt(ExpandedKey[i][j-1],16));
                }
                else if(j==3){
                    ExpandedKey[i][j] = Integer.toHexString(Integer.parseInt(initialKey[i][3],16) ^ Integer.parseInt(ExpandedKey[i][j-1],16));
                }

                if(ExpandedKey[i][j].length()<2){
                    ExpandedKey[i][j] = "0"+ExpandedKey[i][j];
                }
            }
        }
        return ExpandedKey;
    }

    public static void Key(String[][] initialKey){
        String[][] round1 = keyExpansion(initialKey,0);
        print(round1);
        String[][] round2 = keyExpansion(round1,1);
        print(round2);
        String[][] round3 = keyExpansion(round2,2);
        print(round3);
        String[][] round4 = keyExpansion(round3,3);
        print(round4);
        String[][] round5 = keyExpansion(round4,4);
        print(round5);
        String[][] round6 = keyExpansion(round5,5);
        print(round6);
        String[][] round7 = keyExpansion(round6,6);
        print(round7);
        String[][] round8 = keyExpansion(round7,7);
        print(round8);
        String[][] round9 = keyExpansion(round8,8);
        print(round9);
        String[][] round10 = keyExpansion(round9,9);
        print(round10);

    }

    public static void print(String[][] rounds){

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                System.out.print(rounds[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
