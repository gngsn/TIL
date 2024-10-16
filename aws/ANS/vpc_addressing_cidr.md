# VPC Addressing (CIDR)

- IP 주소는 네트워크 내에서 사용하는 각 호스트의 ID 
  - 두 종류의 IP 주소가 존재: IPv4 (32 bit) 와 IPv6 (128 bit)
- **IPv4 address**
  - 네 개의 옥텟(octet) 으로 구성 (4 x 8 bits). 
    - Example: `x.x.x.x`
  - 각 옥텟은 0-255 범위의 십진수로 표현
    - Example: `192.168.56.212`
  - 주소를 이진수로 표현하고 싶으면 각 옥텟 마다 이진수로 변환해서 표기할 수 있음
    - Example: 192 = 128+64 = 2^7+2^6 = 11000000 
    - 즉, `11000000.10101000.00111000.11010100`

<br>

### CIDR

- IP 주소 할당 스키마 (IP Addressing schema)
- 예전 방식인 Class A, B, C 표기를 대체 
- IP address 와 prefix 로 표기됨: IPv4 CIDR `192.168.0.0/16`


<table>
<tr>
<td colspan="8"><center>192</center></td>
<td>.</td>
<td colspan="8"><center>168</center></td>
<td>.</td>
<td colspan="8"><center>0</center></td>
<td>.</td>
<td colspan="8"><center>0</center></td>
<td>.</td>
<td><b>/16</b></td>
</tr>
<tr>
<td>1</td>
<td>1</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>.</td>
<td>1</td>
<td>0</td>
<td>1</td>
<td>0</td>
<td>1</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>.</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>.</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>0</td>
<td>.</td>
<td><b>/16</b></td>
</tr>
<tr>
<td colspan="17">
<center>
Network Address

`192.168` → 고정 주소
</center>
</td>
<td>.</td>
<td colspan="17">
<center>
<b>Host Address</b>

`0-255.0-255` → 해당 범위 만큼의 IP 개수를 가질 수 있음
</center>
</td>
<td>.</td>
<td><b>/16</b></td>
</tr>
</table>

얼마나 많은 IP 주소를 얻을 수 있나?

<br>

#### Example 1. `10.10.0.0/16` 일 때

- 첫 두 옥텟의 경우의 수는 `1` x `1` = `1`
- 마지막 두 옥텟의 범위의 경우의 수는 `255` x `255` = `65536`

이 때, 다르게 접근해보면 각 옥텟은 8자리의 2진수

→ `0` or `1` 인 경우의 수의 조합이기 때문에 `2^8` * `2^8` = `2^16` 

네 옥텟의 총 경우의 수는 `2^32` 이기 때문에 해당 경우의 수를 제외 시키면 `32 - 16 = 16`

즉, `2^16` = `65536` 

<br>

#### Example 2. `10.100.0.0/24` 일 때

사용 가능한 총 IP 개수는 `32` - `24` = `8`, 즉, `2^8` = `256` 개

<br>

#### Example 2. `10.100.0.0/28` 일 때

사용 가능한 총 IP 개수는 `32` - `28` = `4`, 즉, `2^4` = `16` 개


- `192.168.0.0/24`: `192.168.0.0 – 192.168.0.255` (256 IP)
- `192.168.0.0/16`: `192.168.0.0 – 192.168.255.255` (65,536 IP)
- `134.56.78.123/32` = just `134.56.78.123`
- `0.0.0.0/0`: **All IPs**!

