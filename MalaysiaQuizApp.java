import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * MalaysiaQuizApp
 * MCQ quiz about Malaysia
 */

public class MalaysiaQuizApp {

    String title = "Malaysia Quiz";

    private JFrame frame;
    private JLabel questionLabel, resultLabel, scoreLabel;
    private JRadioButton opt1, opt2, opt3;
    private ButtonGroup group;
    private JButton nextButton, doneButton;

    private int currentQuestion = 0;
    private int score = 0;
    private int correctCount = 0;

    private String[][] quiz = {
            {"What is the capital of Malaysia?", "Kuala Lumpur", "Penang", "Johor Bahru", "Kuala Lumpur"},
            {"Which state is famous for nasi lemak?", "Sabah", "Selangor", "All states", "All states"},
            {"What is Malaysia's national animal?", "Tiger", "Elephant", "Lion", "Tiger"},
            {"Which city is known as Silicon Valley of Malaysia?", "Cyberjaya", "Ipoh", "Melaka", "Cyberjaya"},
            {"What is the national language of Malaysia?", "English", "Malay", "Chinese", "Malay"}
        };

    public MalaysiaQuizApp() {
        createGUI();
        loadQuestion();
    }

    private void createGUI() {
        frame = new JFrame("Malaysia Quiz Game");
        frame.setSize(450, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        scoreLabel = new JLabel("Score: 0");
        questionLabel = new JLabel("");

        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();

        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);

        nextButton = new JButton("Next");
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        resultLabel.setForeground(Color.BLUE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(scoreLabel);
        panel.add(questionLabel);
        panel.add(opt1);
        panel.add(opt2);
        panel.add(opt3);
        panel.add(nextButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);

        // Anonymous class (event handling)
        nextButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {

        // Get selected answer
        String selectedAnswer = "";
        if (opt1.isSelected()) selectedAnswer = opt1.getText();
        if (opt2.isSelected()) selectedAnswer = opt2.getText();
        if (opt3.isSelected()) selectedAnswer = opt3.getText();

        // Check if user selected an option
        if (selectedAnswer.equals("")) {
            resultLabel.setText("Please select an answer!");
            resultLabel.setForeground(Color.RED); // bold red
            return;
        }

        // Check answer
        AnswerChecker checker = new AnswerChecker();
        boolean isCorrect = checker.isCorrect(selectedAnswer);

        // Update score
        score += isCorrect ? 10 : 0;
        if (isCorrect) {
            correctCount++;
            resultLabel.setText("Correct!");
            resultLabel.setForeground(new Color(0, 128, 0)); // dark green
        } else {
            resultLabel.setText("Wrong!");
            resultLabel.setForeground(Color.RED);
        }

        scoreLabel.setText("Score: " + score);

        currentQuestion++;

        // Check if quiz finished
        if (currentQuestion < quiz.length) {
            loadQuestion();
            group.clearSelection();
        } else {
            showFinalScreen(); // only updates same frame
        }
    }
});

        frame.setVisible(true);
    }

    // Load question
    private void loadQuestion() {
        questionLabel.setText(quiz[currentQuestion][0]);
        opt1.setText(quiz[currentQuestion][1]);
        opt2.setText(quiz[currentQuestion][2]);
        opt3.setText(quiz[currentQuestion][3]);
    }

    private void showFinalScreen() {
        int totalQuestions = quiz.length;
        double percentage = ((double) correctCount / totalQuestions) * 100;

        // Clear frame
        frame.getContentPane().removeAll();

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new GridLayout(5, 1));

        JLabel finishLabel = new JLabel("Quiz Finished!");
        JLabel correctLabel = new JLabel("Correct Answers: " + correctCount + "/" + totalQuestions);
        JLabel scoreFinal = new JLabel("Total Score: " + score);
        JLabel percentLabel = new JLabel("Percentage: " + String.format("%.2f", percentage) + "%");

        doneButton = new JButton("Done");

        finalPanel.add(finishLabel);
        finalPanel.add(correctLabel);
        finalPanel.add(scoreFinal);
        finalPanel.add(percentLabel);
        finalPanel.add(doneButton);

        frame.add(finalPanel);

        // Anonymous class for Done button
        doneButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.dispose(); // close window
                }
            });

        frame.revalidate();
        frame.repaint();
    }

    // Member inner class
    class AnswerChecker {
        boolean isCorrect(String answer) {
            return answer.equals(quiz[currentQuestion][4]);
        }
    }

    // Shadowing
    class InnerShadow {
        String title = "Inner Quiz Title";

        void showTitle() {
            System.out.println("Inner: " + title);
            System.out.println("Outer: " + MalaysiaQuizApp.this.title);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new MalaysiaQuizApp();
                }
            });
    }
}