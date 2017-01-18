package backgammon2;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BackGammon2 {
    public static JTextArea area2= new JTextArea();
    public static int location=0;
    public static JTextField to=new JTextField("0");
    public static JTextField from=new JTextField("0");
    public static boolean turn=true;
    public static JLabel turnlab=new JLabel("Player O");
    public static JLabel dice=new JLabel("0,0,0,0");
    public static JLabel cube=new JLabel("1");
    public static JTextArea area= new JTextArea();
    public static Board Gameboard=new Board();
    public static ArrayList<Board> BoardSave=new ArrayList();
    public static int doublestate=0;
    public static boolean POAI=true;
    public static boolean PXAI=false;
    public static void main(String[] args) {
        JFrame frame= new JFrame();
        JFrame frame2= new JFrame();
        JPanel pan=new JPanel(new GridLayout(1,2));     
        JPanel pan2=new JPanel(new GridLayout(1,3)); 
        area.setPreferredSize(new Dimension(350,250));
        area.setEditable(false);
        area2.setPreferredSize(new Dimension(350,250));
        area2.setEditable(false);
        pan.add(area);
        pan2.add(area2);
        JButton but1=new JButton("Left");
        but1.addActionListener(act2());
        JButton but2=new JButton("Right");
        but2.addActionListener(act3());
        pan2.add(but1);
        pan2.add(but2);
        Font ft=new Font("monospaced",Font.PLAIN,12);
        area.setFont(ft);
        area.setText(Gameboard.displayBoard());
        area2.setFont(ft);
        area2.setText(Gameboard.displayBoard());
        pan.add(CreatePanel());
        frame.add(pan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame2.add(pan2);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.pack();
        frame2.setVisible(true);
        frame.setVisible(true);
        while(Gameboard.getDie1()==Gameboard.getDie2()){
        Gameboard.rollDie();
        }
        BoardSave.add(new Board(Gameboard));
        dice.setText(Gameboard.getDie1()+","+Gameboard.getDie2()+","+Gameboard.getDie3()+","+Gameboard.getDie4()); 
        if(Gameboard.getDie1()>Gameboard.getDie2()){
            Gameboard.nextturn();
            turn=!turn;
            turnlab.setText("Player X");
        }
        //AIGO();
    }
    public static void init(){
        turn=true;
        BoardSave=new ArrayList();
        Gameboard=new Board();
        while(Gameboard.getDie1()==Gameboard.getDie2()){
        Gameboard.rollDie();
        }
        BoardSave.add(new Board(Gameboard));
        if(Gameboard.getDie1()>Gameboard.getDie2()){
            Gameboard.nextturn();
            turn=!turn;
            turnlab.setText("Player X");
        }
    }
    public static JPanel CreatePanel(){
        JPanel pan=new JPanel(new GridLayout(5,2));
        JPanel pan2=new JPanel(new GridLayout(2,1));
        JButton but=new JButton("Move");
        JLabel label=new JLabel("To");
        JLabel label2=new JLabel("From");
         JLabel label3=new JLabel("dice");
         JLabel label5=new JLabel("Doubling Cube");
        JLabel label8=new JLabel("It is your turn");
        pan.add(turnlab);
        pan.add(label8);
        pan.add(label3);
        pan.add(dice);
        pan.add(label5);
        pan.add(cube);
        pan.add(label);
        pan.add(label2);
        pan.add(to);
        pan.add(from);
        pan2.add(pan);
        pan2.add(but);
        but.addActionListener(act());
        return pan2;
    }
    public static void endturncheck(){
        GameEnd();
        if (Gameboard.turnover()) {
            turn = !turn;
            if (turn) {turnlab.setText("Player O");} 
            else turnlab.setText("Player X");
            Doubled();
            Gameboard.rollDie();
            BoardSave.add(new Board(Gameboard));
            dice.setText(Gameboard.getDie1()+","+Gameboard.getDie2()+","+Gameboard.getDie3()+","+Gameboard.getDie4());
        }
        while(!Gameboard.canbearOff() && !Gameboard.canmove()){
            if(turn){
                JOptionPane.showMessageDialog(null, "Player O, you do not have a valid move, your turn is over.");}
            else JOptionPane.showMessageDialog(null, "Player X, you do not have a valid move, your turn is over.");
                turn=!turn;
                Gameboard.nextturn();
                Doubled();
                Gameboard.rollDie();
                BoardSave.add(new Board(Gameboard));
                dice.setText(Gameboard.getDie1()+","+Gameboard.getDie2()+","+Gameboard.getDie3()+","+Gameboard.getDie4());
            }
        if (turn) {turnlab.setText("Player O");} 
        else turnlab.setText("Player X");
    }
    public static ActionListener act2(){
        ActionListener action=(ActionEvent e) -> {
            if(location>0){
                location--;
                area2.setText(BoardSave.get(location).displayBoard()+location);
            }
        };return action;}
    public static ActionListener act3(){
        ActionListener action=(ActionEvent e) -> {
            if(location<BoardSave.size()-1){
                location++;
                area2.setText(BoardSave.get(location).displayBoard()+location);
            }
        };return action;}
public static ActionListener act(){
        ActionListener action=(ActionEvent e) -> {
            if(turn && POAI){
                Gameboard=AI(Gameboard);
            }
            else if(!turn && PXAI){
                Gameboard=AI(Gameboard);
            }
            else{
                Gameboard.move(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()));
            }
            dice.setText(Gameboard.getDie1()+","+Gameboard.getDie2()+","+Gameboard.getDie3()+","+Gameboard.getDie4());
            area.setText(Gameboard.displayBoard());
            endturncheck();
        };
        return action;
    }
    public static void Doubled(){
        if(doublestate==0){
            if(POAI){}
            else if(JOptionPane.showConfirmDialog(null,"Player O, would you like to double","Double Cube",JOptionPane.YES_NO_OPTION)==0){
                doublestate=2;
                Gameboard.doubledown();
                cube.setText(String.valueOf(Gameboard.getCube()));
                return;
            }
            if(PXAI){}
            else if(JOptionPane.showConfirmDialog(null,"Player X, would you like to double","Double Cube",JOptionPane.YES_NO_OPTION)==0){
                doublestate=1;
                Gameboard.doubledown();
                cube.setText(String.valueOf(Gameboard.getCube()));
                return;
            }
        }
        else if(doublestate==1){
            if(POAI){}
            else if(JOptionPane.showConfirmDialog(null,"Player O, would you like to double","Double Cube",JOptionPane.YES_NO_OPTION)==0){
                doublestate=2;
                Gameboard.doubledown();
                cube.setText(String.valueOf(Gameboard.getCube()));
                return;
            }
        }
        else
        {
            if(PXAI){}
            else if(JOptionPane.showConfirmDialog(null,"Player X, would you like to double","Double Cube",JOptionPane.YES_NO_OPTION)==0){
                doublestate=1;
                Gameboard.doubledown();
                cube.setText(String.valueOf(Gameboard.getCube()));
                return;
            }
        }
    }
    
    public static Board AI(Board g){
        if(!g.canmove() && !g.canbearOff()){
            return g;
        }
        ArrayList<Board> b=new ArrayList();
        ArrayList<MoveCont> ml=g.Movecalc();
        for(MoveCont m:ml){
            Board temp=new Board(g);
            temp.move(m.getFrom(), m.getTo());
            b.add(AI(temp));
        }
        int tempe;
        int x=0;
        if(b.isEmpty()){g.print();}
        else{
        int maxscore=b.get(0).ReturnScore();
        if(turn){
        for(int i=1; i<b.size();i++){
            tempe=b.get(i).ReturnScore();
            if(tempe>maxscore){
                x=i;maxscore=tempe;
            }
        }
        }
        else {
            for(int i=1; i<b.size();i++){
            tempe=b.get(i).ReturnScore();
            if(tempe<maxscore){
                x=i;maxscore=tempe;
            }
        }
        }
        return b.get(x);
    }
        return g;
    }
    
public static void GameEnd(){
    if(Gameboard.GameOver()){
        init();
    }
}


}

