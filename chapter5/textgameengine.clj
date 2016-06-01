(def nodes ["living-room" "garden" "attic"])

(def *nodes* (zipmap
              nodes
              ["you are in the living-room. a wizard is snoring loudly on the couch"
               "you are in a beautiful garden. there is a well in front of you"
               "you are in the attic. there is a giant welding torch in the corner"
               ]))

(def *edges* (zipmap nodes
             [[["garden" "west" "door"] ["attic" "upstairs" "ladder"]]
              [["living-room" "east" "door"]]
              [["living-room" "downstairs" "ladder"]]]))

(def *objects* ["whiskey" "bucket" "frog" "chain"])

(def ^:dynamic *object-locations* (zipmap *objects* ["living-room"
                                           "living-room"
                                           "garden"
                                           "garden"]))

(defn describe-location [location nodes]
  (nodes location))


(defn describe-path [edge]
  (str " There is a " (edge 2) " going " (edge 1) " from here."))

(defn describe-paths [location edges]
   (apply str (map #(describe-path %1) (edges location))))

(defn objects-at [loc objs obj-locs]
  (let [at-loc-p #(= (obj-locs %) loc)]
    (filter at-loc-p objs)
    ))


(defn describe-objects [loc objs obj-locs]
  (let [describe-obj #(str " you see a " % " on the floor.")]
    (apply str (map describe-obj (objects-at loc objs obj-locs)))))

(defn look [location]
  (println (apply str
    (interleave [(describe-location location *nodes*)
                 (describe-paths location *edges*)
                 (describe-objects location *objects* *object-locations*)]
                 (repeat "\n")))))


;; the following function deviates
;; from the book in the sense that this
;; function does not modify any global state
;; (probably need to refactor this)
(defn walk [location direction]
  (let [next (first (filter #(= (% 1) direction) (*edges* location)))]
    (if next
      (do
        (look (next 0))
        (next 0))
      (println "you cannot go that way"))))

;; the non-mutative part of this will eventually make
;; this complicated in the next chapter yay!!!
(defn pickup [location object]
  (if (objects-at location *objects* *object-locations*)))
(walk  "living-room" "west")
