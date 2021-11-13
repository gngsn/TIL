## Docker



**서비스를 제공하기 위한 서버 서버란 ?** 

우리가 작성한 코드를 실제로 서비스하는 컴퓨터입니다.



**서버에서 작성한 코드를 실행하기 위해서는 다양한 작업을 해주어야 합니다.** 

👉 os 설치 , 실행환경 세팅 (java, python 등 ), 어플리케이션 코드( 혹은 빌드 ) 다운로드 , 필요한 다른 코드 다운로드 (library) 등등의 과정이 필요합니다



**서비스를 운영할 때 필요한 서버 운영작업**

- 서버를 운영한다는 것은1)보안 결함이 없어야 하고 2 필요할 때 새로운 코드나 ,라이브러리 추가가 가능해야 합니다.

- 많은 수의 요청을 처리하기 위해서는 많은 수의 서버에 대해서 운영을 해주어야 합니다.



실제 서비스를 제공하는 서버의 경우 안정성을 위해 최소2대에서 많게는100+이상의 서버를 관리하게 됩니다.

위의 운영을 쉽게하기 위해서 **스크립트**를 작성하여 사용합니다



- 서비스를 운영하다 보면 이외에도 다양한 요청이 들어올 수 있습니다.



```
ubuntu말고 centos서버를 사용해보고 싶어요!
```

```
python3.8에서python3.9로 업데이트 하고 싶어요!
```

```
새로운 서비스는 Java로 만들어 보고 싶어요!
```



- 서비스가 커지고, 서버가 늘어나면 어플리케이션들을 원하는 대로 실행하는 것의 난이도가 기하급수적으로 상승합니다.

Q. 왜 난이도가 있나요? 

A. 각 상황에 따른 스크립트를 작성하고 .모든 서버에서 원하는 상태를 만들어주어야 하기 때문입니다.

Q. 서버를 원하는 상태로 만들어야 하나요?

A. 코드가 아예 작동을 안 하는 경우가 생기는 경우도 문제지만 , 간헐적으로 문제가 생기는 경우 이슈를 찾는데 드는 시간이 매우 늘어납니다

결론 )원하는 프로그램을 실행하는게 어렵다!





### Docker 가 문제를 해결한 방법 #1

\10) Docker의 발상

- 문제 정의) 원하는 프로그램을 실행하는 게 어렵다!
  - 서비스를 운영하기 위해서 단순히 코드를 짜는 것뿐만 아니라 서버도 운영을 해주어야 한다. 그런데 서버를 운영하는건 점점 복잡해진다.
- 해결 방안) 서버 운영에서 인프라 관리와 어플리케이션 작성을 분리하면 어떨까?
  - 실행환경, 실행하는 코드, 필요한 라이브러리, 설정 파일을 한곳에 정의해두면 어떨까? ⇒ **도커 이미지**
  - 서버에서는 간단하게 이미지를 가지고 실행만 시키자!

- 이런 패러다임을 적용시키기 위해서 해결해야 할 3가지 문제

  1. 이미지를 생성하는 법

     <aside> 👉 일관성 있게 이미지를 만들 수 있고, 어플리케이션을 실행하는 데 필요한 모든 것을 설정할 수 있는 이미지

     </aside>

  2. 이미지를 공유하는 법

     <aside> 👉 이미지가 어디에서든 잘 전달할 수 있게 만들어 필요한 곳에서 사용할 수 있게 한다.

     </aside>

  3. 이미지를 실행하는 법

     <aside> 👉 일관성있게 이미지를 실행할 수 있다.

     </aside>

- 그래서 도커란?

  <aside> 💡 도커란 원하는 프로그램을 쉽게 실행하기 위한 플랫폼

  </aside>



![image-20211113165026137](/Users/gyeongseon/Library/Application Support/typora-user-images/image-20211113165026137.png)





docker의 구성요소 (출처: https://docs.docker.com/get-started/overview/)

- docker image & container
  - image는 어플리케이션을 실행하기 위한 필요한 모든 것이 생성되어 있는 파일입니다.
  - container는 image를 사용하여 실행한 것입니다. 프로세스의 독립성을 보장하기 위해서 네트워크나 저장소가 분리되어 있습니다.
- docker registry
  - docker image를 저장하는 공간
- dockerd
  - docker API requests를 받아서 docker object(image, container등)를 관리
- docker client
  - 사용자가 docker를 사용하기 위해서 사용하는 cli





기존 기술과 다른 부분 

- 각각의 application을 원하는 환경에서 실행하는 기술이 예전에는 없었나?
  - 비슷한 것으로 가상 머신(VM) 기술이 있었습니다.

![Virtual Machine과 docker의 차이](/Users/gyeongseon/Library/Application Support/typora-user-images/image-20211113165001804.png)

**Virtual Machine과 docker의 차이**

- 기존 가상화 기술(VMware, VirtualBox) 은 하이퍼바이저를 사용해서 여러개의 운영체재를 하나의 호스트에서 생성해 사용하는 방식
  - 시스템 자원을 가상화하고, 공간을 생성하는 작업은 하이퍼바이저를 거치기 때문에 성능의 손실이 발생합니다.
  - OS까지 포함해야하므로 이미지의 크기 또한 커집니다.
  - 완벽한 OS를 생성할 수 있다는 장점은 있지만 성능 손실이 있음
- 도커는 리눅스의 자체 기능인 chroot, namespace, cgroup기술을 사용해서 프로세스 단위의 격리 환경을 만들기 때문세 성능 손실이 거의 없습니다.
  - OS를 따로 가지고 있는 것이 아니기 때문에 이미지의 크기도 매우 작아집니다.
  - host OS의 kernel을 공유해서 사용하므로 성능 손실이 거의 없습니다.





### docker run

```
docker run [options] {image_name} [command]
```

docker run은 docker image를 사용해서 container를 실행하는 명령어입니다. 필요에 따라 다양한 옵션들과 함께 사용할 수 있습니다.



`-p host_port:container_port`  : 컨테이너의 포트와 호스트의 포트를 연결해줍니다. 

`-d` : detach. 실행한 docker container가 백그라운드에서 실행된다는 의미입니다.





#### docker container process

```
$ docker ps
CONTAINER ID   IMAGE                  COMMAND           CREATED          STATUS          PORTS                                       NAMES
74420343a892   docker-test:version1   "python app.py"   20 minutes ago   Up 20 minutes   0.0.0.0:5000->5000/tcp, :::5000->5000/tcp   condescending_joliot
```

- CONTAINER_ID: container의 고유 ID
- IMAGE: container를 띄우는데 사용한 image 이름
- COMMAND: container에서 실행한 command
- CREATED: conatiner가 생성된 시간
- STATUS: container의 상태
- PORT: port를 publish 하는 경우 어떻게 되어 있는지
- NAMES: container의 이름



#### docker container stop

```
docker stop {container_id|conatinaer_name}
```

```
docker kill {container_id|conatinaer_name}
```



- 두개의 명령어가 비슷한 결과를 보이지만 조금 다릅니다.
  - stop의 경우에는 SIGTERM이라는 신호를 보냄
  - kill의 경우에는 SIGKILL이라는 신호를 보냄





#### docker container 되살리기

```
docker restart {container_id|container_name}
```





#### 실행중인 container에서 명령어 실행하기

```
docker exec [options] {container_id|container_name} [command]
```





#### docker container 삭제하기

```
docker rm {container_id}
```







### Docker image 



docker image는 layer라는 개념이 있는데 각가의  이미지가 어떻게 만들어졌는지 docker history로 확인할 수 있음

base 이미지에서부터 하나하나 명령어가 실행될 때마다 각각의 레이어가 생성되고, 

layer가 docker image는 dockerfile로 부터 일관성 있게 생성되기 때문에 동일한 환경에서 동일한 command 로 빌드했을 때, 동일한 결과물을 가져와 사용하기 때문에 빌드하는 시간을 단축시킨다.



Docker image를 만들기 위해서는 Dockerfile이 필요합니다.

Dockerfile이란 docker image를 어떻게 생성할 것인지를 정의한 파일입니다.



``` dockerfile
FROM python:3.8

ADD requirements.txt .

RUN pip install -r requirements.txt

ADD templates templates

ADD app.py .

CMD ["python", "app.py"]
```

<aside> 👉 `FROM` 이란 Docker image를 생성할 때 기본으로 사용할 base image를 적는 부분입니다.

</aside>

<aside> 👉 `ADD src dst`  호스트 머신에 있는 파일이나 폴더를, dst라는 위치에 저장합니다.

</aside>

<aside> 👉 `RUN script` 는 script를 실행합니다

</aside>

<aside> 👉 `CMD` 는 생성된 docker image를 실행할 때 자동으로 실행되는 커맨드 입니다.

</aside>



#### Docker image build

```
docker build [OPTIONS] PATH

ex) docker build -t docker-memo:version1 .
```



👉 `-t` 옵션을 사용하면 image에 원하는 이름을 붙일 수 있습니다. `{image_name}:{tag}` 의 형태로 사용하며, `{tag}`를 붙이지 않을경우 자동으로 `latest`가 됩니다. `-t` 옵션을 사용할 때 동일한 `image_name:tag` 를 사용할경우 **override**되게 됩니다. 조심하세요!

👉 `.` 는 docker build를 어느 위치에서 실행할 것인지 정의합니다. 이 위치에 따라 `ADD` 커맨드에서 호스트의 파일 위치를 사용하는게 바뀔 수 있습니다.





#### Docker image 목록 확인하기

``` 
docker images
```



#### Docker image 실행하기

```
docker run -d -p 5000:5000 docker-memo:version1
```





### 어플리케이션 업데이트하기

어플리케이션을 개발하는 과정에서 코드를 업데이트하는 시나리오를 가볍게 진행해보려고 합니다. 먼저, index.html을 마음대로 바꿔보세요!





### docker hub

- 도커 이미지의 이름을 repository와 동일하게 만들어주어야 합니다.

  - docker image 빌드하기

    ```bash
    docker build -t {user_id}/docker-memo:version2 .
    ```

- 도커 이미지를 올리기 전에 권한을 얻어야 합니다!

  - docker hub에 로그인

    ```bash
    docker login
    # 후에 나오는 username, password에 치면 됩니다!
    ```

- 진짜 마지막으로 docker registry에 이미지를 올려봅시다.

  - docker image push 하기

    ```bash
    docker push wellshs/docker-memo:version2
    ```





