# Introduction to Deployment with Kubeadm

## on Apple Silicon

VirtualBox가 Mac Apple Silicon 지원을 활발하게 하지 않기 때문에, Multipass 를 이용해서 설치


**Multipass**는 단 하나의 명령어로 Cloud-like Ubuntu VM 을 생성할 수 있음. Multipass는 가상 머신을 실행하고 public cloud 처럼 설정해줌 


```bash
❯ brew install multipass
```


<details>
<summary>Installation <code>multipass</code></summary>

```
❯ brew install multipass
controlplane
Deleting controlplane
Launching controlplane
Launched: controlplane
controlplane booted!
Launching node01
Launched: node01
node01 booted!
Launching node02
Launched: node02
node02 booted!
Setting hostnames
++ netstat -rn -f inet
++ grep '^default.*en0'
++ awk '{print $2}'
++ awk 'BEGIN { FS="." } { printf "%s.%s.%s", $1, $2, $3 }'
+ network=192.168.0
+ '[' -f /tmp/hostentries ']'
+ for node in controlplane '$workers'
+ '[' BRIDGE = BRIDGE ']'
++ multipass info controlplane --format json
++ jq -r --arg nw 192.168.0 'first( .info[] )| .ipv4  | .[] | select(startswith($nw))'
+ ip=192.168.0.250
+ echo '192.168.0.250 controlplane'
+ for node in controlplane '$workers'
+ '[' BRIDGE = BRIDGE ']'
++ multipass info node01 --format json
++ jq -r --arg nw 192.168.0 'first( .info[] )| .ipv4  | .[] | select(startswith($nw))'
+ ip=192.168.0.251
+ echo '192.168.0.251 node01'
+ for node in controlplane '$workers'
+ '[' BRIDGE = BRIDGE ']'
++ multipass info node02 --format json
++ jq -r --arg nw 192.168.0 'first( .info[] )| .ipv4  | .[] | select(startswith($nw))'
+ ip=192.168.0.252
+ echo '192.168.0.252 node02'
+ for node in controlplane '$workers'
+ multipass transfer /tmp/hostentries controlplane:/tmp/
+ multipass transfer /Users/gyeongsun/git/kodekloud/certified-kubernetes-administrator-course/kubeadm-clusters/apple-silicon/scripts/01-setup-hosts.sh controlplane:/tmp/
+ multipass exec controlplane -- /tmp/01-setup-hosts.sh BRIDGE 192.168.0
192.168.0.250 controlplane
192.168.0.251 node01
192.168.0.252 node02
^@+ for node in controlplane '$workers'
+ multipass transfer /tmp/hostentries node01:/tmp/
+ multipass transfer /Users/gyeongsun/git/kodekloud/certified-kubernetes-administrator-course/kubeadm-clusters/apple-silicon/scripts/01-setup-hosts.sh node01:/tmp/
+ multipass exec node01 -- /tmp/01-setup-hosts.sh BRIDGE 192.168.0
192.168.0.250 controlplane
192.168.0.251 node01
192.168.0.252 node02
+ for node in controlplane '$workers'
+ multipass transfer /tmp/hostentries node02:/tmp/
+ multipass transfer /Users/gyeongsun/git/kodekloud/certified-kubernetes-administrator-course/kubeadm-clusters/apple-silicon/scripts/01-setup-hosts.sh node02:/tmp/
+ multipass exec node02 -- /tmp/01-setup-hosts.sh BRIDGE 192.168.0
192.168.0.250 controlplane
192.168.0.251 node01
192.168.0.252 node02
+ echo -e '\033[1;32mDone!\033[0m'
Done!
+ '[' '' = -auto ']'
```

</details>

Get an instant Ubuntu VM with a single command. Multipass can launch and run virtual machines and configure them with cloud-init like a public cloud.


```Bash
❯ multipass shell controlplane
Welcome to Ubuntu 22.04.4 LTS (GNU/Linux 5.15.0-113-generic aarch64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/pro

 System information as of Sun Jun 30 16:21:44 KST 2024

  System load:             0.15
  Usage of /:              33.8% of 4.68GB
  Memory usage:            6%
  Swap usage:              0%
  Processes:               103
  Users logged in:         0
  IPv4 address for enp0s1: 192.168.65.2
  IPv6 address for enp0s1: fdb4:8252:e9ce:b35f:5054:ff:fe72:c42b


Expanded Security Maintenance for Applications is not enabled.

2 updates can be applied immediately.
2 of these updates are standard security updates.
To see these additional updates run: apt list --upgradable

Enable ESM Apps to receive additional future security updates.
See https://ubuntu.com/esm or run: sudo pro status


Last login: Sun Jun 30 16:21:10 2024 from 192.168.65.1
ubuntu@controlplane:~$
```

---

### Verify the MAC address and product_uuid are unique for every node

It is very likely that hardware devices will have unique addresses, although some virtual machines may have identical values. Kubernetes uses these values to uniquely identify the nodes in the cluster. If these values are not unique to each node, the installation process may fail.

```Bash
ubuntu@controlplane:~$ ip link
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN mode DEFAULT group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
2: enp0s1: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:72:c4:2b brd ff:ff:ff:ff:ff:ff
3: enp0s2: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc fq_codel state UP mode DEFAULT group default qlen 1000
    link/ether 52:54:00:3f:28:2c brd ff:ff:ff:ff:ff:ff
```

<br>

### Check network adapters
If you have more than one network adapter, and your Kubernetes components are not reachable on the default route, we recommend you add IP route(s) so Kubernetes cluster addresses go via the appropriate adapter.

<br>

### Check required ports

```Bash
ubuntu@controlplane:~$ for ports in 6443 2379 2380 10250 10259 10257; do nc 127.0.0.1 $ports -v; done;
nc: connect to 127.0.0.1 port 6443 (tcp) failed: Connection refused
...
nc: connect to 127.0.0.1 port 10257 (tcp) failed: Connection refuse
```

