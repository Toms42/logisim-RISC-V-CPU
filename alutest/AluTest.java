public class AluTest
{
	public static void main(String[] args)
	{
		for(int test = 0; test < 1 << 8; test++)
		{
			int opc = test & 0x7;
			int op1u = (test >> 3) & 0x3;
			int op1 = op1u | ((op1u & 0x2) << 30 >> 30);
			int op2u = (test >> 5) & 0x3;
			int op2 = op2u | ((op2u & 0x2) << 30 >> 30);
			boolean sub = ((test >> 7) & 0x1) == 0x1 ? true : false;

			System.out.print("opc:");
			printLsbs(opc, 2);
			System.out.print(" (" + decodeOpc(opc, sub) + ')');
			System.out.print("\top1:");
			printLsbs(op1, 1);
			System.out.print("\top2:");
			printLsbs(op2, 1);
			System.out.print("\tsub:" + (sub ? 1 : 0) + '\t');

			int res = 0;
			long addsub = !sub ? widen(op1) + widen(op2) : widen(op1) - widen(op2);
			switch(opc) {
			case 0x0: // ADD or SUB
				res = (int) addsub;
				break;
			case 0x1: // SLL
				res = op1 << op2;
				break;
			case 0x2: // SLT
				res = ((int) addsub) >>> 31;
				break;
			case 0x3: // SLTU
				res = (((int) addsub) >>> 31) ^ (op1 >>> 31) ^ (op2 >>> 31);
				break;
			case 0x4: // XOR
				res = op1 ^ op2;
				break;
			case 0x5: // SRL or SLA
				res = !sub ? op1 >>> op2 : op1 >> op2;
				break;
			case 0x6: // OR
				res = op1 | op2;
				break;
			case 0x7: // AND
				res = op1 & op2;
				break;
			}
			System.out.print((test & 0x1) + "\t");
			print32(res);
			System.out.print("\t" + ((addsub >>> 32) & 0x1));
			System.out.println("\t" + (res == 0 ? 1 : 0));
		}
	}

	public static void print32(int num)
	{
		printLsbs(num, 31);
	}

	public static void printLsbs(int num, int msb)
	{
		for(int bit = msb; bit >= 0; bit--)
		{
			System.out.print((num >> bit) & 0x1);
			if(bit % 4 == 0 && bit != 0)
				System.out.print(' ');
		}
	}

	public static long widen(int narrow)
	{
		return narrow & ((0x1L << 32) - 1);
	}

	public static String decodeOpc(int opc, boolean sub)
	{
		switch(opc) {
			case 0x0:
				return sub ? "SUB" : "ADD";
			case 0x1:
				return "SLL";
			case 0x2:
				return sub ? "SLT" : "???";
			case 0x3:
				return sub ? "SLU" : "???";
			case 0x4:
				return "XOR";
			case 0x5:
				return sub ? "SRA" : "SRL";
			case 0x6:
				return "OR";
			case 0x7:
				return "AND";
			default:
				return "";
		}
	}
}
