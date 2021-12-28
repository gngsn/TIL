package builder;

public abstract class Builder {
    protected Algorithm algorithm;

    public Builder setAlgorithm(Algorithm algorithm) {
        System.out.println("빌드 객체를 저장합니다.");
        this.algorithm = algorithm;

        return this;
    }

    public Computer getInstance() {
        return this.algorithm.getInstance();
    }

    public abstract Builder build();
}
