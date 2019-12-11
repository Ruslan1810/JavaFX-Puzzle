package javafx1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextArea puzzle_area;
    @FXML
    private TextArea answer_area;
    @FXML
    private Button button_send;
    String answer;
    String puzzle_file;
    String answer_file;
    ArrayList<Puzzle> ar_str;

    @FXML
    private TextField false_true_area;
    @FXML
    private Button button_next;
    int count;
    int count_right_answ;
    @FXML
    private Label count_right_answer;
    @FXML
    private MenuItem menu_close;
    @FXML
    private MenuItem menu_open;
    @FXML
    private RadioMenuItem menu_save;
    @FXML
    private AnchorPane myAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        count_right_answ = 0;
        try {
            ar_str = new ArrayList<>();
            File file = new File("puzzle.txt");
            Scanner sc = new Scanner(file);
            //считываем данные из файла и добавляем их в списочный массив
            while (sc.hasNextLine()) {
                puzzle_file = sc.nextLine();
                answer_file = sc.nextLine();
                ar_str.add(new Puzzle(puzzle_file, answer_file));
            }

            for (int i = 0; i < ar_str.size(); i++) {
                System.out.print(i);
                System.out.print(" элемент массива");
                System.out.println();
                System.out.println(ar_str.get(i).question);
                System.out.println(ar_str.get(i).answer);
                System.out.println();
            }
            count = 0;//переменная для элеметов листа
            //добавляем вопрос в окно вопросов
            puzzle_area.setText(ar_str.get(count).question);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void answer_question(MouseEvent event) {
        //считываем данные из окна ответов
        answer = answer_area.getText();
        //проверяем ответ на строку
        Scanner sc = new Scanner(answer);
        if (sc.hasNextLine() == true) {
            //сравниваем данные из окна ответов с данными из списочного массива
            if (answer.equals(ar_str.get(count).answer)) {
                false_true_area.setText("TRUE");
                count_right_answ++;
                count_right_answer.setText(Integer.toString(count_right_answ));
            } else {
                false_true_area.setText("FALSE");
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Введите ответ");
            alert.setContentText("Введите значение");
            alert.showAndWait();
        }
    }

    @FXML
    private void next_question(MouseEvent event) {
        count = count + 1;

        puzzle_area.setText(ar_str.get(count).question);

    }

    @FXML
    private void menu_close_action(ActionEvent event) {
    }

    @FXML
    private void menu_open_action(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //добыть сцену из основного элемента AnchorPane нашего окна
        Stage stage = (Stage) myAnchorPane.getScene().getWindow();

        //для этой сцены stage запустить диалоговое окно save или open
        File file = fileChooser.showOpenDialog(stage);

        try {
            if (file != null) {

                //считывание новой загадки из файла
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    puzzle_file = sc.nextLine();
                    answer_file = sc.nextLine();
                    ar_str.add(0, new Puzzle(puzzle_file, answer_file));
                    //запись новой загадки в файл с загадками
                    /*PrintWriter pw = new PrintWriter("puzzle.txt");
                    pw.print(puzzle_file);
                    pw.println();
                    pw.print(answer_file);
                    pw.close();
                     */
                }
                puzzle_area.setText(ar_str.get(0).question);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void menu_save_action(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //добыть сцену из основного элемента AnchorPane нашего окна
        Stage stage = (Stage) myAnchorPane.getScene().getWindow();

        //для этой сцены stage запустить диалоговое окно save или open
        File file = fileChooser.showSaveDialog(stage);

        try {
            if (file != null) {
                PrintWriter pw = new PrintWriter(file);
                pw.print(puzzle_area.getText());
                pw.println();
                pw.print(answer);
                pw.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

class Puzzle {

    String question;
    String answer;

    public Puzzle(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
