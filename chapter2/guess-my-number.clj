
(defn ash
  [number shift]
  (if (> 0 shift)
    (bit-shift-right number (* -1 shift))
    (bit-shift-left number shift)))

(defn guess-my-number
  [small big]
    (ash (+ small big) -1))

(defn smaller
  [smaller bigger]
  (dec (guess-my-number smaller bigger)))

(defn bigger
  [smaller bigger]
  (inc (guess-my-number smaller bigger)))

;; recursive repl
;; why recursive? because global state is garbage

(defn repl-kinda-meh
  [small big]
   (do
      (println " Is this( your number? " (guess-my-number small big))
      (let [yayinput (read-line)]
        (cond
          (= yayinput "greater") (recur (bigger small big) big)
          (= yayinput "smaller")  (recur small (smaller small big))
          (= yayinput "yes") (guess-my-number small big)
          :else (do
                  (println "Command not recognised")
                  (recur small big))
          )
        )))

(defn play-game []
   (repl-kinda-meh 1 100))
