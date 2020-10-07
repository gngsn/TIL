# Shell



```
$cat /etc/shells

/bin/sh 							<-- 명령어 해석기. 커널에서 분리된 별도의 프로그램. 
/bin/ksh							<-- AT&T 사의 데이비드 콘이 개발. 명령어 완성 기능. 히스토리 기능.
/bin/bash							<-- ksh과 csh의 장점을 결합. (Bourne Again Shell) 리눅스 표준
/sbin/nologin
/bin/csh							<-- 버클리 대학의 빌조이가 개발. (강력한 프로그램 작성 기능. 히스토리, 별명, 작업 제어 등)
/bin/tcsh							<-- 편집 기능을 제공하는 확장 C셸
```



## chsh & usermod 

`chsh -s [변경 셸]` 은 새로운 셸로 변경

`usermod -s [변경 셸] [계정 명]` 은 새로운 셸로 변경

