package prototype.cookie;

public class CookieMachine {

    private Cookie cookie;//could have been a private Cloneable cookie;

    public CookieMachine(Cookie cookie) {
        this.cookie = cookie;
    }
    public Cookie makeCookie() {
        return (Cookie) cookie.clone();
    }
//    public Object clone() { }

    public static void main(String[] args) {
        Cookie tempCookie;
        Cookie prot = new CoconutCookie();
        CookieMachine cm = new CookieMachine(prot);
        for (int i=0; i<100; i++)
            tempCookie = cm.makeCookie();
    }
}
