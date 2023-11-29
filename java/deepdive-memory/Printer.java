public class Printer {

	public static void main(String[] args) {
		Printer printer = new Printer();
		printer.greeting("World");
	}

	private void greeting(String param) {
		String hello = "Hello ";
		String result = hello + param;
		System.out.println(result);
	}
}
