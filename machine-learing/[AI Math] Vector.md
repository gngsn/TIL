#Mathematics for Artificial Intelligence



## Vector

벡터는 숫자를 원소로 가지는 리스트(list) 또는 배열 (array) 입니다.

<br/>

세로로 나열 - 열 벡터

가로로 나열 - 행 벡터

보통 행 벡터 사용!

<br/>

### 벡터 ? 

✔️ 벡터는 공간에서 **한 점**을 나타냄

✔️ 벡터는 원점으로부터 상대적 **위치**를 표현

<img src="./img/vector1.png" alt="vector1" style="zoom:40%;" />

<br/>

✔️ 숫자($\alpha$)를 곱해주면 **길이**만 변함 (스칼라곱) 

<img src="./img/vector2.png" alt="vector2" style="zoom:40%;" />



$\alpha > 1$ 일 때, 벡터의 길이는 늘어나고,  $\alpha < 1$ 일 때, 벡터의 길이는 줄어든다. 단,  $\alpha < 0$ 일 때, 벡터는 반대 방향이 된다.

<br/><br/>

### 벡터의 연산

✔️ 벡터끼리 같은 모양을 가진다면 덧셈과 뺄셈을 할 수 있음

<br/>

<img src="./img/vector3.png" alt="vector3" style="zoom:30%;" />

<br/><br/>

✔️ 벡터끼리 같은 모양을 가지면 성분곱(Hadamard product)

<br/>

<img src="./img/vector4.png" alt="vector4" style="zoom:30%;" />

<br/><br/>

### 벡터의 노름(norm)

✔️ 벡터의 노름(norm)은 **원점에서부터의 거리**

다양한 방식으로 노름을 정의함. 임의의 차원 $d$에서 원점에서 부터의 거리

노름(norm)의 기호 : $∥ ⋅ ∥$ 

<br/>

#### $L_1\ norm$ 

각 성분의 변화량의 절대값을 모두 더함

<img src="./img/vector5.png" alt="vector5" style="zoom:40%;" />

$||x||_1 = \sum_{i=i}^{d}|x_i| = L_1norm$

<br/>

``` python
# L1 numpy로 표현해보기
def l1_norm(x):
  x_norm = np.abs(x)
  x_norm = np.sum(x_norm)
  return x_norm
```

<br/><br/>

#### $L_2\ norm$ 

피타고라스 정리를 이용해 **유클리드 거리를 계산**

<img src="./img/vector6.png" alt="vector6" style="zoom:40%;" />

$||x||_2 = \sqrt {\sum_{i=i}^{d}|x_i|^2} = L_2norm$

<br/>

``` python
# L2 numpy로 표현해보기
def l2_norm(x):
  x_norm = x * x
  x_norm = np.sum(x_norm)
  x_norm = np.sqrt(x_norm)
  return x_norm
```

<br/><br/>

#### $L_1\ norm$ , $L_2\ norm$ 어떤 걸 사용해야할까?

<br/>

노름의 종류에 따라 **기하학적 성질**이 달라진다. 머신러닝에선 각 성질들이 필요할 때가 있으므로 **둘 다 사용**.



<br/>

<img src="./img/vector7.png" alt="vector7" style="zoom:40%;" />

<br/>

기계학습에서 사용되는 **목적**에 따라 달라짐.

$L_1\ norm$ : $Robust$학습,$Lasso$회귀 등에 사용됨

$L_2\ norm$ : $Laplace$근사,$Ridge$회귀 등에 사용됨

<br/><br/>

### 두 벡터 사이의 거리

두 벡터 사이의 거리를 계산할 때는 벡터의 **뺄셈**을 이용

<br/>

<img src="./img/vector8.png" alt="vector8" style="zoom:30%;" />

✔️ **X와 Y사이의 거리** : 두 노름의 차



### 두 벡터 사이의 각도

각도는 $L_2\ norm$에서만 연산 가능 👉🏻 $d$ 차원에서 계산할 수 있도록 사용













