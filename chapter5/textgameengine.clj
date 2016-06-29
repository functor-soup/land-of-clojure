(def nodes ["living-room" "garden" "attic"])

(def *nodes*
  {
    "living-room"  "you are in the living-room. a wizard is snoring loudly on the couch"
    "garden"       "you are in a beautiful garden. there is a well in front of you"
    "attic"        "you are in the attic. there is a giant welding torch in the corner"
  })

(def *edges*
  {
    "living-room" {"garden" ["west" "door"] "attic" ["upstairs" "ladder"]}
    "garden"      {"living-room" ["east" "door"]}
    "attic"       {"living-room" ["downstairs" "ladder"]}
   })

(def *objects* ["whiskey" "bucket" "frog" "chain"])

(def *object-locations* (atom {"living-room" ["whiskey" "bucket"]
                               "garden"      ["frog" "chain"]
                               "body"        [] }))

;; current location
(def *location* (atom "living-room"))

(defn describe-location [location nodes]
  (nodes location))

(defn describe-path [edge]
  (let [[direction object] (edge 1)]
  (str " There is a " object " going " direction " from here.")))

(defn describe-paths [location edges]
   (apply str (map #(describe-path %) (edges location))))

(defn describe-objects [loc obj-locs]
  (let [describe-obj #(str " you see a " % " on the floor.")]
    (apply str (map describe-obj (get obj-locs loc)))))

(defn look []
   (apply str
    (interleave [(describe-location @*location* *nodes*)
                 (describe-paths @*location* *edges*)
                 (describe-objects @*location* @*object-locations*)]
                 (repeat "\n"))))

(defn walk [direction]
  (let [values (get *edges* @*location*)
        keys_  (keys values)
        vals_  (->> (vals values)
                    (map first))
        new-map (zipmap vals_ keys_)
        next (get new-map direction)]
    (if next
      (do
        (reset! *location* next)
        (look))
      "you cannot go that way")))

;; seems messy, doubt any future improvements
(defn pickup [object]
  (if (some #(= object %) (get @*object-locations*  @*location*))
    (do
      (swap! *object-locations* update-in ["body"] #(concat % [object]))
      (str "you are now carrying the " object))
    (str "you cannot get that")))


(defn inventory []
  (str "inventory :"
       (apply str (-> (get @*object-locations* "body")
                      (interleave (repeat " , "))
                      (butlast)))))


