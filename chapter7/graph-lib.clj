(require '[clojure.string :as s])
(require '[clojure.java.shell :as shell])
(require '[clojure.set :as set])

;; write to a file
;; (spit "something.txt" "hello world")


(def *max-label-length* 30)

(def *nodes* ["living-room","garden","attic"])

(def *wizard-nodes* (zipmap *nodes* ["you are in the living-room. a wizard is snoring loudly on the couch" "you are in a beautiful garden. there is a well in front of you" "you are in the attic. there is a giant welding torch in the corner."]))

(def *wizard-edges* (zipmap *nodes* [[["garden" "west" "door"] ["attic" "upstairs" "ladder"]] [["living-room" "east" "door"]] [["living-room" "downstairs" "ladder"]]]))

(defn dot-name
  [exp]
  (s/replace exp #"[^0-9^a-z^A-Z]" "_"))

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
         (dot-label (s/join " => " node))
         "\"];"
         )) nodes))


(defn edges->dot
  [edges]
  (let [node-names (keys edges)]
    (flatten
      (map (fn [node]
           (map
            (fn [edge]
              (str
                  (dot-name node)
                  "->"
                  (dot-name (first edge))
                  "[label=\"" 
                  (dot-label (s/join " " (rest edge)))
                  "\"];"
            )) (edges node)))
       node-names
    ))))

(defn graph->dot
  [nodes edges]
  (str
   "digraph{"
   (s/join "\n" (nodes->dot nodes))
   (s/join "\n" (edges->dot edges))
   "}")
  )

(defn graph->png
 [fname nodes edges]
 (do
   (spit fname (graph->dot nodes edges))
   (shell/sh "dot" "-Tpng" "-O" fname)))



;;(graph->png "wizard" *wizard-nodes* *wizard-edges*)


(defn maplist
  [x]
  (if-not (seq x)
    []
    (cons (seq x) (maplist (rest x)))))


(defn uedges->dot
  [edges]
  (let [node-names (keys edges)
        ml-node-names (maplist node-names)
        get-conn-nodes-names (fn [node]
                              (map #(first %)(edges node)))]
    (remove nil?
     (flatten
      (map (fn [node]
             (let [node-name (first node)
                   conn-nodes (get-conn-nodes-names node-name)
                   exc (seq (set/difference  (set conn-nodes) (set (rest node))))]
               (when exc
                 (map (fn [x]
                        (str
                         (dot-name node-name)
                         "--"
                         (dot-name x)
                         "[label=\"" 
                         (dot-label (s/join " " (rest ((edges node-name) 0))))
                         "\"];"
                         )) exc)))) ml-node-names)))))


(defn ugraph->dot
  [nodes edges]
  (str
   "graph{"
   (s/join "\n" (nodes->dot nodes))
   "\n"
   (s/join "\n" (uedges->dot edges))
   "}")
  )

(defn ugraph->png
  [fname nodes edges]
  (do
    (spit fname (ugraph->dot nodes edges))
    (shell/sh "dot" "-Tpng" "-O" fname)))
