
- linux 에서 OS 확인

```Bash
# cat /etc/os-release 
NAME="Alpine Linux"
ID=alpine
VERSION_ID=3.18.4
PRETTY_NAME="Alpine Linux v3.18"
HOME_URL="https://alpinelinux.org/"
BUG_REPORT_URL="https://gitlab.alpinelinux.org/alpine/aports/-/issues"
```

<br/>

- `Alpine`에서 `curl` 설치

```Bash
/ # apk --no-cache add curl
fetch https://dl-cdn.alpinelinux.org/alpine/v3.18/main/aarch64/APKINDEX.tar.gz
fetch https://dl-cdn.alpinelinux.org/alpine/v3.18/community/aarch64/APKINDEX.tar.gz

(1/6) Installing brotli-libs (1.0.9-r14)
(2/6) Installing libunistring (1.1-r1)
(3/6) Installing libidn2 (2.3.4-r1)
(4/6) Installing nghttp2-libs (1.57.0-r0)
(5/6) Installing libcurl (8.4.0-r0)
(6/6) Installing curl (8.4.0-r0)
Executing busybox-1.36.1-r4.trigger
OK: 47 MiB in 61 packages

/ # curl --version
curl 8.4.0 (aarch64-alpine-linux-musl) libcurl/8.4.0 OpenSSL/3.1.4 zlib/1.2.13 brotli/1.0.9 libidn2/2.3.4 nghttp2/1.57.0
Release-Date: 2023-10-11
Protocols: dict file ftp ftps gopher gophers http https imap imaps mqtt pop3 pop3s rtsp smb smbs smtp smtps telnet tftp ws wss
Features: alt-svc AsynchDNS brotli HSTS HTTP2 HTTPS-proxy IDN IPv6 Largefile libz NTLM SSL threadsafe TLS-SRP UnixSockets
```

<br/>

- docker registry 에 등록된 리스트 확인하기

```Bash
/ # curl -X GET http://localhost:5000/v2/_catalog
{"repositories":["gngsn/nginx","gngsn/nginx-nuxt","gngsn/todoapi","gngsn/tododb","gngsn/tododb-master","gngsn/tododb-slave","gngsn/todoweb"]}
```

<br/>

- docker registry 에 등록된 `tag list` 확인하기
```Bash
/ # curl -X GET http://localhost:5000/v2/{image_name}/tags/list
```

```Bash
-- example
/ # curl -X GET http://localhost:5000/v2/gngsn/tododb/tags/list
{"name":"gngsn/tododb","tags":["latest"]}
```