public class StackA {

	public static void main(String[] args) {
		StackA stackA = new StackA();
		stackA.addTen(5);
	}

	private int addTen(int a) {
		int b = 10;
		int sum = a + b;
        printer(String.valueOf(sum));
        return sum;
	}
    
	private void printer(String string) {
		System.out.println(string);
	}
}
