# TLS in Kubernetes: Certificate Creation

Certificate (인증서)를 생성하기 위해서 다양한 툴들이 존재

- Easy RSA
- OpenSSL
- CF SSL


### Open SSL

<pre>
<b>FYI.</b>
<code lang="Bash">OPENSSL(1ssl)                       OpenSSL                      OPENSSL(1ssl)


NAME
       openssl - OpenSSL command line program

SYNOPSIS
       openssl command [ options ... ] [ parameters ... ]

       openssl no-XXX [ options ]

       openssl -help | -version

DESCRIPTION
       OpenSSL is a cryptography toolkit implementing the Secure Sockets Layer
       (SSL) and Transport Layer Security (TLS) network protocols and related
       cryptography standards required by them.

       The openssl program is a command line program for using the various
       cryptography functions of OpenSSL's crypto library from the shell.  It
       can be used for

        o  Creation and management of private keys, public keys and parameters
        o  Public key cryptographic operations
        o  Creation of X.509 certificates, CSRs and CRLs
        o  Calculation of Message Digests and Message Authentication Codes
        o  Encryption and Decryption with Ciphers
        o  SSL/TLS Client and Server Tests
        o  Handling of S/MIME signed or encrypted mail
        o  Timestamp requests, generation and verification
</code></pre>

<br>

**1. Generate Keys**


OpenSSL 을 통한 키 생성 요청

> **openssl genrsa**: Generation of RSA Private Key. Superseded by openssl-genpkey(1).


비밀키 이름을 지정해서 `ca.key` 생성 RSA 2048 bit 키 생성  


```Bash
❯ openssl genrsa -out ca.key 2048
```

<br>

발급된 `ca.key` 확인

```Bash
❯ openssl rsa -in ca.key -check
RSA key ok
writing RSA key
-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCyCww0wYOQpxgB
kSy0PNAfPKOPIPzBqjFGh4SyMqm31HtliWDKJAKj97Vr1NK9nSfd+DVS8O82TJOU
...
bDeK3YubrXKDviOnwEpB4C66Z4Vc+jyDy3okAC6Ym4+vJdxo8tmrfLFb8UadeuEc
DFv06/XMm9YRyettDkTDYdU=
-----END PRIVATE KEY-----
```

<br>

**2. Certificate Signing Request**

> **openssl req** PKCS#10 X.509 Certificate Signing Request (CSR) Management.

그런 다음 방금 만든 키와 함께 OpenSSL Request 명령을 사용하여 **인증서 서명 요청** 생성

인증서 서명 요청은 요청자의 모든 세부 정보가 포함되어 있지만 서명은 없는 인증서와 같음

```Bash
❯ openssl req -new -key ca.key -subj "/CN=KUBERNETES-CA" -out ca.csr
```

인증서 서명 요청을 위해서는 CN (Common name) 필드에 특정 이름을 명시

> CSR is short for Certificate Signing Request. A CSR is created from the Private Key and is then further used to create the final certificate.
> 
> - https://www.geekersdigest.com/how-to-create-and-work-with-openssl-ssl-tls-certificates/

이 경우 Kubernetes CA에 대한 인증서를 생성하기 때문에 `KUBERNETES-CA` 라고 명명

생성된 `ca.csr` 정보 확인

<br>

```Bash
❯ openssl req -noout -text -in ca.csr
Certificate Request:
    Data:
        Version: 1 (0x0)
        Subject: CN=KUBERNETES-CA
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:b2:...:b4:3c:
                    ...
                Exponent: 65537 (0x10001)
        Attributes:
            (none)
            Requested Extensions:
    Signature Algorithm: sha256WithRSAEncryption
    Signature Value:
        65:4e:82:...:50:7a:52:
        ...

❯ cat ca.csr
-----BEGIN CERTIFICATE REQUEST-----
MIICXTCCAUUCAQAwGDEWMBQGA1UEAwwNS1VCRVJORVRFUy1DQTCCASIwDQYJKoZI
hvcNAQEBBQADggEPADCCAQoCggEBALILDDTBg5CnGAGRLLQ80B88o48g/MGqMUaH
...
yYcdXFnXWTTC0/sy+shQQGa4A/qg/W6NceKe0/4N3Te/6HeS4evSa4zlQppKueq0
tM4xOZXAp8vl36DouM2qpF3R6lq/21yb3eO4GFCP6cGY
-----END CERTIFICATE REQUEST-----
```

<br>

**3. Sign Certificates**

마지막으로 OpenSSL x509 명령에, 이전 명령에서 생성한 인증서를 명시하여 서명 요청 전송

```Bash
❯ openssl x509 -req -in ca.csr -signkey ca.key -out ca.crt
Certificate request self-signature ok
subject=CN=KUBERNETES-CA
```

이는 CA 자체를 위한 것으로, CA는 첫 번째 단계에서 생성한 자체 개인 키를 사용하여 CA에 의해 자체 서명됨

다른 모든 인증서에는 CA 키 페어를 써서 서명

```Bash
❯ openssl x509 -text -in ca.crt
Certificate:
    Data:
        Version: 3 (0x2)
        Serial Number:
            7d:cc:...:3b:75:e6
        Signature Algorithm: sha256WithRSAEncryption
        Issuer: CN=KUBERNETES-CA
        Validity
            Not Before: May  5 16:28:54 2024 GMT
            Not After : Jun  4 16:28:54 2024 GMT
        Subject: CN=KUBERNETES-CA
        Subject Public Key Info:
            Public Key Algorithm: rsaEncryption
                Public-Key: (2048 bit)
                Modulus:
                    00:b2:...:2c:b4:3c:
                    ...
                Exponent: 65537 (0x10001)
        X509v3 extensions:
            X509v3 Subject Key Identifier:
                9C:30:...:50:F4:3A
    Signature Algorithm: sha256WithRSAEncryption
    Signature Value:
        71:8a:8f:...:5f:27:
        ...
-----BEGIN CERTIFICATE-----
MIIC3zCCAcegAwIBAgIUfcy8HfmVcw2boU9s/I2Yxao7deYwDQYJKoZIhvcNAQEL
BQAwGDEWMBQGA1UEAwwNS1VCRVJORVRFUy1DQTAeFw0yNDA1MDUxNjI4NTRaFw0y
...
WkOioG3CW8OdGQtyJ6MlMfBXsEiShO6S2o39NfA9axo2csqeGErgnHEe4ehCtedJ
kkBVtvyg7lgmr7TJ9AEeQ0usQw==
-----END CERTIFICATE-----

```

CA는 이제 개인 키와 Root 인증서 파일을 준비 완료




















