# clj-ketama

clojure implementation of [ketama](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama") consistent hashing scheme

## Usage

```Clojure
(require '[clj-ketama.consistent-hash :as ketama])
(require '[clj-ketama.server])
(import  '[clj_ketama.server Server])

(defn resource-hash [resource]
  (Math/abs (.hashCode resource))


(def ring1 (atom (ketama/make-ring [(Server. "192.168.1.1" 1) ; server1 weight = 1 
                                    (Server. "192.168.1.2" 2) ; server2 weight = 2
                                    (Server. "192.168.1.3" 3) ; serverweight = 3
                                   ])))

(ketama/find-node @ring1 (resource-hash "some_resource-1"))
;; "192.168.1.3"
(ketama/find-node @ring1 (resource-hash "test_resource"))
;; "192.168.1.3"

(def ring2 (atom (ketama/make-ring [(Server. "192.168.1.1" 1)
                                   (Server. "192.168.1.2" 2)
                                   (Server. "192.168.1.3" 3)
                                   (Server. "192.168.1.4" 4)])))

(ketama/find-node @ring2 (resource-hash "some_resource-1"))
;; "192.168.1.4"
(ketama/find-node @ring2 (resource-hash "test_resource"))
;; "192.168.1.3"

(def ring3 (atom (ketama/make-ring [(Server. "192.168.1.1" 1)
                                   (Server. "192.168.1.2" 2)])))

(ketama/find-node @ring3 (resource-hash "some_resource-1"))
;; "192.168.1.2"
(ketama/find-node @ring3 (resource-hash "test_resource"))
;; "192.168.1.2"


```

## Links

1. Clojure [http://clojure.org/](http://clojure.org/ "Clojure")  
2. Consistent hashing [https://en.wikipedia.org/wiki/Consistent_hashing](https://en.wikipedia.org/wiki/Consistent_hashing "Consistent hashing")  
3. Ketama consistent hashing algorithm [link](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama")  
