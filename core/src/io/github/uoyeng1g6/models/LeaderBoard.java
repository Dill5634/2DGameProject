package io.github.uoyeng1g6.models;

import java.io.FileWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LeaderBoard {
    private String read;

    private ArrayList<Entry> board = new ArrayList<Entry>();


    public LeaderBoard(){
        try {
            // creates filler leaderboard on first run
            File notes = new File("leaderboard.txt");
            if (notes.createNewFile()) {
                for(int x = 0; x<10;x++){
                    board.add(new Entry("null",0f));
                }
                // writes new filler leaderboard oto
                write();
            } else {
                readAndAssign(notes);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    public boolean insert(String name,float score){

        if(score < board.get(9).getScore()) return false;


        Entry currentGame = new Entry(name,score);
        board.add(currentGame);
        board.sort(
                (b, a) -> a.getScore().compareTo(b.getScore()));
        board.remove(10);
        write();


        return true;
    }



    public String[] show(int x){
        String score2Decimals = String.format("%.2f", board.get(x).getScore());
        return new String[]{board.get(x).getNickname(), score2Decimals};

    }

    // reads leaderboard from file
    private void readAndAssign(File notes){
        try {
            Scanner reader = new Scanner(notes);
            board = new ArrayList<>();
            for(int x= 0;x<10;x++){

                String[] nameAndScore = reader.nextLine().split(" ");
                board.add(new Entry(nameAndScore[0], Float.parseFloat(nameAndScore[1])));

            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }
    // writes new leaderboard to file
    private void write(){
        StringBuilder toBeWritten = new StringBuilder();
        for(Entry x: this.board){
            toBeWritten.append(x.getNickname()).append(" ").append(x.getScore());
            toBeWritten.append(System.lineSeparator());

        }
        try {

            FileWriter writer = new FileWriter("leaderboard.txt",false);
            writer.write(toBeWritten.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }



    public class Entry {

        public Entry(String nickname, Float score){
            this.nickname = nickname;
            this.score = score;


        }
        public Entry(Float score){
            this.nickname = "YOU";
            this.score = score;


        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        String nickname;
        Float score;

        public Float getScore() {
            return score;
        }

        public String getNickname() {
            return nickname;
        }
    }
}


