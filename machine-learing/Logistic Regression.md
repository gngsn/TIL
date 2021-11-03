``` python
# k-최근접 이웃 분류 클래스를 임포트한다.
from sklearn.neighbors import KNeighborsClassifier

# 최근접 이웃 개수를 3으로 지정하여 모델 객체를 만든다.
kn = KNeighborsClassifier(n_neighbors=3)

# 전처리된 훈련세트로 모델을 훈련시킨다.
kn.fit(train_scaled, train_target)

# 훈련세트와 테스트세트를 평가한다.
# ✍🏻 결정 계수라고 하는 결과값이 나옴. 점수가 썩 좋지 않음
print(kn.score(train_scaled, train_target))
print(kn.score(test_scaled, test_target))

# KNeighborsClassifier에서 정렬된 타깃값은 classes_ 속성에 저장되어 있다.
# 순서가 자동으로 알파벳순으로 매겨진다
print(kn.classes_)

# predict()는 타깃값으로 예측을 출력한다.
# 테스트 세트에 있는 처음 5개의 샘플의 타깃값을 예측해본다.
# ✍🏻 score과는 다르게 점수가 아니고 예측 데이터(classes)로 나온 다는 것
print(kn.predict(test_scaled[:5]))


import numpy as np
# 사이킷런의 분류모델은 predict_proba()로 클래스별 확률값을 반환한다.
# ✍🏻 proba -> probability 확률 '값'
proba = kn.predict_proba(test_scaled[:5])

# decimals=4은 반올림해서 소숫점 4번째 자리까지 표시한다.
print(np.round(proba, decimals=4))

# 인덱스 3번째 샘플의 최근접 이웃의 클래스를 확인해본다.
# n_neighbors=3 으로 모델을 생성했으므로 이웃의 수는 3개이다.
distances, indexes = kn.kneighbors(test_scaled[3:4])
print(train_target[indexes])

```





### 로지스틱 회귀

이름은 회귀이지만 `분류모델`. 선형 회귀와 동일하게 선형 방정식을 학습.



> $𝑧=𝑎×무게+𝑏×길이+𝑐×대각선+𝑑×높이+𝑒×두께+𝑓$

<small> a, b, c, d, e는 계수 또는 가중치. </small>

**시그모이드**<small>sigmoid function</small> **함수(로지스틱 함수)**를 사용해서 z를 확률 값(0~1사이값)으로 만듦.



![image-20211013102012835](/Users/gyeongseon/Library/Application Support/typora-user-images/image-20211013102012835.png)



\- $z \Rightarrow -∞$ : 0에 수렴

\- $z \Rightarrow ∞$ : 1에 수렴

\- $z = 0$ : 0.5





``` python
import numpy as np
import matplotlib.pyplot as plt

# -5와 5 사이에 0.1 간격으로 배열 z를 만든다.
Z = np.arange(-5, 5, 0.1)

# z 위치마다 시그모이드 함수를 계산한다.
# 지수함수 계산은 np.exp()를 사용한다.
phi = 1 /(1+np.exp(-Z))

plt.plot(Z, phi)
plt.show()

# [기초] 불리언 인덱싱(boolean indexing)
# 넘파이 배열은 True, False 값을 전달하여 행을 선택할 수 있다.
char_arr = np.array(['A', 'B', 'C', 'D', 'E'])
print(char_arr[[True, False, True, False, False]])

# train_target 배열에서 도미(Bream)과 빙어(Smelt)일 경우 True,
# 나머지는 False 값이 반환되어 bream_smelt_indexes 배열이 만들어진다.
bream_smelt_indexes = (train_target == 'Bream') | (train_target == 'Smelt')

# bream_smelt_indexes 배열을 이용해서 훈련세트에서 도미와 빙어 데이터만 골라낸다.
train_bream_smelt = train_scaled[bream_smelt_indexes]
target_bream_smelt = train_target[bream_smelt_indexes]
```



