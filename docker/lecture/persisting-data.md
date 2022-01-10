## Docker Container 데이터 유지

**Container 데이터를 유지시켜야 하는 상황들**

>  Docker container를 생성해야하는 경우가 존재 ( *EX. 새로운 이미지 혹은 새로운 서버를 제작할 때* ) 하지만 컨테이너 안에는 많은 정보가 존재하고, 그 중 데이터베이스를 사용하는 등 유지가 필요한 데이터들이 존재한다.

<br/>

기존에는 <small>*docker를 사용하지 않는 경우*</small> disk에 데이터를 올려두고 참조하는 식으로 사용을 했다.

docker에서도 데이터를 관리하는 방법이 다양하게 있는데, container는 생성될 때마다 실행될 때마다 새로운 파일 시스템을 얻기때문에 디스크에 데이터를 올려두고 참조하는 방식을 사용하기 어려움. 또, 동일 이미지를 사용하더라도 컨테이너끼리는 file system을 공유하지 않음. 이 때 필요한 개념이 **named volume**와 **bind mount**이다.  <small>두 개의 사용법이나 개념이 거의 비슷함!</small>

<br/><br/>

**mongodb docker로 띄우기**

따로 복잡한 설치 과정없이 빠르게 실행해볼 수 있다는 장점이 있음. docker run을 실행했을 때 **mongo_db ** 이미지가 없더라도 **docker hub**에 등록되어 있는 mongo_db 이미지를 가져와 실행해줌

```bash
$ docker run --name mongo_db -p 27017:27017 -d mongo
Unable to find image 'mongo:latest' locally
latest: Pulling from library/mongo
7b1a6ab2e44d: Pull complete 
90eb44ebc60b: Pull complete 
5085b59f2efb: Pull complete 
c7499923d022: Pull complete 
019496b6c44a: Pull complete 
c0df4f407f69: Pull complete 
351daa315b6c: Pull complete 
557b07ecd9d7: Pull complete 
a2dff157a5e3: Pull complete 
07d83e88231b: Pull complete 
Digest: sha256:4088649f737cf704deaf350ccd5ad8045552c5a0f8a5a2e81c1c23e280db2d80
Status: Downloaded newer image for mongo:latest
123a5f85412b00a7639c6cdba6b12efbc756196495c96751fc8304c8700c8b78
```

`--name` : container에 이름을 지정

`-p` : localhost의 27017번 port를 container의 27017번 port에 연결해서 container의 27017번 port를 사용할 수 있게 해줌.

실행한 mongodb container를 삭제하면 데이터들이 당연히 사라지겠죠.

mongo db는 file system의 disk에 데이터를 저장함. 이 저장되는 부분을 named volume으로 설정해보자.

<br/><br/>

### Named volume

**named volume** :  docker에서 유지되는 데이터를 관리해야 할 때, 편리하게 이름으로 사용할 수 있게 만들어둔 volume

```
$ docker volume create {named_volume}
```

<br/><br/>

```
$ docker volume inspect {named_volume}

/* Example
$ docker volume inspect mongo
[
    {
        "CreatedAt": "2021-11-13T11:06:57Z",
        "Driver": "local",
        "Labels": {},
        "Mountpoint": "/var/lib/docker/volumes/mongo/_data",
        "Name": "mongo",
        "Options": {},
        "Scope": "local"
    }
]
*/
```

커맨드를 통해서 named volume에 대한 정보를 더 알 수 있습니다. 예를들면 어디에 저장되어 있는지요!

<br/>

유지시켜야 할 데이터를 named volume에 저장하는데, named volume를 container와 연결하여 사용. 유지되어야 하는 데이터의 폴더와 named volume를 연결하고 폴더 내의 데이터가 저장될 때마다 named volume도 같이 변경. 새로운 container를 띄울 때 동일한 위치에 named volume과 연결하면 기존의 데이터를 유지할 수 있게 됨.

<br/>

**mongodb에 named volume 연결**

*`-v` : volume으로 연결*

<br/>

```bash
$ docker run -p 27017:27017 -v memo:/data/db mongo
```

`memo`라는 named volume을 container의 `/data/db` 폴더와 연결하겠다.

데이터를 생성해보고, 삭제한 다음 다시 유지되는지 확인해보자.

<br/><br/>

### Bind mount

코드가 수정될 때마다 image를 생성하고, container를 새로 띄우는 건 너무 cost가 큼

👉🏻 내가 원하는 volume을 docker container 내의 volume에 연결해보자.

<br/>

**언제 사용?**

자주 바뀌는 코드가 바로 변경되면 좋겠다. 데이터가 생성되고 유지시키고자 할 때. 이미지를 새로 빌드하는 일 없이 업데이트 하고 싶을 때 사용.

<br/>

bind mount의 문법은 named volume과 거의 비슷

```bash
$ docker run -v {host_path}:{container_path} <docker_image>
```

<br/>

```bash
$ docker run -v "$(pwd)/templates:/templates" -p 80:5000 {docker_image}:{version}
```

<small>혹시 access denied가 뜬다면, 커맨드를 실행하고 있는 위치가 프로젝트의 root폴더인지 확인</small>

<br/><br/>

### Network

docker application 사이에 **서로 통신**할 수 있는 방법

<br/>

**Docker network란?**

Docker container는 분리된 filesystem과 네트워크를 가진다. <small>따로 설정을 하지 않으면 filesystem과 network 통신이 불가능</small>

👉🏻 network를 통해서 docker container끼리 통신을 하게 만들 수 있어요

<br/>

#### docker network 생성

`docker network create` 명령어를 통해 사용자 정의 network bridge를 생성할 수 있음

```bash
// EXAMPLE
$ docker network create my-net
```

<br/>

#### docker network 삭제

`docker network rm` 명령어를 통해 사용자 정의 network bridge를 삭제할 수 있음

만약, container와 연결 중이라면 `docker network disconnect` 명령어를 통해 연결을 먼저 끊어주어야 함

```bash
// EXAMPLE
// disconnects the my-nginx container from the my-net network first.
$ docker network disconnect my-net my-nginx
// remove a user-defined bridge network
$ docker network rm my-net
```

<br/>

#### docker network 연결

1. **container 실행과 동시에 연결**

```bash
$ docker run -d --name my-nginx \
  --network my-net \
  --network-alias {network_alias} \
  -p 8080:80 \
  nginx:latest
```

`run`이 아닌 `create` (container create, but not running) 를 사용할 수도 있음 ~ 

<br/>

2. **실행 중인 container에 연결**

```bash
$ docker network connect my-net my-nginx
```

<br/>

**container에서 다른 container 호출**

network alias를 통해 확인하기

```bash
docker run -it --network test nicolaka/netshoot
> dig mongo
```

Docker container의 리소스 사용량 제한

docker가 host의 리소스를 많이 쓰면 문제가 생기는데요! 적당히 제한을 해두면 마음 놓고 docker로 application을 쓸 수 있습니다!

<br/><br/>

### CPU, Memory 리소스 제한

docker에서는 host의 disk와 memory를 같이 사용해서 cpu와 memory, disk 다른 프로세스에도 영향을 줄 수 있음.

<br/>

✔️ **Memory**

memory가 부족할 때 최악의 경우 host의 kernel에서 **OOME**<small>(Out Of Memory Exception)</small>을 일으켜서 **여유 memory를 생성하기 위해서 process를 삭제** 할 수도 있음 🤭

✔️  **CPUs**

cpu를 한 container가 다 사용하게 되는 경우 다른 container에 cpu가 할당되지 않아 동작하지 않을 수 있음

✔️  **Disk**

disk가 부족할 경우에는 데이터를 disk에 저장하지 못하거나, 새로운 프로세스를 사용하지 못할 수 있음

<br/>

**memory 제한하기**

```bash
docker run -i -m 10m python:3.8
```

 the minimum allowed value is `6m` (6 megabyte).

<br/>

**cpu 제한하기**

```bash
docker run -i --cpus=0.5 python:3.8
```

<br/>

사용하고 있는 리소스 확인하기

```jsx
docker stats
```









