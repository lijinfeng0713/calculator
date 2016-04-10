package com.lijinfeng.calculator_fengge;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	//private Calculate calculate=new Calculate();
	private Button btnC=null;
	private Button btnBackspace=null;
	
	//创建数字按钮
	private Button btn0=null;
	private Button btn1=null;
	private Button btn2=null;
	private Button btn3=null;
	private Button btn4=null;
	private Button btn5=null;
	private Button btn6=null;
	private Button btn7=null;
	private Button btn8=null;
	private Button btn9=null;
	private Button btnPoint=null;
	
	//创建运算符按钮
	private Button btnAdd=null;
    private Button btnSub=null;
    private Button btnMul=null;
    private Button btnDiv=null;
    private Button btnEqu=null;
    
    private Button btnKuoLeft=null;
    private Button btnKuoRight=null;
    private Button btnFactorial=null;
    private Button btnPower=null;
    private Button btnSqrt=null;
    private Button btnPi=null;
    private Button btnLog=null;
    private Button btnSin=null;
    private Button btnCos=null;
    private Button btnTan=null;
    
    private TextView edit=null; 
    private TextView edit2=null; 
    private double number1=0;
    private double number2=0;
    private String result=null;
    private int op=0;        //运算符标志
    private int index=0;    //括号输入计数标志，输入“(”时，index++,输入“)”时，index
    boolean isClickEqu=false;//判断是否按了“=”按钮
    boolean isClickPoint=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//从页面布局中获取所有的按钮
		btnC=(Button)findViewById(R.id.button1);
		btnBackspace=(Button)findViewById(R.id.button2);
		
		btn0=(Button)findViewById(R.id.button16);
		btnPoint=(Button)findViewById(R.id.button17);
		btn1=(Button)findViewById(R.id.button13);
		btn2=(Button)findViewById(R.id.button14);
		btn3=(Button)findViewById(R.id.button15);
		btn4=(Button)findViewById(R.id.button9);
		btn5=(Button)findViewById(R.id.button10);
		btn6=(Button)findViewById(R.id.button11);
		btn7=(Button)findViewById(R.id.button5);
		btn8=(Button)findViewById(R.id.button6);
		btn9=(Button)findViewById(R.id.button7);
		
		btnAdd=(Button)findViewById(R.id.button12);
		btnSub=(Button)findViewById(R.id.button8);
		btnMul=(Button)findViewById(R.id.button4);
		btnDiv=(Button)findViewById(R.id.button3);
		btnEqu=(Button)findViewById(R.id.button18);
		edit=(TextView)findViewById(R.id.edit1);
		edit2=(TextView)findViewById(R.id.edit2);
		
		btnFactorial=(Button)findViewById(R.id.btnFactorial);
		btnPower=(Button)findViewById(R.id.btnPower);
		btnSqrt=(Button) findViewById(R.id.btnSqrt);
		btnPi=(Button) findViewById(R.id.btnPi);
		btnLog=(Button) findViewById(R.id.btnLog);
		btnKuoLeft=(Button) findViewById(R.id.btnKuohaoLeft);
		btnKuoRight=(Button) findViewById(R.id.btnKuohaoRight);
		btnSin=(Button) findViewById(R.id.btnSin);
		btnCos=(Button) findViewById(R.id.btnCos);
		btnTan=(Button)findViewById(R.id.btnTan);
		
		//将edit和edit2加入到滚动事件中
		edit.setMovementMethod(ScrollingMovementMethod.getInstance());
		edit2.setMovementMethod(ScrollingMovementMethod.getInstance());
		
		//将所有的按钮加入到监听事件中
		btnC.setOnClickListener(this);
		btnBackspace.setOnClickListener(this);
		
		btn0.setOnClickListener(this);
		btnPoint.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		
		btnAdd.setOnClickListener(this);
		btnSub.setOnClickListener(this);
		btnMul.setOnClickListener(this);
		btnDiv.setOnClickListener(this);
		btnEqu.setOnClickListener(this);
		
		btnKuoLeft.setOnClickListener(this);
		btnKuoRight.setOnClickListener(this);
		btnFactorial.setOnClickListener(this);
		btnPower.setOnClickListener(this);
		btnSqrt.setOnClickListener(this);
		btnPi.setOnClickListener(this);
		btnLog.setOnClickListener(this);
		btnSin.setOnClickListener(this);
		btnCos.setOnClickListener(this);
		btnTan.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			edit.setText("");
			edit2.setText("");
			isClickPoint = false;
			index=0;
			break;

		case R.id.button2:
			String editStr = edit.getText().toString();
			try {
				if("(".equals(editStr.substring(editStr.length()-1, editStr.length()))) {--index;}
				if(")".equals(editStr.substring(editStr.length()-1, editStr.length()))) {++index;}
				if(".".equals(editStr.substring(editStr.length()-1, editStr.length()))) {isClickPoint = false;}
				edit.setText(editStr.substring(0, editStr.length()-1));
			} catch (Exception e) {
				// TODO: handle exception
				edit.setText("");
				isClickPoint = false;
			}
			break;
			
		/**
		 * 功能：获取数字按钮的点击事件
		 */
		case R.id.button16:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr0 =edit.getText().toString();
			if(!editStr0.equals("0")) {
				editStr0 += "0";
				edit.setText(editStr0);
				
			}
			break;
		
		case R.id.button17:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
				isClickPoint = false;
			}
			String editStrPoint =edit.getText().toString();
			if(!editStrPoint.equals("") && !isClickPoint) {
				editStrPoint += ".";
				isClickPoint = true;
				edit.setText(editStrPoint);
			}
			break;
			
		case R.id.button13:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr1 =edit.getText().toString();
			editStr1 += "1";
			edit.setText(editStr1);
			break;
			
		case R.id.button14:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr2 =edit.getText().toString();
			editStr2 += "2";
			edit.setText(editStr2);
			break;
			
		case R.id.button15:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr3 =edit.getText().toString();
			editStr3 += "3";
			edit.setText(editStr3);
			break;
			
		case R.id.button9:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr4 =edit.getText().toString();
			editStr4 += "4";
			edit.setText(editStr4);
			break;
		
		case R.id.button10:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr5 =edit.getText().toString();
			editStr5 += "5";
			edit.setText(editStr5);
			break;
		
		case R.id.button11:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr6 =edit.getText().toString();
			editStr6 += "6";
			edit.setText(editStr6);
			break;	
			
		case R.id.button5:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr7 =edit.getText().toString();
			editStr7 += "7";
			edit.setText(editStr7);
			break;
			
		case R.id.button6:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr8 =edit.getText().toString();
			editStr8 += "8";
			edit.setText(editStr8);
			break;
			
		case R.id.button7:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editStr9 =edit.getText().toString();
			editStr9 += "9";
			edit.setText(editStr9);
			break;
			
		/**
		 * 功能：获取括号，三角函数等点击事件
		 */
		case R.id.btnKuohaoLeft:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editLeft =edit.getText().toString();
			editLeft += "(";
			++index;
			edit.setText(editLeft);
			break;
			
		case R.id.btnKuohaoRight:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editRight =edit.getText().toString();
			if(index<=0) { return; }
			editRight += ")";
			--index;
			edit.setText(editRight);
			break;
		
		case R.id.btnSin:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editSin =edit.getText().toString();
			editSin += "sin";
			edit.setText(editSin);
			break;
			
		case R.id.btnCos:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editCos =edit.getText().toString();
			editCos += "cos";
			edit.setText(editCos);
			break;
		
		case R.id.btnTan:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editTan =edit.getText().toString();
			editTan += "tan";
			edit.setText(editTan);
			break;
		
		case R.id.btnFactorial:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editFactotial =edit.getText().toString();
			editFactotial += "!";
			edit.setText(editFactotial);
			break;
			
		case R.id.btnPower:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editPower =edit.getText().toString();
			editPower += "^";
			edit.setText(editPower);
			break;
		
		case R.id.btnSqrt:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editSqrt=edit.getText().toString();
			editSqrt += "√";
			edit.setText(editSqrt);
			break;
			
		case R.id.btnPi:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editPi=edit.getText().toString();
			editPi += "π";
			edit.setText(editPi);
			break;
		
		case R.id.btnLog:
			if(isClickEqu) {
				edit.setText(null);
				isClickEqu = false;
			}
			String editLog=edit.getText().toString();
			editLog += "log";
			edit.setText(editLog);
			break;	
			
		/**
		 * 功能：获取运算符点击事件	
		 */
		case R.id.button12:
			String editStrAdd = edit.getText().toString();
			if("".equals(editStrAdd)) {
				return;
			}
			if(isClickEqu) {
				editStrAdd = result;
				isClickEqu = false;
			} 
			editStrAdd += "+";
			edit.setText(editStrAdd);
			op = 1;
			isClickEqu = false;
			isClickPoint = false;
			break;
			
		case R.id.button8:
			String editStrSub = edit.getText().toString();
			if("".equals(editStrSub)) {
				return;
			}
			if(isClickEqu) {
				editStrSub = result;
				isClickEqu = false;
			} 
			editStrSub += "-";
			edit.setText(editStrSub);
			op = 2;
			isClickEqu = false;
			isClickPoint = false;
			break;
			
		case R.id.button4:
			String editStrMul = edit.getText().toString();
			if("".equals(editStrMul)) {
				return;
			}
			if(isClickEqu) {
				editStrMul = result;
				isClickEqu = false;
			} 
			editStrMul += "×";
			edit.setText(editStrMul);
			op = 3;
			isClickEqu = false;
			isClickPoint = false;
			break;

		case R.id.button3:
			String editStrDiv = edit.getText().toString();
			if("".equals(editStrDiv)) {
				return;
			}
			if(isClickEqu) {
				editStrDiv = result;
				isClickEqu = false;
			} 
			editStrDiv += "÷";
			edit.setText(editStrDiv);
			op = 4;
			isClickEqu = false;
			isClickPoint = false;
			break;
		
		case R.id.button18:
			String editStrEqu = edit.getText().toString();
			if(index!=0) {
				result = "ERROR";
			} else {
				try {
					Calculate c = new Calculate();
					result = c.process(c.replaceOp(editStrEqu));
					if("ERROR".equals(result)) {
						result = "ERROR";
					} else {
						//去掉整数结果末尾的“.0”
						if("0".equals(result.substring(result.length()-1, result.length())) &&
								".".equals(result.substring(result.length()-2, result.length()-1))) {
							result = (String) result.subSequence(0, result.length()-2);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = "ERROR";
				}
			}
			
			edit2.setText(editStrEqu+"=");
			edit.setText(result);
	        isClickEqu=true;
	        isClickPoint=false;
	        index=0;
			break;
			
		default:
			break;
		}
	}
}
