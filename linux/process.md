# Proccess Utility



### 새로운 프로세스 만들기

하나의 프로세스가 다른 프로세스를 실행하기 위해 시스쳄 호출을 받는 방법 : fork(), exec()

#### ✔️ fork()

새로운 프로세스를 위해 메모리를 할당받아 **복사본 형태의 프로세스를 실행**하는 형태로 **기존의 프로세스는 그대로 실행**

**pstree -h** 를 하면 fork()에 의해 생성된 부모 - 자식 프로세스의 관계를 확인할 수 있음

#### ✔️ exec()

원래의 프로세스를 새로운 프로세스로 대체하는 형태로 호출한 프로세스의 메모리에 **새로운 프로세스의 코드를 덮어씌움**



## 데몬

주기적이고 지속적인 서비스 요청을 처리하기 위해 계속 실행되는 프로세스. 백그라운드 프로세스의 일종으로 보통 서버 역할을 하는 프호그램들이 이에 해당. 부팅 시 자동으로 실행되는 백그라운드 프로세스. p.170

| Standalone Daemon<br />(단독 실행 방식) | 서비스 요청이 들어오기 전에 서비스가 메모리에 상주하는 단독 실행 방식<br />자주 사용하는 서비스. |
| --------------------------------------- | ------------------------------------------------------------ |
| inetd Daemon (슈퍼 데몬)                | 다른 데몬 위에 존재하는 Standalone Daemon. <br />보안 상의 이유로 리눅스 커널 2.4부터는 **xinetd**가 inetd 역할을 수행 |
| inetd 타입 데몬                         | 다른 데몬이 활성화가 되어야만 해당 서비스 제공. ex) telnet, pop3, finger |

서비스 데몬을 구동하는 방법 : /etc/rc.d/init.d/nfs start



#### 시그널 (외워보기...)

| 번호 | 시그널  | 발생 조건                                                    |
| ---- | ------- | ------------------------------------------------------------ |
| 1    | SIGHUP  | 터미널과 연결이 끊어졌을 때 (signal hang up). 종료 후 재시작 |
| 2    | SIGINT  | ctrl + c. 종료                                               |
| 3    | SIGQUIT | ctrl + \ .코어덤프                                           |
| 6    | SIGABRT | abort(비정상 종료) 함수에 의해 발생                          |
| 9    | SIGKILL | 프로세스 강제 종료                                           |
| 13   | SIGPIPE | 종료된 소켓에 쓰기를 시도할 때                               |
| 14   | SIGALRM | 알람 타이머 만료. 코어덤프                                   |
| 15   | SIGTERM | kill 시스템 호출 시                                          |
| 17   | SIGCHID | 자식 프로세스가 종료 시. 무시                                |
| 18   | SIGCONT | 중지된 프로세스 실행 시. 무시                                |
| 19   | SIGSTOP | SIGCONT 시그널을 받을 때까지. 종료                           |
| 20   | SIGTSTP | ctrl + z . 프로세스 대기로 전환 ( 포그라운드 프로세스를 백그라운드로 바꿀 때 Suspend로 전환) |

ex) ctrl + c 입력 시 전송되는 시그널의 번호는?

ex) SIGTERM의 시그널 번호는





## ps 

process status. 현재 실행 중인 프로그램. CPU 사용도가 낮은 순서로 출력.

| 옵션 | 설명                                                         |
| ---- | ------------------------------------------------------------ |
| a    | 현재 실행중인 모든 프로세스 출력                             |
| e    | 모든 프로세서 정보                                           |
| u    | 사용자 이름과 프로세스 시작 시간 출력                        |
| x    | 접속된 터미널뿐만 아니라 사용되고 있는 모든 프로세스들을 출력 |
| -l   | 자세한 정보 출력 (F: 프로세스 플래그 값. S: 프로세스 상태 출력. UID, PPID, C: 짧은 시간 동안의 CPU 사용률) |





### kill

**-l** : 시그널 종류 나열.

**-s 시그널 번호 & 시그널명** : 전달할 시그널의 종류 지정

**-1** : -HUP, 프로세스를 재시작. hangup signal

**-9** : 프로세스를 강제로 종료

**-15** : 정상 종료



### nohup

로그아웃하거나 작업 중인 터미널 창이 닫혀도 실행 중인 프로세스를 백그라운드 프로세스로 계속 작업할 수 있음.

마지막에 '&' 표시 붙여야함



### cron

서비스 데몬은 **crond**, 관련 파일은 **/etc/crontab** 

**-l** : crontab에 설정된 내용 출력

**-e** : crontab을 작성하거나 수정

**-r** : crontab의 내용을 삭제

**-u** : 특정 사용자의 일정 수정



| 분   | 시   | 날   | 달    | 요일 | 명령어         |
| ---- | ---- | ---- | ----- | ---- | -------------- |
| *    | 4    | 1    | 1-8/2 | *    | /etc/backup.sh |

1월부터 8월까지 두 달 주기로 1일날 4시에 backup.sh 를 실행



NI(nice)의 범위는 -20 ~ 19. 기본값은 0. nice 명령어의 기본 조정치 수는 10(우선순위 낮춤)







