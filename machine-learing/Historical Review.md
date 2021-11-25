Deep Learning Basic

## Leture 1: Historical Review

Deep Learning?

아주 다양한 연구주제가 있음 -> Disclaimer: 그림과 같이 장님이 코끼리를 만지는 것.



### Introduction

what make you a **good deep learner**?

세가지 정도

- Implementation Skills 구현 능력 (with Tesorflow, Pytorch 등...)

- Math Skills (Linear Algebra, Probability - 선형 대수)

- Knowing <small>a lot of recent</small> Papers



### Historical Review



<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 3.59.29.png" alt="스크린샷 2021-11-25 오후 3.59.29" style="zoom:50%;" />



**Artificial Inteligence**

사람의 지능을 모방



**Machine Learning**

 강아지와 고양이를 분류하는 문제를 풀고자 했을 때, 강아지와 고양이 이미지를 많이 모아서 이것을 자동으로 분류하는 알고리즘(모델)을 만드는 것 



**Deep Learning**

Neural Network를 통해 데이터를 학습하는 세부적인 분야





### Key Components of Deep Learning

*✔️ The **data** that the model can learn from*

위의 예시와 같이 강아지와 고양이를 분류하는 문제가 있을 때, 강아지와 고양이에 해당하는 대량의 데이터가 필요

자연어를 처리한다고 하면 말뭉치 데이터가 필요하고 , 비디오를 다룬다고 하면 수많은 유투브와 같은 영상 데이터들이 필요하게 된다.



*✔️ The **model** how to transform the data*

데이터를 통해 무엇인가를 학습하는 모델이 필요.

강아지를 분류하는 문제가 있을 때 강아지 이미지를 입력으로 넣으면 '강아지'라는 Label이 출력되게끔 만든다.

즉, 이미지를 라벨로 변경해주는 모델이 필요하게 됨. 

모델을 정의하고 학습하는 것이 중요



✔️ The **loss** function that quantifies the badness of the model

모델을 학습하기 위한 loss function이 필요함



✔️ The algorithm to adjust the parameters to minimize the loss

loss function을 최소화하기 위한 알고리즘이 필요함.



새로운 논문이나 연구를 봤을 때, 이 네 가지 요소를 확인하면 어떤 장점과 contrbution이 있는지 확인할 수 있어서 논문이나 연구를 더 잘 이해할 수 있음





### Data

Data depend on the type of the problem to solve.

데이터는 풀고자하는 문제에 의존하게 된다. 

![스크린샷 2021-11-25 오후 4.11.32](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.11.32.png)

예를 들어 Semantic Segmentation 각 이미지의 각 픽셀이 어느 클래스에 속하는지 (도로, 사람, 동물 - dense classification을 구별하는 문제) 를 분류하는 문제



Detection

Semantic Segmentation과 비슷하지만 물체에 대한 바운딩 박스를 찾고 싶은 것. 



Pose Estimation

사람이 있다고 하면, 이 사람의 3차원(혹은 2차원) skeleton 정보



Visual QnA

질문이 던져졌을 때 그 답을 말할 수 있는 것.







### Neural Networks & Muti-Layer Perceptron





### Optimization Methods