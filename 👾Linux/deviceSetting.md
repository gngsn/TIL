# 장치 설정

<br/>

## 프린터 인쇄 시스템

### LPRng

리눅스 초기. 버클리 프린팅 시스템으로 BSD 계열 유닉스에서 사용하기 위해 개발

프린터 스폴링과 네트워크 프린터 서버를 지원. 설정 파일 /etc/printcap

<br/>

### CUPS

애플이 개발한 오픈 소스 프린팅 시스템.  유닉스 계열의 운영체제의 시스템을 프린터 서버로 사용 가능하게 함.

HTTP 기반의 IPP(Internet Printing Protocol)를 사용하여 프린터를 웹 기반으로 제어

LPRng는 515포트. CUPS는 631포트를 사용

설정 디렉터리는 /etc/cups. 사용자 및 호스트 시반의 인증을 제공

**/etc/cups/cupsd.conf** : 프린터 데몬 환경 설정 파일

**/etc/cups/printers.conf** : 프린터 큐 관련 환경 설정 파일. Ipadmin 명령을 이용하거나 웹을 통해 제어

**/etc/cups/classes.conf** : CUPS 프린터 데몬의 클래스 설정 파일

**cupsd** : CUPS의 프린터 데몬

<br/><br/>

## 프린터 설정

Red Hat Enterprise Linux 상에서의 인쇄 설정.

직렬 포트 : /dev/Ip0 파일로 사용 가능

USB 포트 : /dev/usb/Ip0 파일로 사용 가능

<br/><br/>

## 사운드 카드 설치 및 설정

### OSS (Open Sound System)

리눅스 및 유닉스 계열 운영체제에서 사운드를 만들고 캡처하는 인터페이스. 표준 유닉스 장치 시스템 콜에 기반을 둔 것. ALSA로 대체 됨

<br/>

### ALSA (Advanced Linux Sound Architecture)

리눅스 커널 요소. Jaroslav Kysela에 의해 시작. GPL 및 LGPL 라이센스 기반으로 배포

환경 설정 파일은 /etc/asound.state

<br/><br/>

## 스캐너 설치 및 설정

### SANE ( Scanner Access Now Easy )

이미지 관련 하드웨어를 제어하는 API

**SCSI scanner** : /dev/sg0, /dev/scanner 로 인식

**USB scanner** : /dev/usb/scanner, /dev/usbscanner 로 인식

<br/>

### XSANE ( X based inerface for the SANE )

SANE 스캐너 인터페이스를 이용하여 X-Windows 기반의 스태너 프로그램

**xsane** 명령어를 입력하면 실행

캡처한 이미지에 수정 작업을 할 수도 있음.

<br/><br><br/>

# 주변 장치 활용

<br>

**BSD 계열 프린터 명령어들**

## lpr 

프린터 작업 요청. 

**-# n** : 인쇄할 매수를 지정 (1~100)

**-m** : 작업이 완료되면 관련 정보를 E-mail로 전송

**-P 프린트 명** : 기본 설정 프린트 이외에 다른 프린터 지정

**-r** : 출력한 뒤에 지정한 파일 삭제

<br>

## lpq

프린터 큐에 있는 작업 목록을 출력한다.

**-a** : 설정되어 있는 모든 프린터의 작업 정보를 출력

**-l** : 출력 결과를 자세히 출력

**-P 프린트 명** : 특정 프린트를 지정

<br>

## lprm

프린터 큐에 대기 중인 작업을 삭제. 취소할 작업 번호를 입력. 없으면 가장 마지막에 요청한 작업이 취소

**-** : 프린터 큐에 있는 모든 작업 취소

**-U** : 지정한 사용자의 인쇄 작업 취소

**-P 프린트 명** : 특정 프린트를 지정

**-h 서버** : 지정한 서버의 인쇄 작업 취소

<br>

## lpc

라인 프린터 컨트롤 프로그램. 프린터나 프린터 큐를 제어

<br><br><br>

System V 계열 프린터 명령어들

### lp 

프린터 작업 요청

**-n 값** : 인쇄할 매수를 지정 (1~100)

**-d 프린트 명** : 기본 설정 프린트 이외에 다른 프린터 지정

<br>

### lpstat

프린터 큐의 상태를 확인

**-p** : 프린터의 인쇄 가능 여부를 출력

**-t** : 프린터의 상태 정보를 출력

**-a** : 프린터가 허가(access)된 사오항 정보 출력

<br>

### cancel

프린트 작업 취소. 취소할 요청 ID를 lpstat로 확인 후 삭제.

**-a** : 프린터 큐에 있는 모든 작업 취소

<br>

<br>

**alsactl** : ALSA 사운드 카드를 제어

**alsamixer** : 커서(ncurses) 라이브러리 기반의 오디오 프로그램

**cdparanoia** : 오디오 CD로부터 음악 파일을 추출 시 사용

**sane-find-scanner** : SCSI 스캐너와 USB 스캐너 관련 장치 파일을 찾아줌

**scanimage** : 이미지를 스캔

**scanadf** : 자동 문서 공금 장치가 장착된 스캐너에서 여러 개의 사진을 스캔

**xcam** : GUI 기반으로 평판 스캐너나 카메라로부터 이미지 스캔







