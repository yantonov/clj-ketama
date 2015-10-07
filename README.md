# clj-ketama

clojure implementation of [ketama](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama") consistent hashing scheme

## Usage

```Clojure
(require '[clj-ketama.consistent-hash :as ketama])
(require '[clj-ketama.server])
(import  '[clj_ketama.server Server])

(def ring (atom (ketama/make-ring [(Server. "192.168.1.1" 1)
                                   (Server. "192.168.1.2" 2)
                                   (Server. "192.168.1.3" 3)])))

(defn resource-hash [resource]
  (Math/abs (.hashCode resource))

(ketama/find-node @ring (resource-hash "some_resource"))
(ketama/find-node @ring (resource-hash "test_resource"))
```

## Links

1. Clojure [http://clojure.org/](http://clojure.org/ "Clojure")  
2. Consistent hashing [https://en.wikipedia.org/wiki/Consistent_hashing](https://en.wikipedia.org/wiki/Consistent_hashing "Consistent hashing")  
3. Ketama consistent hashing algorithm [link](https://www.google.ru/?gws_rd=ssl#newwindow=1&q=ketama+consistent+hashing "ketama")  
