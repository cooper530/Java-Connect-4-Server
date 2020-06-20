import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game {
    Board board = new Board();
    Player currentPlayer;

    public synchronized void updateBoard(int col, Player player)
    {
        board.update(col, player.player);
        currentPlayer = currentPlayer.opponent;
    }

    class Player implements Runnable {
        Socket socket;
        Scanner input;
        PrintWriter output;
        String name;
        String playerColor;
        Player opponent;
        int player;

        public Player(Socket socket, int player) {
            this.socket = socket;
            this.player = player;
        }

        @Override
        public void run() {
            try {
                setupPlayer();
                processCommands();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(opponent != null && opponent.output != null)
                {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch(IOException ignored) {}
            }
        }

        public void setupPlayer() throws IOException, InterruptedException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            this.name = input.nextLine();
            this.playerColor = input.nextLine();
            System.out.println("Client connected");
            output.println("MESSAGE Connected to server");
            if (player == 1) {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                String opponentName = currentPlayer.name;
                opponent.output.println("CHIP_COLOR " + this.playerColor);
                output.println("CHIP_COLOR " + opponent.playerColor);
                currentPlayer.output.println("YOUR_MOVE " + -1);
                currentPlayer.opponent.output.println("MESSAGE It is " + opponentName + "'s turn");
            }
        }

        public void processCommands() throws IOException {
            while (input.hasNextLine()) {
                String command = input.nextLine();
                if (command.startsWith("UPDATE_BOARD")) {
                    updateBoard(Integer.parseInt(command.substring(13)), this);
                    if(board.checkBoard()) {
                        this.output.println("VICTORY");
                        this.opponent.output.println("DEFEAT " + Integer.parseInt(command.substring(13)));
                        socket.close();
                        opponent.socket.close();
                        board.clearBoard();
                    } else if(board.isFull()) {
                        this.output.println("TIE");
                        this.opponent.output.println("TIE " + Integer.parseInt(command.substring(13)));
                        socket.close();
                        opponent.socket.close();
                        board.clearBoard();
                    } else {
                        String opponentName = currentPlayer.name;
                        currentPlayer.opponent.output.println("MESSAGE It is " + opponentName + "'s turn");
                        currentPlayer.output.println("YOUR_MOVE " + Integer.parseInt(command.substring(13)));
                    }
                }
            }
        }
    }
}