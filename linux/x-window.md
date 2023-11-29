# X-Window

그래픽 사용자 인터페이스(GUI)를 제공. 플랫폼과 독립적으로 작동하는 그래픽 시스템

네트워크 프로토콜(X 프로토콜) 기반의 클라이언트/서버 시스템

서버는 **클라이언트들의 디스플레이에 관한 접근 허용**, **클라이언트 간의 자원 공유**, **네트워크 메시지 전달**, **클라이언트와 입풀력 기기와의 중계**를 담당한다.

오픈 데스크탑 환경으로는 KDE, GNOME, XFCE 이 있음

`system-config-display` X-윈도우 환경 설정 파일 `/etc/X11/xorg.conf` 를 호출

<br> <br>

**XProtocol** : X 서버와 클라이언트 사이의 메시지 타입. 메시지 교확 방법을 규정. Xlib와 Xtoolkit 인터페이스 사용

**Xlib** : C나 Lisp언어로 만든 XProtocol 지원 클라이언트 저수준 라이브러리. 

**XCB** : Xlib를 대체하기 위해 등장한 클라이언트 라이브러리. 향상된 스레드와 확장성이 뛰어남

**Glibc** : print()처럼 기본적인 함수부터 네트워크 연결을 위한 함수를 포함

**Xtoolkit** : Xlib로 스크롤바, 메뉴, 버튼 만들 때 효율성 문제 발생. 

​				Wiget과 Xt Intrinsic을 포함. 그 밖에 툴 킷으로는 XView, Xaw, Motfi, Qt, KTK 등이 있음

**XFree86** : 인텔X86 계열의 유닉스 운영체계에서 동작하는 X 서버. **XF86Config**가 설정 파일

<br><br>

## /etc/inittab

부팅 모드를 설정.

리눅스 사용 환경을 초기화 : 파일 시스템을 점검, 서비스 프로세스 관리, 가상 콘솔을 관리, 실행 level을 관리

```shell
이름:런레벨:옵션:process -옵션
# id:5:initdefault:
```

run level 3 : 텍스트 모드. 다중 사용자 모드

run level 5 : 그래픽 모드. 다중 사용자 모드

<br><br>

## starts -- [인자값]

X-윈도우를 실행하는 스크립트. 시스템 환경을 초기화하고 xinit을 호출

**터미널 변경** : Ctrl + Alt + F1 ~F4

**X-window 상태 전환** : Ctrl + Alt + F7

**X-window 강제 종료** : Ctrl + Alt + Back Space

<br><br>

## 환경 변수 DISPLAY

프로세스가 컴퓨터에서 동작하는 방식에 영향을 주는 동적인 값. X-윈도우의 위치를 지정할 수 있다.

```
export DISPLAY=IP주소:디스플레이번호.스크린번호
```

<br><br>

## 윈도우 매니저

윈도우 매니저는 **X window 상에서 창의 배치와 표현을 담당**

창 열기와 닫기, 창의 생성 위치, 창 크기 조정, 창의 외양과 테두리를 변화

twm -> fvwm -> AfterStep, mw, windowMaker(GNOME과 KDE에 통합), Mutter 등이 있음

<br><br>

## 데스크톱 환경 = 데스크톱 관리자

GUI 사용자에게 제공하는 인터페이스 스타일

**KDE** : Kool Desktop Environment 약자. Qt 툴킷 기반. 

**GNOME** : GNU Network Model Environment 약자. GTK+ 라이브러리를 기반. GNU 프로젝트.

**LXDE** : Light X11 Desktop Environment. 툴 킷으로는 GTK 2를 사용. 

**Xfce** : XForums Common Environment의 약자. 툴 킷으로는 GTK+ 2를 사용. 

|                   | KDE                | GNOME               |
| ----------------- | ------------------ | ------------------- |
| 그래픽 라이브러리 | Qt                 | GTK+                |
| 설정 항목 수      | 최대한 많이 보여줌 | 필요한 것만 보여줌  |
| 기본 텍스트에디터 | kate               | gedit               |
| 기본 브라우저     | Konquerer          | Web                 |
| 파일 탐색기       | Konquerer          | Nautilus            |
| 윈도우 매니저     | Kwin               | Multter or Metacity |

<br><br>

## 디스플레이 매니저

X window system 상에서 작동.X server의 접속과 세션 시작을 담당. 그래픽 로그인 화면 제공

**xdm** : X11에 도입

**kdm** : KDE에서 사용

**gdm** : Gnome에서 사용. GTK 라이브러리. CentOS 디스플레이 매니저.

**dtlogin** : CDE에서는 디스플레이 매니저로 사용.



## xhost 

X 서버에 접속할 수 있는 클라이언트를 지정하거나 해제

**xhost +** : X 서버에 모든 클라이언트 접속을 허용

**xhost -** : X 서버에 모든 클라이언트 접속을 금지

**xhost + IP 주소** : 해당 IP 주소를 가진 호스트의 접속을 허용

**xhost - IP 주소** : 해당 IP 주소를 가진 호스트의 접속을 금지



## xauth

.Xauthority 파일의 쿠키 내용을 추가, 삭제, 리스트를 출력

**list** 모든 쿠키값 리스트 확인

**xauth add 표시장치명 프로토콜명 쿠키값** : 지정된 프로토콜 및 키를 지정된 표시 장치의 권한 부여 파일에 추가





