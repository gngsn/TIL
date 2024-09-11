## Site Reliability Engineering
<i><small>How google runs production systems</small></i>

<br/>

### Part I. Introduction

- CHAPTER 01. Introduction
- [CHAPTER 02. The Production Environment at Google, from the Viewpoint of an SRE](https://github.com/gngsn/TIL/tree/master/books/sre/chapter02)

### Part II. Principles

- [CHAPTER 03. Embracing RiskEmbracing Risk](https://github.com/gngsn/TIL/tree/master/books/sre/chapter03)
- [CHAPTER 04. Service Level Objectives](https://github.com/gngsn/TIL/tree/master/books/sre/chapter04)
- [CHAPTER 05. Eliminating Toil](https://github.com/gngsn/TIL/tree/master/books/sre/chapter05)

<details>
<summary><b>✔️ TL;DR</b></summary>

_: CHAPTER 03 ~ 05_

1️⃣ 시스템의 **신뢰성**을 높이는 데 소요되는 **비용은 비례해서 증가하지 않음**. 반대로, **신뢰성을 향상**시키는 것이 **비용을 100배 증가시키기도 함**

2️⃣ 서비스 별로 **서비스 성격에 맞는 지표를 살펴보고 분석하는 것이 중요**

3️⃣ **지표는 평균보다는 분포(distribution)가 더 중요**

**4️⃣ 목표치 선택하기**
  1. 현재 성능을 기준으로 목표치를 설정하지 말 것
  2. 최대한 단순하게 생각할 것
  3. 자기 만족에 얽매이지 말 것
  4. 가능한 적은 수의 SLO를 설정할 것
  5. 처음부터 완벽하게 하려고 하지 말 것

**✔️ 새로 알게된 내용**
- **SLI**: Service Level Indicator, 서비스 수준 지표
  - 서비스 수준을 판단할 수 있는 여러 지표를 정량적으로 측정한 값
- **SLO**: Service Level Objective, 서비스 수준 목표
  - SLI로 측정된 서비스 수준의 목표치 또는 일정 범위를 의미
- **SLA**: Service Level Agreement. 서비스 수준 협약
  - 서비스 수준 목표(SLO)를 달성했을 경우 or 달성하지 못했을 경우의 보상에 대한 사용자와의 명시적 또는 암묵적인 계약

</details>
<br/><br/>