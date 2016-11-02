(ns clj-ketama.core-test
  (:require [clojure.test :refer :all])
  (:require [clj-ketama.core :as ketama])
  (:require [clj-ketama.server :as svr]))

(deftest no-servers
  (is (thrown? IllegalArgumentException
               (ketama/make-ring []))))

(deftest single-server
  (let [ring (ketama/make-ring [(svr/make-server "svr" 123)])]
    (is (= "svr"
           (ketama/find-node ring 0)))))

(deftest get-servers
  (let [servers [(svr/make-server "abc" 2)
                 (svr/make-server "def" 10)
                 (svr/make-server "xyz" 19)]
        ring (ketama/make-ring servers)]
    (is (= servers (ketama/get-servers ring)))))

(deftest ring-distribution-simple
  (let [servers [(svr/make-server "abc" 2)
                 (svr/make-server "def" 10)
                 (svr/make-server "xyz" 19)]
        ring (ketama/make-ring servers)]
    (are [hash expected-server-name]
        (= expected-server-name
           (ketama/find-node ring hash))
      0 "xyz"
      348807 "xyz"
      1490535 "abc"
      3549308 "xyz"
      7874284 "xyz"
      8693666 "def"
      9185365 "xyz"
      21756786 "def"
      27440544 "xyz"
      40230023 "abc"
      42731476 "xyz"
      43382758 "xyz"
      46839417 "xyz"
      61614724 "def"
      67431050 "xyz"
      79730703 "xyz"
      93005009 "def"
      101903653 "abc"
      133751847 "xyz"
      139304271 "xyz"
      151882042 "xyz"
      167794016 "xyz"
      168133897 "xyz"
      174547158 "xyz"
      185036249 "xyz"
      187229212 "xyz"
      200549217 "def"
      204454161 "xyz"
      218930468 "xyz"
      239378544 "xyz"
      243353743 "def"
      245243319 "xyz"
      261466433 "def"
      263060385 "abc"
      268631200 "def"
      268661468 "xyz"
      273034927 "def"
      276575760 "abc"
      278650024 "xyz"
      287724469 "abc"
      291614403 "xyz"
      301802930 "def"
      314843910 "xyz"
      324661112 "abc"
      331358173 "xyz"
      332820369 "def"
      333137264 "xyz"
      365789123 "xyz"
      374712354 "xyz"
      376437780 "xyz"
      382860289 "def"
      395431109 "def"
      401834106 "xyz"
      403819125 "xyz"
      416138655 "def"
      424931253 "abc"
      433397323 "xyz"
      438550777 "def"
      446004713 "xyz"
      450742155 "xyz"
      467783896 "def"
      524225002 "xyz"
      531126152 "def"
      552426632 "def"
      590797392 "abc"
      616186383 "xyz"
      640224668 "xyz"
      650917004 "xyz"
      653525793 "def"
      674097584 "def"
      675106862 "def"
      677920924 "xyz"
      691710055 "def"
      697711442 "xyz"
      702508064 "xyz"
      719844961 "xyz"
      727580835 "xyz"
      727712733 "xyz"
      728127585 "xyz"
      729636893 "def"
      733805483 "def"
      739648643 "xyz"
      739667361 "xyz"
      741845849 "xyz"
      754558638 "xyz"
      758000497 "xyz"
      769924390 "def"
      779979827 "xyz"
      789089897 "xyz"
      805138301 "xyz"
      806863140 "def"
      811893385 "xyz"
      820861760 "xyz"
      831663912 "xyz"
      832341227 "def"
      832560512 "def"
      850714386 "def"
      859368218 "xyz"
      887172741 "xyz"
      897552338 "def"
      924724372 "def"
      936279822 "xyz"
      953499101 "abc"
      959036045 "xyz"
      960164216 "xyz"
      961295103 "xyz"
      965186719 "def"
      982311639 "def"
      985513403 "xyz"
      1009276915 "xyz"
      1024716784 "xyz"
      1030673046 "def"
      1032340106 "def"
      1039975701 "def"
      1057954120 "xyz"
      1058729715 "xyz"
      1069555484 "def"
      1070446177 "xyz"
      1079381777 "xyz"
      1079576888 "xyz"
      1081028501 "xyz"
      1085753031 "def"
      1108652134 "def"
      1111096128 "xyz"
      1113906757 "def"
      1117732457 "xyz"
      1118741366 "xyz"
      1127385311 "xyz"
      1131965330 "def"
      1145660140 "def"
      1162436311 "xyz"
      1165569642 "xyz"
      1172713350 "xyz"
      1173795289 "xyz"
      1178085286 "xyz"
      1180207365 "def"
      1180380974 "xyz"
      1192138817 "def"
      1200793691 "xyz"
      1204631898 "xyz"
      1206393082 "def"
      1221576287 "def"
      1231611205 "xyz"
      1261530551 "xyz"
      1284064341 "def"
      1294477914 "xyz"
      1302291870 "xyz"
      1326337831 "xyz"
      1326919124 "xyz"
      1331243063 "def"
      1333220058 "xyz"
      1396473385 "xyz"
      1397669814 "xyz"
      1402476055 "xyz"
      1409927721 "xyz"
      1414924738 "xyz"
      1433285897 "xyz"
      1435576062 "xyz"
      1435687503 "xyz"
      1439860784 "abc"
      1466233907 "def"
      1483805368 "abc"
      1506892334 "def"
      1515930888 "xyz"
      1529109691 "def"
      1551717501 "def"
      1560954361 "xyz"
      1564175192 "xyz"
      1567388312 "xyz"
      1569664766 "def"
      1582218832 "def"
      1592579498 "xyz"
      1607983862 "abc"
      1612473498 "def"
      1623200274 "xyz"
      1637441242 "xyz"
      1642119058 "def"
      1659105799 "xyz"
      1678360453 "def"
      1683373570 "def"
      1694514197 "xyz"
      1702401968 "xyz"
      1709275074 "xyz"
      1719706032 "def"
      1759456799 "abc"
      1765315980 "xyz"
      1766686907 "xyz"
      1776655531 "def"
      1791228601 "def"
      1802983415 "abc"
      1808022643 "xyz"
      1808333144 "abc"
      1816871848 "def"
      1820266788 "xyz"
      1834392270 "def"
      1844628562 "xyz"
      1856248323 "xyz"
      1859528079 "def"
      1860968358 "xyz"
      1861699373 "xyz"
      1878433310 "xyz"
      1888666629 "def"
      1893314303 "xyz"
      1905397969 "def"
      1905681446 "xyz"
      1905750151 "xyz"
      1909337109 "abc"
      1910426305 "def"
      1915002769 "xyz"
      1938515414 "xyz"
      1965103829 "xyz"
      1968742655 "def"
      1972314078 "xyz"
      1988460106 "def"
      1989755708 "xyz"
      1997863631 "xyz"
      2021027560 "xyz"
      2039008111 "def"
      2067665122 "xyz"
      2107380987 "def"
      2120493237 "def"
      2142213250 "xyz"
      2151183072 "xyz"
      2160313425 "xyz"
      2165008649 "xyz"
      2167098537 "def"
      2173526796 "xyz"
      2177770037 "def"
      2183182202 "xyz"
      2184669884 "def"
      2188522070 "xyz"
      2195974602 "xyz"
      2208065356 "xyz"
      2210042312 "def"
      2210702310 "def"
      2210889861 "xyz"
      2231637920 "xyz"
      2246440562 "xyz"
      2276710603 "xyz"
      2279247602 "xyz"
      2290318160 "xyz"
      2310248875 "def"
      2313275883 "xyz"
      2320727861 "def"
      2321385008 "xyz"
      2330844815 "def"
      2344575029 "xyz"
      2370837089 "def"
      2389197631 "xyz"
      2395291724 "xyz"
      2397421078 "def"
      2421055205 "def"
      2434657203 "def"
      2445431456 "def"
      2446704810 "xyz"
      2448158555 "xyz"
      2467940926 "xyz"
      2478505400 "def"
      2489492435 "def"
      2495373406 "def"
      2502472932 "xyz"
      2517371842 "xyz"
      2522121955 "xyz"
      2541019798 "def"
      2548726676 "xyz"
      2558630146 "xyz"
      2560292185 "xyz"
      2573313105 "def"
      2611984599 "def"
      2612315048 "xyz"
      2617449954 "xyz"
      2618770804 "xyz"
      2627854086 "xyz"
      2642632867 "def"
      2646250286 "xyz"
      2655237631 "xyz"
      2663774278 "xyz"
      2666739288 "xyz"
      2667310524 "def"
      2673160242 "abc"
      2703840503 "xyz"
      2708692871 "abc"
      2716698924 "xyz"
      2721697666 "xyz"
      2728612399 "def"
      2733588646 "def"
      2734262771 "def"
      2735710300 "xyz"
      2746161582 "def"
      2746289598 "def"
      2746835077 "abc"
      2747774360 "def"
      2759471469 "xyz"
      2771215594 "def"
      2772640924 "xyz"
      2785584444 "xyz"
      2796200578 "xyz"
      2798079269 "xyz"
      2799314398 "xyz"
      2807098094 "xyz"
      2808045459 "xyz"
      2830331017 "xyz"
      2834113343 "xyz"
      2834351239 "def"
      2836726811 "xyz"
      2862673093 "xyz"
      2874527777 "def"
      2876018558 "xyz"
      2881187647 "xyz"
      2881471652 "xyz"
      2883626174 "xyz"
      2895653728 "xyz"
      2905596296 "xyz"
      2912734983 "abc"
      2924721091 "xyz"
      2926326515 "def"
      2933184016 "xyz"
      2941697737 "def"
      2942298548 "def"
      2957593448 "xyz"
      2959479006 "def"
      2960995432 "xyz"
      2989155456 "def"
      2996738362 "def"
      3006000043 "def"
      3011810350 "xyz"
      3016897404 "xyz"
      3018295511 "xyz"
      3022355771 "def"
      3024274901 "xyz"
      3039926348 "def"
      3045914545 "def"
      3054701220 "def"
      3070401685 "xyz"
      3075168902 "xyz"
      3093963653 "def"
      3108268826 "xyz"
      3135482562 "xyz"
      3144254640 "xyz"
      3159081173 "xyz"
      3162080413 "xyz"
      3176564353 "xyz"
      3192301753 "xyz"
      3199012227 "def"
      3208848733 "xyz"
      3225164316 "xyz"
      3225638099 "xyz"
      3259821577 "xyz"
      3264409020 "xyz"
      3270803490 "def"
      3299171321 "def"
      3317968987 "abc"
      3345140881 "def"
      3348992076 "xyz"
      3354367203 "xyz"
      3374435518 "def"
      3376150578 "xyz"
      3377293840 "def"
      3385154259 "xyz"
      3387069683 "def"
      3393115564 "abc"
      3397902276 "xyz"
      3399322640 "def"
      3400596754 "def"
      3402401493 "xyz"
      3415072462 "xyz"
      3415837681 "def"
      3432915783 "def"
      3444081142 "xyz"
      3447715724 "def"
      3449498556 "xyz"
      3468088625 "xyz"
      3474246470 "xyz"
      3482107532 "xyz"
      3483501469 "abc"
      3490392791 "def"
      3492775249 "xyz"
      3513789176 "xyz"
      3517937816 "xyz"
      3517958238 "xyz"
      3519430903 "def"
      3519657657 "def"
      3529620242 "xyz"
      3531535625 "abc"
      3538221388 "xyz"
      3542307962 "def"
      3542708020 "def"
      3547213459 "xyz"
      3553910699 "xyz"
      3563889593 "xyz"
      3572914593 "def"
      3573209320 "xyz"
      3586466617 "xyz"
      3593695140 "xyz"
      3607017694 "xyz"
      3613118311 "def"
      3614764937 "xyz"
      3616425049 "xyz"
      3623933390 "xyz"
      3632303584 "xyz"
      3637163584 "def"
      3667739467 "def"
      3676213914 "def"
      3676495713 "def"
      3688218033 "xyz"
      3689901992 "xyz"
      3705630954 "xyz"
      3706359288 "def"
      3712205798 "def"
      3714230757 "xyz"
      3724516657 "xyz"
      3726964375 "xyz"
      3732058522 "xyz"
      3738468766 "xyz"
      3741741443 "def"
      3750205944 "def"
      3790232742 "def"
      3791811773 "xyz"
      3791874573 "abc"
      3791990049 "xyz"
      3802927407 "xyz"
      3806814784 "xyz"
      3807265820 "xyz"
      3817449688 "xyz"
      3835906645 "xyz"
      3867217709 "xyz"
      3869611398 "def"
      3869823783 "def"
      3893969704 "xyz"
      3908315114 "xyz"
      3913431297 "xyz"
      3913629030 "def"
      3922015564 "xyz"
      3933240059 "xyz"
      3935142928 "xyz"
      3944813928 "xyz"
      3947979286 "xyz"
      3948049843 "def"
      3953354352 "xyz"
      3957703759 "xyz"
      3959152330 "def"
      3966094832 "def"
      3972108856 "xyz"
      3976819577 "xyz"
      3977946975 "xyz"
      3980567085 "abc"
      3982091016 "xyz"
      4006153239 "xyz"
      4033875054 "xyz"
      4038251010 "def"
      4047486085 "xyz"
      4049334070 "xyz"
      4056797598 "xyz"
      4071575246 "xyz"
      4078332030 "def"
      4087224470 "xyz"
      4089363566 "xyz"
      4104488312 "xyz"
      4138798343 "xyz"
      4148811535 "abc"
      4159058475 "xyz"
      4169500061 "def"
      4176812083 "xyz"
      4183094544 "xyz"
      4187562118 "xyz"
      4193607993 "xyz"
      4223022032 "xyz"
      4224757518 "xyz"
      4246826732 "xyz"
      4259072803 "xyz"
      4259637271 "xyz"
      4261097964 "xyz"
      4270143576 "def"
      4270143577 "xyz")))
