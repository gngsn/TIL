<small> | attention | 이 글은 한글 번역을 최대한 자연스럽게 하기 위해 의역과 문장을 재조정했습니다. </small> 

## Build Systems and Build Philosophy

> *Written by Erik Kuefler | Edited by Lisa Carey*

[🔗origin link](https://abseil.io/resources/swe-book/html/ch18.html)


만약 당신이 구글 엔지니어들에게 그들이 구글에서 일하는 것에 대해 (무료 음식과 멋진 구글 제품을 제외하고) 가장 좋아하는 것이 무엇인지 물어보면, "우리들의 빌드 시스템을 사랑한다"는 놀라운 대답을 들을 수 있습니다. 구글은 설립 이래로 (from the ground up: 근간으로 부터) 그들만의 독자적인 빌드 시스템을 구축하기 위해 엄청난 공학적 노력을 기울였고, 동시에 엔지니어들이 빠르고 안정적으로 코드를 빌드하는데 확신할 수 있도록 했습니다. 이러한 구글의 노력은 굉장히 성공적이어서 빌드 시스템의 메인 컴포넌트인 Blaze를 퇴사한 구글러들이 여러번 재구현해올 정도입니다. 2015년에 구글은 마침내 Blaze을 구현한 오픈 소스 Bazel을 공개했습니다.