## Question

Upgrade the current version of kubernetes from `1.29.0` to `1.30.0` exactly using the `kubeadm` utility. 
Make sure that the upgrade is carried out one node at a time starting with the `controlplane` node. 
To minimize downtime, the deployment `gold-nginx` should be rescheduled on an alternate node before upgrading each node.


<small>Upgrade `controlplane` node first and drain node `node01` before upgrading it. Pods for `gold-nginx` should run on the `controlplane` node subsequently.</small>

**#1. 버전 확인**

```Bash
controlplane ~ ➜  kubectl get node
NAME           STATUS   ROLES           AGE   VERSION
controlplane   Ready    control-plane   55m   v1.29.0
node01         Ready    <none>          54m   v1.29.0

controlplane ~ ➜  cat /etc/*release*
DISTRIB_ID=Ubuntu
DISTRIB_RELEASE=22.04
DISTRIB_CODENAME=jammy
DISTRIB_DESCRIPTION="Ubuntu 22.04.4 LTS"
PRETTY_NAME="Ubuntu 22.04.4 LTS"
NAME="Ubuntu"
VERSION_ID="22.04"
VERSION="22.04.4 LTS (Jammy Jellyfish)"
VERSION_CODENAME=jammy
ID=ubuntu
ID_LIKE=debian
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
UBUNTU_CODENAME=jammy

controlplane ~ ➜  kubeadm version
kubeadm version: &version.Info{Major:"1", Minor:"29", GitVersion:"v1.29.0", GitCommit:"3f7a50f38688eb332e2a1b013678c6435d539ae6", GitTreeState:"clean", BuildDate:"2023-12-13T08:50:10Z", GoVersion:"go1.21.5", Compiler:"gc", Platform:"linux/amd64"}
```


```Bash
# Find the latest 1.30 version in the list.
# It should look like 1.30.x-*, where x is the latest patch.
❯ sudo apt update
❯ sudo apt-cache madison kubeadm

## Upgrading control plane nodes
# replace x in 1.30.x-* with the latest patch version
❯ sudo apt-mark unhold kubeadm && \
    sudo apt-get update && sudo apt-get install -y kubeadm='1.30.x-*' && \
    sudo apt-mark hold kubeadm

❯ kubeadm version
❯ sudo kubeadm upgrade plan

# Same as the first control plane node but use:
# ❯ sudo kubeadm upgrade node
# instead of:
❯ sudo kubeadm upgrade apply 1.30.x

# version 확인
❯ kubectl get node
```

<details>
<summary>How it went</summary>

```
controlplane ~ ➜ sudo apt-cache madison kubeadm
   kubeadm | 1.30.3-1.1 | https://pkgs.k8s.io/core:/stable:/v1.30/deb  Packages
   kubeadm | 1.30.2-1.1 | https://pkgs.k8s.io/core:/stable:/v1.30/deb  Packages
   kubeadm | 1.30.1-1.1 | https://pkgs.k8s.io/core:/stable:/v1.30/deb  Packages
   kubeadm | 1.30.0-1.1 | https://pkgs.k8s.io/core:/stable:/v1.30/deb  Packages
   
controlplane ~ ➜ sudo apt-mark unhold kubeadm && \
    sudo apt-get update && sudo apt-get install -y kubeadm='1.30.0-1.1' && \
    sudo apt-mark hold kubeadm
...
kubeadm set on hold.
    
controlplane ~ ➜  kubeadm version
kubeadm version: &version.Info{Major:"1", Minor:"30", GitVersion:"v1.30.0", GitCommit:"7c48c2bd72b9bf5c44d21d7338cc7bea77d0ad2a", GitTreeState:"clean", BuildDate:"2024-04-17T17:34:08Z", GoVersion:"go1.22.2", Compiler:"gc", Platform:"linux/amd64"}

controlplane ~ ✖ sudo kubeadm upgrade plan
[upgrade/config] Making sure the configuration is correct:
[preflight] Running pre-flight checks.
[upgrade/config] Reading configuration from the cluster...
[upgrade/config] FYI: You can look at this config file with 'kubectl -n kube-system get cm kubeadm-config -o yaml'
[upgrade] Running cluster health checks
[upgrade] Fetching available versions to upgrade to
[upgrade/versions] Cluster version: 1.29.0
[upgrade/versions] kubeadm version: v1.30.0
[upgrade/versions] Target version: v1.30.3
[upgrade/versions] Latest version in the v1.29 series: v1.29.7

Components that must be upgraded manually after you have upgraded the control plane with 'kubeadm upgrade apply':
COMPONENT   NODE           CURRENT   TARGET
kubelet     controlplane   v1.29.0   v1.29.7
kubelet     node01         v1.29.0   v1.29.7

Upgrade to the latest version in the v1.29 series:

COMPONENT                 NODE           CURRENT    TARGET
kube-apiserver            controlplane   v1.29.0    v1.29.7
kube-controller-manager   controlplane   v1.29.0    v1.29.7
kube-scheduler            controlplane   v1.29.0    v1.29.7
kube-proxy                               1.29.0     v1.29.7
CoreDNS                                  v1.10.1    v1.11.1
etcd                      controlplane   3.5.10-0   3.5.12-0

You can now apply the upgrade by executing the following command:

        kubeadm upgrade apply v1.29.7

_____________________________________________________________________

Components that must be upgraded manually after you have upgraded the control plane with 'kubeadm upgrade apply':
COMPONENT   NODE           CURRENT   TARGET
kubelet     controlplane   v1.29.0   v1.30.3
kubelet     node01         v1.29.0   v1.30.3

Upgrade to the latest stable version:

COMPONENT                 NODE           CURRENT    TARGET
kube-apiserver            controlplane   v1.29.0    v1.30.3
kube-controller-manager   controlplane   v1.29.0    v1.30.3
kube-scheduler            controlplane   v1.29.0    v1.30.3
kube-proxy                               1.29.0     v1.30.3
CoreDNS                                  v1.10.1    v1.11.1
etcd                      controlplane   3.5.10-0   3.5.12-0

You can now apply the upgrade by executing the following command:

        kubeadm upgrade apply v1.30.3

Note: Before you can perform this upgrade, you have to update kubeadm to v1.30.3.

_____________________________________________________________________


The table below shows the current state of component configs as understood by this version of kubeadm.
Configs that have a "yes" mark in the "MANUAL UPGRADE REQUIRED" column require manual config upgrade or
resetting to kubeadm defaults before a successful upgrade can be performed. The version to manually
upgrade to is denoted in the "PREFERRED VERSION" column.

API GROUP                 CURRENT VERSION   PREFERRED VERSION   MANUAL UPGRADE REQUIRED
kubeproxy.config.k8s.io   v1alpha1          v1alpha1            no
kubelet.config.k8s.io     v1beta1           v1beta1             no
_____________________________________________________________________


controlplane ~ ✖ sudo kubeadm upgrade apply v1.30.0
[upgrade/config] Making sure the configuration is correct:
...
[upgrade/successful] SUCCESS! Your cluster was upgraded to "v1.30.0". Enjoy!

[upgrade/kubelet] Now that your control plane is upgraded, please proceed with upgrading your kubelets if you haven't already done so.

controlplane ~ ➜  k get node
NAME           STATUS   ROLES           AGE   VERSION
controlplane   Ready    control-plane   76m   v1.29.0
node01         Ready    <none>          75m   v1.29.0


controlplane ~ ➜  sudo kubeadm upgrade node
[upgrade] Reading configuration from the cluster...
...
upgrade] The configuration for this node was successfully updated!
[upgrade] Now you should go ahead and upgrade the kubelet package using your package manager.

controlplane ~ ➜  kubectl drain controlplane --ignore-daemonsets
node/controlplane cordoned
Warning: ignoring DaemonSet-managed Pods: kube-system/kube-proxy-ppzz4, kube-system/weave-net-hwc6r
node/controlplane drained

controlplane ~ ✖ sudo apt-mark unhold kubelet kubectl && \
    sudo apt-get update && sudo apt-get install -y kubelet='1.30.0-1.1' kubectl='1.30.0-1.1' && \
    sudo apt-mark hold kubelet kubectl
...
kubelet set on hold.
kubectl set on hold.

controlplane ~ ➜  sudo systemctl daemon-reload

controlplane ~ ➜  sudo systemctl restart kubelet

controlplane ~ ➜  kubectl uncordon controlplane
node/controlplane uncordoned

controlplane ~ ➜  k get node
NAME           STATUS   ROLES           AGE   VERSION
controlplane   Ready    control-plane   81m   v1.30.0
node01         Ready    <none>          80m   v1.29.0
```

</details>

