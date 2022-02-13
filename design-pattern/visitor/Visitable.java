package visitor;

interface Visitable {
    public String accept(Visitor visitor);
}
