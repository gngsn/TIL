## Three Big Concepts

### ✔️ Chart 
*a Helm package*
Kubernetes cluster 내의 Application, Tool, Service 실행의 필수적인 리소스 정의를 모두 포함합니다. 
Homebrew formula, Apt dpkg, Yum RPM 파일과 동일한 방식이되, Kubernetes 을 위한 방식으로 볼 수 있습니다.

```
It contains all of the resource definitions necessary to run an application, tool, or service inside of a Kubernetes cluster. Think of it like the Kubernetes equivalent of a Homebrew formula, an Apt dpkg, or a Yum RPM file.
```

### ✔️ Repository
*The place where charts can be collected and shared*
마치 Perl 의 CPAN archive 혹은 Fedora Package Database 와 같은 역할을 하되, Kubernetes packages 를 기능한다고 볼 수 있습니다. 

```
It's like Perl's CPAN archive or the Fedora Package Database, but for Kubernetes packages.
```

### ✔️ Release
*An instance of a chart running in a Kubernetes cluster*
하나의 Chart는 동일한 클러스터에 여러 번 설치될 수 있습니다. 설치가 될 때마다, 새로운 Release 가 생성됩니다. 가령, MySQL Chart를 생각해보면, 클러스터에 두 개의 데이터베이스를 Running 시키고 싶다면 Chart를 두 번 다운 받아올 수 있습니다. 각각 고유의 Release를 갖게 되며, 각각 고유의 Release 이름을 차례로 갖게 됩니다.

```
One chart can often be installed many times into the same cluster. And each time it is installed, a new release is created. Consider a MySQL chart. If you want two databases running in your cluster, you can install that chart twice. Each one will have its own release, which will in turn have its own release name.
```

With these concepts in mind, we can now explain Helm like this:
Helm은 Kubernetes에서 charts를 내려 받는데, 이 때 설치를 할 때마다 해당 설치에 대한 새로운 release를 생성합니다. 또, repositories에서 새로운 Chart를 검색해 찾을 수 있습니다.

```
Helm installs charts into Kubernetes, creating a new release for each installation. And to find new charts, you can search Helm chart repositories.
```

