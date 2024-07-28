#### Q1. Create a new service account with the name `pvviewer`. Grant this Service account access to `list` all PersistentVolumes in the cluster by creating an appropriate cluster role called `pvviewer-role` and `ClusterRoleBinding` called `pvviewer-role-binding`. Next, create a pod called `pvviewer` with the `image: redis` and `serviceAccount: pvviewer` in the `default` namespace.

<br>

#### Answer

1. create serviceaccount

```Bash
controlplane ~ ➜  kubectl create serviceaccount pvviewer
serviceaccount/pvviewer created
```

2. create role

```Bash
controlplane ~ ➜ kubectl create role pvviewer-role --verb=list --resource=persistentvolumes
role.rbac.authorization.k8s.io/pvviewer-role created
```

kubectl create clusterrole pvviewer-role --resource=persistentvolumes --verb=list



3. create role-binding

```Bash
controlplane ~ ➜  kubectl create clusterrolebinding pvviewer-role-binding --clusterrole=pvviewer-role --user=pvviewer
clusterrolebinding.rbac.authorization.k8s.io/pvviewer-role-binding created
```

kubectl create clusterrolebinding pvviewer-role-binding --clusterrole=pvviewer-role --serviceaccount=default:pvviewer



4. create pod & configure serviceAccount by specifying `serviceAccountName`

```Bash
controlplane ~ ➜  k run pvviewer --image=redis   -o yaml --dry-run=client > pvviewer.yaml

controlplane ~ ➜  vi pvviewer.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: pvviewer
  name: pvviewer
spec:
  containers:
  - image: redis
    name: pvviewer
    resources: {}
  serviceAccountName: pvviewer 
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  k apply -f pvviewer.yaml 
pod/pvviewer created
```

apiVersion: v1
kind: Pod
metadata:
labels:
run: pvviewer
name: pvviewer
spec:
containers:
- image: redis
  name: pvviewer
# Add service account name
serviceAccountName: pvviewer

check permission

```
controlplane ~ ➜  kubectl auth can-i list pv
Warning: resource 'persistentvolumes' is not namespace scoped

yes
```

<br><br>

---

#### Q2. List the `InternalIP` of all nodes of the cluster. Save the result to a file `/root/CKA/node_ips`.

Answer should be in the format: `InternalIP of controlplane<space>InternalIP of node01` (in a single line)

<br>

#### Answer

[🔗 JSONPath Support](https://kubernetes.io/docs/reference/kubectl/jsonpath/) 참고

```Bash
controlplane ~ ➜  k get nodes -o=jsonpath='{range .items[*]}{ .status.addresses[?(@.type=="InternalIP")].address } {"of"} { .metadata.name } { end }'
192.12.122.12 of controlplane 192.12.122.3 of node01 
controlplane ~ ➜  k get nodes -o=jsonpath='{range .items[*]}{ .status.addresses[?(@.type=="InternalIP")].address } {"of"} { .metadata.name } { end }' > /root/CKA/node_ips
```
kubectl get nodes -o jsonpath='{.items[*].status.addresses[?(@.type=="InternalIP")].address}'

<br><br>

---

#### Q3. Create a pod called `multi-pod` with two containers.
Container 1: name: `alpha`, image: `nginx`
Container 2: name: `beta`, image: `busybox`, command: `sleep 4800`

Environment Variables:
container 1:
`name: alpha`

Container 2:
`name: beta`

<br>

#### Answer

```Bash
controlplane ~ ➜  k run alpha --image=nginx --env="name=alpha"
pod/alpha created

controlplane ~ ➜  k run beta --image=busybox --env="name=beta" --command -- sleep 4800
pod/beta created
```

```Bash
controlplane ~ ➜  k get pods alpha -o yaml
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: alpha
  name: alpha
  namespace: default
  ...
spec:
  containers:
  - env:
    - name: name
      value: alpha
    image: nginx
    imagePullPolicy: Always
    name: alpha
  ...

controlplane ~ ➜  k get pods beta -o yaml
apiVersion: v1
kind: Pod
metadata:
  labels:
    run: beta
  name: beta
  namespace: default
  ...
spec:
  containers:
  - command:
    - sleep
    - "4800"
    env:
    - name: name
      value: beta
    image: busybox
    name: beta
  ...
```

apiVersion: v1
kind: Pod
metadata:
name: multi-pod
spec:
containers:
- image: nginx
  name: alpha
  env:
  - name: name
    value: alpha
- image: busybox
  name: beta
  command: ["sleep", "4800"]
  env:
  - name: name
    value: beta
  - 

<br><br>

---

#### Q4. Create a Pod called `non-root-pod`, image: `redis:alpine`

- `runAsUser: 1000`
- `fsGroup: 2000`

<br>

#### Answer

[🔗Configure a Security Context for a Pod or Container](https://kubernetes.io/docs/tasks/configure-pod-container/security-context/#set-the-security-context-for-a-pod) 참고

```Bash
controlplane ~ ➜  k run non-root-pod --image=redis:alpine --dry-run=client -o yaml > non-root-pod.yaml

controlplane ~ ➜  vi non-root-pod.yaml
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: non-root-pod
  name: non-root-pod
spec:
  securityContext:
    runAsUser: 1000
    fsGroup: 2000
  containers:
  - image: redis:alpine
    name: non-root-pod
    resources: {}
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  k apply -f non-root-pod.yaml 
pod/non-root-pod created
```

<br><br>

---

#### Q5. We have deployed a new pod called `np-test-1` and a service called `np-test-service`. 
Incoming connections to this service are not working. Troubleshoot and fix it.
Create NetworkPolicy, by the name `ingress-to-nptest` that allows incoming connections to the service over port `80`.

Important: Don't delete any current objects deployed.

<br>

#### Answer


[Network Policies](https://kubernetes.io/docs/concepts/services-networking/network-policies/)

```Bash
controlplane ~ ➜  vi ingress-to-nptest.yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: ingress-to-nptest
spec:
  podSelector:
    matchLabels:
      run: np-test-1
  ingress:
  - from:
    ports:
    - protocol: TCP
      port: 80
  policyTypes:
  - Ingress

controlplane ~ ➜  k apply -f ingress-to-nptest.yaml 
networkpolicy.networking.k8s.io/allow-all-ingress created
```

?? How to check ?

<br><br>

---

#### Q6. Taint the worker node `node01` to be Unschedulable. 
Once done, create a pod called `dev-redis`, image `redis:alpine`, to ensure workloads are not scheduled to this worker node.
Finally, create a new pod called `prod-redis` and image: `redis:alpine` with toleration to be scheduled on `node01`.

key: `env_type`, value: `production`, operator: `Equal` and effect: `NoSchedule`

<br>

#### Answer

```Bash
controlplane ~ ➜  kubectl taint nodes node01 env_type=production:NoSchedule
node/node01 tainted

controlplane ~ ➜  k run dev-redis --image=redis:alpine
pod/dev-redis created

controlplane ~ ➜  k get pods -o wide
NAME           READY   STATUS    RESTARTS   AGE   IP             NODE           NOMINATED NODE   READINESS GATES
dev-redis      1/1     Running   0          6s    10.244.0.4     controlplane   <none>           <none>
...

controlplane ~ ➜  k run prod-redis --image=redis:alpine --dry-run=client -o yaml > prod-redis.yaml

controlplane ~ ➜  vi prod-redis.yaml 
apiVersion: v1
kind: Pod
metadata:
  creationTimestamp: null
  labels:
    run: prod-redis
  name: prod-redis
spec:
  containers:
  - image: redis:alpine
    name: prod-redis
    resources: {}
  tolerations:
  - key: "env_type"
    operator: "Equal"
    value: "production"
    effect: "NoSchedule"
  dnsPolicy: ClusterFirst
  restartPolicy: Always
status: {}

controlplane ~ ➜  k apply -f prod-redis.yaml 
pod/prod-redis created

controlplane ~ ➜  k get pod -o wide -w
NAME           READY   STATUS    RESTARTS   AGE    IP             NODE           NOMINATED NODE   READINESS GATES
dev-redis      1/1     Running   0          2m7s   10.244.0.4     controlplane   <none>           <none>
prod-redis     1/1     Running   0          6s     10.244.192.6   node01         <none>           <none>
...
```
