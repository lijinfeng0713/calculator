package com.lijinfeng.calculator_fengge;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 * @author :李进锋
 * @ 
 *
 */
import java.math.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
public class Calculate02
{
	Calculate02(){}
    Calculate02(String s)
    {
        express = s;
        operator_symbol = new ArrayList<String>();
        operator_symbol.add("!");
        operator_symbol.add("sin");
        operator_symbol.add("(");
        operator_symbol.add(")");
        operator_symbol.add("cos");
        operator_symbol.add("tan");
        operator_symbol.add("ln");
        operator_symbol.add("log");
        operator_symbol.add("+");
        operator_symbol.add("-");
        operator_symbol.add("*");
        operator_symbol.add("÷");
        operator_symbol.add("^");
        operator_symbol.add("=");
        operator_symbol.add("√");
    }
    //计算伽马函数
    public static Double GammaLanczos(Double x)
    {
        Double p, q, y;
        Double [] c = {1.0, 76.18009173, -86.50532033,
                24.01409822, -1.231739516, 0.120858033e-2,
                -0.536382e-5};
        int i;
        if (x == 1.0) return 1.0;
        if (x > 1)
            y = x - 1;
        else
            y = x;
        q = (y + 0.5) * Math.log(y + 5.5) - y - 5.5;
        p = c[0];
        for(i = 1; i < 7; ++i)
        {
            y += 1;
            p += c[i] / y;
        }
        p = p * Math.exp(q) * Math.sqrt(2*Math.PI);
        if ( x < 1.0) p/= x;
        return p;
    }
    
    /**
     * 功能：计算阶乘
     * 描述：由于采用递归调用Factorial(),所以只需要判断x是否小于0即可屏蔽小数的阶乘运算
     */
    public Double Factorial(Double x) {
 
    	if(x<0) { 
    		return Double.NaN;
    	}
    	if(x==0 || x==1) { 
    		return 1.0;
    	} else {
    		return Factorial(x-1)*x;
    	}  	
    }
    
    static final int operator_level(String op)
    {
        switch (op)
        {
            case "+":return 0;
            case "-":return 1;
            case "×":return 2;
            case "÷":return 3;
            case "^":return 4;
            case "√":return 5;
            case "sin":return 6;
            case "cos":return 7;
            case "tan":return 8;
            case "ln":return 9;
            case "log":return 10;
            case "!":return 11;
            case "(":return 12;
            case ")":return 13;
            case "=":return 14;
            default:System.out.println(op);System.out.println("error");return -1;
        }
    }
    
    Double get_value(Double a, String op, Double b)
    {
        switch (op)
        {
            case "+":return new Double(df.format(a + b));
            case "-":return new Double(df.format(a - b));
            case "×":return new Double(df.format(a * b));
            case "÷":return new Double(df.format(a / b));
            case "^":return new Double(df.format(Math.pow(a, b)));
            case "√":return new Double(df.format(a * Math.sqrt(b)));
            case "sin":return new Double(df.format(a * Math.sin(b)));
            case "cos":return new Double(df.format(a * Math.cos(b)));
            case "tan":return new Double(df.format(a * Math.tan(b)));
            case "ln":return new Double(df.format(a * Math.log(b)));
            case "log":return new Double(df.format(a * Math.log10(b)));
            case "!":return new Double(df.format(a * Factorial(b)));
            default:return Double.NaN;
        }
    }
    
    boolean isDigit(char c)
    {
        for(int i = 0; i < Digit.length(); ++i)
            if (c == Digit.charAt(i))
                return true;
        return false;
    }
    
    boolean isfunction(String op)
    {
        if (op.equals("!") || op.equals("sin") || op.equals("cos")
          ||op.equals("tan") || op.equals("ln") || op.equals("log")
          ||op.equals("√"))
            return true;
        else if (op.equals("!"))
            return true;
        else
            return false;
    }
    
    boolean isOperator(String op)
    {
        for(int i = 0; i < Operator_Set.length; i++)
        {
            if (op.equals(Operator_Set[i]))
                return true;
        }
        return false;
    }
    
    Queue<String> cut_express()
    {
        Queue<String> s = new LinkedList<String>();
        Queue<String> tmp1 = new LinkedList<String>();
        Stack<String> tmp2 = new Stack<String>();
        String tmp = "";
        char c;
        int minus = 0;
        for(int i = 0; i < express.length();)
        {
            c = express.charAt(i);
            if(Digit.indexOf(c)!=-1)
            {
                for(; i < express.length()&& Digit.indexOf(c) != -1;)
                {
                    tmp += c;
                    ++i;
                    if (i < express.length())
                    {
                        c = express.charAt(i);
                    }
                }
                s.offer(tmp);
                if (minus > 0)
                {
                    s.offer(")");
                    minus--;
                }
                tmp = "";
            }
            else
            {
                for(; i < express.length() && Digit.indexOf(c) == -1;)
                {
                    tmp += c;
                    ++i;
                    if (isOperator(tmp))
                        break;
                    if (i < express.length())
                    {
                        c = express.charAt(i);
                    }
                }
                tmp1.addAll(s);
                while(!tmp1.isEmpty())
                {
                    tmp2.push(tmp1.poll());
                }
                if (tmp.equals("-") && (tmp2.isEmpty() || Digit.indexOf(tmp2.peek().charAt(0)) == -1))
                {
                    minus++;
                    s.offer("(");
                    s.offer("0");
                }
                s.offer(tmp);
                tmp = "";
            }
        }
        if (minus>0)
        {
            s.poll();
            while(minus>0)
            {
                s.offer(")");
                minus--;
            }
            s.offer("=");
        }
        return s;
    }
    
    public Double get_result()
    {
        Stack<String> OPTR = new Stack<String>();
        Stack<Double> OPND = new Stack<Double>();
        OPTR.push("=");
        Double a = new Double(0), b = new Double(0);
        Queue<String> OPTRandOPND = cut_express();
        String tmp = OPTRandOPND.poll();
        String op;
        while (!tmp.equals("=") || !OPTR.peek().equals("="))
        {
            if (!isOperator(tmp))
            {
                if (tmp.equals("e"))
                    OPND.push(Math.E);
                else if (tmp.equals("π"))
                    OPND.push(Math.PI);
                else
                    OPND.push(new Double(tmp));
                tmp = OPTRandOPND.poll();
            }
            else
            {
                switch (priority[operator_level(OPTR.peek())][operator_level(tmp)])
                {
                case '<':;OPTR.push(tmp); tmp = OPTRandOPND.poll(); break;
                case '=':OPTR.pop(); tmp = OPTRandOPND.poll();break;
                case '>':
                    op = OPTR.pop();
                    b = OPND.pop();
                    if (isfunction(op))
                    {
                        OPND.push(new Double(1.0));
                    }
                    a = OPND.pop();
                    OPND.push(get_value(a, op, b));
                    break;
                }
            }
        }
        if (OPND.isEmpty())
            return new Double(0.0);
        return OPND.peek();
    }
    private DecimalFormat df = new DecimalFormat( "0.00000000");  
    private String express;
    private ArrayList<String> operator_symbol;
    public static final String Digit= "0123456789.eπ";
    static final String Operator_Set[] =
        {"+", "-", "×", "÷", "^", "=", "√", "!",
        "sin", "(", ")", "sin", "cos", "tan", "ln", "log"};
    static final char[][] priority = 
    {
        {'>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '<', '<', '<', '<', '<', '<', '<', '<', '<', '>', '>'},
        {'>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>'},
        {'<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '=', ' '},
        {'>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', '>', ' ', '>', '>'},
        {'<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', '<', ' ', '='},
    };
}
