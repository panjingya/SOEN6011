package com.gammaCalculator.impl;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**************************************
  The calculator for the gamma function.
 
  @author Jingya Pan
  @version 1.1
*/
public class MainFrame extends JFrame {

  /**panel to contain components. */
  private JPanel contentPane;

  /** text field to specify the input value. */
  private static JTextField textFieldInput;

  /** text field to specify the error message. */
  private JTextField txtError;

  /** button group to assemble the radio button. */
  private ButtonGroup g1;
  
  /** button0. */
  private JButton button0;
  
  /** button1. */
  private JButton button1;
  
  /** button2. */
  private JButton button2;
  
  /** button3. */
  private JButton button3;
  
  /** button4. */
  private JButton button4;
  
  /** button5. */
  private JButton button5;
  
  /** button6. */
  private JButton button6;
  
  /** button7. */
  private JButton button7;
  
  /** button8. */
  private JButton button8;
  
  /** button9. */
  private JButton button9;
  
  /** button for addition. */
  private JButton buttonAddition;
  
  /** button for subtraction. */
  private JButton buttonSubtraction;
  
  /** button for multiply. */
  private JButton buttonMultiply;
  
  /** button for division. */
  private JButton buttonDivision;
  
  /** button for the dot sign. */
  private JButton buttonDot;
  
  /** button for the equal sign. */
  private JButton buttonEqual;
  
  /** button for the back sign. */
  private JButton btnBack;
  
  /** label for arithmetic use. */
  private JLabel lbl;
  
  /** variable used in arithmeticOperation method. */
  double num;
  
  /** variable used in arithmeticOperation method. */
  double ans;
  
  /** condition variables used in arithmeticOperation method. */
  int calculation;
  
  /***************************************************************************************
   * Use calculation variable to check the calculation type.
   * 
   */
  public void arithmeticOperation(String str) {
    switch (calculation) {
      case 1://Addition
        ans = num + Double.parseDouble(str);
        textFieldInput.setText(Double.toString(ans));
        break;
        
      case 2://Subtraction
        ans = num - Double.parseDouble(str);
        textFieldInput.setText(Double.toString(ans));
        break;

      case 3://Multiply
        ans = num * Double.parseDouble(str);
        textFieldInput.setText(Double.toString(ans));
        break;
        
      case 4://Division
        ans = num / Double.parseDouble(str);
        textFieldInput.setText(Double.toString(ans));
        break;
        
      case 5://Gamma
        ans = lanczosGamma(Double.parseDouble(str));
        textFieldInput.setText(Double.toString(ans));
        break;
        
      default:
        txtError.setText("unsure calculation type");
        txtError.setVisible(true);
        break;
    }
  }

  /***************************************************************************************
  Check double is integer or not. 
  Eg: 4.0 is actually an integer.
 
  @param d input value
  @return boolean
  */
  public static boolean isNegativeIntegerForDouble(double d) {
    double eps = 1e-10;
    return d - Math.floor(d) < eps && d < 0;
  }


  /***************************************************************************************
  Check whether the input is a number.
 
  @param str input value
  @return boolean
  */
  public static boolean isNumeric(String str) {
    String checkString;
    try {
      checkString = new BigDecimal(str).toString();
    } catch (Exception e) {
      return false;
    }
    return true;
  }


  /***************************************************************************************
  Lanczos approximation for Gamma function.
 
  @param d input value
  @return double
  */
  public static double lanczosGamma(double d) {
    double[] coefficientP = {0.99999999999980993, 676.5203681218851, -1259.1392167224028,
                             771.32342877765313, -176.61502916214059, 12.507343278686905,
                             -0.13857109526572012, 9.9843695780195716e-6, 1.5056327351493116e-7};
    int g = 7;
    if (d < 0.5) {
      return Math.PI / (Math.sin(Math.PI * d) * lanczosGamma(1 - d));
    }
    d -= 1;
    double a = coefficientP[0];
    double t = d + g + 0.5;
    for (int i = 1; i < coefficientP.length; i++) {
      a += coefficientP[i] / (d + i);
    }

    return Math.sqrt(2 * Math.PI) * Math.pow(t, d + 0.5) * Math.exp(-t) * a;
  }


  /***************************************************************************************
  Launch the application.

  */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          MainFrame frame = new MainFrame();

          // textField get focus
          frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
              textFieldInput.requestFocusInWindow();
            }
          });
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /***************************************************************************************
  Create the frame.

  */
  public MainFrame() {
    setResizable(false);
    setFont(new Font("Dialog", Font.BOLD, 14));
    setTitle("Calculator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 395, 405);
    getContentPane().setLayout(null);

    textFieldInput = new JTextField();
    textFieldInput.setHorizontalAlignment(SwingConstants.RIGHT);
    textFieldInput.setFont(new Font("Tahoma", Font.BOLD, 16));
    textFieldInput.setBounds(21, 24, 336, 43);
    textFieldInput.setBorder(null);
    getContentPane().add(textFieldInput);
    textFieldInput.setColumns(10);

    JButton btnGamma = new JButton("Gamma");

    // Add action to the execute button
    btnGamma.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        // clear error message
        txtError.setVisible(false);

        // get focus
        textFieldInput.requestFocus();
        String str = textFieldInput.getText();

        // Check whether it is empty, else if Check if it is a number,else not a number
        if (str.contains(" ") || str.equals("")) {
          txtError.setText("*please input a real number except negative integer");
          txtError.setVisible(true);
        } else if (isNumeric(str)) {
          if (str.matches("^-[0-9]*[1-9][0-9]*$")
              || isNegativeIntegerForDouble(Double.parseDouble(str))) {
            txtError.setText("*Negative integer is not acceptable, try other numbers");
            txtError.setVisible(true);
          } else {
            //textFieldInput.setText(String.valueOf(lanczosGamma(Double.parseDouble(str))));
            num =  Double.parseDouble(textFieldInput.getText());
            calculation = 5;
            lbl.setText(" Γ(" + num + ")");
            arithmeticOperation(textFieldInput.getText());
            
          }
        } else {
          txtError.setText("*please input a real number except negative integer");
          txtError.setVisible(true);
        }
      }
    });
    btnGamma.setFont(new Font("Tahoma", Font.BOLD, 11));
    btnGamma.setBounds(229, 235, 128, 35);
    getContentPane().add(btnGamma);

    JButton btnClear = new JButton("Clear");

    // Add action to the clear button
    btnClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        lbl.setText("");
        textFieldInput.setText("");
        textFieldInput.requestFocus();
        textFieldInput.setText("");
        txtError.setVisible(false);
      }
    });
    btnClear.setFont(new Font("Tahoma", Font.BOLD, 11));
    btnClear.setBounds(21, 78, 72, 45);
    getContentPane().add(btnClear);

    txtError = new JTextField();
    txtError.setVisible(false);
    txtError.setBorder(null);
    txtError.setFont(new Font("Tahoma", Font.PLAIN, 14));
    txtError.setEditable(false);
    txtError.setText("*Negative integer is not acceptable, try other numbers");
    txtError.setBounds(21, 335, 338, 23);
    getContentPane().add(txtError);
    txtError.setColumns(10);

    JRadioButton rdbtnOn = new JRadioButton("On");
    rdbtnOn.setSelected(true);

    // radio button on
    rdbtnOn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setEnabled(true);
        textFieldInput.requestFocusInWindow();
        btnGamma.setEnabled(true);
        btnClear.setEnabled(true);
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);
        button5.setEnabled(true);
        button6.setEnabled(true);
        button7.setEnabled(true);
        button8.setEnabled(true);
        button9.setEnabled(true);
        buttonAddition.setEnabled(true);
        buttonSubtraction.setEnabled(true);
        buttonMultiply.setEnabled(true);
        buttonDivision.setEnabled(true);
        buttonDot.setEnabled(true);
        buttonEqual.setEnabled(true);
        btnBack.setEnabled(true);
      }
    });
    rdbtnOn.setFont(new Font("Tahoma", Font.BOLD, 14));
    rdbtnOn.setBounds(310, 74, 47, 23);
    getContentPane().add(rdbtnOn);

    JRadioButton rdbtnOff = new JRadioButton("Off");

    // radio button off
    rdbtnOff.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText("");
        textFieldInput.setEnabled(false);
        btnGamma.setEnabled(false);
        btnClear.setEnabled(false);
        button0.setEnabled(false);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button5.setEnabled(false);
        button6.setEnabled(false);
        button7.setEnabled(false);
        button8.setEnabled(false);
        button9.setEnabled(false);
        buttonAddition.setEnabled(false);
        buttonSubtraction.setEnabled(false);
        buttonMultiply.setEnabled(false);
        buttonDivision.setEnabled(false);
        buttonDot.setEnabled(false);
        buttonEqual.setEnabled(false);
        btnBack.setEnabled(false);
        lbl.setText("");
      }
    });
    rdbtnOff.setFont(new Font("Tahoma", Font.BOLD, 14));
    rdbtnOff.setBounds(310, 100, 47, 23);
    getContentPane().add(rdbtnOff);

    g1 = new ButtonGroup();
    g1.add(rdbtnOn);
    g1.add(rdbtnOff);
    
    button7 = new JButton("7");
    button7.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "7");
      }
    });
    button7.setFont(new Font("Tahoma", Font.BOLD, 20));
    button7.setBounds(21, 141, 56, 35);
    getContentPane().add(button7);
    
    button8 = new JButton("8");
    button8.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "8");
      }
    });
    button8.setFont(new Font("Tahoma", Font.BOLD, 20));
    button8.setBounds(87, 141, 56, 35);
    getContentPane().add(button8);
    
    buttonSubtraction = new JButton("-");
    buttonSubtraction.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //if it is a real number
        if (isNumeric(textFieldInput.getText())) {
          num =  Double.parseDouble(textFieldInput.getText());
          calculation = 2;
          textFieldInput.setText("");
          lbl.setText(num + "-");
        } else {
          txtError.setText("*please input a real number");
          txtError.setVisible(true);
        }
      }
    });
    buttonSubtraction.setFont(new Font("Tahoma", Font.BOLD, 20));
    buttonSubtraction.setBounds(229, 141, 56, 35);
    getContentPane().add(buttonSubtraction);
    
    button9 = new JButton("9");
    button9.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "9");
      }
    });
    button9.setFont(new Font("Tahoma", Font.BOLD, 20));
    button9.setBounds(153, 141, 56, 35);
    getContentPane().add(button9);
    
    button5 = new JButton("5");
    button5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "5");
      }
    });
    button5.setFont(new Font("Tahoma", Font.BOLD, 20));
    button5.setBounds(87, 187, 56, 35);
    getContentPane().add(button5);
    
    button4 = new JButton("4");
    button4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "4");
      }
    });
    button4.setFont(new Font("Tahoma", Font.BOLD, 20));
    button4.setBounds(21, 187, 56, 35);
    getContentPane().add(button4);
    
    buttonMultiply = new JButton("*");
    buttonMultiply.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //if it a real number
        if (isNumeric(textFieldInput.getText())) {
          num =  Double.parseDouble(textFieldInput.getText());
          calculation = 3;
          textFieldInput.setText("");
          lbl.setText(num + "*");
        } else {
          txtError.setText("*please input a real number");
          txtError.setVisible(true);
        }
      }
    });
    buttonMultiply.setFont(new Font("Tahoma", Font.BOLD, 20));
    buttonMultiply.setBounds(229, 189, 56, 35);
    getContentPane().add(buttonMultiply);
    
    button6 = new JButton("6");
    button6.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "6");
      }
    });
    button6.setFont(new Font("Tahoma", Font.BOLD, 20));
    button6.setBounds(153, 187, 56, 35);
    getContentPane().add(button6);
    
    button2 = new JButton("2");
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "2");
      }
    });
    button2.setFont(new Font("Tahoma", Font.BOLD, 20));
    button2.setBounds(87, 235, 56, 35);
    getContentPane().add(button2);
    
    button1 = new JButton("1");
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "1");
      }
    });
    button1.setFont(new Font("Tahoma", Font.BOLD, 20));
    button1.setBounds(21, 235, 56, 35);
    getContentPane().add(button1);
    
    buttonDivision = new JButton("/");
    buttonDivision.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (isNumeric(textFieldInput.getText())) {
          num =  Double.parseDouble(textFieldInput.getText());
          calculation = 4;
          textFieldInput.setText("");
          lbl.setText(num + "/");
        } else {
          txtError.setText("*please input a real number");
          txtError.setVisible(true);
        }
      }
    });
    buttonDivision.setFont(new Font("Tahoma", Font.BOLD, 20));
    buttonDivision.setBounds(301, 189, 56, 35);
    getContentPane().add(buttonDivision);
    
    button3 = new JButton("3");
    button3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "3");
      }
    });
    button3.setFont(new Font("Tahoma", Font.BOLD, 20));
    button3.setBounds(153, 235, 56, 35);
    getContentPane().add(button3);
    
    button0 = new JButton("0");
    button0.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + "0");
      }
    });
    button0.setFont(new Font("Tahoma", Font.BOLD, 20));
    button0.setBounds(21, 290, 56, 35);
    getContentPane().add(button0);
    
    buttonDot = new JButton(".");
    buttonDot.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        textFieldInput.setText(textFieldInput.getText() + ".");
      }
    });
    buttonDot.setFont(new Font("Tahoma", Font.BOLD, 20));
    buttonDot.setBounds(87, 290, 56, 35);
    getContentPane().add(buttonDot);
    
    buttonEqual = new JButton("=");
    buttonEqual.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtError.setText("");
        //if the textfield value is a real number
        if (isNumeric(textFieldInput.getText())) {
          String str = textFieldInput.getText();
          textFieldInput.setText("");
          arithmeticOperation(str);
          lbl.setText("");
        } else {
          txtError.setText("*please input a real number");
          txtError.setVisible(true);
        }
      }
    });
    buttonEqual.setBounds(153, 290, 204, 35);
    getContentPane().add(buttonEqual);
    
    buttonAddition = new JButton("+");
    buttonAddition.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //If the input is a number
        if (isNumeric(textFieldInput.getText())) {
          num =  Double.parseDouble(textFieldInput.getText());
          calculation = 1;
          textFieldInput.setText("");
          lbl.setText(num + "+");
        } else {
          txtError.setText("*please input a real number");
          txtError.setVisible(true);
        }
      }
    });
    buttonAddition.setFont(new Font("Tahoma", Font.BOLD, 20));
    buttonAddition.setBounds(301, 141, 56, 35);
    getContentPane().add(buttonAddition);
    
    btnBack = new JButton("<--");
    btnBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int length = textFieldInput.getText().length();
        int number = textFieldInput.getText().length() - 1;
        if (length > 0) {
          StringBuilder sbBuilder = new StringBuilder(textFieldInput.getText());
          sbBuilder.deleteCharAt(number);
          textFieldInput.setText(sbBuilder.toString());
        }
      }
    });
    btnBack.setFont(new Font("Tahoma", Font.BOLD, 13));
    btnBack.setBounds(103, 78, 72, 45);
    getContentPane().add(btnBack);
    
    lbl = new JLabel("");
    lbl.setFont(new Font("Tahoma", Font.PLAIN, 10));
    lbl.setHorizontalAlignment(SwingConstants.RIGHT);
    lbl.setForeground(new Color(255, 99, 71));
    lbl.setBounds(229, 11, 128, 14);
    getContentPane().add(lbl);
  }
}
