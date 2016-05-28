(require '[clojure.string :as s])

;; write to a file
;; (spit "something.txt" "hello world")


(def *max-label-length* 30)

(def *nodes* ["living-room","garden","attic"])

(def *wizard-nodes* (zipmap *nodes* ["you are in the living-room. a wizard is snoring loudly on the couch" "you are in a beautiful garden. there is a well in front of you" "you are in the attic. there is a giant welding torch in the corner."]))

(def *wizard-edges* (zipmap *nodes* [[["garden" "west" "door"] ["attic" "upstairs" "ladder"]] [["living-room" "east" "door"]] [["living-room" "downstairs" "ladder"]]]))

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



(defn nodes->dot
  [nodes]
  (map (fn [node]
         (str
         (dot-name (first node))
         "[label=\""
         (dot-label (second node))
         "\"];"
         )) nodes))


(defn edges->dot
  [edges]
  (let [node-names (keys edges)]
    (map (fn [node]
           (map
            (fn [edge]
              (str
                (dot-name (node))
                  "->"
                  (dot-name (first edge))
                  "[label=\"" 
                  (dot-label (rest edge))
                  "\"];"
            )) (edges node))
       node-names)
    )))

(edges->dot *wizard-edges* )
*wizard-edges*
