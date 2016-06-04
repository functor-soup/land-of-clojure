(require '[clojure.string :as str])
(load-file  "../chapter5/textgameengine.clj")

(def *allowed-commands* ["look" "walk"
                          "pickup" "inventory"])


(defn game-read
  []
  (let [cmd (read-line)]))


(defn game-eval
  [sexp]
  (let [command ]
    (if () (eval))))

(look "garden")
