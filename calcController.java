package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class calcController
{
    private static double result = 0; // result of math operation
    private static double firstNumber = 0; // first nr in math operation
    private static double secondNumber = 0; // second nr in math operation
    private static byte whatToDo = -1;  // -1 - start, 0 - nothing, 1 - divide, 2 - multiply, 3 - minus, 4 - plus
    private static boolean isCommaPresent = false;
    private static boolean isNotCommaPresent = false; // is number present
    private static boolean isResultJustDisplayed = false;

    @FXML
    private TextField display; // display of calc

    @FXML //function for key handling
    void keyPressed(KeyEvent event)
    {
        String key = event.getCode().toString();
        //System.out.println(key);
        switch (key) {
            case "NUMPAD0": case "DIGIT0":
                numberButtonFunction((byte)0);
                break;
            case "NUMPAD1": case "DIGIT1":
                numberButtonFunction((byte)1);
                break;
            case "NUMPAD2":  case "DIGIT2":
                numberButtonFunction((byte)2);
                break;
            case "NUMPAD3":  case "DIGIT3":
                numberButtonFunction((byte)3);
                break;
            case "NUMPAD4":  case "DIGIT4":
                numberButtonFunction((byte)4);
                break;
            case "NUMPAD5": case "DIGIT5":
                numberButtonFunction((byte)5);
                break;
            case "NUMPAD6": case "DIGIT6":
                numberButtonFunction((byte)6);
                break;
            case "NUMPAD7": case "DIGIT7":
                numberButtonFunction((byte)7);
                break;
            case "NUMPAD8": case "DIGIT8":
                numberButtonFunction((byte)8);
                break;
            case "NUMPAD9": case "DIGIT9":
                numberButtonFunction((byte)9);
                break;
            case "ADD":
                plusFunction();
                break;
            case "ENTER":
                doResult();
                break;
            case "SUBTRACT":
                minusFunction();
                break;
            case "DIVIDE":
                divideFunction();
                break;
            case "MULTIPLY":
                multiplyFunction();
                break;
            case "ESCAPE":
                cFunction();
                break;
            case "DECIMAL":
                commaFunction();
                break;
        }
    }

    /*--------------------numbers----------------*/
    @FXML //  function for numbers buttons
    public void numberButtonPressed(ActionEvent event) {
        Button button = (Button) event.getSource();
        numberButtonFunction(Byte.parseByte(button.getText()));
    }

    // function for adding numbers to display
    private void numberButtonFunction(byte x)
    {
        /*System.out.println("whatToDo: " + whatToDo);
        System.out.println("isCommaPresent: " + isCommaPresent);
        System.out.println("isNotCommaPresent: " + isNotCommaPresent);*/
        String temp = display.getText();
        if (!isResultJustDisplayed)
        {
            if (whatToDo != 0)
                    {
                        if (!temp.equals("0"))
                        {
                            display.setText(temp + x);
                            isNotCommaPresent = true;
                        } else
                        {
                            display.setText(Byte.toString(x));
                            if(x != 0)
                                isNotCommaPresent = true;
                        }
                    } else if(isNotCommaPresent)
                    {
                        display.setText(temp + x);
                        isNotCommaPresent = true;
                    } else
                    {
                        display.setText(Byte.toString(x));
                        if(x != 0)
                            isNotCommaPresent = true;
                    }
        } else
        {
            display.setText(Byte.toString(x));
            isResultJustDisplayed = false;
        }
    }

    /*---------------------numbers end-------------------------*/

    @FXML
    void cPressed(ActionEvent event)
    {
        cFunction();

    }
    private void cFunction()
    {
        display.setText("0");
        result = 0;
        isCommaPresent = false;
        isNotCommaPresent = false;
        firstNumber = 0;
    }

    @FXML
    void dividePressed(ActionEvent event) {
        divideFunction();
    }
    private void divideFunction()
    {
        secondNumber = toNumber(display.getText());
        resultFunction();
        whatToDo = 1;
        displayResult();
    }

    @FXML
    void multiplyPressed(ActionEvent event) {
        multiplyFunction();
    }
    private void multiplyFunction()
    {
        secondNumber = toNumber(display.getText());
        resultFunction();
        whatToDo = 2;
        displayResult();
    }

    @FXML
    void minusPressed(ActionEvent event) {
        minusFunction();
    }
    private void minusFunction()
    {
        secondNumber = toNumber(display.getText());
        resultFunction();
        whatToDo = 3;
        displayResult();
    }

    @FXML
    void plusPressed(ActionEvent event)
    {
        plusFunction();
    }
    private void plusFunction()
    {
        secondNumber = toNumber(display.getText());
        resultFunction();
        whatToDo = 4;
        displayResult();
    }

    @FXML
    void commaPressed(ActionEvent event)
    {
        commaFunction();
    }
    private void commaFunction()
    {
        /*System.out.println("Coma whatToDo: " + whatToDo);
        System.out.println("Coma isCommaPresent: " + isCommaPresent);
        System.out.println("Coma isNotCommaPresent: " + isNotCommaPresent);*/
        String temp = display.getText();
        if (!isResultJustDisplayed)
        {
            if(whatToDo !=0) //if previous operation not completed
                    {
                        if (!isCommaPresent)
                        {
                            if (!temp.equals("0"))
                            {
                                display.setText(temp + ",");
                                isNotCommaPresent = true;
                            } else
                            {
                                display.setText("0" + ",");
                                isNotCommaPresent = true;
                            }
                        }

                    } else if(isNotCommaPresent)
                    {
                        display.setText(temp + ",");
                        isNotCommaPresent = true;
                    } else
                    {
                        result = 0;
                        display.setText("0" + ",");
                        isNotCommaPresent = true;
                    }
        } else
        {
            display.setText("0" + ",");
            isResultJustDisplayed = false;
        }

        isCommaPresent = true;
    }

    @FXML
    void resultPressed(ActionEvent event)
    {
        doResult();
    }
    private void doResult()
    {
        secondNumber = toNumber(display.getText());
        resultFunction();
        displayResult();
    }
    private void displayResult() //display result
    {
        String temp = prettyPrint(result);
        char[] charArray = temp.toCharArray();
        //change . to ,
        for (int i = 0; i < charArray.length; i++)
        {
            if (charArray[i] == '.')
                charArray[i] = ',';
        }
        display.setText(new String(charArray));
        isResultJustDisplayed = true;
    }
    private void resultFunction()
    {
        /*System.out.println("resultFunction - whatToDo: " + whatToDo);
        System.out.println("resultFunction - firstNumber: " + firstNumber);
        System.out.println("resultFunction - secondNumber " + secondNumber);*/
        switch (whatToDo)
        {
            case -1: //start
                firstNumber = secondNumber;
                break;
            case 0:
                break;
            case 1: //divide
                result = firstNumber / secondNumber;
                break;
            case 2: //multiply
                result = firstNumber * secondNumber;
                break;
            case 3: //minus
                result = firstNumber - secondNumber;
                break;
            case 4: //plus
                result = firstNumber + secondNumber;
                break;
        }

        isCommaPresent = false;

        displayResult();

        isNotCommaPresent = false;
        if(whatToDo != -1)
            firstNumber = result;
    }

    double toNumber(String text)
    {
        char[] charArray = text.toCharArray();
        //change , to .
        for (int i = 0; i < charArray.length; i++)
        {
            if (charArray[i] == ',')
                charArray[i] = '.';
        }
        return Double.parseDouble(new String(charArray));
    }

    //get rid of 0 after .
    public static String prettyPrint(double d) {
        long i = (long) d;
        return d == i ? String.valueOf(i) : String.valueOf(d);
    }
}
