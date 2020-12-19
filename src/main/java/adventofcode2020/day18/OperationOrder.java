package adventofcode2020.day18;

import lombok.NonNull;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationOrder {

    static final Pattern addition = Pattern.compile("(\\d+ \\+ \\d+)");
    static final Pattern multiplication = Pattern.compile("(\\d+ \\* \\d+)");
    static final Pattern parenthesis = Pattern.compile("(\\([\\d+*\\s]*\\))");
    static final Pattern calcExpression = Pattern.compile("(\\d+) ([+*]) (\\d+)");
    static final Pattern number = Pattern.compile("^[(]?(\\d+)[)]?$");

    static List<String> readExpressions(@NonNull String inputFilePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(Path.of(inputFilePath).toFile());
        List<String> opStrings = new ArrayList<>();
        while (scanner.hasNextLine()) {
            opStrings.add(scanner.nextLine());
        }
        return opStrings;
    }

    public static BigInteger scanExpressionsRule1(@NonNull String inputFilePath) throws FileNotFoundException {
        return readExpressions(inputFilePath).stream()
                .map(OperationOrder::evalExpressionRule1)
                .map(BigInteger::new)
                .reduce(BigInteger::add).get();
    }

    public static BigInteger scanExpressionsRule2(@NonNull String inputFilePath) throws FileNotFoundException {
        return readExpressions(inputFilePath).stream()
                .map(OperationOrder::evalExpressionRule2)
                .map(BigInteger::new)
                .reduce(BigInteger::add).get();
    }

    public static String calc(String exp) {
        Matcher expressionMatcher = calcExpression.matcher(exp);
        if (expressionMatcher.find()) {
            BigInteger op1 = new BigInteger(expressionMatcher.group(1));
            BigInteger op2 = new BigInteger(expressionMatcher.group(3));
            char op = expressionMatcher.group(2).charAt(0);
            if (op == '+') {
                return op1.add(op2).toString();
            } else if (op == '*')
                return op1.multiply(op2).toString();
        }
        throw new IllegalStateException(exp);
    }

    public static String evalExpressionRule1(String expression) {
        Matcher numberMatcher = number.matcher(expression);
        if (numberMatcher.find()){
            return numberMatcher.group();
        }

        Matcher parenthesisMatcher = parenthesis.matcher(expression);
        while(parenthesisMatcher.find()) {
            String exp = parenthesisMatcher.group();
            String result = evalExpressionRule1(exp.substring(1, exp.length()-1));
            expression = expression.replaceFirst(parenthesisMatcher.pattern().pattern(), result);
            parenthesisMatcher = parenthesis.matcher(expression);
        }

        Matcher calcExpressionMatcher = calcExpression.matcher(expression);
        while(calcExpressionMatcher.find()) {
            String result = calc(calcExpressionMatcher.group());
            expression = expression.replaceFirst(calcExpressionMatcher.pattern().pattern(), result);
            calcExpressionMatcher = calcExpression.matcher(expression);
        }

        return evalExpressionRule1(expression);
    }

    public static String evalExpressionRule2(String expression) {
        Matcher numberMatcher = number.matcher(expression);
        if (numberMatcher.find()){
            return numberMatcher.group();
        }

        Matcher parenthesisMatcher = parenthesis.matcher(expression);
        while(parenthesisMatcher.find()) {
            String exp = parenthesisMatcher.group();
            String result = evalExpressionRule2(exp.substring(1, exp.length()-1));
            expression = expression.replaceFirst(parenthesisMatcher.pattern().pattern(), result);
            parenthesisMatcher = parenthesis.matcher(expression);
        }

        Matcher additionMatcher = addition.matcher(expression);
        while(additionMatcher.find()) {
            String result = calc(additionMatcher.group(1));
            expression = expression.replaceFirst(additionMatcher.pattern().pattern(), result);
            additionMatcher = addition.matcher(expression);
        }

        Matcher multiplicationMatcher = multiplication.matcher(expression);
        while(multiplicationMatcher.find()) {
            String addExp = multiplicationMatcher.group();
            String result = calc(addExp);
            expression = expression.replaceFirst(multiplicationMatcher.pattern().pattern(), result);
            multiplicationMatcher = multiplication.matcher(expression);
        }
        return evalExpressionRule2(expression);
    }
}
