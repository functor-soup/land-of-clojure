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

(defn describe-path (edge)
  (str "there is a " (edge 2) " going " (edge 1) " from here."))

(describe-path (*edges*))
