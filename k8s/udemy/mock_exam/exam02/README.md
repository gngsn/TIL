### Q1. Take a backup of the etcd cluster and save it to `/opt/etcd-backup.db`.


<br>

#### Answer

Kubernetes 공식 문서에서 `etcd backup` 검색 후 가장 상단의 `Operating etcd clusters for Kubernetes | Kubernetes` 참고

https://kubernetes.io/docs/tasks/administer-cluster/configure-upgrade-etcd/#snapshot-using-etcdctl-options

```Bash
controlplane ~ ✖ cat /etc/kubernetes/manifests/etcd.yaml | grep '\-\-'
    - --advertise-client-urls=https://192.23.197.8:2379
    - --cert-file=/etc/kubernetes/pki/etcd/server.crt
    - --client-cert-auth=true
    - --data-dir=/var/lib/etcd
    - --experimental-initial-corrupt-check=true
    - --experimental-watch-progress-notify-interval=5s
    - --initial-advertise-peer-urls=https://192.23.197.8:2380
    - --initial-cluster=controlplane=https://192.23.197.8:2380
    - --key-file=/etc/kubernetes/pki/etcd/server.key
    - --listen-client-urls=https://127.0.0.1:2379,https://192.23.197.8:2379
    - --listen-metrics-urls=http://127.0.0.1:2381
    - --listen-peer-urls=https://192.23.197.8:2380
    - --name=controlplane
    - --peer-cert-file=/etc/kubernetes/pki/etcd/peer.crt
    - --peer-client-cert-auth=true
    - --peer-key-file=/etc/kubernetes/pki/etcd/peer.key
    - --peer-trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt
    - --snapshot-count=10000
    - --trusted-ca-file=/etc/kubernetes/pki/etcd/ca.crt

controlplane ~ ✖ ETCDCTL_API=3 etcdctl --endpoints=https://127.0.0.1:2379 \
 --cacert=/etc/kubernetes/pki/etcd/ca.crt \
 --cert=/etc/kubernetes/pki/etcd/server.crt \
 --key=/etc/kubernetes/pki/etcd/server.key \
 snapshot save /opt/etcd-backup.db
Snapshot saved at /opt/etcd-backup.db
```

<br><br>

### Create a Pod called `redis-storage` with image: `redis:alpine` with a Volume of type `emptyDir` that lasts for the life of the Pod.

Specs on the below.

- Pod named 'redis-storage' created
- Pod 'redis-storage' uses Volume type of emptyDir
- Pod 'redis-storage' uses volumeMount with mountPath = /data/redis

<br>

#### Answer


```Bash
apiVersion: v1
kind: Pod
metadata:
name: redis-storage
spec:
containers:
- image: redis:alpine
  name: redis-storage-container
  volumeMounts:
    - mountPath: /data/redis
      name: redis-volume
      volumes:
- name: redis-volume
  emptyDir:
    sizeLimit: 500Mi
```


<br><br>
