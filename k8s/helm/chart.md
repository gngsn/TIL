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

<br/>

### The Chart.yaml File

`Chart.yaml` 파일은 Chart를 위해 필수로 정의되어야 합니다.
해당 파일은 아래와 같은 필드들이 포함됩니다.

<pre>
<b>apiVersion</b>: The chart API version (required)
<b>name</b>: The name of the chart (required)
<b>version</b>: A SemVer 2 version (required)
<b>kubeVersion</b>: A SemVer range of compatible Kubernetes versions (optional)
<b>description</b>: A single-sentence description of this project (optional)
<b>type</b>: The type of the chart (optional)
<b>keywords</b>:
  - A list of keywords about this project (optional)
<b>home</b>: The URL of this projects home page (optional)
<b>sources</b>:
  - A list of URLs to source code for this project (optional)
<b>dependencies</b>: # A list of the chart requirements (optional)
  - <b>name</b>: The name of the chart (nginx)
    <b>version</b>: The version of the chart ("1.2.3")
    <b>repository</b>: (optional) The repository URL ("https://example.com/charts") or alias ("@repo-name")
    <b>condition</b>: (optional) A yaml path that resolves to a boolean, used for enabling/disabling charts (e.g. subchart1.enabled )
    <b>tags</b>: # (optional)
      - Tags can be used to group charts for enabling/disabling together
    <b>import-values</b>: # (optional)
      - ImportValues holds the mapping of source values to parent key to be imported. Each item can be a string or pair of child/parent sublist items.
    <b>alias</b>: (optional) Alias to be used for the chart. Useful when you have to add the same chart multiple times
<b>maintainers</b>: # (optional)
  - <b>name</b>: The maintainers name (required for each maintainer)
    <b>email</b>: The maintainers email (optional for each maintainer)
    <b>url</b>: A URL for the maintainer (optional for each maintainer)
<b>icon</b>: A URL to an SVG or PNG image to be used as an icon (optional).
<b>appVersion</b>: The version of the app that this contains (optional). Needn't be SemVer. Quotes recommended.
<b>deprecated</b>: Whether this chart is deprecated (optional, boolean)
<b>annotations</b>:
  <b>example</b>: A list of annotations keyed by name (optional).
</pre>

v3.3.2 부터, 추가적인 필드들을 기입할 수 없게 됩니다. 추천하는 방식은 custom metadata를 annotations에 작성하는 것입니다.


