# clj-ketama

clojure implementation of [ketama](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama") consistent hashing scheme

## Usage

```Clojure
(require '[clj-ketama.core :as ketama])
(require '[clj-ketama.server :as s])

(defn resource-hash [resource]
  (Math/abs (.hashCode resource))

(def ring1 (atom (ketama/make-ring [(s/make-server "192.168.1.1" 1) ; server1 weight = 1 
                                    (s/make-server "192.168.1.2" 2) ; server2 weight = 2
                                    (s/make-server "192.168.1.3" 3) ; serverweight = 3
                                   ])))

(ketama/find-node @ring1 (resource-hash "some_resource-1"))
;; "192.168.1.3"
(ketama/find-node @ring1 (resource-hash "test_resource"))
;; "192.168.1.3"
;; higher weight - higher probability to use this server

(def ring2 (atom (ketama/make-ring [(s/make-server "192.168.1.1" 1)
                                    (s/make-server "192.168.1.2" 2)
                                    (s/make-server "192.168.1.3" 3)
                                    (s/make-server "192.168.1.4" 4)])))

(ketama/find-node @ring2 (resource-hash "some_resource-1"))
;; "192.168.1.4"
(ketama/find-node @ring2 (resource-hash "test_resource"))
;; "192.168.1.3"

;; in case of topology changes its supposed to create new ring based on new list of servers
;; current servers can be obtained:
(ketama/get-servers @ring2)


```

## Links

1. Clojure [http://clojure.org/](http://clojure.org/ "Clojure")  
2. Consistent hashing [https://en.wikipedia.org/wiki/Consistent_hashing](https://en.wikipedia.org/wiki/Consistent_hashing "Consistent hashing")  
3. Ketama consistent hashing [algorithm](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama")  
