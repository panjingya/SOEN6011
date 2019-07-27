package com.gammaCalculator.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.gammaCalculator.impl.MainFrame;

class CalculatorTests {
  
  /**
   * According to the Requirement R1
   * 
   * <p>Check the user input is a real number or not.
   * other characteristics are not acceptable.
   */
  @Test
  void isNumeric() {
    assertEquals(true,MainFrame.isNumeric("123"));
    assertEquals(true,MainFrame.isNumeric("120.9"));
    assertEquals(false,MainFrame.isNumeric("sss"));
    assertEquals(false,MainFrame.isNumeric(", .!?"));
    assertEquals(false,MainFrame.isNumeric("----"));
    assertEquals(false,MainFrame.isNumeric("@~#"));
  }
  
  /**
   * According to the Requirement R1
   * 
   * <p>Part of Gamma function requirement(negative integer is not acceptable).
   * -4.0 belongs to a negative integer.
   * 
   */
  @Test
  void isNegativeIntegerForDouble() {
    assertEquals(true,MainFrame.isNegativeIntegerForDouble(-4.0));
    assertEquals(false,MainFrame.isNegativeIntegerForDouble(4.0));
    assertEquals(false,MainFrame.isNegativeIntegerForDouble(-4.2));
  }
  
  /**
   * According to the Requirement R2,R3
   * 
   * <p>Lanczos approximation for Gamma function. 
   * Special cases:
   * The negative integer will not be allowed to enter, so no need to test here.
   * Γ(0) = infinity.
   * When the input is sufficient large, eg:143, the result will show infinity.
   * 
   */
  @Test
  void lanczosGamma() {
    assertEquals(0.9999999999999998,MainFrame.lanczosGamma(1));
    assertEquals(1.0000000000000002,MainFrame.lanczosGamma(2.0));
    assertEquals(120.00000000000021,MainFrame.lanczosGamma(6));
    assertEquals(362880.0000000015,MainFrame.lanczosGamma(10));
    assertEquals(-2.2049805184191302,MainFrame.lanczosGamma(-2.2));
    assertEquals(-9.18493541678207E-7,MainFrame.lanczosGamma(-10.2));
  }
  
}
