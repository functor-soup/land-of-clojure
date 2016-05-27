(require '[clojure.string :as s])

;; write to a file
;; (spit "something.txt" "hello world")


(def *max-label-length* 30)

(defn dot-name
  [exp]
  (s/replace exp #"[^0-9^a-z^A-Z]" "-"))

;; put in explanation for breaking every good practice
;; for clojure code, have to make this prettier in the
;; name of art
(defn dot-label
  [exp]
  (cond
    (s/blank? exp) ""
    (> (count exp)  *max-label-length*) (apply str (-> exp
                                                     (subs 0 *max-label-length*)
                                                     (concat "...")))
    :else exp))



(dot-label "alpha gaama est mucho lasto" )
