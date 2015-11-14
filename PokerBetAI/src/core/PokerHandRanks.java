/*Checklist:
 * high: Done
 * pair: Done
 * two: Done <- this and pair are one
 * three:
 * straight: Done
 * flush: Done
 * full:
 * four: Done
 * sFlush:
 * Royal:
 */


public class PokerHandRanks {
    
    private static int royal(int [] a, int [] b, String [] c, String [] d) {
        
        return 0;
    }
    
    private static int sFlush(int [] a, int [] b, String [] c, String [] d) {
        
        return 0;
    }
    
    private static int four(int [] a, int [] b) {
        int track = 0;
        int trackTwo = 0;
        if (a[0] == a[1]) {
            for (int i = 0; i < 3; i++) {
                if (a[i] == a[i + 1]) {
                    track++;
                }
            }
        }
        else {
            if (a[2] == a[1]) {
                for (int i = 1; i < 4; i++) {
                    if (a[i] == a[i + 1]) {
                        track++;
                    }
                }
            }
        }
        if (b[0] == b[1]) {
            for (int i = 0; i < 3; i++) {
                if (b[i] == b[i + 1]) {
                    trackTwo++;
                }
            }
        }
        else {
            if (b[2] == b[1]) {
                for (int i = 1; i < 4; i++) {
                    if (b[i] == b[i + 1]) {
                        trackTwo++;
                    }
                }
            }
        }
        if (track == 3 && trackTwo == 3) {
            return high(a, b);
        }
        if (track == 3) {
            return 1;
        }
        if (trackTwo == 3) {
            return 2;
        }
        return 0;
    }
    
    private static int full(int [] a, int [] b) {
        
        return 0;
    }
    
    private static int flush(String [] a, String [] b) {
        boolean aWin = true;
        boolean bWin = true;
        for (int i = 0; i < 4; i++) {
            aWin = aWin && (a[i].equals(a[i+1]));
        }
        for (int i = 0; i < 4; i++) {
            bWin = bWin && (b[i].equals(b[i+1]));
        }
        if (aWin == bWin) {
            return 0;
        }
        if (aWin) {
            return 1;
        }
        else {
            return 2;
        }
    }
    
    private static int straight(int [] a, int [] b) {
        int track = 0;
        int trackTwo = 0;
        for (int i = 0; i < 4; i++) {
            if ((a[i] + 1) == a[i + 1]) {
                track++;
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((b[i] + 1) == b[i + 1]) {
                trackTwo++;
            }
        }
        if (track == 4 && trackTwo == 4) {
            return high(a, b);
        }
        if (track == 4) {
            return 1;
        }
        if (trackTwo == 4) {
            return 2;
        }
        return 0;
    }
    
    private static int three(int [] a, int [] b) {
        
        return 0;
    }
    
    private static int pairs(int [] a, int [] b) {
        int count = 0;
        int countTwo = 0;
        int track = 0;
        int trackTwo = 0;
        for (int i = 0; i < 4; i++) {
            if (a[i] == a[i + 1]) {
                count++;
                track = a[i];
            }
        }
        for (int i = 0; i < 4; i++) {
            if (b[i] == b[i + 1]) {
                countTwo++;
                trackTwo = b[i];
            }
        }
        if (count > countTwo) {
            return 1;
        }
        if (countTwo > count) {
            return 2;
        }
        if (track > trackTwo) {
            return 1;
        }
        if (trackTwo > track) {
            return 2;
        }
        return 0;
    }
    
    private static int high(int [] a, int [] b) {
        if (a[4] > b[4]) {
            return 1;
        }
        if (b[4] > a[4]) {
            return 2;
        }
        return 0;
    }
    
    private static int [] sort(int [] a) {
        for (int i = 1; i < 5; i++) {
            int x = a[i];
            int j = i;
            while(j > 0 && a[j - 1] > x) {
                a[j] = a[j - 1]; 
                j--;
                a[j] = x;
            }
        }
        return a;
    }
    
    public static void main(String[] args) {
        int [] one = new int [5];
        int [] two = new int [5];
        String [] oneS = new String [5];
        String [] twoS = new String [5];
        int wins = 0;
        while (!StdIn.isEmpty()) {
            //0 = tie, 1 = player 1, 2 = player 2
            int winner = 0;
            for (int i = 0; i < 5; i++) {
                one[i] = StdIn.readInt();
                
                oneS[i] = StdIn.readString();
            }
            for (int i = 0; i < 5; i++) {
                two[i] = StdIn.readInt();
                twoS[i] = StdIn.readString();
            }
            one = sort(one);
            two = sort(two);
            while(true) {
                winner = royal(one, two, oneS, twoS);
                if(winner != 0) {
                    break;
                }
                winner = sFlush(one, two, oneS, twoS);
                if(winner != 0) {
                    break;
                }
                winner = four(one, two);
                if(winner != 0) {
                    break;
                }
                winner = full(one, two);
                if(winner != 0) {
                    break;
                }
                winner = flush(oneS, twoS);
                if(winner != 0) {
                    break;
                }
                winner = straight(one, two);
                if(winner != 0) {
                    break;
                }
                winner = three(one, two);
                if(winner != 0) {
                    break;
                }
                winner = pairs(one, two);
                if(winner != 0) {
                    break;
                }
                winner = high(one, two);
                if(winner != 0) {
                    break;
                }
            }
            if (winner == 1) {
                wins++;
            }
        }
        StdOut.println(wins);
    }
}
