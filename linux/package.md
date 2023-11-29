# Software install and delete



## 레드헷 계열

업체 : Fedora, CentOS, RHEL, openSUSE, Mandirva

패키지 툴 : rpm, yum





rpm은 -i, -e 로 설치, 삭제하고 yum 은 install, remove처럼 설치 삭제

### RPM

Redhat Package Manager. 

```
패키지명-버전-릴리즈번호.[페도라버전/CentOS버전.]아키텍처.rpm
```

ex) sendmail-8.14.3-5.fc11.i586.rpm

ex) kernel-3.10.0-327.el7.x86_64.rpm



**-i** : 설치

**-h** : 해시(#) 마크 표시로 프로그래스 바 나옴

**-v** 진행 과정을 메시지로 표시

**-vv** 메시지를 상세히 명시

**-V** : verify의 약자, 검증할 때 사용

**-a** : 모든 패키지 검사

**-U** : 기존의 패키지 업그레이드

**-e** : 제거

**-q** : 패키지 설치 여부 확인

**-f  \<file\>** \<file\> 을 포함하는 패키지에 대해 질문을 수행

**-F  \<file\>** 위와 같은데 파일이름을 표준 입력에서 읽음



**--nodeps** 의존성 관계를 무시하고 설치

**--oldpackage** : 구 버전으로 다운그레이드

**--replacepkgs** : 패키지 재설치

**--replacefiles** : 이미 있던 패키지를 덮어쓰면서라고 강제 설치

**--force** : --replacefiles, --replacepkgs, --oldpackage 모두 사용





### yum 

Yellowdog Updater Modified. RPM의 의존성 문제를 해결하기 위한 유틸리티.

| 명령         | 기능                           |
| ------------ | ------------------------------ |
| install      | 설치 여부 물으면서 설치        |
| groupinstall | 패키지 그룹 설치               |
| -y install   | 설치 여부를 묻는 질문에 Yes    |
| update       | install 이랑 똑같음            |
| localinstall | 인터넷 사용 X. *.rpm 파일 설치 |
| remove       | 패키지 제거                    |
| groupremove  | 패키지 그룹 제거               |
| info         | 요약 정보                      |
| list         | 패키지 전체 정보               |
| grouplist    | 그룹 정보                      |
| check update | 업테이트 가능 패키지 목록      |
| search       | 패키지 검색                    |
| check-update | 업데이트가 필요한 패키지 출력  |
| history      | 작업 이력 확인                 |





## 데비안 계열

업체 : Debian, Ubuntu, Xandros, Linspire

패키지 툴 : dpkg, apt-get, aptitude



### dpkg

저레벨 패키지 관리 툴. 의존성 문제 있음

```
패키지명_버전-릴리즈버전-리버전_아키텍처.deb
```



**-i** : 패키지 설치. 정상적인 설치가 안되는 경우 발생

**-r ** 설치된 패키지만 삭제

**-P ** 패키지와 설정 정보 모두 삭제

**-s** : 패키지 상황 정보 (버전, 관리자, 간략 설명 등)

**-L** : dpkg가 설치한 모든 파일 목록 확인





### apt-get

asvanced packaging tool get

의존성과 출동 문제를 해결하기 위해 `/etc/apt/sources.list` 파일을 참조



**install**  새 패키지 설치

**dist-upgrade** : 의존성을 검사하며 설치

**update** : 새 패키지 목록 가져오기

**upgrade** : 업그레이드 실행

**remove** : 패키지 제거

**-y** : 모든 질문을 표시하지 않고 '예'로 자동 처리

**-u** : 업그레이드한 패키지 목록 표시

**-V** : 자세한 버전 표시









# 소스 파일 설치



### tar 

**z** : gzip으로 압축 or 해제 

**j** : bzip2으로 압축 or 해제

**J** : xz으로 tar.xz에 사용

**v** : 파일의 정보를 화면에 출력

**c** : tar 생성

**x** : 원본 파일로 복원

**d** : 아카이브에 있는 파일과 비교

**r** : 아카이브된 파일의 마지막 부분에 파일 추가

**t** : 파일 목록 나열

**u** : 새로운 파일로 업데이트

**f** : 아카이브 파일명을 지정. 디폴트 파일명으로 지정

**P** : 절대 경로 정보 유지

**--exclude** : 특정 디렉터리를 제외하고 묶음





### compress / uncompress ( .Z )

**-d** : 파일 압축을 해제

**-c** : .Z 가 아닌 지정 파일로 생성

**-v** : 압축 진행 과정을 화면에 표시

**-V** : compress 명령어 버전 정보 출력



### gzip / gunzip /zcat ( .gz )  &  bzip2 / bunzip2 / bzcat ( .bz2 )

**-d** : 파일 압축을 해제

**-l** : 압축된 파일의 정보를 나타냄

**-v** : 압축 진행 과정을 화면에 표시



### xz / unxz ( .xz )

**-z** : 강한 파일 압축

**-d** : 강한 파일 압축 해제

**-v** : 압축 진행 과정을 화면에 표시





### 소스 코드 설치

컴파일 순서 :  설치 파일의 환경설정(./configure), 컴파일(make), 파일 설치(make install)









