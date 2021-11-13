Docker Container 데이터를 유지



application에서 생성한 데이터가 실행될 때마다 삭제된다면 너무 아쉽잖아요! 이런 경우에 쓸 수 있는 named volume에 대해서 알아봅시다.



container를 다시 실행하면 기존에 기록했던 데이터들이 삭제

데이터를 메모리에 올려두는데 새로운 container가 실행될 때마다 메모리를 삭제가 되게 됩니다.



Container 데이터를 유지시켜야 하는 상황들

- 시간이 지나면 코드는 진화하기 때문에 새로운 Docker container를 생성해야하는 경우가 존재
  - 새로운 이미지 혹은 새로운 서버
- 하지만 컨테이너 안에는 많은 정보가 존재함. 그 중 유지를 시켜야 하는 것들이 있음
  - 가치가 있는 데이터
- docker를 사용하지 않는 경우에 disk에 데이터를 올려두고 참조하는 식으로 사용을 합니다.
- 그러나 그냥  컨테이너는 실행될 때마다 자신이 마음대로 조작(create, update, delete)할 수 있는 file system을 얻습니다. 동일 이미지를 사용하더라도 컨테이너끼리는 file system을 공유하지 않습니다.
- Docker에서도 그 방법을 쉽게 제공할 필요가 있었음.
- 사용방법을 거의 비슷하지만 두가지 방법이 있음 하나는 named volume, 하나는 bind mount







### named volume

named volume이란 docker에서 유지되는 데이터를 관리해야 할 때 편리하게 이름으로 사용할 수 있게 만들어둔 volume 입니다.

```
docker volume create memo
```





```
docker volume inspect memo
```

커맨드를 통해서 named volume에 대한 정보를 더 알 수 있습니다. 예를들면 어디에 저장되어 있는지요!





mongodb에 named volume 을 연결

```
docker run -p 27017:27017 -v memo:/data/db mongo
```

데이터를 생성해보고, 삭제한 다음 다시 유지되는지 확인





### Docker bind mount

- 내가 원하는 volume을 docker container 내의 volume에 연결하는 기능입니다.

  - 코드가 수정될 때마다 image를 생성하고, container를 새로 띄우는 건 너무 cost가 크겠죠!

- bind mount의 문법은 named volume과 거의 비슷합니다.

  ```bash
  docker run -v {host_path}:{container_path} <docker_image>
  ```

- bind mount를 사용하여 memo application을 실행해봅시다!

  ```bash
  docker run -v "$(pwd)/templates:/templates" -p 80:5000 wellshsdocker-memo:version2
  ```

  혹시 access denied가 뜬다면, 커맨드를 실행하고 있는 위치가 프로젝트의 root폴더인지 확인







### 여러 container 연결

docker로 편리하게 application을 띄웠는데 연결이 안되면 안되겠죠! 서로 통신할 수 있는 방법을 알아봅시다.











