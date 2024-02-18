## Charts

Helm uses a packaging format called charts. A chart is a collection of files that describe a related set of Kubernetes resources. A single chart might be used to deploy something simple, like a memcached pod, or something complex, like a full web app stack with HTTP servers, databases, caches, and so on.
Charts are created as files laid out in a particular directory tree. They can be packaged into versioned archives to be deployed.
If you want to download and look at the files for a published chart, without installing it, you can do so with helm pull chartrepo/chartname.
This document explains the chart format, and provides basic guidance for building charts with Helm.

<br/>

### The Chart File Structure
하나의 Chart는 하나의 디렉터리에 일종의 파일 묶음 (Collection of files) 입니다. 해당 디렉터리 이름이 바로 Chart의 이름이 됩니다 (버전 정보 제외). 가령, wordpress 디렉터리를 살펴보면 아래와 같이 wordpress/ 디렉터리 하위에 Chart들이 구성되어 있습니다.

<br/>

Helm은 다음과 같은 구조의 디렉터리 구성을 가집니다.

```
wordpress/
  Chart.yaml          # chart 정보를 갖는 하나의 YAML file
  LICENSE             # OPTIONAL: chart의 license 정보를 갖는 텍스트 파일
  README.md           # OPTIONAL: README 파일
  values.yaml         # 해당 chart에서 사용할 기본 환경설정 값들 (default configuration values)
  values.schema.json  # OPTIONAL: values.yaml 파일 구조를 제약하기 위한 A JSON Schema
  charts/             # 해당 차트에 의존하는 차트들을 보관하는 directory
  crds/               # Custom 자원 정의 모음
  templates/          # 템플릿이 values와 결합될 때,
                      # 유효한 쿠버네티스 manifest 파일들을 생성할 템플릿들의 디렉터리
  templates/NOTES.txt # OPTIONAL: 간단한 사용법을 다룬 텍스트 파일
```

Helm은 `charts/`, `crds/`, `templates/` 디렉터리 명과 위 목록의 파일 명을 미리 예약하여 사용하고 있습니다. 다른 이름들은 그 자체로 남습니다.





