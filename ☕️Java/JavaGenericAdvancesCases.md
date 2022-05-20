## Java Generic Advanced Cases

*본 글은 [java-generics-advanced-cases](https://levelup.gitconnected.com/java-generics-advanced-cases-d05db19b47d5?gi=e60afeece5d7)의 글을 번역한 내용입니다.*

<br/><br/>

우리가 작성하는 코드에서 제너릭을 가장 효율적이게 사용하는 방법은 무엇일까? 
지금부터 해당 주제를 다뤄보자.

<br/>

### Don’t Use Raw Types 
> Raw Type을 사용하지 마라.

이 내용은 당연하며, Raw 타입은 제너릭이라는 개념자체를 다룰 수 없다. 
Raw 타입을 사용하면 컴파일러가 타입 에러를 찾지 못한다.
뿐만아니라 아래와 같은 문제가 발생한다.




