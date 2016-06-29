
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

(defn repl-machine
  [small big]
    (println " Is this your number? " (guess-my-number small big))
    (let [input (read-line)]
      (case input
        "greater" (recur (bigger small big) big)
        "smaller" (recur small (smaller small big))
        "yes"     (guess-my-number small big)
        (do (println "Command not recognised")
            (recur small big))
      )))

(defn play-game [] (repl-machine 1 100))
