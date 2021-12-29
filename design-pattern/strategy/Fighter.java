package strategy;

public class Fighter extends Strategy {
    @Override
    public void attack() {
        if (this.delegate == null) {
            // 무기 선택 안한 경우
            System.out.println("맨손 공격");
        } else {
            this.delegate.attack();
        }
    }
}
