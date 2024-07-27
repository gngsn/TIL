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

---

#### Q3. Create a new pod called `super-user-pod` with image `busybox:1.28`. Allow the pod to be able to set `system_time`.

The container should sleep for 4800 seconds.

- **Pod**: `super-user-pod`
- **Container Image**: `busybox:1.28`
- Is `SYS_TIME` capability set for the container?

<br>

### Answer

```Bash
controlplane ~ ➜  vi super-user-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  name: super-user-pod
spec:
  containers:
  - name: busybox
    image: busybox:1.28
    securityContext:
      capabilities:
        add: ["SYS_TIME"]
    command:
    - sleep
    - "4800"
```

---

#### Q4. A pod definition file is created at `/root/CKA/use-pv.yaml`. Make use of this manifest file and mount the persistent volume called `pv-1`. Ensure the pod is running and the PV is bound.

- mountPath: `/data`
- persistentVolumeClaim Name: `my-pvc`
- persistentVolume Claim configured correctly
- pod using the correct mountPath
- pod using the persistent volume claim?

<br>

### Answer

**1. PV 스펙 확인**

```Bash
controlplane ~ ➜  k get pv -o yaml
apiVersion: v1
items:
- apiVersion: v1
  kind: PersistentVolume
  metadata:
    creationTimestamp: "2024-07-27T04:50:58Z"
    finalizers:
    - kubernetes.io/pv-protection
    name: pv-1
    resourceVersion: "4521"
    uid: 879b96c9-470e-4d60-89ff-211e8a9c485f
  spec:
    accessModes:
    - ReadWriteOnce
    capacity:
      storage: 10Mi
    hostPath:
      path: /opt/data
      type: ""
    persistentVolumeReclaimPolicy: Retain
    volumeMode: Filesystem
  status:
    lastPhaseTransitionTime: "2024-07-27T04:50:58Z"
    phase: Available
kind: List
metadata:
  resourceVersion: ""
```

<br>

**2. PVC 작성 후 Pod 정의 스펙에 명시**

```Bash
controlplane ~ ➜  cat my-pvc.yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: my-pvc 
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 10Mi
      
controlplane ~ ➜  cat /root/CKA/use-pv.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: use-pv
  name: use-pv
spec:
  containers:
  - image: nginx
    name: use-pv
    resources: {}
    volumeMounts:
      - mountPath: "/data"
        name: my-pvc-volume
  dnsPolicy: ClusterFirst
  restartPolicy: Always
  volumes:
  - name: my-pvc-volume
    persistentVolumeClaim:
      claimName: my-pvc
status: {}
```


---

#### Q5. Create a new deployment called `nginx-deploy`, with image `nginx:1.16` and `1` replica. Next upgrade the deployment to version `1.17` using rolling update.


- **Deployment** : `nginx-deploy`. Image: `nginx:1.16`
- **Image**: `nginx:1.16`
- **Task**: Upgrade the version of the deployment to `1:17`
- **Task**: Record the changes for the image upgrade

<br>

#### Answer


