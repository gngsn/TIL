package chainOfResponsibility.middleware;

/**
 * ConcreteHandler. Checks whether there are too many failed login requests.
 */
public class ThrottlingMiddleware extends Middleware {
    private int limit;
    private int request = 0;

    public ThrottlingMiddleware(int limit) {
        this.limit = limit;
    }

    public boolean check(String email, String password) {
        request++;

        if (request > limit) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
        }
        return checkNext(email, password);
    }
}