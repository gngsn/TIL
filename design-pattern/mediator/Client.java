package mediator;

public class Client {
    public static void main(String[] args) {
        Dialog dialog = new Dialog();

        System.out.println("==== 1.Current state");
        dialog.printCurrentStatus();

        System.out.println();
        System.out.println("==== 2. Convert from login to user join form");
        dialog.isJoinForm.click();

        System.out.println();
        System.out.println("==== 3. submit user join");
        dialog.ok.click();

        System.out.println();
        System.out.println("==== 4. Convert from user join to login");
        dialog.isJoinForm.click();

        System.out.println();
        System.out.println("==== 5. Check remember me");
        dialog.remeberMe.click();

        System.out.println();
        System.out.println("==== 6. Do login");
        dialog.ok.click();
    }
}
