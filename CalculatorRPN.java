package calculatorrpn;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class CalculatorRPN {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        CalculatorRPN.inputLine();
    }

    private static void inputLine() {
        System.out.println("Введите ваше выражение:");//ввод выражения
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        try { // обработка исключений
            System.out.println("Результат:" + " " + obch(input));
        } catch (ArithmeticException e) {
            System.out.println("Деление на ноль");
            System.out.println("Введите выражение еще раз");
            CalculatorRPN.inputLine();
        } catch (NumberFormatException e) {
            System.out.println("Ввод неизвестных символов");
            System.out.println("Введите, пожалуйста, числовое выражение");
            CalculatorRPN.inputLine();
        } catch (NoSuchElementException e) {
            System.out.println("Непарное количество скобок или пустой оператор ");
            System.out.println("Проверьте правильность написания выражения");
            CalculatorRPN.inputLine();
        }

    }

    static boolean space(char c) { 
        return c == ' ';
    }

    private static boolean operators(char c) { // проверка операторов среди символов
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':

                return true;
        }
        return false;
    }

    static int priority(char operator) { // приоритеты операций
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;

        }
        return 0;
    }

    private static int stringparsing(String input)  { // блок обработки строки

        LinkedList<Integer> numbers = new LinkedList<>();
        LinkedList<Character> operator = new LinkedList<>();
        for (int i = 0; i < input.length(); i++) {  // проход по строке

            char c = input.charAt(i);
            if (space(c)) {
                continue;
            }
            if (c == '(') {
                operator.add('(');
            } else if (c == ')') {
                while (operator.getLast() != '(') {
                    calculation(numbers, operator.removeLast());
                }
                operator.removeLast();
            } else if (operators(c)) {
                while (!operator.isEmpty() && priority(operator.getLast()) >= priority(c)) {
                    calculation(numbers, operator.removeLast());
                }
                operator.add(c);
            } else {
                String operand = "";
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    operand += input.charAt(i++);
                }
                --i;
                numbers.add(Integer.parseInt(operand));
            }
        }
        while (!operator.isEmpty()) {
            calculation(numbers, operator.removeLast());
        }
        return numbers.get(0);
    }

    static void calculation(LinkedList<Integer> number, char operator) { // блок вычисления
        int a = number.removeLast();
        int b = number.removeLast();
        switch (operator) {
            case '+':
                number.add(b + a);
                break;
            case '-':
                number.add(b - a);
                break;
            case '*':
                number.add(b * a);
                break;
            case '/':
                number.add(b / a);
                break;
        }
    }

}
