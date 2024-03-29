SELECT x1, y1, x2, y2 FROM test.multi_in_clause order by pk;
SHOW INDEX FROM test.multi_in_clause;


EXPLAIN SELECT * FROM test.multi_in_clause WHERE (x1, y1, x2, y2) IN (
(251, 337, 931, 647),
(440, 261, 985, 143),
(760, 372, 578, 778),
(154, 436, 718, 283),
(262, 459, 513, 186),
(392, 404, 845,  12),
(527, 599, 416, 284),
(170, 999, 487, 436),
(719, 288, 282, 549),
(900, 851, 555, 224),
(457, 611, 684, 586),
(882, 651, 610,  97),
(655, 984, 957, 833),
(293, 969, 966, 923),
(717, 818, 939, 243),
(398, 261, 112, 775),
(543, 388, 311, 391),
( 23, 944, 649, 414),
(125, 382, 537, 540),
( 90, 829, 876, 893),
(836, 504,  12, 549),
(708, 895, 350,  66),
(282, 212, 216, 442),
(561, 483, 730, 201),
(818, 485, 973, 411),
(134, 439, 791, 642),
(837, 261, 792, 176),
(507,   6, 512, 542),
(172, 236, 666, 622),
(112, 695, 139, 611),
(638, 356, 869, 275),
(770,  26, 818,  14),
(616,  40, 351, 635),
(122, 705, 160, 688),
(957, 724, 750, 577),
(637, 453, 356, 420),
( 31, 898, 397, 293),
(274, 492, 638, 716),
(664, 173, 872, 845),
(607, 502, 691, 948),
(666, 488, 442, 745),
(400, 765, 626, 837),
(307,  23, 196, 912),
(972, 124, 705, 151),
(644, 764, 891, 161),
(133, 181, 510,   6),
(501, 486, 929, 187),
(150, 189, 497, 916),
( 91, 705, 254, 156),
( 18, 622,  56, 415),
(906, 286, 714, 712),
(419, 961, 547, 854),
(628, 581,  19, 353),
(710, 490, 320, 130),
(691,  66, 258,  93),
(689, 167, 767, 336),
(379, 889, 306, 867),
(414, 471, 113, 153),
(426, 672,  81, 389),
(702, 343, 609,  19),
(268, 285, 618, 239),
(340, 984, 899, 544),
( 24, 490, 377, 414),
(942, 470, 521, 196),
(416, 495, 227, 649),
(565, 877, 693, 833),
( 87, 937, 422, 303),
(247, 325, 887, 462),
(647, 852, 318,  35),
(223,  10, 382, 879),
(250, 613, 316, 741),
(757, 562, 542,  21),
(483, 351, 305, 473),
(449, 828, 794, 488),
( 58, 825, 954, 295),
(612, 176,  46, 700),
(364, 719, 503, 358),
(284, 345, 876, 343),
( 87, 408, 780, 676),
( 41, 179, 771, 320),
(285, 466, 478, 990),
(517, 615, 527, 787),
(358, 428,  67,  51),
( 54, 116, 421, 755),
(513, 299, 958, 893),
(591, 276, 609, 219),
(266, 675, 578, 864),
(587, 344, 959, 765),
(950, 452, 412, 702),
(278, 283, 583,  64),
(571, 663, 603,  29),
(335, 588, 935, 914),
(764,  78,  97, 255),
(982, 148, 794, 526),
(251, 674, 618,  69),
(490, 244, 753,  31),
(896, 387, 247,  76),
(638, 965, 911, 659),
(563, 837, 497, 977),
(391,  26, 959, 716),
(705, 376, 767, 708),
(240,  77, 666,  99),
(495, 181, 420, 555),
(518, 925,  72, 587),
(717, 825, 973, 390),
( 34, 998, 890, 456),
(611, 685, 596, 925),
(835, 402, 507, 330),
(129, 654, 886, 468),
(681,   3, 971, 847),
(321,  67, 371, 653),
(153, 807, 575, 454),
(547, 375, 234,  44),
(520, 466, 771, 458),
(978, 514, 639, 655),
(356, 817,  19, 641),
(151, 832, 708,  44),
( 98, 357, 492, 387),
(463, 152, 374, 413),
(942, 472, 535, 259),
(689, 669, 279, 390),
(114, 400, 659,  93),
(492, 179, 420, 563),
(558,  98, 818, 795),
(524, 233, 593, 269),
(567,  29, 444, 133),
(332, 262, 315, 788),
(996, 615,  88, 595),
(711, 772, 729, 329),
(456, 296, 113, 678),
( 50, 215, 929, 998),
(205,  32, 546, 635),
(535, 772, 256, 963),
( 47, 347, 596, 939),
(908, 723, 891, 287),
(760, 943, 433, 338),
(390, 936, 511, 746),
(197, 751, 162, 559),
(309, 866, 407, 437),
(966, 516, 684, 875),
(320, 976, 920, 671),
(598, 975,  82, 485),
(180, 446, 688, 106)
);
/*
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
| id | select_type | table           | partitions | type  | possible_keys | key    | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | multi_in_clause | NULL       | range | IDX_XY        | IDX_XY | 16      | NULL |  148 |   100.00 | Using where; Using index |
+----+-------------+-----------------+------------+-------+---------------+--------+---------+------+------+----------+--------------------------+
1 row in set, 1 warning (0.02 sec)
*/