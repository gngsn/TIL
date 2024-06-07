# Pod Networking

Kubernetes 내에 있는 Master 노드와 Worker 노드들은 서로 네트워킹할 수 있게 설정되어 있음

그치만, 노드 사이의 네트워크 통신을 원활하게 한다고 해서 모든 준비가 되는 건 아님 → Pod 사이의 네트워킹 설정이 필요

쿠버네티스 내에는 수 많은 Pod 를 가질 수 있고, 이 Po 들이 통신할 수 있도록 해야함 

쿠버네티스는 이를 위한 해결을 Built-in 서비스로 해결하지 않고, 사용자가 직접 구현해서 해결하도록 함

하지만, Kubernetes는 Pod 네트워킹에 대한 요구 사항을 명확하게 제시함

<br><img src="./img/pod_networking_img1.png" width="80%" ><br>

쿠버네티스는 모든 Pod가 유니크 한 IP 주소를 갖고 동일 노드 내에 다른 Pod 와 통신할 때 해당 IP를 사용하도록 하며,
모든 Pod는 해당 IP를 사용해서 다른 노드에 있는 모든 Pod에 접근할 수 있어야 함

> - Every POD should have an IP Address.
> - Every POD should be able to communicate with every other POD in the same node.
> - Every POD should be able to communicate with every other POD on other nodes without NAT.


어떤 IP 대역을 갖고 있는지, 어떤 Subnet을 속하는지 중요하지 않음

IP 주소를 자동으로 할당하고 노드의 포드와 다른 노드의 포드 간에 연결을 설정하는 솔루션을 구현할 수 있다면, 기본 규칙을 따로 구성할 필요 없음

- [Flannel](https://github.com/flannel-io/flannel)
- [Cilium](https://cilium.io/)
- [vmware NSX](https://www.vmware.com/products/nsx.html)
- [Calico](https://docs.tigera.io/calico/latest/about/)


트를 실행하여 네트워크에 연결 및 설정.