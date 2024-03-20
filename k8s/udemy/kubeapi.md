# kubeapi


When you run a kubectl command, the kubectl utility is in fact reaching to the kube-apiserver.
The kube-apiserver first authenticates the request and validates it.
It then retrieves the data from the etcd cluster and responds back with the requested information.
You don't really need to use the kubectl command line.
Instead, you could also invoke the APIs directly by sending a POST request like this.
Let's look at an example of creating a pod.
When you do that, as before, the request is authenticated first and then validated.
In this case, the API server creates a pod object without assigning it to a node.

`kubectl` 명령어를 입력하면, 내부적으로는 `kube-apiserver`와 통신합니다.
`kube-apiserver`는 가장 먼저 요청을 인증하고 검증합니다.

