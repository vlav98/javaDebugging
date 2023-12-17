import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {
    private Calculator calculatorUnderTest;

    private static Instant startedAt;

    @BeforeAll
    public static void initStartingTime() {
        System.out.println("Appel avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    public static void showTestDuration() {
        System.out.println("Appel après tous les tests");
        Instant endedAt = Instant.now();
        long duration = Duration.between(startedAt, endedAt).toMillis();
        System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
    }

    @BeforeEach
    public void initCalculator() {
        calculatorUnderTest = new Calculator();
        System.out.println("Appel avant chaque test");
    }

    @AfterEach
    public void undefCalculator() {
        System.out.printf("Appel après chaque test");
        calculatorUnderTest = null;
    }

    @Test
    void testAddTwoPositiveNumbers() {
        // ARRANGE
        final int a = 2;
        final int b = 3;

        // ACT
        final int sum = calculatorUnderTest.add(a,b);

        // ASSERT
        assertEquals(5, sum);
    }

    @Test
    void testMultiplyTwoPositiveNumbers() {
        // ARRANGE
        final int a = 2;
        final int b = 3;

        // ACT
        final int product = calculatorUnderTest.multiply(a, b);

        // ASSERT
        assertEquals(6, product);
    }

    @ParameterizedTest(name = "{0} x 0 doit être égal à 0")
    @ValueSource(ints = {1, 2, 42})
    public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
        // ARRANGE - Tout est prêt

        // ACT - Multiplier par zéro
        final int actualResult = calculatorUnderTest.multiply(arg, 0);

        // ASSERT - Ca vaut toujours zéro
        assertEquals(0, actualResult);
    }

    @ParameterizedTest(name = "{0} + {1} doit être égal à {2}")
    @CsvSource({"1, 1, 2", "2, 3, 5", "42, 57, 99"})
    public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
        // ACT
        final int actualResult = calculatorUnderTest.add(arg1, arg2);

        // ASSERT - Ca vaut toujours zéro
        assertEquals(expectResult, actualResult);
    }

    @Test
    @Timeout(1)
    public void longCalcul_shouldComputeInLessThan1Second() {
        calculatorUnderTest.longCalculation();
    }
}