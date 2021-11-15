### Docker-compose

<aside> 🔥 매번 docker run... 은 그만! 편하게 docker로 application을 띄우게 해주는 도구인 docker-compose를 살펴볼게요.

</aside>

- 1. Docker-compose란?

  - 여러 컨테이너를 편리하게 실행하기 위해서 만들어둔 도구입니다
  - YAML 파일을 통해 container를 실행하는데 필요한 옵션을 정의할 수 있어요
    - 더 편리하게 실행하는 방법 관리, 버전 관리 등등

  <aside> 💡 YAML이란  YAML Ain't Markup Language 으로, 데이터를 저장하는 파일 포맷입니다. 비슷한 형식으로 XML과 JSON이 존재하지만, 사람이 읽기가 더 편하다는 장점이 있습니다.

  </aside>

- 1. docker-compose로 memo application 실행하기

  - 기존 커맨드 생각해보기

    ```jsx
    docker build . -t wellshs/docker-memo:latest
    docker run -d -p 5000:5000 wellshs/docker-memo:latest
    ```

  - 도커 컨테이너 정의를 적어둔 yaml 생성하기

    ```docker
    version: "3.9"
    
    services:
      flask:
        build:
          context: .
        ports:
          - "5000:5000"
    ```

    <aside> 🙋‍♂️ docker-compose.yaml이라는 이름으로 만들면 docker-compose command를 사용할 때 기본으로 사용됩니다.

    </aside>

    <aside> 🙋‍♂️ 맨 위에 version은 docker-compose의 스키마의 버젼을 뜻합니다.

    </aside>

    <aside> 🙋‍♂️ services 밑에 container의 정의를 적어서 사용합니다.

    </aside>

    <aside> 🙋‍♂️ build를 적어두면 이미지를 빌드하여 사용합니다.

    </aside>

  - docker-compose로 container 띄우기

    ```docker
    docker-compose up -d
    ```

  - docker-compose로 container 삭제하기

    ```docker
    docker-compose down
    ```

- 1. docker-compose로 DB가 있는 application 연결하기

  - docker-compose는 여러개의 application을 지원하기 위해서 만들어 둔 것이기 때문에 따로 한 파일에 정의를 한다면 기본적으로 같은 network를 사용하게 됩니다.

  - docker-compose.yaml

    ```docker
    version: "3.9"
    
    services:
      flask:
        build:
          context: .
        ports:
          - "5000:5000"
      mongo:
        image: mongo:latest
        ports:
          - "27017:27017"
    ```

- 1. docker-compose를 이용하여 volume으로 유지되는 appilcation 실행하기

  - docker-compose에서는 bind mount, named volume을 모두 지원합니다.

  - docker-compose.yaml

    ```docker
    version: "3.9"
    services:
      flask:
        build:
          context: .
        volumes:
          - ./templates:/templates
        ports:
          - "5000:5000"
      mongo:
        image: mongo:latest
        volumes:
          - mongo:/data/db/
        ports:
          - "27017:27017"
    
    volumes:
      mongo:
    ```

