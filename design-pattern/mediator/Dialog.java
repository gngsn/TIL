package mediator;

public class Dialog implements Mediator {

    public String title;
    public Checkbox isJoinForm;

    public Textbox username;
    public Textbox password;

    public Button ok;
    public Button cancel;

    public Checkbox remeberMe;

    public Dialog() {

        super();

        title = "Login";
        isJoinForm = new Checkbox(this);

        username = new Textbox(this);
        password = new Textbox(this);

        ok = new Button(this);
        cancel = new Button(this);

        remeberMe = new Checkbox(this);
    }

    public void printCurrentStatus() {
        System.out.println("Title: " + title);
        System.out.println("isJoinForm: " + isJoinForm.checked());
        System.out.println("remeberMe: " + remeberMe.checked());
    }

    @Override
    public void notify(Component sender, String event) {

        // OK 버튼을 눌렀을 경우
        if(sender.equals(ok) && "click".equals(event)) {
            if(isJoinForm.checked()) {
                System.out.println("Validate - user info \n Sign up");
            } else {
                System.out.println("Login");
            }
        }
        // Sign Up 폼을 부르는 버튼을 눌렀을 경우
        else if(sender.equals(isJoinForm) && "check".equals(event)) {
            if(isJoinForm.checked()) {
                title = "User Join Form";
                System.out.println("show colleague components");
            } else {
                title = "Login";
                System.out.println("hide colleague components");
            }
        }
        // 정보 저장 버튼을 눌렀을 경우
        else if(sender.equals(remeberMe) && "check".equals(event)) {
            if(remeberMe.checked()) {
                System.out.println("set ID in cookie");
            } else {
                System.out.println("remove ID from cookie");
            }
        }
    }
}