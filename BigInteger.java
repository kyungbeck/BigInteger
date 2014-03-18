import java.io.*;
import java.util.StringTokenizer;

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
				input = input.replace(" ", "");	// delete all blanks
				if (input.compareTo("quit") == 0)
				{
					break;
				}
				calculate(input);
			}
			catch (Exception e)
			{
				System.out.println("�Է��� �߸��Ǿ����ϴ�. ���� : " + e.toString());
			}
		}
	}

	private static void calculate(String input)
	{
		// reference: http://sibnt.net/m/post/view/id/173
		StringTokenizer stok = new StringTokenizer ( input, "/+/-/*", true ); 
		while( stok.hasMoreTokens() )
		{
			System.out.println( stok.nextToken() );
		}
		// System.out.println("<< calculate �Լ�����" + input + "�� ����� �����Դϴ� >>");
	}
}