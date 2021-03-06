package Domain;



import Domain.Player.Player;

import java.time.Instant;

import static Domain.Problem.*;


public class Game {
    //private GameInterface gameInterface;
    private int number_of_play;
    private Table table;
    private boolean player_who_plays;
    private boolean player_who_has_to_win;
    private Player player1;
    private Player player2;
    private Instant start;
    private Instant end;
    private String FEN;
    public Game () {
    }

    public String getFEN() {
        return FEN;
    }



    public void prepareTablewithFEN(String FEN){
        this.FEN = FEN;
        number_of_play = ConvertInputtonumber_of_play(FEN)*2;
        player_who_plays = ConvertInputtoplayer_who_start(FEN);
        table = new Table(FEN);
        player_who_has_to_win = ConvertInputtoplayer_who_has_to_win(FEN);
    }
    public void prepareTablewithParameters(String FEN, boolean player_who_start, boolean player_who_has_to_win, int number_of_play){
        this.FEN = convertParameterstoFEN(FEN,player_who_start,player_who_has_to_win,number_of_play);
        table = new Table(FEN);
        player_who_plays = player_who_start;
        this.player_who_has_to_win = player_who_has_to_win;
        this.number_of_play = number_of_play*2;
    }
    public boolean Piececanmoveinthismoment(int i_origen, int j_origen){
        return table.getTable()[i_origen][j_origen].getPiece() != null && table.getTable()[i_origen][j_origen].getPiece().getColor() == player_who_plays;

    }
    public boolean getPlayer_who_plays() {
        return player_who_plays;
    }
    /*
    public void graphic_move_and_internal_move(int i_origen, int j_origen, int i_destino, int j_destino){
        if (!table.CorrectMove(i_origen, j_origen, i_destino, j_destino)) return;
        gameInterface.getChessBoard()[i_destino][j_destino].setIcon(gameInterface.getChessBoard()[i_origen][j_origen].getIcon());
        gameInterface.getChessBoard()[i_origen][j_origen].setIcon(null);
        gameInterface.getChessBoard()[i_origen][j_origen].paint_cell();
        player_who_plays = !player_who_plays;
        number_of_play = number_of_play-1;
        gameInterface.paint_info();
        try {
            endofgame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    public boolean getPlayer_who_has_to_win() {
        return player_who_has_to_win;
    }

    public int getNumber_of_play() {
        return number_of_play;
    }

    public void setNumber_of_play(int number_of_play) {
        this.number_of_play = number_of_play;
        //gameInterface.setNumber_of_plays_label(number_of_play);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }


    public void setPlayer_who_plays(boolean player_who_plays) {
        this.player_who_plays = player_who_plays;
        //gameInterface.setPlayer_who_plays_label(player_who_plays);
    }
    /*
    public void setGameInterface(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }
    */
    public void setPlayer_who_has_to_win(boolean player_who_has_to_win) {
        this.player_who_has_to_win = player_who_has_to_win;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    //revisar si funciona correctamente endofgame
    public boolean endofgame(){
        return number_of_play <= 0 || table.king_position(true) == null || table.king_position(false) == null || table.checkmate(true,player_who_plays) || table.checkmate(false,player_who_plays);
    }

    public Player getPlayerwhoplays(){
        if (player_who_plays) return player1;
        else return player2;
    }
    public void startchronometer(){
        start =  Instant.now();
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }
}
