# 성능 최적화 팁 정리


### Tip 1.

DB성능의 대부분은 I/O 엑세스에서 발생.

I/O 튜닝의 핵심 원리는 아래 2가지 항목이 됨

  1. Sequential 액세스의 선택도를 높인다(레코드간 논리적 또는 물리적인 순서를 따라 차례대로 읽음)
  2. Random 액세스 발생량을 줄임(레코드간 논리적, 물리적인 순서를 따르지 않고 한건을 읽기 위해 한 블록씩접근)