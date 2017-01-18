package backgammon2;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    final int[] powers={0,40,40,44,48,52,56,60,64,68,72,76,80,84,88,92};
    final int[] hits={0,6,7,8,9,8,10,3,4,3,2,1,3};
    int[] points = {0, 2, 0, 0, 0, 0, -5, 0, -3, 0, 0, 0, 5, -5, 0, 0, 0, 3, 0, 5, 0, 0, 0, 0, -2, 0};
    int doublecube = 1;
    int dice1 = 0;
    int dice2 = 0;
    int dice3 = 0;
    int dice4 = 0;
    int turn = 0;
    boolean playdub = false;

    public Board() {
    }
    public Board(Board b){
        this.dice1=b.dice1;
        this.dice2=b.dice2;
        this.dice3=b.dice3;
        this.dice4=b.dice4;
        this.turn=b.turn;
        this.playdub=b.playdub;
        this.doublecube=b.doublecube;
        this.points=b.points.clone();
    }
    public boolean canmove() {
        boolean playturn = turn % 2 == 0;
        if (playturn) {
            if (points[0] != 0) {
                return (points[dice1]>= -1&& dice1!=0) || (points[dice2] >= -1 && dice2!=0);
            } else {
                for (int i = 1; i < 25; i++) {
                    if (dice1 != 0 && i + dice1 < 25 && points[i] > 0 && points[i + dice1] >= -1 ) {
                        return true;
                    } else if (dice2 != 0 && i + dice2 < 25 && points[i] > 0 && points[i + dice2] >= -1 ) {
                        return true;
                    }
                }
            }
        } else {
            if (points[25] != 0) {
                return (points[25 - dice1] <= 1 && dice1!=0) || (points[25 - dice2] <= 1 && dice2!=0);
            } else {
                for (int i = 24; i > 0; i--) {
                    if (i - dice1 > 0 && points[i - dice1] <= 1 && points[i] < 0 && dice1 != 0) {
                        return true;
                    } else if (i - dice2 > 0 && points[i - dice2] <= 1 && points[i] < 0 && dice2 != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public int getPoint(int point) {
        return points[point];
    }
    public boolean turnover() {
        if (dice1 == 0 && dice2 == 0 && dice3 == 0 && dice4 == 0) {
            turn += 1;
            return true;
        }
        return false;
    }
    public boolean move(int from, int to) {
        boolean playturn = turn % 2 == 0;
        if(to==0 && !playturn && bearOff(from)){return true;}
        if(to==25 && playturn && bearOff(from)){return true;}
        if (to <= 0 || to >= 25) {
            return false;
        }
        if (playturn && points[from] > 0 && points[to] >= -1 && (from == 0 || points[0] == 0)) {
            if (checkdice(to - from)) {
                move(to-from);
                points[from] -= 1;
                points[to] += 1;
                if (points[to] == 0) {
                    points[to] += 1;
                    points[25] -= 1;
                }
                return true;
            } 
        } else if (!playturn && points[from] < 0 && points[to] <= 1 && (from == 25 || points[25] == 0)) {
            
            if (checkdice(from - to)) {
                move(from-to);
                points[from] += 1;
                points[to] -= 1;
                if (points[to] == 0) {
                    points[to] -= 1;
                    points[0] += 1;
                }
                return true;
            } 
        }
        return false;
    }
    public boolean canbearOff() {
        int temp = 12;
        boolean playturn = turn % 2 == 0;
        if (playturn) {
            for (int i = 0; i < 19; i++) {
                if (points[i] > 0) {
                    return false;
                }
            }
            for (int i = 24; i > 19; i--) {
                if (points[i] > 0) {
                    temp = i;
                }
            }
            return dice1 > 25 - temp || dice2 > 25 - temp || points[25 - dice1] > 0 || points[25 - dice2] > 0;
        } else {
            for (int i = 7; i < 26; i++) {
                if (points[i] < 0) {
                    return false;
                }
            }
            for (int i = 1; i < 7; i++) {
                if (points[i] < 0) {
                    temp = i;
                }
            }
            return dice1 > temp || dice2 > temp || points[dice1] < 0 || points[dice2] < 0;
        }
    }
    public void removeLargest(){
    if(dice4!=0){
        dice4=0;
    }
    else if(dice3!=0){
        dice3=0;
    }
    else if(dice1>dice2){
        dice1=0;
    }
    else dice2=0;
      
}
    public boolean bearOff(int from) {
        if (!canbearOff()) {
            return false;
        }
        boolean playturn = turn % 2 == 0;
        int temp = 0;
        if (playturn && points[from] > 0) {
            for (int i = 24; i > 19; i--) {
                if (points[i] > 0) {
                    temp = i;
                }
            }
            if (checkdice(25 - from)) {
                move(25-from);
                points[from] -= 1;
                return true;
            } 
            else if((dice1>25-temp || dice2>25-temp) && from==temp){removeLargest();points[from]-=1;return true;}
        } else if (!playturn && points[from] < 0) {
            for (int i = 1; i < 7; i++) {
                if (points[i] < 0) {
                    temp = i;
                }
            }
            if (checkdice(from)) {
                move(from);
                points[from] += 1;
                return true;
            } 
            else if((dice1>temp || dice2>temp) && from==temp){removeLargest();points[from]+=1;return true;}
        }
        return false;
    }
    public boolean canbearOff(int from) {
        if (!canbearOff()) {
            return false;
        }
        boolean playturn = turn % 2 == 0;
        int temp = 0;
        if (playturn && points[from] > 0) {
            for (int i = 24; i > 19; i--) {
                if (points[i] > 0) {
                    temp = i;
                }
            }
            if (checkdice(25 - from)) {
                return true;
            } 
            else if((dice1>25-temp || dice2>25-temp) && from==temp){return true;}
        } else if (!playturn && points[from] < 0) {
            for (int i = 1; i < 7; i++) {
                if (points[i] < 0) {
                    temp = i;
                }
            }
            if (checkdice(from)) {
                return true;
            } 
            else if((dice1>temp || dice2>temp) && from==temp){return true;}
        }
        return false;
    }
    public int getDie1() {
        return dice1;
    }
    public int getDie2() {
        return dice2;
    }
    public int getDie3() {
        return dice3;
    }
    public int getDie4() {
        return dice4;
    }
    public boolean checkdice(int i){
        return dice1==i || dice2==i || dice3==i || dice4==i;
    }
    public void move(int i){
        if(dice4==i){dice4=0;}
        else if(dice3==i){dice3=0;}
        else if(dice2==i){dice2=0;}
        else if(dice1==i){dice1=0;}
    }
    public void rollDie() {
        Random ran = new Random();
        dice3=0;
        dice4=0;
        dice1 = ran.nextInt(6) + 1;
        dice2 = ran.nextInt(6) + 1;
        if (dice1 == dice2) {
            dice3=dice1;
            dice4=dice1;
        }
    }
    public int getCube() {
        return doublecube;
    }
    public boolean candouble() {
        if (doublecube == 1) {
            return true;
        }
        return !playdub == (turn % 2 == 0);
    }
    public void doubledown() {
        doublecube *= 2;
        playdub = turn % 2 == 0;
    }
    public void nextturn() {
        turn += 1;
    }
    public int getturn() {
        return turn;
    }
    public ArrayList GenerateMoves(){
        // First make an array of all places with my pieces
        // Second, check if one of these is 0, if so, must move from there
        // Third Check if bearing off is possible, by seeing if the array is all less then 6 create the array in order and check first or last value
        // Fourth check each place if it can move or bear off, then do again on new board for next dice, and so on.
    ArrayList movelist=new ArrayList();
    int[] boardcopy=points.clone();
    boolean playturn=turn%2==0;
    return movelist;
    }
    public ArrayList PWP(){
        ArrayList<Integer> places=new ArrayList();
        if(turn%2==0){
    for( int i=0;i<26;i++){
        if(points[i]>0){
            places.add(i);
        }
    }}
    else{
    for( int i=0;i<26;i++){
        if(points[i]<0){
            places.add(i);
        }
    }}
        return places;
    }
    public ArrayList Movecalc(){
        ArrayList<MoveCont> move=new ArrayList();
            if(turn%2==0){
                if(points[0]!=0){
                    if(points[dice1]>=-1 && dice1!=0){move.add(new MoveCont(0,dice1));}
                    if(dice2!=dice1 && points[dice2]>=-1 && dice2!=0){move.add(new MoveCont(0,dice2));}
                }
                else{
                    ArrayList<Integer> places=PWP();
                    for(int i:places){
                        if(canbearOff(i)){move.add(new MoveCont(i,25));}
                        if(i+dice1<25 && points[i+dice1]>=-1 && dice1!=0){move.add(new MoveCont(i,i+dice1));}
                        if(i+dice2<25 && dice2!=dice1 && points[i+dice2]>=-1 && dice2!=0 ){move.add(new MoveCont(i,i+dice2));}
                    }
                }
            }
            else{
                if(points[25]!=0){
                    if(points[25-dice1]<=1 && dice1!=0){move.add(new MoveCont(25,25-dice1));}
                    if(dice2!=dice1 && points[25-dice2]<=1 && dice2!=0){move.add(new MoveCont(25,25-dice2));}
                }
                else{
                    ArrayList<Integer> places=PWP();
                    for(int i:places){
                        if(canbearOff(i)){move.add(new MoveCont(i,0));}
                        if(i-dice1>0 && points[i-dice1]<=1 && dice1!=0){move.add(new MoveCont(i,i-dice1));}
                        if(i-dice2>0 && dice2!=dice1 && points[i-dice2]<=1 && dice2!=0 ){move.add(new MoveCont(i,i-dice2));}
                    }
                }
            }
            return move;
    }
    public int Curve(int i){
        int[] x={30,24,23,22,21,20,19,19,18,18,17,16,15,14,13,12,11,10,8,6,5,4,4,3,3,0};
        return x[i];
    }
    public int ReturnScore(){
        int x=0;
        boolean bt=turn%2==0;
        for(int i=0;i<26;i++){
            if(points[i]==0){}
            else if(points[i]<-1){
                x-=(int)(Curve(25-i)*points[i]*powers[-points[i]]); // need to calculate average number of dice to bear off
            }
            else if(points[i]>1){
                x-=(int)(Curve(i)*points[i]*powers[points[i]]);
            }
            else if(points[i]==-1){
                x+=Curve(25-i)*powers[-points[i]];
                if(!bt){
                    x+=Curve(i)*ChanceCalc(i);}
            }
            else{
                x-=Curve(i)*powers[points[i]];
                if(bt){
                x-=Curve(25-i)*ChanceCalc(i);}
                //x-=Curve(25-i)/2;}
            }
            
        }
        return x;
    }
    public void print(){
    for(int i:points){
        System.out.print(i);
    }
        System.out.println("");
}
    int ChanceCalc(int i){
        int temp=0;
        int temp2=25;
        if(turn%2==0){
        if(i<14){temp2=i+12;}
        for(int j=i;j<=temp2;j++){
            if(points[j]<0){
                temp+=hits[j-i];
            }
        }
        }
        else{
            temp2=0;
            if(i>12){temp2=i-12;}
            for(int j=i;j>=temp2;j--){
            if(points[j]>0){
                temp+=hits[i-j];
            }
        }
        }
    return temp;
}
    public String displayBoard(){  //adapted from cs5400 backgammon code source
        String disp=("   +24-23-22-21-20-19--25--18-17-16-15-14-13-+\n");
    for (int row = 1; row < 6; row++)
    {
      disp+=("   ");
      disp+=("|");
      for (int col = 24; col > 18; col --)
      {
        disp=displayStack(col, row,disp);
      }
      disp+=("|");
      disp=displayStack(25, row,disp);
      disp+=("|");
      for (int col = 18; col > 12; col--)
      {
        disp=displayStack(col, row,disp);
      }
      disp+=("|\n");
    }
    disp+=("   |                  |BAR|                  |\n");
    for (int row = 5; row > 0; row --)
    {
      disp+=("   ");
      disp+=("|");
      for (int col = 1; col < 7; col ++)
      {
        disp=displayStack(col, row,disp);
      }
      disp+=("|");
      disp=displayStack(0, row,disp);
      disp+=("|");
      for (int col = 7; col < 13; col++)
      {
        disp=displayStack(col, row,disp);
      }
      disp+=("|\n");
    }
    disp+=("   +1--2--3--4--5--6---0---7--8--9--10-11-12-+\n");
    return disp;
    }
    public String displayStack(int point, int height, String disp)
  {
    int checkers = points[point];
    char player = 'O';
    if (checkers < 0)
    {
      checkers *= -1;
      player = 'X';
    }
    if (checkers < height)
    {
      disp+=("  ");
    }
    else if (height < 5)
    {
      disp+=(player + " ");
    }
    else if (checkers < 10)
    {
      disp+=(checkers + " ");
    }
    else
    {
      disp+=(checkers);
    }
    disp+=(" ");
    return disp;
  }
    public boolean GameOver(){
        boolean a=true;
        boolean b=true;
        for(int i:points){
            if(i>0){a=false;}
            if(i<0){b=false;}
        }
        return a || b;
    }
    public boolean WhoWon(){
        for(int i:points){
            if(i>0){return true;}}
        return false;    
    }
    public String WriteState(){
        String buff="";
        buff+=turn%2 + ",";
        for(int i:points){
            if(i>=5){
                buff+=Integer.toString(5)+",";
            }
            else if(i<=-5){
                buff+=Integer.toString(-5)+",";
            }
            else buff+=Integer.toString(i)+",";
        }
        return buff;
    }
    int pidX(){
        int sum=0;
        for(int i=0;i<26;i++){
            if(points[i]<0){
                sum+=points[i]*i;
            }
        }
        return sum;
    }
    int pidO(){
        int sum=0;
        for(int i=0;i<26;i++){
            if(points[i]>0){
                sum+=points[i]*(25-i);
            }
        }
        return sum;
    }
}

class MoveCont{
    int t,f;
    MoveCont(int from,int to){
        t=to;f=from;
    }
    public int getTo(){
        return t;
    }
    public int getFrom(){
        return f;
    }
}
