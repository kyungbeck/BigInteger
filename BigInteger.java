import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;

/*
 * Input: 123+456
 * Internal data structure: '1' '2' '3' '+' '4' '5' '6'
 * Length: 3, 3
 * Have to delete two functions likes GetIntIndexAt, SetIntIndexAt. And I recommend to implement GetIntDigitAt, SetIntDigitAt. 
 */
class MyInteger {
	private static final int N = 500;
	private char sign; // '+', '-'
	private char A[] = new char[N]; 
	private int length; // if 234 then length: 3

	public MyInteger()
	{
		SetCharSign('+');
		SetIntLength(1);
		SetCharZeroToAllBuffer();
	}

	public MyInteger(char _sign, char[] _array, int _length)
	{
		SetCharSign(_sign);
		SetIntLength(_length);		
		SetCharZeroToAllBuffer();
		for (int i = 0; i < GetIntLength(); i++) // Input: '1' '2' '3' -> Internal: '3' '2' '1'
			SetCharDigitAt(i, _array[i]);
		AdjustLength();
	}

	public void Print()
	{
		if (GetCharSign() == '+')
			;
		else // case '-' sign
		{
			if (this.GetIntLength() == 1 && this.GetCharIndexAt(0) == '0') // case of -0
				;
			else
				System.out.printf("-");
		}
		int idx_max_len = GetIntLength() - 1;
		for (int i = idx_max_len; i >= 0; i--)
			System.out.print(this.GetIntIndexAt(i));
	}

	public int GetIntIndexAt(int _i) // A[_i]
	{
		return A[_i] - '0';
	}

	public char GetCharIndexAt(int _i) // A[_i]
	{
		return A[_i];
	}
	
	public void SetIntIndexAt(int _i, int _val) // A[_i]
	{				
		A[_i] = (char) (_val + '0');
	}

	public void SetCharDigitAt(int _exp, char _val) // 10^_exp  
	{
		int length = GetIntLength();
		int idx = length - 1 - _exp;
		A[idx] = _val;
	}

	public int GetIntLength()
	{
		return length;
	}

	public char GetCharSign()
	{
		return sign;
	}

	public void AdjustLength() // Adjust length from idx_max_len to 0
	{
		int i;
		int idx_max_len = this.A.length - 1;
		for (i = idx_max_len; i >= 0; i--)
			if (GetIntIndexAt(i) != 0)
				break;
		if ( i == -1 )
			SetIntLength(1);
		else
			SetIntLength(i + 1);
	}

	public void SetCharSign(char _val)
	{
		sign = _val;
	}

	public void SetCharInverseSign()
	{
		if ( sign == '+' ) this.SetCharSign('-');
		else this.SetCharSign('+');
	}
	
	public void SetIntLength(int _val)
	{
		length = _val;
	}
	
	public void SetCharZeroToAllBuffer()
	{
		for (int i = 0; i < A.length; i++)
			A[i] = '0';
	}
}

interface MyBigIntegerFunctions
{
	public char compareTo(MyBigInteger val);
	public char compareTo_abs(MyBigInteger val);
	public boolean GetBoolSameSign(MyBigInteger val);	
	public MyBigInteger add(MyBigInteger val);
	public MyBigInteger subtract(MyBigInteger val);
	public MyBigInteger multiply_one(int d);
	public MyBigInteger multiply_ten(int exp);
	public MyBigInteger multiply(MyBigInteger val);
}

class MyBigInteger extends MyInteger implements MyBigIntegerFunctions {

	public MyBigInteger()
	{
		super ();
	}
	
	public MyBigInteger(char _charAt, char[] _charArray, int _length)
	{
		super (_charAt, _charArray, _length);
	}

	public char compareTo(MyBigInteger _val)
	{
		if ((this.GetCharSign() == '-') && (_val.GetCharSign() == '+'))	return '<';
		if ((this.GetCharSign() == '+') && (_val.GetCharSign() == '-'))	return '>';
		// If two operands have a same sign.
		char ret = this.compareTo_abs(_val);
		if ( this.GetCharSign() == '+')
		{
			if ( ret == '>' ) return '>';
			else if ( ret == '<' ) return '<';
			else return '=';
		}
		else
		{
			if ( ret == '>' ) return '<';
			else if ( ret == '<' ) return '>';
			else return '=';			
		}
	}

	public char compareTo_abs(MyBigInteger _val)
	{
		int idx_max_index = this.GetIntLength() - 1;
		if (this.GetIntLength() < _val.GetIntLength()) return '<';
		else if (this.GetIntLength() > _val.GetIntLength()) return '>';
		for (int i = idx_max_index; i >= 0; i--) // same length
		{
			if (this.GetIntIndexAt(i) < _val.GetIntIndexAt(i)) return '<';
			else if (this.GetIntIndexAt(i) > _val.GetIntIndexAt(i)) return '>';
		}
		return '=';
	}

	public boolean GetBoolSameSign(MyBigInteger _val)
	{
		if (this.GetCharSign() == _val.GetCharSign())
			return true;
		else
			return false;
	}	
	
	// 문제로 풀어보는 알고리즘
	public MyBigInteger add(MyBigInteger _val)
	{
		MyBigInteger result = new MyBigInteger();
		// Case 1. two signs are different.
		if (this.GetBoolSameSign(_val) == false)
		{
			if (this.compareTo_abs(_val) == '=')
			{
				return result; // 0
			} 
			else if (this.compareTo_abs(_val) == '>')
			{
				_val.SetCharInverseSign();
				return this.subtract(_val);
			}
			else
			{
				this.SetCharInverseSign();
				return _val.subtract(this);
			}
		}
		// End Case 1.
		result.SetCharSign(this.GetCharSign());
		result.SetIntLength(Math.max(this.GetIntLength(), _val.GetIntLength()) + 1);
		
		int sum = 0, carry = 0;
		for (int i = 0; i <= Math.max(this.GetIntLength(), _val.GetIntLength()); i++)
		{
			sum = carry + this.GetIntIndexAt(i) + _val.GetIntIndexAt(i);
			result.SetIntIndexAt(i, sum % 10);
			carry = sum / 10;
		}
		result.AdjustLength();
		return result;
	}

	// 문제로 풀어보는 알고리즘
	public MyBigInteger subtract(MyBigInteger _val)
	{
		MyBigInteger result = new MyBigInteger();

		if (this.GetBoolSameSign(_val) == false)
		{
			_val.SetCharInverseSign();
			return this.add(_val);
		}

		if (this.GetCharSign() == '-' && _val.GetCharSign() == '-')
		{
			this.SetCharSign('+');
			_val.SetCharSign('+');
			result = this.subtract(_val);
			if (this.compareTo_abs(_val) == '>' || this.compareTo_abs(_val) == '=')
				result.SetCharSign('-');
			else
				result.SetCharSign('+');
			return result;
		}

		if (this.compareTo(_val) == '<') // a < b
		{
			result = _val.subtract(this);
			result.SetCharSign('-');
			return result;
		}

		int borrow = 0;
		int dif = 0;	
		for (int i = 0; i < this.GetIntLength(); i++)
		{
			dif = -borrow + this.GetIntIndexAt(i) - _val.GetIntIndexAt(i);
			if (dif < 0)
			{
				borrow = 1;
				dif = dif + 10;
			} else
				borrow = 0;

			result.SetIntIndexAt(i, dif % 10);
		}
		result.SetCharSign(this.GetCharSign());
		result.AdjustLength();
		return result;
	}

	// 문제로 풀어보는 알고리즘
	public MyBigInteger multiply_one(int d) 
	{
		int i;
		MyBigInteger result = new MyBigInteger();
		result.SetCharSign(this.GetCharSign());
		result.SetIntLength(this.GetIntLength() + 1);

		int carry = 0, sum = 0;
		for (i = 0; i < this.GetIntLength(); i++)
		{
			sum = (this.GetIntIndexAt(i) * d) + carry;
			result.SetIntIndexAt(i, sum % 10);
			carry = sum / 10;
		}
		result.SetIntIndexAt(i, carry);
		result.AdjustLength();
		return result;
	}

	// 문제로 풀어보는 알고리즘
	public MyBigInteger multiply_ten(int _exp) // 10^_exp
	{
		MyBigInteger result = new MyBigInteger();
		result.SetCharSign(this.GetCharSign());
		result.SetIntLength(this.GetIntLength());

		int length = this.GetIntLength();
		for (int i = 0; i < length; i++)
			result.SetIntIndexAt(i + _exp, this.GetIntIndexAt(i));
		result.AdjustLength();
		return result;
	}

	// 문제로 풀어보는 알고리즘
	public MyBigInteger multiply(MyBigInteger _val)
	{
		MyBigInteger result = new MyBigInteger();
		MyBigInteger tmp_result = new MyBigInteger();

		result.SetCharSign(this.GetCharSign());
		result.SetIntLength(this.GetIntLength() * 2 + 1);

		for (int i = 0; i < _val.GetIntLength(); i++)
		{
			tmp_result = this.multiply_one(_val.GetIntIndexAt(i)).multiply_ten(i);
			result = result.add(tmp_result);
		}

		if (this.GetBoolSameSign(_val) == false)
			result.SetCharSign('-');
		else
			result.SetCharSign('+');

		result.AdjustLength();
		return result;
	}

}

public class BigInteger
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("quit") == 0) break;
				input = preprocess(input);
				calculate(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	// Remove tabs and spaces.
	private static String preprocess(String input)
	{
		input = input.replace("\t", "");
		input = input.replace(" ", "");		
		return input;
	}
	
	private static void calculate(String input)
	{
		int state = 0;
		String operand1_sign = "";
		String operand1_value = "";
		String operator = "";
		String operand2_sign = "";
		String operand2_value = "";

		// Start of parsing using FSM.
		// StringTokenizer : http://sibnt.net/m/post/view/id/173
		StringTokenizer stok = new StringTokenizer(input, "/+/-/*", true);
		while (stok.hasMoreTokens())
		{
			String s = stok.nextToken();
			if (state == 0 && s.matches("[+-]"))
			{
				state = 1;
				operand1_sign = s;
				continue;
			}
			if (state == 0 && s.matches("[0-9]+"))
			{
				state = 2;
				operand1_sign = "+";
				operand1_value = s;
				continue;
			}
			// matches : http://pupustory.tistory.com/132
			if (state == 1 && s.matches("[0-9]+"))
			{
				state = 3;
				operand1_value = s;
				continue;
			}
			if ((state == 2 || state == 3) && s.matches("[*+-]"))
			{
				state = 4;
				operator = s;
				continue;
			}
			if (state == 4 && s.matches("[+-]"))
			{
				state = 5;
				operand2_sign = s;
				continue;
			}
			if (state == 4 && s.matches("[0-9]+"))
			{
				state = 6;
				operand2_sign = "+";
				operand2_value = s;
				continue;
			}
			if (state == 5 && s.matches("[0-9]+"))
			{
				state = 7;
				operand2_value = s;
				continue;
			}
		}
		// End of parsing using FSM.

		MyBigInteger firstOperandCNumber = new MyBigInteger(
				operand1_sign.charAt(0),
				operand1_value.toCharArray(),
				operand1_value.length());
		MyBigInteger secondOperandCNumber = new MyBigInteger(
				operand2_sign.charAt(0),
				operand2_value.toCharArray(),
				operand2_value.length());

		MyBigInteger resultMyBigInteger = new MyBigInteger();
		switch (operator)
		{
		case "+":
			resultMyBigInteger = firstOperandCNumber.add(secondOperandCNumber);
			break;
		case "-":
			resultMyBigInteger = firstOperandCNumber.subtract(secondOperandCNumber);
			break;
		case "*":
			resultMyBigInteger = firstOperandCNumber.multiply(secondOperandCNumber);
			break;
		default:
			break;
		}
		resultMyBigInteger.Print();
		System.out.println();
	}
}