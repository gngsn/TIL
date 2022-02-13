package visitor;

interface Visitable {
    String accept(Visitor visitor);
}
