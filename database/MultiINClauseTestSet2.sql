/*
\(\s*(\d*),\s*(\d*),\s*(\d*),\s*(\d*)\),*

\(x1 = $1 AND y1 = $2 AND x2 = $3 AND y2 = $4\) OR
*/

EXPLAIN  
SELECT * FROM test.multi_in_clause WHERE (
(x1 = 251 AND y1 = 337 AND x2 = 931 AND y2 = 647) OR
(x1 = 440 AND y1 = 261 AND x2 = 985 AND y2 = 143) OR
(x1 = 760 AND y1 = 372 AND x2 = 578 AND y2 = 778) OR
(x1 = 154 AND y1 = 436 AND x2 = 718 AND y2 = 283) OR
(x1 = 262 AND y1 = 459 AND x2 = 513 AND y2 = 186) OR
(x1 = 392 AND y1 = 404 AND x2 = 845 AND y2 = 12) OR
(x1 = 527 AND y1 = 599 AND x2 = 416 AND y2 = 284) OR
(x1 = 170 AND y1 = 999 AND x2 = 487 AND y2 = 436) OR
(x1 = 719 AND y1 = 288 AND x2 = 282 AND y2 = 549) OR
(x1 = 900 AND y1 = 851 AND x2 = 555 AND y2 = 224) OR
(x1 = 457 AND y1 = 611 AND x2 = 684 AND y2 = 586) OR
(x1 = 882 AND y1 = 651 AND x2 = 610 AND y2 = 97) OR
(x1 = 655 AND y1 = 984 AND x2 = 957 AND y2 = 833) OR
(x1 = 293 AND y1 = 969 AND x2 = 966 AND y2 = 923) OR
(x1 = 717 AND y1 = 818 AND x2 = 939 AND y2 = 243) OR
(x1 = 398 AND y1 = 261 AND x2 = 112 AND y2 = 775) OR
(x1 = 543 AND y1 = 388 AND x2 = 311 AND y2 = 391) OR
(x1 = 23 AND y1 = 944 AND x2 = 649 AND y2 = 414) OR
(x1 = 125 AND y1 = 382 AND x2 = 537 AND y2 = 540) OR
(x1 = 90 AND y1 = 829 AND x2 = 876 AND y2 = 893) OR
(x1 = 836 AND y1 = 504 AND x2 = 12 AND y2 = 549) OR
(x1 = 708 AND y1 = 895 AND x2 = 350 AND y2 = 66) OR
(x1 = 282 AND y1 = 212 AND x2 = 216 AND y2 = 442) OR
(x1 = 561 AND y1 = 483 AND x2 = 730 AND y2 = 201) OR
(x1 = 818 AND y1 = 485 AND x2 = 973 AND y2 = 411) OR
(x1 = 134 AND y1 = 439 AND x2 = 791 AND y2 = 642) OR
(x1 = 837 AND y1 = 261 AND x2 = 792 AND y2 = 176) OR
(x1 = 507 AND y1 = 6 AND x2 = 512 AND y2 = 542) OR
(x1 = 172 AND y1 = 236 AND x2 = 666 AND y2 = 622) OR
(x1 = 112 AND y1 = 695 AND x2 = 139 AND y2 = 611) OR
(x1 = 638 AND y1 = 356 AND x2 = 869 AND y2 = 275) OR
(x1 = 770 AND y1 = 26 AND x2 = 818 AND y2 = 14) OR
(x1 = 616 AND y1 = 40 AND x2 = 351 AND y2 = 635) OR
(x1 = 122 AND y1 = 705 AND x2 = 160 AND y2 = 688) OR
(x1 = 957 AND y1 = 724 AND x2 = 750 AND y2 = 577) OR
(x1 = 637 AND y1 = 453 AND x2 = 356 AND y2 = 420) OR
(x1 = 31 AND y1 = 898 AND x2 = 397 AND y2 = 293) OR
(x1 = 274 AND y1 = 492 AND x2 = 638 AND y2 = 716) OR
(x1 = 664 AND y1 = 173 AND x2 = 872 AND y2 = 845) OR
(x1 = 607 AND y1 = 502 AND x2 = 691 AND y2 = 948) OR
(x1 = 666 AND y1 = 488 AND x2 = 442 AND y2 = 745) OR
(x1 = 400 AND y1 = 765 AND x2 = 626 AND y2 = 837) OR
(x1 = 307 AND y1 = 23 AND x2 = 196 AND y2 = 912) OR
(x1 = 972 AND y1 = 124 AND x2 = 705 AND y2 = 151) OR
(x1 = 644 AND y1 = 764 AND x2 = 891 AND y2 = 161) OR
(x1 = 133 AND y1 = 181 AND x2 = 510 AND y2 = 6) OR
(x1 = 501 AND y1 = 486 AND x2 = 929 AND y2 = 187) OR
(x1 = 150 AND y1 = 189 AND x2 = 497 AND y2 = 916) OR
(x1 = 91 AND y1 = 705 AND x2 = 254 AND y2 = 156) OR
(x1 = 18 AND y1 = 622 AND x2 = 56 AND y2 = 415) OR
(x1 = 906 AND y1 = 286 AND x2 = 714 AND y2 = 712) OR
(x1 = 419 AND y1 = 961 AND x2 = 547 AND y2 = 854) OR
(x1 = 628 AND y1 = 581 AND x2 = 19 AND y2 = 353) OR
(x1 = 710 AND y1 = 490 AND x2 = 320 AND y2 = 130) OR
(x1 = 691 AND y1 = 66 AND x2 = 258 AND y2 = 93) OR
(x1 = 689 AND y1 = 167 AND x2 = 767 AND y2 = 336) OR
(x1 = 379 AND y1 = 889 AND x2 = 306 AND y2 = 867) OR
(x1 = 414 AND y1 = 471 AND x2 = 113 AND y2 = 153) OR
(x1 = 426 AND y1 = 672 AND x2 = 81 AND y2 = 389) OR
(x1 = 702 AND y1 = 343 AND x2 = 609 AND y2 = 19) OR
(x1 = 268 AND y1 = 285 AND x2 = 618 AND y2 = 239) OR
(x1 = 340 AND y1 = 984 AND x2 = 899 AND y2 = 544) OR
(x1 = 24 AND y1 = 490 AND x2 = 377 AND y2 = 414) OR
(x1 = 942 AND y1 = 470 AND x2 = 521 AND y2 = 196) OR
(x1 = 416 AND y1 = 495 AND x2 = 227 AND y2 = 649) OR
(x1 = 565 AND y1 = 877 AND x2 = 693 AND y2 = 833) OR
(x1 = 87 AND y1 = 937 AND x2 = 422 AND y2 = 303) OR
(x1 = 247 AND y1 = 325 AND x2 = 887 AND y2 = 462) OR
(x1 = 647 AND y1 = 852 AND x2 = 318 AND y2 = 35) OR
(x1 = 223 AND y1 = 10 AND x2 = 382 AND y2 = 879) OR
(x1 = 250 AND y1 = 613 AND x2 = 316 AND y2 = 741) OR
(x1 = 757 AND y1 = 562 AND x2 = 542 AND y2 = 21) OR
(x1 = 483 AND y1 = 351 AND x2 = 305 AND y2 = 473) OR
(x1 = 449 AND y1 = 828 AND x2 = 794 AND y2 = 488) OR
(x1 = 58 AND y1 = 825 AND x2 = 954 AND y2 = 295) OR
(x1 = 612 AND y1 = 176 AND x2 = 46 AND y2 = 700) OR
(x1 = 364 AND y1 = 719 AND x2 = 503 AND y2 = 358) OR
(x1 = 284 AND y1 = 345 AND x2 = 876 AND y2 = 343) OR
(x1 = 87 AND y1 = 408 AND x2 = 780 AND y2 = 676) OR
(x1 = 41 AND y1 = 179 AND x2 = 771 AND y2 = 320) OR
(x1 = 285 AND y1 = 466 AND x2 = 478 AND y2 = 990) OR
(x1 = 517 AND y1 = 615 AND x2 = 527 AND y2 = 787) OR
(x1 = 358 AND y1 = 428 AND x2 = 67 AND y2 = 51) OR
(x1 = 54 AND y1 = 116 AND x2 = 421 AND y2 = 755) OR
(x1 = 513 AND y1 = 299 AND x2 = 958 AND y2 = 893) OR
(x1 = 591 AND y1 = 276 AND x2 = 609 AND y2 = 219) OR
(x1 = 266 AND y1 = 675 AND x2 = 578 AND y2 = 864) OR
(x1 = 587 AND y1 = 344 AND x2 = 959 AND y2 = 765) OR
(x1 = 950 AND y1 = 452 AND x2 = 412 AND y2 = 702) OR
(x1 = 278 AND y1 = 283 AND x2 = 583 AND y2 = 64) OR
(x1 = 571 AND y1 = 663 AND x2 = 603 AND y2 = 29) OR
(x1 = 335 AND y1 = 588 AND x2 = 935 AND y2 = 914) OR
(x1 = 764 AND y1 = 78 AND x2 = 97 AND y2 = 255) OR
(x1 = 982 AND y1 = 148 AND x2 = 794 AND y2 = 526) OR
(x1 = 251 AND y1 = 674 AND x2 = 618 AND y2 = 69) OR
(x1 = 490 AND y1 = 244 AND x2 = 753 AND y2 = 31) OR
(x1 = 896 AND y1 = 387 AND x2 = 247 AND y2 = 76) OR
(x1 = 638 AND y1 = 965 AND x2 = 911 AND y2 = 659) OR
(x1 = 563 AND y1 = 837 AND x2 = 497 AND y2 = 977) OR
(x1 = 391 AND y1 = 26 AND x2 = 959 AND y2 = 716) OR
(x1 = 705 AND y1 = 376 AND x2 = 767 AND y2 = 708) OR
(x1 = 240 AND y1 = 77 AND x2 = 666 AND y2 = 99) OR
(x1 = 495 AND y1 = 181 AND x2 = 420 AND y2 = 555) OR
(x1 = 518 AND y1 = 925 AND x2 = 72 AND y2 = 587) OR
(x1 = 717 AND y1 = 825 AND x2 = 973 AND y2 = 390) OR
(x1 = 34 AND y1 = 998 AND x2 = 890 AND y2 = 456) OR
(x1 = 611 AND y1 = 685 AND x2 = 596 AND y2 = 925) OR
(x1 = 835 AND y1 = 402 AND x2 = 507 AND y2 = 330) OR
(x1 = 129 AND y1 = 654 AND x2 = 886 AND y2 = 468) OR
(x1 = 681 AND y1 = 3 AND x2 = 971 AND y2 = 847) OR
(x1 = 321 AND y1 = 67 AND x2 = 371 AND y2 = 653) OR
(x1 = 153 AND y1 = 807 AND x2 = 575 AND y2 = 454) OR
(x1 = 547 AND y1 = 375 AND x2 = 234 AND y2 = 44) OR
(x1 = 520 AND y1 = 466 AND x2 = 771 AND y2 = 458) OR
(x1 = 978 AND y1 = 514 AND x2 = 639 AND y2 = 655) OR
(x1 = 356 AND y1 = 817 AND x2 = 19 AND y2 = 641) OR
(x1 = 151 AND y1 = 832 AND x2 = 708 AND y2 = 44) OR
(x1 = 98 AND y1 = 357 AND x2 = 492 AND y2 = 387) OR
(x1 = 463 AND y1 = 152 AND x2 = 374 AND y2 = 413) OR
(x1 = 942 AND y1 = 472 AND x2 = 535 AND y2 = 259) OR
(x1 = 689 AND y1 = 669 AND x2 = 279 AND y2 = 390) OR
(x1 = 114 AND y1 = 400 AND x2 = 659 AND y2 = 93) OR
(x1 = 492 AND y1 = 179 AND x2 = 420 AND y2 = 563) OR
(x1 = 558 AND y1 = 98 AND x2 = 818 AND y2 = 795) OR
(x1 = 524 AND y1 = 233 AND x2 = 593 AND y2 = 269) OR
(x1 = 567 AND y1 = 29 AND x2 = 444 AND y2 = 133) OR
(x1 = 332 AND y1 = 262 AND x2 = 315 AND y2 = 788) OR
(x1 = 996 AND y1 = 615 AND x2 = 88 AND y2 = 595) OR
(x1 = 711 AND y1 = 772 AND x2 = 729 AND y2 = 329) OR
(x1 = 456 AND y1 = 296 AND x2 = 113 AND y2 = 678) OR
(x1 = 50 AND y1 = 215 AND x2 = 929 AND y2 = 998) OR
(x1 = 205 AND y1 = 32 AND x2 = 546 AND y2 = 635) OR
(x1 = 535 AND y1 = 772 AND x2 = 256 AND y2 = 963) OR
(x1 = 47 AND y1 = 347 AND x2 = 596 AND y2 = 939) OR
(x1 = 908 AND y1 = 723 AND x2 = 891 AND y2 = 287) OR
(x1 = 760 AND y1 = 943 AND x2 = 433 AND y2 = 338) OR
(x1 = 390 AND y1 = 936 AND x2 = 511 AND y2 = 746) OR
(x1 = 197 AND y1 = 751 AND x2 = 162 AND y2 = 559) OR
(x1 = 309 AND y1 = 866 AND x2 = 407 AND y2 = 437) OR
(x1 = 966 AND y1 = 516 AND x2 = 684 AND y2 = 875) OR
(x1 = 320 AND y1 = 976 AND x2 = 920 AND y2 = 671) OR
(x1 = 598 AND y1 = 975 AND x2 = 82 AND y2 = 485) OR
(x1 = 180 AND y1 = 446 AND x2 = 688 AND y2 = 106)
);
/*
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
| id | select_type | table           | partitions | type  | possible_keys | key    | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | multi_in_clause | NULL       | range | IDX_XY        | IDX_XY | 16      | NULL |  148 |   100.00 | Using where; Using index |
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
1 row in set, 1 warning (0.03 sec)
*/
