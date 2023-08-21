import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {
    private int targetNumber;
    private int maxAttempts = 10;
    private int attempts = 0;
    private int totalScore = 0;
    private int currentRound = 1;
    private final int totalRounds = 3;

    private final int lowerBound = 1;
    private final int upperBound = 100;

    private JTextField guessTextField;
    private JLabel messageLabel;
    private JButton guessButton;
    private JButton exitButton;
    private JButton replayButton;
    private JLabel resultLabel;
    private JLabel scoreLabel;
    private JLabel attemptsLabel;
    private JLabel roundLabel;

    private JPanel scoreBoardPanel; // Score board panel
    private ArrayList<JLabel> scoreLabels; // Labels for each round's score
    private JLabel totalScoreLabel; // Total score label

    private Random random = new Random();
    private int roundsPlayed = 0;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));

        messageLabel = new JLabel("Round " + currentRound + ": I'm thinking of a number between "
                + lowerBound + " and " + upperBound);
        add(messageLabel);

        guessTextField = new JTextField(10);
        guessButton = new JButton("Guess");
        exitButton = new JButton("Exit");
        replayButton = new JButton("Replay");
        replayButton.setEnabled(false);
        resultLabel = new JLabel("");
        scoreLabel = new JLabel("Total Score: " + totalScore);
        attemptsLabel = new JLabel("Attempts left: " + (maxAttempts - attempts));
        roundLabel = new JLabel("Round " + currentRound);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Your Guess: "));
        inputPanel.add(guessTextField);
        inputPanel.add(guessButton);
        inputPanel.add(exitButton);
        inputPanel.add(replayButton);
        add(inputPanel);

        add(resultLabel);
        add(scoreLabel);
        add(attemptsLabel);
        add(roundLabel);

        scoreBoardPanel = new JPanel(new GridLayout(1, totalRounds + 2));
        add(scoreBoardPanel);

        scoreLabels = new ArrayList<>();
        for (int i = 1; i <= totalRounds; i++) {
            JLabel label = new JLabel("Round " + i + ": 0");
            scoreLabels.add(label);
            scoreBoardPanel.add(label);
        }

        totalScoreLabel = new JLabel("Total: 0");
        scoreBoardPanel.add(totalScoreLabel);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userGuess;
                try {
                    userGuess = Integer.parseInt(guessTextField.getText());
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                    return;
                }

                attempts++;

                if (userGuess == targetNumber) {
                    resultLabel.setText("Congratulations! You guessed the number in " + attempts + " attempts.");
                    int roundScore = maxAttempts - attempts + 1;
                    totalScore += roundScore;
                    scoreLabel.setText("Total Score: " + totalScore);
                    scoreLabels.get(currentRound - 1).setText("Round " + currentRound + ": " + roundScore);

                    if (currentRound < totalRounds) {
                        replayButton.setEnabled(true);
                    } else {
                        replayButton.setEnabled(true);
                    }

                    guessButton.setEnabled(false);
                    exitButton.setEnabled(true);
                    totalScoreLabel.setText("Total: " + totalScore);
                } else if (userGuess < targetNumber) {
                    resultLabel.setText("Try a higher number.");
                } else {
                    resultLabel.setText("Try a lower number.");
                }

                if (attempts == maxAttempts) {
                    resultLabel.setText("Sorry, you've reached the maximum number of attempts. The number was "
                            + targetNumber + ".");
                    replayButton.setEnabled(true);
                    guessButton.setEnabled(false);
                    exitButton.setEnabled(true);
                }

                attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roundsPlayed > 0) {
                    showFinalScore();
                }
                System.exit(0);
            }
        });

        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roundsPlayed++;
                currentRound++;
                if (currentRound <= totalRounds) {
                    startNewRound();
                } else {
                    showFinalScore();
                }
            }
        });

        startNewRound();
    }

    private void startNewRound() {
        targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        attempts = 0;
        messageLabel.setText("Round " + currentRound + ": Number is in Between "
                + lowerBound + " and " + upperBound);
        resultLabel.setText("");
        attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));
        roundLabel.setText("Round " + currentRound);
        guessButton.setEnabled(true);
        replayButton.setEnabled(false);
        exitButton.setEnabled(false);
    }

    private void showFinalScore() {
        StringBuilder finalScoreMessage = new StringBuilder();
        finalScoreMessage.append("Game Over! Rounds Played: ").append(roundsPlayed).append("\n");
        finalScoreMessage.append("Total Score: ").append(totalScore).append("\n");
        JOptionPane.showMessageDialog(null, finalScoreMessage.toString());
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NumberGuessingGameGUI gameGUI = new NumberGuessingGameGUI();
                gameGUI.setVisible(true);
                gameGUI.setLocationRelativeTo(null);
            }
        });
    }
}
