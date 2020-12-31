package andbook.example.calculator;

import java.util.ArrayList;
import java.util.Stack;

public class NumCalc {

    private static final int NUM = 0; // 숫자
    private static final int LEFT = 1; // 왼쪽 괄호
    private static final int RIGHT = 2; // 오른쪽 괄호
    private static final int OP = 3; // 연산자

    private static int getType(String expr) {
        if (expr.equals("("))
            return LEFT;
        if (expr.equals(")"))
            return RIGHT;
        if (expr.equals("+"))
            return OP;
        if (expr.equals("-"))
            return OP;
        if (expr.equals("*"))
            return OP;
        if (expr.equals("/"))
            return OP;
        if (expr.equals("%"))
            return OP;
        if (expr.equals("^"))
            return OP;
        return NUM;
    }

    private static ArrayList<String> parse(String expr) {
        if (getType(expr.substring(expr.length() - 1)) == OP)
            return null;

        ArrayList<String> result = new ArrayList<String>();
        StringBuilder buf = new StringBuilder(32);

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if ((ch >= '0' && ch <= '9') || ch == '.' || ch == 'E') {
                buf.append(ch);
            } else if (ch == '(' || ch == ')') {
                if (buf.length() > 0) {
                    result.add(buf.toString());
                    buf.delete(0, buf.length());
                }
                buf.append(ch);
                result.add(buf.toString());
                buf.delete(0, buf.length());
            } else if (ch == '-') {
                if (i == 0) {
                    buf.append(ch);
                } else {
                    char c = expr.charAt(i - 1);
                    if (c == '(' || c == '*' || c == '/' || c == '%' || c == '^' || c == 'E')
                        buf.append(ch);
                    else {
                        if (buf.length() > 0) {
                            result.add(buf.toString());
                            buf.delete(0, buf.length());
                        }
                        buf.append(ch);
                        result.add(buf.toString());
                        buf.delete(0, buf.length());
                    }
                }
            } else if (ch == '+' || ch == '*' || ch == '/' || ch == '%' || ch == '^') {
                if (buf.length() > 0) {
                    result.add(buf.toString());
                    buf.delete(0, buf.length());
                }
                buf.append(ch);
                result.add(buf.toString());
                buf.delete(0, buf.length());
            }
        }
        result.add(buf.toString());
        return result;
    }

    private static int getPrecedence(String op) {
        if (op.equals("+"))
            return 1;
        else if (op.equals("-"))
            return 1;
        else if (op.equals("*"))
            return 2;
        else if (op.equals("/"))
            return 2;
        else if (op.equals("%"))
            return 3;
        else if (op.equals("^"))
            return 3;
        return 0;
    }

    private static ArrayList<String> postfix(ArrayList<String> expr) {
        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();

        for (String str : expr) {
            if (getType(str) == NUM)
                result.add(str);
            else if (getType(str) == LEFT) {
                stack.push(str);
            } else if (getType(str) == OP) {
                if (stack.isEmpty())
                    stack.push(str);
                else {
                    while (!stack.isEmpty()) {
                        if (getPrecedence(stack.lastElement()) >= getPrecedence(str))
                            result.add(stack.pop());
                        else
                            break;
                    }
                    stack.push(str);
                }
            } else if (getType(str) == RIGHT) {
                while (!stack.isEmpty() && (getType(stack.lastElement()) != LEFT)) {
                    result.add(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private static String calcRPN(ArrayList<String> expr) {
        Stack<Double> stack = new Stack<Double>();
        double n1, n2, result;

        for (String str : expr) {
            if (getType(str) == NUM) {
                stack.push(Double.parseDouble(str));
            } else if (getType(str) == OP) {
                n2 = stack.pop();
                n1 = stack.pop();
                if (str.equals("+")) {
                    result = n1 + n2;
                } else if (str.equals("-")) {
                    result = n1 - n2;
                } else if (str.equals("*")) {
                    result = n1 * n2;
                } else if (str.equals("/")) {
                    if (n2 == 0.0)
                        return null;
                    else
                        result = n1 / n2;
                } else if (str.equals("%")) {
                    result = n1 * n2 / 100.0;
                } else if (str.equals("^")) {
                    result = Math.pow(n1, n2);
                } else {
                    return null;
                }
                stack.push(result);
            }
        }
        return stack.pop().toString();
    }

    public static String calc(String expr) {
        ArrayList<String> parsed_expr;
        String result = null;

        parsed_expr = NumCalc.parse(expr);
        if (parsed_expr != null) {
            result = NumCalc.calcRPN(NumCalc.postfix(parsed_expr));
            if (result != null) {
                if (result.endsWith(".0")) {
                    result = result.substring(0, result.length() - 2);
                }
                if (result.endsWith("-0")) {
                    result = result.replace("-0", "0");
                }
            }
        }
        return result;
    }
}
