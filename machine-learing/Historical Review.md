<small>Deep Learning Basic</small>

# Leture 1: Historical Review

Deep Learning? 아주 다양한 연구주제가 있음

👉🏻 Disclaimer: 그림과 같이 장님이 코끼리를 만지는 것.

<br/>

## Introduction

what make you a **good deep learner**?

- **Implementation Skills** : 구현 능력 (with Tesorflow, Pytorch 등...)

- **Math Skills** (Linear Algebra, Probability - 선형 대수)

- **Knowing <small>a lot of recent</small> Papers**

<br/>

## Historical Review

<br/>

### Deep Learning

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 3.59.29.png" alt="스크린샷 2021-11-25 오후 3.59.29" style="zoom:50%;" />

<br/>

**Artificial Inteligence**

사람의 지능을 모방

<br/>

**Machine Learning**

 강아지와 고양이를 분류하는 문제를 풀고자 했을 때, 강아지와 고양이 이미지를 많이 모아서 이것을 자동으로 분류하는 알고리즘(모델)을 만드는 것 

<br/>

**Deep Learning**

Neural Network를 통해 데이터를 학습하는 세부적인 분야

<br/><br/>

## Key Components of Deep Learning

<br/>

### Data

*✔️ The **data** that the model can learn from*

위의 예시와 같이 강아지와 고양이를 분류하는 문제가 있을 때, 강아지와 고양이에 해당하는 대량의 데이터가 필요. 자연어를 처리한다고 하면 말뭉치 데이터가 필요하고 , 비디오를 다룬다고 하면 수많은 유투브와 같은 영상 데이터들이 필요하게 된다.

<br/>

**Data depend on the type of the problem to solve.**

데이터는 풀고자하는 문제에 의존하게 된다. 

<br/>

![스크린샷 2021-11-25 오후 4.11.32](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.11.32.png)

<br/>

**Classification**

강아지와 고양이를 분류하는 문제와 같이 개체를 분류하는 문제

<br/>

**Semantic Segmentation** 

각 이미지의 각 픽셀이 어느 클래스에 속하는지 (도로, 사람, 동물 - dense classification을 구별하는 문제) 를 분류하는 문제

<br/>

**Detection**

Semantic Segmentation과 비슷하지만 물체에 대한 바운딩 박스를 찾고 싶은 것. 

<br/>

**Pose Estimation**

사람이 있다고 하면, 이 사람의 3차원(혹은 2차원) skeleton 정보

<br/>

**Visual QnA**

질문이 던져졌을 때 그 답을 말할 수 있는 것.

<br/><br/>

### Model

*✔️ The **model** how to transform the data*

데이터를 통해 무엇인가를 학습하는 모델이 필요. 강아지를 분류하는 문제가 있을 때 강아지 이미지를 입력으로 넣으면 '강아지'라는 Label이 출력되게끔 만든다. 즉, 이미지를 라벨로 변경해주는 모델이 필요하게 됨. 모델을 정의하고 학습하는 것이 중요

<br/>

![스크린샷 2021-11-25 오후 4.21.09](/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.21.09.png)

<br/>

모델은 이미지가 주어지거나 텍스트, 단어가 주어어졌을 때 직접적으로 알고싶은 것(라벨 등) 바꿔주는 모델

같은 데이터나 테스크가 주어졌다고 해서 모델의 성질에 따라서 성능이 정해지고 더 나은 성능을 위해 테크닉들을 추가하고 그 테크닉들을 살펴본다.

<br/>

### Loss

✔️ The **loss** function that quantifies the badness of the model

모델을 학습하기 위한 loss function이 필요함

<br/>

**The loss function is a proxy of what we want to achieve**

모델과 데이터가 정해져있을 때, 어떻게 모델을 학습할지를 나타낸다.

결국 Neural Network형태를 가지게 되고, 각 레이블은 weight와 bias로 이루어져있고 그 weight의 각각 파라미터를 어떻게 업데이트할 것인지를 판단함

<br/>

**Regression Task** 

$MSE = \frac{1}{N} 
\sum_{i=1}^{N} 
\sum_{d=1}^{D} 
(y_i^{(d)}-\hat{y}_i^{(d)})^2 $

출력값과 Target점의 제곱을 최소화(일반적인 목표) - 제곱을 평균해서 줄이는 Mean Squared Error

<br/>

**Classification Task**

 $CE = -\frac{1}{N} 
\sum_{i=1}^{N}
\sum_{d=1}^{D}
y_i^{(d)} \log \hat{y}_i^{(d)}$

Crossentropy Loss

<br/>

**Probabillistic Task**

$MLE = \frac{1}{N} 
\sum_{i=1}^{N}
\sum_{d=1}^{D}
\log \mathcal{N}( y_i^{(d)} ;\log \hat{y}_i^{(d)}, 1) 
\ \ {\scriptsize (=MSE)}$

확률적인 모델을 사용한다면, 값에 대한 평균과 분산 등을 사용한다면 Maximum Likelihood Estimation 사용

<br/>

사실 Loss function이 줄어든다고 해서 우리가 원하는 결과를 얻는다고는 할 수 없음.

예를 들어 MSE를 사용해서 에러가 큰 값이 나올 때 전체 학습률을 줄어드는 영향을 줄 수 있음. 

그래서 절댓값을 주어주는 등의 어떻게 해결해나아가는 지를 알아보는 것이 중요.

따라서 항상 회귀는 MSE로 푸는 등 항상 위와 같이 사용해야하는 건 아니다.

<br/>

### <small>optimization</small> Algorithm

✔️ The **algorithm** to adjust the parameters to minimize the loss

loss function을 최소화하기 위한 알고리즘이 필요함.

<br/>

새로운 논문이나 연구를 봤을 때, 이 네 가지 요소를 확인하면 어떤 장점과 contrbution이 있는지 확인할 수 있어서 논문이나 연구를 더 잘 이해할 수 있음

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.47.47.png" alt="스크린샷 2021-11-25 오후 4.47.47" style="zoom:50%;" />



<br/>

최적화 방법 ? 네트워크를 어떻게 줄일지. SGD와 같이 first order method - 1차 미분한 정보를 활용한다.

<br/>

학습이 오히려 잘 안되게 하는 방법을 추가하기도 하는데, 그 이유는 loss function을 무식하게 줄이는 것보다 학습하지 않은 데이터에서 잘 동작하는 것이 목적. 

<br/><br/><br/>

## Overview

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.50.01.png" alt="스크린샷 2021-11-25 오후 4.50.01" style="zoom:50%;" />

<br/><br/>

### 2012 - AlexNet

2024 x 2024 이미지가 들어왔을 때 분류하는 것이 목표인 대회. AlexNet이 1등을 함.

그 전에는 고전적인 머신러닝의 조합이 1등을 했는데, AlexNet은 딥러닝을 사용해서 1등을 하고 나서 black magic ~ 

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.50.57.png" alt="스크린샷 2021-11-25 오후 4.50.57" style="zoom:33%;" />



<br/><br/>

### 2013 - DQN

알파고를 만든 Deepmind

 논문

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.52.16.png" alt="스크린샷 2021-11-25 오후 4.52.16" style="zoom:33%;" />

<br/><br/>

### 2014 - Encoder / Decoder

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.53.06.png" alt="스크린샷 2021-11-25 오후 4.53.06" style="zoom:33%;" />

단어의 연속이 주어졌을 때 다른 언어의 단어의 연속으로 바꿔주는 것.

중국어 단어 Sequence -> 영어 단어 Sequence

<br/><br/>

### 2014 - Adam Opimizer

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.54.16.png" alt="스크린샷 2021-11-25 오후 4.54.16" style="zoom:33%;" />

왜 아담을 사용할까?

결과가 잘나와서.. 일반적인 딥러닝 논문을 읽다보면 희한한 짓을 하는데,,, 

그렇게 하지 않으면 같은 결과가 복원이 안됨 (더 안좋은 성능이 나올 수 있음) .. Hyper parameter

-> 웬만하면 잘된다.

<br/><br/>

### 2015 - Generative Adverdarial Network



<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.58.10.png" alt="스크린샷 2021-11-25 오후 4.58.10" style="zoom:33%;" />



이미지를 어떻게 만들지 ~ 굉장히 중요.

<br/><br/>

### 2015 - Residual Networks

딥러닝의 딥러닝이 가능하게 해줌

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 4.59.44.png" alt="스크린샷 2021-11-25 오후 4.59.44" style="zoom:33%;" />

<br/>

너무 깊게 쌓으면 좋지 않은 성능이 나온다. 

근데 Residual Networks이 나온 다음엔 테스트 데이터의 성능이 좋게끔 만들어줌

-> 깊게 쌓을 수 있게 만들어줌

<br/><br/>

### 2017 - Transformer

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 5.01.22.png" alt="스크린샷 2021-11-25 오후 5.01.22" style="zoom:33%;" />

<br/><br/>

### 2018 - BERT <small>(fine-tuned NLP models)</small>

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 5.03.30.png" alt="스크린샷 2021-11-25 오후 5.03.30" style="zoom:33%;" />

<br/>

fine-tuned NLP models . Language Model

이전의 단어로 다음 단어를 예측하는 것.

만약 정말 멋진 뉴스를 쓰고 싶다. 근데 세상엔 뉴스에 대한 데이터가 그렇게 많지 않음. 이 때, 위키피디아와 같은 대용량 말뭉치 데이터를 사용해서 pretrained를 진행하고 fine tuning을 하는 것

<br/><br/>

### 2019 - BIG Language Models

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 5.06.00.png" alt="스크린샷 2021-11-25 오후 5.06.00" style="zoom:33%;" />

<br/>

BERT의 끝판왕. Language Model

아주 큰 파라미터를 가짐..그래서 Big -

<br/><br/>

### 2020 - Self Supevised Learning

<br/>

<img src="/Users/gyeongseon/Library/Application Support/typora-user-images/스크린샷 2021-11-25 오후 5.06.53.png" alt="스크린샷 2021-11-25 오후 5.06.53" style="zoom:33%;" />



2020년도의 트렌드를 하나만 꼽으라면 단연코 "Self Supevised Learning"

이 방법론은 이미지와 분류와 같은 분류 문제를 풀고 싶은데 학습데이터는 한정되어있음.

학습데이터 외에 라벨을 모르는 unsupevised 모델을 사용하겠다는 것.

라벨을 모르는 이미지라는 것들을 가지고 와서 강아지와 고양이를 구별하는 학습하는 것에 추가하겠다.

SimCLR이 굉장히 성공하고 그 이후 많은 연구들이 진행되고 있음

한가지 트렌드 - 이미지가 주어졌을 때 도메인 지식이나 시뮬레이터를 사용해서 데이터셋을 오히려 만들어내는 연구