package prototype.cookie;

public class CookieMachine {

    private Cookie cookie;

    public CookieMachine(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie makeCookie() {
        System.out.println("Coconut Cookie를 복제합니다.");
        return (Cookie) cookie.clone();
    }

    public static void main(String[] args) {
        final int CREATE_NUM = 10;

        Cookie prototype = new CoconutCookie();
        CookieMachine cookieMachine = new CookieMachine(prototype);

        for (int i =0; i < CREATE_NUM; i++) {
            Cookie clone = cookieMachine.makeCookie();

        }
    }
}
