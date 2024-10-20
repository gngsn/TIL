# VPC Firewall - Network Access Control List


>    🖥️ ← 📋←⎯ Allowed ⎯⎯   👩🏻‍💻
>
>  **Inbound NACL**
>
>  | Port Range | Source    |
> |------------|-----------|
> | `80`       | `1.2.3.4` |
>
>
>    🖥️  ⎯⎯ Allowed ⎯→   👩🏻‍💻  ⬅ **Stateful**
>
>  Even if Outbound Rule doesn't have anything.


어떤 Port 로 나가야할지 모르기 때문에 
사용자가 사용할 수 있는 포트 범위인 `32768 ~ 60999` 범위를 모두 입력해줌

> Ephemeral port range
> 
> | Range	      | Operating system               |
> |-------------|--------------------------------|
> | 32768–60999 | used by many Linux kernels.    |
> | 32768–65535 | used by Solaris OS and AIX OS. |
> | 1024–65535  | RFC 6056                       |
> | ...         | ...                            |
> 
> [🔗 Wikipedia - Ephemeral Port](https://en.wikipedia.org/wiki/Ephemeral_port)
	
	
	




