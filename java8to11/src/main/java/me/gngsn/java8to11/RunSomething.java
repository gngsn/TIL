package me.gngsn.java8to11;

// 함수형 인터페이스에 붙이는 Annotation
@FunctionalInterface
public interface RunSomething {
    void doIt();

    // 추상 메소드가 하나'만' 있어야 함수형 인터페이스
//  void doItAgain();

//  public 생략 가능!
//  인터페이스이지만, 일반 메소드 생성 가능.
    public static void printName() {
        System.out.println("gngsn");
    }

//  default 메소드 생성 가능.
    default void printAge() {
        System.out.println("23");
    }

}
